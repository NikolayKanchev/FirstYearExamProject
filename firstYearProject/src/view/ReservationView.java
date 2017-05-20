package view;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import controller.COController;
import controller.Helper;
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
import model.CamperType;
import model.ExtraItem;
import model.ExtrasLineItem;
import model.Reservation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Jakub on 15.05.2017.
 */
public class ReservationView implements Initializable{

    private COController coController = new COController();

    private Screen screen = new Screen();

    private Reservation selectedReservation;

    private Object timePeriod;

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
    TableView<ExtraItem> extrasTableView;

    @FXML
    TableColumn<String, ExtraItem> extrasItemColumn;

    @FXML
    TableColumn<Double, ExtrasLineItem> extrasPriceColumn;

    @FXML
    TableView<ExtrasLineItem> chosenExtrasTableView;

    @FXML
    TableColumn<String, ExtrasLineItem> chosenItemsColumn;

    @FXML
    TableColumn<Integer, ExtrasLineItem> quantityColumn;

    @FXML
    TableColumn<Double, ExtrasLineItem> subTotalColumn;

    @FXML
    ChoiceBox exitOptions;

    @FXML
    Label redLabel;

    @FXML
    Button saveButton;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

        timePeriod = COController.getSelectedTimePeriod();

        exitOptions.setItems(FXCollections.observableArrayList("Log out", "Exit"));

        selectedReservation = COController.getSelectedReservation();

        loadData();

        //region table extraItems
        extrasItemColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        extrasPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        ObservableList<ExtraItem> extraItems = FXCollections.observableArrayList();
        extraItems.addAll(coController.getExtras());
        extrasTableView.setItems(extraItems);

        loadExtraLineItems();

        coController.calculateExtraLinesItemsTotal(chosenExtrasTableView, extraFeeExtrasField);
        //endregion
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

        typeComboBox.getItems().addAll(coController.getCamperTypes());

        typeComboBox.getSelectionModel().select(coController.getCamperTypeByItsID(selectedReservation.getRvTypeID()));


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


    public void addExtraItem(MouseEvent mouseEvent)
    {
        extrasTableView.setOnMousePressed(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent)
            {

                if (mouseEvent.isPrimaryButtonDown() && mouseEvent.getClickCount() == 2)
                {
                    ExtraItem chosenItem =  extrasTableView.getSelectionModel().getSelectedItem();

                    coController.addExtraLineItem(chosenItem, chosenExtrasTableView,
                            selectedReservation.getId(), "reservation");

                    loadExtraLineItems();

                    coController.calculateExtraLinesItemsTotal(chosenExtrasTableView, extraFeeExtrasField);

                    coController.getRentTotal(reservPriceField, extraFeePeriodField, extraFeeKmField, extraFeeExtrasField, totalField);
                }
            }
        });

    }

    private void loadExtraLineItems()
    {
        chosenItemsColumn.setCellValueFactory(new PropertyValueFactory<>("extraItemName"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        subTotalColumn.setCellValueFactory(new PropertyValueFactory<>("subTotal"));


        ObservableList<ExtrasLineItem> lineItems = FXCollections.observableArrayList();
        lineItems.addAll(coController.getExtrasLineItems(selectedReservation.getId(), "reservation"));
        chosenExtrasTableView.setItems(lineItems);
    }

    public void subtractExtraItem(MouseEvent mouseEvent)
    {
        chosenExtrasTableView.setOnMousePressed(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent)
            {
                if (mouseEvent.isPrimaryButtonDown() && mouseEvent.getClickCount() == 2)
                {

                    ExtrasLineItem chosenExLineItem = chosenExtrasTableView.getSelectionModel().getSelectedItem();

                    coController.subtractExtraLineItemQuantity(chosenExLineItem);

                    loadExtraLineItems();

                    coController.calculateExtraLinesItemsTotal(chosenExtrasTableView, extraFeeExtrasField);

                    coController.getRentTotal(reservPriceField, extraFeePeriodField, extraFeeKmField, extraFeeExtrasField, totalField);

                }
            }
        });

    }

    public void calculateExtraKmFee(KeyEvent keyEvent)
    {

        coController.calculateKmPriceAndTotal(
                startKmField, extraFeeKmField, totalField, extraFeeKmField,
                reservPriceField, extraFeePeriodField, extraFeeExtrasField);

    }

    public void calculateExtraKmFeeEndLocation(KeyEvent keyEvent)
    {
        coController.calculateKmPriceAndTotal(
                endKmField, extraFeeKmField, totalField, extraFeeKmField,
                reservPriceField, extraFeePeriodField, extraFeeExtrasField);

    }

    public void calculateProlongPeriodPrice(ActionEvent event) throws InterruptedException
    {
        redLabel.setVisible(false);

        int id = selectedReservation.getId();

        boolean dateValidation = coController.calculateProlongPeriodPrice(id, endDatePicker, redLabel, extraFeePeriodField);

        if (!dateValidation)
        {
            coController.getRentTotal(reservPriceField, extraFeePeriodField, extraFeeKmField, extraFeeExtrasField, totalField);
            return;
        }

        if(!coController.checkAvailability(typeComboBox.getValue().toString(), startDatePicker.getValue(), endDatePicker.getValue()))
        {
            redLabel.setText("You can't prolong the period\n       (date - not available)");

            redLabel.setVisible(true);

            extraFeePeriodField.setText("");

            return;
        }


        coController.getRentTotal(reservPriceField, extraFeePeriodField, extraFeeKmField, extraFeeExtrasField, totalField);
    }

    public void changeCamperType(ActionEvent event)
    {
        saveButton.setDisable(false);

        boolean camperIsAvailable = checkCamperTypeAvailable(typeComboBox.getSelectionModel().getSelectedItem().toString());

        if (!camperIsAvailable)
        {

            reservPriceField.setText("");
            extraFeePeriodField.setText("");
            extraFeeKmField.setText("");
            extraFeeExtrasField.setText("");

            saveButton.setDisable(true);

            coController.getRentTotal(reservPriceField, extraFeePeriodField, extraFeeKmField, extraFeeExtrasField, totalField);

            return;
        }

        changeCamperTypeNewPrice();


    }

    private void changeCamperTypeNewPrice()
    {
        String camper = typeComboBox.getSelectionModel().getSelectedItem().toString();

        String newPrice = Helper.seasonalPriceChange(startDatePicker.getValue(), endDatePicker.getValue(), coController.getCamperPrice(camper)).toString();

        reservPriceField.setText(newPrice);

        coController.getRentTotal(reservPriceField, extraFeePeriodField, extraFeeKmField, extraFeeExtrasField, totalField);
    }

    private boolean checkCamperTypeAvailable(String type)
    {
        redLabel.setVisible(false);

        if(!coController.checkAvailability(typeComboBox.getValue().toString(), startDatePicker.getValue(), endDatePicker.getValue()))
        {
            redLabel.setText("There are no available campers of this type\n       (for the selected period)");

            redLabel.setVisible(true);

            extraFeePeriodField.setText("");
            return false;
        }

        return true;
    }
}
