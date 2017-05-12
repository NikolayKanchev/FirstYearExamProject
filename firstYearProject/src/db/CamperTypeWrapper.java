package db;

import model.CamperType;

import java.sql.*;

/**
 * Created by Dunkl on 10/05/2017.
 */
public class CamperTypeWrapper
{
    private static final String TABLE = "`nordic_motorhomes`.`rvs_type`";
    private static CamperTypeWrapper thisWrapper;
    private Connection conn = null;

    public static synchronized CamperTypeWrapper getInstance()
    {
        if(thisWrapper == null)
        {
            thisWrapper = new CamperTypeWrapper();
        }
        return thisWrapper;
    }

    private CamperTypeWrapper()
    {
    }

    public int saveNew(CamperType type)
    {
        conn = DBCon.getConn();

        int typeId = -1;

        String sqlTxt = "INSERT INTO " + TABLE + " (" +
                "`brand`, `model`, `capacity`, `base_price`, `description`" +
                ") VALUES (" +
                "?, ?, ?, ?, ?" +
                ");";

        try
        {
            PreparedStatement prepStmt =
                    conn.prepareStatement(sqlTxt, Statement.RETURN_GENERATED_KEYS);

            prepStmt.setString(1, type.getBrand());
            prepStmt.setString(2, type.getModel());
            prepStmt.setInt(3, type.getCapacity());
            prepStmt.setDouble(4, type.getPrice());
            prepStmt.setString(5, type.getDescription());

            prepStmt.execute();

            ResultSet rs = prepStmt.getGeneratedKeys();

            if (rs.next())
            {
                typeId = rs.getInt(1);
            }

            prepStmt.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return typeId;
    }

    public CamperType load(int id)
    {
        conn = DBCon.getConn();

        String sqlTxt = "SELECT * FROM " + TABLE +
                " WHERE `rvs_type`.`id` = '" + id + "';";

        try
        {
            PreparedStatement prepStmt =
                    conn.prepareStatement(sqlTxt);

            ResultSet rs = prepStmt.executeQuery();

            String brand = rs.getString(2);
            String model = rs.getString(3);
            int capacity = rs.getInt(4);
            double price = rs.getDouble(5);
            String descr = rs.getString(6);

            prepStmt.close();

            return new CamperType(id, brand, model, capacity, price, descr);

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public boolean update(CamperType type)
    {
        conn = DBCon.getConn();

        String sqlTxt = "UPDATE " + TABLE + " SET " +
                "`brand` = ?," +
                "`model` = ?," +
                "`capacity` = ?," +
                "`base_price` = ?," +
                "`description` = ?" +
                " WHERE `id` = " + type.getId() + ";";

        try
        {
            PreparedStatement prepStmt =
                    conn.prepareStatement(sqlTxt);

            prepStmt.setString(1, type.getBrand());
            prepStmt.setString(2, type.getModel());
            prepStmt.setInt(3, type.getCapacity());
            prepStmt.setDouble(4, type.getPrice());
            prepStmt.setString(5, type.getDescription());

            prepStmt.execute();

            prepStmt.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean delete(int id)
    {
        conn = DBCon.getConn();

        String sqlTxt = "DELETE FROM " + TABLE +
                " WHERE `rvs_type`.`id` = '" + id + "';";

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
}
