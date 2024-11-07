package store.model.Discount;

public class Discount {
    private final String name;
    private final Integer limit;
    private final Double rate;
    private Integer totalDiscountAmount;

    public Discount(
            final String name,
            final Integer limit,
            final Double rate) {
        this.name = name;
        this.limit = limit;
        this.rate = rate;
        this.totalDiscountAmount = 0;
    }

    public Integer applyDiscount(Integer beforeDiscount) {
        Integer discountAmount = (int) (beforeDiscount * rate);
        if (totalDiscountAmount + discountAmount < limit) {
            totalDiscountAmount += discountAmount;
            return discountAmount;
        }
        return limit;
    }

}
