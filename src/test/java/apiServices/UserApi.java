package apiServices;


import enums.LoginInfo;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import util.Configuration;

import java.io.IOException;

public class UserApi {
    private static String USER_ENDPOINT ="v2/user/";
    private static String USER_ENDPOINT2 ="login/";
    public Response userApiGetRequest(String name) throws IOException {
        Configuration configuration = new Configuration();
        RestAssured.baseURI = configuration.getApiBaseUrl();

        RequestSpecification request = RestAssured
                .given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("api_key", configuration.getApiKey());
        return request
                .when()
                .get(USER_ENDPOINT + name)
                .then()
                .extract()
                .response();
    }

    public Response getUserSession(LoginInfo username, LoginInfo password) throws IOException {
        Configuration configuration = new Configuration();
        RestAssured.baseURI = configuration.getApiBaseUrl();

        RequestSpecification request = RestAssured
                .given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("api_key", configuration.getApiKey())
                .queryParam("username",username.getLoginInfo())
                .queryParam("password",password.getLoginInfo());
        return request
                .when()
                .get(USER_ENDPOINT + USER_ENDPOINT2)
                .then()
                .extract()
                .response();
    }
}
