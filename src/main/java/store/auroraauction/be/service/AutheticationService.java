package store.auroraauction.be.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import store.auroraauction.be.Models.LoginRequest;
import store.auroraauction.be.Models.RegisterRequest;
import store.auroraauction.be.entity.Account;
import store.auroraauction.be.repository.AutheticationRepository;

import java.util.List;

@Service
public class AutheticationService implements UserDetailsService {
    // xu li logic
    @Autowired //
    AuthenticationManager authenticationManager;

    @Autowired //
    AutheticationRepository autheticationRepository ;
    @Autowired
    PasswordEncoder passwordEncoder ;
    public Account register(RegisterRequest registerRequest){
        // xu li logic register
        Account account = new Account();
        account.setPhoneNumber(registerRequest.getPhone());
        account.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        // nho repo => save xuong database
        autheticationRepository.save(account);
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

        return autheticationRepository.findAccountByPhoneNumber(loginRequest.getPhone());
    }
}
