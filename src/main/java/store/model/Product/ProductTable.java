package store.model.Product;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import store.model.Promotion.Promotion;
import store.model.PurchaseRequest.PurchaseRequest;
import store.model.Transaction.Transaction;
import store.utils.ErrorMessage;

import static store.model.Product.ProductFactory.bootProductTable;

public class ProductTable {
    private static List<Product> table = bootProductTable();

    public ProductTable() {
        this.table = bootProductTable();
    }

    public static void checkRequestValidity(final List<PurchaseRequest> purchaseRequests) {
        for (PurchaseRequest purchaseRequest : purchaseRequests) {
            if (!hasSuchProduct(purchaseRequest.getProductName())) {
                throw new IllegalArgumentException(ErrorMessage.PRODUCT_NOT_EXIST.getDescription());
            }
            if (!hasEnoughQuantity(purchaseRequest.getProductName(), purchaseRequest.getQuantity())) {
                throw new IllegalArgumentException(ErrorMessage.QUANTITY_NOT_ENOUGH.getDescription());
            }
        }
    }

    public static boolean hasSuchProduct(final String productName) {
        return table.stream().map(Product::getName).anyMatch(name -> name.equals(productName));
    }

    public static boolean hasEnoughQuantity(final String productName, final Integer requestQuantity) {
        Integer totalQuantity = table.stream().filter(product -> product.getName().equals(productName))
                .mapToInt(Product::getQuantity).sum();
        if (totalQuantity >= requestQuantity) {
            return true;
        }
        return false;
    }

    public static Integer getAdditionalQuantityToOptimize(final String productName, final Integer requestQuantity) {
        List<Product> targetProduct = getPromotionProducts(productName);
        Promotion targetPromotion = targetProduct.get(0).getPromotion();
        Integer buy = targetPromotion.getBuy();
        Integer get = targetPromotion.getGet();
        if (requestQuantity % (buy + get) == buy) {
            return get;
        }
        return 0;
    }

    public static void update(final Transaction transaction) {
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

    public static boolean hasPromotion(final String productName, final LocalDateTime time) {
        List<Product> products = table.stream()
                .filter(product -> product.getName().equals(productName) && product.getPromotion() != null).toList();
        List<Product> currentPromotionProduct = products.stream()
                .filter(product -> product.getPromotion().isPromotioning(time)).toList();
        return !currentPromotionProduct.isEmpty();
    }

    public static List<Product> getPromotionProducts(final String productName) {
        List<Product> products = table.stream()
                .filter(product -> product.getName().equals(productName) && product.getPromotion() != null).toList();
        return products;
    }

    public static boolean hasEnoughPromotionQuantity(final String productName, final Integer requestQuantity) {
        if (requestQuantity > getPromotionBundleCount(productName)) {
            return false;
        }
        return true;
    }

    public static Integer getPromotionBundleCount(final String productName) {
        Product targetProduct = getPromotionProducts(productName).get(0);
        Integer buy = targetProduct.getPromotion().getBuy();
        Integer get = targetProduct.getPromotion().getGet();
        return targetProduct.getQuantity() - (targetProduct.getQuantity() % (buy + get));
    }

    public static List<Product> getProductsByProductName(final String productName) {
        return table.stream().filter(product -> product.getName().equals(productName)).toList();
    }

    public static Integer getProductPriceByName(final String productName) {
        List<Product> products = table.stream().filter(product -> product.getName().equals(productName)).toList();
        return products.get(0).getPrice();
    }

    public static Promotion getPromotionByProductName(final String productName, final LocalDateTime time) {
        if (!hasPromotion(productName, time)) {
            return null;
        }
        return getPromotionProducts(productName).get(0).getPromotion();
    }

    public static String productTableToString() {
        return table.stream().map(Product::toString).collect(Collectors.joining("\n"));
    }
}
