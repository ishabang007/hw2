package model;
import java.util.ArrayList;
import java.util.List;

public class AmountFilter implements TransactionFilter {

    private double amount;

    public AmountFilter(double amount) {
        this.amount = amount;
    }

    public List<Transaction> filter(List<Transaction> transactions) {
        List<Transaction> filteredTransactions = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.getAmount() == amount) {
                filteredTransactions.add(transaction);
            }
        }
        return filteredTransactions;
    }
}

