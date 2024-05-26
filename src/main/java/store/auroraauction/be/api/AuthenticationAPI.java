package store.auroraauction.be.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import store.auroraauction.be.Models.LoginRequest;
import store.auroraauction.be.Models.RegisterRequest;
import store.auroraauction.be.entity.Account;
import store.auroraauction.be.service.AutheticationService;

@RestController // nhan dien api
public class AuthenticationAPI {
    //nhan request tu frontend
    @Autowired
    AutheticationService autheticationService;

    @GetMapping("/test1")
    public ResponseEntity test() {
        return ResponseEntity.ok("test ok");
    }
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequest registerRequest) {
        Account account =autheticationService.register(registerRequest);
        return ResponseEntity.ok(account);
    }
    @GetMapping("/Accounts")
    public ResponseEntity getAccounts() {
        return ResponseEntity.ok(autheticationService.getAccounts());
    }
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest registerRequest) {
        Account account =autheticationService.login(registerRequest);
        return ResponseEntity.ok(account);
    }
}
