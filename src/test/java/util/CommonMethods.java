package util;


import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;

import java.io.File;
import java.util.Random;

import static com.jayway.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

public class CommonMethods {

    public int createRandomInt() {
        Random random = new Random();
        return random.nextInt(1000) + 1;
    }

    public String getRandomPetCategory() {
        String[] patCategories = {"Rabbits", "Mice", "Cats", "dogs"};

        Random random = new Random();

        return patCategories[random.nextInt(patCategories.length - 1)];
    }

    public String getRandomPetName() {
        String[] petNames = {"paşa", "pamuk", "duman", "tarçın", "zeytin"};

        Random random = new Random();

        return petNames[random.nextInt(petNames.length - 1)];
    }

    public String getRandomTag() {
        String[] tags = {"tag1", "tag2", "tag3", "tag4", "tag5"};

        Random random = new Random();

        return tags[random.nextInt(tags.length - 1)];
    }

    public void isJsonSchemaMatches(Response response, String path) {
        response.then()
                .assertThat()
                .body(matchesJsonSchema(new File(path)));
    }

    public String getPathValue(Response response, String key) {
        String responseBody = response.getBody().asString();
        JsonPath jsonPath = new JsonPath(responseBody);
        return jsonPath.get(key).toString();
    }
}

