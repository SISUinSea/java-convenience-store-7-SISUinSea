package store.utils;

public enum ErrorMessage {
    PRODUCT_NOT_EXIST("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요."),
    QUANTITY_NOT_ENOUGH("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    ILLEGAL_REQUEST_SYNTAX("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    ILLEGAL_INPUT("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요."),
    ILLEGAL_BOOT_SYSTEM("[ERROR] 잘못된 형식입니다. md 파일을 확인해주세요.");

    private final String description;

    private ErrorMessage(final String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
