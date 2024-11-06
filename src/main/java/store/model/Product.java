package store.model;

import java.text.NumberFormat;

import static store.model.PromotionTable.getPromotionByName;

public class Product {
    private final String name;
    private final Integer price;
    private Integer quantity;
    private final Promotion promotion;

    public Product(final String name, final Integer price, final Integer quantity, final String promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = getPromotionByName(promotion);
    }

     

    public String getName() { return name; }
    public Integer getQuantity() { return quantity; }
    public Promotion getPromotion() { return promotion; }
    
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
