package gui;

import test.Test;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import student.Student;

/**
 * FXML Controller class
 *
 * @author lucas.burdell
 */
public class PasswordAskerController implements Initializable {

    @FXML
    private PasswordField password;
    @FXML
    private Button submitPass;
    @FXML
    private TextField username;

    private Student student;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void doLoginThing(ActionEvent event) {
        student = Student.pullData(username.getText(), password.getText());
        StudentAppProject.getAppController().loadMain(student);
    }

}
