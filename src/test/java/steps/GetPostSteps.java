package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import utilities.RestAssuredExtension;
import java.util.HashMap;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;

public class GetPostSteps {

    private static ResponseOptions<Response> response;

    // Perform GET request and store the response
    @Given("I perform GET operation for {string}")
    public void i_perform_get_operation_for_post(String url) {
        response = RestAssuredExtension.GetOPS(url);
        System.out.println(response.getBody().asPrettyString());
    }

    // Perform GET request for a specific post number
    @And("I perform GET for the post number {string}")
    public void iPerformGETForThePostNumber(String postNumber) {
        BDDStyleMethods.SimpleGetPost(postNumber);
    }

    // Verify the name in the response
    @Then("I should see the name as {string}")
    public void i_should_see_the_name_as(String name) {
        assertThat(response.getBody().jsonPath().get("data.first_name"), hasItem(name));
    }

    // Perform GET request to fetch all posts
    @Given("I perform GET for the all posts")
    public void i_perform_get_for_the_all_posts() {
    }

    // Verify the author's name in the response
    @Then("I should see the author name")
    public void i_should_see_the_author_name() {
        BDDStyleMethods.VerifyAllPagesInTheCollection("Byron");
    }

    // Verify multiple author names in the response
    @Then("I should see the author names")
    public void i_should_see_the_author_names() {
        BDDStyleMethods.PerformContainsCollection();
    }

    // Perform POST request with a predefined body
    @Given("I perform POST operation for {string}")
    public void iPerformPOSTOperationFor(String url) {
        BDDStyleMethods.PerformPOSTWithBodyParameter();
    }

    // Verify the name in the body of the response
    @Then("I should see the body has name as {string}")
    public void iShouldSeeTheBodyHasNameAs(String name) {
        assertThat(response.getBody().jsonPath().get("name"), hasItem(name));
        System.out.println(response.getBody().asPrettyString());
    }

    // Perform POST request with provided body data from DataTable
    @Given("I perform POST operation for {string} with body")
    public void iPerformPOSTOperationForWithBody(String url, DataTable table) {
        var body = table.asMaps(String.class, String.class);
        HashMap<String, String> pathParams = new HashMap<>();

        response = RestAssuredExtension.PostWithBodyParams(url, body);
    }

    // Print the response body in the console
    @And("I print the data in console")
    public void iPrintTheDataInConsole()
    {
        System.out.println("Response:" + response.getBody().asString());
    }

    // Perform PUT request with provided body data from DataTable
    @Given("I perform PUT operation for {string} with body")
    public void iPerformPutOperationForWithBody(String url, DataTable table) {
        var body = table.asMaps(String.class, String.class);
        HashMap<String, String> pathParams = new HashMap<>();

        response = RestAssuredExtension.PutWithBodyParams(url, body);
    }

    // Verify the status code of the response
    @Then("I have a {int} status code response")
    public void iHaveAStatusCodeResponse(int status) {
        System.out.println("Status was: "  + response.getStatusCode());
        assertThat(response.getStatusCode(), equalTo(status));
    }

    // Perform DELETE request with provided body data from DataTable
    @When("I perform DELETE operation for {string} with body")
    public void iPerformDELETEOperationForWithBody(String url, DataTable table) {
        var body = table.asMaps(String.class, String.class);
        HashMap<String, String> pathParams = new HashMap<>();

        response = RestAssuredExtension.DeleteWithBodyParams(url, body);
    }

    // Verify the response body is empty
    @And("I should not see a body in the response")
    public void iShouldNotSeeABodyInTheResponse() {
        assertThat(response.getBody().asString(), equalTo(""));
    }
}