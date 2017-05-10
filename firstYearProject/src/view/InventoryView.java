package view;

import controller.AccController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Dunkl on 08/05/2017.
 */
public class InventoryView implements Initializable
{
    //region FXML elements
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
        if(newType)
        {



            //acc.createCamperType();
        }
        else
        {

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
}
