package store.model.Transaction;

import store.model.Promotion.Promotion;

public class TransactionFactory {
    public static Transaction createTransaction(
            final String name,
            final Integer price,
            Integer quantity,
            final Integer promotionQuantity,
            Promotion promotion) {
        if (promotionQuantity > quantity) {
            quantity = promotionQuantity;
        }
        return new Transaction(name, price, quantity, promotionQuantity, promotion);
    }
}
