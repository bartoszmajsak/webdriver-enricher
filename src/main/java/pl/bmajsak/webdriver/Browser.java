package pl.bmajsak.webdriver;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public final class Browser {

    private static final String EMPTYPAGE = "http://emptypage.org/";
    
    private final WebDriver driver;
    
    public Browser(WebDriver driver) {
        this.driver = driver;
        this.driver.get(EMPTYPAGE); // needs to have some DOM to manipulate
    }

    public void fullscreen() {
        new Actions(driver).sendKeys(Keys.F11)
                           .build()
                           .perform();
    }
    
}
