package store.model.Transaction;

import java.util.List;

public class TransactionTable {
    private List<Transaction> purchases;

    public TransactionTable(List<Transaction> purchases) {
        this.purchases = purchases;
    }
}
