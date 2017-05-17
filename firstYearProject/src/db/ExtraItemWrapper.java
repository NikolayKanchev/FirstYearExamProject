package db;

import model.ExtraItem;
import model.ExtrasLineItem;

import java.sql.*;
import java.util.ArrayList;

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

    public ArrayList<ExtrasLineItem> getExtrasLineItems(int id, String state)
    {
        conn = DBCon.getConn();

        ArrayList<ExtrasLineItem> lineItems = new ArrayList<>();

        String sql = "";

        if (state.equals("rental"))
        {
            sql = "SELECT extras_line_item.id, extras.name AS item_name, extras_line_item.item_id, extras_line_item.quantity, " +
                    "SUM(extras.price*extras_line_item.quantity) AS subtotal\n" +
                    "FROM extras_line_item, extras\n" +
                    "WHERE extras_line_item.rental_id = "+ id +" AND extras.id = extras_line_item.item_id\n" +
                    "GROUP BY extras_line_item.id";

        }

        if(state.equals("reservation"))
        {
            sql = "SELECT extras_line_item.id, extras.name AS item_name, extras_line_item.item_id, extras_line_item.quantity, " +
                    "SUM(extras.price*extras_line_item.quantity) AS subtotal\n" +
                    "FROM extras_line_item, extras\n" +
                    "WHERE extras_line_item.reserv_id = "+ id +" AND extras.id = extras_line_item.item_id\n" +
                    "GROUP BY extras_line_item.id";
        }

        try
        {
            PreparedStatement prepStmt =
                    conn.prepareStatement(sql);

            ResultSet rs = prepStmt.executeQuery();

            while (rs.next())
            {
                ExtrasLineItem extrasLineItem = new ExtrasLineItem(
                        rs.getInt(1), rs.getString("item_name"),
                        rs.getInt(3), rs.getInt(4), rs.getDouble("subtotal"));

                lineItems.add(extrasLineItem);
            }

            prepStmt.close();

            return lineItems;
        }
        catch (SQLException e)
        {
            e.printStackTrace();

            return null;
        }
    }
}
