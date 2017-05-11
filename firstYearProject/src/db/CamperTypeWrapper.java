package db;

import model.CamperType;

import java.sql.*;

/**
 * Created by Dunkl on 10/05/2017.
 */
public class CamperTypeWrapper
{
    private static final String table = "`nordic_motorhomes`.`rvs_type`";
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

        String sqlTxt = "INSERT INTO " + table + " (" +
                "`brand`, `model`, `capacity`, `base_price`, `description`" +
                ") VALUES (" +
                "'" + type.getBrand() + "', " +
                "'" + type.getModel() + "', " +
                "'" + type.getCapacity() + "', " +
                "'" + type.getPrice() + "', " +
                "'" + type.getDescription() + "'" +
                ");";

        try
        {
            PreparedStatement prepStmt = conn.prepareStatement(sqlTxt, Statement.RETURN_GENERATED_KEYS);

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

    public int updateCamperType(CamperType camperType)
    {
        conn = DBCon.getConn();

        int typeId = -1;

        String sqlTxt = "";

        try
        {
            PreparedStatement prepStmt = conn.prepareStatement(sqlTxt, Statement.RETURN_GENERATED_KEYS);

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
}
