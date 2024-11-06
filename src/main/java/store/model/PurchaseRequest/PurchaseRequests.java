package store.model.PurchaseRequest;

import java.util.List;

public class PurchaseRequests {
    List<PurchaseRequest> requests;

    public PurchaseRequests(List<PurchaseRequest> queue) {
        this.requests = queue;
    }

    public boolean isEmpty() {
        return requests.isEmpty();
    }

    public List<PurchaseRequest> getRequests() {
        return requests;
    }
}
