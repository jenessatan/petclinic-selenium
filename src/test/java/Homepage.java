// page url: localhost:9966/petclinic

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Homepage extends PageObject{
    public Homepage(WebDriver driver) {
        super(driver);
    }

    private String EXPECTED_PATH = "/petclinic/";

    @FindBy (xpath="//h2[contains(text(),'Welcome')]")
    private WebElement welcome_header;

    public Boolean hasWelcomeHeader() {
        return welcome_header != null;
    }

    public Boolean hasCorrectPath() {
        return getPath().equals(EXPECTED_PATH);
    }
}
