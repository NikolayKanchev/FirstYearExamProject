package view;

import controller.COController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Nikolaj on 14-05-2017.
 */
public class InvoiceView implements Initializable
{
    Screen screen = new Screen();

    @FXML
    TextArea textArea;


    public void goBack(ActionEvent event) throws IOException
    {
       screen.change(event, "rental.fxml");
    }


    public void print(ActionEvent event) throws IOException
    {
        screen.change(event, "rental.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        textArea.setText(COController.getSelectedRental().getContract());
    }

    public void goToPayment(ActionEvent event)
    {

    }
}
