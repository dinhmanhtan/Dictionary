package UpdateWords;

import Dictionary.DictionaryManagement;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class UpdateWordController implements Initializable {

    @FXML
    private JFXTextField keyWord;

    @FXML
    private JFXTextArea meaning;

    @FXML
    private JFXButton buttonSave;

    @FXML
    private JFXButton buttonCancel;

    @FXML
    void CancelChanged(ActionEvent event) {

        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();

    }

//    @FXML
//    void  saveChanged(ActionEvent event) {
//
//        if( !keyWord.getText().isEmpty() && !meaning.getText().isEmpty()) {
//
//            ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
//        } else  {
//
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setHeaderText(null);
//            alert.setContentText("You need to fill out all information");
//            alert.Show();
//        }
//    }






    public void setWord(String Word, String Meaning) {

        keyWord.setText(Word);
        meaning.setText(Meaning);
    }


    public void Save(Text w, Text m) {



        buttonSave.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if( !keyWord.getText().isEmpty() && !meaning.getText().isEmpty()) {

                    // Lấy dữ liệu nhập ràng buộc cho tham chiếu
                     w.textProperty().bind(keyWord.textProperty());
                     m.textProperty().bind(meaning.textProperty());

                    ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
                } else  {

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("You need to fill out all information");
                    alert.show();
                }
            }
        });

    }

    public String getWord() {

        Label label = new Label();

        label.textProperty().bind(keyWord.textProperty());




        return  label.getText();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

      String s = getWord();
    }
}
