package store.auroraauction.be.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import store.auroraauction.be.Models.AuctionRequest;
import store.auroraauction.be.Models.JewelryIntoAuctionRequest;
import store.auroraauction.be.entity.Account;
import store.auroraauction.be.entity.Auction;
import store.auroraauction.be.Models.AddStaff;
import store.auroraauction.be.entity.Jewelry;
import store.auroraauction.be.enums.AuctionsStatusEnum;
import store.auroraauction.be.repository.AuctionRepository;
import store.auroraauction.be.repository.AutheticationRepository;
import store.auroraauction.be.repository.JewelryRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuctionService {
    @Autowired
    private AuctionRepository auctionRepository;
    @Autowired
    private AutheticationRepository autheticationRepository;
    @Autowired
    private JewelryRepository jewelryRepository;
    public Auction add(AuctionRequest newauction) {
        Auction auction = new Auction();
        auction.setDescription(newauction.getDescription());
        auction.setName(newauction.getName());
        auction.setTitle(newauction.getTitle());
        auction.setStart_date(newauction.getStart_date());
        auction.setEnd_date(newauction.getEnd_date());
        auction.setDescription(newauction.getDescription());
        auction.setImage(newauction.getImage());
        auction  = auctionRepository.save(auction);
        return auction;
    }
    public String deleteAuction(long id) {
        auctionRepository.deleteById(id) ;
        return "request delteted";
    }

    public List<Auction> getAllAuctions(){
        List<Auction> auctions = auctionRepository.findAll();
        return auctions;
    }
    public Auction getAuction(long id) {
        Auction auctions = auctionRepository.findById(id).get();
        return auctions;
    }
    public Auction updateAuction(long id1, AuctionRequest newauction) {
        Auction auction = auctionRepository.findById(id1).get();
        auction.setDescription(newauction.getDescription());
        auction.setTitle(newauction.getTitle());
        auction.setStart_date(newauction.getStart_date());
        auction.setEnd_date(newauction.getEnd_date());
        auction.setDescription(newauction.getDescription());
        auction.setImage(newauction.getImage());
        auctionRepository.save(auction);
        return auction;
    }
    public Auction addStaff (long id1, AddStaff stafflist){
        Auction auction = auctionRepository.findById(id1).get();
        Set<Long> staffIds = stafflist.getStaff_id();
        Set<Account> accounts = staffIds.stream()
                .map(id -> autheticationRepository.getAccountById(id))
                .collect(Collectors.toSet());
        auction.setAccounts(accounts);
        auctionRepository.save(auction);
        return auction;
    }
    public Auction isClosed(long id){
        Auction auction = auctionRepository.findById(id).get();
        auction.setAuctionsStatusEnum(AuctionsStatusEnum.ISCLOSED);
        auctionRepository.save(auction);
        return auction;
    }
    public Auction isOpened(long id){
        Auction auction = auctionRepository.findById(id).get();
        auction.setAuctionsStatusEnum(AuctionsStatusEnum.ISOPENED);
        auctionRepository.save(auction);
        return auction;
    }
    public Auction isStopped(long id){
        Auction auction = auctionRepository.findById(id).get();
        auction.setAuctionsStatusEnum(AuctionsStatusEnum.ISSTOPPED);
        auctionRepository.save(auction);
        return auction;
    }
    public Auction setJewelryIntoAuction(JewelryIntoAuctionRequest jewelryIntoAuctionRequest, long auction_id){
        Auction auction = auctionRepository.findById(auction_id).get();
        Set<Jewelry> listJewelryID = auction.getJewelries();// lấy danh sách các món trang sức (Jewelry) từ giỏ hàng (Auction).
        for (long jewelry_id : jewelryIntoAuctionRequest.getListOfJewelry()){
            Jewelry newJewelry= jewelryRepository.findById(jewelry_id).get();

            if (listJewelryID == null) {
                listJewelryID = new HashSet<>();
            }
            newJewelry.getAuctions().add(auction);
            listJewelryID.add(newJewelry);
            auction.setJewelries(listJewelryID);
            auctionRepository.save(auction);
        }
        return auction;
    }

}
