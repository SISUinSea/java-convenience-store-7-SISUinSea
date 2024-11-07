package store.model.Transaction;

import store.model.Product.Product;
import store.model.Transaction.Transaction;

public class TransactionFactory {
    public static Transaction createSingleTransaction(Product product, Integer promotionQuantity) {
        return new Transaction(product, promotionQuantity);
    }

    public static Transaction createSingleTransaction(Product product) {
        return new Transaction(product, null);
    }

}
