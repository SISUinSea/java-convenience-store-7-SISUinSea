package store.model.Transaction;

import store.model.Product.Product;
import store.model.Promotion.Promotion;

public class TransactionFactory {
    public static Transaction createTransaction(String name, Integer price, Integer quantity, Integer promotionQuantity,
            Promotion promotion) {
        if (promotionQuantity > quantity) {
            quantity = promotionQuantity;
        }
        return new Transaction(new Product(name, price, quantity, promotion), promotionQuantity);
    }
}
