package store.auroraauction.be.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.auroraauction.be.Models.RequestBuyRequest;
import store.auroraauction.be.entity.RequestBuy;
import store.auroraauction.be.service.RequestBuyServices;
@RestController
@RequestMapping("/api/request-buy")
@SecurityRequirement(name = "api")
@CrossOrigin("*")
public class RequestBuyAPI {
    @Autowired
    private RequestBuyServices requestBuyService;
    @PostMapping()
    public ResponseEntity add(@RequestBody RequestBuyRequest newrequest){
        RequestBuy request =requestBuyService.add(newrequest);
        return ResponseEntity.ok(request);
    }
    @GetMapping()
    public ResponseEntity getRequests(){
        return ResponseEntity.ok(requestBuyService.getAllRequest());
    }
    @GetMapping("{id}")
    public ResponseEntity getRequest(@PathVariable int id){
        return ResponseEntity.ok(requestBuyService.getRequest(id));
    }
    @DeleteMapping("{id}")
    public ResponseEntity deleteRequest(@PathVariable int id){

        return ResponseEntity.ok(requestBuyService.deleteRequest(id));
    }
    @PutMapping("{id}")
    public ResponseEntity updateRequest(@PathVariable int id,@RequestBody RequestBuyRequest newrequest){

        return ResponseEntity.ok( requestBuyService.updateRequest(id,newrequest));
    }
}
