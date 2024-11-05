package store.controller;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import store.model.ProductTable;

import static store.model.ProductFactory.createProductTable;
import static store.utils.Parser.trimProductStrings;;

public class MainController {
    public static void run() throws IOException {
        List<String> productLines = Files.readAllLines(Paths.get("./src/main/resources/products.md"));
        List<String> trimedProductLines = trimProductStrings(productLines);
        ProductTable productTable = createProductTable(trimedProductLines);
    }
}
