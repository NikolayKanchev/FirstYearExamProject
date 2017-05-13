package model;

import db.MotorhomeDepotWrapper;
import db.PersonWrapper;

import java.util.ArrayList;

/**
 * Created by Jakub on 09.05.2017.
 */
public class MotorhomeDepot
{
    Person signedInPerson;

    PersonWrapper personWrapper = PersonWrapper.getInstance();

    MotorhomeDepotWrapper depotWrapper = MotorhomeDepotWrapper.getInstance();

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

    public ArrayList<Motorhome> getAvailableCampers(Reservation selectedReservation)
    {
        return depotWrapper.getAvailableCampers();
    }

    public ArrayList<Rental> getRentals()
    {
        return depotWrapper.getRentals();
    }

    public Customer getCustomer(int customerID)
    {
        return personWrapper.getCustomer(customerID);
    }
}

