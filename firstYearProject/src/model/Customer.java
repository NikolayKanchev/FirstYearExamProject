package model;

/**
 * Created by Jakub on 09.05.2017.
 */
public class Customer extends Person
{
    public Customer(int id, String pass, String firstName, String lastName, String address, String cpr, String eMail, String phoneNum)
    {
        super(id, pass, firstName, lastName, address, cpr, eMail, phoneNum);
    }
}
