package webpages;// Page URL: http://localhost:9966/petclinic/owners.html?lastName={search value}

import models.Owner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class OwnersListingPage extends PageObject {

    public OwnersListingPage(WebDriver driver) {
        super(driver);
        initSearchFilterElemFields();
        initTableElemFields();
    }

    //header
    @FindBy(xpath = "//h2[contains(text(), 'Owners')]")
    private WebElement header;

    //search filter
    @FindBy(xpath = "//div[@id='owners_filter']")
    private WebElement search_filter_div;
    //filter elements - initiate using search_filter_div
    private String search_label;
    private WebElement search_input;
    //flag to track whether filter has an input
    private Boolean has_filter_input = false;

    //PDF export button
    @FindBy(xpath = "//a[@class='btn' and contains(text(), 'PDF')]")
    private WebElement pdf_button;

    //Owners Table
    @FindBy(id = "owners")
    private WebElement owners_table;

    /** table elements: initiate with owners_table */
    //Table Header elements: Column headers
    private WebElement name_columnHeader;
    private WebElement address_columnHeader;
    private WebElement city_columnHeader;
    private WebElement telephone_columnHeader;
    private WebElement pets_columnHeader;
    //Table Rows: with Owners
    private WebElement[] original_table_rows;
    private WebElement[] filtered_table_rows;
    private WebElement no_matching_elems;


    public int getTotalNumOwnersInList() {
        return original_table_rows.length;
    }

    public void search(String searchString) {
        search_input.sendKeys(searchString);
        has_filter_input = true;
        initTableRows(false);
    }

    public void clearSearch() {
        search_input.clear();
        has_filter_input = false;
        initTableRows(false);
    }

    public Boolean hasHeader() {
        return header != null;
    }

    public Boolean hasCorrectSearchFilter() {
        return search_filter_div != null
                && search_label != null
                && search_label.contains("Search")
                && search_input != null;
    }

    public Boolean hasCorrectOwnersTable() {
        return hasCorrectColumnHeaders()
                && hasCorrectTableLength();
    }

    public Boolean isOwnerInList(Owner owner) {
        Boolean isInList = false;
        WebElement[] tds;

        for (WebElement tr: original_table_rows) {
            tds = tr.findElements(By.xpath("child::td")).toArray(new WebElement[0]);
            if (tds.length == 5) {
                isInList = isInList || isRowOwner(owner, tds);
            } else {
                return isInList;
            }
        }
        return isInList;
    }

    private Boolean isRowOwner(Owner owner, WebElement[] tds) {
        WebElement td_name = tds[0];
        WebElement td_address = tds[1];
        WebElement td_city = tds[2];
        WebElement td_telephone = tds[3];
        WebElement td_pets = tds[4];

        String[] first_and_last_name = td_name.getText().split(" ");
        String first_name = first_and_last_name[0];
        String last_name = first_and_last_name[1];

        Owner owner_in_list = new Owner(first_name, last_name,
                td_address.getText(), td_city.getText(), td_telephone.getText(), null);

        Boolean owner_matches_row_owner = owner_in_list.hasSameInfo(owner);
        return owner_matches_row_owner;
    }

    // ---------------------------------- HELPERS --------------------------- //

    private void initSearchFilterElemFields() {
        search_label = search_filter_div.getText();
        search_input = search_filter_div.findElement(By.xpath("//label/child::input"));
    }


    private void initTableElemFields() {
        initTableHeaders();
        initTableRows(true);
    }

    private void initTableRows(Boolean isFirstInit) {
        List <WebElement> tableRows_list = owners_table.findElements(By.xpath("child::tbody/child::tr"));
        WebElement[] table_rows = tableRows_list.toArray(new WebElement[0]);

        if (isFirstInit != null && isFirstInit) {
            original_table_rows = table_rows;
            filtered_table_rows = table_rows;
        } else {
            filtered_table_rows = table_rows;
        }
    }

    private void initTableHeaders() {
        name_columnHeader = owners_table.findElement(By.xpath("//th[contains(text(), 'Name')]"));
        address_columnHeader = owners_table.findElement(By.xpath("//th[contains(text(), 'Address')]"));
        city_columnHeader = owners_table.findElement(By.xpath("//th[contains(text(), 'City')]"));
        telephone_columnHeader = owners_table.findElement(By.xpath("//th[contains(text(), 'Telephone')]"));
        pets_columnHeader = owners_table.findElement(By.xpath("//th[contains(text(), 'Pets')]"));
    }

    private Boolean hasCorrectColumnHeaders() {
        return name_columnHeader != null
                && address_columnHeader != null
                && city_columnHeader != null
                && telephone_columnHeader != null
                && pets_columnHeader != null;
    }

    private Boolean hasCorrectTableLength() {
        Boolean isCorrectLength = original_table_rows.length >= 2;
        if (has_filter_input) {
            return isCorrectLength && filtered_table_rows.length > 0;
        } return isCorrectLength && filtered_table_rows.length > 2;
    }
}
