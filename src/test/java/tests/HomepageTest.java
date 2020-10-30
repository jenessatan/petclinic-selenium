package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import webpages.*;

import java.util.concurrent.TimeUnit;

public class HomepageTest {
    private static final WebDriver driver = new ChromeDriver();

    @BeforeSuite
    public static void main(String[] args) {
        // ChromeDriver location set up in webpages.Utils class
        System.setProperty("webdriver.chrome.driver", Utils.CHROME_DRIVER_LOCATION);
    }

    @Test(enabled = true)
    public static void navigateHome(){
        System.out.println("nav home test");
        driver.get(Utils.BASE_URL);
        NavBar navbar = new NavBar(driver);

        //Navigate away from webpages.Homepage
        navbar.pressErrorButton();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        //Navigate back to webpages.Homepage
        navbar.pressHomeButton();
        Homepage homepage = new Homepage(driver);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        Assert.assertTrue(homepage.hasWelcomeHeader());
        Assert.assertTrue(homepage.hasCorrectPath());
    }

    @AfterSuite(alwaysRun = true)
    public static void cleanUp(){
        driver.manage().deleteAllCookies();
        driver.close();
    }
}
