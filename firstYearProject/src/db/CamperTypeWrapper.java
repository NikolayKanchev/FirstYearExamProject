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

    public int saveNewCamperType(CamperType type)
    {
        conn = DBCon.getConn();

        int typeId = -1;

        String sqlTxt = "INSERT INTO " + TABLE + " (" +
                "`brand`, `model`, `capacity`, `base_price`, `description`" +
                ") VALUES (?, ?, ?, ?, ?);";

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

    public boolean updateCamperType(CamperType type)
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
}
