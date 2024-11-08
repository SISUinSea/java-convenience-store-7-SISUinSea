package store.view;

import static camp.nextstep.edu.missionutils.Console.readLine;

public class InputView {
    public static String askPurchaseRequest() {
        while (true) {
            try {
                System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
                // TODO. 예외 처리
                return readLine();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static String askAddFreePromotionProducts(String productName, Integer quantity) {
        while (true) {
            try {
                System.out.printf("현재 %s은(는) %d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)",
                        productName,
                        quantity);
                String answer = readLine();
                if (!answer.equals("Y") && !answer.equals("N")) {
                    throw new IllegalArgumentException("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");
                }
                return answer;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static String askRemoveNoPromotionProducts(String productName, Integer removeQuantity) {
        while (true) {
            try {
                System.out.printf("현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)",
                        productName,
                        removeQuantity);
                String answer = readLine();
                if (!answer.equals("Y") && !answer.equals("N")) {
                    throw new IllegalArgumentException("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");
                }
                return answer;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static String askApplyMembershipDiscount() {
        System.out.println("멤버십 할인을 받으시겠습니까? (Y/N)");
        return readLine();
    }

    public static String askToContinuePurchase() {
        System.out.println("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");
        return readLine();
    }
}
