package view;

import com.jfoenix.controls.JFXComboBox;
import controller.COController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.Motorhome;
import model.Rental;
import model.Reservation;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Rasmus on 08-05-2017.
 */
public class OrdersView implements Initializable
{

    Screen screen = new Screen();
    COController coController = new COController();

    @FXML
    ChoiceBox exitOptions;

    @FXML
    JFXComboBox timeComboBox;

    @FXML
    TextField reservSearchField, rentalSearchField;

    @FXML
    Button assignButton;

    @FXML
    TableView<Reservation> reservationsTable;
    @FXML
    TableColumn<Integer, Reservation> reservID;
    @FXML
    TableColumn<Date, Reservation> reservStartDate, reservEndDate;
    @FXML
    TableColumn<String, Reservation> reservStartLocation;

    @FXML
    TableView<Rental> rentalsTable;
    @FXML
    TableColumn<Integer, Rental> rentalID;
    @FXML
    TableColumn<Date, Rental> rentalStartDate, rentalEndDate;
    @FXML
    TableColumn<String, Rental> rentalStartLocation;

    @FXML
    TableView<Motorhome> campersTable;
    @FXML
    TableColumn<Integer, Motorhome> campID;
    @FXML
    TableColumn<String, Motorhome> campPlate;





    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

        exitOptions.setItems(FXCollections.observableArrayList("Log out", "Exit"));

        timeComboBox.setItems(FXCollections.observableArrayList("Today","Past","Future","All"));

        timeComboBox.getSelectionModel().selectFirst();

        loadReservations("today");

        loadRentals("today");

        timeComboBox.valueProperty().addListener(new ChangeListener()
        {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue)
            {
                String selectedItem = timeComboBox.getSelectionModel().getSelectedItem().toString();

                if(selectedItem.equals("Future"))
                {
                    loadReservations("future");
                    loadRentals("future");
                    return;
                }

                if(selectedItem.equals("All"))
                {
                    loadReservations("all");
                    loadRentals("all");
                    return;

                }

                if(selectedItem.equals("Past"))
                {
                    loadReservations("past");
                    loadRentals("past");
                    return;

                }

                if(selectedItem.equals("Today"))
                {
                    loadReservations("today");
                    loadRentals("today");
                    return;

                }
            }
        });

    }

    private void loadRentals(String str)
    {
        ArrayList<Rental> rentals = coController.getRentals(str);

        rentalID.setCellValueFactory(new PropertyValueFactory<>("id"));

        rentalStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));

        rentalEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));

        rentalStartLocation.setCellValueFactory(new PropertyValueFactory<>("startLocation"));

        ObservableList<Rental> ren = FXCollections.observableArrayList();

        ren.addAll(rentals);

        rentalsTable.setItems(ren);

    }

    private void loadReservations(String str)
    {
        ArrayList<Reservation> reservations = coController.getReservations(str);

        reservID.setCellValueFactory(new PropertyValueFactory<>("id"));

        reservStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));

        reservEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));

        reservStartLocation.setCellValueFactory(new PropertyValueFactory<>("startLocation"));

        ObservableList<Reservation> res = FXCollections.observableArrayList();

        res.addAll(reservations);

        reservationsTable.setItems(res);


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

    public void loadCampersOfSelectedType(MouseEvent mouseEvent)
    {
        Reservation selectedReservation = reservationsTable.getSelectionModel().getSelectedItem();

        if(selectedReservation == null)
        {
            return;
        }

        ArrayList<Motorhome> campers = coController.getAvailableCampers(selectedReservation);

        ObservableList<Motorhome> camp = FXCollections.observableArrayList();

        camp.addAll(campers);

        campID.setCellValueFactory(new PropertyValueFactory<>("id"));
        campPlate.setCellValueFactory(new PropertyValueFactory<>("plate"));

        campersTable.setItems(camp);
    }
}
