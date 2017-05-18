package db;

import model.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.util.Date;

/**
 * Created by bc on 09/05/2017.
 */
public class DepotWrapper
{
    private static DepotWrapper depotWrapper;

    private DepotWrapper() {}

    private Connection conn = DBCon.getConn();



    public synchronized static DepotWrapper getInstance()
    {
        if (depotWrapper==null)
        {
            depotWrapper = new DepotWrapper();
        }
        return depotWrapper;
    }

    public ArrayList<Service> getServices()
    {
        ArrayList<Service> services = new ArrayList<>();

        try
        {
            String sql =
                    "SELECT " +
                            "service.id, service.camper_id, rvs.plate, service.km_count, " +
                            "service.km_checked, service.enough_gas, " +
                            "service.no_repair, service.cleaned " +

                            "FROM service, rvs " +

                            "WHERE service.camper_id = rvs.id";

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                int id = rs.getInt("id");
                int camperId = rs.getInt("camper_id");
                String camperPlate = rs.getString("plate");
                double kmCount = rs.getDouble("km_count");
                boolean kmChecked = rs.getInt("km_checked") != 0;
                boolean enoughGas = rs.getInt("enough_gas") != 0;
                boolean noRepair = rs.getInt("no_repair") != 0;
                boolean cleaned = rs.getInt("cleaned") != 0;

                services.add(new Service(id, camperId, camperPlate,
                        kmCount, kmChecked, enoughGas, noRepair, cleaned));
            }

            ps.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return services;
    }


  /*  public ArrayList<Employee> getEmployee(){
        ArrayList <Employee> employees = new ArrayList<>();
        try
        {
            String sql = "SELECT * FROM `nordic_motorhomes`.`persons`";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                employees.add(
                        new Employee(

                                rs.getString("first_name"),
                                rs.getString("last_name"),
                                rs.getString("address"),
                                rs.getString("cpr"),
                                rs.getString("driver_license"),
                                rs.getString("e_mail"),
                                rs.getString("phone"),
                                rs.getString("pass")


                        )
                );
            }
            ps.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return employees;
    }*/


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
                CamperType type = new CamperType(rs.getInt("id"), rs.getString("brand"),
                        rs.getString("model"), rs.getInt("capacity"),
                        rs.getDouble("base_price"), rs.getString("description"));
                type.setDeliveryKmPrice(rs.getDouble("km_price"));
                list.add(type);
            }
            ps.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return list;
    }

    public ArrayList<Camper> getCampers()
    {
        ArrayList<Camper> campers = new ArrayList<>();

        try
        {
            String sql = "SELECT * FROM `nordic_motorhomes`.`rvs`";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                campers.add(
                        new Camper(
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
            String sql = "SELECT * FROM reservations";

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

    public ArrayList<Camper> getAvailableCampers()
    {

        ArrayList<Camper> availableCampers = new ArrayList<>();

        try
        {
            String sql = "SELECT * FROM rvs WHERE status = 'available'";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                availableCampers.add
                        (new Camper(
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
                r.setRv_id(rs.getInt("rv_id"));
                r.setCustomer_id(rs.getInt("customer_id"));

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
                "`reserv_price`, `contract`, `extra_km`, `gas_fee`, `damaged_price`, `reserv_id`, `rv_id`, `customer_id`) " +
                "" +
                "VALUES (NULL, '"+rental.getStartDate()+"', '"+ rental.getEndDate() +"'," +
                " ?, ?, " +
                "?, ?, " +
                " ?, '0', '0', '0', '"+ rental.getReservID() +"', '"+ rental.getRv_id() +"', '"+ rental.getCustomer_id() +"');";

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

    public void deleteRental(int id)
    {
        String sql = "DELETE FROM rentals WHERE id = " + id;

        try
        {
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.executeUpdate();

            statement.close();

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public ArrayList<Rental> getRentalsBySearchText(String text)
    {
        ArrayList<Rental> rentals = new ArrayList<>();

        String sql = "SELECT * FROM rentals WHERE id LIKE ? OR start_date LIKE ? OR end_date LIKE ? OR start_location LIKE ? OR reserv_id LIKE ?;";

        try
        {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, "%"+text+"%");
            ps.setString(2, "%"+text+"%");
            ps.setString(3, "%"+text+"%");
            ps.setString(4, "%"+text+"%");
            ps.setString(5, "%"+text+"%");

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
                r.setRv_id(rs.getInt("rv_id"));
                r.setCustomer_id(rs.getInt("customer_id"));

                rentals.add(r);
            }

            ps.close();

        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return rentals;
    }

    public ArrayList<Reservation> getReservationsBySearchText(String text)
    {
        ArrayList<Reservation> reservations = new ArrayList<>();

        String sql = "SELECT * FROM reservations WHERE id LIKE ? OR start_date LIKE ? OR end_date LIKE ? OR start_location LIKE ?;";

        try
        {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, "%"+text+"%");
            ps.setString(2, "%"+text+"%");
            ps.setString(3, "%"+text+"%");
            ps.setString(4, "%"+text+"%");

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


    public ArrayList<Camper> getValidCampers(String selectedType, LocalDate startDate, LocalDate endDate)
    {
        ArrayList<Camper> campers = new ArrayList<>();
        ArrayList<Camper> availableCampers = new ArrayList<>();
        ArrayList<Reservation> reservations = new ArrayList<>();
        int id = 0;

        java.sql.Date startingDate = java.sql.Date.valueOf(startDate);
        java.sql.Date endingDate = java.sql.Date.valueOf(endDate);

        //step 1.) get ID from selectedType String

        String sql = "SELECT id FROM rvs_type WHERE brand = ? ;";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,selectedType);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //step 2.) select every camper for this id

        sql = "SELECT * FROM rvs WHERE rv_type = ?;";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                campers.add(new Camper(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getDouble(5)));
            }
            System.out.println("CAMPERS: ");
            for (Camper camper:campers)
            {
                System.out.println(camper.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //counts how many reservations ARE done for requested dates (means how many campers are UNavailable)

        for (Camper camper: campers)
        {
            int counter = 0;

            sql = "SELECT * FROM reservations WHERE rv_type = ? AND state != ? AND " +
                    "((start_date >= ? AND end_date >= ? AND start_date <= ? ) OR " +
                    "(start_date <= ? AND end_date <= ? AND end_date > ?) OR " +
                    "(start_date <= ? AND end_date >= ?) OR " +
                    "(start_date >= ? AND end_date <= ?));";
            try
            {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1,id);
                ps.setString(2,"Cancelled");
                ps.setDate(3, startingDate);
                ps.setDate(4, endingDate);
                ps.setDate(5, endingDate);
                ps.setDate(6, startingDate);
                ps.setDate(7, endingDate);
                ps.setDate(8, endingDate);
                ps.setDate(9, startingDate);
                ps.setDate(10, endingDate);
                ps.setDate(11, startingDate);
                ps.setDate(12, endingDate);
                ResultSet rs = ps.executeQuery();
                while (rs.next())
                {
                    System.out.println(startingDate + " <--startingDate");
                    System.out.println(endingDate + " <--endingDate");
                    System.out.println(rs.getDate(2) + " <-- startDate");
                    System.out.println(rs.getDate(3) + " <-- endDate");
                    counter += 1;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("COUNTER: " + counter);


        }

        return availableCampers;
    }
}
