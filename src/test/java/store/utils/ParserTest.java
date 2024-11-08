package store.utils;

import static store.utils.Parser.validateRequestsSyntax;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ParserTest {
    @DisplayName("구매 요청 입력 예외 테스트")
    @ParameterizedTest
    @ValueSource(strings = { "[-]", "[]", "[--]", "-[]", "][]", "[콜라-3],", ",[콜라-3]" })
    void validateRequestsSyntaxTest(String requestsLine) {
        assertThatThrownBy(
                () -> validateRequestsSyntax(requestsLine))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
