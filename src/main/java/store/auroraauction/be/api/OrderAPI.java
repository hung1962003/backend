package store.auroraauction.be.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.auroraauction.be.Models.CategoryRequest;
import store.auroraauction.be.repository.OrderRepository;
import store.auroraauction.be.service.BidService;
import store.auroraauction.be.service.OrderService;
@RestController
@RequestMapping("api/order")
@SecurityRequirement(name = "api")
@CrossOrigin("*")
public class OrderAPI {
    @Autowired
    private OrderService orderService;
    @Autowired
    private BidService bidService;
    @GetMapping("checkOrder")
    public ResponseEntity getAllOrder(){
        return ResponseEntity.ok(orderService.getAllOrder());
    }
    @GetMapping("{id}")
    public ResponseEntity getOrder(@PathVariable int id){
        return ResponseEntity.ok(orderService.getOrder(id));
    }
    @DeleteMapping("{id}")
    public ResponseEntity deleteOrder(@PathVariable int id){

        return ResponseEntity.ok(orderService.deleteOrder(id));
    }
//    @GetMapping("updateStatus")
//    public ResponseEntity updateStatus(){
//        return ResponseEntity.ok(bidService.updateStatus());
//    }


}
