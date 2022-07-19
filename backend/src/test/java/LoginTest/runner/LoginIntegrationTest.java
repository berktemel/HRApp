package LoginTest.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/ems/login.feature",
        glue = {"LoginTest.definition"}
)
public class LoginIntegrationTest {
}
