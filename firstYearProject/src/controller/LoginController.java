package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.Depot;
import model.Person;
import view.LoginView;
import view.Screen;

import java.io.IOException;

/**
 * Created by Jakub on 09.05.2017.
 */
public class LoginController
{

    Screen screen = new Screen();

    Depot mhDepot = new Depot();

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

    //region Login countdown (Rasmus)
    public void countDown (LoginView view)
    {
        CountDownTimer timer = new CountDownTimer(view);

        Thread thread = new Thread(timer);

        thread.start();
    }

    private class CountDownTimer implements Runnable
    {
        LoginView view;

        public CountDownTimer(LoginView view)
        {
            this.view = view;
        }

        @Override
        public void run()
        {

            view.setCountdown(true, false);

            try
            {
                Thread.sleep(5000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            view.setCountdown(false, true);

            return;
        }
    }
    //endregion
}