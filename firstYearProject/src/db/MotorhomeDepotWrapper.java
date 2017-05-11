package db;

import javafx.collections.FXCollections;
import model.CamperType;
import model.MotorhomeDepot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by bc on 09/05/2017.
 */
public class MotorhomeDepotWrapper
{
    private static MotorhomeDepotWrapper depotWrapper;

    private MotorhomeDepotWrapper () {}

    private Connection conn = DBCon.getConn();



    public synchronized static MotorhomeDepotWrapper getInstance()
    {
        if (depotWrapper==null)
        {
            depotWrapper = new MotorhomeDepotWrapper();
        }
        return depotWrapper;
    }

    public ArrayList<CamperType> getMotorhomeTypes()
    {
        ArrayList<CamperType> list = new ArrayList<>();

        try
        {
            String sql = "SELECT * FROM `rvs_type`";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                list.add(new CamperType(rs.getInt("id"), rs.getString("brand"), rs.getString("model"), rs.getInt("capacity"), rs.getDouble("base_price"), rs.getString("description")));
            }
            ps.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return list;
    }
}
