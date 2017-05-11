package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
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
}
