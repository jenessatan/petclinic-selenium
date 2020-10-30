package tests;

import models.Owner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import webpages.*;

import java.util.concurrent.TimeUnit;

public class FindOwnersPageTest {
    private static final WebDriver driver = new ChromeDriver();
    private static NavBar navBar;
    private static FindOwnersPage findOwnersPage;

    @BeforeSuite
    public static void main(String[] args) {
        // ChromeDriver location set up in webpages.Utils class
        System.setProperty("webdriver.chrome.driver", Utils.CHROME_DRIVER_LOCATION);
    }

    @BeforeMethod
    public static void init() {
        driver.get(Utils.BASE_URL);
        navBar = new NavBar(driver);

        //Navigate to FindOwners Page
        navToFindOwnersPage();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test(enabled = true)
    public static void navigateToFindOwnersPageTest(){
        Assert.assertTrue(findOwnersPage.hasCorrectPath(),
                "Path for findOwners page is incorrect");

        Assert.assertTrue(findOwnersPage.hasFindOwnersHeader(),
                "FindOwners page is missing header");
        Assert.assertTrue(findOwnersPage.hasCorrectSearchForm(),
                "FindOwner's search form is malformed");
        Assert.assertTrue(findOwnersPage.hasAddOwnerLink(),
                "FindOwner is missing Add Owner link");
    }

    @Test(enabled = true)
    public static void searchNonExistentOwnersTest(){
        findOwnersPage.searchForOwners("NON-EXISTENT-LNAME:@12345");
        WebElement hasNotBeenFound = driver.findElement(By.id("owner.errors"));
        Assert.assertTrue(hasNotBeenFound != null,
                "'has not been found' message doesn't appear after searching for nonExistent owners");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test(enabled = true)
    public static void searchExistentOwnersTest(){
        Owner owner1 = new Owner();

        //add a single owner and nav back to findOwners page
        addOwner(owner1);
        navToFindOwnersPage();
        //search for newly created owner
        findOwnersPage.searchForOwners(owner1.getLastName());

        OwnerInfoPage ownerInfoPage = new OwnerInfoPage(driver);

        Assert.assertTrue(ownerInfoPage.hasCorrectOwnerInfo(owner1),
                "OwnerInfoPage doesn't display newly added owner's info");

        navToFindOwnersPage();

        //add another owner with same last name
        Owner owner2 = new Owner();
        owner2.setLastName(owner1.getLastName());
        addOwner(owner2);
        navToFindOwnersPage();

        //search for newly created owner: expect list of owners (at least 2)
        findOwnersPage.searchForOwners(owner2.getLastName());
        OwnersListingPage ownersListingPage = new OwnersListingPage(driver);

        Assert.assertTrue(ownersListingPage.getTotalNumOwnersInList() > 1 ,
                "Number of ownersListingPage is incorrect");
        Assert.assertTrue(ownersListingPage.isOwnerInList(owner2),
                "Newly added owner is not found in OwnersListingPage");
    }

    @Test(enabled = true)
    public static void searchAllOwners() {
        //data doesn't get wiped after each test,
        //so we assume we have at least two owners in existence now

        findOwnersPage.searchForOwners("");
        OwnersListingPage ownersListingPage = new OwnersListingPage(driver);
        Assert.assertTrue(ownersListingPage.getTotalNumOwnersInList() > 0,
                "Searching empty string should result in all users in database/storage");
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

    @AfterSuite(alwaysRun = true)
    public static void cleanUp(){
        driver.manage().deleteAllCookies();
        driver.close();
    }
}
