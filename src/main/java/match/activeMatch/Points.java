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

}
