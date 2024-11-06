package store.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import store.model.Product.ProductTable;
import store.model.Promotion.PromotionTable;
import store.model.PurchaseRequest.PurchaseRequests;

import static camp.nextstep.edu.missionutils.Console.readLine;
import static store.model.Product.ProductFactory.createProductTable;
import static store.model.Promotion.PromotionFactory.createPromotionTable;
import static store.model.PurchaseRequest.PurchaseRequestFactory.createPurchaseRequestQueue;
import static store.utils.Parser.removeHeader;
import static store.view.OutputView.printProductTable;

public class MainController {
    public static void run() throws IOException {
        ProductTable productTable = bootProductTable();
        printProductTable(productTable);

        String purchaseLine = askPurchaseRequest();
        PurchaseRequests purchaseRequests = createPurchaseRequestQueue(purchaseLine);
        productTable.validateRequests(purchaseRequests.getRequests());
        
        
    }

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
