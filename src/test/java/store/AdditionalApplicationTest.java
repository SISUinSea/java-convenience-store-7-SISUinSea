package store;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.util.stream.Stream;

import static camp.nextstep.edu.missionutils.test.Assertions.assertNowTest;
import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;

class AdditionalApplicationTest extends NsTest {
    @Test
    void 파일에_있는_상품_목록_출력() {
        assertSimpleTest(() -> {
            run("[물-1]", "N", "N");
            assertThat(output()).contains(
                    "- 콜라 1,000원 10개 탄산2+1",
                    "- 콜라 1,000원 10개",
                    "- 사이다 1,000원 8개 탄산2+1",
                    "- 사이다 1,000원 7개",
                    "- 오렌지주스 1,800원 9개 MD추천상품",
                    "- 오렌지주스 1,800원 재고 없음",
                    "- 탄산수 1,200원 5개 탄산2+1",
                    "- 탄산수 1,200원 재고 없음",
                    "- 물 500원 10개",
                    "- 비타민워터 1,500원 6개",
                    "- 감자칩 1,500원 5개 반짝할인",
                    "- 감자칩 1,500원 5개",
                    "- 초코바 1,200원 5개 MD추천상품",
                    "- 초코바 1,200원 5개",
                    "- 에너지바 2,000원 5개",
                    "- 정식도시락 6,400원 8개",
                    "- 컵라면 1,700원 1개 MD추천상품",
                    "- 컵라면 1,700원 10개");
        });
    }

    @Test
    void 여러_개의_일반_상품_구매() {
        assertSimpleTest(() -> {
            run("[비타민워터-3],[물-2],[정식도시락-2]", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains("내실돈18,300");
        });
    }

    private static Stream<Arguments> freePromotionSuggestTestArguments() {
        return Stream.of(Arguments.arguments("[콜라-2]", "N", "N", "N", "현재 콜라은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)"),
                Arguments.arguments("[사이다-2]", "N", "N", "N", "현재 사이다은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)"),
                Arguments.arguments("[탄산수-2]", "N", "N", "N", "현재 탄산수은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)"),
                Arguments.arguments("[오렌지주스-1]", "N", "N", "N", "현재 오렌지주스은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)"),
                Arguments.arguments("[초코바-1]", "N", "N", "N", "현재 초코바은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)"),
                Arguments.arguments("[감자칩-1]", "N", "N", "N", "현재 감자칩은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)"));
    }

    @ParameterizedTest
    @MethodSource("freePromotionSuggestTestArguments")
    void 무료_증정_상품_정상_권유_테스트(
            String purchaseRequests,
            String freeSuggestAnswer,
            String membershipDiscountAnswer,
            String repurchaseAnswer,
            String expectToContain) {
        assertSimpleTest(() -> {
            run(purchaseRequests, freeSuggestAnswer, membershipDiscountAnswer, repurchaseAnswer);
            assertThat(output()).contains(expectToContain);
        });
    }

    private static Stream<Arguments> decreaseQuantitySuggestTestArguments() {
        return Stream.of(
                Arguments.arguments("[콜라-10]", "N", "N", "N", "현재 콜라 1개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)"),
                Arguments.arguments("[사이다-15]", "N", "N", "N", "현재 사이다 9개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)"),
                Arguments.arguments("[오렌지주스-9]", "N", "N", "N", "현재 오렌지주스 1개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)"),
                Arguments.arguments("[탄산수-5]", "N", "N", "N", "현재 탄산수 2개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)"),
                Arguments.arguments("[감자칩-10]", "N", "N", "N", "현재 감자칩 6개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)"),
                Arguments.arguments("[초코바-10]", "N", "N", "N", "현재 초코바 6개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)"),
                Arguments.arguments("[컵라면-10]", "N", "N", "N", "현재 컵라면 10개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)"));
    }

    @ParameterizedTest
    @MethodSource("decreaseQuantitySuggestTestArguments")
    void 프로모션_비적용_상품_제외_권유_테스트(
            String purchaseRequests,
            String freeSuggestAnswer,
            String membershipDiscountAnswer,
            String repurchaseAnswer,
            String expectToContain) {
        assertSimpleTest(() -> {
            run(purchaseRequests, freeSuggestAnswer, membershipDiscountAnswer, repurchaseAnswer);
            assertThat(output()).contains(expectToContain);
        });
    }

    @ParameterizedTest
    @CsvSource(value = { "[컵라면-12],N,N",
            "[정식도시락-9],N,N",
            "[에너지바-6],N,N",
            "[초코바-11],N,N",
            "[감자칩-11],N,N",
            "[비타민워터-7],N,N",
            "[물-11],N,N",
            "[탄산수-6],N,N",
            "[오렌지주스-10],N,N",
            "[사이다-16],N,N",
            "[콜라-21],N,N" })
    void 예외_테스트(String purchaseRequestLine, String decreaseQuantityAnswer, String membershipDiscountAnswer) {
        assertSimpleTest(() -> {
            runException(purchaseRequestLine, decreaseQuantityAnswer, membershipDiscountAnswer);
            assertThat(output()).contains("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        });
    }

    @Test
    void 멤버십_할인_금액_한계_테스트() {
        assertSimpleTest(() -> {
            run("[정식도시락-8],[에너지바-5],[비타민워터-6],[물-10]", "Y", "N");
            assertThat(output().replaceAll("\\s", "")).contains("멤버십할인-8,000");
        });
    }

    @Override
    public void runMain() {
        Application.main(new String[] {});
    }
}
