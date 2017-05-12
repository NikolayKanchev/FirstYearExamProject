package view;

import controller.AccController;
import controller.Converter;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.CamperType;

import java.net.URL;
import java.util.DoubleSummaryStatistics;
import java.util.ResourceBundle;

/**
 * Created by Dunkl on 08/05/2017.
 */
public class InventoryView implements Initializable
{
    //region FXML elements

    //region CamperType table view
    @FXML
    public TableView<CamperType> camperTypeTbl;
    @FXML
    public TableColumn brandClmn;
    @FXML
    public TableColumn modelClmn;
    @FXML
    public TableColumn capacityClmn;
    @FXML
    public TableColumn typePriceClmn;
    //endregion

    //region CamperType GUI-elements
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
    //endregion

    //region Camper GUI-elements
    @FXML
    public TextField plateTxtFld;
    @FXML
    public Button camperDeleteBtn;
    //endregion

    //region Extras GUI-element
    @FXML
    public TextField extraNameTxtFld;
    @FXML
    public TextField extraPriceTxtFld;
    @FXML
    public Button extraDeleteBtn;
    //endregion

    private AccController acc = new AccController();

    private ObservableList<CamperType> typeList;

    private boolean newType = false;
    private int typeId = -1;

    private boolean newCamper = false;
    private int camperId = -1;

    private boolean newExtra = false;
    private int extraId = -1;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        brandClmn.setCellValueFactory(
                new PropertyValueFactory<CamperType, String>("brand"));
        modelClmn.setCellValueFactory(
                new PropertyValueFactory<CamperType, String>("model"));
        capacityClmn.setCellValueFactory(
                new PropertyValueFactory<CamperType, Integer>("capacity"));
        typePriceClmn.setCellValueFactory(
                new PropertyValueFactory<CamperType, String>("price"));

        updateCamperTypes();
        setNewType(true);
    }

    public void updateCamperTypes()
    {
        typeList = acc.loadCamperTypes();
        camperTypeTbl.setItems(typeList);
        updateTypeFields();
    }

    private void updateTypeFields()
    {
        CamperType type =
                camperTypeTbl.getSelectionModel().getSelectedItem();

        if (type != null)
        {
            brandTxtFld.setText(type.getBrand());
            modelTxtFld.setText(type.getModel());
            capacityTxtFld.setText(type.getCapacity() + "");
            typePriceTxtFld.setText(type.getPrice() + "");
            typeDescrTxtFld.setText(type.getDescription());
        }
        else
        {
            clearTypeFields();
        }

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

        if(!newType)
        {
            CamperType type = camperTypeTbl.getSelectionModel().getSelectedItem();

            if (type != null)
            {
                typeId = type.getId();
            }
        }

        if (acc.saveCamperType(typeId, brand, model, capacity, price, descr))
        {
            updateCamperTypes();
            clearTypeFields();
        }
    }

    public void camperSaveAct(ActionEvent actionEvent)
    {

    }

    public void typeNewAct(ActionEvent actionEvent)
    {
        clearTypeFields();
        setNewType(true);
    }

    public void typeDeleteAct(ActionEvent actionEvent)
    {
        if (newType)
        {
            setNewType(false);
        }
        else
        {
            CamperType type = camperTypeTbl.getSelectionModel().getSelectedItem();

            if (type != null)
            {
                type.delete();

                updateCamperTypes();
                clearTypeFields();
            }
        }
    }

    public void camperDeleteAct(ActionEvent actionEvent)
    {

    }

    public void camperNewAct(ActionEvent actionEvent)
    {
        clearCamperFields();
        newCamper = true;
        camperDeleteBtn.setText("Cancel");
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
    }

    public void camperTypeTblAct(MouseEvent mouseEvent)
    {
        updateTypeFields();
        setNewType(false);
    }

    public void setNewType(boolean newType)
    {
        CamperType type = camperTypeTbl.getSelectionModel().getSelectedItem();
        if (!newType && type != null)
        {
            this.newType = false;
            typeDeleteBtn.setText("Delete");
        }
        else
        {
            this.newType = true;
            typeDeleteBtn.setText("Cancel");
        }
    }

    public void setNewCamper(boolean newCamper)
    {
        CamperType type = camperTypeTbl.getSelectionModel().getSelectedItem();
        if (!newCamper && type != null)
        {
            this.newCamper = true;
            camperDeleteBtn.setText("Cancel");
        }
        else
        {
            this.newCamper = false;
            camperDeleteBtn.setText("Delete");
        }
    }

    public void setNewExtra(boolean newExtra)
    {
        CamperType type = camperTypeTbl.getSelectionModel().getSelectedItem();
        if (!newExtra && type != null)
        {
            this.newExtra = true;
            extraDeleteBtn.setText("Cancel");
        }
        else
        {
            this.newExtra = false;
            extraDeleteBtn.setText("Delete");
        }
    }
}
