package com.odp.walled.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.odp.walled.dto.common.GraphSummaryResponse;
import com.odp.walled.mapper.TransactionMapper;
import com.odp.walled.model.Transaction;
import com.odp.walled.model.Transaction.TransactionType;
import com.odp.walled.repository.TransactionRepository;
import com.odp.walled.repository.WalletRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GraphService {
    private final TransactionRepository transactionRepository;

    public List<GraphSummaryResponse> getSummaryByWallet(Long walletId, String period) {
        LocalDateTime fromDate = null;

        if ("weekly".equalsIgnoreCase(period)) {
            fromDate = LocalDateTime.now().minusWeeks(1);
        } else if ("monthly".equalsIgnoreCase(period)) {
            fromDate = LocalDateTime.now().minusMonths(1);
        } else if ("quarterly".equalsIgnoreCase(period)) {
            fromDate = LocalDateTime.now().minusMonths(3);
        }

        List<Transaction> transactions = (fromDate != null)
                ? transactionRepository.findByWalletIdAndTransactionDateAfter(walletId, fromDate)
                : transactionRepository.findAllByWalletIdOrRecipientWalletId(walletId);

        BigDecimal income = transactions.stream()
                .filter(t ->
                        (t.getTransactionType() == TransactionType.TOP_UP &&
                         t.getWallet().getId().equals(walletId)) ||
                        (t.getTransactionType() == TransactionType.TRANSFER &&
                         t.getRecipientWallet() != null &&
                         t.getRecipientWallet().getId().equals(walletId))
                )
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal expense = transactions.stream()
                .filter(t ->
                        t.getTransactionType() == TransactionType.TRANSFER &&
                        t.getWallet().getId().equals(walletId) &&
                        t.getRecipientWallet() != null &&
                        !t.getRecipientWallet().getId().equals(walletId)
                )
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal savingPercentage = BigDecimal.ZERO;
        if (income.compareTo(BigDecimal.ZERO) > 0) {
            savingPercentage = income.subtract(expense)
                    .divide(income, 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
        }

        GraphSummaryResponse response = new GraphSummaryResponse();
        response.setWalletId(walletId);
        response.setPeriod(period != null ? period.toLowerCase() : "all");
        response.setIncome(income);
        response.setExpense(expense);
        response.setSavingPercentage(savingPercentage);

        return List.of(response);

    }

}
