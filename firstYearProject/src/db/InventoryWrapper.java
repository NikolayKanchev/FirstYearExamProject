package db;

import model.CamperType;
import model.ExtraItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by bc on 09/05/2017.
 */
public class InventoryWrapper {

    private static InventoryWrapper inventoryWrapper;

    private InventoryWrapper () {}

    private Connection conn = DBCon.getConn();

    public static InventoryWrapper getInstance()
    {
        if (inventoryWrapper==null)
        {
            inventoryWrapper = new InventoryWrapper();
        }
        return inventoryWrapper;
    }

    public ArrayList<ExtraItem> getExtras()
    {
        ArrayList<ExtraItem> list = new ArrayList<>();

        try
        {
            String sql = "SELECT * FROM `extras`";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                list.add(new ExtraItem(rs.getInt("id"),rs.getString("name"),rs.getDouble("price")));
            }
            ps.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return list;
    }
}
