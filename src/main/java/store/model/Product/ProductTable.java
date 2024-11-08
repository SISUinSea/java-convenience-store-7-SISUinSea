package store.model.Product;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import store.model.Promotion.Promotion;
import store.model.PurchaseRequest.PurchaseRequest;
import store.model.Transaction.Transaction;
import store.utils.ErrorMessage;

public class ProductTable {
    List<Product> table;

    public ProductTable(List<Product> table) {
        this.table = table;
    }

    public void checkRequestValidity(List<PurchaseRequest> purchaseRequests) {
        for (PurchaseRequest purchaseRequest : purchaseRequests) {
            if(!hasSuchProduct(purchaseRequest.getProductName())) {
                throw new IllegalArgumentException(ErrorMessage.PRODUCT_NOT_EXIST.getDescription());
            }
            if (!hasEnoughQuantity(purchaseRequest.getProductName(), purchaseRequest.getQuantity())) {
                throw new IllegalArgumentException(ErrorMessage.QUANTITY_NOT_ENOUGH.getDescription());
            }
        }
    }

    public boolean hasSuchProduct(String productName) {
        return table.stream().map(Product::getName).anyMatch(name -> name.equals(productName));
    }

    public boolean hasEnoughQuantity(String productName, Integer requestQuantity) {
        Integer totalQuantity = table.stream().filter(product -> product.getName().equals(productName))
                .mapToInt(Product::getQuantity).sum();
        if (totalQuantity >= requestQuantity) {
            return true;
        }
        return false;
    }

    public Integer getAdditionalQuantityToOptimize(String productName, Integer requestQuantity) {
        List<Product> targetProduct = getPromotionProducts(productName);
        Promotion targetPromotion = targetProduct.get(0).getPromotion();
        Integer buy = targetPromotion.getBuy();
        Integer get = targetPromotion.getGet();
        if (requestQuantity % (buy + get) == buy) {
            return get;
        }
        return 0;
    }

    public void update(Transaction transaction) {
        List<Product> targetProducts = table.stream()
                .filter(p -> p.getName().equals(transaction.getName())).toList();
        Integer totalPurchaseQuantity = 0;
        for (Product product : targetProducts) {
            while (!product.isEmpty() && totalPurchaseQuantity < transaction.getQuantity()) {
                product.decreaseQuantityByOne();
                totalPurchaseQuantity++;
            }
        }
    }

    public boolean hasPromotion(String productName, LocalDateTime time) {
        List<Product> products = table.stream()
                .filter(product -> product.getName().equals(productName) && product.getPromotion() != null).toList();
        List<Product> currentPromotionProduct = products.stream()
                .filter(product -> product.getPromotion().isPromotioning(time)).toList();
        return !currentPromotionProduct.isEmpty();
    }

    public List<Product> getPromotionProducts(String productName) {
        List<Product> products = table.stream()
                .filter(product -> product.getName().equals(productName) && product.getPromotion() != null).toList();
        return products;
    }

    public boolean hasEnoughPromotionQuantity(String productName, Integer requestQuantity) {
        if (requestQuantity > getPromotionBundleCount(productName)) {
            return false;
        }
        return true;
    }

    public Integer getPromotionBundleCount(String productName) {
        Product targetProduct = getPromotionProducts(productName).get(0);
        Integer buy = targetProduct.getPromotion().getBuy();
        Integer get = targetProduct.getPromotion().getGet();
        return targetProduct.getQuantity() - (targetProduct.getQuantity() % (buy + get));
    }

    public List<Product> getProductsByProductName(String productName) {
        return table.stream().filter(product -> product.getName().equals(productName)).toList();
    }

    public Integer getProductPriceByName(String productName) {
        List<Product> products = table.stream().filter(product -> product.getName().equals(productName)).toList();
        return products.get(0).getPrice();
    }

    public Promotion getPromotionByProductName(String productName, LocalDateTime time) {
        if (!hasPromotion(productName, time)) {
            return null;
        }
        List<Product> promotionProducts = getPromotionProducts(productName);
        return getPromotionProducts(productName).get(0).getPromotion();
    }

    @Override
    public String toString() {
        return table.stream().map(Product::toString).collect(Collectors.joining("\n"));
    }
}
