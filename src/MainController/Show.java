package MainController;

import UpdateWords.MainUpdate;
import com.jfoenix.controls.*;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import Dictionary.DictionaryManagement;
import javafx.stage.Modality;
import javafx.stage.PopupWindow;
import UpdateWords.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import TextToSpeech.*;
import marytts.modules.synthesis.Voice;

public class Show implements Initializable {

    @FXML
    private JFXDrawer drawerShow;       // Thanh menu

    @FXML
    private JFXHamburger hamburgerShow;  // hamburger menu

    @FXML
    private ScrollPane ScrollContent;        // Hiển thị nội dung của từ

    @FXML
    private AnchorPane paneContent;   // Chứa ScrollContent

    @FXML
    private TextField inputSearch;    // Nhập từ

    @FXML
    private Button buttonSearch;    // button tra từ


    @FXML
    private Button button_star;

    @FXML
    private Button button_remove;  // Các button đánh dấu từ , sửa, xóa từ

    @FXML
    private Button button_reword;

    @FXML
    private HBox hBox;    // phần lưu nội dung cho content


    @FXML
    private ListView<JFXButton> listView;  // Hiển thị danh sách từ hỗ trợ khi tra

    @FXML
    private AnchorPane body;

    @FXML
    private AnchorPane top_header;
    @FXML
    private AnchorPane top_search;

    @FXML
    private Label label_content;

    @FXML
    private ImageView btn_facebook;
    @FXML
    private ImageView btn_twitter;
    @FXML
    private ImageView btn_instagram;

    private VBox vbox ;
    private  VBox listChoseYourWord;

    private  DictionaryManagement dic ;

    private Tooltip tt_remove, tt_star, tt_reword;

    // các lựa chọn menu
    private Node btn_viewAllWords, btn_yourWords, btn_recentWords, btn_addWords;

    private HamburgerBackArrowBasicTransition transition;

    private TextToSpeech tts;

    private Button sound;

    private HostServices hostServices ;




    public HostServices getHostServices() {
        return hostServices ;
    }

    public void setHostServices(HostServices hostServices) {
        this.hostServices = hostServices ;
    }

    public void Init() {

      dic = new DictionaryManagement();

      dic.InitDictionary();

      //  Khởi tạo giá trị ban đầu

        ScrollContent.setContent(null);

        drawerShow.setDisable(true);
        button_star.setDisable(true);
        button_remove.setDisable(true);
        button_reword.setDisable(true);

     // Cài đặt tool tip
        tt_remove = new Tooltip("Remove this word");
        tt_star = new Tooltip("Add to your words");
        tt_reword = new Tooltip("Correct this word");
        tt_remove.setAnchorLocation(PopupWindow.AnchorLocation.WINDOW_TOP_RIGHT);
        tt_reword.setAnchorLocation(PopupWindow.AnchorLocation.WINDOW_TOP_RIGHT);

        button_reword.setTooltip(tt_reword);
        button_remove.setTooltip(tt_remove);


        listView.getItems().clear();
        listView.setDisable(true);
        listView.setPrefHeight(0);

        transition = new HamburgerBackArrowBasicTransition(hamburgerShow);
        transition.setRate(-1);

        //
        tts = new TextToSpeech();

       // Voice.getAvailableVoices().forEach(System.out::println);
        tts.setVoice("dfki-poppy-hsmm");


    }


    /**
     *   Khởi tạo nội dung in ra
     * @param input  từ cần tra
     */

