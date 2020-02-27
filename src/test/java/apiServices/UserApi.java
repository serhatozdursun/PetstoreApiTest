package apiServices;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import util.Configuration;

import java.io.IOException;

public class UserApi {
    String userEndPoint ="v2/user";
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
                .get(userEndPoint + "/" + name)
                .then()
                .extract()
                .response();
    }
}
