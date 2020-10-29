import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class TestPlan {
    private static final WebDriver driver = new ChromeDriver();

    @BeforeSuite
    public static void main(String[] args) {
        // ChromeDriver location set up in Utils class
        System.setProperty("webdriver.chrome.driver", Utils.CHROME_DRIVER_LOCATION);
    }

    @Test(enabled = true)
    public static void navigateHome(){
        System.out.println("nav home test");
        driver.get(Utils.BASE_URL);
        NavBar navbar = new NavBar(driver);
        navbar.pressErrorButton();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        navbar.pressHomeButton();
        Homepage homepage = new Homepage(driver);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        homepage.hasWelcomeHeader();
    }

    @AfterSuite(alwaysRun = true)
    public static void cleanUp(){
        driver.manage().deleteAllCookies();
        driver.close();
    }
}
