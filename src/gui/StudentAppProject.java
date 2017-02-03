/*
 * Copyright (C) 2017 Lucas Burdell lucas.burdell@blackburn.edu
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package gui;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import student.Student;

/**
 *
 *
 * @author Lucas Burdell lucas.burdell@blackburn.edu
 */
//http://htmlunit.sourceforge.net/
public class StudentAppProject extends Application {

    private Stage mainStage;
    private Scene mainScene;
    private static StudentAppProject appController;

    public void loadMain(Student student) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("MainGui.fxml"));
                    Parent root = loader.load();
                    MainGuiController controller = (MainGuiController) loader.getController();
                    controller.setStudent(student);
                    mainScene.setRoot(root);
                } catch (IOException ex) {
                    Logger.getLogger(StudentAppProject.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    @Override
    public void start(Stage stage) throws Exception {
        appController = this;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PasswordAsker.fxml"));
        Parent root = loader.load();

        mainScene = new Scene(root);
        mainStage = stage;
        mainStage.setScene(mainScene);
        mainStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            launch(args);
        } catch (Exception ex) {
            Logger.getLogger(StudentAppProject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the appController
     */
    public static StudentAppProject getAppController() {
        return appController;
    }
}
