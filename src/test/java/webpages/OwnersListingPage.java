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
        initTableElemFields();
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

    //PDF export button
    @FindBy(xpath = "//a[@class='btn' and contains(text(), 'PDF')]")
    private WebElement pdf_button;

    //Owners Table
    @FindBy(id = "owners")
    private WebElement owners_table;

    /** table elements: initiate with owners_table */
    //Table Header elements: Row headers
    private WebElement name_rowHeader;
    private WebElement address_rowHeader;
    private WebElement city_rowHeader;
    private WebElement telephone_rowHeader;
    private WebElement pets_rowHeader;
    //Table Rows
    private WebElement[] tableRows;

    public int getNumOwnersInList() {
        return tableRows.length;
    }

    public Boolean isOwnerInList(Owner owner) {
        Boolean isInList = false;
        WebElement[] tds;

        for (WebElement tr: tableRows) {
            tds = tr.findElements(By.xpath("child::td")).toArray(new WebElement[0]);
            System.out.println(tds.length);
            if (tds.length == 5) {
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

                isInList = isInList || owner_in_list.hasSameInfo(owner);
            } else {
                System.out.println("tds not equal five");
                return isInList;
            }
        }
        return isInList;
    }


    private void initTableElemFields() {
        initTableHeaders();
        initTableRows();
    }

    private void initTableRows() {
        List <WebElement> tableRows_list = owners_table.findElements(By.xpath("child::tbody/child::tr"));
        tableRows = tableRows_list.toArray(new WebElement[0]);
    }

    private void initTableHeaders() {
        name_rowHeader = owners_table.findElement(By.xpath("//th[contains(text(), 'Name')]"));
        address_rowHeader = owners_table.findElement(By.xpath("//th[contains(text(), 'Address')]"));
        city_rowHeader = owners_table.findElement(By.xpath("//th[contains(text(), 'City')]"));
        telephone_rowHeader = owners_table.findElement(By.xpath("//th[contains(text(), 'Telephone')]"));
        pets_rowHeader = owners_table.findElement(By.xpath("//th[contains(text(), 'Pets')]"));
    }


}
