package pl.bmajsak.webdriver.script;

public class CssAppenderScript implements Script {
    
    private static final String ADD_CSS_TO_HEAD =  "$('<style>%s</style>').attr('type', 'text/css').appendTo('head')";    

    private final String stylesheetContent;
    
    public CssAppenderScript(String stylesheetContent) {
        this.stylesheetContent = stylesheetContent.replace("'", "\\'")
                                                  .replaceAll("\\n","")
                                                  .replaceAll("\\t","");
    }

    public String apply() {
        return String.format(ADD_CSS_TO_HEAD, stylesheetContent);
    }

}
