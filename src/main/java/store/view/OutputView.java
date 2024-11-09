package store.view;

import java.util.stream.Collectors;

import store.model.Receipt.Receipt;

import static store.model.Product.ProductTable.productTableToString;

public class OutputView {
    private static final String HELLO = "안녕하세요. W편의점입니다.";
    private static final String CURRENT_STOCK_HEADER = "현재 보유하고 있는 상품입니다.";
    private static final String DIVIDER_RECEIPT_HEADER = "==============W 편의점================";
    private static final String RECEIPT_COLUMN_HEADER = "상품명" + "\t\t" + "수량" + "\t" + "금액";
    private static final String DIVIDER_PROMOTION_HEADER = "=============증	정===============";
    private static final String DIVIDER_DEFAULT_LINE = "====================================";
    private static final String TOTAL_PURCHASE_PAYMENT = "총구매액";
    private static final String PROMOTION_DISCOUNT = "행사할인";
    private static final String MEMBERSHIP_DISCOUNT = "멤버십할인";
    private static final String FINAL_PURCHASE_PAYMENT = "내실돈";

    public static void printProductTable() {
        printProductTableHeader();
        System.out.println(productTableToString());
        System.out.println();
    }

    private static void printProductTableHeader() {
        System.out.println(HELLO);
        System.out.println(CURRENT_STOCK_HEADER);
        System.out.println();
    }

    public static void printReceipt(Receipt receipt) {
        printTotalTransactions(receipt);
        printPromotionTransaction(receipt);
        printCostsStatistic(receipt);
    }

    private static void printTotalTransactions(Receipt receipt) {
        System.out.println(DIVIDER_RECEIPT_HEADER);
        System.out.println(RECEIPT_COLUMN_HEADER);
        System.out.println(receipt.getTotalTransactions().stream().collect(Collectors.joining("\n")));
    }

    private static void printPromotionTransaction(Receipt receipt) {
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

    private static void printCostsStatistic(Receipt receipt) {
        System.out.println(DIVIDER_DEFAULT_LINE);
        System.out.println(TOTAL_PURCHASE_PAYMENT + "\t\t" + receipt.getTotalQuantity() + "\t"
                + receipt.getCostInformation().get(0));
        System.out.println(PROMOTION_DISCOUNT + "\t\t" + "\t" + receipt.getCostInformation().get(1));
        System.out.println(MEMBERSHIP_DISCOUNT + "\t\t" + "\t" + receipt.getCostInformation().get(2));
        System.out.println(FINAL_PURCHASE_PAYMENT + "\t\t" + "\t" + receipt.getCostInformation().get(3));
    }
}
