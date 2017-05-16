package controller;

import model.Employee;

/**
 * Created by Jakub on 09.05.2017.
 */
public class AdminController {
    //Martin


    public void saveEmployee(String firstNameText, String lastNameText, String cprText, String passText,String driverText,String possitionText, String eMailText, String addressText, String phoneNumText, String accNoText, String regNrText) {

        Employee  employee = new Employee( passText,firstNameText,lastNameText,addressText,driverText,cprText,eMailText,phoneNumText);

        employee.setStatus(possitionText);
        employee.setAccNo(accNoText);
        employee.setRegNr(regNrText);
        employee.storeEmployee();


    }
}
