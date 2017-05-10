package db;

import model.Employee;
import model.Person;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Nikolaj on 10-05-2017.
 */
public class PersonWrapper
{
    private static PersonWrapper personWrapper;
    private Connection conn;

    public static synchronized PersonWrapper getInstance()
    {
        if(personWrapper == null)
        {
            personWrapper = new PersonWrapper();
        }
        return personWrapper;
    }

    private PersonWrapper()
    {

    }


    public Person getPerson(String eMail, String pass)
    {
        conn = DBCon.getConn();
        Person person = null;

        try
        {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM `persons`WHERE `e_mail`= 'hhh' AND `pass`= 'hhh'");

            while (rs.next())
            {
                String status = rs.getString("status");

                if (status.equals("employee"))
                {
                    
                }

                return person;
            }

            conn.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return person;
    }
}
