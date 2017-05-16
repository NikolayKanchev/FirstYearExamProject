package view;

import controller.Helper;
import controller.ServiceController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.Service;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;

/**
 * Created by Rasmus on 08-05-2017.
 */
public class ServiceView implements Initializable{

    //region FXML elements
    @FXML
    public TableView<Service> serviceTbl;
    @FXML
    public TableColumn plateClmn;
    @FXML
    public TableColumn cleaningClmn;
    @FXML
    public TableColumn mechClmn;
    @FXML
    public CheckBox kmCountChk;
    @FXML
    public TextField kmCountTxtFld;
    @FXML
    public CheckBox gasChk;
    @FXML
    public CheckBox repairChk;
    @FXML
    public CheckBox cleanedChk;
    @FXML
    public Label msgLbl;
    //endregion

    private ServiceController controller = new ServiceController();

    private ObservableList<Service> serviceList;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        plateClmn.setCellValueFactory(
                new PropertyValueFactory<Service, String>("camperPlate"));
        cleaningClmn.setCellValueFactory(
                new PropertyValueFactory<Service, String>("cleanStatus"));
        mechClmn.setCellValueFactory(
                new PropertyValueFactory<Service, String>("mechStatus"));


        msgLbl.setText("");
        updateServices();
    }

    //region Change GUI
    public void updateServices()
    {
        int selected = 0;
        Service service = serviceTbl.getSelectionModel().getSelectedItem();

        if (service != null)
        {
            selected = serviceList.indexOf(service);
        }

        serviceList = controller.loadServices();
        serviceTbl.setItems(serviceList);

        // tableview will keep selected row after updating
        if (serviceList != null && serviceList.size() > 0)
        {
            serviceTbl.getSelectionModel().select(selected);
        }

        updateFields();
    }

    private void updateFields()
    {
        Service service = serviceTbl.getSelectionModel().getSelectedItem();
        controller.setService(service);

        if (service == null)
        {
            clearFields();
            return;
        }

        kmCountTxtFld.setText(service.getKmCount() + "");

        kmCountChk.setSelected(service.getKmChecked());
        gasChk.setSelected(service.getEnoughGas());
        repairChk.setSelected(service.getNoRepair());
        cleanedChk.setSelected(service.getCleaned());
    }

    private void clearFields()
    {
        kmCountTxtFld.setText("");
        kmCountChk.setSelected(false);
        gasChk.setSelected(false);
        repairChk.setSelected(false);
        cleanedChk.setSelected(false);
    }
    //endregion

    private boolean saveCurrent()
    {
        Service service = serviceTbl.getSelectionModel().getSelectedItem();

        if (service == null)
        {
            msgLbl.setText("non selected");
            clearFields();
            return false;
        }

        Helper h = new Helper();
        double kmCount = h.doubleFromTxt(kmCountTxtFld.getText());

        boolean kmChecked = kmCountChk.isSelected();
        boolean enoughGas = gasChk.isSelected();
        boolean noRepair = repairChk.isSelected();
        boolean cleaned = cleanedChk.isSelected();

        if (!kmChecked || kmCount == -12345)
        {
            kmCountChk.setSelected(false);
            kmChecked = false;
            System.out.println(service.getKmCount());
            kmCount = service.getKmCount();
        }

        if (controller.updateService(service.getId(), kmCount, kmChecked,
                enoughGas, noRepair, cleaned))
        {
            msgLbl.setText("changes saved");
            updateServices();
            return true;
        }

        msgLbl.setText("changes not saved");
        return false;
    }

    //region JavaFX mehtods
    public void serviceTblAct(MouseEvent mouseEvent)
    {
        msgLbl.setText("");
        updateServices();
        updateFields();
    }

    public void kmCountChkAct(ActionEvent actionEvent)
    {
        Helper helper = new Helper();

        double kmCount = helper.doubleFromTxt(kmCountTxtFld.getText());

        if (kmCount != -12345)
        {
            saveCurrent();
            updateServices();
        }
        else
        {
            msgLbl.setText("invalid format for km count");
            kmCountChk.setSelected(false);
        }
    }

    public void gasChkAct(ActionEvent actionEvent)
    {
        saveCurrent();
    }

    public void repairChkAct(ActionEvent actionEvent)
    {
        saveCurrent();
    }

    public void cleanedChkAct(ActionEvent actionEvent)
    {
        saveCurrent();
    }

    public void doneBtnAct(ActionEvent actionEvent)
    {
        Service service = serviceTbl.getSelectionModel().getSelectedItem();

        if (service == null)
        {
            msgLbl.setText("non selected");
            return;
        }

        if (kmCountChk.isSelected() &&
                gasChk.isSelected() &&
                repairChk.isSelected() &&
                cleanedChk.isSelected())
        {
            Screen screen = new Screen();

            if (screen.confirm("Service done",
                    "The camper is about to be marked as available. " +
                            "\nDo you wish to continue?"))
            {
                if (controller.markDone(service))
                {
                    msgLbl.setText("service done");
                    updateServices();
                }
                else
                {
                    msgLbl.setText("couldn't set as 'done'");
                }
                return;
            }
        }
        else
        {
            msgLbl.setText("unfinished service");
        }
    }
    //endregion
}
