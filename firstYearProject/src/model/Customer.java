package model;

import db.PersonWrapper;
import javafx.scene.control.TextField;

/**
 * Created by Jakub on 09.05.2017.
 */
public class Customer extends Person
{
    private PersonWrapper personWrapper = PersonWrapper.getInstance();
    private String driverLicenseNum;

    public Customer(String pass,String firstName, String lastName, String address, String cpr,String driverLicenseNum, String eMail, String phoneNum)
    {
        super(pass,firstName, lastName, address, cpr,driverLicenseNum, eMail, phoneNum);
    }

    public String getDriverLicenseNum()
    {
        return driverLicenseNum;
    }

    public void setDriverLicenseNum(String driverLicenseNum)
    {
        this.driverLicenseNum = driverLicenseNum;
    }

    public void saveChanges(Customer selectedCustomer, TextField firstNameTxt, TextField lastNameTxt, TextField cprTxt, TextField drLicenseTxt, TextField phoneNumTxt, TextField emailTxt, TextField addressTxt)
    {
        personWrapper.updateCustomer(selectedCustomer,firstNameTxt,lastNameTxt,cprTxt,drLicenseTxt,phoneNumTxt,emailTxt,addressTxt);
    }

    public int storeCustomer()
    {
        return personWrapper.saveNewCustomer(this);
    }
}
