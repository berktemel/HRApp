package AddEmployeeTest.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/ems/addEmployee.feature",
        glue = {"AddEmployeeTest.definition"}
)
public class AddEmployeeRunner {

}
