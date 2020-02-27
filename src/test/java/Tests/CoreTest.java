package Tests;

import apiServices.PetsApi;
import apiServices.UserApi;
import com.jayway.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class CoreTest {

    @Test
    public void petEndPointTest() throws IOException {
        PetsApi petApi = new PetsApi();
        Response putApiResponse = petApi.petsApiPutResponse();
        Assert.assertEquals("pets can not inserted ",200,putApiResponse.statusCode());

        petApi.petApiPutRequestResponseSchemaIsValid(putApiResponse);
        int petId = petApi.getPetIdInPatPutApiResponse(putApiResponse);

        Response getApiResponse = petApi.petApiGetRequest(petId);
        Assert.assertEquals("pets can not get",200,putApiResponse.statusCode());

        petApi.petApiGetRequestResponseSchemaIsValid(getApiResponse);
        petApi.comparePutDataAndGetData(putApiResponse,getApiResponse);

        String name = "sanji";
        String status = "pending";

        petApi.petApiUpdateRequest(petId,name,status);

        getApiResponse = petApi.petApiGetRequest(petId);
        Assert.assertEquals("pets can not get",200,putApiResponse.statusCode());


        Assert.assertEquals("Pet name was not updated",name,petApi.getPetName(getApiResponse));
        Assert.assertEquals("Pet status was not updated",status,petApi.getPetstatus(getApiResponse));

        petApi.petApiDeleteRequest(petId);

        getApiResponse = petApi.petApiGetRequest(petId);
        Assert.assertEquals("pets can not deleted ",404,getApiResponse.statusCode());

    }

    @Test
    public void getUserNotFoundTest() throws IOException {
        UserApi userApi = new UserApi();
        Response response = userApi.userApiGetRequest("notExists");

        Assert.assertEquals("invalid user name but response status code is not 404",404,response.statusCode());
    }
}
