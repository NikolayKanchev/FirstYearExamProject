package controller;

import javafx.event.ActionEvent;
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
    public static int personId;

    Screen screen = new Screen();

    Depot mhDepot = new Depot();

    public boolean validateUser(String eMail, String pass, ActionEvent event) throws IOException
    {
        Person signedInPerson = mhDepot.validateUser(eMail, pass);

        if (signedInPerson != null)
        {
            personId = signedInPerson.getId();
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
    public void countDown (LoginView view, int attemptNo)
    {
        CountDownTimer timer = new CountDownTimer(view, attemptNo);

        Thread thread = new Thread(timer);

        thread.start();
    }

    private class CountDownTimer implements Runnable
    {
        LoginView view;
        int waitTime;

        public CountDownTimer(LoginView view, int attemptNo)
        {
            this.view = view;
            this.waitTime = (attemptNo - 2) * 5000;
        }

        @Override
        public void run()
        {

            view.setCountdown(true, false);

            try
            {
                Thread.sleep(waitTime);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            view.setCountdown(false, true);

            return;
        }
    }

    public static int getPersonId()
    {
        return personId;
    }

    public static void setPersonId(int personId)
    {
        LoginController.personId = personId;
    }

    //endregion
}