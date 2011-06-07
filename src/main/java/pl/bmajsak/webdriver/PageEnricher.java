package pl.bmajsak.webdriver;

import java.io.File;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import pl.bmajsak.webdriver.script.CssAppenderScript;
import pl.bmajsak.webdriver.script.CssClassPresenceScript;
import pl.bmajsak.webdriver.script.JQueryPresenceScript;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.io.Files;
import com.google.common.io.Resources;

public class PageEnricher {

    private final EventFiringWebDriver driver;
    
    public PageEnricher(WebDriver drv) {
        driver = WebDriverWrapper.castOrWrap(drv);
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
        CssClassPresenceScript cssClassPresenceScript = new CssClassPresenceScript(className);
        driver.executeScript(cssClassPresenceScript.apply());
        Object result = driver.executeScript(cssClassPresenceScript.call());
        Preconditions.checkArgument((result instanceof Boolean),
        "Expected return type from javascript call should be Boolean.");
        return ((Boolean) result).booleanValue();
    }
    
    public boolean isJQueryLoaded() {
        Object result = driver.executeScript(new JQueryPresenceScript().apply());
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
