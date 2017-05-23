package view;

import com.jfoenix.controls.JFXDatePicker;
import controller.COController;
import controller.Helper;
import controller.LoginController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;
import java.util.ResourceBundle;

import static controller.Helper.screen;

/**
 * Created by Rasmus on 08-05-2017.
 */
public class OrderEditView implements Initializable
{

    @FXML
    ComboBox<CamperType> chooseRVType;
    @FXML
    JFXDatePicker startDate;
    @FXML
    JFXDatePicker endDate;

    @FXML
    Label availableLabel;
    @FXML
    Label motorhomePrice;
    @FXML
    Label extrasPrice;
    @FXML
    Label deliveryPrice;
    @FXML
    Label estimatedPrice, redLabel;


    @FXML
    TableView<ExtraItem> listExtras;
    @FXML
    TableColumn<String, ExtraItem> item;
    @FXML
    TableColumn<Double, ExtraItem> price;

    @FXML
    TableView<ExtrasLineItem> chosenExtras;
    @FXML
    TableColumn<String, ExtrasLineItem> itemChosen;
    @FXML
    TableColumn<Integer, ExtrasLineItem> quantityChosen;
    @FXML
    TableColumn<Double, ExtrasLineItem> priceChosen;

    @FXML
    TextField startDistance;
    @FXML
    TextField endDistance;
    @FXML
    TextField startLocation;
    @FXML
    TextField endLocation;

    @FXML
    TextField customerIDField;

    COController logic = new COController();

    Reservation reservation = new Reservation();

    ObservableList<ExtraItem> extraItemList = FXCollections.observableArrayList();

    ObservableList<ExtrasLineItem> lineItemList = FXCollections.observableArrayList();

    ObservableList<CamperType> camperList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

        customerIDField.setText(String.valueOf(COController.getCreatedCustomerID()));

        redLabel.setVisible(false);
        camperList.clear();
        camperList.addAll(logic.getCamperTypes());
        chooseRVType.setItems(camperList);

