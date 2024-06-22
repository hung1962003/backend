package store.auroraauction.be.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import store.auroraauction.be.Models.AddPreliminaryValuation;
import store.auroraauction.be.Models.RequestCompanyRequest;
import store.auroraauction.be.entity.RequestCompany;
import store.auroraauction.be.service.RequestCompanyService;

import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/api/requestcompany")
@SecurityRequirement(name = "api")
@CrossOrigin("*")
public class RequestCompanyAPI {
    @Autowired
    RequestCompanyService requestCompanyService;
    @PostMapping({"/addStaff/{requestbuy_id}"})
    public ResponseEntity add(@PathVariable long requestbuy_id ) {
        RequestCompany requestCompany = requestCompanyService.add(requestbuy_id);
        return ResponseEntity.ok(requestCompany);
    }
    @PostMapping("/addStaff/")
    public ResponseEntity add1(@RequestBody RequestCompanyRequest requestCompany1 ) {
        RequestCompany requestCompany = requestCompanyService.add1(requestCompany1);
        return ResponseEntity.ok(requestCompany);
    }
    @PutMapping("/addPreliminaryValuation/{id}")
    public ResponseEntity AddPreliminaryValuation(@RequestBody AddPreliminaryValuation addPreliminaryValuation,@PathVariable long id ) {
        RequestCompany requestCompany = requestCompanyService.addpreliminaryvaluation(addPreliminaryValuation,id);
        return ResponseEntity.ok(requestCompany);
    }

    @PutMapping("/PreliminaryValuationApprovalAndSendToCompanyAndReceivedJewelry/{id}")
    public ResponseEntity PreliminaryValuationApprovalAndSendToCompanyAndReceivedJewelry(@PathVariable long id ) {
        RequestCompany requestCompany = requestCompanyService.PreliminaryValuationApprovalAndSendToCompanyAndReceivedJewelry(id);
        return ResponseEntity.ok(requestCompany);
    }

    @GetMapping("/getRequestCompanyWithEstimatePrices")
    public  ResponseEntity getRequestCompanyWithEstimatePrices() {
        List<RequestCompany> requestCompany = requestCompanyService.getRequestCompanyWithEstimatePrices();
        return ResponseEntity.ok(requestCompany);
    }
}
