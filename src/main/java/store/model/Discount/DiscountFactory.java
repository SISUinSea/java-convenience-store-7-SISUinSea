package store.model.Discount;

public class DiscountFactory {
    public static Discount createMembershipDiscount() {
        return new Discount("멤버십", 8000, 0.3);
    }
}
