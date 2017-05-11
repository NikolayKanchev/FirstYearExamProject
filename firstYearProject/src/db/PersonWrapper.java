package db;

import model.Employee;
import model.Person;

import java.sql.*;

/**
 * Created by Nikolaj on 10-05-2017.
 */
public class PersonWrapper
{
    private static PersonWrapper personWrapper;
    private Connection conn = null;

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

    /*Returns the signed in person by checking e-mail and pass.
    * It checks also which type of person it is and creates the object out of this type
    * The method is using prepared statement and MD5 build in function*/
    public Person getPerson(String eMail, String pass)
    {

        Person person = null;

        try
        {

            conn = DBCon.getConn();

            String sql = "SELECT * FROM `persons`WHERE `e_mail`= ? AND `pass`= MD5(?)";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, eMail);
            ps.setString(2, pass);

            ps.execute();

            ResultSet rs = ps.getResultSet();

            while (rs.next())
            {
                String status = rs.getString("status");

                if (status.equals("mechanic") || status.equals("assistant") ||
                        status.equals("admin") || status.equals("accountant") || status.equals("cleaner"))
                {
                    person = new Employee(
                            rs.getInt("id"), rs.getString("pass"),
                            rs.getString("first_name"), rs.getString("last_name"),
                            rs.getString("address"), rs.getString("cpr"),
                            rs.getString("e_mail"), rs.getString("phone"));
                    person.setStatus(rs.getString("status"));
                }

                if (status.equals("customer"))
                {
                    person = new Employee(
                            rs.getInt("id"), rs.getString("pass"),
                            rs.getString("first_name"), rs.getString("last_name"),
                            rs.getString("address"), rs.getString("cpr"),
                            rs.getString("e_mail"), rs.getString("phone"));
                    person.setStatus(rs.getString("status"));
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

    //was used to hash the passwords
//    public void hashPassword()
//    {
//        try
//        {
//
//            conn = DBCon.getConn();
//
//            String sql = "UPDATE  `nordic_motorhomes`.`persons` SET  `pass` =  MD5(?) WHERE  `persons`.`e_mail` =  'jak@yahoo.com'";
//
//            PreparedStatement ps = conn.prepareStatement(sql);
//
//            ps.setString(1, "jakpass");
//
//            ps.executeUpdate();
//
//            conn.close();
//        } catch (SQLException e)
//        {
//            e.printStackTrace();
//        }
//
//    }
}