        item.setCellValueFactory(new PropertyValueFactory<>("name"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

        itemChosen.setCellValueFactory(new PropertyValueFactory<>("extraItemName"));
        quantityChosen.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceChosen.setCellValueFactory(new PropertyValueFactory<>("subTotal"));

        ObservableList<ExtraItem> extras = FXCollections.observableArrayList();
        extras.addAll(logic.getExtras());
        listExtras.setItems(extras);

        Screen.restrictNumberInput(startDistance);
        Screen.restrictNumberInput(endDistance);

        updateExtrasTables();
    }

    public void updateFields(Reservation reservation, Collection<ExtrasLineItem> lineItems)
    {
        this.reservation = reservation;

        if (lineItems != null)
        {
            this.lineItemList.clear();
            this.lineItemList.addAll(lineItems);
            updateExtrasTables();
        }

        startLocation.setText(reservation.getStartLocation());
        startDistance.setText(reservation.getExtraKmStart() + "");
        endLocation.setText(reservation.getEndLocation());
        endDistance.setText(reservation.getExtraKmEnd() + "");

        calcDeliveryPrice();

        if (reservation.getRvTypeID() > 0)
        {
            CamperType type = null;

            for (CamperType thisType : camperList)
            {
                if (thisType.getId() == reservation.getRvTypeID())
                {
                    type = thisType;
                    break;
                }
            }

            if (type != null)
            {
                chooseRVType.getSelectionModel().select(type);
            }

            if (reservation.getStartDate() != null)
            {
                startDate.setValue(reservation.getStartDate().toLocalDate());
                endDate.setValue(reservation.getEndDate().toLocalDate());
            }

            checkAvailable();
        }
    }

    private void updateExtrasTables()
    {
        extraItemList.clear();
        extraItemList.addAll(logic.getExtras());

        listExtras.setItems(extraItemList);
        chosenExtras.setItems(lineItemList);
    }

    public void addExtra(MouseEvent mouseEvent)
    {
        ExtraItem item = listExtras.getSelectionModel().getSelectedItem();
        lineItemList = logic.addToExtraLocal(item, lineItemList);
        updateExtrasTables();
        sumOfPrices();
    }

    public void subtractExtra(MouseEvent mouseEvent)
    {
        ExtrasLineItem lineItem = chosenExtras.getSelectionModel().getSelectedItem();
        lineItemList = logic.subtractExtraLocal(lineItem, lineItemList, extraItemList);
        updateExtrasTables();

        extrasPrice.setText(logic.calcExtrasPrice(lineItemList) + "");
        sumOfPrices();
    }

    private void sumOfPrices()
    {
        extrasPrice.setText(logic.calcExtrasPrice(lineItemList) + "");
        Control[] controls = {motorhomePrice, extrasPrice, deliveryPrice};
        estimatedPrice.setText(Helper.sumOfGUI(controls) + "");
        redLabel.setVisible(false);
    }

    public void checkAvailability(ActionEvent actionEvent)
    {
        checkAvailable();
    }

    private void checkAvailable()
    {
        try
        {
            CamperType camperType = chooseRVType.getSelectionModel().getSelectedItem();

            if (logic.checkAvailability(camperType.getId(), startDate.getValue(), endDate.getValue()))
            {
                availableLabel.setText("Available");
                motorhomePrice.setText(Helper.seasonalPriceChange(startDate.getValue(), endDate.getValue(), logic.getCamperPrice(camperType.getId())).toString());
                reservation.setRvTypeID(camperType.getId());
                //setReservation();


                CamperType type = chooseRVType.getSelectionModel().getSelectedItem();
                reservation.setRvTypeID(type.getId());

                Date startDateSql = Date.valueOf(startDate.getValue());
                Date endDateSql = Date.valueOf(endDate.getValue());

                reservation.setStartDate(startDateSql);
                reservation.setEndDate(endDateSql);

                calcDeliveryPrice();
            } else
            {
                availableLabel.setText("Unavailable");
            }
        } catch (Exception e)
        {
            //e.printStackTrace();
            screen.warning("Fill in RV type and dates", "You have not filled RV type or dates! Please fill in data again.");
        }
        sumOfPrices();
    }


    //region calculateDeliveryPrice NEED TO MOVE TO LOGIC  CAN BE DELETED!!!
    /*public void calculateDeliveryPrice()
    {
        startDistance.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
            {
                double startKm;
                double endKm;

                if (newValue.matches(""))
                {
                    startKm = 0;
                }
                else
                {
                    startKm = Double.parseDouble(newValue);
                }
                if (!endDistance.getText().equals(""))
                {
                    endKm = Double.parseDouble(endDistance.getText());
                }
                else
                {
                    endKm = 0;
                }
                String type = (String) chooseRVType.getSelectionModel().getSelectedItem();
                String price = String.valueOf((logic.calculateDeliveryPrice(startKm, endKm, type)));
                deliveryPrice.setText(price);
            }

        });


        endDistance.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
            {
                double endKm;
                double startKm;
                if (newValue.matches(""))
                {
                    endKm = 0;
                }
                else
                {
                    endKm = Double.parseDouble(newValue);
                }
                if (!startDistance.getText().equals(""))
                {
                    startKm = Double.parseDouble(startDistance.getText());
                }
                else
                {
                    startKm = 0;
                }

                String type = (String) chooseRVType.getSelectionModel().getSelectedItem();
                String price = String.valueOf((logic.calculateDeliveryPrice(startKm, endKm, type)));
                deliveryPrice.setText(price);
            }

        });

    }*/
    //endregion  NEED TO MOVE TO LOGIC

    public void calcDeliveryPrice()
    {
        Helper helper = new Helper();

        double startKm = helper.doubleFromTxt(startDistance.getText());
        double endKm = helper.doubleFromTxt(endDistance.getText());

        if (reservation.getRvTypeID() < 1)
        {
            return;
        }

        if (startKm < 0 ||endKm < 0 ||
                reservation == null)
        {
            return;
        }

        double totalDelivery = logic.calculateDeliveryPrice(
                startKm, endKm, reservation.getRvTypeID());

        deliveryPrice.setText(totalDelivery + "");

        reservation.setStartLocation(startLocation.getText());
        reservation.setExtraKmStart(startKm);
        reservation.setEndLocation(endLocation.getText());
        reservation.setExtraKmEnd(endKm);

        sumOfPrices();
    }

    public void checkFields(ActionEvent actionEvent)
    {
        calcDeliveryPrice();
    }

    public void cancelBtnAct(ActionEvent actionEvent)
    {
        Screen screen = new Screen();
        try
        {
            screen.change(actionEvent, "orders.fxml");
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public boolean setReservation()
    {
        Helper helper = new Helper();

        int customerId = helper.intFromString(customerIDField.getText());

        if(customerId <= 0)
        {
            redLabel.setVisible(true);
            return false;
        }

        redLabel.setVisible(false);

        if (!availableLabel.getText().equals("Available"))
        {
            screen.warning("Incomplete reservation",
                    "Please select an available motorhome");
            return false;
        }

        double price = helper.doubleFromTxt(estimatedPrice.getText());
        if (price <= 0)
        {
            return false;
        }

        Date today = Date.valueOf(LocalDate.now());

        reservation.setState("reservation");
        reservation.setEstimatedPrice(price);
        reservation.setCustomerID(customerId);
        reservation.setCreationDate(today);
        reservation.setAssistantID(LoginController.getPersonId());

        CamperType type = chooseRVType.getSelectionModel().getSelectedItem();
        reservation.setRvTypeID(type.getId());

        return true;
    }

    public void nextBtnAct(ActionEvent actionEvent)
    {
        Screen screen = new Screen();

        screen.changeToCustInfo(actionEvent, reservation, lineItemList);
    }

    public void saveNewReservation(ActionEvent event)
    {
        if (setReservation())
        {
            System.out.println("success");
            logic.saveNewReservation(event, reservation, lineItemList);
        }
        else
        {
            System.out.println("wrong");
        }
    }
}

