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
        account.setUsername(registerRequest.getUsername());
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
    public Account updateAccount (Account newaccount, long id){
        Account account =  autheticationRepository.findById(id).get();
        account.setPassword(newaccount.getPassword());
        account.setUsername(newaccount.getUsername());
        account.setName(newaccount.getName());
        account.setPhoneNumber(newaccount.getPhoneNumber());
        account.setAddress(newaccount.getAddress());
        account.setEmail(newaccount.getEmail());
        return autheticationRepository.save(account);
    }
    public String deleteAccount(Long id){
        autheticationRepository.deleteById(id);
        return "delete success";
    }
    public List<Account> getAccounts(){
        List<Account> accounts = autheticationRepository.findAll();
        return accounts;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return autheticationRepository.findAccountByUsername(username);
    }
    public Account login(LoginRequest loginRequest){

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
            ));

        //=> account chuẩn
        Account account = autheticationRepository.findAccountByUsername(loginRequest.getUsername());
        String token = tokenService.generateToken (account);
        AccountResponse accountResponse =new AccountResponse();
        accountResponse.setUsername(account.getUsername());
        accountResponse.setToken(token);
        return accountResponse;
    }
    public Account getAccount (String id){
        Account account = autheticationRepository.findAll().get(Integer.parseInt(id));
        return account;
    }

}
