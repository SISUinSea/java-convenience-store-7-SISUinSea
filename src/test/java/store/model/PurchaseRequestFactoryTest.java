package store.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import store.model.PurchaseRequest.PurchaseRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static store.model.PurchaseRequest.PurchaseRequestFactory.createSinglePurchaseRequest;

public class PurchaseRequestFactoryTest {
    @ParameterizedTest
    @CsvSource(value = { "콜라-10,콜라,10", "사이다-12,사이다,12" })
    void createSinglePurchaseRequestTest(String request, String expectedProductName, Integer expectedQuantity) {
        PurchaseRequest purchaseRequest = createSinglePurchaseRequest(request);
        assertThat(purchaseRequest
                .getProductName())
                .isEqualTo(expectedProductName);
        assertThat(purchaseRequest
                .getQuantity())
                .isEqualTo(expectedQuantity);
    }
}
