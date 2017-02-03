package gui;

import test.Test;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
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
    @FXML
    private AnchorPane loginPane;
    @FXML
    private ProgressIndicator progress;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void doLoginThing(ActionEvent event) {
        ExecutorService pool = Executors.newFixedThreadPool(10);
        pool.submit(new Runnable(){
            @Override
            public void run() {
                Student output = Student.pullData(username.getText(), password.getText());
                StudentAppProject.getAppController().loadMain(output);
                pool.shutdown();
            }
        });
        
        ParallelTransition pt = new ParallelTransition();
        
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), loginPane);
        fadeIn.setFromValue(1);
        fadeIn.setToValue(0);
        
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), progress);
        fadeOut.setFromValue(0);
        fadeOut.setToValue(1);
        progress.setVisible(true);
        
        pt.getChildren().add(fadeIn);
        pt.getChildren().add(fadeOut);
        pt.play();
    }

}
