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
import com.gargoylesoftware.htmlunit.html.HtmlButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;

/**
 *
 * @author lucas.burdell
 */
public class Test {

    public static void tryLogin(String password) throws Exception {
        try (final WebClient webClient = new WebClient()) {
            webClient.getOptions().setJavaScriptEnabled(true);
            webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
            webClient.getOptions().setThrowExceptionOnScriptError(false);
      
            HtmlPage page = webClient.getPage("https://portals.blackburn.edu/estudent/login.asp");
            //System.out.println(page.getTitleText());

            HtmlForm form = (HtmlForm) page.getElementById("frmLogin");
            System.out.println(form.asXml());
            
            HtmlTextInput usernameE = form.getInputByName("txtUsername");
            HtmlPasswordInput passwordE = form.getInputByName("txtPassword");
            
            usernameE.setValueAttribute("lucas.burdell");
            passwordE.setValueAttribute(password);
            
            HtmlButtonInput button = form.getInputByName("btnLogin");
            HtmlPage page2 = button.click();
            webClient.waitForBackgroundJavaScript(6000);
            page2 = webClient.getPage("https://portals.blackburn.edu/estudent/index.asp");
            
            webClient.close();

        }
    }
}
