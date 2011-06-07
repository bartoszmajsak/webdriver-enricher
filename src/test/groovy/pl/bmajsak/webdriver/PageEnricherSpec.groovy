package pl.bmajsak.webdriver

import static org.fest.assertions.Assertions.*;

import org.openqa.selenium.firefox.FirefoxDriver;

import spock.lang.*


class PageEnricherSpec extends Specification {

    @Shared def driver = new FirefoxDriver();
    
    def "Should detect jQuery on it's main page"() {
        given : "jQuery website is loaded"
            driver.get("http://jquery.com")
            def pageEnricher = new PageEnricher(driver)
            
        when : "Checks for jQuery present"
            def jQueryLoaded = pageEnricher.isJQueryLoaded()
            
        then :
            assertThat(jQueryLoaded).isTrue()
    }
    
    def "Should not detect jQuery on google"() {
        given : "Google website is loaded"
            driver.get("http://google.com")
            def pageEnricher = new PageEnricher(driver)
            
        when : "Checks for jQuery present"
            def jQueryLoaded = pageEnricher.isJQueryLoaded()
            
        then :
            assertThat(jQueryLoaded).isFalse()
    }
    
    def "Should insert jQuery on google"() {
        given : "Google website is loaded"
            driver.get("http://google.com")
            def pageEnricher = new PageEnricher(driver)
            
        when : "Enhance it with jQuery"
            pageEnricher.loadJQuery();
            
        then :
            def jQueryLoaded = pageEnricher.isJQueryLoaded()
            assertThat(jQueryLoaded).isTrue()
    }
    
    def "Close browser"() {
        setup:
            driver.quit()
    }
    
}
