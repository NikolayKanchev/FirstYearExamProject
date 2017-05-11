package view;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.Motorhome;
import model.Rental;
import model.Reservation;

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

    @FXML
    JFXComboBox timeComboBox;

    @FXML
    TextField reservSearchField, rentalSearchField;

    @FXML
    TableView<Reservation> reservationsTable;

    @FXML
    TableView<Rental> rentalsTable;

    @FXML
    TableView<Motorhome> campersTable;





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

    public void cancelRental(ActionEvent event)
    {

    }

    public void cancelReservation(ActionEvent event)
    {

    }

    public void createRental(ActionEvent event)
    {

    }

    public void manageInventory(ActionEvent event)
    {

    }

    public void searchReservations(KeyEvent keyEvent)
    {

    }


    public void searchRentals(KeyEvent keyEvent)
    {

    }


    public void showForTimePeriod(MouseEvent mouseEvent)
    {

    }
}
