package store.view;

import static camp.nextstep.edu.missionutils.Console.readLine;
import static store.utils.Parser.validateRequestsSyntax;
import static store.utils.Parser.validateYNAnswer;

import java.util.function.Supplier;

public class InputView {
    private static final String ASK_REQUESTS = "\n구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])\n";
    private static final String ASK_FREE_QUANTITY = "\n현재 %s은(는) %d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)\n";
    private static final String ASK_BUY_WITHOUT_PROMOTION = "\n현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)\n";
    private static final String ASK_MEMBERSHIP_DISCOUNT = "\n멤버십 할인을 받으시겠습니까? (Y/N)\n";
    private static final String ASK_REMAIN_REQUEST = "\n감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)\n";

    public static String askPurchaseRequests() {
        System.out.println(ASK_REQUESTS);
        String purchaseRequestsLine = readLine();
        validateRequestsSyntax(purchaseRequestsLine);
        return purchaseRequestsLine;
    }

    public static String askAddFreePromotionProducts(String productName, Integer quantity) {
        while (true) {
            try {
                System.out.printf(ASK_FREE_QUANTITY,
                        productName,
                        quantity);
                String answer = readLine();
                validateYNAnswer(answer);
                return answer;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static String askRemoveNoPromotionProducts(String productName, Integer removeQuantity) {
        while (true) {
            try {
                System.out.printf(ASK_BUY_WITHOUT_PROMOTION,
                        productName,
                        removeQuantity);
                String answer = readLine();
                validateYNAnswer(answer);
                return answer;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static String askApplyMembershipDiscount() {
        System.out.println(ASK_MEMBERSHIP_DISCOUNT);
        String answer = readLine();
        validateYNAnswer(answer);
        return answer;
    }

    public static String askToContinuePurchase() {
        System.out.println(ASK_REMAIN_REQUEST);
        String answer = readLine();
        validateYNAnswer(answer);
        return answer;
    }

    public static Object askUntilGetValidAnswer(Supplier supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
