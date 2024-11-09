package store.view;

import java.util.stream.Collectors;

import store.model.Receipt.Receipt;

import static store.model.Product.ProductTable.productTableToString;

public class OutputView {
    public static void printProductTable() {
        printProductTableHeader();
        System.out.println(productTableToString());
        System.out.println("\n");
    }

    private static void printProductTableHeader() {
        System.out.println("안녕하세요. W편의점입니다.");
        System.out.println("현재 보유하고 있는 상품입니다.");
        System.out.print("\n");
    }

    public static void printReceipt(Receipt receipt) {
        printTotalTransactions(receipt);
        printPromotionTransaction(receipt);
        printCostsStatistic(receipt);
    }

    private static void printTotalTransactions(Receipt receipt) {
        System.out.println("==============W 편의점================"); // TODO. DIVIDER_RECEIPT_HEADER
        System.out.println("상품명" + "\t\t" + "수량" + "\t" + "금액");
        System.out.println(receipt.getTotalTransactions().stream().collect(Collectors.joining("\n")));
    }

    private static void printPromotionTransaction(Receipt receipt) {
        if (receipt.getPromotionTransactions().stream().filter(t -> t != null).count() == 0) {
            return;
        }
        System.out.println("=============증	정==============="); // TODO. DIVIDER_PROMOTION_HEADER;
        System.out.println(
                receipt.getPromotionTransactions()
                        .stream()
                        .filter(t -> t != null)
                        .collect(Collectors.joining("\n")));
    }

    private static void printCostsStatistic(Receipt receipt) {
        System.out.println("===================================="); // TODO. DIVIDER_DEFAULT_LINE
        System.out.println("총구매액" + "\t\t" + receipt.getTotalQuantity() + "\t" + receipt.getCostInformation().get(0));
        System.out.println("행사할인" + "\t\t" + "\t" + receipt.getCostInformation().get(1));
        System.out.println("멤버십할인" + "\t\t" + "\t" + receipt.getCostInformation().get(2));
        System.out.println("내실돈" + "\t\t" + "\t" + receipt.getCostInformation().get(3));
    }
}
/*
 * ====================================
 * 총구매액 8 13,000
 * 행사할인 -1,000
 * 멤버십할인 -3,000
 * 내실돈 9,000
 */