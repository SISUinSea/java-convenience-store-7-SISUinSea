package store.model.Product;

import static store.utils.Parser.parsePromotionName;
import static store.utils.Parser.removeHeader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static store.model.Product.ProductTable.getProductPriceByName;
import static store.model.Product.ProductTable.getPromotionByProductName;

public class ProductFactory {
    public static List<Product> bootProductTable() {
        try {
            List<String> productLines = Files.readAllLines(Paths.get("./src/main/resources/products.md"));
            List<String> trimmedProductLines = removeHeader(productLines);

            return createProductTable(trimmedProductLines);
        } catch (IOException e) {
            throw new IllegalStateException();
        }
    }

    private static List<Product> createProductTable(List<String> productTableData) {
        List<Product> table = productTableData.stream().map(ProductFactory::createSingleProduct).toList();
        // List<Product> filledTable = fillGeneralProductIfAbsent(table);
        return table;
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
            Integer promotionQuantity, LocalDateTime time) {
        if (promotionQuantity == 0 || promotionQuantity == null) {
            return new Product(name, getProductPriceByName(name), totalQuantity, "null");
        }

        return new Product(name, getProductPriceByName(name), totalQuantity,
                getPromotionByProductName(name, time).getName());
    }
}
