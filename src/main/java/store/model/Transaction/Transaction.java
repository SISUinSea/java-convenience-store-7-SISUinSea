package store.model.Transaction;

import java.text.NumberFormat;

import store.model.Product.Product;

public class Transaction {
    private final Product product;
    private final Integer promotionQuantity;

    public Transaction(final Product product, final Integer promotionQuantity) {
        this.product = product;
        this.promotionQuantity = promotionQuantity;
    }

    public String getName() {
        return product.getName();
    }

    public Integer getTotalCost() {
        return product.getPrice() * product.getQuantity();
    }

    public Integer getQuantity() {
        return product.getQuantity();
    }

    public Integer getPromotionDiscountCost() {
        if (!product.hasPromotion()) {
            return 0;
        }
        return product.getPromotion().getFreeQuantity(promotionQuantity) * product.getPrice();
    }

    @Override
    public String toString() {
        return product.getName() + "\t\t" + product.getQuantity() + "\t" + formattedTotalPrice();
    }

    public String promotionToString() {
        if (!product.hasPromotion()) {
            return null;
        }
        Integer get = product.getPromotion().getGet();
        Integer bundle = product.getPromotion().getBundle();
        return product.getName() + "\t\t" + (promotionQuantity / bundle) * get; // TODO. refactor
    }

    public String formattedTotalPrice() {
        return NumberFormat.getInstance().format(product.getPrice() * product.getQuantity());
    }
}
