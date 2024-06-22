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
    @PostMapping("{auction_name}/{jewelry_id}")
    public ResponseEntity add(@RequestBody BidRequest newbid,@PathVariable long jewelry_id,@PathVariable String  auction_name){
        Bid bid = bidService.add(newbid,jewelry_id,auction_name);
        return ResponseEntity.ok(bid);
    }

}
