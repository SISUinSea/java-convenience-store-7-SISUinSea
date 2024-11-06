package store.model;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class ProductTable {
    List<Product> table;

    public ProductTable(List<Product> table) {
        this.table = table;
    }

    public void validateRequests(List<PurchaseRequest> purchaseRequests) {
        for (PurchaseRequest purchaseRequest: purchaseRequests) {
            if (!hasSuchProduct(purchaseRequest.getProductName()) 
                || !hasEnoughQuantity(purchaseRequest.getProductName(), purchaseRequest.getQuantity())) {
                throw new IllegalArgumentException();
            }
        }
    }

    public boolean hasSuchProduct(String productName) {
        return table.stream().map(Product::getName).anyMatch(name -> name.equals(productName));
    }

    public boolean hasEnoughQuantity(String productName, Integer requestQuantity) {
        Integer totalQuantity = table.stream().mapToInt(Product::getQuantity).sum();
        if (totalQuantity >= requestQuantity) {
            return true;
        }
        return false;
    }

    public boolean hasPromotion(String productName, LocalDateTime time) {
        List<Product> products = table.stream().filter(product -> product.getName().equals(productName) && product.getPromotion() != null).toList();
        List<Product> currentPromotionProduct = products.stream().filter(product -> product.getPromotion().isPromotioning(time)).toList();
        return !currentPromotionProduct.isEmpty();
    }

    public boolean hasEnoughPromotionQuantity(String productName, Integer requestQuantity) {
        List<Product> promotionProducts = table.stream().filter(product -> product.getName().equals(productName) && product.getPromotion() != null).toList();
        Integer promotionQuantity = promotionProducts.stream().mapToInt(Product::getQuantity).sum();
        if (promotionQuantity >= requestQuantity) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return table.stream().map(Product::toString).collect(Collectors.joining("\n"));
    }
}
