/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.gargoylesoftware.htmlunit.AlertHandler;
import com.gargoylesoftware.htmlunit.ConfirmHandler;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlCaption;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableBody;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import java.util.ArrayList;

/**
 *
 * @author lucas.burdell
 */
public class Test {

    public static void tryLogin(String username, String password) throws Exception {
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
            webClient.waitForBackgroundJavaScript(2000);
            page2 = webClient.getPage("https://portals.blackburn.edu/estudent/index.asp");
            //System.out.println(page2.asXml());
            
            //myCourses
            HtmlCaption tableCaption = (HtmlCaption) page2.getElementById("myCourses");
            HtmlTable table = (HtmlTable) tableCaption.getEnclosingElement("table");
            ArrayList<String> classes = new ArrayList<>();
            //System.out.println(table.asXml());
            System.out.println("About to print cells");
            synchronized (Test.class) {
                Test.class.wait(1000);                
            }

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
            for (String s : classes) {
                System.out.println(s);
            }
            
            webClient.close();

        }
    }
}
