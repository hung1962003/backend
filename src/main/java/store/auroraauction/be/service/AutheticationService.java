package store.auroraauction.be.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import store.auroraauction.be.Models.*;
import store.auroraauction.be.entity.Account;
import store.auroraauction.be.entity.Cart;
import store.auroraauction.be.entity.Wallet;
import store.auroraauction.be.enums.RoleEnum;


import store.auroraauction.be.exception.BadRequestException;
import store.auroraauction.be.repository.AutheticationRepository;
import store.auroraauction.be.repository.CartRepository;
import store.auroraauction.be.repository.WalletRepository;
import store.auroraauction.be.utils.AccountUtils;

import java.util.List;

@Service
public class AutheticationService implements UserDetailsService {
    // xu li logic
    @Autowired
    EmailService emailService;
    @Autowired //
    AuthenticationManager authenticationManager;
    @Autowired //
    AutheticationRepository autheticationRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    TokenService tokenService;

    @Autowired
    AccountUtils accountUtils;
    @Autowired
    WalletRepository    walletRepository;

    @Autowired
    private CartRepository cartRepository;

    public Account register(RegisterRequest registerRequest) {
        // xu li logic register
        Account account = new Account();
        Cart cart = new Cart();
        Wallet wallet = new Wallet();

        account.setUsername(registerRequest.getUsername());
        account.setLastname(registerRequest.getLastName());
        account.setPhoneNumber(registerRequest.getPhoneNumber());
        account.setFirstname(registerRequest.getFirstName());
        account.setEmail(registerRequest.getEmail());
        account.setAddress(registerRequest.getAddress());
        account.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        account.setRoleEnum(RoleEnum.CLIENT);
        wallet.setAmount(0);
        wallet = walletRepository.save(wallet);
        cart = cartRepository.save(cart);
        account.setWallet(wallet);
        account.setCart(cart);
        account = autheticationRepository.save(account);

        try {
            EmailDetail emailDetail = new EmailDetail();
            emailDetail.setRecipient(account.getEmail());
            emailDetail.setSubject("You are invited to system");
            emailDetail.setMsgBody("aaa");
            emailDetail.setButtonValue("Login to system");
            emailDetail.setLink("http://aurora-auction/");
            // nho repo => save xuong database
            emailService.sendMailTemplate(emailDetail);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }


        return account;
    }
    public void registerADMIN() {
        // xu li logic register
        Account account = new Account();
        Cart cart = new Cart();
        Wallet wallet = new Wallet();

        account.setUsername("system");
        account.setLastname("system");
        account.setPhoneNumber("system");
        account.setFirstname("system");
        account.setEmail("hungnq1962003@gmail.com");
        account.setAddress("Tòa nhà FPT, số 10 phố Phạm Văn Bạch, phường Dịch Vọng, quận Cầu Giấy, Hà Nội, Việt Nam");
        account.setPassword(passwordEncoder.encode("system"));
        account.setRoleEnum(RoleEnum.ADMIN);
        wallet.setAmount(0);
        wallet = walletRepository.save(wallet);
        cart = cartRepository.save(cart);
        account.setWallet(wallet);
        account.setCart(cart);
        account = autheticationRepository.save(account);

        try {
            EmailDetail emailDetail = new EmailDetail();
            emailDetail.setRecipient(account.getEmail());
            emailDetail.setSubject("You are invited to system");
            emailDetail.setMsgBody("aaa");
            emailDetail.setButtonValue("Login to system");
            emailDetail.setLink("http://aurora-auction/");
            // nho repo => save xuong database
            emailService.sendMailTemplate(emailDetail);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }



    }
    public Account updateUPROLEAccount1(UPROLEREQUEST uprolerequest,long id){
        Account account = autheticationRepository.findById(id).get();
        account.setRoleEnum(uprolerequest.getRoleEnum());
        return autheticationRepository.save(account);
    }
    public Account updateUPROLEAccount(UPROLEREQUEST uprolerequest){
        long id = accountUtils.getCurrentAccount().getId();
        Account account = autheticationRepository.findById(id).get();
        account.setRoleEnum(uprolerequest.getRoleEnum());
        return autheticationRepository.save(account);
    }
    public Account updateAccount(Account newaccount, long id) {
        Account account = autheticationRepository.findById(id).get();
        account.setPassword(newaccount.getPassword());
        account.setUsername(newaccount.getUsername());
        account.setFirstname(newaccount.getFirstname());
        account.setLastname(newaccount.getLastname());
        account.setPhoneNumber(newaccount.getPhoneNumber());
        account.setAddress(newaccount.getAddress());
        account.setEmail(newaccount.getEmail());
        return autheticationRepository.save(account);
    }

