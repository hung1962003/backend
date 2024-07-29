package store.auroraauction.be.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
import store.auroraauction.be.enums.StatusJewelryEnum;
import store.auroraauction.be.exception.BadRequestException;
import store.auroraauction.be.repository.AuctionRepository;
import store.auroraauction.be.repository.AutheticationRepository;
import store.auroraauction.be.repository.JewelryRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;
@Slf4j
@Service
@EnableAsync
public class AuctionService {
    @Autowired
    SimpMessagingTemplate messagingTemplate;
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
        auction.setStart_date(newauction.getStart_date());
        auction.setEnd_date(newauction.getEnd_date());
        auction.setDescription(newauction.getDescription());
        auction.setImage(newauction.getImage());
        auction.setAuctionsStatusEnum(AuctionsStatusEnum.UPCOMING);
        Account staff=autheticationRepository.findById(newauction.getStaff_id()).get();
        auction.setAccount(staff);
        Jewelry newJewelry= jewelryRepository.findById(newauction.getJewelry_id()).get();
        newJewelry.setStatusJewelryEnum(StatusJewelryEnum.UPCOMING);
        jewelryRepository.save(newJewelry);
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
        auction.setStart_date(newauction.getStart_date());
        auction.setEnd_date(newauction.getEnd_date());
        auction.setDescription(newauction.getDescription());
        auction.setImage(newauction.getImage());
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

    public Auction isPaused(long id){
        Auction auction = auctionRepository.findById(id).get();
        auction.setAuctionsStatusEnum(AuctionsStatusEnum.ISPAUSED);
        auctionRepository.save(auction);
        return auction;
    }

    public Auction UpComing(long id){
        Auction auction = auctionRepository.findById(id).get();
        if(auction.getJewelry()!=null && auction.getAccount()!=null){
            auction.setAuctionsStatusEnum(AuctionsStatusEnum.UPCOMING);
        }
        auctionRepository.save(auction);
        return auction;
    }

    public List<Auction> CloseExpiredAuctions() {
        List<Auction> OpenedAuctions = auctionRepository.findByAuctionsStatusEnum(AuctionsStatusEnum.ISOPENED);
        ZoneId hcmZoneId = ZoneId.of("Asia/Ho_Chi_Minh");
        ZonedDateTime hcmTime = ZonedDateTime.now(hcmZoneId);
        List<Auction> auctionList= new ArrayList<>();
        for (Auction auction : OpenedAuctions) {
            if (auction.getEnd_date().isBefore(hcmTime.toLocalDateTime())) {
                auction.setAuctionsStatusEnum(AuctionsStatusEnum.ISCLOSED);
                auctionRepository.save(auction);
                auctionList.add(auction);
                messagingTemplate.convertAndSend("/topic/time", "BidSuccessfully");
            }
        }return auctionList;
    }


    public void OpenedExpiredAuctions() {
        List<Auction> ReadyOpenedAuctions = auctionRepository.findByAuctionsStatusEnum(AuctionsStatusEnum.UPCOMING);
        ZoneId hcmZoneId = ZoneId.of("Asia/Ho_Chi_Minh");
        ZonedDateTime hcmTime = ZonedDateTime.now(hcmZoneId);
        for (Auction auction : ReadyOpenedAuctions) {
            if(auction.getJewelry()!= null){
                //log.info("Auction does have jewelries");
            }
            if(auction.getAccount()!=null){
               // log.info("Auction need to have staff to manage auction");
            }
            if (auction.getStart_date().isBefore(hcmTime.toLocalDateTime())) {
                auction.setMinPriceBeforeStart(auction.getJewelry().getLast_price());
                auction.setAuctionsStatusEnum(AuctionsStatusEnum.ISOPENED);
                auctionRepository.save(auction);
                messagingTemplate.convertAndSend("/topic/time", "time");
                //log.info("Opened auction: {}. Number of jewelries: {}", auction.getName(), auction.getJewelry());
            }
        }
    }

    public List<Auction> GetAuctionISSOLD() {
        List<Auction>auctionList= auctionRepository.findAuctionByAuctions_Status_Enum(AuctionsStatusEnum.ISSOLD.toString());
        return auctionList;
    }
}
