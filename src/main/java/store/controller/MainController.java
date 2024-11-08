package store.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import store.model.Discount.Discount;
import store.model.Product.Product;
import store.model.Product.ProductTable;
import store.model.Promotion.Promotion;
import store.model.Promotion.PromotionTable;
import store.model.Transaction.Transaction;
import store.model.Transaction.TransactionTable;
import store.model.PurchaseRequest.PurchaseRequest;
import store.model.PurchaseRequest.PurchaseRequests;
import store.model.Receipt.Receipt;

import static camp.nextstep.edu.missionutils.DateTimes.now;
import static store.model.Product.ProductFactory.createProductTable;
import static store.model.Promotion.PromotionFactory.createPromotionTable;
import static store.model.PurchaseRequest.PurchaseRequestFactory.createPurchaseRequests;
import static store.model.Transaction.TransactionFactory.createTransaction;
import static store.model.Discount.DiscountFactory.createMembershipDiscount;
import static store.utils.Parser.removeHeader;
import static store.view.OutputView.printProductTable;
import static store.view.OutputView.printReceipt;
import static store.view.InputView.askAddFreePromotionProducts;
import static store.view.InputView.askApplyMembershipDiscount;
import static store.view.InputView.askRemoveNoPromotionProducts;
import static store.view.InputView.askToContinuePurchase;

public class MainController {
    public static void run() throws IOException {
        ProductTable productTable = bootProductTable();
        purchaseUntilGetStopCommand(productTable);
    }

    public static void purchaseUntilGetStopCommand(ProductTable productTable) {
        while (true) {
            printProductTable(productTable);
            PurchaseRequests purchaseRequests = getPurchaseRequests(productTable);
            TransactionTable transactionTable = processRequests(productTable, purchaseRequests, now());
            Discount membershipDiscount = suggestMembershipDiscount();
            Receipt receipt = new Receipt(transactionTable, membershipDiscount);
            printReceipt(receipt);
            if (askToContinuePurchase().equals("N")) {
                break;
            }
        }
    }

    public static PurchaseRequests getPurchaseRequests(ProductTable productTable) {
        PurchaseRequests purchaseRequests = createPurchaseRequests();
        productTable.checkRequestValidity(purchaseRequests.getRequests());
        return purchaseRequests;
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

    public static TransactionTable processRequests(ProductTable productTable, PurchaseRequests purchaseRequests,
            LocalDateTime time) {
        List<Transaction> transactions = new ArrayList<>();
        for (PurchaseRequest request : purchaseRequests.getRequests()) {
            Transaction transaction = processRequest(productTable, request, time);
            transactions.add(transaction);
            productTable.update(transaction);
        }
        return new TransactionTable(transactions);
    }

    private static Transaction processRequest(ProductTable productTable, PurchaseRequest request, LocalDateTime time) {
        String name = request.getProductName();
        Integer price = productTable.getProductPriceByName(request.getProductName());
        Promotion promotion = productTable.getPromotionByProductName(name, time);
        Integer quantity = adjustQuantity(productTable, request, promotion);
        Integer promotionQuantity = getPromotionQuantity(name, quantity, promotion, productTable);
        return createTransaction(name, price, quantity, promotionQuantity, promotion);
    }

    private static Integer adjustQuantity(ProductTable productTable, PurchaseRequest request, Promotion promotion) {
        if (promotion == null) {
            return request.getQuantity();
        }
        if (productTable.hasEnoughPromotionQuantity(request.getProductName(), request.getQuantity())) {
            return calculateAdditionalOptimalValue(request.getProductName(), request.getQuantity(), promotion);
        }
        return suggestDecreaseQuantity(request.getProductName(), request.getQuantity(), promotion, productTable);
    }

    private static Integer calculateAdditionalOptimalValue(String name, Integer quantity, Promotion promotion) {
        Integer bundle = promotion.getBundle();
        Integer buy = promotion.getBuy();
        Integer get = promotion.getGet();
        if (quantity % bundle == buy) {
            String answer = askAddFreePromotionProducts(name, get);
            if (answer.equals("Y")) {
                return quantity + get;
            }
        }
        return quantity;
    }

    private static Integer suggestDecreaseQuantity(String name, Integer quantity, Promotion promotion,
            ProductTable productTable) {
        Integer surpulsQuantity = quantity - productTable.getPromotionBundleCount(name);
        String answer = askRemoveNoPromotionProducts(name, surpulsQuantity);
        if (answer.equals("Y")) {
            return quantity;
        }
        return quantity - surpulsQuantity;
    }

    private static Integer getPromotionQuantity(String name, Integer quantity, Promotion promotion,
            ProductTable productTable) {
        if (promotion == null) {
            return 0;
        }
        Integer bundle = promotion.getBundle();
        Integer quantityBundleCount = quantity - (quantity % bundle);
        Integer promotionBundleCount = productTable.getPromotionBundleCount(name);
        return Math.min(quantityBundleCount, promotionBundleCount);
    }

    public static Discount suggestMembershipDiscount() {
        String answer = askApplyMembershipDiscount();
        if (answer.equals("Y")) {
            return createMembershipDiscount();
        }
        return null;
    }

}
