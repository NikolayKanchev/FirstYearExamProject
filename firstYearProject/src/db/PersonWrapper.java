package db;

import model.Customer;
import model.Employee;
import model.Person;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Nikolaj on 10-05-2017.
 */
public class PersonWrapper
{
    private static final String TABLE  = "`nordic_motorhomes`.`persons`";
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

            ps.setString(2, addSalt(pass));

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
                            rs.getString("driver_license"),
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
                            rs.getString("driver_license"),
                            rs.getString("first_name"), rs.getString("last_name"),
                            rs.getString("address"), rs.getString("cpr"),
                            rs.getString("e_mail"), rs.getString("phone"));
                    customer.setStatus(rs.getString("status"));
                    customer.setId(rs.getInt("id"));
                    person = customer;
                }

                return person;
            }

            //conn.close();
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

       String sql =  "INSERT INTO " + TABLE + " (" +
               "`first_name`, `last_name`, `address`, `driver_license`,`cpr`,`e_mail`,`phone`,`account_number`,`reg_number`,`pass`,`status`" +
               ") VALUES (" +
               "?, ?, ?, ?,?,?,?,?,?,MD5(?),?" +
               ");";


         try {
              PreparedStatement pstmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
              pstmt.setString(1,employee.getFirstName());
              pstmt.setString(2,employee.getLastName());
              pstmt.setString(3,employee.getAddress());
              pstmt.setString(4,employee.getCpr());
              pstmt.setString(5,employee.getDriverLicense());
              pstmt.setString(6,employee.getEMail());
              pstmt.setString(7,employee.getPhoneNum());
              pstmt.setString(8,employee.getAccNo());
              pstmt.setString(9,employee.getRegNr());

              String password = addSalt(employee.getPass());
              pstmt.setString(10, password);

              pstmt.setString(11,employee.getStatus());
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
     public ArrayList<Employee> readEmployee() /*(int employeeID)*/{
         ArrayList <Employee> employees = new ArrayList<>();

         try
         {

             System.out.println("loading");
             conn = DBCon.getConn();

             String sql = "SELECT * FROM `persons`";

             PreparedStatement ps = conn.prepareStatement(sql);



             ResultSet rs = ps.executeQuery();

             while (rs.next())
             {
                 Employee empployee = new Employee(
                         rs.getString("pass"),
                         rs.getString("first_name"),
                         rs.getString("last_name"), rs.getString("address"),
                         rs.getString("cpr"), rs.getString("driver_license"),
                         rs.getString("e_mail"), rs.getString("phone"));
                 empployee.setAccNo(rs.getString("account_number"));
                 empployee.setRegNr(rs.getString("reg_number"));
                 empployee.setPossition(rs.getString("status"));



                 employees.add(empployee);
             }
             ps.close();

         } catch (SQLException e)
         {
             e.printStackTrace();
         }

         return employees;

     }

    private String addSalt (String txt) // Rasmus
    {
        final String salt = "6&pjlRTm8K+BqXEa";
        int saltIndex = 0;
        String newTxt = "";

        for (char c : txt.toCharArray())
        {
            newTxt += c + "" + salt.charAt(saltIndex);

            if (saltIndex == salt.length() - 1)
            {
                saltIndex = 0;
            } else
            {
                saltIndex++;
            }
        }
        return newTxt;
    }

    public boolean delete(int id)
    {
        conn = DBCon.getConn();

        String sqlTxt = "DELETE FROM " + TABLE +
                " WHERE `id` = '" + id + "';";

        try
        {
            PreparedStatement prepStmt =
                    conn.prepareStatement(sqlTxt);

            prepStmt.execute();

            prepStmt.close();

            return true;

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
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
                            rs.getString("driver_license"),
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
