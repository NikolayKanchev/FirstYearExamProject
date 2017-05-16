package view;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import controller.COController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.ExtraItem;
import model.Reservation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Jakub on 15.05.2017.
 */
public class ReservationView implements Initializable{

    COController coController = new COController();

    Screen screen = new Screen();

    Reservation selectedReservation;

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

        selectedReservation = COController.getSelectedReservation();

        loadData();

    }

    private void loadData()
    {
        reservationIDField.setText(String.valueOf(selectedReservation.getId()));
        assistantIDField.setText(String.valueOf(selectedReservation.getAssistantID()));
        custIdField.setText(String.valueOf(selectedReservation.getCustomerID()));
        camperID.setText(String.valueOf(selectedReservation.getRvTypeID()));
        startDatePicker.setValue(selectedReservation.getStartDate().toLocalDate());
        endDatePicker.setValue(selectedReservation.getEndDate().toLocalDate());
        startLocationField.setText(selectedReservation.getStartLocation());
        endLocationField.setText(selectedReservation.getEndLocation());
        reservPriceField.setText(String.valueOf(selectedReservation.getEstimatedPrice()));


        typeComboBox.setItems(FXCollections.observableArrayList(coController.getCamperBrandAndModel(selectedReservation.getRvTypeID())));
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

    public void goToCustommer(ActionEvent event) throws IOException
    {
        COController.setSelectedRentalCustID(selectedReservation.getCustomerID());

        screen.change(event, "customerdetails.fxml");

    }

}
