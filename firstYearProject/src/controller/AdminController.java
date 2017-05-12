package controller;

import model.Employee;

/**
 * Created by Jakub on 09.05.2017.
 */
public class AdminController {
    //Martin
    public  void saveEmployee (int id,
            String pass,
            String firstName,
            String lastName,
            String address,
            String cpr,
            String eMail,
            String phoneNum){
        Employee  employee = new Employee(id, pass, firstName, lastName, address, cpr, eMail, phoneNum);
        employee.storeEmployee();

    }

}
