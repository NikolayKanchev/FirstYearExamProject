package view;

import controller.AccController;
import controller.Converter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.CamperType;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Dunkl on 08/05/2017.
 */
public class InventoryView implements Initializable
{
    //region FXML elements
    @FXML
    public TableView<CamperType> camperTypeTbl;
    @FXML
    public TextField brandTxtFld;
    @FXML
    public TextField modelTxtFld;
    @FXML
    public TextField capacityTxtFld;
    @FXML
    public TextField typePriceTxtFld;
    @FXML
    public TextField typeDescrTxtFld;
    @FXML
    public Button typeDeleteBtn;
    @FXML
    public TextField plateTxtFld;
    @FXML
    public Button camperDeleteBtn;
    @FXML
    public TextField extraNameTxtFld;
    @FXML
    public TextField extraPriceTxtFld;
    @FXML
    public Button extraDeleteBtn;
    //endregion

    private AccController acc = new AccController();

    private boolean newType = false;
    private int typeId = -1;

    private boolean newCamper = false;
    private int camperId = -1;

    private boolean newExtra = false;
    private int extraId = -1;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

    }

    public void updateTables()
    {

    }

    private void updateTypeFields()
    {

    }

    private void clearTypeFields()
    {
        brandTxtFld.setText("");
        modelTxtFld.setText("");
        capacityTxtFld.setText("");
        typePriceTxtFld.setText("");
        typeDescrTxtFld.setText("");
    }

    private void clearCamperFields()
    {
        plateTxtFld.setText("");
    }

    private void clearExtraFields()
    {
        extraNameTxtFld.setText("");
        extraPriceTxtFld.setText("");

    }

    public void typeSaveAct(ActionEvent actionEvent)
    {
        Converter c = new Converter();

        String brand = brandTxtFld.getText();
        String model = modelTxtFld.getText();
        int capacity = c.intFromString(capacityTxtFld.getText());
        double price = c.doubleFromTxt(typePriceTxtFld.getText());
        String descr = typeDescrTxtFld.getText();

        if (capacity == -12345 || price == -12345)
        {
            return;
        }

        if (c.hasEmptyTxt(new String[]{brand, model}))
        {
            return;
        }

        int typeId = -1;

        if(!newType && camperTypeTbl.getItems().size() < 0)
        {
            CamperType type = camperTypeTbl.getSelectionModel().getSelectedItem();

            typeId = type.getId();
        }

        if (acc.saveCamperType(typeId, brand, model, capacity, price, descr))
        {
            typeDeleteBtn.setText("Delete");
        }
    }

    public void camperSaveAct(ActionEvent actionEvent)
    {

    }

    public void typeNewAct(ActionEvent actionEvent)
    {
        clearTypeFields();
        newType = true;
        typeDeleteBtn.setText("Cancel");

        if (newCamper)
        {
            clearCamperFields();
            camperDeleteBtn.setText("Delete");
            newCamper = false;
        }
        if (newExtra)
        {
            clearExtraFields();
            extraDeleteBtn.setText("Delete");
            newExtra = false;
        }
    }

    public void typeDeleteAct(ActionEvent actionEvent)
    {

    }

    public void camperDeleteAct(ActionEvent actionEvent)
    {

    }

    public void camperNewAct(ActionEvent actionEvent)
    {
        clearCamperFields();
        newCamper = true;
        camperDeleteBtn.setText("Cancel");

        if (newType)
        {
            clearTypeFields();
            typeDeleteBtn.setText("Delete");
            newType = false;
        }
        if (newExtra)
        {
            clearExtraFields();
            extraDeleteBtn.setText("Delete");
            newExtra = false;
        }
    }

    public void extraSaveAct(ActionEvent actionEvent)
    {

    }

    public void extraDeleteAct(ActionEvent actionEvent)
    {

    }

    public void extraNewAct(ActionEvent actionEvent)
    {
        clearExtraFields();
        newExtra = true;
        extraDeleteBtn.setText("Cancel");

        if (newType)
        {
            clearTypeFields();
            typeDeleteBtn.setText("Delete");
            newType = false;
        }

        if (newCamper)
        {
            clearCamperFields();
            camperDeleteBtn.setText("Delete");
            newCamper = false;
        }
    }

    public void camperTypeTblAct(MouseEvent mouseEvent)
    {
        System.out.println("jdsfkdjf");
    }
}
