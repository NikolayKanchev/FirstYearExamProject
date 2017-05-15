package db;

import model.ExtraItem;

import java.sql.*;

/**
 * Created by Dunkl on 14/05/2017.
 */
public class ExtraItemWrapper
{
    private static final String TABLE = "`nordic_motorhomes`.`extras`";
    private static ExtraItemWrapper thisWrapper;
    private Connection conn = null;

    public static synchronized ExtraItemWrapper getInstance()
    {
        if(thisWrapper == null)
        {
            thisWrapper = new ExtraItemWrapper();
        }
        return thisWrapper;
    }

    private ExtraItemWrapper()
    {
    }




    public int saveNew(ExtraItem item)
    {
        conn = DBCon.getConn();

        int newId = -1;

        String sqlTxt = "INSERT INTO " + TABLE + " (" +
                "`name`, `price`" +
                ") VALUES (" +
                "?, ?" +
                ");";

        try
        {
            PreparedStatement prepStmt =
                    conn.prepareStatement(sqlTxt, Statement.RETURN_GENERATED_KEYS);

            prepStmt.setString(1, item.getName());
            prepStmt.setDouble(2, item.getPrice());

            prepStmt.execute();

            ResultSet rs = prepStmt.getGeneratedKeys();

            if (rs.next())
            {
                newId = rs.getInt(1);
            }

            prepStmt.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return newId;
    }

    public ExtraItem load(int id)
    {
        conn = DBCon.getConn();

        String sqlTxt = "SELECT * FROM " + TABLE +
                " WHERE `id` = '" + id + "';";

        try
        {
            PreparedStatement prepStmt =
                    conn.prepareStatement(sqlTxt);

            ResultSet rs = prepStmt.executeQuery();

            if (!rs.next())
            {
                return null;
            }

            String name = rs.getString("name");
            double price = rs.getDouble("price");

            prepStmt.close();

            return new ExtraItem(id, name, price);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public boolean update(ExtraItem item)
    {
        conn = DBCon.getConn();

        String sqlTxt = "UPDATE " + TABLE + " SET " +
                "`name` = ?," +
                "`price` = ?" +
                " WHERE `id` = '" + item.getId() + "';";

        try
        {
            PreparedStatement prepStmt =
                    conn.prepareStatement(sqlTxt);

            prepStmt.setString(1, item.getName());
            prepStmt.setDouble(2, item.getPrice());

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
}
