package enums;

public enum LoginInfo {
    USER_NAME("serhat_pet_store"),
    PASSWORD("pet_store213");

    private String loginInfoValue;

    LoginInfo(String loginInfoValue) {
        this.loginInfoValue = loginInfoValue;
    }

    public String getLoginInfo() {
        return loginInfoValue;
    }
}
