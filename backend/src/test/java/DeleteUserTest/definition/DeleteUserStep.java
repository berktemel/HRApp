package DeleteUserTest.definition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static org.junit.Assert.*;

public class DeleteUserStep {
    private final String BASE_URL = "http://localhost:8080/api";
    private Response response;
    private RequestSpecification request;
    @Given("Admin tries to delete a user")
    public void adminTriesToDeleteAUser() {
        RestAssured.baseURI = BASE_URL;
        request = RestAssured.given();
        request.header("Content-type", "application/json");
    }
    @When("{long} is given")
    public void idIsGiven(long id) {
        String body = "{\n \"id\": \"" + id + "\" \n}";
        response = request.body(body).delete("/users/"+id);
    }
    @Then("User should be deleted")
    public void userShouldBeDeleted() {
        assertEquals(204, response.getStatusCode());
    }
}
