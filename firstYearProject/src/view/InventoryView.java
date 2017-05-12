package view;

import controller.AccController;
import controller.Converter;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.CamperType;
import model.ExtraItem;
import model.Motorhome;

import java.net.URL;
import java.util.DoubleSummaryStatistics;
import java.util.ResourceBundle;

/**
 * Created by Dunkl on 08/05/2017.
 */
public class InventoryView implements Initializable
{
    //region FXML elements

    //region CamperType-table
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

    //region Camper-table
    @FXML
    public TableView<Motorhome> camperTbl;
    @FXML
    public TableColumn plateClmn;
    @FXML
    public TableColumn camperReadyClmn;
    @FXML
    public TableColumn kmCountClmn;
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
    public ComboBox<CamperType> typeCmbBox;
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

    //endregion

    //region Other fields
    private AccController acc = new AccController();

    private ObservableList<CamperType> typeList;
    private ObservableList<Motorhome> camperList;
    private ObservableList<ExtraItem> extrasList;

    private boolean newTypeMode = false;
    private int typeId = -1;

    private boolean newCamperMode = false;
    private int camperId = -1;

    private boolean newExtraMode = false;
    private int extraId = -1;
    //endregion

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
                new PropertyValueFactory<CamperType, Double>("price"));

        plateClmn.setCellValueFactory(
                new PropertyValueFactory<Motorhome, String>("plate"));
        camperReadyClmn.setCellValueFactory(
                new PropertyValueFactory<Motorhome, String>("status"));
        kmCountClmn.setCellValueFactory(
                new PropertyValueFactory<Motorhome, Double>("kmCount"));

        updateCamperTypes();
        updateCampers();
        setNewTypeMode(true);
        setNewCamperMode(true);
        setNewExtraMode(true);
    }

    //region Updating GUI
    public void updateCamperTypes()
    {
        typeList = acc.loadCamperTypes();
        camperTypeTbl.setItems(typeList);
        updateTypeFields();
        typeCmbBox.setItems(typeList);
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

    public void updateCampers()
    {
        camperList = acc.loadCampers();
        camperTbl.setItems(camperList);
        updateCamperFields();
    }

    private void updateCamperFields()
    {
        Motorhome camper =
                camperTbl.getSelectionModel().getSelectedItem();

        if (camper != null)
        {
            typeLoop : for (CamperType type : typeList)
            {
                if (type.getId() == camper.getRvTypeID())
                {
                    camper.setCamperType(type);
                    break typeLoop;
                }
            }

            typeCmbBox.setValue(camper.getCamperType());
            plateTxtFld.setText(camper.getPlate());
        }
        else
        {
            clearCamperFields();
        }
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
    //endregion

    //region "Save"-button
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

        if(!newTypeMode)
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

        if(!newTypeMode)
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

    public void extraSaveAct(ActionEvent actionEvent)
    {

    }
    //endregion

    //region "New"-button
    public void typeNewAct(ActionEvent actionEvent)
    {
        clearTypeFields();
        setNewTypeMode(true);
    }

    public void camperNewAct(ActionEvent actionEvent)
    {
        clearCamperFields();
        newCamperMode = true;
        camperDeleteBtn.setText("Cancel");
    }

    public void extraNewAct(ActionEvent actionEvent)
    {
        clearExtraFields();
        newExtraMode = true;
        extraDeleteBtn.setText("Cancel");
    }
    //endregion

    //region "Delete"-button
    public void typeDeleteAct(ActionEvent actionEvent)
    {
        if (newTypeMode)
        {
            setNewTypeMode(false);
        }
        else
        {
            CamperType type = camperTypeTbl.getSelectionModel().getSelectedItem();

            if (type != null)
            {
                acc.deleteCamperType(type.getId());

                updateCamperTypes();
                clearTypeFields();
            }
        }
    }

    public void camperDeleteAct(ActionEvent actionEvent)
    {

    }

    public void extraDeleteAct(ActionEvent actionEvent)
    {

    }
    //endregion

    //region Table actions
    public void camperTypeTblAct(MouseEvent mouseEvent)
    {
        updateTypeFields();
        setNewTypeMode(false);
    }

    public void camperTblAct(MouseEvent mouseEvent)
    {
        updateCamperFields();
        setNewCamperMode(false);
    }
    //endregion

    //region "new"-mode
    public void setNewTypeMode(boolean newTypeMode)
    {
        CamperType type = camperTypeTbl.getSelectionModel().getSelectedItem();
        if (!newTypeMode && type != null)
        {
            this.newTypeMode = false;
            typeDeleteBtn.setText("Delete");
        }
        else
        {
            this.newTypeMode = true;
            typeDeleteBtn.setText("Cancel");
        }
    }

    public void setNewCamperMode(boolean newCamperMode)
    {
        CamperType type = camperTypeTbl.getSelectionModel().getSelectedItem();
        if (!newCamperMode && type != null)
        {
            this.newCamperMode = true;
            camperDeleteBtn.setText("Cancel");
        }
        else
        {
            this.newCamperMode = false;
            camperDeleteBtn.setText("Delete");
        }
    }

    public void setNewExtraMode(boolean newExtraMode)
    {
        CamperType type = camperTypeTbl.getSelectionModel().getSelectedItem();
        if (!newExtraMode && type != null)
        {
            this.newExtraMode = true;
            extraDeleteBtn.setText("Cancel");
        }
        else
        {
            this.newExtraMode = false;
            extraDeleteBtn.setText("Delete");
        }
    }
    //endregion
}
