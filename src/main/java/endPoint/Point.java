package endPoint;

public enum Point {
    BASE_URL("http://localhost:9090"),
    USER_TOKEN("/oauth/token"),
    ZIP_CODES("/zip-codes"),
    EXPAND_ZIP_CODES("/expand"),
    USERS("/users");
    private String apiPont;

    public String getApiPoint() {
        return apiPont;
    }

    Point(String endPoint) {
        this.apiPont = endPoint;
    }
}
