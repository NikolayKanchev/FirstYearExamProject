package db;

import model.Customer;
import model.Employee;
import model.Person;

import java.sql.*;

/**
 * Created by Nikolaj on 10-05-2017.
 */
public class PersonWrapper
{
    private static final String table = "`persons`";
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

    public PersonWrapper()
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
                    Employee employee = new Employee(
                            rs.getString("pass"),
                            rs.getString("first_name"), rs.getString("last_name"),
                            rs.getString("address"), rs.getString("cpr"),
                            rs.getString("e_mail"), rs.getString("phone"));
                    employee.setStatus(rs.getString("status"));
                    employee.setId(rs.getInt("id"));
                    employee.setAccNo(rs.getString("account_number"));
                    employee.setRegNr(rs.getString("reg_number"));
                    person = employee;

                }

                if (status.equals("customer"))
                {
                    Customer customer = new Customer(
                            rs.getString("pass"),
                            rs.getString("first_name"), rs.getString("last_name"),
                            rs.getString("address"), rs.getString("cpr"),
                            rs.getString("e_mail"), rs.getString("phone"));
                    customer.setStatus(rs.getString("status"));
                    customer.setId(rs.getInt("id"));
                    person = customer;
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
    //Martin
     public int saveNewEmployee(Employee employee){
        conn = DBCon.getConn();
       int personId = -1;

       String sql = "INSERT INTO " + table +
               " (" +
               "`first_name`, `last_name`, `address`, `cpr`, `e_mail`,`phone`,`pass`,`status`" +
               ") VALUES (" +
               "'" + employee.getFirstName() + "', " +
               "'" + employee.getLastName() + "', " +
               "'" + employee.getAddress() + "', " +
               "'" + employee.getCpr() + "', " +
               "'" + employee.geteMail() + "'" +
               "'" + employee.getPhoneNum() + "'" +
               "'" + employee.getPass() + "'" +
               "'" + employee.getStatus() + "'" +
               ");";
         try {
              PreparedStatement pstmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
              pstmt.execute();
              ResultSet rs = pstmt.getGeneratedKeys();
              if (rs.next()){
                  personId = rs.getInt(1);
              }
              pstmt.close();
         }
         catch (SQLException e){
             e.printStackTrace();

         }
        return  personId;
     }

    public Customer getCustomer(int customerID)
    {
        Customer customer = null;

        try
        {

            conn = DBCon.getConn();

            String sql = "SELECT * FROM `customers`WHERE `id`= " + customerID;

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.execute();

            ResultSet rs = ps.getResultSet();

            while (rs.next())
            {
                    customer = new Customer(
                            rs.getString("pass"),
                            rs.getString("first_name"), rs.getString("last_name"),
                            rs.getString("address"), rs.getString("cpr"),
                            rs.getString("e_mail"), rs.getString("phone"));
                    customer.setDriverLicenseNum(rs.getString("driver_license"));
                    customer.setId(rs.getInt("id"));
            }

        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return customer;
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
