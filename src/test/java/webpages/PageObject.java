package webpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class PageObject {
    protected WebDriver driver;

    public PageObject(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    protected String getPath() {
        String url = driver.getCurrentUrl();
        String path;
        try {
            String pathWithSession = new URL(url).getPath();
            path = pathWithSession.split(";")[0];
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "Error: trying to get path from malformed url";
        }
        return path;
    }
}
