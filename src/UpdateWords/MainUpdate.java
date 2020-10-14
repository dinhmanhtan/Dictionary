package UpdateWords;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MainUpdate {


    public  void show() throws IOException {

        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        Parent root = FXMLLoader.load(getClass().getResource("UpdateWord.fxml"));
        window.setTitle("Update");

        Scene scene = new Scene(root,600,372);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        window.setScene(scene);
        window.setResizable(false);
        window.initStyle(StageStyle.DECORATED);

        window.show();
    }



}
