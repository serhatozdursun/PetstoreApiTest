package apiServices;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import util.CommonMethods;
import util.Configuration;

import java.io.IOException;

public class StoreApi extends CommonMethods {
    private static String STORE_ENDPONT1 = "v2/store/";
    private static String STORE_ENDPOINT2 ="order/";
    private static String STORE_ENDPOINT3 ="inventory/";


    public Response getStore(int storeId) throws IOException {
        Configuration configuration = new Configuration();
        RestAssured.baseURI = configuration.getApiBaseUrl();

        RequestSpecification request = RestAssured
                .given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("api_key", configuration.getApiKey());
        return request
                .when()
                .get(STORE_ENDPONT1 +STORE_ENDPOINT2+ "/" + storeId)
                .then()
                .extract()
                .response();
    }


    public Response getStoreInventory() throws IOException {
        Configuration configuration = new Configuration();
        RestAssured.baseURI = configuration.getApiBaseUrl();

        RequestSpecification request = RestAssured
                .given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("api_key", configuration.getApiKey());
        return request
                .when()
                .get(STORE_ENDPONT1 +STORE_ENDPOINT3)
                .then()
                .extract()
                .response();
    }

    public String getType(Response response){
        return  getPathValue(response,"type");
    }

    public String getAvailable(Response response){
        return  getPathValue(response,"available");
    }


}
