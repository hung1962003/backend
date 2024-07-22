package store.auroraauction.be.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.threeten.bp.LocalDateTime;
import store.auroraauction.be.service.AuctionService;
import store.auroraauction.be.service.BidService;

@Slf4j
@Component
public class ScheduledTasks {

    @Autowired
    private AuctionService auctionService;

    @Autowired
    private BidService bidService;
    @Scheduled(fixedRate = 10000) // Check every 10 seconds
    public void closeAndopenExpiredAuctions() {

        //log.info("Scheduled task started");
        auctionService.CloseExpiredAuctions();
        auctionService.OpenedExpiredAuctions();
        bidService.updateStatus();
        //log.info("Scheduled task ended");
    }

//    @Scheduled(fixedRate = 1000) // Check every 5 seconds
//    public void closeAndopenExpiredAuctions() {
//
//        //log.info("Scheduled task started");
//        LocalDateTime now = LocalDateTime.now();
//        LocalDateTime createDate = LocalDateTime.now();
//        //log.info("{}", now);
//        //log.info("Scheduled task ended");
//    }
//    @Scheduled(fixedRate = 10000) // Check every 1 seconds
//    public void UpdateStatusBid() {
//        bidService.updateStatus();
//
//
//    }
}
