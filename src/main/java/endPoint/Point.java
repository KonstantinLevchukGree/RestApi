package endPoint;

public enum Point {
    BASE_URL("http://localhost:9090"),
    GET_USER_TOKEN("/oauth/token"),

    GET_ZIP_CODES("/zip-codes");
    private String apiMethod;

    public String getApiMethod() {
        return apiMethod;
    }

    Point(String endPoint) {
        this.apiMethod = endPoint;
    }
}
