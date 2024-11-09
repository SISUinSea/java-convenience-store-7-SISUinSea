package store.model.PurchaseRequest;

import static store.view.InputView.askPurchaseRequests;
import static store.view.InputView.askUntilGetValidAnswer;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import store.model.Product.ProductTable;
import store.utils.Parser;

public class PurchaseRequestFactory {
    public static PurchaseRequests createPurchaseRequests(ProductTable productTable) {
        return (PurchaseRequests) askUntilGetValidAnswer(() -> {
            String purchaseRequestsLine = askPurchaseRequests();
            List<String> requests = Arrays.stream(purchaseRequestsLine.split(","))
                    .map(Parser::removeSquareBrakets)
                    .toList();
            List<PurchaseRequest> purchaseRequests = requests.stream()
                    .map(PurchaseRequestFactory::createSinglePurchaseRequest)
                    .toList();
            productTable.checkRequestValidity(purchaseRequests);
            return new PurchaseRequests(new LinkedList<PurchaseRequest>(purchaseRequests));
        });
    }

    public static PurchaseRequest createSinglePurchaseRequest(String request) {
        String[] parsedRequest = request.split("-");
        String productName = parsedRequest[0];
        Integer quantity = Integer.parseInt(parsedRequest[1]);
        return new PurchaseRequest(productName, quantity);
    }
}
