package studentappproject;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import test.Student;

/**
 *
 * @author lucas.burdell
 */
public class MainGuiController implements Initializable {

    @FXML
    private ScrollPane scroll;
    @FXML
    private Accordion accordion;
    @FXML
    private ImageView campusmapimage;
    @FXML
    private TabPane maintabpane;

    private Student student;

    @FXML
    private VBox classList;

    /**
     * @return the student
     */
    public Student getStudent() {
        return student;
    }

    /**
     * @param student the student to set
     */
    public void setStudent(Student student) {
        this.student = student;
        for (int i = 0; i < student.getClasses().size(); i++) {
            String elementAt = student.getClasses().get(i);
            Text text = new Text(elementAt);
            classList.getChildren().add(text);
        }
    }

    private static class Announcement {

        private Hyperlink header;
        private Node[] text;
        private TextFlow display = new TextFlow();
        private Stage announcementStage = new Stage();

        public Announcement(String header, String text) {
            this.header = new Hyperlink(header);
            Text titleText = new Text(header + "\n");
            titleText.setFill(Color.RED);
            titleText.setFont(Font.font(header, FontWeight.BOLD, 16));
            titleText.setUnderline(true);
            this.text = new Node[]{titleText, new Text(text)};
            this.header.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    handleHeaderClick();
                }
            });
        }

        public void handleHeaderClick() {
            if (!announcementStage.isShowing()) {
                StackPane root = new StackPane();
                root.getChildren().add(getDisplay());
                getDisplay().getChildren().addAll(text);
                Scene scene = new Scene(root, 300, 250);
                getAnnouncementStage().setTitle(getHeader().getText());
                getAnnouncementStage().setScene(scene);
                getAnnouncementStage().show();
            }
        }

        /**
         * @return the header
         */
        protected Hyperlink getHeader() {
            return header;
        }

        /**
         * @return the text
         */
        protected Node[] getText() {
            return text;
        }

        /**
         * @return the announcementStage
         */
        public Stage getAnnouncementStage() {
            return announcementStage;
        }

        /**
         * @return the display
         */
        public TextFlow getDisplay() {
            return display;
        }
    }

    private static class AnnouncementCategory {

        private ArrayList<Announcement> announcements = new ArrayList<>();
        private TextFlow container = new TextFlow();
        private TitledPane title;

        public AnnouncementCategory(String name) {
            title = new TitledPane(name, getContainer());
        }

        /**
         * @return the name
         */
        public String getName() {
            return getTitle().getText();
        }

        /**
         * @return the announcements
         */
        public ArrayList<Announcement> getAnnouncements() {
            return announcements;
        }

        public void addAnnouncement(Announcement announcement) {
            getAnnouncements().add(announcement);
            getContainer().getChildren().add(announcement.getHeader());
        }

        /**
         * @return the container
         */
        public TextFlow getContainer() {
            return container;
        }

        /**
         * @return the title
         */
        public TitledPane getTitle() {
            return title;
        }
    }

    private void handleButtonAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AnnouncementCategory special = new AnnouncementCategory("Special");
        Announcement test1 = new Announcement("Testing", "Hello, this is a "
                + "test of the announcements part of the Student App. It's a"
                + " proof of concept, not a functional design.");
        special.addAnnouncement(test1);
        accordion.getPanes().add(special.getTitle());
        campusmapimage.setImage(new Image("resources/campus-map.jpg"));
        campusmapimage.fitWidthProperty().bind(maintabpane.widthProperty().subtract(25));
        campusmapimage.fitHeightProperty().bind(maintabpane.heightProperty().subtract(25));
    }

}
