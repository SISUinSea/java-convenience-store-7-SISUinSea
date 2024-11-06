package store.model;

import java.util.List;

public class PromotionTable {
    private static List<Promotion> promotions;

    public PromotionTable(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public static Promotion getPromotionByName(String promotionName) {
        List<Promotion> targetPromotion = promotions.stream().filter(promotion -> promotion.getName().equals(promotionName)).toList();
        if (targetPromotion.isEmpty()) {
            return null;
        }
        return targetPromotion.get(0);
    }
}
