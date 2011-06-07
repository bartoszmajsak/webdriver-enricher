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
    
    private String getFileAsString(String file) {
        String content = "";
        try {
            URL jqueryUrl = getClass().getResource(file);
            File jquery = new File(jqueryUrl.toURI());
            content  = Files.toString(jquery, Charsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return content;
    }

    void loadJQuery() {
        loadScript("jquery-1.6.1.min.js");
    }

    boolean isCssClassPresent(String className) {
        CssClassPresenceScript cssClassPresenceScript = new CssClassPresenceScript(className);
        driver.executeScript(cssClassPresenceScript.apply());
        Object result = driver.executeScript(cssClassPresenceScript.call());
        Preconditions.checkArgument((result instanceof Boolean),
        "Expected return type from javascript call should be Boolean.");
        return ((Boolean) result).booleanValue();
    }

    boolean isJQueryLoaded() {
        Object result = driver.executeScript(new JQueryPresenceScript().apply());
        Preconditions.checkArgument((result instanceof Boolean),
        "Expected return type from javascript call should be Boolean.");
        return ((Boolean) result).booleanValue();
    }
}
