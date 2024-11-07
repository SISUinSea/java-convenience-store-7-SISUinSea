package store.model.Transaction;

import store.model.Product.Product;

public class Transaction{
    private final Product product;
    private final Integer promotionQuantity;

    public Transaction(final Product product, final Integer promotionQuantity) {
        this.product = product;
        this.promotionQuantity = promotionQuantity;
    }


    
    
}
