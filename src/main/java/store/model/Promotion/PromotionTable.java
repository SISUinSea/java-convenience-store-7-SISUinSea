package store.model.Promotion;

import java.util.List;

import static store.model.Promotion.PromotionFactory.bootPromotionTable;

public class PromotionTable {
    private static List<Promotion> promotions = bootPromotionTable();

    private PromotionTable() {
    }

    public static Promotion getPromotionByName(final String promotionName) {
        List<Promotion> targetPromotion = promotions.stream()
                .filter(promotion -> promotion.getName().equals(promotionName)).toList();
        if (targetPromotion.isEmpty()) {
            return null;
        }
        return targetPromotion.get(0);
    }
}