    public void InitContent(String input) {

        String meaning = dic.Lookup(input);

        ScrollContent.setLayoutY(3);
        ScrollContent.setPrefHeight(483);
        ScrollContent.getStyleClass().remove("content-body-2");
        ScrollContent.getStyleClass().add("content-body-1");
        ScrollContent.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        paneContent.getStyleClass().remove("pane-content");

        vbox = new VBox();

        Text text1 = new Text();
        text1.setText(meaning);

        Text text2 = new Text();
        text2.setText(input);
        text2.setFont(Font.font("verdana", FontWeight.BOLD, 23));

        HBox hBox = new HBox(30);


        sound = new Button();

        sound.setPrefSize(37,37);
        sound.getStyleClass().add("button-star");
        sound.setStyle("-fx-background-image: url('image/speaker_26px.png')");
        sound.setCursor(Cursor.HAND);

        hBox.getChildren().addAll(text2,sound);

        if(dic.dictionary.containsKey(input) ) {


            button_star.setStyle("-fx-background-image: url('image/star_48px.png')");
            button_star.setDisable(false);
            button_remove.setDisable(false);
            button_remove.setStyle("-fx-background-image: url('image/remove_30px.png')");

            button_reword.setDisable(false);
            button_reword.setStyle("-fx-background-image: url('image/edit_property_26px.png')");

            if(dic.yourWords.get(input) == false) {

                tt_star.setText("Add to your words");

                button_star.setStyle("-fx-background-image: url('image/star_48px.png')");

            } else {
                tt_star.setText("Remove this from your words");
                button_star.setStyle("-fx-background-image: url('image/star.png')");
            }

            button_star.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                   if(dic.yourWords.get(input) == false) {

                       dic.yourWords.replace(input,true);
                       dic.WriteDataToFile(input,"File/YourWords.txt");
                       button_star.setStyle("-fx-background-image: url('image/star.png')");
                       tt_star.setText("Remove this from your words");

                   } else {

                       dic.yourWords.replace(input,false);
                       dic.DeleteDataFromFile(input,"File/YourWords.txt");
                       button_star.setStyle("-fx-background-image: url('image/star_48px.png')");

                       tt_star.setText("Add to your words");
                   }
                  

                }
            });

            // Tạo Hbox mới để lưu  button star , remove , reword
            button_star.setTooltip(tt_star);

            HBox box = new HBox();
            box.setAlignment(Pos.TOP_LEFT);
            box.setPadding(new Insets(0,5,30,10));

            box.setSpacing(35);
            box.getChildren().addAll(button_star,button_remove,button_reword);
            vbox.getChildren().addAll(box,hBox);
        }


        vbox.getChildren().add(text1);
        vbox.setSpacing(15);
        vbox.setPadding(new Insets(20,5,5,15));

        ScrollContent.setContent(vbox);

        button_remove.setOnAction(event -> RemoveWord(input));


        sound.setOnAction(event -> tts.speak(input,2.0f,false,false));


        button_reword.setOnAction(event -> {
               try {
                   UpdateWord(input, dic.getFullMeaning(input), "reword");
               } catch (IOException e) {
                   e.printStackTrace();
               }
        });

    }

    /**
     *   Sửa , thêm từ
     *    @param message điều kiện để thực hiện Thêm hoặc Sửa từ
     */

    public void UpdateWord(String word, String meaning, String message) throws IOException {

        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateWords/UpdateWord.fxml"));

        Parent root = loader.load();
        window.setTitle("Update");

        UpdateWordController controller = (UpdateWordController) loader.getController();
        controller.setWord(word,meaning);

        // Lấy dữ liệu word thay đổi


         Text textWord  = new Text();
         Text textMeaning = new Text();


         controller.Save(textWord,textMeaning);


        Scene scene = new Scene(root,600,372);
        scene.getStylesheets().add(getClass().getResource("/UpdateWords/style.css").toExternalForm());

        window.setScene(scene);
        window.setResizable(false);
        window.initStyle(StageStyle.DECORATED);

        window.showAndWait();

        String w = textWord.getText();
        String m = textMeaning.getText();
        String wordToFile = "@" + w + '\n' + m;
        if( !w.isEmpty() && !m.isEmpty())  {

           if( message.equals("reword")) {

               dic.dictionary.remove(word);
               dic.Put(w,"",m,"");

               dic.yourWords.put(w, false);

               dic.WriteDataToFile(word,"File/DeletedWords.txt");
               dic.WriteDataToFile(wordToFile,"File/ChangedWords.txt");
               inputSearch.setText(w);
               InitContent(w);
           } else  if(message.equals("create")) {

             if( dic.dictionary.containsKey(textWord.getText()) ) {

                  Alert alert = createAlert("Warring", "Do you want replace this ?",
                         "This has already on Dictionary");


                  Optional<ButtonType> result = alert.showAndWait();

                  if (result.get().getButtonData() == ButtonBar.ButtonData.YES) {


                      dic.dictionary.remove(word);
                      dic.WriteDataToFile(word,"File/DeletedWords.txt");

                      dic.Put(w,"",m,"");
                      dic.WriteDataToFile(wordToFile,"File/ChangedWords.txt");

                      InitContent(w);
                  } else if (result.get().getButtonData() == ButtonBar.ButtonData.NO)
                      System.out.println("Code for no");

               } else  {
                  dic.Put(w,"m",m,"");
                  dic.yourWords.put(w,false);
                  dic.WriteDataToFile(wordToFile,"File/NewWords.txt");
                  InitContent(w);
               }

           }

        }

    }

    /**
     *  Tạo hộp thoại cảnh báo
     */

    Alert createAlert(String title, String content, String header ) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle(title);
        alert.setContentText(content);
        alert.setHeaderText(header);

        ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.NO);

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        return  alert;
    }

    /**
     *  Xóa từ
     */

    public void RemoveWord(String input) {

        Alert alert = createAlert("Cofirmation","Do you want to remove this word ?",null);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get().getButtonData() == ButtonBar.ButtonData.YES) {

            dic.dictionary.remove(input);
            dic.yourWords.remove(input);
            dic.WriteDataToFile(input,"File/DeletedWords.txt");

            inputSearch.setText(null);
            ScrollContent.setContent(null);

        } else if (result.get().getButtonData() == ButtonBar.ButtonData.NO)
            System.out.println("Code for no");

    }


    /**
     *    Tìm kiếm từ bằng click button
     * @param event click button
     */
    public void SearchByButton(ActionEvent event) {

        String input = inputSearch.getText();
        listView.getItems().clear();

        listView.setPrefHeight(0);
        listView.setManaged(true);

        if(inputSearch.getText() != null && !input.isEmpty() )   {

            if(dic.dictionary.containsKey(input))
                  InitContent(input);
            else {

                 ListOfGuessWords(input);
            }
        }


    }

    /**
     *    Tìm kiếm từ bằng nhấn Enter
     */
    public void SearchByEnter() {
        inputSearch.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                if(event.getEventType() == KeyEvent.KEY_PRESSED && event.getCode() == KeyCode.ENTER &&
                        !inputSearch.getText().isEmpty()) {

                    listView.getItems().clear();
                    listView.setPrefHeight(0);
                    listView.setManaged(true);

                    if(dic.dictionary.containsKey(inputSearch.getText()))
                          InitContent(inputSearch.getText());
                    else {

                        ListOfGuessWords(inputSearch.getText());
                    }
                }
            }
        });

    }


    public void ShowMeaning(String text) {

        InitContent(text);

    }

    /**
     *  Thêm từ mới
     */

    public void AddNewWord() {

        btn_addWords.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    inputSearch.setText(null);
                    drawerShow.setDisable(true);
                    drawerShow.close();
                    inputSearch.setDisable(false);

                    UpdateWord(null,null,"create");

                    transition.setRate(transition.getRate() * -1);
                    transition.play();

                    listView.getItems().clear();

                    
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        });


    }



    /**
     *  Show Menu bar
     * @throws IOException aa
     */

    public void ClickMenuBar() throws IOException {


        FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
        AnchorPane anchorPane = loader.load();

        drawerShow.setSidePane(anchorPane);


        /**
         *    get Node button từ Menu bar
         */

         btn_viewAllWords = anchorPane.getChildren().get(1);
         btn_yourWords = anchorPane.getChildren().get(2);
         btn_recentWords = anchorPane.getChildren().get(3);
         btn_addWords = anchorPane.getChildren().get(4);

         btn_recentWords.setOnMouseClicked(event -> {
            InitContent("recent");

            inputSearch.setText(null);
            drawerShow.setDisable(true);
            drawerShow.close();
            inputSearch.setDisable(false);

             transition.setRate(transition.getRate() * -1);
             transition.play();
        });

         btn_yourWords.setOnMouseClicked(event -> {

             ScrollContent.setLayoutY(70);
             ScrollContent.setPrefHeight(415);
             ScrollContent.getStyleClass().add("content-body-2");
             paneContent.getStyleClass().add("pane-content");

             label_content.setText("Your words");
             ListChoseYourWord(); });


        /*
         *   Hiện menu bar khi nhấn Hamburger
         */


        hamburgerShow.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
            transition.setRate(transition.getRate() * -1);
            transition.play();

            listView.getItems().clear();
            listView.setPrefHeight(0);
            listView.setDisable(true);


            if (drawerShow.isOpened()) {
                drawerShow.setDisable(true);
                drawerShow.close();
                inputSearch.setDisable(false);

            } else {
                drawerShow.setDisable(false);
                drawerShow.open();
                inputSearch.setDisable(true);

            }
        });

    }

    /**
     *  Hiển thị danh sách từ hỗ trợ khi tra
     */

  public void ShowListWordWhenSeach() {


       inputSearch.setOnKeyReleased(new EventHandler<KeyEvent>() {
           @Override
           public void handle(KeyEvent event) {

               if (event.getCode() != KeyCode.ENTER) {

                   InitListWords();
                   SearchByClickList();
                   }
               }

       });


      inputSearch.setOnMouseReleased(event -> {

          if( inputSearch.getText() != null && !inputSearch.getText().isEmpty() ) {
              InitListWords();
              SearchByClickList();
          }
      });


      ScrollContent.setOnMouseReleased(event -> {
          listView.getItems().clear();
          listView.setPrefHeight(0);
          listView.setDisable(true);
      });
      top_header.setOnMouseReleased(event -> {
          listView.getItems().clear();
          listView.setPrefHeight(0);
          listView.setDisable(true);
      });

      top_search.setOnMouseReleased(event -> {
          listView.getItems().clear();
          listView.setPrefHeight(0);
          listView.setDisable(true);
      });


  }

    /**
     *  Khởi tạo danh sách từ hỗ trợ ki tra
     */

  void InitListWords() {
      Text text = new Text();
      listView.getItems().clear();

      text.textProperty().bind(inputSearch.textProperty());
      String word = inputSearch.getText();

      List<String> list = dic.dictionarySeacher(word);

      if (!list.isEmpty() && !word.isEmpty()) {

          listView.setPrefHeight(330);
          listView.setDisable(false);

          for (int i = 0; i < list.size(); i++) {

              JFXButton button = new JFXButton();

              button.setAlignment(Pos.BASELINE_CENTER);
              button.setPrefSize(listView.getPrefWidth()-65, 30);
              button.setText(list.get(i));
              listView.getItems().add(button);

          }


      } else
          listView.setPrefHeight(0);

  }

    /**
     *  Chọn từ trong danh sách từ hỗ trợ
     */

  void SearchByClickList() {

      int size =  listView.getItems().size();

      for(int i=0; i<size ; i++) {

          JFXButton button = listView.getItems().get(i);

          button.setOnMouseClicked(new EventHandler<MouseEvent>() {
              @Override
              public void handle(MouseEvent event) {

                  if( button.getText()  != null ) {

                      listView.getItems().clear();
                      listView.setPrefHeight(0);
                      listView.setDisable(true);

                      InitContent(button.getText());
                      inputSearch.setText(button.getText());
                  }
              }
          });

      }

  }

  public void ListChoseYourWord() {

      List<String> list = dic.ListYourWord();

      label_content.setStyle("-fx-font-szie: 25");
      listChoseYourWord = new VBox(20);
      listChoseYourWord.getStyleClass().add("List-chose-word");

      listChoseYourWord.setPadding(new Insets(30,0,10,30));

      inputSearch.setText(null);
      drawerShow.setDisable(true);
      drawerShow.close();
      inputSearch.setDisable(false);
      transition.setRate(transition.getRate() * -1);
      transition.play();

      if ( !list.isEmpty() ) {

          for(String str : list) {

              JFXButton button = new JFXButton();

              VBox container = new VBox(10);
              container.getStyleClass().add("vbox-word");
              Label label1 = new Label();
              Label label2 = new Label();
              Label label3 = new Label();

              label1.setText(str);
              label2.setText(dic.getPronunc(str));

              label3.setText( "");

              label1.getStyleClass().add("label1-your-word");
              label2.getStyleClass().add("label2-your-word");

              container.setPrefWidth(paneContent.getPrefWidth()-100);
              container.getChildren().addAll(label1,label2,label3);

              listChoseYourWord.getChildren().add(container);
          }
          ScrollContent.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
          ScrollContent.setContent(listChoseYourWord);


          for(int i=0; i<list.size(); i++) {

              VBox child = (VBox) listChoseYourWord.getChildren().get(i);

              child.setOnMouseClicked(event -> {


              Label label = (Label) child.getChildren().get(0);
                  InitContent(label.getText());
              });

          }


      }

  }

  public void ListOfGuessWords(String key) {

      ScrollContent.setLayoutY(70);
      ScrollContent.setPrefHeight(415);
      ScrollContent.getStyleClass().add("content-body-2");
      paneContent.getStyleClass().add("pane-content");



      List<String> list = dic.ListOfGuessCorrectWords(key);

      if(list.isEmpty()) {
          ScrollContent.setContent(null);
          ScrollContent.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
          label_content.setStyle("-fx-font-size:18");
          label_content.setText("We cannot find any entries matching \"" + key + "\"" + '\n' +
                                 "Please check you have typed the word correctly");

      } else  {
          label_content.setStyle("-fx-font-size:22");
          label_content.setText("Search suggestions for \"" + key + "\"");

          VBox container = new VBox(20);
          container.setPadding(new Insets(10,0,10,100));

          for (String word : list) {

             JFXButton button = new JFXButton();
             button.setText(word);
             button.setPrefSize(ScrollContent.getPrefWidth()-250,30);
             button.getStyleClass().add("button-guess-word");

             container.getChildren().add(button);
          }

         ScrollContent.setContent(container);

          for(int i=0; i<list.size(); i++) {

              JFXButton button = (JFXButton) container.getChildren().get(i);

              button.setOnMouseClicked(event -> {
                  InitContent(button.getText());
              });

          }
      }


  }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        Init();


        try {
            ClickMenuBar();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SearchByEnter();
        AddNewWord();
        ShowListWordWhenSeach();

        btn_facebook.setOnMouseClicked(event -> {
            getHostServices().showDocument("https://www.facebook.com/CUPCambridgeDictionary/");
        });

        btn_twitter.setOnMouseClicked(event -> {
            getHostServices().showDocument("https://twitter.com/CambridgeWords");
        });

        btn_instagram.setOnMouseClicked(event -> {
            getHostServices().showDocument("https://www.instagram.com/accounts/login/?next=/cambridgewords/");
        });
    }


}
