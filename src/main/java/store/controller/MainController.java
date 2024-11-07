package store.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import camp.nextstep.edu.missionutils.Console;
import store.model.Product.Product;
import store.model.Product.ProductTable;
import store.model.Promotion.Promotion;
import store.model.Promotion.PromotionTable;
import store.model.Transaction.Transaction;
import store.model.Transaction.TransactionTable;
import store.model.PurchaseRequest.PurchaseRequest;
import store.model.PurchaseRequest.PurchaseRequests;

import static camp.nextstep.edu.missionutils.DateTimes.now;
import static store.model.Product.ProductFactory.createProductTable;
import static store.model.Product.ProductFactory.createSingleTransactionProduct;
import static store.model.Promotion.PromotionFactory.createPromotionTable;
import static store.model.PurchaseRequest.PurchaseRequestFactory.createPurchaseRequests;
import static store.model.Transaction.TransactionFactory.createSingleTransaction;
import static store.utils.Parser.removeHeader;
import static store.view.OutputView.printProductTable;
import static store.view.InputView.askPurchaseRequest;
import static store.view.InputView.askAddFreePromotionProducts;
import static store.view.InputView.askRemoveNoPromotionProducts;

public class MainController {
    public static void run() throws IOException {
        ProductTable productTable = bootProductTable();
        printProductTable(productTable);

        String purchaseLine = askPurchaseRequest();
        PurchaseRequests purchaseRequests = createPurchaseRequests(purchaseLine);
        productTable.validateRequests(purchaseRequests.getRequests());
        List<Transaction> transactions = new ArrayList<>();
        for (PurchaseRequest request : purchaseRequests.getRequests()) {
            // 프로모션이 아닌 경우
            if (!productTable.hasPromotion(request.getProductName(), now())) {
                Integer price = productTable.getProductPriceByName(request.getProductName());
                // TODO. refactor to ProductFactory
                Product transactionProduct = new Product(
                        request.getProductName(),
                        price,
                        request.getQuantity(),
                        null);
                Transaction purchasedRequest = createSingleTransaction(transactionProduct);
                // TODO. refactor to TransactionFactory
                productTable.update(transactionProduct);
                transactions.add(purchasedRequest);
            }
            // 프로모션이고 프로모션 재고가 충분한 경우
            else if (productTable.hasEnoughPromotionQuantity(request.getProductName(), request.getQuantity())) {
                Integer optimalQuantity = productTable.getOptimalQuantity(request.getProductName(),
                        request.getQuantity());
                if (optimalQuantity > 0) {
                    // 최적의 값을 요청한게 아닌 경우 최적값으로 바꿀지 제안
                    String answer = askAddFreePromotionProducts(request.getProductName(), optimalQuantity);
                    if (answer.equals("Y")) {
                        Product transactionProduct = createSingleTransactionProduct(
                                request.getProductName(),
                                request.getQuantity() + optimalQuantity,
                                optimalQuantity,
                                productTable);
                        productTable.update(transactionProduct);
                        transactions.add(
                                createSingleTransaction(transactionProduct,
                                        optimalQuantity + request.getQuantity()));
                    }
                }
            } else if (!productTable.hasEnoughPromotionQuantity(request.getProductName(), request.getQuantity())) {
                String answer = askRemoveNoPromotionProducts(request.getProductName(),
                        request.getQuantity() - productTable.getPromotionBundleCount(request.getProductName()));
                if (answer.equals("Y")) {
                    Product transactionProduct = createSingleTransactionProduct(request.getProductName(),
                            productTable.getPromotionBundleCount(request.getProductName()),
                            productTable.getPromotionBundleCount(request.getProductName()), productTable);
                    productTable.update(transactionProduct);
                    // transactions.add()
                } else if (answer.equals("N")) {
                    Product transactionProduct = createSingleTransactionProduct(request.getProductName(),
                            request.getQuantity(),
                            productTable.getPromotionBundleCount(request.getProductName()), productTable);
                    productTable.update(transactionProduct);
                }
            }
            TransactionTable transactionTable = new TransactionTable(transactions);

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

}
