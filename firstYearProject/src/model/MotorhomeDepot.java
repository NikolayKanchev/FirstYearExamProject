package model;

import db.PersonWrapper;

/**
 * Created by Jakub on 09.05.2017.
 */
public class MotorhomeDepot
{
    Person signedInPerson;

    PersonWrapper personWrapper = PersonWrapper.getInstance();

    public boolean validateUser(String eMail, String pass)
    {

        boolean userExist = false;

        signedInPerson = personWrapper.getPerson(eMail, pass);

        if(signedInPerson != null)
        {
            userExist = true;
        }

        return userExist;
    }
}

