package view;

import com.jfoenix.controls.JFXComboBox;
import controller.COController;
import controller.Helper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.Camper;
import model.Rental;
import model.Reservation;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
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
    Label redLabel;

    @FXML
    JFXComboBox timeComboBox;

    @FXML
    TextField reservSearchField, rentalSearchField, reservStateField;

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
    TableColumn<Integer, Rental> rentalID, resID;
    @FXML
    TableColumn<Date, Rental> rentalStartDate, rentalEndDate;
    @FXML
    TableColumn<String, Rental> rentalStartLocation;

    @FXML
    TableView<Camper> campersTable;
    @FXML
    TableColumn<Integer, Camper> campID;
    @FXML
    TableColumn<String, Camper> campPlate;

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
                    clearTableCampers();
                    return;
                }

                if(selectedItem.equals("All"))
                {
                    loadReservations("all");
                    loadRentals("all");
                    clearTableCampers();
                    return;

                }

                if(selectedItem.equals("Past"))
                {
                    loadReservations("past");
                    loadRentals("past");
                    clearTableCampers();
                    return;

                }

                if(selectedItem.equals("Today"))
                {
                    loadReservations("today");
                    loadRentals("today");
                    clearTableCampers();
                    return;

                }
            }
        });



    }


    private void loadRentals(String str)
    {
        ArrayList<Rental> rentals = coController.getRentals(str);

        rentalID.setCellValueFactory(new PropertyValueFactory<>("id"));

        resID.setCellValueFactory(new PropertyValueFactory<>("reservID"));

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
        redLabel.setVisible(false);

        Rental selectedRental =  rentalsTable.getSelectionModel().getSelectedItem();

        if (selectedRental == null)
        {
            redLabel.setText("You have to select a rental first");
            redLabel.setVisible(true);
            return;
        }

        Boolean sure = screen.confirm("Confirmation", "You are about to delete a rental. Are you sure?");

        if (!sure)
        {
            return;
        }

        coController.deleteRental(selectedRental);

        loadRentals(timeComboBox.getSelectionModel().getSelectedItem().toString().toLowerCase());

        loadReservations(timeComboBox.getSelectionModel().getSelectedItem().toString().toLowerCase());

    }

    public void cancelReservation(ActionEvent event)
    {

    }

    public void createRental(ActionEvent event)
    {
        redLabel.setVisible(false);

        Reservation selectedReservation = reservationsTable.getSelectionModel().getSelectedItem();

        Camper selectedCamper = campersTable.getSelectionModel().getSelectedItem();

        if(selectedCamper == null || selectedReservation == null)
        {
            redLabel.setVisible(true);
            return;
        }

        coController.createRental(selectedReservation, selectedCamper);

        loadRentals(timeComboBox.getSelectionModel().getSelectedItem().toString().toLowerCase());

        loadReservations(timeComboBox.getSelectionModel().getSelectedItem().toString().toLowerCase());

        loadCampersOfType();

    }

    public void manageInventory(ActionEvent event) throws IOException
    {
        screen.change(event, "inventory.fxml");
    }

    public void searchReservations(KeyEvent keyEvent)
    {
        ArrayList<Reservation> reservations = coController.searchReservations(reservSearchField.getText());

        reservID.setCellValueFactory(new PropertyValueFactory<>("id"));

        reservStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));

        reservEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));

        reservStartLocation.setCellValueFactory(new PropertyValueFactory<>("startLocation"));

        ObservableList<Reservation> res = FXCollections.observableArrayList();

        res.addAll(reservations);

        reservationsTable.setItems(res);
    }


    public void searchRentals(KeyEvent keyEvent)
    {
        ArrayList<Rental> rentals = coController.searchRentals(rentalSearchField.getText());

        rentalID.setCellValueFactory(new PropertyValueFactory<>("id"));

        resID.setCellValueFactory(new PropertyValueFactory<>("reservID"));

        rentalStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));

        rentalEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));

        rentalStartLocation.setCellValueFactory(new PropertyValueFactory<>("startLocation"));

        ObservableList<Rental> ren = FXCollections.observableArrayList();

        ren.addAll(rentals);

        rentalsTable.setItems(ren);

    }


    public void showForTimePeriod(MouseEvent mouseEvent)
    {

    }

    public void loadCampersOfSelectedType(MouseEvent mouseEvent)
    {
        loadCampersOfType();
    }

    private void loadCampersOfType()
    {
        reservStateField.setText("");

        Reservation selectedReservation = reservationsTable.getSelectionModel().getSelectedItem();

        if(selectedReservation == null)
        {
            return;
        }

        if(selectedReservation.getState().equals("rental"))
        {
            campersTable.setItems(null);
            reservStateField.setText("Became Rental");
            return;
        }

        if(selectedReservation.getState().equals("Cancelled"))
        {
            campersTable.setItems(null);
            reservStateField.setText("Was Cancelled");
            return;
        }

        ArrayList<Camper> campers = coController.getAvailableCampers(selectedReservation);

        ObservableList<Camper> camp = FXCollections.observableArrayList();

        camp.addAll(campers);

        campID.setCellValueFactory(new PropertyValueFactory<>("id"));

        campPlate.setCellValueFactory(new PropertyValueFactory<>("plate"));

        campersTable.setItems(camp);

        reservStateField.setText("Reservation");

    }

    public void clearTableCampers()
    {
        ObservableList<Camper> camp = FXCollections.observableArrayList();

        camp.addAll();

        campID.setCellValueFactory(new PropertyValueFactory<>("id"));
        campPlate.setCellValueFactory(new PropertyValueFactory<>("plate"));

        campersTable.setItems(camp);
    }


    public void goToReservation(KeyEvent keyEvent) throws IOException
    {
        screen.changeOnKeyEvent(keyEvent, "orderedit.fxml");
    }

    public void goToRental(KeyEvent keyEvent) throws IOException
    {
        Rental selectedRental = rentalsTable.getSelectionModel().getSelectedItem();

        COController.setSelectedRental(selectedRental);

        screen.changeOnKeyEvent(keyEvent, "rental.fxml");
    }

    public void goToRental(MouseEvent mouseEvent) throws IOException
    {
        Rental selectedRental = rentalsTable.getSelectionModel().getSelectedItem();

        COController.setSelectedRental(selectedRental);

        screen.changeOnMouse(mouseEvent, "rental.fxml");
    }

    public void doubleClick(MouseEvent mouseEvent)
    {
        Rental selectedRental = rentalsTable.getSelectionModel().getSelectedItem();

        COController.setSelectedRental(selectedRental);
        Helper.doubleClick(mouseEvent, rentalsTable, "rental.fxml");

    }
}
