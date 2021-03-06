package pl.bmajsak.webdriver.tooltip

import static org.fest.assertions.Assertions.*;

import org.openqa.selenium.firefox.FirefoxDriver;

import pl.bmajsak.webdriver.Browser
import pl.bmajsak.webdriver.PageEnricher
import pl.bmajsak.webdriver.tooltip.Tooltip;

import spock.lang.*


class TooltipSpec extends Specification {

    @Shared driver = new FirefoxDriver();
    
    def setupSpec() {
        def browser = new Browser(driver);
        browser.fullscreen();
    }
    
    def cleanupSpec() {
        driver.quit()
    }
    
    def "Should not detect tooltip on google"() {
        
        given : "Google website is loaded"
            def tooltip = new Tooltip(driver)
            driver.get("http://google.com")
            
        when : "Checks for tooltip script present"
            def tooltipLoaded = tooltip.isTooltipLoaded()
            
        then :
            assertThat(tooltipLoaded).isFalse()
    }
    
    def "Should not detect tooltip on jQuery project page"() {
        
        given : "Google website is loaded"
            def tooltip = new Tooltip(driver)
            driver.get("http://jquery.com")
            
        when : "Checks for tooltip script present"
            def tooltipLoaded = tooltip.isTooltipLoaded()
            
        then :
            assertThat(tooltipLoaded).isFalse()
    }
    
    def "Should detect tooltip on it's demo page"() {
        
        given : "Tooltip demo website is loaded"
            def tooltip = new Tooltip(driver)
            driver.get("http://www.mudaimemo.com/p/simpledialog/")
            
        when : "Checks for tooltip script present"
            def tooltipLoaded = tooltip.isTooltipLoaded()
            
        then :
            assertThat(tooltipLoaded).isTrue()
    }
    
    def "Should insert tooltip on google page"() {
        
        given : "Google website is loaded"
            def tooltip = new Tooltip(driver)
            driver.get("http://google.com")
            
        when : "Extend it with jQuery and tooltip"
            tooltip.loadTooltip()
            
        then :
            assertThat(tooltip.isTooltipLoaded()).isTrue()
    }
    
    def "Should have tooltip stylesheet attached together with script"() {
        given : "Google website is loaded"
            def tooltip = new Tooltip(driver)
            def pageEnricher = new PageEnricher(driver);
            driver.get("http://google.com")
            
        when : "Extend it with jQuery and tooltip"
            tooltip.loadTooltip()
            
        then :
            assertThat(pageEnricher.isCssClassPresent(".sd_content")).isTrue()
    }
    
    def "Should show tooltip when visiting Google website"() {
        given : "Google website is loaded"
            def tooltip = new Tooltip(driver)
            def pageEnricher = new PageEnricher(driver);
            driver.get("http://google.com")
        
       when : "Extend it with jQuery and tooltip"
            tooltip.show("Step 1", "Should show tooltip when visiting Google website");
            tooltip.show("Step 2", "Extend it with jQuery and tooltip. Lime, also traditionally known as lime green or lime-green, is a color three-fourths of the way between yellow and green (closer to yellow than to green).");
            
        then :
            assertThat(pageEnricher.isCssClassPresent(".sd_content")).isTrue()
    }
    
}
