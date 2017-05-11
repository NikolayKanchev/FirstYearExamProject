package model;

import db.InventoryWrapper;
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
    InventoryWrapper inventoryWrapper = InventoryWrapper.getInstance();



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
        return inventoryWrapper.getExtras();
    }

}

