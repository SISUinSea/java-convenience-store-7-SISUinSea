package store.utils;

import java.util.List;

public class Parser {
    public static List<String> removeHeader(List<String> productStrings) {
        productStrings.remove(0);
        return productStrings;
    }

    public static String parsePromotionName(String promotionName) {
        if (promotionName.equals("null")) {
            return null;
        }
        return promotionName;
    }
}
