package pl.bmajsak.webdriver.script;

public class JQueryPresenceVerificationScript implements Script {

    public String apply() {
        return "return (typeof jQuery === 'function')";
    }

}
