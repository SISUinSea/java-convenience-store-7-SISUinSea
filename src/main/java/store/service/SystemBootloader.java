package store.service;

import static store.model.Product.ProductFactory.createProductTable;
import static store.utils.Parser.removeHeader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import store.model.Product.ProductTable;

public class SystemBootloader {
    public static ProductTable bootProductTable() throws IOException {
        List<String> productLines = Files.readAllLines(Paths.get("./src/main/resources/products.md"));
        List<String> trimmedProductLines = removeHeader(productLines);

        return createProductTable(trimmedProductLines);
    }

}
