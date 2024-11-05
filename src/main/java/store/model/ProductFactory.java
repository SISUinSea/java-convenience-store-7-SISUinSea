package store.model;

import static store.utils.Parser.parsePromotionName;

import java.util.List;

public class ProductFactory {
    public static ProductTable createProductTable(List<String> productTableData) {
        List<Product> table = productTableData.stream().map(ProductFactory::createSingleProduct).toList();
        return new ProductTable(table);
    }

    public static Product createSingleProduct(String productData) {
        String[] parsedProductData = productData.split(",");
        String name = parsedProductData[0];
        Integer price = Integer.parseInt(parsedProductData[1]);
        Integer quantity = Integer.parseInt(parsedProductData[2]);
        String promotion = parsePromotionName(parsedProductData[3]);
        return new Product(name, price, quantity, promotion);
    }
}
