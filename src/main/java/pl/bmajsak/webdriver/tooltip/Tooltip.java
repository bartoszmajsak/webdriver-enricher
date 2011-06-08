package pl.bmajsak.webdriver.tooltip;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import pl.bmajsak.webdriver.PageEnricher;
import pl.bmajsak.webdriver.WebDriverWrapper;
import pl.bmajsak.webdriver.script.TooltipPresenceScript;

import com.google.common.base.Preconditions;

public class Tooltip {

    private final EventFiringWebDriver driver;

    private final PageEnricher pageEnricher;
    
    public Tooltip(WebDriver drv) {
        driver = WebDriverWrapper.castOrWrap(drv);
        pageEnricher = new PageEnricher(driver);
    }


    public void show(String title, String message) {
        loadTooltip();
        TooltipEnricher tooltipEnricher = new TooltipEnricher(driver);
        tooltipEnricher.show(title, message);
        tooltipEnricher.dispose();
    }

    void loadTooltip() {
        if (!pageEnricher.isJQueryLoaded()) {
            pageEnricher.loadJQuery();
        }
        
        if (!isTooltipLoaded()) {
            pageEnricher.loadScript("jquery/jquery.simpledialog.0.1.min.js");
            pageEnricher.loadCss("jquery/jquery.simpledialog.0.1.css");
        }
    }
    
    boolean isTooltipLoaded() {
        if (!pageEnricher.isJQueryLoaded()) {
            return false;
        }
        Object result = driver.executeScript(new TooltipPresenceScript().apply());
        Preconditions.checkArgument((result instanceof Boolean),
        "Expected return type from javascript call should be Boolean.");
        return ((Boolean) result).booleanValue();
    }
    
    boolean isTooltipPresent() {
        boolean tooltipFound = true;
        try {
            driver.findElement(By.id(TooltipEnricher.ID));
        } catch (NoSuchElementException e) {
            tooltipFound = false;
        }
        return tooltipFound;
    }
    
}
