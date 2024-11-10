package store.service;

import static store.model.Transaction.TransactionFactory.createTransaction;
import static store.view.InputView.askAddFreePromotionProducts;
import static store.view.InputView.askRemoveNoPromotionProducts;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import store.model.Promotion.Promotion;
import store.model.PurchaseRequest.PurchaseRequest;
import store.model.PurchaseRequest.PurchaseRequests;
import store.model.Transaction.Transaction;
import store.model.Transaction.TransactionTable;

import static store.model.Product.ProductTable.update;
import static store.model.Product.ProductTable.getProductPriceByName;
import static store.model.Product.ProductTable.getPromotionByProductName;
import static store.model.Product.ProductTable.hasEnoughPromotionQuantity;
import static store.model.Product.ProductTable.getPromotionBundleCount;

public class RequestProcessor {
    public static TransactionTable processRequests(
            final PurchaseRequests purchaseRequests,
            final LocalDateTime time) {
        List<Transaction> transactions = new ArrayList<>();
        for (PurchaseRequest request : purchaseRequests.getRequests()) {
            Transaction transaction = processRequest(request, time);
            transactions.add(transaction);
            update(transaction);
        }
        return new TransactionTable(transactions);
    }

    private static Transaction processRequest(
            final PurchaseRequest request,
            final LocalDateTime time) {
        String name = request.getProductName();
        Integer price = getProductPriceByName(request.getProductName());
        Promotion promotion = getPromotionByProductName(name, time);
        Integer quantity = adjustQuantity(request, promotion);
        Integer promotionQuantity = getPromotionQuantity(name, quantity, promotion);
        return createTransaction(name, price, quantity, promotionQuantity, promotion);
    }

    private static Integer adjustQuantity(
            final PurchaseRequest request,
            final Promotion promotion) {
        if (promotion == null) {
            return request.getQuantity();
        }
        if (hasEnoughPromotionQuantity(request.getProductName(), request.getQuantity())) {
            return calculateAdditionalOptimalValue(request.getProductName(), request.getQuantity(), promotion);
        }
        return suggestDecreaseQuantity(request.getProductName(), request.getQuantity(), promotion);
    }

    private static Integer calculateAdditionalOptimalValue(
            final String name,
            final Integer quantity,
            final Promotion promotion) {
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

    private static Integer suggestDecreaseQuantity(
            final String name,
            final Integer quantity,
            final Promotion promotion) {
        Integer surpulsQuantity = quantity - getPromotionBundleCount(name);
        String answer = askRemoveNoPromotionProducts(name, surpulsQuantity);
        if (answer.equals("Y")) {
            return quantity;
        }
        return quantity - surpulsQuantity;
    }

    private static Integer getPromotionQuantity(
            final String name,
            final Integer quantity,
            final Promotion promotion) {
        if (promotion == null) {
            return 0;
        }
        Integer bundle = promotion.getBundle();
        Integer quantityBundleCount = quantity - (quantity % bundle);
        Integer promotionBundleCount = getPromotionBundleCount(name);
        return Math.min(quantityBundleCount, promotionBundleCount);
    }
}
