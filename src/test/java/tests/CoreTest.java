package tests;

import apiServices.PetsApi;
import apiServices.StoreApi;
import apiServices.UserApi;
import baseSetting.TestSetting;
import com.jayway.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static enums.LoginInfo.PASSWORD;
import static enums.LoginInfo.USER_NAME;
import static enums.Status.AVAILABLE;
import static enums.Status.PENDING;

public class CoreTest extends TestSetting {


    @Test
    public void petEndPointTest() throws IOException {
        PetsApi petApi = new PetsApi();
        Response putApiResponse = petApi.petsApiPutResponse();
        Assert.assertEquals("pets can not inserted ", 200, putApiResponse.statusCode());

        petApi.petApiPutRequestResponseSchemaIsValid(putApiResponse);
        //get petId from JsonPath
        int petId = petApi.getPetIdInPatPutApiResponse(putApiResponse);

        //get request 1
        Response getApiResponse = petApi.petApiGetRequest(petId);
        Assert.assertEquals("pets can not get", 200, putApiResponse.statusCode());

        petApi.petApiGetRequestResponseSchemaIsValid(getApiResponse);
        petApi.comparePutDataAndGetData(putApiResponse, getApiResponse);

        String name = "sanji";
        String status = "pending";

        petApi.petApiUpdateRequest(petId, name, status);

        getApiResponse = petApi.petApiGetRequest(petId);
        Assert.assertEquals("pets can not get", 200, putApiResponse.statusCode());

        Assert.assertEquals("Pet name was not updated", name, petApi.getPetName(getApiResponse));
        Assert.assertEquals("Pet status was not updated", status, petApi.getPetstatus(getApiResponse));

        petApi.petApiDeleteRequest(petId);

        getApiResponse = petApi.petApiGetRequest(petId);
        Assert.assertEquals("pets can not deleted ", 404, getApiResponse.statusCode());
        getApiResponse.prettyPrint();
    }

    @Test
    public void getUserNotFoundTest() throws IOException {
        UserApi userApi = new UserApi();
        Response response = userApi.userApiGetRequest("notExists");
        Assert.assertEquals("invalid user name but response status code is not 404", 404, response.statusCode());
    }

    @Test
    public void getStoreTest() throws IOException {
        StoreApi storeApi = new StoreApi();
        //get request 2
        Response response = storeApi.getStore(5);
        Assert.assertEquals("unknown", storeApi.getType(response));
        Assert.assertEquals(response.statusCode(), 404);
        //get request 3 no parameter
        Response inventoryRespons = storeApi.getStoreInventory();
        Assert.assertEquals(inventoryRespons.statusCode(), 200);
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
        response.prettyPrint();
    }
}


