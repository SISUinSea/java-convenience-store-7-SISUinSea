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

import static store.service.PromotionCalculator.adjustQuantity;
import static store.service.PromotionCalculator.getPromotionQuantity;
import static store.model.Product.ProductTable.update;
import static store.model.Product.ProductTable.getProductPriceByName;
import static store.model.Product.ProductTable.getPromotionByProductName;

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

    
}
