package store.model.Promotion;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import store.utils.ErrorMessage;

import static store.utils.Parser.removeHeader;

public class PromotionFactory {
    public static List<Promotion> bootPromotionTable() {
        try {
            List<String> promotionLines = Files.readAllLines(Paths.get("./src/main/resources/promotions.md"));
            List<String> trimmedPromotionLines = removeHeader(promotionLines);
            return createPromotionTable(trimmedPromotionLines);
        } catch (IOException e) {
            throw new IllegalStateException(ErrorMessage.ILLEGAL_BOOT_SYSTEM.getDescription());
        }
    }

    private static List<Promotion> createPromotionTable(final List<String> promotionTableData) {
        List<Promotion> promotions = promotionTableData.stream()
                .map(PromotionFactory::createSinglePromotion).toList();
        return promotions;
    }

    private static Promotion createSinglePromotion(final String promotionData) {
        String[] parsedPromotionData = promotionData.split(",");
        String name = parsedPromotionData[0];
        Integer buy = Integer.parseInt(parsedPromotionData[1]);
        Integer get = Integer.parseInt(parsedPromotionData[2]);
        LocalDate startDate = LocalDate.parse(parsedPromotionData[3]);
        LocalDate endDate = LocalDate.parse(parsedPromotionData[4]);
        return new Promotion(name, buy, get, startDate, endDate);
    }
}
