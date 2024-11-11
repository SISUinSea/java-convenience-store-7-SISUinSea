package store.service;

import static store.model.Product.ProductTable.getPromotionBundleCount;
import static store.model.Product.ProductTable.hasEnoughPromotionQuantity;
import static store.view.InputView.askAddFreePromotionProducts;
import static store.view.InputView.askRemoveNoPromotionProducts;

import store.model.Promotion.Promotion;
import store.model.PurchaseRequest.PurchaseRequest;

public class PromotionCalculator {
    public static Integer adjustQuantity(
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

    public static Integer getPromotionQuantity(
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
