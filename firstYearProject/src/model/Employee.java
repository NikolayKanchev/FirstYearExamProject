package model;

import db.PersonWrapper;

/**
 * Created by Jakub on 09.05.2017.
 */
public class Employee extends Person
{
    private String accNo;
    private String regNr;
    public Employee( String pass, String firstName, String lastName, String address, String cpr, String eMail, String phoneNum)
    {
        super( pass, firstName, lastName, address, cpr, eMail, phoneNum);
    }


    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getRegNr() {
        return regNr;
    }

    public void setRegNr(String regNr) {
        this.regNr = regNr;
    }

    public void storeEmployee(){
        PersonWrapper personWrapper = PersonWrapper.getInstance();
        personWrapper.saveNewEmployee(this);



    }


}
