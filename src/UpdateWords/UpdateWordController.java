package UpdateWords;

import Dictionary.Dictionary;
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
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;

import Dictionary.*;

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


   public void CancelChanged(ActionEvent event) {

        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();

    }


    public void setWord(String Word, String Meaning) {

        keyWord.setText(Word);
        meaning.setText(Meaning);
    }


    public void Save(Text w, Text m, DictionaryManagement dic, Text replace) {



        buttonSave.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if( !keyWord.getText().isEmpty() && !meaning.getText().isEmpty() ) {

                    // Lấy dữ liệu nhập ràng buộc cho tham chiếu
                     w.textProperty().bind(keyWord.textProperty());
                     m.textProperty().bind(meaning.textProperty());

                     if(dic.dictionary.containsKey(keyWord.getText())) {
                         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

                         alert.setTitle("Warring");
                         alert.setContentText("Do you want replace this ?");
                         alert.setHeaderText("This has already on Dictionary");

                         ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
                         ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.NO);
                         alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
                         Optional<ButtonType> result = alert.showAndWait();

                         if (result.get().getButtonData() == ButtonBar.ButtonData.YES) {
                                 replace.setText("yes");
                               ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();

                         } else if (result.get().getButtonData() == ButtonBar.ButtonData.NO)
                                 replace.setText("no");


                     } else

                       ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
                } else  {

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("You need to fill out all information");
                    alert.showAndWait();
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
