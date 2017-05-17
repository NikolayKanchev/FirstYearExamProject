package view;

import com.jfoenix.controls.JFXDatePicker;
import controller.COController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.CamperType;
import model.ExtraItem;
import model.Rental;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
            chooseRVType.getItems().addAll(type.getBrand());
        }

        item.setCellValueFactory(new PropertyValueFactory<String, ExtraItem>("name"));
        price.setCellValueFactory(new PropertyValueFactory<Double, ExtraItem>("price"));
        ObservableList<ExtraItem> extras = FXCollections.observableArrayList();
        extras.addAll(logic.getExtras());
        listExtras.setItems(extras);

        Screen.restrictIntInput(startDistance);
        Screen.restrictIntInput(endDistance);

    }


    public void addExtra(MouseEvent mouseEvent)
    {
        ExtraItem extraItem = (ExtraItem) listExtras.getSelectionModel().getSelectedItem();   //needed to cast
        COController.setSelectedExtra(extraItem);

        itemChosen.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceChosen.setCellValueFactory(new PropertyValueFactory<>("price"));

        chosenExtras.getItems().add(extraItem);

    }

    public void substractExtra(MouseEvent mouseEvent) {

        ExtraItem extraItem = (ExtraItem) listExtras.getSelectionModel().getSelectedItem();   //needed to cast
        COController.setSelectedExtra(extraItem);

        itemChosen.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceChosen.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        chosenExtras.getItems().remove(extraItem);

    }
}
