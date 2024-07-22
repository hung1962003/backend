package store.auroraauction.be.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.auroraauction.be.Models.RechargeRequest;
import store.auroraauction.be.Models.TransactionResponse;
import store.auroraauction.be.Models.WithDrawRequest;
import store.auroraauction.be.entity.Transaction;
import store.auroraauction.be.entity.Wallet;
import store.auroraauction.be.service.WalletService;

import java.util.List;


@RestController
@CrossOrigin("*")
@RequestMapping("/api/wallet")
@SecurityRequirement(name = "api")
public class WalletAPI {
    @Autowired
    private WalletService walletService;

    @PostMapping("/request-recharge-vnpay")
    public ResponseEntity createUrl(@RequestBody RechargeRequest rechargeRequest) throws Exception {
        String url = walletService.createUrl(rechargeRequest);
        return  ResponseEntity.ok(url);
    }
    @PutMapping("/recharge-wallet/{wallet_id}")
    public ResponseEntity recharge(@PathVariable long wallet_id) throws Exception {
        Wallet url = walletService.recharge(wallet_id);
        return  ResponseEntity.ok(url);
    }
    @PostMapping("/withDraw")
    public ResponseEntity withDraw(@RequestBody WithDrawRequest withDrawRequest) throws Exception {
        Transaction transaction = walletService.withdraw(withDrawRequest);
        return   ResponseEntity.ok(transaction);
    }
    @GetMapping("/requestsWithDraw")
    public ResponseEntity requestWithDraw() throws Exception {
        List<TransactionResponse> list = walletService.requestWithDraw();
        return  ResponseEntity.ok( list);
    }

    @PutMapping("/acceptWithDraw")
    public ResponseEntity acpWithDraw(@RequestParam("TransactionId") long id) throws Exception {
        Transaction transaction = walletService.acceptwithDraw(id);
        return  ResponseEntity.ok( transaction);
    }

    @PutMapping("/rejectWithDraw")
    public ResponseEntity rejectWithDraw(@RequestParam("TransactionId") long id, @RequestParam("reason") String reason) throws Exception {
        Transaction transaction = walletService.rejectWithDraw(id,reason);
        return ResponseEntity.ok( transaction);
    }
    @GetMapping("/walletDetail/{id}")
    public ResponseEntity walletDetail(@PathVariable long id) throws Exception {
        Wallet wallet = walletService.walletDetail(id);
        return ResponseEntity.ok( wallet);
    }
}
