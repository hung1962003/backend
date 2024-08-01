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

import java.sql.SQLOutput;
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
    SystemProfitNumberService  systemProfitNumberService;
    @Autowired
    SystemProfitNumberRepository systemProfitNumberRepository;;
    @Autowired
    SystemProfitRepository systemProfitRepository;
    @Autowired
    AutheticationService autheticationService ;
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
    public Bid addhigherBid(BidRequest newbid, long auctionid) throws BadRequestException {
        Account account = accountUtils.getCurrentAccount();
        Bid bid = new Bid();

        ZoneId hcmZoneId = ZoneId.of("Asia/Ho_Chi_Minh");
        ZonedDateTime hcmTime = ZonedDateTime.now(hcmZoneId);

        Auction auction = auctionRepository.findAuctionById(auctionid);
        long jewelryId = auction.getJewelry().getId();

        Bid latestbid = bidRepository.findMaxBidInAuction(auctionid, jewelryId);
        Bid mylastestbid = bidRepository.findMaxBidInAuctionInBuyer_Id(auctionid, jewelryId, account.getId());

        AuctionsStatusEnum status = auction.getAuctionsStatusEnum();
        switch (status) {
            case ISCLOSED:
                throw new BadRequestException("Auction is closed");
            case ISOPENED, UPCOMING:
                Wallet wallet = walletRepository.findWalletByAccountId(account.getId());
                Double amountofmoneyinWallet = wallet.getAmount();
                Double mynewbid = newbid.getAmountofmoney();
                Jewelry jewelry = jewelryRepository.findById(jewelryId);

                if (account.getId() == auction.getJewelry().getAccount().getId()) {
                    throw new BadRequestException("Can't bid your own jewelry");
                }
                if(newbid.getAmountofmoney()%10!=0){
                    throw new BadRequestException("You have to bid amount of money that divide by 10");
                }
                //set thong tin co ban
                bid.setBidStatusEnum(BidStatusEnum.BIDDING);
                bid.setWallet(wallet);
                bid.setJewelry(jewelryRepository.findById(auction.getJewelry().getId()));
                bid.setCreateAt(hcmTime.toLocalDateTime());
                bid.setAuction(auctionRepository.findById(auctionid).orElseThrow(() -> new BadRequestException("Auction not found")));
                bid.setAccount(account);
                if(latestbid==null){
                    // them dk lon hon gia thap nhat
                    if(amountofmoneyinWallet-mynewbid >0 && newbid.getAmountofmoney()>=auction.getJewelry().getLow_estimated_price()){
                        //add 1 bid dau tien
                        bid.setAmountofmoney(mynewbid);
                        bid.setAmountofadd(mynewbid);
                        wallet.setAmount(amountofmoneyinWallet - mynewbid);
                        walletRepository.save(wallet);
                        bid.setWallet(wallet);
                        bid.setThisIsTheHighestBid(ThisIsTheHighestBid.ONE);
                        bidRepository.save(bid);

                        jewelry.setLast_price(mynewbid);// set price moi nhat
                        jewelryRepository.save(jewelry);
                    }
                    else if(amountofmoneyinWallet < newbid.getAmountofmoney()){
                        throw new BadRequestException("Insufficient balance in wallet for bid.");
                    }
                    else if (mynewbid - auction.getJewelry().getLow_estimated_price()<=0){
                        throw new BadRequestException("Your bid is lower than the low estimated price of jewelry");
                    }
                }
                else if(mylastestbid== null && latestbid!=null){
                    if(amountofmoneyinWallet-mynewbid>0 && mynewbid > latestbid.getAmountofmoney()){
                        //add bid lan thu 2
                        bid.setAmountofmoney(mynewbid);
                        bid.setAmountofadd(mynewbid);
                        wallet.setAmount(amountofmoneyinWallet - mynewbid);
                        walletRepository.save(wallet);
                        bid.setWallet(wallet);
                        bid.setThisIsTheHighestBid(ThisIsTheHighestBid.ONE);
                        bidRepository.save(bid);

                        jewelry.setLast_price(mynewbid);// set price moi nhat
                        jewelryRepository.save(jewelry);
                    }
                    else if(amountofmoneyinWallet < newbid.getAmountofmoney()){
                        throw new BadRequestException("Insufficient balance in wallet for bid.");
                    }
                    else if (mynewbid -latestbid.getAmountofmoney()<=0){
                        throw new BadRequestException("Your bid is lower than the highest bid");
                    }

                }
                else {
                    //add bid moi lan thu n
                    mylastestbid.setThisIsTheHighestBid(ThisIsTheHighestBid.ZERO);
                    //tien chenh lech
                    double amountofMoneyNeedToMinusMore = mynewbid - mylastestbid.getAmountofmoney();
                    // tien con lai trong wallet
                    double newAmount = amountofmoneyinWallet - amountofMoneyNeedToMinusMore;
                    if (amountofMoneyNeedToMinusMore>0 && mynewbid>latestbid.getAmountofmoney()) {
                        if (newAmount > 0) {
                            bid.setAmountofmoney(mynewbid);
                            bid.setAmountofadd(amountofMoneyNeedToMinusMore);
                            bid.setThisIsTheHighestBid(ThisIsTheHighestBid.ONE);
                            wallet.setAmount(newAmount);
                            walletRepository.save(wallet);
                            bid.setWallet(wallet);
                            bidRepository.save(bid);

                            jewelry.setLast_price(mynewbid);// set price moi nhat
                            jewelryRepository.save(jewelry);
                        }
                        else{
                            throw new BadRequestException("Insufficient balance in wallet for bid");
                        }
                    }
                    else {
                        throw new BadRequestException("Your bid is lower than the highest bid");
                    }
                }
                auction.setTotalUser(bidRepository.countUserInAuction(auction.getId()));
                auctionRepository.save(auction);
                messagingTemplate.convertAndSend("/topic/sendBid", "addBid");
                return bid;

            case ISPAUSED:
                throw new BadRequestException("Auction is stopped");
            case NOTREADY:
                throw new BadRequestException("Auction is not ready");
            case ISSOLD:
                throw new BadRequestException("Auction is sold");
            default:
                throw new BadRequestException("Unknown auction status");
        }
    }

    @Transactional
    public void updateStatus() {
        //Order order = new Order();
        List<Auction> auctionList = auctionRepository.findByAuctionsStatusEnum(AuctionsStatusEnum.ISCLOSED);
        ZoneId hcmZoneId = ZoneId.of("Asia/Ho_Chi_Minh");
        ZonedDateTime hcmTime = ZonedDateTime.now(hcmZoneId);

        if(auctionList != null) {
            for (Auction auction : auctionList) {
                Jewelry jewelry = auction.getJewelry();
                //Hibernate.initialize(jewelry);  // Initialize the lazy-loaded collection
                Set<Bid> bidSet = auction.getBid();
                //Hibernate.initialize(bidSet);  // Initialize the lazy-loaded collection
                Bid theHighestBid = bidRepository.findMaxBidInAuction(auction.getId(), jewelry.getId());
                System.out.println(auction.getAuctionsStatusEnum());
                if (auction.getAuctionsStatusEnum().equals(AuctionsStatusEnum.ISCLOSED) && auction.getEnd_date().isBefore(hcmTime.toLocalDateTime())) {
                    System.out.println("333");
                    if (theHighestBid != null) {
                        System.out.println("11");
                        auction.setAuctionsStatusEnum(AuctionsStatusEnum.ISSOLD);

                        theHighestBid.setBidStatusEnum(BidStatusEnum.SUCCESSFUL);
                        theHighestBid.setThisIsTheHighestBid(ThisIsTheHighestBid.TWO);
                        bidRepository.save(theHighestBid);
                        // luu thong tin jewelry si sold
                        jewelry.setStatusJewelryEnum(StatusJewelryEnum.isSold);
                        jewelryRepository.save(jewelry);
                        // cong tien cho nguoi ban
                        Wallet wallet = walletRepository.findWalletByAccountId(jewelry.getAccount().getId());
                        // tru them tien phi system
                        //get systemprofitnumber
                        SystemProfitNumber systemProfitNumber=systemProfitNumberService.getSystemProfitNumber();
                        //double systemProfitprecent =systemProfitNumber.getPercentofsystemprofit();
                        //cong tien seller
                        wallet.setAmount(wallet.getAmount() + theHighestBid.getAmountofmoney() - (double) 5 /100 * theHighestBid.getAmountofmoney());
                        walletRepository.save(wallet);
                        //Set Order
                        try {
//                            order.setAuction(auction);
//                            order.setJewelry(jewelry);
//                            order.setFinal_price(theHighestBid.getAmountofmoney());
//                            order.setBuyer(theHighestBid.getAccount());
//                            order.setCreatedAt(LocalDateTime.now());
//                            order.setStaff(auction.getAccount());
//                            orderRepository.save(order);
                        }catch(Exception e){
                            e.printStackTrace(); // In ra chi tiết lỗi
                        }

                        // Clone the accounts set to avoid shared references
//                  Set<Account> clonedAccounts = new HashSet<>(auction.getAccounts());
//                  order.setAccounts(clonedAccounts);

                        // set systemprofit
                        //lay 5% chi suat lam phi
                        SystemProfit systemProfit = new SystemProfit();
                        systemProfit.setBalance((double) 5 /100 * theHighestBid.getAmountofmoney());
                        systemProfit.setDescription("Fee Using System");
                        systemProfit.setDate(LocalDateTime.now());
                        //systemProfit.setTransaction(transaction1);
                        systemProfit.setBid(theHighestBid);
                        systemProfitRepository.save(systemProfit);
                        messagingTemplate.convertAndSend("/topic/time", "BidSuccessfully");


//
//                      chuyen tien cho he thong

                        Account accountsystem= autheticationRepository.findAccountByUsername("system");
                        if(accountsystem != null){
                            autheticationService.registerADMIN();
                        }
                        Wallet walletsystem= walletRepository.findWalletByAccountId(accountsystem.getId());
                        walletsystem.setAmount(walletsystem.getAmount()+(double) 5 /100 * theHighestBid.getAmountofmoney());
                        walletRepository.save(walletsystem);

                        // send mail
                        EmailDetail emailDetail = new EmailDetail();
                        emailDetail.setRecipient(theHighestBid.getAccount().getEmail());
                        emailDetail.setSubject("Đấu giá thành công sản phẩm: " + theHighestBid.getJewelry().getName());
                        emailDetail.setButtonValue("Login to system");
                        emailDetail.setLink("http://aurora-auction/");
                        // nho repo => save xuong database
                        try {
                            emailService.sendMailTemplate2(emailDetail);
                        }catch (Exception ex){

                        }

                        auctionRepository.save(auction);
                        System.out.println(auction.getAuctionsStatusEnum());
                    } else if(theHighestBid == null) {
                        System.out.println("22");
                        auction.setAuctionsStatusEnum(AuctionsStatusEnum.CANTSELL);
                        jewelry.setStatusJewelryEnum(StatusJewelryEnum.CANTSELL);
                        auctionRepository.save(auction);
                        System.out.println(auction.getAuctionsStatusEnum());
                        jewelryRepository.save(jewelry);
                        System.out.println(jewelry.getStatusJewelryEnum());
                    }

                    for (Bid bid : bidSet) {
                        if (bid.getJewelry().equals(jewelry) && !bid.equals(theHighestBid)) {
                            EmailDetail emailDetail = new EmailDetail();
                            emailDetail.setRecipient(bid.getAccount().getEmail());
                            emailDetail.setSubject("Bạn chưa thành công trong phiên đấu giá này");
                            emailDetail.setButtonValue("Login to system");
                            emailDetail.setLink("http://aurora-auction/");
                            try {
                                emailService.sendMailFail(emailDetail);
                            }catch (Exception ex){

                            }
                            // tim ra account -> cong don tien ->

//                                System.out.println(bid.getAccount().getWallet().getAmount());
//                                bid.getAccount().getWallet().setAmount(bid.getAccount().getWallet().getAmount()+ bid.getAmountofmoney());
                            bid.setBidStatusEnum(BidStatusEnum.FAILED);


                            bidRepository.save(bid);
                        }
                    }
                } else {
//                throw new BadRequestException("Auction not ended");
                }
            }
        }
    }

    public  void  returnMoneyToFailBid(){
        List<Auction> auctionList = auctionRepository.findAll();
        List<Bid> bidList = bidRepository.findBidByThisIsTheHighestBid(ThisIsTheHighestBid.ONE);
        for (Bid bid : bidList) {
            //System.out.println(bid.getId());
            if(bid.getReturnMoneyStatusEnum()== null && bid.getAuction().getAuctionsStatusEnum()==AuctionsStatusEnum.ISSOLD){// ISCLOSED
                Wallet wallet = bid.getWallet();
                wallet.setAmount(wallet.getAmount() + bid.getAmountofmoney());
                bid.setReturnMoneyStatusEnum(ReturnMoneyStatusEnum.RETURN_MONEY);
                //System.out.println("1");
                //System.out.println(bid.getAccount().getId());
                //System.out.println( bid.getAmountofmoney());
                bidRepository.save(bid);
                walletRepository.save(wallet);
            }
        }
    }

}

