package store.auroraauction.be.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import store.auroraauction.be.Models.EmailDetail;
import store.auroraauction.be.Models.LoginRequest;
import store.auroraauction.be.Models.RegisterRequest;
import store.auroraauction.be.entity.Account;
import store.auroraauction.be.service.AutheticationService;
import store.auroraauction.be.service.EmailService;
import store.auroraauction.be.service.TokenService;

@RestController // nhan dien api

@RequestMapping("api")
@SecurityRequirement(name = "api")
public class AuthenticationAPI {
    //nhan request tu frontend
    @Autowired
    AutheticationService autheticationService;

    @Autowired
    EmailService emailService;

    @GetMapping("/test1")
    public ResponseEntity test() {
        return ResponseEntity.ok("test ok");
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequest registerRequest) {
        Account account = autheticationService.register(registerRequest);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/Accounts")
    public ResponseEntity getAccounts() {
        return ResponseEntity.ok(autheticationService.getAccounts());
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest registerRequest) {
        Account account = autheticationService.login(registerRequest);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/admin-only")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity adminOnly() {
        return ResponseEntity.ok("admin only");
    }

    @GetMapping("test")
    public void sendMail() {
        EmailDetail emailDetail = new EmailDetail();
        emailDetail.setRecipient("hungnqse172466@fpt.edu.vn");
        emailDetail.setSubject("test123");
        emailDetail.setMsgBody("aaa");
        emailService.sendMailTemplate(emailDetail);
    }
}