    public String deleteAccount(Long id) {
        autheticationRepository.deleteById(id);
        return "delete success";
    }

    public List<Account> getAccounts() {
        List<Account> accounts = autheticationRepository.findAll();
        return accounts;
    }
    public List<Account> getStaff() {
        List<Account> accounts = autheticationRepository.findAccountByRoleEnum(RoleEnum.STAFF);
        return accounts;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return autheticationRepository.findAccountByUsername(username);
    }

    public Account login(LoginRequest loginRequest) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        ));

        //=> account chuẩn
        Account account = autheticationRepository.findAccountByUsername(loginRequest.getUsername());
        String token = tokenService.generateToken(account);
        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setId(account.getId());
        accountResponse.setUsername(account.getUsername());
        accountResponse.setRoleEnum(account.getRoleEnum());
        accountResponse.setAddress(account.getAddress());
        accountResponse.setFirstname(account.getFirstname());
        accountResponse.setLastname(account.getLastname());
        accountResponse.setEmail(account.getEmail());
        accountResponse.setPhoneNumber(account.getPhoneNumber());
        accountResponse.setToken(token);
        return accountResponse;
    }

    public Account getAccount(String id) {
        Account account = autheticationRepository.findAll().get(Integer.parseInt(id));
        return account;
    }

    public AccountResponse LoginGoogle(LoginGoogleRequest loginGoogleRequest) {
        AccountResponse accountResponse = new AccountResponse();
        try {
            FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(loginGoogleRequest.getToken());
            String email = firebaseToken.getEmail();
            Account account = autheticationRepository.findByEmail(email);
            if (account == null) {
                account = new Account();
                Wallet wallet = new Wallet();
                Cart cart = new Cart();
                account.setFirstname(firebaseToken.getName());
                account.setEmail(firebaseToken.getEmail());
                account.setUsername(firebaseToken.getEmail());
                account.setRoleEnum(RoleEnum.CLIENT);
                wallet = walletRepository.save(wallet);
                cart = cartRepository.save(cart);
                account.setWallet(wallet);
                account.setCart(cart);
                account = autheticationRepository.save(account);
            }
            accountResponse.setId(account.getId());
            accountResponse.setRoleEnum(RoleEnum.CLIENT);
            accountResponse.setFirstname(account.getFirstname());
            accountResponse.setEmail(account.getEmail());
            String token = tokenService.generateToken(account);
            accountResponse.setToken(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accountResponse;
    }

    public void forgetpassword(ForgetPasswordRequest forgotPasswordRequest) {
        Account account = autheticationRepository.findByEmail(forgotPasswordRequest.getEmail());
        if (account == null) {
            try {
                throw new BadRequestException("Account not found");
            } catch (BadRequestException e) {
                throw new RuntimeException(e);

            }
        }
        EmailDetail emailDetail = new EmailDetail();
        emailDetail.setRecipient(forgotPasswordRequest.getEmail());
        emailDetail.setSubject("Reset Password for account" + forgotPasswordRequest.getEmail());
        emailDetail.setMsgBody("");
        emailDetail.setButtonValue("Reset Password");
        emailDetail.setFullName(account.getFirstname());
        emailDetail.setLink("http://aurora-auction.shop/reset-password?token="+tokenService.generateToken(account));
        //emailDetail.setLink("http://localhost:5173/reset-password?token=" + tokenService.generateToken(account));
        Runnable r = new Runnable() {

            @Override
            public void run() {
                emailService.sendMailTemplate(emailDetail);
            }
        };
        new Thread(r).start();

    }

    public Account resetpassword(ResetPasswordRequest resetPasswordRequest) {
        Account account = accountUtils.getCurrentAccount();
        account.setPassword(passwordEncoder.encode(resetPasswordRequest.getPassword()));
        return autheticationRepository.save(account);

    }
}

