package view;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import controller.COController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import model.ExtraItem;
import model.Rental;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RentalView implements Initializable
{

    COController coController = new COController();

    Screen screen = new Screen();

    Rental selectedRental;

    @FXML
    TextField reservationIDField, assistantIDField, startLocationField, startKmField,
            endLocationField, endKmField, reservPriceField, extraFeePeriodField,
            extraFeeKmField, extraFeeExtrasField, totalField, camperID, custIdField;

    @FXML
    JFXTextField possibleLabel;

    @FXML
    JFXDatePicker startDatePicker, endDatePicker;

    @FXML
    JFXComboBox typeComboBox;

    @FXML
    TableView<ExtraItem> extrasTableView, chosenExtrasTableView;

    @FXML
    TableColumn<String, ExtraItem> extrasItemColumn, chosenItemsColumn;

    @FXML
    TableColumn<Integer, ExtraItem> quantityColumn;

    @FXML
    TableColumn<Double, ExtraItem> extrasPriceColumn;

    @FXML
    ChoiceBox exitOptions;


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        exitOptions.setItems(FXCollections.observableArrayList("Log out", "Exit"));

        selectedRental = COController.getSelectedRental();

        loadData();

    }

    private void loadData()
    {
        reservationIDField.setText(String.valueOf(selectedRental.getReservID()));
        assistantIDField.setText(String.valueOf(selectedRental.getAssistantID()));
        custIdField.setText(String.valueOf(selectedRental.getCustomer_id()));
        camperID.setText(String.valueOf(selectedRental.getRv_id()));
        startDatePicker.setValue(selectedRental.getStartDate().toLocalDate());
        endDatePicker.setValue(selectedRental.getEndDate().toLocalDate());
        startLocationField.setText(selectedRental.getStartLocation());
        endLocationField.setText(selectedRental.getEndLocation());
        reservPriceField.setText(String.valueOf(selectedRental.getReservPrice()));


        typeComboBox.setItems(FXCollections.observableArrayList(coController.getCamperType(selectedRental.getRv_id())));
        typeComboBox.getSelectionModel().selectFirst();

    }

    public void goBack(ActionEvent event) throws IOException
    {
        screen.change(event, "orders.fxml");
    }

    public void exitOrLogOut(MouseEvent mouseEvent)
    {
        screen.exitOrLogOut(mouseEvent, exitOptions);
    }

    public void saveChanges(ActionEvent event)
    {

    }

    public void goToReservation(ActionEvent event) throws IOException
    {
        screen.change(event, "orderedit.fxml");
    }

    public void goToCustomer(ActionEvent event) throws IOException
    {
        COController.setSelectedRentalCustID(selectedRental.getCustomer_id());

        screen.change(event, "customerdetails.fxml");
    }

    public void printContract(ActionEvent event) throws IOException
    {
        COController.setSelectedRental(selectedRental);
        screen.change(event, "contract.fxml");
    }
}
