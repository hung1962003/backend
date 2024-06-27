package store.auroraauction.be.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import store.auroraauction.be.Models.BidRequest;
import store.auroraauction.be.entity.Account;
import store.auroraauction.be.entity.Auction;
import store.auroraauction.be.entity.Bid;
import store.auroraauction.be.entity.Wallet;
import store.auroraauction.be.enums.AuctionsStatusEnum;
import store.auroraauction.be.repository.AuctionRepository;
import store.auroraauction.be.repository.BidRepository;
import store.auroraauction.be.repository.WalletRepository;
import store.auroraauction.be.utils.AccountUtils;

import java.util.Date;
import java.util.List;
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

    public String deleteBid(long id) {
        bidRepository.deleteById(id) ;
        return "bid delteted";
    }

    public List<Bid> getAllBid(){
        List<Bid> bid = bidRepository.findAll();
        return bid;
    }

    public Bid getBid( long id) {
        Bid bid = bidRepository.findById(id).get();
        return bid;
    }

    public Bid add(BidRequest newbid ,long jewelry_id, long  auctionid) {
        Bid bid = new Bid();
        Date date = new Date();
        Auction auction =auctionRepository.findAuctionById(auctionid);
        bid.setCreateAt(date);
        Account account =accountUtils.getCurrentAccount();
        bid.setAccount(account);
        bid.setJewelry(jewelryService.getJewelry(jewelry_id));
        bid.setAuction(auction);
        AuctionsStatusEnum status = auction.getAuctionsStatusEnum();
        switch (status){
            case ISCLOSED:
                throw new RuntimeException("Auction is closed");
            case ISOPENED:
                Wallet wallet = walletRepository.findWalletByAccountId(account.getId());
                if (wallet == null) {
                    throw new RuntimeException("Wallet not found for account");
                }
                bid.setWallet(wallet);
                Double amountofmoneyinWallet = wallet.getAmount();
                if(amountofmoneyinWallet>newbid.getAmountofmoney()){
                    bid.setAmountofmoney(newbid.getAmountofmoney());
                    bid.setAmountofadd(newbid.getAmountofmoney());
                    wallet.setAmount(amountofmoneyinWallet-newbid.getAmountofmoney());
                    walletRepository.save(wallet);
                }else{
                    throw new RuntimeException("Insufficient balance in wallet for bid.");
                }
                bidRepository.save(bid);
                return bid;
            case ISSTOPPED:
                throw new RuntimeException("Auction is stopped");
            default:
                throw new RuntimeException("Unknown auction status");
        }

    }

    public Bid addhigherBid( BidRequest newbid,long jewelryId, long auctionid) {
        Account account= accountUtils.getCurrentAccount();
        Bid bid = new Bid();
        Bid latestbid = bidRepository.findMaxBidInAuction(auctionid,jewelryId);
        Bid mylastestbid = bidRepository.findMaxBidInAuctionInBuyer_Id(auctionid,jewelryId,account.getId());
        Double mynewbid= newbid.getAmountofmoney();
        if(latestbid!=null){
            if(Double.compare(mynewbid,latestbid.getAmountofmoney())>0){
                bid.setAccount(account);
                bid.setAuction(auctionRepository.findById(auctionid).orElseThrow(() -> new RuntimeException("Auction not found")));
                bid.setJewelry(jewelryService.getJewelry(jewelryId));
                bid.setCreateAt(new Date());
                bid.setAmountofmoney(mynewbid);
                bid.setAmountofadd(mylastestbid != null ? mynewbid-mylastestbid.getAmountofmoney():null);
                Wallet wallet = walletRepository.findWalletByAccountId(account.getId());
                Double amountofmoneyinWallet = wallet.getAmount();
                wallet.setAmount(amountofmoneyinWallet-newbid.getAmountofmoney());
                walletRepository.save(wallet);
                bid.setWallet(wallet);
                bidRepository.save(bid);
                return bid;
            }else{
                throw new RuntimeException("Your bid is lower than the highest bid");
            }
        }else{
            BidService bidService =new BidService();
            bidService.add( newbid, jewelryId,  auctionid);
        }
        return null;
    }



}
