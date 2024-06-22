package store.auroraauction.be.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import store.auroraauction.be.Models.BidRequest;
import store.auroraauction.be.entity.Account;
import store.auroraauction.be.entity.Bid;
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
    public Bid add(BidRequest newbid ,long jewelry_id, String auction_name) {
        Bid bid = new Bid();
        Date date = new Date();
        bid.setCreateAt(date);
        Account account =accountUtils.getCurrentAccount();
        bid.setAccount(account);
        bid.setJewelry(jewelryService.getJewelry(jewelry_id));
        bid.setAuction(auctionRepository.findAuctionByName(auction_name));
        bid.setWallet(walletRepository.findWalletByAccountId(accountUtils.getCurrentAccount().getId()));
        Double amountofmoneyinWallet = walletRepository.findWalletByAccountId(account.getId()).getAmount();
        if(amountofmoneyinWallet>newbid.getAmountofmoney()){
            bid.setAmountofmoney(newbid.getAmountofmoney());
        }else{
            throw new RuntimeException("Insufficient balance in wallet for bid.");
        }
        bidRepository.save(bid);
        return bid;
    }

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
    public Bid updateCategory(long id, BidRequest newbid) {
       Bid bid = bidRepository.findById(id).get();
//        bid.setAccount(accountUtils.getCurrentAccount());
//        bid.setJewelry(jewelryService.getJewelry(jewelry_id));
//        bid.setAuction(auctionRepository.findAuctionByName(auction_name));
//        bidRepository.save(bid);
           return bid;
    }
}
