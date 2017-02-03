package test;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlCaption;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import java.util.ArrayList;

/**
 *
 * @author lucas.burdell
 */
public class Student {

    private final String name;
    private final ArrayList<String> classes;
    
    private Student(String name, ArrayList<String> classes) {
        this.name = name;
        this.classes = classes;
    }
    
    public static Student pullData(String username, String password) {
        ArrayList<String> classes = new ArrayList<>();
        String name = "login failed";
        try (final WebClient webClient = new WebClient()) {
            webClient.getOptions().setJavaScriptEnabled(true);
            webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            System.getProperties().put("org.apache.commons.logging.simplelog.defaultlog", "fatal");

            HtmlPage page = webClient.getPage("https://portals.blackburn.edu/estudent/login.asp");
            //System.out.println(page.getTitleText());

            HtmlForm form = (HtmlForm) page.getElementById("frmLogin");
            //System.out.println(form.asXml());

            HtmlTextInput usernameE = form.getInputByName("txtUsername");
            HtmlPasswordInput passwordE = form.getInputByName("txtPassword");

            usernameE.setValueAttribute(username);
            passwordE.setValueAttribute(password);

            HtmlButtonInput button = form.getInputByName("btnLogin");
            HtmlPage page2 = button.click();
            webClient.waitForBackgroundJavaScript(3000);
            page2 = webClient.getPage("https://portals.blackburn.edu/estudent/index.asp");
            //System.out.println(page2.asXml());

            //myCourses
            HtmlCaption tableCaption = (HtmlCaption) page2.getElementById("myCourses");
            HtmlTable table = (HtmlTable) tableCaption.getEnclosingElement("table");
            
            HtmlElement nameDiv = (HtmlElement) page2.getElementById("UserName");
            name = nameDiv.getTextContent();
            
            //System.out.println(table.asXml());
            
            int i = 0;
            for (HtmlTableRow row : table.getRows()) {
                if (i == 0) {
                    i++;
                    continue;
                }
                HtmlTableCell classCell = row.getCell(0);
                System.out.println(classCell.asXml());
                HtmlElement a = (HtmlElement) classCell.getChildElements().iterator().next();
                String className = a.getTextContent();//aTags.get(0).getTextContent();
                classes.add(className.trim());
            }
            
            webClient.close();
        } catch (Exception e) {
            // an error occured, handle accordingly
        }
        
        return new Student(name, classes);
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the classes
     */
    public ArrayList<String> getClasses() {
        return classes;
    }
}
