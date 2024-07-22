package store.auroraauction.be.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.auroraauction.be.service.TransactionService;
@RestController // nhan dien api
@CrossOrigin("*")
@RequestMapping("/api/transaction")
@SecurityRequirement(name = "api")
public class TransactionAPI {
    @Autowired
    private TransactionService transactionService;
    @GetMapping("/allTransaction")
    public ResponseEntity  allTransactions(){
        return ResponseEntity.ok(transactionService.allTransaction());
    }
    @GetMapping("/getTransactionById")
    public ResponseEntity getTransactionById(){
        return ResponseEntity.ok(transactionService.getTransactionById());
    }
}
