package store.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import store.model.ProductTable;
import store.model.PromotionTable;
import store.model.PurchaseRequestQueue;

import static camp.nextstep.edu.missionutils.Console.readLine;
import static store.model.ProductFactory.createProductTable;
import static store.model.PromotionFactory.createPromotionTable;
import static store.model.PurchaseRequestFactory.createPurchaseRequestQueue;
import static store.utils.Parser.removeHeader;
import static store.view.OutputView.printProductTable;

public class MainController {
    public static void run() throws IOException {
        ProductTable productTable = bootProductTable();
        printProductTable(productTable);
        PromotionTable promotionTable = bootPromotionTable();
        String purchaseLine = askPurchaseRequest();
        PurchaseRequestQueue purchaseRequestQueue = createPurchaseRequestQueue(purchaseLine);
    }

    public static ProductTable bootProductTable() throws IOException {
        List<String> productLines = Files.readAllLines(Paths.get("./src/main/resources/products.md"));
        List<String> trimmedProductLines = removeHeader(productLines);
        
        return createProductTable(trimmedProductLines);
    }

    public static PromotionTable bootPromotionTable() throws IOException {
        List<String> promotionLines = Files.readAllLines(Paths.get("./src/main/resources/promotions.md"));
        List<String> trimmedPromotionLines = removeHeader(promotionLines);
        return createPromotionTable(trimmedPromotionLines);
    }

    public static String askPurchaseRequest() {
        while (true) {
            try {
                System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
                return readLine();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
