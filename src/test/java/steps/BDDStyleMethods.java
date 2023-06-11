package steps;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.core.Is;

import java.util.HashMap;
import java.util.List;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

//First try I changed the method to RestAssuredExtension

public class BDDStyleMethods {
    public static void SimpleGetPost(String postNumber){
        given().contentType(ContentType.JSON).when()
                .get(String.format("https://reqres.in/api/users/%s",postNumber)).
                then().body("data.first_name", equalTo("Janet"));
    }

    public static void PerformContainsCollection(){
        given()
                .contentType(ContentType.JSON)
        .when()
                .get("https://reqres.in/api/users")
        .then()
                .assertThat()
                .body("data.find { it.first_name == 'George' }.first_name", equalTo("George"));
    }

    public static void VerifyAllPagesInTheCollection(String name){
        int pageNumber = 1;
        while (true) {
            Response response =
                    given()
                            .contentType(ContentType.JSON)
                    .when()
                            .queryParam("page", pageNumber)
                            .get("https://reqres.in/api/users")
                    .then()
                            .contentType(ContentType.JSON).
                            extract().
                            response();

            // Check if 'name' is in this page.
            List<String> firstNames = response.jsonPath().getList("data.first_name");
            if (firstNames.contains(name)) {
                System.out.println(name + " found on page " + pageNumber);
            }

            //Verify total pages
            int total_pages = response.jsonPath().getInt("total_pages");

            //If actual page is equal to total pages then break
            if (pageNumber >= total_pages) {
                break;
            }
            pageNumber++;
        }
    }

    public static void ShowData(String postNumber){
        Response response = RestAssured.get(String.format("https://reqres.in/api/users?page=2",postNumber));

        // Print the entire response
        System.out.println("Response:" + response.asString());

        // Get the status code
        int statusCode = response.getStatusCode();
        System.out.println("Status Code: " + statusCode);

        // Get a specific field from the JSON response body
        String message = response.jsonPath().getString("message");
        System.out.println("Message: " + message);

        // You can extract other information from the response, like headers
        String contentType = response.getHeader("Content-Type");
        System.out.println("Content-Type: " + contentType);
    }

    public static void PerformPOSTWithBodyParameter() {
        HashMap<String,String> postContent = new HashMap<>();
        postContent.put("email", "jayson.test@test.com");
        postContent.put("first_name", "Jayson");
        postContent.put("last-name", "tester");
        postContent.put("avatar", "https://reqres.in/img/faces/2-image.jpg");

        given()
                .contentType(ContentType.JSON).
        with()
                .body(postContent).
        when()
                .post("https://reqres.in/api/users").
        then()
                .body("first_name", Is.is("Jayson"));
    }

}
