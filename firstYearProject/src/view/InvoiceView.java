package view;

import com.jfoenix.controls.JFXComboBox;
import controller.COController;
import controller.Helper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import model.Invoice;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Nikolaj on 14-05-2017.
 */
public class InvoiceView implements Initializable
{
    private Screen screen = new Screen();

    private COController coController = new COController();

    private Invoice selectedInvoice = null;

    @FXML
    TextArea textArea;

    @FXML
    JFXComboBox comboBox;


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        ObservableList<Invoice> invoices = FXCollections.observableArrayList();

        invoices.addAll(coController.getInvoices());

        comboBox.setItems(invoices);

        textArea.setText(invoices.get(0).getText());

        comboBox.valueProperty().addListener(new ChangeListener()
        {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue)
            {
                selectedInvoice = (Invoice) comboBox.getSelectionModel().getSelectedItem();

                textArea.setText(selectedInvoice.getText());
            }
        });


    }

    public void goBack(ActionEvent event) throws IOException
    {
       screen.change(event, "rental.fxml");
    }

    public void print(ActionEvent event) throws IOException
    {
        screen.change(event, "rental.fxml");
    }

    public void goToPayment(ActionEvent event)
    {
        boolean paymentValidation = coController.validatePayment();

        if(paymentValidation)
        {
            Helper.dispplayConfirmation("Payment validation", null, "The payment has been verified");
        }
    }

}
