package pl.bmajsak.webdriver.script;

public class TooltipPresenceVerificationScript implements Script {

    public String apply() {
        return "return (typeof jQuery.fn.simpleDialog === 'function')";
    }

}
