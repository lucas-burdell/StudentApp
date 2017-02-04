package student;

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
import java.util.logging.Level;
import org.apache.commons.logging.LogFactory;

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

    /** This method tells HtmlUnit to not log CSS syntax warnings and JavaScript errors.
     * The warnings and error messages resulted in a lot of extraneous output
     * that made debugging difficult.
     * 
     * @param webClient 
     */
    private static void squelchErrorLogger(WebClient webClient) {
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);
    }

    /** This method pulls data from the student portal using the provided username 
     * and password. Username and Password are used to fill in the HTML login form.
     * 
     * @param username the Username
     * @param password the Password
     * @return 
     */
    public static Student pullData(String username, String password) {
        
        System.out.println("Pulling");
        ArrayList<String> classes = new ArrayList<>();
        String name = null;
        
        try (final WebClient webClient = new WebClient()) {
            // tell HtmlUnit to shut up
            squelchErrorLogger(webClient);
            
            System.out.println("web client created");
            webClient.getOptions().setJavaScriptEnabled(true);
            webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
            webClient.getOptions().setThrowExceptionOnScriptError(false);
           // System.getProperties().put("org.apache.commons.logging.simplelog.defaultlog", "fatal");
            
            System.out.println("page loading");
            HtmlPage page = webClient.getPage("https://portals.blackburn.edu/estudent/login.asp");
            //System.out.println(page.getTitleText());

            
            System.out.println("page loaded");
            
            HtmlForm form = (HtmlForm) page.getElementById("frmLogin");
            //System.out.println(form.asXml());

            HtmlTextInput usernameE = form.getInputByName("txtUsername");
            HtmlPasswordInput passwordE = form.getInputByName("txtPassword");

            
            // Fill in forms
            usernameE.setValueAttribute(username);
            passwordE.setValueAttribute(password);

            // Submit form and wait for the JavaScript animation to finish 
            //before loading the portal view.
            HtmlButtonInput button = form.getInputByName("btnLogin");
            HtmlPage page2 = button.click();
            System.out.println("waiting for javascript");
            webClient.waitForBackgroundJavaScript(3000);
            
            System.out.println("retrieving new page");
            page2 = webClient.getPage("https://portals.blackburn.edu/estudent/index.asp");
            
            //System.out.println(page2.asXml());

            // Retrieve the MyCourses table from the home page of the student portal.
            HtmlCaption tableCaption = (HtmlCaption) page2.getElementById("myCourses");
            HtmlTable table = (HtmlTable) tableCaption.getEnclosingElement("table");

            // Retrieve the name of the student from the student portal 
            // (differs from username in that it has no period.)
            HtmlElement nameDiv = (HtmlElement) page2.getElementById("UserName");
            name = nameDiv.getTextContent();

           
            
            // iterate over rows of the courses table, skipping the first row 
            // because its just filled with the labels.
            int i = 0;
            for (HtmlTableRow row : table.getRows()) {
                if (i == 0) {
                    i++;
                    continue;
                }
                HtmlTableCell classCell = row.getCell(0);
                HtmlElement classNameTag = (HtmlElement) classCell.getChildElements().iterator().next();
                String className = classNameTag.getTextContent();
                classes.add(className.trim());
            }

            // close the student portal window and shut down the web client.
            webClient.close();
        } catch (Exception e) {
            // an error occured, handle accordingly
        }

        // create a new Student object using the data pulled from the site.
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
