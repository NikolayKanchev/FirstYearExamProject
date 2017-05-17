package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Depot;
import model.Employee;

import java.util.ArrayList;

/**
 * Created by Jakub on 09.05.2017.
 */
public class AdminController {
    //Martin


    private Depot depot = new Depot();

    public void saveEmployee(String firstNameText, String lastNameText, String cprText, String passText,String driverText,String possitionText, String eMailText, String addressText, String phoneNumText, String accNoText, String regNrText)
    {

        Employee  employee = new Employee( passText,firstNameText,lastNameText,addressText,driverText,cprText,eMailText,phoneNumText);

        employee.setStatus(possitionText);
        employee.setAccNo(accNoText);
        employee.setRegNr(regNrText);
        employee.storeEmployee();


    }

    public ArrayList<Employee> loadEmployee()
    {

       return depot.getEmployees();

    }

}
