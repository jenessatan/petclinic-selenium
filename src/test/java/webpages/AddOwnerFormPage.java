package webpages;// Page URL: http://localhost:9966/petclinic/owners/new

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AddOwnerFormPage extends PageObject {
    public AddOwnerFormPage(WebDriver driver) {
        super(driver);
        initFormRelatedProperties();
    }

    private String EXPECTED_PATH = "/petclinic/owners/new";

    @FindBy(xpath = "//h2[contains(text(),'New Owner')]")
    private WebElement header;

    @FindBy(xpath = "//form[@id='add-owner-form']")
    private WebElement add_owner_form;

    @FindBy(xpath = "//a[text()='Add Owner']")
    private WebElement add_owner_link;

    //Form labels
    private WebElement first_name_label;
    private WebElement last_name_label;
    private WebElement address_label;
    private WebElement city_label;
    private WebElement telephone_label;
    //Form inputs
    private WebElement first_name_input;
    private WebElement last_name_input;
    private WebElement address_input;
    private WebElement city_input;
    private WebElement telephone_input;
    //Form button
    private WebElement add_owner_button;


    public void createNewOwner(String firstName, String lastName,
                               String address, String city, String telephone) {
        //fill in all the inputs
        first_name_input.sendKeys(firstName);
        last_name_input.sendKeys(lastName);
        address_input.sendKeys(address);
        city_input.sendKeys(city);
        telephone_input.sendKeys(telephone);

        //click add button
        add_owner_button.click();
    }

    ;


    public Boolean hasAddOwnerHeader() {
        return header != null;
    }

    public Boolean hasCorrectPath() {
        return getPath().equals(EXPECTED_PATH);
    }

    public Boolean hasCorrectSearchForm() {
        Boolean hasAddOwnerButton = add_owner_button != null;
        return hasAddOwnerButton && hasAllFormLabels() && hasAllInputFields();
    }

    public Boolean hasAddOwnerLink() {
        return add_owner_link != null;
    }


    //------------------------------- Helper methods --------------------------------//

    private void initFormRelatedProperties() {
        initFormLabelProperties();
        initFormInputProperties();
        add_owner_button = add_owner_form.findElement(
                By.xpath("//button[@type='submit' and contains(text(), 'Add Owner')]"
                ));
    }

    private void initFormInputProperties() {
        first_name_input = add_owner_form.findElement(By.id("firstName"));
        last_name_input = add_owner_form.findElement(By.id("lastName"));
        address_input = add_owner_form.findElement(By.id("address"));
        city_input = add_owner_form.findElement(By.id("city"));
        telephone_input = add_owner_form.findElement(By.id("telephone"));
    }

    private void initFormLabelProperties() {
        first_name_label = add_owner_form.findElement(By.xpath("//label[contains(text(), 'First Name')]"));
        last_name_input = add_owner_form.findElement(By.xpath("//label[contains(text(), 'Last Name')]"));
        address_label = add_owner_form.findElement(By.xpath("//label[contains(text(), 'Address')]"));
        city_label = add_owner_form.findElement(By.xpath("//label[contains(text(), 'City')]"));
        telephone_label = add_owner_form.findElement(By.xpath("//label[contains(text(), 'Telephone')]"));
    }

    private Boolean hasAllFormLabels() {
        Boolean hasAllLabels =
                first_name_label != null
                        && last_name_label != null
                        && address_label != null
                        && city_label != null
                        && telephone_label != null;
        return hasAllLabels;
    }

    private Boolean hasAllInputFields() {
        Boolean hasAllInputFields =
                first_name_input != null
                        && last_name_input != null
                        && address_input != null
                        && city_input != null
                        && telephone_input != null;
        return hasAllInputFields;
    }
}
