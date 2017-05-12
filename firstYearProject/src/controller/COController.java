package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Created by Jakub on 09.05.2017.
 */
public class COController
{

    MotorhomeDepot motorhomeDepot = new MotorhomeDepot();

    public ObservableList<CamperType> getMotorhomeTypes()
    {
        ObservableList<CamperType> types = FXCollections.observableArrayList();
        types.addAll(motorhomeDepot.getMotorhomeTypes());
        return types;
    }

    public ArrayList<ExtraItem> getExtras()
    {
        ArrayList<ExtraItem> extras = new ArrayList<>();
        extras = motorhomeDepot.getExtras();
        return extras;
    }

    public ArrayList<Reservation> getReservations(String str)
    {
        ArrayList<Reservation> allReservations = new ArrayList<>();
        allReservations.addAll(motorhomeDepot.getReservations());

        ArrayList<Reservation> reservationsForPeriod = new ArrayList<>();

        Date today = Date.valueOf(LocalDate.now());

        switch (str)
        {
            case "today":
                for (Reservation r: allReservations)
                {
                    if(r.getStartDate().equals(today))
                    {
                        reservationsForPeriod.add(r);
                    }
                }
                break;

            case "past":
                for (Reservation r: allReservations)
                {
                    if(r.getStartDate().before(today))
                    {
                        reservationsForPeriod.add(r);
                    }
                }
                break;

            case "future":
                for (Reservation r: allReservations)
                {
                    if(r.getStartDate().after(today))
                    {
                        reservationsForPeriod.add(r);
                    }
                }
                break;

            case "all":
                reservationsForPeriod =  allReservations;
                break;
        }

        return reservationsForPeriod;

    }

    public ArrayList<Motorhome> getAvailableCampers(Reservation selectedReservation)
    {
        ArrayList<Motorhome> allAvailable = new ArrayList<>();
        ArrayList<Motorhome> availableOfSelectedType = new ArrayList<>();

        allAvailable = motorhomeDepot.getAvailableCampers(selectedReservation);


        for (Motorhome camper: allAvailable)
        {
            if(camper.getRvTypeID() == selectedReservation.getRvTypeID())
            {
                availableOfSelectedType.add(camper);
            }
        }

        return availableOfSelectedType;
    }

    public ArrayList<Rental> getRentals(String str)
    {

        ArrayList<Rental> allRentals = new ArrayList<>();
        allRentals.addAll(motorhomeDepot.getRentals());

        ArrayList<Rental> rentalsForPeriod = new ArrayList<>();

        Date today = Date.valueOf(LocalDate.now());

        switch (str)
        {
            case "today":
                for (Rental r: allRentals)
                {
                    if(r.getStartDate().equals(today))
                    {
                        rentalsForPeriod.add(r);
                    }
                }
                break;

            case "past":
                for (Rental r: allRentals)
                {
                    if(r.getStartDate().before(today))
                    {
                        rentalsForPeriod.add(r);
                    }
                }
                break;

            case "future":
                for (Rental r: allRentals)
                {
                    if(r.getStartDate().after(today))
                    {
                        rentalsForPeriod.add(r);
                    }
                }
                break;

            case "all":
                rentalsForPeriod =  allRentals;
                break;
        }

        return rentalsForPeriod;

    }
}
