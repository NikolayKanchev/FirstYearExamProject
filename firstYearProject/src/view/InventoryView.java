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
    @FXML
    public Label typeMsgLbl;
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

    //region Camper GUI-elements
    @FXML
    public ComboBox<CamperType> typeCmbBox;
    @FXML
    public TextField plateTxtFld;
    @FXML
    public Label statusLbl;
    @FXML
    public Label kmCountLbl;
    @FXML
    public Button camperDeleteBtn;
    @FXML
    public Label camperMsgLbl;
    //endregion

    //region Extras table
    @FXML
    public TableView<ExtraItem> extrasTbl;
    @FXML
    public TableColumn extrasNameClmn;
    @FXML
    public TableColumn extrasPriceClmn;
    //endregion

    //region Extras GUI-elements
    @FXML
    public TextField extraNameTxtFld;
    @FXML
    public TextField extraPriceTxtFld;
    @FXML
    public Button extraDeleteBtn;
    @FXML
    public Label extrasMsgLbl;
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

        extrasNameClmn.setCellValueFactory(
                new PropertyValueFactory<Motorhome, String>("name"));
        extrasPriceClmn.setCellValueFactory(
                new PropertyValueFactory<Motorhome, Double>("price"));

        updateCamperTypes();
        updateCampers();
        updateExtras();
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

    public void updateCampers()
    {
        camperList = acc.loadCampers();
        camperTbl.setItems(camperList);
        updateCamperFields();
    }

    public void updateExtras()
    {
        extrasList = acc.loadExtraItems();
        extrasTbl.setItems(extrasList);
        updateExtrasFields();
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

        typeMsgLbl.setText("");
        camperMsgLbl.setText("");
        extrasMsgLbl.setText("");
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
            statusLbl.setText(camper.getStatus());
            kmCountLbl.setText(camper.getKmCount() + "");
        }
        else
        {
            clearCamperFields();
        }

        typeMsgLbl.setText("");
        camperMsgLbl.setText("");
        extrasMsgLbl.setText("");
    }

    private void updateExtrasFields() //not
    {
        ExtraItem item =
                extrasTbl.getSelectionModel().getSelectedItem();

        if (item != null)
        {
            extraNameTxtFld.setText(item.getName());
            extraPriceTxtFld.setText(item.getPrice() + "");
        }
        else
        {
            clearExtrasFields();
        }

        typeMsgLbl.setText("");
        camperMsgLbl.setText("");
        extrasMsgLbl.setText("");
    }

    private void clearTypeFields()
    {
        brandTxtFld.setText("");
        modelTxtFld.setText("");
        capacityTxtFld.setText("");
        typePriceTxtFld.setText("");
        typeDescrTxtFld.setText("");

        typeMsgLbl.setText("");
        camperMsgLbl.setText("");
        extrasMsgLbl.setText("");
    }

    private void clearCamperFields()
    {
        typeCmbBox.setValue(null);
        plateTxtFld.setText("");
        statusLbl.setText("");
        kmCountLbl.setText("");

        typeMsgLbl.setText("");
        camperMsgLbl.setText("");
        extrasMsgLbl.setText("");
    }

    private void clearExtrasFields()
    {
        extraNameTxtFld.setText("");
        extraPriceTxtFld.setText("");

        typeMsgLbl.setText("");
        camperMsgLbl.setText("");
        extrasMsgLbl.setText("");
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

        if (c.hasEmptyTxt(new String[]{brand, model}))
        {
            typeMsgLbl.setText("fields missing");
            return;
        }

        if (capacity == -12345 || price == -12345)
        {
            typeMsgLbl.setText("wrong input");
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
            else
            {
                typeMsgLbl.setText("non selected");
                return;
            }
        }

        if (acc.saveCamperType(typeId, brand, model, capacity, price, descr))
        {
            updateCamperTypes();
            clearTypeFields();

            typeMsgLbl.setText("saved");
            return;
        }

        typeMsgLbl.setText("not saved");
    }

    public void camperSaveAct(ActionEvent actionEvent)
    {
        int camperId = -1;
        String plate = plateTxtFld.getText();
        int typeId;
        CamperType type = typeCmbBox.getSelectionModel().getSelectedItem();
        String status = "available";
        double kmCount = 0;

        if (plate == null || plate.isEmpty() || type == null)
        {
            camperMsgLbl.setText("fields missing");
            return;
        }

        typeId = type.getId();

        if(!newCamperMode)
        {
            Motorhome camper = camperTbl.getSelectionModel().getSelectedItem();

            if (camper != null)
            {
                camperId = camper.getId();
                status = camper.getStatus();
                kmCount = camper.getKmCount();
            }
            else
            {
                camperMsgLbl.setText("non selected");
                return;
            }
        }

        if (acc.saveCamper(camperId, typeId, plate, status, kmCount))
        {
            updateCampers();
            clearCamperFields();
            camperMsgLbl.setText("saved");
            return;
        }

        camperMsgLbl.setText("not saved");
    }

    public void extraSaveAct(ActionEvent actionEvent)
    {
        Converter c = new Converter();

        String name = extraNameTxtFld.getText();
        double price = c.doubleFromTxt(extraPriceTxtFld.getText());

        if (name == null || name.isEmpty())
        {
            extrasMsgLbl.setText("fields missing");
            return;
        }

        if (price == -12345)
        {
            extrasMsgLbl.setText("wrong input");
            return;
        }

        int itemId = -1;

        if(!newExtraMode)
        {
            ExtraItem item = extrasTbl.getSelectionModel().getSelectedItem();

            if (item != null)
            {
                itemId = item.getId();
            }
            else
            {
                extrasMsgLbl.setText("non selected");
                return;
            }
        }

        if (acc.saveExtraItem(itemId, name, price))
        {
            updateExtras();
            clearExtrasFields();

            extrasMsgLbl.setText("saved");
            return;
        }

        extrasMsgLbl.setText("not saved");
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
        setNewCamperMode(true);
    }

    public void extraNewAct(ActionEvent actionEvent)
    {
        clearExtrasFields();
        setNewExtraMode(true);
    }
    //endregion

    //region "Delete"-button
    public void typeDeleteAct(ActionEvent actionEvent)
    {
        if (newTypeMode)
        {
            clearTypeFields();
            typeMsgLbl.setText("canceled");
        }
        else
        {
            CamperType type = camperTypeTbl.getSelectionModel().getSelectedItem();

            if (type != null)
            {
                acc.deleteCamperType(type.getId());

                updateCamperTypes();
                clearTypeFields();
                typeMsgLbl.setText("deleted");
            }
            else
            {
                typeMsgLbl.setText("non selected");
            }
        }
    }

    public void camperDeleteAct(ActionEvent actionEvent)
    {
        if (newCamperMode)
        {
            clearCamperFields();
            camperMsgLbl.setText("canceled");
        }
        else
        {
            Motorhome camper = camperTbl.getSelectionModel().getSelectedItem();

            if (camper != null)
            {
                acc.deleteCamper(camper.getId());

                updateCampers();
                clearCamperFields();
                camperMsgLbl.setText("deleted");
            }
            else
            {
                camperMsgLbl.setText("non selected");
            }
        }
    }

    public void extraDeleteAct(ActionEvent actionEvent)
    {
        if (newExtraMode)
        {
            clearExtrasFields();
            extrasMsgLbl.setText("canceled");
        }
        else
        {
            ExtraItem item = extrasTbl.getSelectionModel().getSelectedItem();

            if (item != null)
            {
                acc.deleteExtraItem(item.getId());

                updateExtras();
                clearExtrasFields();
                extrasMsgLbl.setText("deleted");
            }
            else
            {
                extrasMsgLbl.setText("non selected");
            }
        }
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

    public void extrasTblAct(MouseEvent mouseEvent)
    {
        updateExtrasFields();
        setNewExtraMode(false);
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
        Motorhome camper = camperTbl.getSelectionModel().getSelectedItem();
        if (!newCamperMode && camper != null)
        {
            this.newCamperMode = false;
            camperDeleteBtn.setText("Delete");
        }
        else
        {
            this.newCamperMode = true;
            camperDeleteBtn.setText("Cancel");
        }
    }

    public void setNewExtraMode(boolean newExtraMode)
    {
        ExtraItem item = extrasTbl.getSelectionModel().getSelectedItem();
        if (!newExtraMode && item != null)
        {
            this.newExtraMode = false;
            extraDeleteBtn.setText("Delete");
        }
        else
        {
            this.newExtraMode = true;
            extraDeleteBtn.setText("Cancel");
        }
    }
    //endregion
}
