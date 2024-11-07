package store.model.Transaction;

import store.model.Product.Product;
import store.model.Transaction.Transaction;

public class TransactionFactory {
    public Transaction createSinglePurchase(Product product, Integer promotionQuantity) {
        return new Transaction(product, promotionQuantity);
    }
}
