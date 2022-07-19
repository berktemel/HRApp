package LoginTest.definition;

import ems.controller.UserController;
import ems.module.UserDto;
import ems.service.UserService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

import static org.junit.Assert.*;

public class LoginStep {
    private final String BASE_URL = "http://localhost:8080/api";
    private Response response;
    private RequestSpecification request;
    private String content;
    @Given("User navigate to login page")
    public void user_navigate_to_login_page() {
        RestAssured.baseURI = BASE_URL;
        request = RestAssured.given();
        request.header("Content-type", "application/json");
    }
    @When("User logs in by using {string}")
    public void userLogsInByUsingEmail(String email) {
        content = "{\n  \"email\": \"" + email + "\",\n  \"password\": ";
    }

    @And("By using {string}")
    public void byUsingPassword(String password) {
        content += password + "\n}";
        response = request.body(content).post("/users/login");
    }
    @Then("^Login is \"([^\"]*)\"$")
    public void loginIs(String arg1) {
        assertEquals(arg1, response.getBody().print());
    }
}
