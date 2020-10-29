import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

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
            path = new URI(url).getPath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return "Error: trying to get path from malformed url";
        }
        return path;
    }
}
