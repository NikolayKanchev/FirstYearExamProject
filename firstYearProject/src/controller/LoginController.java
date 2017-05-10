package controller;

import javafx.event.ActionEvent;
import model.MotorhomeDepot;
import model.Person;
import view.Screen;

import java.io.IOException;

/**
 * Created by Jakub on 09.05.2017.
 */
public class LoginController
{

    Screen screen = new Screen();

    MotorhomeDepot mhDepot = new MotorhomeDepot();

    public boolean validateUser(String eMail, String pass, ActionEvent event) throws IOException
    {
        Person signedInPerson = mhDepot.validateUser(eMail, pass);

        if (signedInPerson != null)
        {
            switch (signedInPerson.getStatus())
            {
                case "accountant" :
                    screen.change(event, "orders.fxml");
                    return true;

                case "employee" :
                    screen.change(event, "orders.fxml");
                    return true;

                case "admin" :
                    screen.change(event, "employees.fxml");
                    return true;

                case "customer" :
                    screen.change(event, "orderedit.fxml");
                    return true;

                case "assistant" :
                    screen.change(event, "orders.fxml");
                    return true;

                case "mechanic" :
                    screen.change(event, "service.fxml");
                    return true;

                case "cleaner" :
                    screen.change(event, "service.fxml");
                    return true;

            }
        }
        return false;
    }

    public void changeScreen()
    {

    }
}
