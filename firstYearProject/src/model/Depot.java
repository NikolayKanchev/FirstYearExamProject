package model;

import com.jfoenix.controls.JFXComboBox;
import db.DepotWrapper;
import db.PersonWrapper;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Created by Jakub on 09.05.2017.
 */
public class Depot
{
    Person signedInPerson;

    PersonWrapper personWrapper = PersonWrapper.getInstance();

    DepotWrapper depotWrapper = DepotWrapper.getInstance();
    private ArrayList<Customer> customers;

    public Person validateUser(String eMail, String pass)
    {
        signedInPerson = personWrapper.getPerson(eMail, pass);

        if(signedInPerson != null)
        {
            return signedInPerson;
        }
        //personWrapper.hashPassword(); - was used to hash the passwords

        return signedInPerson;
    }

    public ArrayList<Service> getServices ()
    {
        return depotWrapper.getServices();
    }


    public ArrayList<CamperType> getMotorhomeTypes()
    {
        return depotWrapper.getMotorhomeTypes();
    }

    public ArrayList<ExtraItem> getExtras()
    {
        return depotWrapper.getExtras();
    }

    public ArrayList<Reservation> getReservations()
    {
        return depotWrapper.getReservations();
    }

    public ArrayList<Camper> getAvailableCampers(/*Reservation selectedReservation*/) //argument not needed
    {
        return depotWrapper.getAvailableCampers();
    }

    public ArrayList<Camper> getCampers()
    {
        return depotWrapper.getCampers();
    }

    public ArrayList<Rental> getRentals()
    {
        return depotWrapper.getRentals();
    }

    public Customer getCustomer(int customerID)
    {
        return personWrapper.getCustomer(customerID);
    }

    public void setCamperStatus(int rv_id)
    {
        ArrayList<Camper> campers = new ArrayList<>();
        campers.addAll(getCampers());

        System.out.println(rv_id);

        for (Camper camper: campers)
        {
            if(camper.getId() == rv_id)
            {
                camper.setStatus("available");
            }
        }

    }

    public void setReservationStatus(int reservID)
    {
        ArrayList<Reservation> reservations = new ArrayList<>();
        reservations.addAll(getReservations());

        for(Reservation reservation: reservations)
        {
            if(reservation.getId() == reservID)
            {
                reservation.setState("Cancelled");
            }
        }
    }

    public ArrayList<Rental> serchRentals(String text)
    {
        return depotWrapper.getRentalsBySearchText(text);
    }

    public ArrayList<Reservation> searchReservations(String text)
    {
        return depotWrapper.getReservationsBySearchText(text);
    }
    public ArrayList<Employee> getEmployees()
    {

        ArrayList<Employee> employees =  PersonWrapper.getInstance().readEmployee();

        return employees;
    }

    public boolean getValidCampers(int typeId, LocalDate startDate, LocalDate endDate)
    {if (depotWrapper.checkAvailability(typeId, startDate, endDate))
        {
            return true;
        }
        else
        {
            return false;
        }


    }

    public CamperType getCamperType(int rvTypeID)
    {
        for (CamperType camperType: getMotorhomeTypes())
        {
            if(camperType.getId() == rvTypeID);
            return camperType;
        }
        return null;
    }

    public ArrayList<Customer> getCustomers() {

        return personWrapper.getCustomers();
    }
}

