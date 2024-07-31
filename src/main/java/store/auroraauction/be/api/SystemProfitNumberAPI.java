package store.auroraauction.be.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.auroraauction.be.Models.RequestBuyRequest;
import store.auroraauction.be.Models.SystemProfitNumberRequest;
import store.auroraauction.be.service.SystemProfitNumberService;
@RestController // nhan dien api
@CrossOrigin("*")
@RequestMapping("/api/systemProfitNumber")
@SecurityRequirement(name = "api")
public class SystemProfitNumberAPI {
    @Autowired
    private SystemProfitNumberService systemProfitNumberService;
    @PutMapping(    )
    public ResponseEntity updateSystemProfitNumber( @RequestBody SystemProfitNumberRequest systemProfitNumberRequest){

        return ResponseEntity.ok( systemProfitNumberService.updateSystemProfitNumber(systemProfitNumberRequest));
    }
    @PostMapping()
    public ResponseEntity addSystemProfitNumber( @RequestBody SystemProfitNumberRequest systemProfitNumberRequest){

        return ResponseEntity.ok( systemProfitNumberService.addSystemProfitNumber(systemProfitNumberRequest));
    }
}
