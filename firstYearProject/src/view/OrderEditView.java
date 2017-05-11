package view;

import controller.COController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.CamperType;
import model.ExtraItem;

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
    TableView listExtras;
    @FXML
    TableColumn<String, ExtraItem> item;
    @FXML
    TableColumn<Double, ExtraItem> price;

    COController logic = new COController();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        for (CamperType type: logic.getMotorhomeTypes())
        {
            chooseRVType.getItems().addAll(type.getBrand());
        }

        item.setCellValueFactory(new PropertyValueFactory<String, ExtraItem>("name"));
        price.setCellValueFactory(new PropertyValueFactory<Double, ExtraItem>("price"));
        ObservableList<ExtraItem> extras = FXCollections.observableArrayList();
        extras.addAll(logic.getExtras());
        listExtras.setItems(extras);

    }




}
