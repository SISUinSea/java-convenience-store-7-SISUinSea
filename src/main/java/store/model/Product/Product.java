package store.model.Product;

import static store.model.Promotion.PromotionTable.getPromotionByName;

import java.text.NumberFormat;

import store.model.Promotion.Promotion;

public class Product {
    final String name;
    final Integer price;
    Integer quantity;
    Promotion promotion;

    public Product(final String name, final Integer price, final Integer quantity, final String promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = getPromotionByName(promotion);
    }

    public Product(final String name, final Integer price, final Integer quantity, final Promotion promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public boolean isEmpty() {
        if (quantity == 0) {
            return true;
        }
        return false;
    }

    public void decreaseQuantityByOne() {
        if (isEmpty()) {
            // throw new IllegalStateException();
            return;
        }
        quantity--;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public boolean hasPromotion() {
        if (promotion == null) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        // - 콜라 1,000원 10개 탄산2+1
        return "- " + name + " " + formattedPrice() + " " + formattedQuantity() + " " + formattedPromotion();
    }

    private String formattedPrice() {
        return NumberFormat.getInstance().format(price) + "원";
    }

    private String formattedQuantity() {
        if (quantity == 0) {
            return "재고 없음";
        }
        return String.valueOf(quantity) + "개";
    }

    private String formattedPromotion() {
        if (promotion == null) {
            return "";
        }
        return promotion.getName();
    }
}
