package store.controller;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import store.model.ProductTable;
import store.model.PromotionTable;

import static store.model.ProductFactory.createProductTable;
import static store.model.PromotionFactory.createPromotionTable;
import static store.utils.Parser.removeHeader;
import static store.view.OutputView.printProductTable;

public class MainController {
    public static void run() throws IOException {
        List<String> productLines = Files.readAllLines(Paths.get("./src/main/resources/products.md"));
        List<String> trimmedProductLines = removeHeader(productLines);
        ProductTable productTable = createProductTable(trimmedProductLines);
        printProductTable(productTable);
        List<String> promotionLines = Files.readAllLines(Paths.get("./src/main/resources/promotions.md"));
        List<String> trimmedPromotionLines = removeHeader(promotionLines);
        PromotionTable promotionTable = createPromotionTable(trimmedPromotionLines);
    }
}
