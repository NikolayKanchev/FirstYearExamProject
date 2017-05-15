package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Nikolaj on 10-05-2017.
 */
public class Screen
{
    public void change(ActionEvent actionEvent, String fxml) throws IOException
    {
        Stage stage = (Stage)(((Node) actionEvent.getSource()).getScene().getWindow());
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource(fxml))));
    }

    public void changeOnMouse(MouseEvent mouseEvent, String fxml) throws  IOException
    {
        Stage stage = (Stage)(((Node) mouseEvent.getSource()).getScene().getWindow());
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource(fxml))));
    }

    public void exitOrLogOut(MouseEvent mouseEvent, ChoiceBox exitOptions)
    {
        exitOptions.getSelectionModel().selectedItemProperty().addListener((v,oldValue, newValue) -> {
            if(exitOptions.getSelectionModel().getSelectedItem().equals("Exit")){
                System.exit(0);
            }

            if(exitOptions.getSelectionModel().getSelectedItem().equals("Log out")){
                Stage stage = (Stage)(((Node) mouseEvent.getSource()).getScene().getWindow());
                try {
                    stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("login.fxml")),900, 600));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void changeOnKeyEvent(KeyEvent keyEvent, String fxml) throws IOException
    {
        Stage stage = (Stage)(((Node) keyEvent.getSource()).getScene().getWindow());
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource(fxml))));
    }

    public Boolean confirm(String confirmation, String message)
    {
        final Boolean[] sure = {false};

        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(confirmation);
        window.setMinWidth(500);
        window.setMinHeight(150);

        Label label = new Label(message);
        Button yes = new Button("Yes");
        Button no = new Button("No");
        yes.setOnAction(e-> {
            window.close();
            sure[0] = true;
        });

        no.setOnAction(e-> window.close());

        HBox layout = new HBox(20);
        layout.getChildren().addAll(label, yes, no);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return sure[0];


    }
}
