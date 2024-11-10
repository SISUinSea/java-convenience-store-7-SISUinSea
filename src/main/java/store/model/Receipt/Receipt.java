package store.model.Receipt;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import store.model.Discount.Discount;
import store.model.Transaction.Transaction;
import store.model.Transaction.TransactionTable;

public class Receipt {
    private List<String> totalTransactions = new ArrayList<>();
    private List<String> promotionTransactions = new ArrayList<>();
    private Integer totalQuantity = 0;
    private Integer totalPurchaseCost = 0;
    private Integer promotionCost = 0;
    private Integer discountCost = 0;
    private Integer finalPayCost;

    public Receipt(final TransactionTable transactionTable, final Discount discount) {
        transactionTable.getTransactions().stream()
                .forEach(transaction -> recordTransaction(transaction));
        if (discount != null) {
            discountCost = discount.applyDiscount(totalPurchaseCost - promotionCost);
        }
        finalPayCost = totalPurchaseCost - promotionCost - discountCost;
    }

    private void recordTransaction(final Transaction transaction) {
        totalTransactions.add(transaction.toString());
        totalQuantity += transaction.getQuantity();
        promotionTransactions.add(transaction.promotionToString());
        totalPurchaseCost += transaction.getTotalCost();
        promotionCost += transaction.getPromotionDiscountCost();
    }

    public List<String> getTotalTransactions() {
        return totalTransactions;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public List<String> getPromotionTransactions() {
        return promotionTransactions;
    }

    public List<String> getCostInformation() {
        return List.of(NumberFormat.getInstance().format(totalPurchaseCost),
                "-" + NumberFormat.getInstance().format(promotionCost),
                "-" + NumberFormat.getInstance().format(discountCost),
                NumberFormat.getInstance().format(finalPayCost));
    }
}
