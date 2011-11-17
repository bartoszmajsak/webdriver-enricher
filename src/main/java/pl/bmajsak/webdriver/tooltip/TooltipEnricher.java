package pl.bmajsak.webdriver.tooltip;

import org.openqa.selenium.support.events.EventFiringWebDriver;

import pl.bmajsak.webdriver.script.CreateTooltipScript;

public class TooltipEnricher {

    public static final String ID = "jqueryWebDriverTooltipLink";

    public static final String DIV_ID = "jqueryWebDriverTooltip";

    private static final Long DEFAULT_TIME_SECONDS = 5000L;

    private static final Long SECOND = 1000L;

    private final EventFiringWebDriver driver;

    public TooltipEnricher(EventFiringWebDriver driver) {
        this.driver = driver;
    }

    public void show(String title, String message) {
        show(title, message, DEFAULT_TIME_SECONDS);
    }

    public void show(String title, String message, long tooltipOnScreenInSeconds) {
        waitFor(SECOND);
        driver.executeScript(new CreateTooltipScript(title, message).apply());
        driver.executeScript(String.format("$('#%s').simpleDialog();", TooltipEnricher.ID));
        driver.executeScript(String.format("$('#%s').click();", TooltipEnricher.ID));
        waitFor(tooltipOnScreenInSeconds);
    }

    private void waitFor(long seconds) {
        try {
            Thread.sleep(seconds);
        } catch (InterruptedException e) {
           throw new RuntimeException(e);
        }
    }

    public void dispose() {
        driver.executeScript("$.simpleDialog.close();");
        driver.executeScript(String.format("$('#%s').remove();", TooltipEnricher.ID));
        driver.executeScript(String.format("$('#%s').remove();", TooltipEnricher.DIV_ID));
    }


}
