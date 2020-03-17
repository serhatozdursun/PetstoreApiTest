package enums;

public enum Status {
    AVAILABLE("available"),
    PENDING("pending"),
    SOLD("sold");

    private String status;

    Status(String loginInfoValue) {
        this.status = loginInfoValue;
    }

    public String getstatus() {
        return status;
    }

}
