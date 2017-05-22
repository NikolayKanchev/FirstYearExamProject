package view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import controller.COController;
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
import model.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RentalView implements Initializable
{

    private COController coController = new COController();

    private Screen screen = new Screen();

    private Rental selectedRental;

    private Object timePeriod;

    @FXML
    TextField reservationIDField, assistantIDField, startLocationField, startKmField,
            endLocationField, endKmField, reservPriceField, extraFeePeriodField,
            extraFeeKmField, extraFeeExtrasField, totalField, camperID, custIdField;

    @FXML
    JFXButton saveButton, dropOffButton, invoicesButton;

    @FXML
    JFXTextField possibleLabel;
    @FXML
    JFXDatePicker startDatePicker, endDatePicker;
    @FXML
    JFXComboBox<CamperType> typeComboBox;
    @FXML
    ChoiceBox exitOptions;
    @FXML
    Label redLabel;

    @FXML
    TableView<ExtraItem> extrasTable;
    @FXML
    TableColumn<String, ExtraItem> extrasItemColumn;
    @FXML
    TableColumn<Double, ExtraItem> extrasPriceColumn;

    @FXML
    TableView<ExtrasLineItem> extrasLineItemTable;
    @FXML
    TableColumn<String, ExtrasLineItem> lineItemName;
    @FXML
    TableColumn<Integer, ExtrasLineItem> quantityColumn;
    @FXML
    TableColumn<Double, ExtrasLineItem> subTotalColumn;


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        dropOffButton.setVisible(false);

        invoicesButton.setVisible(false);

        selectedRental = COController.getSelectedRental();

        if(coController.getInvoices(selectedRental.getId()).isEmpty())
        {
            dropOffButton.setVisible(true);

        }else
        {
            invoicesButton.setVisible(true);
        }

        timePeriod = COController.getSelectedTimePeriod();

        redLabel.setVisible(false);

        exitOptions.setItems(FXCollections.observableArrayList("Log out", "Exit"));


        loadData();

        Tooltip tooltip = new Tooltip("Press Enter after you type the number");

        startKmField.setTooltip(tooltip);

        endKmField.setTooltip(tooltip);
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
        startKmField.setText(String.valueOf(selectedRental.getExtraKmStart()));
        endKmField.setText(String.valueOf(selectedRental.getExtraKmEnd()));
        reservPriceField.setText(String.valueOf(selectedRental.getReservPrice()));


        typeComboBox.setItems(coController.getMotorhomeTypes());
        typeComboBox.getSelectionModel().selectFirst();

        //region table extraItems
        extrasItemColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        extrasPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        ObservableList<ExtraItem> extraItems = FXCollections.observableArrayList();
        extraItems.addAll(coController.getExtras());
        extrasTable.setItems(extraItems);
//endregion

        loadExtraLineItems();

        coController.calculateExtraLinesItemsTotal(extrasLineItemTable, extraFeeExtrasField);

        coController.getRentTotal(reservPriceField, extraFeePeriodField, extraFeeKmField, extraFeeExtrasField, totalField);

        calculateAtTheBegining();

    }

    private void loadExtraLineItems()
    {
        lineItemName.setCellValueFactory(new PropertyValueFactory<>("extraItemName"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        subTotalColumn.setCellValueFactory(new PropertyValueFactory<>("subTotal"));


        ObservableList<ExtrasLineItem> lineItems = FXCollections.observableArrayList();
        lineItems.addAll(coController.getExtrasLineItems(selectedRental.getId(), "rental"));
        extrasLineItemTable.setItems(lineItems);

    }

    public void goBack(ActionEvent event) throws IOException
    {
        screen.change(event, "orders.fxml");
    }

    public void exitOrLogOut(MouseEvent mouseEvent)
    {
        screen.exitOrLogOut(mouseEvent, exitOptions);
    }

    public void saveChanges(ActionEvent event) throws IOException
    {

        boolean emptyFields = coController.checkAreFieldsEmpty(startLocationField, endLocationField, startKmField, endKmField, redLabel);

        if (!emptyFields)
        {
            redLabel.setText("You have to fill out all the fields");
            redLabel.setVisible(true);
            return;
        }

        double startKm = Double.parseDouble(startKmField.getText());
        double endKm = Double.parseDouble(endKmField.getText());
        LocalDate endDate = endDatePicker.getValue();
        String startLocation = startLocationField.getText();
        String endLocation = endLocationField.getText();

        coController.updateRental(selectedRental,startLocation, endLocation, endDate, startKm, endKm);

        goBack(event);
    }

    public void goToReservation(ActionEvent event) throws IOException
    {
        screen.change(event, "orderedit.fxml");
    }

    public void goToCustomer(ActionEvent event) throws IOException
    {
        COController.setSelectedCustomerID(selectedRental.getCustomer_id());

        screen.change(event, "customerdetails.fxml");
    }

    public void printContract(ActionEvent event) throws IOException
    {
        COController.setSelectedRental(selectedRental);
        screen.change(event, "contract.fxml");
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
        disableFieldsAndButton(false);

        redLabel.setVisible(false);

        int dateChoice = coController.validateEndDateChoice(endDatePicker, redLabel, extraFeePeriodField, "rental");

        if(dateChoice == 1)
        {
            disableFieldsAndButton(true);
            return;
        }

        if(dateChoice ==2 || dateChoice == 3)
        {
            return;
        }

        int id = selectedRental.getReservID();

        boolean dateValidation = coController.calculateProlongPeriodPrice(id, endDatePicker, redLabel, extraFeePeriodField);

        if (!dateValidation)
        {
            coController.getRentTotal(reservPriceField, extraFeePeriodField, extraFeeKmField, extraFeeExtrasField, totalField);
            return;
        }

        if(!coController.checkAvailability(typeComboBox.getValue().getId(), startDatePicker.getValue(), endDatePicker.getValue()))
        {
            redLabel.setText("You can't prolong the period\n       (date - not available)");

            redLabel.setVisible(true);

            extraFeePeriodField.setText("");

            disableFieldsAndButton(true);

            return;
        }


        coController.getRentTotal(reservPriceField, extraFeePeriodField, extraFeeKmField, extraFeeExtrasField, totalField);
    }

    private void disableFieldsAndButton(boolean truOrFalse)
    {
        extrasTable.setDisable(truOrFalse);
        extrasLineItemTable.setDisable(truOrFalse);
        startLocationField.setDisable(truOrFalse);
        endLocationField.setDisable(truOrFalse);
        startKmField.setDisable(truOrFalse);
        endKmField.setDisable(truOrFalse);
        saveButton.setDisable(truOrFalse);
    }

    public void addExtraItem(MouseEvent mouseEvent)
    {
        extrasTable.setOnMousePressed(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent)
            {

                if (mouseEvent.isPrimaryButtonDown() && mouseEvent.getClickCount() == 2)
                {
                   ExtraItem chosenItem =  extrasTable.getSelectionModel().getSelectedItem();

                   coController.addExtraLineItem(chosenItem, extrasLineItemTable,
                           selectedRental.getId(), "rental");

                   loadExtraLineItems();

                   coController.calculateExtraLinesItemsTotal(extrasLineItemTable, extraFeeExtrasField);

                    coController.getRentTotal(reservPriceField, extraFeePeriodField, extraFeeKmField, extraFeeExtrasField, totalField);
                }
            }
        });

    }

    public void subtractExtraItem(MouseEvent mouseEvent)
    {
        extrasLineItemTable.setOnMousePressed(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent)
            {
                if (mouseEvent.isPrimaryButtonDown() && mouseEvent.getClickCount() == 2)
                {

                    ExtrasLineItem chosenExLineItem = extrasLineItemTable.getSelectionModel().getSelectedItem();

                    coController.subtractExtraLineItemQuantity(chosenExLineItem);

                    loadExtraLineItems();

                    coController.calculateExtraLinesItemsTotal(extrasLineItemTable, extraFeeExtrasField);

                    coController.getRentTotal(reservPriceField, extraFeePeriodField, extraFeeKmField, extraFeeExtrasField, totalField);

                }
            }
        });

    }

    public void calculateAtTheBegining()
    {
        coController.calculateKmAndSetTotal(
                endKmField, extraFeeKmField, totalField, extraFeeKmField,
                reservPriceField, extraFeePeriodField, extraFeeExtrasField);

        int id = selectedRental.getReservID();
        coController.calculateProlongPeriodPrice(id, endDatePicker, redLabel, extraFeePeriodField);
        redLabel.setVisible(false);
        coController.getRentTotal(reservPriceField, extraFeePeriodField, extraFeeKmField, extraFeeExtrasField, totalField);

    }

    public void dropOff(ActionEvent event) throws IOException
    {
        // an invoice
        //create a servise

        System.out.println(selectedRental.getId());

        coController.createInvoice(selectedRental, totalField, extraFeePeriodField, extraFeeKmField, extraFeeExtrasField);

        ArrayList<Invoice> invoices = coController.getInvoices(selectedRental.getId());

        screen.change(event, "invoice.fxml");
    }

    public void manageInvoices(ActionEvent event) throws IOException
    {



        screen.change(event, "invoice.fxml");
    }
}
