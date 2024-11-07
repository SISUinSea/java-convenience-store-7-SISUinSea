package store.model.Product;

import static store.utils.Parser.parsePromotionName;

import java.util.List;

public class ProductFactory {
    public static ProductTable createProductTable(List<String> productTableData) {
        List<Product> table = productTableData.stream().map(ProductFactory::createSingleProduct).toList();
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

    public static Product createSingleTransactionProduct(String name, Integer totalQuantity, Integer promotionQuantity,
            ProductTable productTable) {
        if (promotionQuantity == 0 || promotionQuantity == null) {
            return new Product(name, productTable.getProductPriceByName(name), totalQuantity, null);
        }

        return new Product(name, productTable.getProductPriceByName(name), totalQuantity,
                productTable.getPromotionByName(name).getName());
    }
}
