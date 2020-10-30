package webpages;// Page URL: http://localhost:9966/petclinic/owners.html?lastName={search value}

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class OwnersListingPage extends PageObject {

    public OwnersListingPage(WebDriver driver) {
        super(driver);
    }

    //header
    @FindBy(xpath = "//h2[contains(text(), 'Owners')]")
    private WebElement header;

    //search filter
    @FindBy(xpath = "//div[@id='owners_filter']")
    private WebElement search_filter_div;
    //filter elements - initiate using search_filter_div
    private WebElement search_label;
    private WebElement search_input;



}
