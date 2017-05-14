package view;

import controller.COController;
import javafx.fxml.Initializable;
import model.Customer;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Rasmus on 08-05-2017.
 */
public class CustomerDetailsView implements Initializable
{
    COController coController = new COController();

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        int customerID = COController.getSelectedRentalCustID();
        //Customer selectedCustomer = coController.getCustomer(customerID);
    }
}
