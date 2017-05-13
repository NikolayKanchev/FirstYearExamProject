package db;

import javafx.collections.FXCollections;
import model.*;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

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

    public ArrayList<Motorhome> getCampers()
    {
        ArrayList<Motorhome> campers = new ArrayList<>();

        try
        {
            String sql = "SELECT * FROM `nordic_motorhomes`.`rvs`";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                campers.add(
                        new Motorhome(
                        rs.getInt("id"),
                        rs.getInt("rv_type"),
                        rs.getString("plate"),
                        rs.getString("status"),
                        rs.getDouble("km_count")
                        )
                );
            }
            ps.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return campers;
    }

    public ArrayList<Reservation> getReservations()
    {
        ArrayList<Reservation> reservations = new ArrayList<>();

        try
        {
            String sql = "SELECT * FROM `reservations`";

            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                Reservation r = new Reservation(
                        rs.getInt("id"), rs.getDate("start_date"), rs.getDate("end_date"),
                                              rs.getString("start_location"), rs.getString("end_location"),
                                               rs.getInt("assistant_id"), rs.getDate("creation_date"),
                        rs.getString("state"), rs.getDouble("estimated_price"));
                r.setRvTypeID(rs.getInt("rv_type"));
                r.setCustomerID(rs.getInt("customer_id"));

                reservations.add(r);
            }

            ps.close();

        } catch (SQLException e)
        {
            e.printStackTrace();
        }


        return reservations;
    }

    public ArrayList<Motorhome> getAvailableCampers()
    {

        ArrayList<Motorhome> availableCampers = new ArrayList<>();

        try
        {
            String sql = "SELECT * FROM rvs WHERE status = 'available'";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                availableCampers.add
                        (new Motorhome(
                        rs.getInt("id"), rs.getInt("rv_type"),
                        rs.getString("plate"), rs.getString("status"),
                        rs.getDouble("km_count"))
                        );

            }

            ps.close();

            return availableCampers;

        } catch (SQLException e)
        {
            e.printStackTrace();
        }



        return null;
    }

    public ArrayList<Rental> getRentals()
    {
        ArrayList<Rental> rentals = new ArrayList<>();

        try
        {
            String sql = "SELECT * FROM rentals";

            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                Rental r = new Rental(
                        rs.getInt("id"), rs.getDate("start_date"),
                        rs.getDate("end_date"), rs.getString("start_location"),
                        rs.getString("end_location"), rs.getInt("assistant_id"));

                r.setReservPrice(rs.getDouble("reserv_price"));
                r.setContract(rs.getString("contract"));
                r.setExtraKilometers(rs.getDouble("extra_km"));
                r.setGasFee(rs.getDouble("gas_fee"));
                r.setDamagedPrice(rs.getDouble("damaged_price"));
                r.setReservID(rs.getInt("reserv_id"));

                rentals.add(r);
            }

            ps.close();

        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return rentals;
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

    public void createRental(Rental rental)
    {

        String sql = "INSERT INTO `nordic_motorhomes`.`rentals` (`" +
                "id`, `start_date`, `end_date`, `start_location`, `end_location`, `assistant_id`, " +
                "`reserv_price`, `contract`, `extra_km`, `gas_fee`, `damaged_price`, `reserv_id`) " +
                "" +
                "VALUES (NULL, '"+rental.getStartDate()+"', '"+ rental.getEndDate() +"'," +
                " ?, ?, " +
                "?, ?, " +
                " ?, '0', '0', '0', '"+ rental.getReservID() +"');";

        try
        {

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setString(1, rental.getStartLocation());
            ps.setString(2, rental.getEndLocation());
            ps.setInt(3, rental.getAssistantID());
            ps.setDouble(4, rental.getReservPrice());
            ps.setString(5, rental.getContract());

            ps.execute();

            ps.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    public void saveCamperStatusChanges(int id, String status)
    {
        String sqlTxt = "UPDATE  `nordic_motorhomes`.`rvs` SET  `status` = ? WHERE  `rvs`.`id` = " + id;

        try
        {
            PreparedStatement prepStmt =
                    conn.prepareStatement(sqlTxt);

            prepStmt.setString(1, status);

            prepStmt.execute();

            prepStmt.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void saveReservationStateChanges(int id, String state)
    {
        String sqlTxt = "UPDATE  `nordic_motorhomes`.`reservations` SET  `state` = ? WHERE  `reservations`.`id` =" + id;

        try
        {
            PreparedStatement prepStmt =
                    conn.prepareStatement(sqlTxt);

            prepStmt.setString(1, state);

            prepStmt.execute();

            prepStmt.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
