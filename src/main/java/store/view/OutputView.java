package store.view;

import store.model.ProductTable;

public class OutputView {
    public static void printProductTable(ProductTable productTable) {
        printProductTableHeader();
        System.out.println(productTable.toString());
        System.out.println("\n");
    }

    private static void printProductTableHeader() {
        System.out.println("안녕하세요. W편의점입니다.");
        System.out.println("현재 보유하고 있는 상품입니다.");
        System.out.print("\n");
    }
}
