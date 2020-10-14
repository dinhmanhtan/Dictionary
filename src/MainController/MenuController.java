package MainController;

import com.jfoenix.controls.JFXButton;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;


public class MenuController implements Initializable {


    @FXML
    private JFXButton btnViewWords;

    @FXML
    private JFXButton bntYourWords;

    @FXML
    private JFXButton btnRecentWords;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
