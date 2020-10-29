// page url: localhost:9966/petclinic

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Homepage extends PageObject{
    public Homepage(WebDriver driver) {
        super(driver);
    }

    @FindBy (xpath="//h2[contains(text(),'Welcome')]")
    private WebElement welcome_header;

    public String hasWelcomeHeader() {
        driver.findElement(By.xpath("//h2[contains(text(),'Welcome')]"));
        return "help me";
    }
}
