package store.model.Product;

import static store.utils.Parser.parsePromotionName;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProductFactory {
    public static ProductTable createProductTable(List<String> productTableData) {
        List<Product> table = productTableData.stream().map(ProductFactory::createSingleProduct).toList();
        // List<Product> filledTable = fillGeneralProductIfAbsent(table);
        return new ProductTable(table);
    }

    private static Product createSingleProduct(String productData) {
        String[] parsedProductData = productData.split(",");
        String name = parsedProductData[0];
        Integer price = Integer.parseInt(parsedProductData[1]);
        Integer quantity = Integer.parseInt(parsedProductData[2]);
        String promotion = parsePromotionName(parsedProductData[3]);
        return new Product(name, price, quantity, promotion);
    }

    private static List<Product> fillGeneralProductIfAbsent(List<Product> table) {
        List<Product> filledTable = new ArrayList<>();
        for (Product product : table) {
            filledTable.add(product);
            if (!hasDoubleProducts(product.getName(), table)) {
                filledTable.add(createEmptyProduct(product.getName(), product.getPrice()));
            }
        }
        return filledTable;
    }

    private static Product createEmptyProduct(String name, Integer price) {
        return new Product(name, price, 0, "null");
    }

    private static boolean hasDoubleProducts(String productName, List<Product> table) {
        if (table.stream().filter(product -> product.getName().equals(productName)).count() == 2) {
            return true;
        }
        return false;
    }

    public static Product createSingleTransactionProduct(String name, Integer totalQuantity,
            Integer promotionQuantity,
            ProductTable productTable, LocalDateTime time) {
        if (promotionQuantity == 0 || promotionQuantity == null) {
            return new Product(name, productTable.getProductPriceByName(name), totalQuantity, "null");
        }

        return new Product(name, productTable.getProductPriceByName(name), totalQuantity,
                productTable.getPromotionByProductName(name, time).getName());
    }
}
