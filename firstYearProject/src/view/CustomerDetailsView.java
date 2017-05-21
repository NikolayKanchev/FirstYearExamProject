package view;

import controller.COController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

import javafx.scene.input.MouseEvent;
import model.Customer;
import model.ExtrasLineItem;
import model.Reservation;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

/**
 * Created by Rasmus on 08-05-2017.
 */
public class CustomerDetailsView implements Initializable
{

    // region FXML elements
    @FXML
     public TableView<Customer> customerTableView;
    @FXML
    public TableColumn<Integer,Customer> customerIdClm;
    @FXML
    public TableColumn<String,Customer> customerCprClm;
    @FXML
    public TableColumn<String,Customer> firstNameClm;
    @FXML
    public TableColumn<String,Customer> lastNameClm;
    @FXML
    public TableColumn<String,Customer> emailClm;
    @FXML
    public TableColumn<String,Customer> phoneNumClm;
    @FXML
    public  Button createCustBtn;

    @FXML
    TextField firstNameTxt,lastNameTxt,cprTxt,drLicenseTxt,phoneNumTxt,emailTxt,addressTxt;

    @FXML
    public ChoiceBox exitOptions;




    // end region
    private Customer selectedCustomer;
    private String screenToGoBack = "";
    private Screen screen = new Screen();
    COController coController = new COController();

    private Reservation reservation;
    private ArrayList<ExtrasLineItem> lineItems = new ArrayList<>();




    public boolean checkforEmpty(){
        if (firstNameTxt.getText().isEmpty()||lastNameTxt.getText().isEmpty()||cprTxt.getText().isEmpty()|| drLicenseTxt.getText().isEmpty()||phoneNumTxt.getText().isEmpty()||emailTxt.getText().isEmpty()||addressTxt.getText().isEmpty()){
            return false;
        }
        return true;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        selectedCustomer = coController.getSelectedCustomer();

        if(COController.getSelectedRental() == null && COController.getSelectedReservation() != null)
        {
            screenToGoBack = "reservation.fxml";
        }else
        {
            screenToGoBack = "rental.fxml";
        }


        loadCustomers();
        loadSelectedCustomer();
       // clearCustomerFileds();
        exitOptions.setItems(FXCollections.observableArrayList("Log out", "Exit"));

    }

    public void exitOrLogOut(MouseEvent mouseEvent)
    {
        screen.exitOrLogOut(mouseEvent, exitOptions);

    }

    private void loadSelectedCustomer()
    {
//         if (selectedCustomer == null)
//         {
//             clearCustomerFileds();
//             return;
//         }
         firstNameTxt.setText(selectedCustomer.getFirstName());
         lastNameTxt.setText(selectedCustomer.getLastName());
         cprTxt.setText(selectedCustomer.getCpr());
         drLicenseTxt.setText(selectedCustomer.getDriverLicense());
         phoneNumTxt.setText(selectedCustomer.getPhoneNum());
         emailTxt.setText(selectedCustomer.getEMail());
         addressTxt.setText(selectedCustomer.getAddress());



    }

    public void clearCustomerFileds()
    {
      firstNameTxt.clear();
      lastNameTxt.clear();
      cprTxt.clear();
      drLicenseTxt.clear();
      phoneNumTxt.clear();
      emailTxt.clear();
      addressTxt.clear();
    }

    private void loadCustomers() {

      customerIdClm.setCellValueFactory(new PropertyValueFactory<>("id"));
       firstNameClm.setCellValueFactory(new PropertyValueFactory<>("firstName"));
       lastNameClm.setCellValueFactory(new PropertyValueFactory<>("lastName"));
       emailClm.setCellValueFactory(new PropertyValueFactory<>("eMail"));
       customerCprClm.setCellValueFactory(new PropertyValueFactory<>("cpr"));
       phoneNumClm.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));

      ObservableList<Customer> cstms = FXCollections.observableArrayList();
      cstms.addAll(coController.getCustomers());
      customerTableView.setItems(cstms);



    }


    public void setResAndItems (Reservation reservation,
                                Collection<ExtrasLineItem> lineItems)
    {
        this.reservation = reservation;
        this.lineItems.addAll(lineItems);
    }

    public void goBack(ActionEvent event) throws IOException
    {
        screen.change(event, screenToGoBack);
    }

    public void saveCustomer(ActionEvent event)
    {
        coController.updateCustomerInfo(selectedCustomer,firstNameTxt,lastNameTxt,cprTxt,drLicenseTxt,phoneNumTxt,emailTxt,addressTxt);
        loadCustomers();
        clearCustomerFileds();
        changeOrderCustomer();


    }

    private void changeOrderCustomer()
    {
        String table = "";

        if(screenToGoBack.equals("reservation.fxml"))
        {
            table = "reservations";
        }else
        {
            table = "rentals";
        }

        coController.changeOrderCustomerID(table, selectedCustomer.getId());
        loadCustomers();
    }

    public void searchCustomers(KeyEvent keyEvent)
    {

    }

   
    public void createCustomer(ActionEvent event)
    {



    }



    public void selectCustomer(MouseEvent mouseEvent) {
        selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        loadSelectedCustomer();
    }

}
