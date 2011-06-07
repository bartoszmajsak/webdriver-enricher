package pl.bmajsak.webdriver.tooltip;

import org.openqa.selenium.support.events.EventFiringWebDriver;

import pl.bmajsak.webdriver.script.CreateTooltipScript;

public class Tooltip {

    public static final String ID = "jqueryWebDriverTooltipLink";
    
    public static final String DIV_ID = "jqueryWebDriverTooltip";
    
    private static final Long DEFAULT_TIME_SECONDS = 3000L;

    private final EventFiringWebDriver driver;
    
    public Tooltip(EventFiringWebDriver driver) {
        this.driver = driver;
    }

    public void show(String title, String message) {
        show(title, message, DEFAULT_TIME_SECONDS);
    }
    
    public void show(String title, String message, long tooltipOnScreenInSeconds) {
        driver.executeScript(new CreateTooltipScript(title, message).apply());
        driver.executeScript(String.format("$('#%s').simpleDialog();", Tooltip.ID));
        driver.executeScript(String.format("$('#%s').click();", Tooltip.ID));
        showFor(tooltipOnScreenInSeconds);
    }

    private void showFor(long seconds) {
        try {
            Thread.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void dispose() {
        driver.executeScript("$.simpleDialog.close();");
        driver.executeScript(String.format("$('#%s').remove();", Tooltip.ID));
        driver.executeScript(String.format("$('#%s').remove();", Tooltip.DIV_ID));
    }


}
