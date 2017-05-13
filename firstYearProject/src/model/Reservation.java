package model;

import db.MotorhomeDepotWrapper;

import java.sql.Date;

/**
 * Created by Jakub on 09.05.2017.
 */
public class Reservation extends Order
{
    MotorhomeDepotWrapper depotWrapper = MotorhomeDepotWrapper.getInstance();

    private Date creationDate;
    private String state;
    private double estimatedPrice;
    private int rvTypeID;
    private int customerID;

    public Reservation(int id, Date startDate, Date endDate, String startLocation, String endLocation,
                       int assistantID, Date creationDate, String state, double estimatedPrice )
    {
        super(id, startDate, endDate, startLocation, endLocation, assistantID);
        this.creationDate = creationDate;
        this.state = state;
        this.estimatedPrice = estimatedPrice;

    }

    public void cancelReservation()
    {

    }

    //region GETTERS AND SETTERS

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
        saveStateChanges(state);
    }

    private void saveStateChanges(String state)
    {
        depotWrapper.saveReservationStateChanges(getId(), state);
    }

    public double getEstimatedPrice() {
        return estimatedPrice;
    }

    public void setEstimatedPrice(double estimatedPrice) {
        this.estimatedPrice = estimatedPrice;
    }

    public int getRvTypeID()
    {
        return rvTypeID;
    }

    public void setRvTypeID(int rvTypeID)
    {
        this.rvTypeID = rvTypeID;
    }

    public int getCustomerID()
    {
        return customerID;
    }

    public void setCustomerID(int customerID)
    {
        this.customerID = customerID;
    }

    //endregion
}
