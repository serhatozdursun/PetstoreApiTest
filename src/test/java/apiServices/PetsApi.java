package apiServices;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Assert;
import util.CommonMethods;
import util.Configuration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class PetsApi extends CommonMethods {
    private static String PET_ENDPOINT = "v2/pet/";
    private static String FIND_BY_STATUS_ENDPOINT = "findByStatus/";

    public Response petsApiPutResponse() throws IOException {
        Configuration configuration = new Configuration();
        RestAssured.baseURI = configuration.getApiBaseUrl();

        JSONObject categoryParams = new JSONObject();
        categoryParams.put("id", createRandomInt());
        categoryParams.put("name", getRandomPetCategory());

        JSONArray tags = new JSONArray();
        JSONObject tagParams = new JSONObject();
        tagParams.put("id", createRandomInt());
        tagParams.put("name", getRandomTag());
        tags.add(tagParams);

        JSONArray photoUrl = new JSONArray();
        photoUrl.add("www.url.com");

        JSONObject requestParams = new JSONObject();
        requestParams.put("id", createRandomInt());
        requestParams.put("category", categoryParams);
        requestParams.put("name", getRandomPetName());
        requestParams.put("photoUrls", photoUrl);
        requestParams.put("tags", tags);
        requestParams.put("status", "available");


        RequestSpecification request = RestAssured
                .given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("api_key", configuration.getApiKey())
                .body(requestParams.toJSONString());

        return request
                .when()
                .put(PET_ENDPOINT)
                .then()
                .extract()
                .response();
    }

    public Response petApiGetRequest(int id) throws IOException {
        Configuration configuration = new Configuration();
        RestAssured.baseURI = configuration.getApiBaseUrl();

        RequestSpecification request = RestAssured
                .given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("api_key", configuration.getApiKey());
        return request
                .when()
                .get(PET_ENDPOINT + id)
                .then()
                .extract()
                .response();
    }

    public void petApiUpdateRequest(int id, String name, String status) throws IOException {
        Configuration configuration = new Configuration();
        RestAssured.baseURI = configuration.getApiBaseUrl();
        RestAssured
                .given()
                .header("accept", "application/json")
                .header("Content-Type", " application/x-www-form-urlencoded")
                .header("api_key", configuration.getApiKey())
                .queryParam("name", name)
                .queryParam("status", status)
                .when()
                .post(PET_ENDPOINT + id)
                .then()
                .extract()
                .response();
    }

    public void petApiDeleteRequest(int id) throws IOException {
        Configuration configuration = new Configuration();
        RestAssured.baseURI = configuration.getApiBaseUrl();

        RestAssured
                .given()
                .header("accept", "application/json")
                .header("Content-Type", " application/x-www-form-urlencoded")
                .header("api_key", configuration.getApiKey())
                .when()
                .delete(PET_ENDPOINT + id)
                .then()
                .extract()
                .response();
    }

    public Response petFindByStatus(ArrayList<Map<String, String>> situations) throws IOException {
        Configuration configuration = new Configuration();
        RestAssured.baseURI = configuration.getApiBaseUrl();
        RequestSpecification request = RestAssured
                .given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("api_key", configuration.getApiKey());
        for (Map status : situations) {
            request.queryParam("status", status.get("status"));
        }

        return request
                .when()
                .get(PET_ENDPOINT + FIND_BY_STATUS_ENDPOINT)
                .then()
                .extract()
                .response();
    }

    public void petApiPutRequestResponseSchemaIsValid(Response response) {
        isJsonSchemaMatches(response, System.getProperty("user.dir") + "\\src\\test\\resources\\jsonSchemas\\petApiPutSchema.JSON");
    }

    public int getPetIdInPatPutApiResponse(Response response) {
        return Integer.parseInt(getPathValue(response, "id"));
    }


    public void petApiGetRequestResponseSchemaIsValid(Response response) {
        isJsonSchemaMatches(response, System.getProperty("user.dir") + "\\src\\test\\resources\\jsonSchemas\\petApiGetSchema.JSON");
    }

    public void comparePutDataAndGetData(Response putApiResponse, Response getApiResponse) {
        Assert.assertEquals(getApiResponse.getBody().asString(), putApiResponse.getBody().asString());
    }

    public String getPetName(Response response){
        return  getPathValue(response,"name");
    }

    public String getPetstatus(Response response){
        return  getPathValue(response,"status");
    }

}
