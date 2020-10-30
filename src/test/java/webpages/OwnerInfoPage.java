package webpages; // Page URL: http://localhost:9966/petclinic/owners/{ownerId}

import models.Owner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class OwnerInfoPage extends PageObject {
    public OwnerInfoPage(WebDriver driver) {
        super(driver);
        initTableElemFields();
    }

    private String EXPECTED_PATH_SUBSTR = "/petclinic/owners";

    //headers
    @FindBy(xpath = "//h2[contains(text(), 'Owner Information')]")
    private WebElement owner_info_header;
    @FindBy(xpath = "//h2[contains(text(), 'Pets and Visits')]")
    private WebElement pet_and_visits_header;

    //table
    @FindBy(xpath = "//table[@class='table table-striped']")
    private WebElement ownerTable;

    //table elements - to be initiated using ownerTable
    private WebElement ownerTableBody;
    //table headers
    private WebElement tableHeader_name;
    private WebElement tableHeader_address;
    private WebElement tableHeader_city;
    private WebElement tableHeader_telephone;
    //table body
    private WebElement tableData_name;
    private WebElement tableData_address;
    private WebElement tableData_city;
    private WebElement tableData_telephone;
    //buttons
    private WebElement edit_owner_button;
    private WebElement add_new_pet_button;

    public Boolean hasOwnerInfoHeader() {
        return owner_info_header != null;
    }

    public Boolean hasPetsAndVisitsHeader() {
        return pet_and_visits_header != null;
    }

    public Boolean hasCorrectPath() {
        String path = getPath();
        int index = path.lastIndexOf("/");
        String[] pathParts = {path.substring(0, index), path.substring(index)};
        String pathWithoutOwnerId = pathParts[0];
        String ownerId = pathParts[1];
        Boolean test =  pathWithoutOwnerId == EXPECTED_PATH_SUBSTR && ownerId.length() > 1;
        return test;
    }

    public Boolean hasCorrectOwnerInfo(Owner owner) {
        String ownerFullName = owner.getFirstName() + " " + owner.getLastName();

        String td_name = tableData_name.getText();
        String td_address = tableData_address.getText();
        String td_city = tableData_city.getText();
        String td_telephone = tableData_telephone.getText();

        return td_name.equals(ownerFullName)
                && td_address.equals(owner.getAddress())
                && td_city.equals(owner.getCity())
                && td_telephone.equals(owner.getTelephone());
    }

    private void initTableElemFields() {
        ownerTableBody = ownerTable.findElement(By.xpath("//tbody"));
        initTableHeaders();
        initTableData();
    }

    private void initTableHeaders() {
        tableHeader_name = ownerTableBody.findElement(By.xpath("//th[text()='Name']"));
        tableHeader_address = ownerTableBody.findElement(By.xpath("//th[text()='Address']"));
        tableHeader_city = ownerTableBody.findElement(By.xpath("//th[text()='City']"));
        tableHeader_telephone = ownerTableBody.findElement(By.xpath("//th[text()='Telephone']"));
    }

    private void initTableData() {
        tableData_name = tableHeader_name.findElement(By.xpath("following-sibling::td"));
        tableData_address = tableHeader_address.findElement(By.xpath("following-sibling::td"));
        tableData_city = tableHeader_city.findElement(By.xpath("following-sibling::td"));
        tableData_telephone = tableHeader_telephone.findElement(By.xpath("following-sibling::td"));
    }
}
