package pl.bmajsak.webdriver.script;

public class CssClassPresenceScript implements Script {
    
    private static final String CSS_CLASS_LOOKUP_FUNCTION = "document.cssClassLookup = function(cssClass) {" +
    "   for(i = 0; i < document.styleSheets.length; i++) {" +
    "       currentSheet = document.styleSheets[i];" +
    "       for(j = 0; j < currentSheet.cssRules.length; j++) {" +
    "           className = currentSheet.cssRules[j].selectorText;" +
    "           if (className === cssClass)  {" +
    "               return true;" +
    "           }" +
    "       }" +
    "   }" +
    "   return false;" +
    "};";
    
    private static final String CSS_CLASS_LOOKUP_CALL = "return document.cssClassLookup('%s');";

    private final String className;
    
    public CssClassPresenceScript(String className) {
        this.className = className;
    }

    public String apply() {
        return CSS_CLASS_LOOKUP_FUNCTION;
    }
    
    public String call() {
       return String.format(CSS_CLASS_LOOKUP_CALL, className);
    }
    
}
