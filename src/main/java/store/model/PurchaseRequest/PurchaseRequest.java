package store.model.PurchaseRequest;

public class PurchaseRequest {
    private final String productName;
    private final Integer quantity;

    public PurchaseRequest(final String productName, final Integer quantity) {
        this.productName = productName;
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
