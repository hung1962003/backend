package store.auroraauction.be.service;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import store.auroraauction.be.Models.TransactionResponse;
import store.auroraauction.be.entity.Account;
import store.auroraauction.be.entity.Transaction;
import store.auroraauction.be.entity.Wallet;
import store.auroraauction.be.repository.TransactionRepository;
import store.auroraauction.be.repository.WalletRepository;
import store.auroraauction.be.utils.AccountUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class TransactionService {
    @Autowired
    private AccountUtils accountUtils;
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    public List<TransactionResponse> getTransactionById() {
        List<TransactionResponse> listTransactionResponseDTO = new ArrayList<>();
        Account account = accountUtils.getCurrentAccount();
        Wallet wallet = walletRepository.findWalletByAccountId(account.getId());
        List<Transaction> transactions = transactionRepository.findTransactionByWallet_Id(wallet.getId());
        for (Transaction transaction : transactions) {
            TransactionResponse transactionResponse = new TransactionResponse();
            transactionResponse.setTransactionId(transaction.getId());
            transactionResponse.setTransactionEnum(transaction.getTransactionEnum());
            transactionResponse.setAmount(transaction.getAmount());
            transactionResponse.setCreatedTransaction(transaction.getCreatedTransaction());
            listTransactionResponseDTO.add(transactionResponse);
        }
        listTransactionResponseDTO = listTransactionResponseDTO.stream()
                .sorted(Comparator.comparing(TransactionResponse::getCreatedTransaction).reversed())
                .collect(Collectors.toList());

        return listTransactionResponseDTO;
    }

    public List<TransactionResponse> allTransaction() {
        List<TransactionResponse> listTransactionResponseDTO = new ArrayList<>();
        List<Transaction> transactions = transactionRepository.findAll();
        for (Transaction transaction : transactions) {
            TransactionResponse transactionResponse = new TransactionResponse();
            transactionResponse.setTransactionId(transaction.getId());
            transactionResponse.setTransactionEnum(transaction.getTransactionEnum());
            transactionResponse.setAmount(transaction.getAmount());
            transactionResponse.setCreatedTransaction(transaction.getCreatedTransaction());


            listTransactionResponseDTO.add(transactionResponse);
        }
        listTransactionResponseDTO = listTransactionResponseDTO.stream()
                .sorted(Comparator.comparing(TransactionResponse::getCreatedTransaction ).reversed())
                .collect(Collectors.toList());

        return listTransactionResponseDTO;
    }





//    public List<TransactionResponse> historyBuyJewelryById() {
//        List<TransactionResponse> listTransactionResponseDTO = new ArrayList<>();
//        Account account = accountUtils.getCurrentAccount();
//        Wallet wallet = walletRepository.findWalletByAccountId(account.getId());
//        List<Transaction> transactions = transactionRepository.findTransactionsByFrom_IdOrTo_Id(wallet.getWalletID());
//        for (Transaction transaction : transactions) {
//            if(transaction.getArtworkID() != null) {
//                TransactionResponse transactionResponse = new TransactionResponse();
//                transactionResponse.setTransactionID(transaction.getTransactionID());
//                transactionResponse.setTransactionType(transaction.getTransactionType());
//                transactionResponse.setAmount(transaction.getAmount());
//                transactionResponse.setDescription(transaction.getDescription());
//                transactionResponse.setCreatedTransaction(transaction.getCreatedTransaction());
//                transactionResponse.setArtwork(artworkRepository.findById((long) transaction.getArtworkID()));
//                transactionResponse.setFrom(transaction.getFrom());
//                transactionResponse.setTo(transaction.getTo());
//
//                listTransactionResponseDTO.add(transactionResponse);
//            }
//        }
//        listTransactionResponseDTO = listTransactionResponseDTO.stream()
//                .sorted(Comparator.comparing(TransactionResponse::getCreatedTransaction.reversed())
//                .collect(Collectors.toList());
//
//        return listTransactionResponseDTO;
//    }
}
