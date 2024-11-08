package store.controller;

import static store.model.Product.ProductFactory.createProductTable;
import static store.model.Promotion.PromotionFactory.createPromotionTable;
import static store.utils.Parser.removeHeader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import store.model.Product.ProductTable;
import store.model.Promotion.PromotionTable;

public class SystemBootloader {
    public static ProductTable bootProductTable() throws IOException {
        bootPromotionTable();
        List<String> productLines = Files.readAllLines(Paths.get("./src/main/resources/products.md"));
        List<String> trimmedProductLines = removeHeader(productLines);

        return createProductTable(trimmedProductLines);
    }

    private static PromotionTable bootPromotionTable() throws IOException {
        List<String> promotionLines = Files.readAllLines(Paths.get("./src/main/resources/promotions.md"));
        List<String> trimmedPromotionLines = removeHeader(promotionLines);
        return createPromotionTable(trimmedPromotionLines);
    }
}
