package MainController;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    @FXML
    private TextField inputSearch;

    @FXML
    private TextArea content_body;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private JFXDrawer drawer;

    public  void changeScenetoShow(Event event, String input) throws IOException {

        // lấy dữ liệu file Show

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Show.fxml"));

        Parent root = loader.load();
        Scene scene = new Scene(root);

        Show controller = loader.getController();
        controller.ShowMeaning(input);

        // Thay doi stage

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();


    }

    public void searchByButton(ActionEvent event) throws IOException {

         String input = inputSearch.getText();

        if( !input.isEmpty() ) {

           changeScenetoShow(event,input);

        }

      
    }

    // Search khi nhan Enter
    public void searchByEnter() {

        inputSearch.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                if(event.getEventType() == KeyEvent.KEY_PRESSED && event.getCode() == KeyCode.ENTER &&
                        !inputSearch.getText().isEmpty()) {
                    try {
                        changeScenetoShow(event,inputSearch.getText());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            }
        });
    }

    public  void menuBar() {
        try {

         /**
          *   Lấy resource từ menu.fxml
          */

            FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
            AnchorPane anchorPane = loader.load();

            drawer.setSidePane(anchorPane);

            /**
             *    get Node button từ Menu bar
              */

            Node buttonViewAll = anchorPane.getChildren().get(1);
            Node buttonYourWords = anchorPane.getChildren().get(2);
            Node buttonRecentWords = anchorPane.getChildren().get(3);

            /**
             *   Button "View all words"
              */
            buttonViewAll.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                    try {
                        changeScenetoShow(event,"view all words");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            /**
             *  Button "Recent words"
             */

            buttonRecentWords.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                    try {
                        changeScenetoShow(event,"recent words");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });


            /**
             *   Button "Your words"
             */
            buttonYourWords.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                    try {
                        changeScenetoShow(event,"Your words");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });


        } catch (IOException e) {
            e.printStackTrace();
        }


        /**
         *   Hiện menu bar khi nhấn Hambuger
         */

        HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hamburger);
        transition.setRate(-1);
        hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
            transition.setRate(transition.getRate() * -1);
            transition.play();

            if (drawer.isOpened()) {
                drawer.setDisable(true);
                drawer.close();
                inputSearch.setDisable(false);

            } else {
                drawer.setDisable(false);
                drawer.open();
                inputSearch.setDisable(true);
            }
        });

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        drawer.setDisable(true);

        // Search khi nhan Enter
        searchByEnter();


       menuBar();


    }
}
