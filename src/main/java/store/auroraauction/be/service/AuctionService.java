package store.auroraauction.be.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import store.auroraauction.be.Models.AuctionRequest;
import store.auroraauction.be.Models.JewelryIntoAuctionRequest;
import store.auroraauction.be.entity.Account;
import store.auroraauction.be.entity.Auction;
import store.auroraauction.be.Models.AddStaff;
import store.auroraauction.be.entity.Jewelry;
import store.auroraauction.be.enums.AuctionsStatusEnum;
import store.auroraauction.be.exception.BadRequestException;
import store.auroraauction.be.repository.AuctionRepository;
import store.auroraauction.be.repository.AutheticationRepository;
import store.auroraauction.be.repository.JewelryRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
@Slf4j
@Service
@EnableAsync
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
        auction.setAuctionsStatusEnum(AuctionsStatusEnum.NOTREADY);
        Account staff=autheticationRepository.findById(newauction.getStaff_id()).get();
        auction.setAccount(staff);
        Jewelry newJewelry= jewelryRepository.findById(newauction.getJewelry_id()).get();
        auction.setJewelry(newJewelry);
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

    public List<Auction> getAllAuctionsReady(){
        List<Auction> auctions = auctionRepository.findAll();
        List<Auction> AuctionsReady = new ArrayList<>();
        for(Auction auction: auctions){
            if(auction.getEnd_date().isAfter(LocalDateTime.now())&& auction.getAuctionsStatusEnum()!=AuctionsStatusEnum.NOTREADY && auction.getAuctionsStatusEnum()!=AuctionsStatusEnum.ISCLOSED){
                AuctionsReady.add(auction);
            }
        }

        return AuctionsReady;
    }

    public Auction  getAuction(long id) {
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

//    public Auction addStaff (long id1, AddStaff stafflist){
//        Auction auction = auctionRepository.findById(id1).get();
//        Set<Long> staffIds = stafflist.getStaff_id();
//        Set<Account> accounts = staffIds.stream()
//                .map(id -> autheticationRepository.getAccountById(id))
//                .collect(Collectors.toSet());
//        auction.setAccounts(accounts);
//        auctionRepository.save(auction);
//        return auction;
//    }
    //@Scheduled(fixedDelay = 600000)

    public Auction isClosed(long id){
        Auction auction = auctionRepository.findById(id).get();
//        LocalDateTime createDate = LocalDateTime.now();
//        if(auction.getEnd_date().equals(createDate)){
              auction.setAuctionsStatusEnum(AuctionsStatusEnum.ISCLOSED);
//        }
        auctionRepository.save(auction);
        return auction;
    }

    //@Scheduled(fixedDelay = 600000)
    public Auction isOpened(long id){
        Auction auction = auctionRepository.findById(id).get();
        //LocalDateTime createDate = LocalDateTime.now();
//        if(auction.getStart_date().equals(createDate)) {
           auction.setAuctionsStatusEnum(AuctionsStatusEnum.ISOPENED);
//        }
        auctionRepository.save(auction);
        return auction;
    }

    public Auction isPaused(long id){
        Auction auction = auctionRepository.findById(id).get();
        auction.setAuctionsStatusEnum(AuctionsStatusEnum.ISPAUSED);
        auctionRepository.save(auction);
        return auction;
    }

    public Auction isReady(long id){
        Auction auction = auctionRepository.findById(id).get();
        if(auction.getJewelry()!=null && auction.getAccount()!=null){
            auction.setAuctionsStatusEnum(AuctionsStatusEnum.READY);
        }
        auctionRepository.save(auction);
        return auction;
    }
//    public Auction setJewelryIntoAuction(JewelryIntoAuctionRequest jewelryIntoAuctionRequest, long auction_id){
//        Auction auction = auctionRepository.findById(auction_id).get();
//        //Set<Jewelry> listJewelryID = auction.getJewelries();// lấy danh sách các món trang sức (Jewelry) từ giỏ hàng (Auction).
//        //Jewelry jewelryID = auction.getJewelry();
//        //for (long jewelry_id : jewelryIntoAuctionRequest.getListOfJewelry()){
//            Jewelry newJewelry= jewelryRepository.findById(jewelryIntoAuctionRequest.getJewelry_id()).get();
////            if (listJewelryID == null) {
////                listJewelryID = new HashSet<>();
////            }
//            //newJewelry.getAuctions().add(auction);
//            //jewelryID.add(newJewelry);
//            auction.setJewelry(newJewelry);
//            auctionRepository.save(auction);
//
//        //}
//        return auction;
//    }

    //@Scheduled(fixedRate = 1000) // Check every minute
    public List<Auction> CloseExpiredAuctions() {
        List<Auction> OpenedAuctions = auctionRepository.findByAuctionsStatusEnum(AuctionsStatusEnum.ISOPENED);
        LocalDateTime  now = LocalDateTime .now();
        List<Auction> auctionList= new ArrayList<>();
        for (Auction auction : OpenedAuctions) {
            if (auction.getEnd_date().isBefore(now)) {
                auction.setAuctionsStatusEnum(AuctionsStatusEnum.ISCLOSED);
                auctionRepository.save(auction);
                auctionList.add(auction);
                //log.info("Closed auction: {}. Number of jewelries: {}", auction.getName(), auction.getJewelry());
            }
        }return auctionList;
    }
    // PHAI XEM CO STAFF VA CO JEWELIES CHUA DE MO
    //@Scheduled(fixedDelay = 1000)
    public void OpenedExpiredAuctions() {
        List<Auction> ReadyOpenedAuctions = auctionRepository.findByAuctionsStatusEnum(AuctionsStatusEnum.READY);
        LocalDateTime  now = LocalDateTime .now();
        for (Auction auction : ReadyOpenedAuctions) {
            if(auction.getJewelry()!= null){
                log.info("Auction does have jewelries");
            }
            if(auction.getAccount()!=null){
                log.info("Auction need to have staff to manage auction");
            }
            if (auction.getStart_date().isBefore(now)) {
                auction.setAuctionsStatusEnum(AuctionsStatusEnum.ISOPENED);
                auctionRepository.save(auction);
                //log.info("Opened auction: {}. Number of jewelries: {}", auction.getName(), auction.getJewelry());
            }
        }
    }
    // can set ready sau khi tao
    public void updateStatusReady(){

    }
//    @Scheduled(fixedDelay = 5000)
////    @Scheduled cron =
//    public void printAuctionCount() {
//        final long auctionCount = auctionRepository.count();
//        log.info("There are {} many auction in the database", auctionCount);
//    }
}
