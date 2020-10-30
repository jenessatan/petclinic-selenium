package webpages;// Page URL: http://localhost:9966/petclinic/owners/find.html

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FindOwnersPage extends PageObject {
    public FindOwnersPage(WebDriver driver) {
        super(driver);
        search_input = search_form.findElement(By.xpath("//label[contains(text(), 'Last name')]"));
        search_input = search_form.findElement(By.xpath("//input[@id='lastName']"));
        submit_button = search_form.findElement(By.xpath("//button[@type='submit']"));
    }

    private String EXPECTED_PATH = "/petclinic/owners/find.html";

    @FindBy(xpath = "//h2[contains(text(),'Find Owners')]")
    private WebElement header;

    @FindBy(id = "search-owner-form")
    private WebElement search_form;

    @FindBy(xpath = "//a[text()='Add Owner']")
    private WebElement add_owner_link;

    private WebElement search_label;
    private WebElement search_input;
    private WebElement submit_button;

    public void searchForOwners(String lastName){
        search_input.sendKeys(lastName);
        submit_button.click();
    };

    public void clickAddOwnerLink() {
        add_owner_link.click();
    }

    public Boolean hasFindOwnersHeader() {
        return header != null;
    }

    public Boolean hasCorrectPath() {
        return getPath().equals(EXPECTED_PATH);
    }

    public Boolean hasCorrectSearchForm(){
        Boolean isSearchFormCorrect =
                search_form != null
                && search_label != null
                && search_input != null
                && submit_button != null;
        return isSearchFormCorrect;
    }

    public Boolean hasAddOwnerLink() {
        return add_owner_link != null;
    }
}
