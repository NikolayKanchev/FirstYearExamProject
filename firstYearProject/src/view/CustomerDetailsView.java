package view;

import controller.COController;
import controller.Helper;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Rasmus on 08-05-2017.
 */
public class CustomerDetailsView implements Initializable
{
    private Customer selectedCustomer;
    private String screenToGoBack = "";
    private Screen screen = new Screen();

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        int customerID = COController.getSelectedRentalCustID();

        selectedCustomer = COController.getCustomer(customerID);

        if(COController.getSelectedRental() == null && COController.getSelectedReservation() != null)
        {
            screenToGoBack = "reservation.fxml";
        }else
        {
            screenToGoBack = "rental.fxml";
        }
    }

    public void goBack(ActionEvent event) throws IOException
    {
        screen.change(event, screenToGoBack);
    }

    public void saveCustomer(ActionEvent event)
    {

    }

    public void searchCustomers(KeyEvent keyEvent)
    {

    }


    public void createCustomer(ActionEvent event)
    {

    }

    public void exitOrLogOut(MouseEvent mouseEvent)
    {

    }
}
