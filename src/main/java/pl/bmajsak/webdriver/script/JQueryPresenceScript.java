package pl.bmajsak.webdriver.script;

public class JQueryPresenceScript implements Script {

    public String apply() {
        return "return (typeof jQuery === 'function')";
    }

}
