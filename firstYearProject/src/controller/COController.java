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

        Date yesterday = Date.valueOf(LocalDate.now().minusDays(1));

        for (Motorhome camper: allAvailable)
        {
            if(camper.getRvTypeID() == selectedReservation.getRvTypeID() && selectedReservation.getStartDate().after(yesterday))
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

    public void createRental(Reservation selectedReservation, Motorhome selectedMotorhome)
    {
        Rental newRental = new Rental(
                -1, selectedReservation.getStartDate(), selectedReservation.getEndDate(),
                selectedReservation.getStartLocation(), selectedReservation.getEndLocation(),
                selectedReservation.getAssistantID());
        newRental.setReservID(selectedReservation.getId());
        newRental.setReservPrice(selectedReservation.getEstimatedPrice());
        newRental.setContract(generateContract(selectedReservation,selectedMotorhome));
    }

    private String generateContract(Reservation selectedReservation, Motorhome selectedMotorhome)
    {
        String contract = "Camper Rental Contract\n" +
                "\n" +
                "This Camper Rental Agreement (“Agreement”) is made and entered into as of "+ LocalDate.now() +" between\n" +
                "Nordic Motor Home Rental, with an address of Universitetsparken 1, 4000 Roskilde (\"Owner\"), and\n" +
                "____________________, with an address of ______________________ (\"Renter\"). Owner and Renter may also be\n" +
                "referred to as “Party” in the singular and “Parties” in the plural. This Agreement is subject to the following terms and\n" +
                "conditions:\n" +
                "Rental Vehicle\n" +
                "Owner hereby agrees to rent to Renter the following vehicle (“Vehicle”):\n" +
                "Make: ******************* Model: __________________\n" +
                "Year: ___________________ Color: ___________________\n" +
                "Mileage: ________________ VIN: __________________\n" +
                "Rental Period\n" +
                "Owner agrees to rent Vehicle to Renter for the following period:\n" +
                "Start Date: ___________________ End Date: _____________________\n" +
                "The Parties agrees that this Agreement terminates upon the End Date specified above. Notwithstanding anything to\n" +
                "the contrary in this Agreement or any Exhibits, either Party may terminate this Agreement prior to the End Date with\n" +
                "at least one (1) day notice. If this Agreement is terminated prior to the End Date, the Parties will work together to\n" +
                "determine whether a refund of Rental Fees is necessary.\n" +
                "Mileage Limit\n" +
                "Renter will obey the following mileage limit for the Vehicle:\n" +
                "[ ] No mileage limit [ ] __________ miles\n" +
                "Rental Fees\n" +
                "The Renter hereby agrees to pay the Owner for use of the Vehicle as follows:\n" +
                "Fees: $______ per day / week.\n" +
                "Fuel: Renter shall pay / is not required to pay for the use of fuel.\n" +
                "Excess Mileage: $______ per mile\n" +
                "Deposit: $_______. Owner shall retain this deposit to be used, in the event of loss of or damage to the\n" +
                "Vehicle during the term of this Agreement, to defray fully or partially the cost of necessary repairs or\n" +
                "replacement. In the absence of damage or loss, said deposit shall be credited toward payment of the rental\n" +
                "fee and any excess shall be returned to the Renter";

        return contract;
    }
}
