package store.model.Discount;

public class DiscountFactory {
    public static Discount makeMembershipDiscount() {
        return new Discount("멤버십", 8000, 0.3);
    }
}
