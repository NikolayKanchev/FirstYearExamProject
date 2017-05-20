package view;

import com.jfoenix.controls.JFXDatePicker;
import controller.COController;
import controller.Helper;
import controller.LoginController;
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
import model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static controller.Helper.doubleClick;
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
    Label estimatedPrice;


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

    COController logic = new COController();

    Reservation reservation = new Reservation();

    ObservableList<ExtraItem> extraItemList = FXCollections.observableArrayList();
    ObservableList<ExtrasLineItem> lineItemList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

        chooseRVType.getItems().addAll(logic.getCamperTypes());

        //calculateDeliveryPrice();

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

        extrasPrice.setText(logic.calcExtrasPrice(lineItemList) + "");
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
        System.out.println("sum of all");
        Control[] controls = {motorhomePrice, extrasPrice, deliveryPrice};
        estimatedPrice.setText(Helper.sumOfGUI(controls) + "");
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
            } else
            {
                availableLabel.setText("Unavailable");
            }
        } catch (Exception e)
        {
            //e.printStackTrace();
            screen.warning("Fill in RV type and dates", "You have not filled RV type! Please fill in data again.");
        }
        sumOfPrices();
    }


    //region calculateDeliveryPrice NEED TO MOVE TO LOGIC
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
    //endregion  NEED TO MOVE TO LOGICNEE

    public void calcDeliveryPrice()
    {
        Helper helper = new Helper();
        double startKm = helper.doubleFromTxt(startDistance.getText());
        double endKm = helper.doubleFromTxt(endDistance.getText());

        System.out.println(startKm + " " + endKm);
        System.out.println(reservation.getRvTypeID());

        if (reservation.getRvTypeID() < 1)
        {
            screen.warning("no motorhome",
                    "No available motor home selected.");
        }

        if (startKm < 0 ||endKm < 0 ||
                reservation == null)
        {
            return;
        }

        double totalDelivery = logic.calculateDeliveryPrice(
                startKm, endKm, reservation.getRvTypeID());

        deliveryPrice.setText(totalDelivery + "");

        sumOfPrices();
    }

    public void checkFields(ActionEvent actionEvent)
    {


        calcDeliveryPrice();
        /*String tester = "";
        System.out.println("Value: " + startDistance.getText() + "...");
        System.out.println("Value: " + tester + "...");
        if (startDistance.getText() != (""))
        {
            System.out.println("SUCCESS");
        }*/
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

        if (!availableLabel.getText().equals("Available"))
        {
            screen.warning("Incomplete reservation",
                    "Please select an available motorhome");
            return false;
        }

        Helper helper = new Helper();
        double price = helper.doubleFromTxt(estimatedPrice.getText());
        if (price == -12345)
        {
            return false;
        }

        double extraKmStart = helper.doubleFromTxt(startDistance.getText());
        double extraKmEnd = helper.doubleFromTxt(endDistance.getText());

        if (extraKmStart == -12345)
        {
            extraKmStart = 0;
        }
        if (extraKmEnd == -12345)
        {
            extraKmEnd = 0;
        }

        Date startDateSql = Date.valueOf(startDate.getValue());
        Date endDateSql = Date.valueOf(startDate.getValue());
        Date today = Date.valueOf(LocalDate.now());

        reservation = new Reservation(
                -1, startDateSql, endDateSql, startLocation.getText(),
                endLocation.getText(), LoginController.getPersonId(),
                today, "reservation", price, extraKmStart, extraKmEnd);
        return true;
    }

    public void nextBtnAct(ActionEvent actionEvent)
    {
        if (!setReservation())
        {
            return;
        }

        Screen screen = new Screen();
        screen.changeToCustInfo(actionEvent, reservation, lineItemList);
    }

    public void startLocChanged(KeyEvent keyEvent)
    {
        endLocation.setText(startLocation.getText());
    }
}

