package store.auroraauction.be.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import store.auroraauction.be.Models.RechargeRequest;
import store.auroraauction.be.Models.TransactionResponse;
import store.auroraauction.be.Models.WithDrawRequest;
import store.auroraauction.be.entity.Account;
import store.auroraauction.be.entity.Transaction;
import store.auroraauction.be.entity.Wallet;
import store.auroraauction.be.enums.TransactionEnum;
import store.auroraauction.be.exception.BadRequestException;
import store.auroraauction.be.repository.AutheticationRepository;
import store.auroraauction.be.repository.TransactionRepository;
import store.auroraauction.be.repository.WalletRepository;
import store.auroraauction.be.utils.AccountUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private AccountUtils accountUtils;


    @Autowired
    private   EmailService emailService;

    @Autowired //
    AutheticationRepository autheticationRepository;


    @Autowired
    TransactionRepository transactionRepository;

    public String createUrl(RechargeRequest rechargeRequest) throws NoSuchAlgorithmException, InvalidKeyException, Exception{
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime createDate = LocalDateTime.now();
        String formattedCreateDate = createDate.format(formatter);

        Account account = accountUtils.getCurrentAccount();
        String orderId = UUID.randomUUID().toString().substring(0,6);
        Wallet wallet = walletRepository.findWalletByAccountId(account.getId());

        Transaction transaction = new Transaction();
        //System.out.println(rechargeRequest.getAmount());
        transaction.setAmount(Double.parseDouble(rechargeRequest.getAmount()));
        //System.out.println(transaction.getAmount());
        transaction.setTransactionEnum(TransactionEnum.PENDING);
        transaction.setCreatedTransaction(formattedCreateDate);
        transaction.setAccountName(account.getLastname());
        transaction.setAccountNumber(account.getPhoneNumber());
        transaction.setBankName("NCB");
        transaction.setWallet(wallet);
        Transaction transactionReturn = transactionRepository.save(transaction);

        String tmnCode = "M8CVOYU7";
        String secretKey = "47T6UO5KAW3XHS5S7G4CQC6Z610LJS8P";
        String vnpUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
        String returnUrl = "http://localhost:5173/wallet?id="+transactionReturn.getId() ;

        String currCode = "VND";//USD
        Map<String, String> vnpParams = new TreeMap<>();
        vnpParams.put("vnp_Version", "2.1.0");
        vnpParams.put("vnp_Command", "pay");
        vnpParams.put("vnp_TmnCode", tmnCode);
        vnpParams.put("vnp_Locale", "vn");
        vnpParams.put("vnp_CurrCode", currCode);
        vnpParams.put("vnp_TxnRef", orderId);
        vnpParams.put("vnp_OrderInfo", "Nap tien cho vi cua ");// + account.getUsername() +" "
        vnpParams.put("vnp_OrderType", "other");
        vnpParams.put("vnp_Amount", rechargeRequest.getAmount() +"00");
        vnpParams.put("vnp_ReturnUrl", returnUrl);
        vnpParams.put("vnp_CreateDate", formattedCreateDate);
        vnpParams.put("vnp_IpAddr", "188.166.208.107");

        StringBuilder signDataBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : vnpParams.entrySet()) {
            signDataBuilder.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.toString()));
            signDataBuilder.append("=");
            signDataBuilder.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.toString()));
            signDataBuilder.append("&");
        }
        signDataBuilder.deleteCharAt(signDataBuilder.length() - 1); // Remove last '&'

        String signData = signDataBuilder.toString();
        String signed = generateHMAC(secretKey, signData);

        vnpParams.put("vnp_SecureHash", signed);

        StringBuilder urlBuilder = new StringBuilder(vnpUrl);
        urlBuilder.append("?");
        for (Map.Entry<String, String> entry : vnpParams.entrySet()) {
            urlBuilder.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.toString()));
            urlBuilder.append("=");
            urlBuilder.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.toString()));
            urlBuilder.append("&");
        }
        urlBuilder.deleteCharAt(urlBuilder.length() - 1); // Remove last '&'

        return urlBuilder.toString();
    }

    private String generateHMAC(String secretKey, String signData) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac hmacSha512 = Mac.getInstance("HmacSHA512");
        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
        hmacSha512.init(keySpec);
        byte[] hmacBytes = hmacSha512.doFinal(signData.getBytes(StandardCharsets.UTF_8));

        StringBuilder result = new StringBuilder();
        for (byte b : hmacBytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }

    public Wallet recharge(long wallet_id) throws BadRequestException {
        Account account= accountUtils.getCurrentAccount();
        Transaction transaction = transactionRepository.findById(wallet_id);
        Wallet wallet = walletRepository.findWalletByAccountId(account.getId());
        if(transaction.getTransactionEnum().equals(TransactionEnum.PENDING)){
            if(wallet.getId()== transaction.getWallet().getId()){
                wallet.setAmount(wallet.getAmount()+transaction.getAmount());
            }
        } else{
            throw new BadRequestException("Reload");
        }
        transaction.setTransactionEnum(TransactionEnum.RECHARGE);
        transactionRepository.save(transaction);
        return walletRepository.save(wallet);
    }

    public Wallet walletDetail(long id){
        return walletRepository.findWalletByAccountId(id);
    }

    public List<TransactionResponse> requestWithDraw(){
        List<TransactionResponse> ListtransactionResponses = new ArrayList<>();
        List<Transaction> transactions = transactionRepository.findTransactionByTransactionEnum(TransactionEnum.WITHDRAW_PENDING);
        for(Transaction transaction : transactions){
            TransactionResponse transactionResponse = new TransactionResponse();
            transactionResponse.setTransactionId(transaction.getId());
            transactionResponse.setAmount(transaction.getAmount());
            transactionResponse.setTransactionEnum(transaction.getTransactionEnum());
            transactionResponse.setCreatedTransaction(transaction.getCreatedTransaction());
            ListtransactionResponses.add(transactionResponse);

        }
        return ListtransactionResponses;

    }

    public Transaction withdraw(WithDrawRequest withDrawRequest) throws BadRequestException {
        Account account =accountUtils.getCurrentAccount();
        Wallet wallet = walletRepository.findWalletByAccountId(account.getId());
        if (wallet.getAmount() >= withDrawRequest.getAmount()){
            Transaction transaction = new Transaction();
            transaction.setAmount(withDrawRequest.getAmount());
            transaction.setTransactionEnum(TransactionEnum.WITHDRAW_PENDING);
            transaction.setAccountName(withDrawRequest.getAccountName());
            transaction.setAccountNumber(withDrawRequest.getAccountNumber());
            transaction.setBankName(withDrawRequest.getBankName());
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            transaction.setCreatedTransaction(now.format(formatter));
            transaction.setWallet(walletRepository.findWalletByAccountId(account.getId()));
            transactionRepository.save(transaction);
            wallet.setAmount(wallet.getAmount()-withDrawRequest.getAmount());
            walletRepository.save(wallet);
            return transactionRepository.save(transaction);
        }else{
            throw new BadRequestException("Insufficient balance in wallet for withdrawal.");
        }
    }

    public Transaction acceptwithDraw(long id){
        Transaction transaction = transactionRepository.findById(id);
        Account account = autheticationRepository.findAccountByFirstname(transaction.getAccountName());
       if(transaction !=null){
           transaction.setTransactionEnum(TransactionEnum.WITHDRAW_SUCCESS);
            threadSendMail(account,"Withdrawal Successfully", "Thank you for trusting and using us");
           return transactionRepository.save(transaction);
       }else{
           return null;
       }
    }

    public Transaction rejectWithDraw(long id, String reason) {
        Transaction transaction = transactionRepository.findById(id);
        Account account = autheticationRepository.findAccountByFirstname(transaction.getAccountName());
        if (transaction != null) {
            Wallet wallet = transaction.getWallet();
            wallet.setAmount(wallet.getAmount() + transaction.getAmount());
            transaction.setTransactionEnum(TransactionEnum.WITHDRAW_REJECT);
            transaction.setReasonWithdrawReject(reason);
            threadSendMail(account, "Withdrawal failed", "You Cannot Withdraw Because: " + reason);
            return transactionRepository.save(transaction);
        } else {
            return null;
        }
    }

    public void threadSendMail(Account account,String subject, String description){
        Runnable r = new Runnable() {
            @Override
            public void run() {
                emailService.sendMail(account,subject,description);
            }

        };
        new Thread(r).start();
}}






















































































