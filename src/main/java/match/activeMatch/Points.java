package match.activeMatch;

public enum Points {
    ZERO("0"),
    FIFTEEN("15"),
    THIRTY("30"),
    FOURTY("40"),
    ADVANTAGE("AD");

    private final String value;

    Points(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public static Points getByValue(String soughtAfterValue) {
        for (Points point : values()) {
            if (point.value().equals(soughtAfterValue)) {
                return point;
            }
        }
        throw new IllegalArgumentException("Нет такого значения Point: " + soughtAfterValue);
    }

    public Points next() {
        int ordinal = this.ordinal();
        if (ordinal + 1 >= values().length) {
            throw new IndexOutOfBoundsException("Отсутствует следующее значение Point.");
        }
        return values()[ordinal + 1];
    }

    public Points previous() {
        int ordinal = this.ordinal();
        if (ordinal - 1 < 0) {
            throw new IndexOutOfBoundsException("Отсутствует предыдущее значение Point.");
        }
        return values()[ordinal - 1];
    }

    public static Points resetPoint() {
        return values()[0];
    }

}
