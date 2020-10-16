
package MainController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Dictionary");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("show.fxml"));
        Parent root = loader.load();



        Show controller = loader.getController();
        controller.setHostServices(getHostServices());

        Scene scene = new Scene(root,550,817);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UNDECORATED);

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
