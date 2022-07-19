package AddUserTest.definition;

import ems.module.UserDto;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static org.junit.Assert.*;


public class AddUserStep {
    private UserDto userDto;
    private final String BASE_URL = "http://localhost:8080/api";
    private Response response;
    private RequestSpecification request;
    private String content;
    @Given("Someone navigate to signup page")
    public void someoneNavigateToSignupPage() {
        RestAssured.baseURI = BASE_URL;
        request = RestAssured.given();
        request.header("Content-type", "application/json");
    }

    @When("Someone enters {string} {string} {string} {string}")
    public void someoneEntersNameLastNameEmailPassword(String name, String lastName, String email, String password) {
        content = "{\n  \"name\": \"" + name + "\"," +
                "\n  \"lastName\": \"" + lastName + "\"," +
                "\n \"email\": \"" + email + "\"," +
                "\n \"password\": \"" + password + "\"\n}";
        response = request.body(content).post("/users");
    }
    @Then("Signup should be successful")
    public void signupShouldBeSuccessful() {
        assertEquals(201, response.getStatusCode());
    }
}
