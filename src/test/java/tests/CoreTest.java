package tests;

import apiServices.PetsApi;
import apiServices.StoreApi;
import apiServices.UserApi;
import baseSetting.TestSetting;
import com.jayway.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static enums.LoginInfo.PASSWORD;
import static enums.LoginInfo.USER_NAME;
import static enums.Status.AVAILABLE;
import static enums.Status.PENDING;

@Execution(ExecutionMode.CONCURRENT)
public class CoreTest extends TestSetting {


    @Test
    public void petEndPointTest() throws IOException {
        PetsApi petApi = new PetsApi();
        Response putApiResponse = petApi.petsApiPutResponse();
        Assertions.assertEquals(200, putApiResponse.statusCode(), "pets can not inserted ");

        petApi.petApiPutRequestResponseSchemaIsValid(putApiResponse);
        //get petId from JsonPath
        int petId = petApi.getPetIdInPatPutApiResponse(putApiResponse);

        //get request 1
        Response getApiResponse = petApi.petApiGetRequest(petId);
        Assertions.assertEquals(200, putApiResponse.statusCode(), "pets can not get");

        petApi.petApiGetRequestResponseSchemaIsValid(getApiResponse);
        petApi.comparePutDataAndGetData(putApiResponse, getApiResponse);

        String name = "sanji";
        String status = "pending";

        petApi.petApiUpdateRequest(petId, name, status);

        getApiResponse = petApi.petApiGetRequest(petId);
        Assertions.assertEquals(200, putApiResponse.statusCode(), "pets can not get");

        Assertions.assertEquals(name, petApi.getPetName(getApiResponse), "Pet name was not updated");
        Assertions.assertEquals(status, petApi.getPetstatus(getApiResponse), "Pet status was not updated");

        petApi.petApiDeleteRequest(petId);

        getApiResponse = petApi.petApiGetRequest(petId);
        Assertions.assertEquals(404, getApiResponse.statusCode(), "pets can not deleted ");
        getApiResponse.prettyPrint();
    }

    @Test
    public void getUserNotFoundTest() throws IOException {
        UserApi userApi = new UserApi();
        Response response = userApi.userApiGetRequest("notExists");
        Assertions.assertEquals(404, response.statusCode(), "invalid user name but response status code is not 404");
    }

    @Test

    public void getStoreTest() throws IOException {
        StoreApi storeApi = new StoreApi();
        //get request 2
        Response response = storeApi.getStore(5);
        Assertions.assertEquals("unknown", storeApi.getType(response));
        Assertions.assertEquals(response.statusCode(), 404);
        //get request 3 no parameter
        Response inventoryRespons = storeApi.getStoreInventory();
        Assertions.assertEquals(inventoryRespons.statusCode(), 200);
    }

    @Test

    public void getSessionId() throws IOException {
        UserApi userApi = new UserApi();
        // parameter get request 4
        Response userApiResponse = userApi.getUserSession(USER_NAME, PASSWORD);
        System.out.println(userApiResponse.statusCode());
    }

    @Test
    public void getFindStatus() throws IOException {
        ArrayList<Map<String, String>> situations = new ArrayList<Map<String, String>>();
        situations.add(
                new HashMap<String, String>() {
                    {
                        put("status", AVAILABLE.getstatus());
                    }
                });
        situations.add(
                new HashMap<String, String>() {
                    {
                        put("status", PENDING.getstatus());
                    }
                });

        PetsApi petsApi = new PetsApi();
        //get request 5
        Response response =petsApi.petFindByStatus(situations);
        Assertions.assertEquals(response.statusCode(), 200);
        response.prettyPrint();
    }
}


