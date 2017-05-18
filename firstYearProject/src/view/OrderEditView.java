package view;

import com.jfoenix.controls.JFXDatePicker;
import controller.COController;
import controller.Helper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Camper;
import model.CamperType;
import model.ExtraItem;
import model.Rental;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static controller.Helper.screen;

/**
 * Created by Rasmus on 08-05-2017.
 */
public class OrderEditView implements Initializable
{

    @FXML
    ComboBox chooseRVType;
    @FXML
    JFXDatePicker startDate;
    @FXML
    JFXDatePicker endDate;

    @FXML
    Label availableLabel;
    @FXML
    Label motorhomePrice;


    @FXML
    TableView listExtras;
    @FXML
    TableColumn<String, ExtraItem> item;
    @FXML
    TableColumn<Double, ExtraItem> price;

    @FXML
    TableView chosenExtras;
    @FXML
    TableColumn<String, ExtraItem> itemChosen;
    @FXML
    TableColumn<Double, ExtraItem> priceChosen;
    @FXML
    TableColumn<Integer, Integer> quantity;


    @FXML
    TextField startDistance;
    @FXML
    TextField endDistance;
    @FXML
    TextField startLocation;
    @FXML
    TextField endLocation;

    COController logic = new COController();

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        for (CamperType type: logic.getMotorhomeTypes())
        {
            chooseRVType.getItems().addAll(type.toStringChoiceBox());
        }

        item.setCellValueFactory(new PropertyValueFactory<>("name"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

        itemChosen.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceChosen.setCellValueFactory(new PropertyValueFactory<>("price"));

        ObservableList<ExtraItem> extras = FXCollections.observableArrayList();
        extras.addAll(logic.getExtras());
        listExtras.setItems(extras);

        Screen.restrictIntInput(startDistance);
        Screen.restrictIntInput(endDistance);
    }


    public void addExtra(MouseEvent mouseEvent)
    {
        ExtraItem extraItem = (ExtraItem) listExtras.getSelectionModel().getSelectedItem();
        chosenExtras.getItems().add(extraItem);

    }

    public void substractExtra(MouseEvent mouseEvent)
    {
        ExtraItem extraItem = (ExtraItem) chosenExtras.getSelectionModel().getSelectedItem();
        chosenExtras.getItems().remove(extraItem);
    }

    public void checkAvailability(ActionEvent actionEvent)
    {
        try
        {
            String camper = chooseRVType.getSelectionModel().getSelectedItem().toString();
            if (logic.checkAvailability(camper,startDate.getValue(),endDate.getValue()))
            {
                availableLabel.setText("Available");
                motorhomePrice.setText(Helper.seasonalPriceChange(startDate.getValue(),endDate.getValue(),logic.getCamperPrice(camper)).toString());
            }
            else
            {
                availableLabel.setText("Unavailable");
            }
        }
        catch(Exception e)
        {
            screen.warning("Fill in RV type and dates", "You have not filled RV type! Please fill in data again.");
        }

    }
}
