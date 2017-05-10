package controller;

import model.MotorhomeDepot;

/**
 * Created by Jakub on 09.05.2017.
 */
public class LoginController
{
    MotorhomeDepot mhDepot = new MotorhomeDepot();

    public boolean validateUser(String eMail, String pass)
    {
        return mhDepot.validateUser(eMail, pass);
    }

    public void changeScreen()
    {

    }
}
