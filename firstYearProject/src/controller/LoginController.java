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
                    System.out.println(signedInPerson.getStatus());
                    return true;

                case "employee" :
                    screen.change(event, "orders.fxml");
                    System.out.println(signedInPerson.getStatus());
                    return true;

                case "admin" :
                    screen.change(event, "employees.fxml");
                    System.out.println(signedInPerson.getStatus());
                    return true;

                case "customer" :
                    screen.change(event, "orderedit.fxml");
                    System.out.println(signedInPerson.getStatus());
                    return true;

                case "assistant" :
                    screen.change(event, "orders.fxml");
                    System.out.println(signedInPerson.getStatus());
                    return true;

                case "mechanic" :
                    screen.change(event, "service.fxml");
                    System.out.println(signedInPerson.getStatus());
                    return true;

                case "cleaner" :
                    screen.change(event, "service.fxml");
                    System.out.println(signedInPerson.getStatus());
                    return true;

            }
        }
        return false;
    }

    public void changeScreen()
    {

    }
}
