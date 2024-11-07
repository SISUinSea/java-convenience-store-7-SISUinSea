package store.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import store.model.Product.ProductTable;
import store.model.Promotion.PromotionTable;
import store.model.Transaction.Transaction;
import store.model.PurchaseRequest.PurchaseRequest;
import store.model.PurchaseRequest.PurchaseRequests;

import static camp.nextstep.edu.missionutils.Console.readLine;
import static camp.nextstep.edu.missionutils.DateTimes.now;
import static store.model.Product.ProductFactory.createProductTable;
import static store.model.Promotion.PromotionFactory.createPromotionTable;
import static store.model.PurchaseRequest.PurchaseRequestFactory.createPurchaseRequests;
import static store.utils.Parser.removeHeader;
import static store.view.OutputView.printProductTable;

public class MainController {
    public static void run() throws IOException {
        ProductTable productTable = bootProductTable();
        printProductTable(productTable);

        String purchaseLine = askPurchaseRequest();
        PurchaseRequests purchaseRequests = createPurchaseRequests(purchaseLine);
        productTable.validateRequests(purchaseRequests.getRequests());

        
        for (PurchaseRequest request : purchaseRequests.getRequests()) {
            // 프로모션이 아닌 경우
            if (!productTable.hasPromotion(request.getProductName(), now())) {
                Transaction purchasedRequest = new Transaction()
            }
            // 프로모션인 경우
            if (productTable.hasEnoughPromotionQuantity(request.getProductName(), request.getQuantity())) {
                // TODO. check promotion optimization
            }

        }
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
