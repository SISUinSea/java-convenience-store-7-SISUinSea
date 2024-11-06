package store.model.Promotion;

import java.time.LocalDate;
import java.util.List;

public class PromotionFactory {
    public static PromotionTable createPromotionTable(List<String> promotionTableData) {
        List<Promotion> promotions = promotionTableData.stream().map(PromotionFactory::createSinglePromotion).toList();
        return new PromotionTable(promotions);
    }

    private static Promotion createSinglePromotion(String promotionData) {
        String[] parsedPromotionData = promotionData.split(",");
        String name = parsedPromotionData[0];
        Integer buy = Integer.parseInt(parsedPromotionData[1]);
        Integer get = Integer.parseInt(parsedPromotionData[2]);
        LocalDate startDate = LocalDate.parse(parsedPromotionData[3]);
        LocalDate endDate = LocalDate.parse(parsedPromotionData[4]);
        return new Promotion(name, buy, get, startDate, endDate);
    }
}
