package webpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NavBar extends PageObject{
    public NavBar(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//a[contains(text(), 'Home')]")
    private WebElement home_button;

    @FindBy(xpath = "//a[contains(text(), 'Find owners')]")
    private WebElement find_owners_button;

    @FindBy(xpath = "//a[contains(text(), 'Veterinarians')")
    private WebElement veternarians_button;

    @FindBy (xpath=".//a[contains(text(), ' Error')]")
    private WebElement error_button;



    public void pressHomeButton() {
        this.home_button.click();
    }

    public void pressFindOwnersButton() { this.find_owners_button.click(); }

    public void pressVeternariansButton() { this.veternarians_button.click(); }

    public void pressErrorButton() {
        this.error_button.click();
    }

}
