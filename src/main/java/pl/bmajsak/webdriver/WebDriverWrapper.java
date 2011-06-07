package pl.bmajsak.webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

public final class WebDriverWrapper {

    public static EventFiringWebDriver castOrWrap(WebDriver drv) {
        EventFiringWebDriver eventFiringWebDriver = null;
        if (!(drv instanceof EventFiringWebDriver)) {
            eventFiringWebDriver = new EventFiringWebDriver(drv);
        } else {
            eventFiringWebDriver = (EventFiringWebDriver) drv;
        }
        
        return eventFiringWebDriver;
    }
    
}
