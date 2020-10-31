package tests;

import models.Owner;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import webpages.*;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class OwnersListingPageTest {
    private static final WebDriver driver = new ChromeDriver();

    private static NavBar navBar;
    private static FindOwnersPage findOwnersPage;
    private static OwnersListingPage ownersListingPage;

    private static Owner owner1;
    private static Owner owner2;
    private static Owner owner3;

//    private static int numFilesInDownloads;

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
        owner3 = new Owner();
        owner3.setLastName(owner1.getLastName());

        navToFindOwnersPage();
        addOwner(owner1);
        navToFindOwnersPage();
        addOwner(owner2);
        navToFindOwnersPage();
        addOwner(owner3);

        navToOwnersListingPage();

//        numFilesInDownloads = getNumContentsInDownloads("C:\\Users\\Jackie\\Downloads");
    }

    @BeforeMethod
    public static void init() {
        driver.get(Utils.BASE_URL);
        navBar = new NavBar(driver);

        //Navigate to OwnersListing Page
        navToOwnersListingPage();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test(enabled = true)
    public static void navigateToOwnersListingPageTest() {
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
        Assert.assertTrue(ownersListingPage.hasNoMatch(),
                "'No matching records found' should be displayed when no rows match filter");
        ownersListingPage.clearSearch();
    }

    @Test(enabled = true)
    public static void searchFilterByLastNameTest() {
        // owner1 and owner3 have same last name
        ownersListingPage.search(owner1.getLastName());

        Assert.assertFalse(ownersListingPage.hasNoMatch());
        Assert.assertTrue(ownersListingPage.isOwnerInList(owner1));
        Assert.assertTrue(ownersListingPage.isOwnerInList(owner3));
        ownersListingPage.clearSearch();
    }

    @Test(enabled = true)
    public static void searchFilterByAddressTest() {
        ownersListingPage.search(owner1.getAddress());

        Assert.assertFalse(ownersListingPage.hasNoMatch());
        Assert.assertTrue(ownersListingPage.isOwnerInList(owner1));

        ownersListingPage.clearSearch();
    }

    @Test(enabled = true)
    public static void searchFilterByFirstNameTest() {
        ownersListingPage.search(owner2.getFirstName());

        Assert.assertFalse(ownersListingPage.hasNoMatch());
        Assert.assertTrue(ownersListingPage.isOwnerInList(owner2));

        ownersListingPage.clearSearch();
    }

    @Test(enabled = true)
    public static void searchFilterByCityTest() {
        ownersListingPage.search(owner3.getCity());

        Assert.assertFalse(ownersListingPage.hasNoMatch());
        Assert.assertTrue(ownersListingPage.isOwnerInList(owner3));

        ownersListingPage.clearSearch();
    }

    @Test(enabled = true)
    public static void searchFilterByTelephoneTest() {
        ownersListingPage.search(owner3.getTelephone());

        Assert.assertFalse(ownersListingPage.hasNoMatch());
        Assert.assertTrue(ownersListingPage.isOwnerInList(owner3));

        ownersListingPage.clearSearch();
    }

    @Test(enabled = true)
    public static void searchFilterTwice() {
        //first search
        ownersListingPage.search(owner1.getFirstName());

        Assert.assertFalse(ownersListingPage.hasNoMatch());
        Assert.assertTrue(ownersListingPage.isOwnerInList(owner1));

        ownersListingPage.clearSearch();

        //second search
        ownersListingPage.search(owner2.getTelephone());

        Assert.assertFalse(ownersListingPage.hasNoMatch());
        Assert.assertTrue(ownersListingPage.isOwnerInList(owner2));

        ownersListingPage.clearSearch();
    }

    /**
     * Test a WIP, because it's complicated to automate the verification of downloads
     *
     * From Selenium docs:
     * "Whilst it is possible to start a download by clicking a link with a browser under Selenium’s control,
     * the API does not expose download progress, making it less than ideal for testing downloaded files.
     * This is because downloading files is not considered an important aspect of
     * emulating user interaction with the web platform."
     *
     * Link: https://www.selenium.dev/documentation/en/worst_practices/file_downloads/
     * */
//    @Test(enabled = false)
//    public static void pdfExportTest() {
//        String downloadPath = "C:\\downloads";
//        Boolean fileExists = false;
//
//        ChromeOptions chromeOptions = new ChromeOptions();
//        chromeOptions.addArguments()
//
////        ownersListingPage.clickPDFButton();
//        System.out.println(numFilesInDownloads);
//        ownersListingPage.clickPDFButton();
//        int test = getNumContentsInDownloads("C:\\Users\\Jackie\\Downloads");
//
//        WebDriverWait wait = new WebDriverWait(driver, 60);
//        wait.until(null);
//        System.out.println(test);
//    }

    // ------------------------------ HELPERS ---------------------------------- //

    private static int getNumContentsInDownloads(String downloadPath) {
        File dir = new File(downloadPath);
        File[] dir_contents = dir.listFiles();

        return dir_contents.length;
    }

    private Boolean isFileDownloaded(String downloadPath, String fileName, String ext) {
        Boolean isFileDownloaded = false;

        File dir = new File(downloadPath);
        File[] dir_contents = dir.listFiles();

        for (int i = 0; i < dir_contents.length; i++) {
            if (dir_contents[i].getName().contains(fileName))
                isFileDownloaded=true;
            dir_contents[i].delete();
        }

        return isFileDownloaded;
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
