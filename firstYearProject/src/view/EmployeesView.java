package view;

import controller.AdminController;
import controller.Helper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Created by Rasmus on 08-05-2017.
 */
//Martin
public class EmployeesView
{
    //region FXML elements

     @FXML
     TextField firstName,lastName,cpr,possition,eMail,address,phoneNum,accNo,regNr,drLicense;
     @FXML
    PasswordField pass;




    // endregion

    private AdminController adm = new AdminController ();
    private Helper converter = new Helper();


    public void saveEmployee(ActionEvent event) {
        adm.saveEmployee(firstName.getText(),lastName.getText(),cpr.getText(),drLicense.getText() ,pass.getText(),possition.getText(),eMail.getText(),address.getText(),phoneNum.getText(),accNo.getText(),regNr.getText());

    }
}
