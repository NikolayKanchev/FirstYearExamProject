package view;

import com.jfoenix.controls.JFXButton;
import controller.COController;
import controller.Helper;
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
    public  Button saveNewCustomer, saveButton;
    @FXML
    TextField firstNameTxt,lastNameTxt,cprTxt,drLicenseTxt,phoneNumTxt,emailTxt,addressTxt,passTxt, searchField;
    @FXML
    public Label passLabel;
    @FXML
    public ChoiceBox exitOptions;
    @FXML
    Button createNewCustButton, assignButton;

    // end region
    private Customer selectedCustomer;
    private String screenToGoBack = "";
    private Screen screen = new Screen();
    private COController coController = new COController();
    private int custIDforNewReservation;
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

        createNewCustButton.setVisible(false);
        saveNewCustomer.setVisible(false);

        passTxt.setVisible(false);
        passLabel.setVisible(false);
        selectedCustomer = coController.getSelectedCustomer();

        if(COController.getSelectedRental() == null && COController.getSelectedReservation() != null)
        {
            screenToGoBack = "reservation.fxml";
            saveNewCustomer.setVisible(false);
            createNewCustButton.setVisible(false);
            assignButton.setVisible(false);
            createNewCustButton.setVisible(false);

        }else
        {
            screenToGoBack = "rental.fxml";
            assignButton.setVisible(false);
            createNewCustButton.setVisible(false);
        }

        if(COController.getSelectedRental() == null && COController.getSelectedReservation() == null)
        {

            assignButton.setVisible(true);

            createNewCustButton.setVisible(true);

            saveButton.setVisible(false);

            screenToGoBack = "orderedit.fxml";

            setDisableCustomerFileds(true);

        }else
        {
            loadSelectedCustomer();

        }

        loadCustomers();


        exitOptions.setItems(FXCollections.observableArrayList("Log out", "Exit"));

    }

    public void exitOrLogOut(MouseEvent mouseEvent)
    {
        screen.exitOrLogOut(mouseEvent, exitOptions);

    }

    private void loadSelectedCustomer()
    {
         firstNameTxt.setText(selectedCustomer.getFirstName());
         lastNameTxt.setText(selectedCustomer.getLastName());
         cprTxt.setText(selectedCustomer.getCpr());
         drLicenseTxt.setText(selectedCustomer.getDriverLicense());
         phoneNumTxt.setText(selectedCustomer.getPhoneNum());
         emailTxt.setText(selectedCustomer.getEMail());
         addressTxt.setText(selectedCustomer.getAddress());
    }

    public void setDisableCustomerFileds(boolean b)
    {
        saveNewCustomer.setDisable(b);
        firstNameTxt.setDisable(b);
        lastNameTxt.setDisable(b);
        cprTxt.setDisable(b);
        drLicenseTxt.setDisable(b);
        phoneNumTxt.setDisable(b);
        emailTxt.setDisable(b);
        addressTxt.setDisable(b);
    }

    public void clearCustomerFileds()
    {

        if(screenToGoBack.equals("orderedit.fxml"))
        {
            setDisableCustomerFileds(false);
        }

        saveNewCustomer.setVisible(true);
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

    public void saveCustomer(ActionEvent event) throws IOException
    {
        coController.updateCustomerInfo(selectedCustomer,firstNameTxt,lastNameTxt,cprTxt,drLicenseTxt,phoneNumTxt,emailTxt,addressTxt);

        loadCustomers();

        clearCustomerFileds();

        if(coController.getSelectedCustomer().getId() != selectedCustomer.getId())
        {
            changeOrderCustomer();

            screen.change(event, "orders.fxml");
        }



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
        customerIdClm.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameClm.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameClm.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        emailClm.setCellValueFactory(new PropertyValueFactory<>("eMail"));
        customerCprClm.setCellValueFactory(new PropertyValueFactory<>("cpr"));
        phoneNumClm.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));

        ObservableList<Customer> cstms = FXCollections.observableArrayList();
        cstms.addAll(coController.searchCustomers(searchField.getText()));
        customerTableView.setItems(cstms);

    }
    public void cprRestrict(KeyEvent keyEvent)
    {
        Screen.restrictIntInput(cprTxt);
    }

    public void drLicenseRestrict(KeyEvent keyEvent)
    {
    Screen.restrictIntInput(drLicenseTxt);
    }

    public void phoneRestrict(KeyEvent keyEvent){
        Screen.restrictIntInput(phoneNumTxt);
    }

    public void createCustomer(ActionEvent event) throws IOException
    {

        System.out.println("something");

        if (!checkforEmpty())
        {
            Helper.displayError("ERROR",null,"Please fill the required information");
            return ;

        }
         custIDforNewReservation =  coController.createCustomer(passTxt.getText(),firstNameTxt.getText(),lastNameTxt.getText(),addressTxt.getText(),phoneNumTxt.getText(),drLicenseTxt.getText(),emailTxt.getText(),cprTxt.getText());


        if (screenToGoBack.equals("orderedit.fxml"))
        {
            COController.setCreatedCustomerID(custIDforNewReservation);

            screen.change(event, screenToGoBack);

            return;
        }

        Helper.dispplayConfirmation("Success",null,"Operation has been successful");
        loadCustomers();
        clearCustomerFileds();


    }

    public void selectCustomer(MouseEvent mouseEvent) {

        if(customerTableView.getSelectionModel().getSelectedItem() == null)
        {
            return;
        }

        if(screenToGoBack.equals("orderedit.fxml"))
        {
            setDisableCustomerFileds(true);
        }

        selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();

        loadSelectedCustomer();
    }

    public void assignToNewReservation(ActionEvent event) throws IOException
    {
        custIDforNewReservation = customerTableView.getSelectionModel().getSelectedItem().getId();

        COController.setCreatedCustomerID(custIDforNewReservation);

        screen.change(event, screenToGoBack);
    }
}
