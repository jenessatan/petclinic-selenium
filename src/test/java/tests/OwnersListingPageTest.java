package tests;

import models.Owner;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import webpages.*;

import java.util.concurrent.TimeUnit;

public class OwnersListingPageTest {
    private static final WebDriver driver = new ChromeDriver();

    private static NavBar navBar;
    private static FindOwnersPage findOwnersPage;
    private static OwnersListingPage ownersListingPage;

    private static Owner owner1;
    private static Owner owner2;

    @BeforeSuite
    public static void main(String[] args) {
        // ChromeDriver location set up in webpages.Utils class
        System.setProperty("webdriver.chrome.driver", Utils.CHROME_DRIVER_LOCATION);
    }

    @BeforeTest
    public static void ensureTwoPlusOwnersExist() {
        driver.get(Utils.BASE_URL);
        navBar = new NavBar(driver);

        owner1 = new Owner();
        owner2 = new Owner();

        navToFindOwnersPage();
        addOwner(owner1);
        navToFindOwnersPage();
        addOwner(owner2);

        navToOwnersListingPage();
    }

    @BeforeMethod
    public static void init() {
        driver.get(Utils.BASE_URL);
        navBar = new NavBar(driver);

        //Navigate to OwnersListing Page
        navToOwnersListingPage();
    }

    @Test(enabled = true)
    public static void navigateToOwnersListingPageTest() {
        navToOwnersListingPage();
        Assert.assertTrue(ownersListingPage.hasHeader(),
                "OwnersListingPage is missing header");
        Assert.assertTrue(ownersListingPage.hasCorrectSearchFilter(),
                "Search Filter element is malformed");
        Assert.assertTrue(ownersListingPage.hasCorrectOwnersTable(),
                "Table has less than 2 rows. Should not be on ownersListingPage");
    }

    @Test(enabled = true)
    public static void searchFilterNonFoundTest() {
        String nonexistent_name = "NON-EXISTENT-LNAME:@12345";
        ownersListingPage.search(nonexistent_name);

    }

    // ------------------------------ HELPERS ---------------------------------- //

    private static void addOwner(Owner owner){
        findOwnersPage.clickAddOwnerLink();
        AddOwnerFormPage addOwnerFormPage = new AddOwnerFormPage(driver);

        addOwnerFormPage.createNewOwner(owner.getFirstName(), owner.getLastName(),
                owner.getAddress(), owner.getCity(), owner.getTelephone());
    }

    private static void navToFindOwnersPage() {
        navBar.pressFindOwnersButton();
        findOwnersPage = new FindOwnersPage(driver);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    private static void navToOwnersListingPage() {
        navToFindOwnersPage();
        findOwnersPage.searchForOwners("");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        ownersListingPage = new OwnersListingPage(driver);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @AfterSuite(alwaysRun = true)
    public static void cleanUp(){
        driver.manage().deleteAllCookies();
        driver.close();
    }
}
