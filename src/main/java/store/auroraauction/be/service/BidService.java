package store.auroraauction.be.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.auroraauction.be.Models.BidRequest;
import store.auroraauction.be.Models.EmailDetail;
import store.auroraauction.be.entity.*;
import store.auroraauction.be.enums.*;
import store.auroraauction.be.exception.BadRequestException;
import store.auroraauction.be.repository.*;
import store.auroraauction.be.utils.AccountUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BidService {
    @Autowired
    private BidRepository bidRepository;
    @Autowired
    private JewelryService jewelryService;
    @Autowired
    private AccountUtils accountUtils;
    @Autowired
    private AuctionRepository auctionRepository;
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private JewelryRepository jewelryRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private AutheticationRepository autheticationRepository;
    @Autowired
    SimpMessagingTemplate messagingTemplate;
    @Autowired
    EmailService emailService;

    @Autowired
    SystemProfitRepository systemProfitRepository;
    public String deleteBid(long id) {
        bidRepository.deleteById(id);
        return "bid delteted";
    }

    public List<Bid> getAllBid() {
        List<Bid> bid = bidRepository.findAll();
        return bid;
    }

    public Bid getBid(long id) {
        Bid bid = bidRepository.findById(id).get();
        return bid;
    }

    public List<Bid> HistoryOfBid(){
        Account account = accountUtils.getCurrentAccount();
        return bidRepository.findBidByBuyer_Id(account.getId());
    }

    public long countUserByAuctionId(Long auctionId) {
        long amount= auctionRepository.countUserByAuctionId(auctionId);
        return amount;
    }
    @Transactional
    public Bid add(BidRequest newbid, long auctionid) throws BadRequestException {
        Bid bid = new Bid();
        Date date = new Date();
        Auction auction = auctionRepository.findAuctionById(auctionid);
        auction.setTotalUser(auctionRepository.countUserByAuctionId(  auctionid));
        auctionRepository.save(auction);
        bid.setCreateAt(date);
        Account account = accountUtils.getCurrentAccount();
        bid.setAccount(account);
        bid.setJewelry(auction.getJewelry());
        bid.setAuction(auction);
        AuctionsStatusEnum status = auction.getAuctionsStatusEnum();
        switch (status) {
            case ISCLOSED :
                throw new BadRequestException("Auction is closed");
            case ISOPENED ,UPCOMING:
                Wallet wallet = walletRepository.findWalletByAccountId(account.getId());
                if (wallet == null) {
                    throw new BadRequestException("Wallet not found for account");
                }
                bid.setBidStatusEnum(BidStatusEnum.BIDDING);
                bid.setWallet(wallet);
                bid.setJewelry(jewelryRepository.findById(auction.getJewelry().getId()));
                Double amountofmoneyinWallet = wallet.getAmount();
                long jewelryId= auction.getJewelry().getId();
                Bid latestbid = bidRepository.findMaxBidInAuction(auctionid, jewelryId);
                if(latestbid ==null) {
                    bid.setAmountofmoney(newbid.getAmountofmoney());
                    bid.setAmountofadd(newbid.getAmountofmoney());
                    bid.setThisIsTheHighestBid(ThisIsTheHighestBid.ONE);
                    wallet.setAmount(amountofmoneyinWallet - newbid.getAmountofmoney());
                    Jewelry jewelry= jewelryRepository.findById(jewelryId);
                    jewelry.setLast_price(newbid.getAmountofmoney());
                    jewelryRepository.save(jewelry);
                    walletRepository.save(wallet);
                }else{
                if (amountofmoneyinWallet > newbid.getAmountofmoney() && newbid.getAmountofmoney() -latestbid.getAmountofmoney()>0) {
                    bid.setAmountofmoney(newbid.getAmountofmoney());
                    bid.setAmountofadd(newbid.getAmountofmoney());
                    bid.setThisIsTheHighestBid(ThisIsTheHighestBid.ONE);
                    wallet.setAmount(amountofmoneyinWallet - newbid.getAmountofmoney());
                    Jewelry jewelry= jewelryRepository.findById(jewelryId);
                    jewelry.setLast_price(newbid.getAmountofmoney());
                    jewelryRepository.save(jewelry);
                    walletRepository.save(wallet);
                } else if(amountofmoneyinWallet < newbid.getAmountofmoney()){
                    throw new BadRequestException("Insufficient balance in wallet for bid.");
                } else if (newbid.getAmountofmoney() -latestbid.getAmountofmoney()<=0){
                    throw new BadRequestException("Your bid is lower than the highest bid");
                }
                }
                bidRepository.save(bid);
                messagingTemplate.convertAndSend("/topic/sendBid", "addBid");// set price moi nhat
                return bid;
            case ISPAUSED:
                throw new BadRequestException("Auction is stopped");
            case NOTREADY:
                throw new BadRequestException("Auction is not ready");
            default:
                throw new BadRequestException("Unknown auction status");
        }}



    @Transactional
    public Bid addhigherBid(BidRequest newbid, long auctionid) throws BadRequestException {
        Account account = accountUtils.getCurrentAccount();
        Bid bid = new Bid();
        Auction auction= auctionRepository.findAuctionById(auctionid);
        auction.setTotalUser(auctionRepository.countUserByAuctionId( auctionid));
        auctionRepository.save(auction);
        long jewelryId= auction.getJewelry().getId();
        Bid latestbid = bidRepository.findMaxBidInAuction(auctionid, jewelryId);
        Bid mylastestbid = bidRepository.findMaxBidInAuctionInBuyer_Id(auctionid, jewelryId, account.getId());
        if(mylastestbid== null){
            return add(newbid, auctionid);
        }else{
            mylastestbid.setThisIsTheHighestBid(ThisIsTheHighestBid.ZERO);
        }
        Double mynewbid = newbid.getAmountofmoney();

        AuctionsStatusEnum status = auction.getAuctionsStatusEnum();
        if(status ==AuctionsStatusEnum.ISOPENED||status ==AuctionsStatusEnum.UPCOMING) {
            if (latestbid != null) {
                Wallet wallet = walletRepository.findWalletByAccountId(account.getId());
                Double amountofmoneyinWallet = wallet.getAmount();
                if( bid.getAmountofadd()==null){
                    return add(newbid, auctionid);
                }
                double newAmount = amountofmoneyinWallet - bid.getAmountofadd();
                if (newAmount > 0) {
                    if (mynewbid - latestbid.getAmountofmoney() > 0) {
                        bid.setAccount(account);
                        bid.setBidStatusEnum(BidStatusEnum.BIDDING);
                        bid.setAuction(auctionRepository.findById(auctionid).orElseThrow(() -> new BadRequestException("Auction not found")));
                        bid.setJewelry(jewelryService.getJewelry(jewelryId));
                        bid.setCreateAt(new Date());
                        bid.setAmountofmoney(mynewbid);
                        bid.setAmountofadd(mylastestbid != null ? mynewbid - mylastestbid.getAmountofmoney() : null);
                        wallet.setAmount(newAmount);
                        walletRepository.save(wallet);
                        bid.setWallet(wallet);
                        bid.setThisIsTheHighestBid(ThisIsTheHighestBid.ONE);
                        bidRepository.save(bid);
                        Jewelry jewelry = jewelryRepository.findById(jewelryId);
                        jewelry.setLast_price(mynewbid);// set price moi nhat
                        jewelryRepository.save(jewelry);
                        bidRepository.countUserInAuction(auction.getId());
                        messagingTemplate.convertAndSend("/topic/sendBid", "addBid");
                        return bid;
                    } else {
                        throw new BadRequestException("Your bid is lower than the highest bid");
                    }
                }else{
                    throw new BadRequestException("Insufficient balance in wallet for bid");
                }
            } else {
                return add(newbid, auctionid);
            }
        }else{
            throw new BadRequestException("Cant Bid");
        }
    }

    @Transactional
    public void updateStatus() {
        Order order = new Order();
        List<Auction> auctionList = auctionRepository.findByAuctionsStatusEnum(AuctionsStatusEnum.ISCLOSED);
        ZoneId hcmZoneId = ZoneId.of("Asia/Ho_Chi_Minh");
        ZonedDateTime hcmTime = ZonedDateTime.now(hcmZoneId);
        //System.out.println(hcmTime.toLocalDateTime());

        if(auctionList != null) {
            for (Auction auction : auctionList) {
                Jewelry jewelry = auction.getJewelry();
                //Hibernate.initialize(jewelry);  // Initialize the lazy-loaded collection
                Set<Bid> bidSet = auction.getBid();
                //Hibernate.initialize(bidSet);  // Initialize the lazy-loaded collection
                Bid theHighestBid = bidRepository.findMaxBidInAuction(auction.getId(), jewelry.getId());
                System.out.println(auction.getAuctionsStatusEnum());
                if (auction.getAuctionsStatusEnum().equals(AuctionsStatusEnum.ISCLOSED) && auction.getEnd_date().isBefore(hcmTime.toLocalDateTime())) {
                    if(bidRepository.findBidByAuction_Id(auction.getId())!= null) {
                        auction.setAuctionsStatusEnum(AuctionsStatusEnum.CANTSELL);
                    }else{
                        //System.out.println("hi");
                        if (theHighestBid != null) {
                            theHighestBid.setBidStatusEnum(BidStatusEnum.SUCCESSFUL);
                            theHighestBid.setThisIsTheHighestBid(ThisIsTheHighestBid.TWO);
                            bidRepository.save(theHighestBid);
                            // luu thong tin jewelry si sold
                            jewelry.setStatusJewelryEnum(StatusJewelryEnum.isSold);
                            jewelryRepository.save(jewelry);
                            // cong tien cho nguoi ban
                            Wallet wallet = walletRepository.findWalletByAccountId(jewelry.getAccount().getId());
                            // tru them tien phi system
                            wallet.setAmount(wallet.getAmount() + theHighestBid.getAmountofmoney() - (double) 5 / 100 * theHighestBid.getAmountofmoney());
                            walletRepository.save(wallet);
                            //Set Order

                            order.setAuction(auction);
                            order.setJewelry(jewelry);
                            order.setFinal_price(theHighestBid.getAmountofmoney());
                            order.setBuyer(theHighestBid.getAccount());
                            order.setCreatedAt(LocalDateTime.now());

                            // Clone the accounts set to avoid shared references
//                  Set<Account> clonedAccounts = new HashSet<>(auction.getAccounts());
//                  order.setAccounts(clonedAccounts);
                            order.setStaff(auction.getAccount());
                            orderRepository.save(order);
                            // set systemprofit
                            //lay 5% chi suat lam phi
                            SystemProfit systemProfit = new SystemProfit();
                            systemProfit.setBalance((double) 5 / 100 * theHighestBid.getAmountofmoney());
                            systemProfit.setDescription("Fee Using System");
                            systemProfit.setDate(LocalDateTime.now());
                            //systemProfit.setTransaction(transaction1);
                            systemProfit.setBid(theHighestBid);
                            systemProfitRepository.save(systemProfit);
                            messagingTemplate.convertAndSend("/topic/BidSuccessfully", "BidSuccessfully");


                            System.out.println("hi");
                            // send mail
                            EmailDetail emailDetail = new EmailDetail();
                            emailDetail.setRecipient(theHighestBid.getAccount().getEmail());
                            emailDetail.setSubject("Đấu giá thành công sản phẩm: " + theHighestBid.getJewelry().getName());
                            emailDetail.setButtonValue("Login to system");
                            emailDetail.setLink("http://aurora-auction/");
                            // nho repo => save xuong database
                            emailService.sendMailTemplate2(emailDetail);
                        }

                        for (Bid bid : bidSet) {
                            if (bid.getJewelry().equals(jewelry) && !bid.equals(theHighestBid)) {
                                EmailDetail emailDetail = new EmailDetail();
                                emailDetail.setRecipient(bid.getAccount().getEmail());
                                emailDetail.setSubject("Bạn chưa thành công trong phiên đấu giá này");
                                emailDetail.setButtonValue("Login to system");
                                emailDetail.setLink("http://aurora-auction/");
                                emailService.sendMailFail(emailDetail);

                                // tim ra account -> cong don tien ->
                                System.out.println(bid.getAccount().getWallet().getAmount());
                                bid.getAccount().getWallet().setAmount(bid.getAccount().getWallet().getAmount()+ bid.getAmountofmoney());
                                bid.setBidStatusEnum(BidStatusEnum.FAILED);
                                bidRepository.save(bid);
                            }
                        }
                    }
                } else {
//                throw new BadRequestException("Auction not ended");
                }
            }
        }
    }





    public  List<Wallet>  returnMoneyToFailBid(){
        List<Bid> bidList = bidRepository.findBidByThisIsTheHighestBid(ThisIsTheHighestBid.ONE);
        List<Wallet> walletlist = new ArrayList<>();
        for (Bid bid : bidList) {
            if(bid.getBidStatusEnum()!= null){
                Wallet wallet = bid.getWallet();
                wallet.setAmount(wallet.getAmount() + bid.getAmountofmoney());
                walletlist.add(wallet);
                bid.setReturnMoneyStatusEnum(ReturnMoneyStatusEnum.RETURN_MONEY);
                bidRepository.save(bid);
                walletRepository.save(wallet);
            }
        }
        return walletlist;
    }

}

