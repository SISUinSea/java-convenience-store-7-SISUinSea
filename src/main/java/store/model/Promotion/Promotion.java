package store.model.Promotion;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Promotion {
    private final String name;
    private final Integer buy;
    private final Integer get;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Promotion(String name, Integer buy, Integer get, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean isPromotioning(LocalDateTime currentTime) {
        if (startDate.atStartOfDay().isBefore(currentTime) && endDate.atTime(LocalTime.MAX).isAfter(currentTime)) {
            return true;
        }
        return false;
    }

    public Integer getFreeQuantity(Integer promotionQuantity) {
        return promotionQuantity / getBundle() * get;
    }

    public Integer getBundle() {
        return buy + get;
    }

    public String getName() {
        return name;
    }

    public Integer getBuy() {
        return buy;
    }

    public Integer getGet() {
        return get;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
