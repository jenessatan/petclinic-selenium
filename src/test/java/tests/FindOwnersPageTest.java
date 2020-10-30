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
        navBar.pressFindOwnersButton();
        findOwnersPage = new FindOwnersPage(driver);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test(enabled = true)
    public static void navigateToFindOwnersPageTest(){
        Assert.assertTrue(findOwnersPage.hasCorrectPath());

        Assert.assertTrue(findOwnersPage.hasFindOwnersHeader());
        Assert.assertTrue(findOwnersPage.hasCorrectSearchForm());
        Assert.assertTrue(findOwnersPage.hasAddOwnerLink());
    }

    @Test(enabled = true)
    public static void searchNonExistentOwnersTest(){
        findOwnersPage.searchForOwners("@12345");
        WebElement hasNotBeenFound = driver.findElement(By.id("owner.errors"));
        Assert.assertTrue(hasNotBeenFound != null);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    public static void searchExistentOwnersTest(){
        Owner owner1 = new Owner();

        //add a single owner and nav back to findOwners page
        addOwner(owner1);
        navToFindOwnersPage();
        //search for newly created owner
        findOwnersPage.searchForOwners(owner1.getLastName());

        OwnerInfoPage ownerInfoPage = new OwnerInfoPage(driver);
        Assert.assertTrue(ownerInfoPage.hasCorrectOwnerInfo(owner1));
        navToFindOwnersPage();

        //add another owner with same last name
        Owner owner2 = new Owner();
        owner2.setLastName(owner1.getLastName());
        addOwner(owner2);
        navToFindOwnersPage();

        //search for newly created owner: expect list of owners (at least 2)
    }

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
