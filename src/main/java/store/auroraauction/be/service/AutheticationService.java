package store.auroraauction.be.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import store.auroraauction.be.Models.AccountResponse;
import store.auroraauction.be.Models.EmailDetail;
import store.auroraauction.be.Models.LoginRequest;
import store.auroraauction.be.Models.RegisterRequest;
import store.auroraauction.be.entity.Account;
import store.auroraauction.be.repository.AutheticationRepository;

import java.util.List;

@Service
public class AutheticationService implements UserDetailsService {
    // xu li logic
    @Autowired
    EmailService emailService;
    @Autowired //
    AuthenticationManager authenticationManager;
    @Autowired //
    AutheticationRepository autheticationRepository ;
    @Autowired
    PasswordEncoder passwordEncoder ;
    @Autowired
    TokenService tokenService;
    public Account register(RegisterRequest registerRequest){
        // xu li logic register
        Account account = new Account();
        account.setPhoneNumber(registerRequest.getPhone());
        account.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        // nho repo => save xuong database
        autheticationRepository.save(account);


//        EmailDetail emailDetail = new EmailDetail();
//        emailDetail.setRecipient(account.get);
//        emailDetail.setSubject("test123");
//        emailDetail.setMsgBody("aaa");
//        emailService.sendMailTemplate(emailDetail);

        return account;
    }
    public List<Account> getAccounts(){
        List<Account> accounts = autheticationRepository.findAll();
        return accounts;
    }

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        return autheticationRepository.findAccountByPhoneNumber(phone);
    }
    public Account login(LoginRequest loginRequest){

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.getPhone(),
                    loginRequest.getPassword()
            ));

        //=> account chuẩn
        Account account = autheticationRepository.findAccountByPhoneNumber(loginRequest.getPhone());
        String token = tokenService.generateToken (account);
        AccountResponse accountResponse =new AccountResponse();
        accountResponse.setPhoneNumber(account.getPhoneNumber());
        accountResponse.setToken(token);
        return accountResponse;
    }
}
