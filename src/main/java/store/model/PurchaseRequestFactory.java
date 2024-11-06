package store.model;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import store.utils.Parser;

public class PurchaseRequestFactory {
    public static PurchaseRequestQueue createPurchaseRequestQueue(String purchaseData) {
        List<String> requests = Arrays.stream(purchaseData.split(","))
                                                .map(Parser::removeSquareBrakets)
                                                .toList();
        List<PurchaseRequest> purchaseRequests = requests.stream()
                                                .map(PurchaseRequestFactory::createSinglePurchaseRequest)
                                                .toList();
        return new PurchaseRequestQueue(new LinkedList<PurchaseRequest>(purchaseRequests));
    }

    public static PurchaseRequest createSinglePurchaseRequest(String request) {
        String[] parsedRequest = request.split("-");
        String productName = parsedRequest[0];
        Integer quantity = Integer.parseInt(parsedRequest[1]);
        return new PurchaseRequest(productName, quantity);
    }
}
