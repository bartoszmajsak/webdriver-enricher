package pl.bmajsak.webdriver.script;

import pl.bmajsak.webdriver.tooltip.TooltipEnricher;

public class CreateTooltipScript implements Script {

    private static final String DIALOG_ELEMENT_CREATION = 
        "$('<div></div>').attr('id', '%1$s').css('display', 'none').appendTo('body');" +
        "$('<h3>%2$s</h3>').css('padding', '0.5em 2em 0em 2em').appendTo('#%1$s');" +
        "$('<p>%3$s</p>').css('max-width', '400px').css('padding', '0em 2em 1em 2em').appendTo('#%1$s');" +
        "$('<a></a>').attr('href', '#').attr('id', '%4$s').attr('rel', '%1$s').appendTo('body');";
    
    private final String title;
    
    private final String message;
    
    public CreateTooltipScript(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public String apply() {
        return String.format(DIALOG_ELEMENT_CREATION, TooltipEnricher.DIV_ID, title, message, TooltipEnricher.ID);
    }
    
}
