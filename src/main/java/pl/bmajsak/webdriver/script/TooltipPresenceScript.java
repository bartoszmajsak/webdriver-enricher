package pl.bmajsak.webdriver.script;

public class TooltipPresenceScript implements Script {

    public String apply() {
        return "return (typeof jQuery.fn.simpleDialog === 'function')";
    }

}
