package AddEmployeeTest.definition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static org.junit.Assert.*;

public class AddEmployeeStep {
    private final String BASE_URL = "http://localhost:8080/api";
    private Response response;
    private RequestSpecification request;
    @Given("User navigates to add employee page")
    public void userNavigatesToAddEmployeePage() {
        RestAssured.baseURI = BASE_URL;
        request = RestAssured.given();
        request.header("Content-type", "application/json");
    }

    @When("User enters {string} {string} {string} {string} {string} {int}")
    public void userEntersNameLastNameEmailDepartmentStartDateSalary(String name, String lastName,
                                                                     String email, String department,
                                                                     String startDate, int salary) {
        String content = "{\n \"firstName\": \"" + name + "\", \n" +
                "\"lastName\": \"" + lastName + "\", \n" +
                "\"email\": \"" + email + "\", \n" +
                "\"department\": { \n \"depName\": \"" + department + "\" \n}," +
                "\"startDate\": \"" + startDate + "\", \n" +
                "\"salary\": " + salary + "\n }";
        response = request.body(content).post("/employees");
    }

    @Then("Add should be successful")
    public void addShouldBeSuccessful() {
        assertEquals(201, response.getStatusCode());
    }
}
