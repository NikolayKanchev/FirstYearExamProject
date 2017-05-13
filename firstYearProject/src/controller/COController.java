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

        newRental.save();

        selectedMotorhome.setStatus("not available");
        selectedReservation.setState("rental");

    }

    private String generateContract(Reservation selectedReservation, Motorhome selectedMotorhome)
    {
//        Customer customer = motorhomeDepot.getCustomer(selectedReservation.getCustomerID());
//
//        String contract = " " +
//                "                               Nordic Motorhomes rental\n" +
//                "www.nordicMotorHomeRental.com\n" +
//                "Phone: 819 2887 E-mail: nordic@motorhomerentals.com\n" +
//                "\n" +
//                "Renter's Name: "+customer.getFirstName()+" " +customer.getLastName()+"\n" +
//                "Renter's CPR: "+ customer.getCpr() +"\n" +
//                "Renter's address: "+customer.getAddress()+"\n" +
//                "Renter's phone number: "+ customer.getPhoneNum() +"\n" +
//                " Drivers License No. " + customer.getDriverLicenseNum() + "\n\n" +
//
//                "Vehicle License Plate No.   "+selectedMotorhome.getPlate()+"\n" +
//                " \n" +
//                "Pick Up Date: "+selectedReservation.getStartDate()+" Return Date: "+selectedReservation.getEndDate()+"\n" +
//                " \n" +
//                "Pick Up address: "+selectedReservation.getStartLocation()+"\n" +
//                " \n" +
//                "Return address: " + selectedReservation.getStartLocation() + "\n" +
//                "\n" +
//                "                              Nordic Motorhomes rental\n" +
//                "                              (Rental Agreement Form)\n" +
//                "\n" +
//                "\n" +
//                "RV must be returned in the same condition as it was when picked up. Clean and damage free no exceptions! " +
//                " Any damage to any RV caused by the renter or third party are to be reported " +
//                "to Little Adventures RV Rentals immediately! and repaired at renters expense.\n" +
//                "\n" +
//                "(Note:) All RV’S ARE (NON SMOKING) IF CIGARETTE SMOKE SMELL IS " +
//                "DETECTED UPON RETURN RENTER MAY COST EXTRA FEE \n" +
//                "\n" +
//                "* There are No Refunds for early returns….\n" +
//                "\n" +
//                "* Do not transport any items inside the RV trailers other then items included in the RV.\n" +
//                "\n" +
//                "* Pets are not allowed inside the tent trailers or travel trailers. (No exceptions). We are animal lovers " +
//                "ourselves but have to be considerate to other renters or allergies. If animal hair/smell is detected inside the RV, " +
//                "renter may pay extra fee. \n" +
//                "\n" +
//                "* Cleaning If on return of RV or trailer there is dead bugs or outside is dirty or dusty please stop by a carwash " +
//                "and wash trailer or a cleaning fee will apply, If using a pressure washer please take care around vents and stickers " +
//                "on trailer or RV, There is also a cleaning bucket with cleaning supplies and a broom for cleaning inside of each RV. " +
//                "Please be sure to wipe down countertops,floors,fridge,bathroom & shake out rugs.\n" +
//                "\n" +
//                "* Damage to RV generator or other must be reported to Camper rv rentals immediately! " +
//                "So that we can make arrangements for repair and notify the next renter of posable delay. Repairs must not be performed " +
//                "by renter unless authorized by Camper rv rentals. \n" +
//                "\n" +
//                "* If Renting an RV with a bathroom Please make sure to Completely Dump both the Grey water (sink-water) and Black water (sewer) " +
//                "Tanks Before returning or a $200. Dumping Fee Will Apply.\n" +
//                "\n" +
//                "(Please Read and Initial)\n" +
//                "\n" +
//                "Dale and owners or Nordic Motorhomes rental will not be held responsible for any motor or transmission failures, " +
//                "vehicle problems, towing fines or regulations due to towing any of our RVs, travel trailers, utility trailers. If you are unsure " +
//                "of towing regulations, equipment or limitations for your vehicle please check your owners manual or check with your dealer. " +
//                "It is strongly advised to have a BCAA Plus RV Roadside Assistance Card in case of a possible breakdown of tow vehicle as " +
//                "Nordic Motorhomes rental will not be held liable for any towing fees back to Kamloops. " +
//                "Please note all property stored in any of our RVs are the responsibility of the renter we are also not responsible " +
//                "for any personal property.\n" +
//                "\n" +
//                "\n" +
//                "(Please initial)________\n" +
//                "\n" +
//                "Liability: (Please Read, sign and date)\n" +
//                "Dale owners or Nordic Motorhomes rentals will not be held responsible for any and all liability " +
//                "that may occur while any RV is in the custody of the renter. This includes property damage, personal injury, " +
//                "death or any third party liability claims. (Generator or RV at your own risk)\n" +
//                "\n" +
//                "I, the renter _________________________  release Nordic Motorhomes rental or owners Dale " +
//                "of any and all claims of liability or third party liability that may arise from me renting" +
//                "Generator, RV or any other items used or rented form Nordic Motorhomes rental.\n" +
//                "\n" +
//                "\n" +
//                "Renter Signature  ___________________________ Date:______________\n" +
//                "\n";


        return "gfsdgfjsgajl";
    }
}
