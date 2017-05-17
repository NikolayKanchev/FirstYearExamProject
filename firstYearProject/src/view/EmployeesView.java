package view;

import controller.AdminController;
import controller.Helper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Employee;
import javafx.scene.control.*;

import model.Person;

import javax.swing.text.TabableView;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;


//Martin
public class EmployeesView implements Initializable
{
    //region FXML elements

     @FXML
     TextField firstName,lastName,cpr,possition,eMail,address,phoneNum,accNo,regNr,drLicense;
     @FXML
    PasswordField pass;
     @FXML
    public TableView<Employee> employeeTabableView;
     @FXML
     public TableColumn<String,Employee> fNameClm;
     @FXML
     public TableColumn<String,Employee> lNameClm;
     @FXML
     public TableColumn<String,Employee> cprClm;
     @FXML
     public TableColumn<String,Employee> pssClm;
     @FXML
     public TableColumn<String,Employee> postClm;
     @FXML
     public TableColumn<String,Employee> drLicenseClm;
     @FXML
     public TableColumn<String,Employee> emailClm;
     @FXML
     public TableColumn<String,Employee> addressClm;
     @FXML
     public TableColumn<String,Employee> phoneNumClm;
     @FXML
     public TableColumn<String,Employee> accNumClm;
     @FXML
     public TableColumn<String,Employee> regNumClm;




    // endregion

    private AdminController adm = new AdminController ();
    private Helper converter = new Helper();



    public void saveEmployee(ActionEvent event) {
        adm.saveEmployee(firstName.getText(),lastName.getText(),cpr.getText(),drLicense.getText() ,pass.getText(),possition.getText(),eMail.getText(),address.getText(),phoneNum.getText(),accNo.getText(),regNr.getText());

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fNameClm.setCellValueFactory(
        new PropertyValueFactory("firstName"));
        lNameClm.setCellValueFactory(new PropertyValueFactory("lastName"));
        addressClm.setCellValueFactory(new PropertyValueFactory("address"));
        cprClm.setCellValueFactory(new PropertyValueFactory("cpr"));
        drLicenseClm.setCellValueFactory(new PropertyValueFactory("driverLicense"));
        emailClm.setCellValueFactory(new PropertyValueFactory("eMail"));
        phoneNumClm.setCellValueFactory(new PropertyValueFactory("phoneNum"));
        accNumClm.setCellValueFactory(new PropertyValueFactory("accNo"));
        regNumClm.setCellValueFactory(new PropertyValueFactory("regNr"));
        pssClm.setCellValueFactory(new PropertyValueFactory("pass"));
        postClm.setCellValueFactory(new PropertyValueFactory("possition"));

        ObservableList<Employee> empls = FXCollections.observableArrayList();
        empls.addAll(adm.loadEmployee());
        employeeTabableView.setItems(empls);



    }
    public void emplTableAct(MouseEvent mouseEvent){

    }
}
