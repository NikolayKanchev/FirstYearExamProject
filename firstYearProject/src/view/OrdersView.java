package view;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Rasmus on 08-05-2017.
 */
public class OrdersView implements Initializable
{

    Screen screen = new Screen();

    @FXML
    ChoiceBox exitOptions;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        exitOptions.setItems(FXCollections.observableArrayList("Log out", "Exit"));
    }

    public void exitOrLogOut(MouseEvent mouseEvent)
    {
        screen.exitOrLogOut(mouseEvent, exitOptions);
    }

    public void createReservation(ActionEvent actionEvent)
    {
        try
        {
            screen.change(actionEvent, "orderedit.fxml");
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
