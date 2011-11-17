package pl.bmajsak.webdriver;

import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import pl.bmajsak.webdriver.script.CssAppenderScript;
import pl.bmajsak.webdriver.script.CssClassPresenceVerificationScript;
import pl.bmajsak.webdriver.script.JQueryPresenceVerificationScript;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.io.Resources;

public class PageEnricher {

    private final EventFiringWebDriver driver;
    
    public PageEnricher(WebDriver drv) {
        driver = WebDriverWrapper.castOrWrap(drv);
    }

    public void hideMouseCursor() {
        Long screenWidth = (Long) driver.executeScript("return screen.width;");
        Long screenHeight = (Long) driver.executeScript("return screen.height;");
        int xOffset = screenWidth.intValue() / 2;
        int yOffset = screenHeight.intValue() / 2;
        new Actions(driver).moveByOffset(xOffset, yOffset).build().perform();
    }
    
    public void loadScript(String scriptFilename) {
        String script = getFileAsString(scriptFilename);
        driver.executeScript(script);
    }

    public void loadCss(String stylesheetFilename) {
        String stylesheet = getFileAsString(stylesheetFilename);
        driver.executeScript(new CssAppenderScript(stylesheet).apply());
    }
    
    public void loadJQuery() {
        if (!isJQueryLoaded()) {
            loadScript("jquery/jquery-1.6.1.min.js");
        }
    }
    
    public boolean isCssClassPresent(String className) {
        CssClassPresenceVerificationScript cssClassPresenceScript = new CssClassPresenceVerificationScript(className);
        driver.executeScript(cssClassPresenceScript.apply());
        Object result = driver.executeScript(cssClassPresenceScript.call());
        Preconditions.checkArgument((result instanceof Boolean),
        "Expected return type from javascript call should be Boolean.");
        return ((Boolean) result).booleanValue();
    }
    
    public boolean isJQueryLoaded() {
        Object result = driver.executeScript(new JQueryPresenceVerificationScript().apply());
        Preconditions.checkArgument((result instanceof Boolean),
        "Expected return type from javascript call should be Boolean.");
        return ((Boolean) result).booleanValue();
    }
    
    private String getFileAsString(String file) {
        String content = "";
        try {
            URL fileUrl = getClass().getClassLoader().getResource(file);
            content  = Resources.toString(fileUrl, Charsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return content;
    }
}
