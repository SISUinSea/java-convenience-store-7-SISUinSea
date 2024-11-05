package store.model;

import java.util.List;
import java.util.stream.Collectors;

public class ProductTable {
    List<Product> table;

    public ProductTable(List<Product> table) {
        this.table = table;
    }

    @Override
    public String toString() {
        return table.stream().map(Product::toString).collect(Collectors.joining("\n"));
    }
}
