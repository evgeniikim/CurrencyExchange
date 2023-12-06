package interfaces;


public enum TransactionType {
    DEPOSIT("Пополнение счета"),
    WITHDRAWAL("Снятие средств"),
    TRANSFER("Перевод средств"),
    CURRENCY_EXCHANGE("Обмен валюты");

    private final String description;

    TransactionType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}