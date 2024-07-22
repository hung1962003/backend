package store.auroraauction.be.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.auroraauction.be.Models.BidRequest;
import store.auroraauction.be.Models.CategoryRequest;
import store.auroraauction.be.entity.Bid;
import store.auroraauction.be.entity.Category;
import store.auroraauction.be.service.BidService;
@RestController
@RequestMapping("api/bid")
@SecurityRequirement(name = "api")
@CrossOrigin("*")
public class BidAPI {
    @Autowired
    BidService bidService;
    @PostMapping("add/{auction_id}")
    public ResponseEntity add(@RequestBody BidRequest newbid,@PathVariable long  auction_id){
        Bid bid = bidService.add(newbid,auction_id);
        return ResponseEntity.ok(bid);
    }
    @PostMapping("addhigherBid/{auction_id}")
    public ResponseEntity addhigherBid(@RequestBody BidRequest newbid,@PathVariable long  auction_id){
        Bid bid = bidService.addhigherBid(newbid,auction_id);
        return ResponseEntity.ok(bid);
    }
    @GetMapping("{id}")
    public ResponseEntity getBid(@PathVariable Long id){
        return ResponseEntity.ok(bidService.getBid(id));
    }
    @GetMapping("")
    public ResponseEntity getAllBid(){
        return ResponseEntity.ok(bidService.getAllBid());
    }
    @DeleteMapping("{id}")
    public ResponseEntity deleteBid(@PathVariable long id){

        return ResponseEntity.ok(bidService.deleteBid(id));
    }
    @GetMapping("/HistoryOfBid")
    public ResponseEntity HistoryOfBid(){
        return ResponseEntity.ok(bidService.HistoryOfBid());
    }


}
