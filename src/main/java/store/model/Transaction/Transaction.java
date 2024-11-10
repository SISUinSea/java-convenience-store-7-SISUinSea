package store.model.Transaction;

import java.text.NumberFormat;

import store.model.Product.Product;
import store.model.Promotion.Promotion;

public class Transaction extends Product {
    private final Integer promotionQuantity;

    public Transaction(
            final String name,
            final Integer price,
            final Integer quantity,
            final Integer promotionQuantity,
            final Promotion promotion) {
        super(name, price, quantity, promotion);
        this.promotionQuantity = promotionQuantity;

    }

    public Integer getTotalCost() {
        return getPrice() * getQuantity();
    }

    public Integer getPromotionDiscountCost() {
        if (!hasPromotion()) {
            return 0;
        }
        return getPromotion().getFreeQuantity(promotionQuantity) * getPrice();
    }

    @Override
    public String toString() {
        return String.format("%-10s%-10d%s", getName(), getQuantity(), formattedTotalPrice());
    }

    public String promotionToString() {
        if (!hasPromotion() || getQuantity() == 0) {
            return null;
        }
        Integer get = getPromotion().getGet();
        Integer bundle = getPromotion().getBundle();
        return String.format("%-10s%-10d", getName(), (promotionQuantity / bundle) * get); // TODO. refactor
    }

    public String formattedTotalPrice() {
        return NumberFormat.getInstance().format(getPrice() * getQuantity());
    }
}
