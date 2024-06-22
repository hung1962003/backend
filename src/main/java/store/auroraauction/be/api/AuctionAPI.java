package store.auroraauction.be.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.auroraauction.be.Models.AuctionRequest;
import store.auroraauction.be.entity.Auction;
import store.auroraauction.be.Models.AddStaff;
import store.auroraauction.be.service.AuctionService;

@RestController // nhan dien api
@CrossOrigin("*")
@RequestMapping("/api/auction")
@SecurityRequirement(name = "api")
public class AuctionAPI {
    @Autowired
    AuctionService auctionService;
    @PostMapping("addStaff/{id}")
    public ResponseEntity add(@RequestBody AddStaff stafflist, @PathVariable long id){
        Auction auction =auctionService.addStaff(id,stafflist);
        return ResponseEntity.ok(auction);
    }
    @PostMapping()
    public ResponseEntity add(@RequestBody AuctionRequest newauction){
        Auction auction =auctionService.add(newauction);
        return ResponseEntity.ok(auction);
    }
    @GetMapping()
    public ResponseEntity getAllAuctions(){
        return ResponseEntity.ok(auctionService.getAllAuctions());
    }
    @GetMapping("{id}")
    public ResponseEntity getAuction(@PathVariable long id){
        return ResponseEntity.ok(auctionService.getAuction(id));
    }
    @DeleteMapping("{id}")
    public ResponseEntity deleteAuction(@PathVariable int id){

        return ResponseEntity.ok(auctionService.deleteAuction(id));
    }
    @PutMapping("{id}")
    public ResponseEntity updateAuction(@PathVariable int id,@RequestBody AuctionRequest newauction){

        return ResponseEntity.ok( auctionService.updateAuction(id,newauction));
    }
}
