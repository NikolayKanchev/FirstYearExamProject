package view;

import controller.AdminController;
import controller.Helper;


import db.PersonWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Employee;
import javafx.scene.control.*; import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.input.KeyEvent;


//Martin
public class EmployeesView implements Initializable
{


    //region FXML elements

     @FXML
     TextField firstName,lastName,cpr,eMail,address,phoneNum,accNo,regNr,drLicense;
     @FXML
    PasswordField pass;
    @FXML
    ChoiceBox exitOptions;

    @FXML
    Button deleteEmpl,saveEmpl,updateButton;

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
     @FXML
     public ChoiceBox<String> possition;




    // endregion

    private AdminController adm = new AdminController ();
    private Helper converter = new Helper();
    private Screen screen = new Screen();
    private Employee selectedEmployee;
    private static final int LIMIT = 10;


     public void setPossition()
     {


     }






    public boolean checkforEmpty()
    {
        if (firstName.getText().isEmpty()||lastName.getText().isEmpty()||cpr.getText().isEmpty()|| drLicense.getText().isEmpty()||/*possition.getValue().toString().isEmpty()||*/eMail.getText().isEmpty()||address.getText().isEmpty()||phoneNum.getText().isEmpty()||accNo.getText().isEmpty()||regNr.getText().isEmpty()){
            return false;
        }
            return true;
    }


    public void saveEmployee(ActionEvent event)
    {


        saveEmpl.setVisible(true);
        updateButton.setVisible(false);

           if (possition.getValue()==null)
           {
               Helper.displayError("ERROR",null,"Please fill the required information");
               return ;
           }
        if (!checkforEmpty())
        {
            Helper.displayError("ERROR",null,"Please fill the required information");
            return ;

        }
           if (pass.getText().isEmpty()){
               Helper.displayError("ERROR",null,"Please fill the required information");

           }
          else
           {
            adm.saveEmployee(firstName.getText(),lastName.getText(),cpr.getText(),pass.getText(), drLicense.getText(),possition.getValue().toString() ,eMail.getText(),address.getText(),phoneNum.getText(),accNo.getText(),regNr.getText());
            Helper.dispplayConfirmation("Confirmation Dialog",null,"Operation has been successful");
               clearEmployeeFields();
                loadData();


        }

    }
    public void clearEmployeeFields(){

        firstName.clear();
        lastName.clear();
        cpr.clear();
        pass.clear();
        drLicense.clear();
        possition.setValue(null);
        eMail.clear();
        address.clear();
        phoneNum.clear();
        accNo.clear();
        regNr.clear();

    }


    public void deleteEmployee(ActionEvent actionEvent)
    {
        if (selectedEmployee==null)
        {
            Helper.displayError("Attention",null,"Please select employee from the table above");
           clearEmployeeFields();
           return;
        }
       adm.deleteEmployee(selectedEmployee);
        loadData();


    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
         loadData();

        saveEmpl.setVisible(false);
        updateButton.setVisible(false);
        exitOptions.setItems(FXCollections.observableArrayList("Log out", "Exit"));
        possition.getItems().addAll("admin","sales assistant","mechanic","cleaning staff","bookkeeper");


    }
    public void loadData(){
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

    public void exitOrLogOut(javafx.scene.input.MouseEvent mouseEvent)
    {
        screen.exitOrLogOut(mouseEvent, exitOptions);
    }



    public void emplTableAct(MouseEvent mouseEvent){

    }



    public void cprRestrict(KeyEvent keyEvent)
    {

        Screen.restrictIntInput(cpr);
        cpr.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.intValue() > oldValue.intValue()){
                    if (cpr.getText().length()> LIMIT){
                        cpr.setText(cpr.getText().substring(0,LIMIT));
                    }
                }
            }
        });

    }




    public void drLicenseRestrcit(KeyEvent keyEvent)
    {
        Screen.restrictIntInput(drLicense);
        drLicense.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.intValue()> oldValue.intValue())
                {
                    if (drLicense.getText().length()> LIMIT-1)
                    {
                        drLicense.setText(drLicense.getText().substring(0,LIMIT-1));
                    }
                }
            }
        });
    }

    public void phoneRestrict(KeyEvent keyEvent)
    {
        Screen.restrictIntInput(phoneNum);
        phoneNum.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.intValue()> oldValue.intValue())
                {
                if (phoneNum.getText().length()> LIMIT-2)
                {
                    phoneNum.setText(phoneNum.getText().substring(0,LIMIT-2));
                }
                }
            }
        });
        {

        }
    }

    public void restrictAccNo(KeyEvent keyEvent)
    {
        Screen.restrictIntInput(accNo);
        accNo.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.intValue()> oldValue.intValue())
                {
                if (accNo.getText().length()>LIMIT)
                {
                    accNo.setText(accNo.getText().substring(0,LIMIT));
                }
                }
            }
        });
    }

    public void restrictRegNr(KeyEvent keyEvent)
    {
        Screen.restrictIntInput(regNr);
        regNr.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(newValue.intValue()> oldValue.intValue())
                {
                    if (regNr.getText().length()>LIMIT-6)
                    {
                        regNr.setText(regNr.getText().substring(0,LIMIT-6));
                    }
                }
            }
        });
    }

    public void createNewEmpl(ActionEvent event) {

        clearEmployeeFields();
        saveEmpl.setVisible(true);
        updateButton.setVisible(false);


    }


    public void selectEmployee(MouseEvent mouseEvent) {
        clearEmployeeFields();
        saveEmpl.setVisible(false);
        updateButton.setVisible(true);
        loadEmployeeData();


    }




    private void loadEmployeeData() {
        selectedEmployee = employeeTabableView.getSelectionModel().getSelectedItem();
        firstName.setText(selectedEmployee.getFirstName());
        lastName.setText(selectedEmployee.getLastName());
        cpr.setText(selectedEmployee.getCpr());
       // pass.setText(selectedEmployee.getPass());
        drLicense.setText(selectedEmployee.getDriverLicense());
        possition.setValue(selectedEmployee.getPossition());
        eMail.setText(selectedEmployee.getEMail());
        address.setText(selectedEmployee.getAddress());
        phoneNum.setText(selectedEmployee.getPhoneNum());
        accNo.setText(selectedEmployee.getAccNo());
        regNr.setText(selectedEmployee.getRegNr());

    }

    public void updateEmployee(ActionEvent event) {



        if (!checkforEmpty()){
            Helper.displayError("ERROR",null,"Please fill the required information");
            return ;
        }
        else {
            adm.updateEmployee(selectedEmployee, firstName, lastName, cpr, pass, drLicense, possition, eMail, address, phoneNum, accNo, regNr);
            loadData();
        }
    }


    public void goBack(ActionEvent actionEvent) {

    }


}
