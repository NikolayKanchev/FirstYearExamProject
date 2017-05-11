package view;

import controller.COController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import model.CamperType;

import java.util.ArrayList;

/**
 * Created by Rasmus on 08-05-2017.
 */
public class OrderEditView
{

    @FXML
    ComboBox chooseRVType;

    COController logic = new COController();

    public void chooseRVType(MouseEvent actionEvent)
    {

        for (CamperType type: logic.getMotorhomeTypes())
        {
            chooseRVType.getItems().addAll(type.getBrand());
        }


    }
}
