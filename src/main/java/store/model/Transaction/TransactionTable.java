package store.model.Transaction;

import java.util.List;

public class TransactionTable {
    private List<Transaction> purchases;

    public TransactionTable(final List<Transaction> purchases) {
        this.purchases = purchases;
    }

    public List<Transaction> getTransactions() {
        return purchases;
    }
}
