package store.view;

import java.util.stream.Collectors;

import store.model.Receipt.Receipt;

import static store.model.Product.ProductTable.productTableToString;

public class OutputView {
    private static final String HELLO = "안녕하세요. W편의점입니다.";
    private static final String CURRENT_STOCK_HEADER = "현재 보유하고 있는 상품입니다.";
    private static final String DIVIDER_RECEIPT_HEADER = "\n==============W 편의점================";
    private static final String RECEIPT_3_ELEMENTS_COLUMN_FORMAT = "%-10s%-10s%s";
    private static final String RECEIPT_2_ELEMENTS_COLUMN_FORMAT = "%-20s%s";
    private static final String PRODUCT_NAME = "상품명";
    private static final String PRICE = "수량";
    private static final String QUANTITY = "금액";
    private static final String DIVIDER_PROMOTION_HEADER = "=============증" + "\t\t" + "정===============";
    private static final String DIVIDER_DEFAULT_LINE = "====================================";
    private static final String TOTAL_PURCHASE_PAYMENT = "총구매액";
    private static final String PROMOTION_DISCOUNT = "행사할인";
    private static final String MEMBERSHIP_DISCOUNT = "멤버십할인";
    private static final String FINAL_PURCHASE_PAYMENT = "내실돈";

    public static void printProductTable() {
        printProductTableHeader();
        System.out.println();
        System.out.println(productTableToString());
    }

    private static void printProductTableHeader() {
        System.out.println(HELLO);
        System.out.println(CURRENT_STOCK_HEADER);
    }

    public static void printReceipt(Receipt receipt) {
        printTotalTransactions(receipt);
        printPromotionTransaction(receipt);
        printCostsStatistic(receipt);
    }

    private static void printTotalTransactions(final Receipt receipt) {
        System.out.println(DIVIDER_RECEIPT_HEADER);
        System.out.println(String.format(RECEIPT_3_ELEMENTS_COLUMN_FORMAT, PRODUCT_NAME, PRICE, QUANTITY));
        System.out.println(receipt.getTotalTransactions().stream().collect(Collectors.joining("\n")));
    }

    private static void printPromotionTransaction(final Receipt receipt) {
        if (receipt.getPromotionTransactions().stream().filter(t -> t != null).count() == 0) {
            return;
        }
        System.out.println(DIVIDER_PROMOTION_HEADER);
        System.out.println(
                receipt.getPromotionTransactions()
                        .stream()
                        .filter(t -> t != null)
                        .collect(Collectors.joining("\n")));
    }

    private static void printCostsStatistic(final Receipt receipt) {
        System.out.println(DIVIDER_DEFAULT_LINE);
        System.out.println(String.format(RECEIPT_3_ELEMENTS_COLUMN_FORMAT,
                TOTAL_PURCHASE_PAYMENT,
                receipt.getTotalQuantity(),
                receipt.getCostInformation().get(0)));
        System.out.println(String.format(RECEIPT_2_ELEMENTS_COLUMN_FORMAT,
                PROMOTION_DISCOUNT,
                receipt.getCostInformation().get(1)));
        System.out.println(String.format(RECEIPT_2_ELEMENTS_COLUMN_FORMAT,
                MEMBERSHIP_DISCOUNT,
                receipt.getCostInformation().get(2)));
        System.out.println(String.format(RECEIPT_2_ELEMENTS_COLUMN_FORMAT,
                FINAL_PURCHASE_PAYMENT,
                receipt.getCostInformation().get(3)));
    }
}
