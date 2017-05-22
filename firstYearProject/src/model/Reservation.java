package model;

import db.DepotWrapper;

import java.sql.Date;

/**
 * Created by Jakub on 09.05.2017.
 */
public class Reservation extends Order
{
    DepotWrapper depotWrapper = DepotWrapper.getInstance();

    private Date creationDate;
    private String state;
    private double estimatedPrice;
    private int rvTypeID;
    private int customerID;
    private double extraKmStart;
    private double extraKmEnd;


    public Reservation()
    {
        super();
    }

    public Reservation(int id, Date startDate, Date endDate, String startLocation, String endLocation,
                       int assistantID, Date creationDate, String state, double estimatedPrice,
                       double extraKmStart, double extraKmEnd)
    {
        super(id, startDate, endDate, startLocation, endLocation, assistantID);
        this.creationDate = creationDate;
        this.state = state;
        this.estimatedPrice = estimatedPrice;
        this.extraKmStart = extraKmStart;
        this.extraKmEnd = extraKmEnd;
    }

    public Reservation (int id, Date startDate, Date endDate, String startLocation, String endLocation, int assistantID)
    {
        super(id, startDate, endDate, startLocation, endLocation, assistantID);
    }

    public void cancelReservation()
    {

    }

    public int saveNew()
    {
        return depotWrapper.saveNewReservation(this);
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

    public double getExtraKmStart()
    {
        return extraKmStart;
    }

    public void setExtraKmStart(double extraKmStart)
    {
        this.extraKmStart = extraKmStart;
    }

    public double getExtraKmEnd()
    {
        return extraKmEnd;
    }

    public void setExtraKmEnd(double extraKmEnd)
    {
        this.extraKmEnd = extraKmEnd;
    }

    //endregion

    public String toStringCheck ()
    {
        return this.getId() + ", " + this.getStartDate() + ", " + this.getEndDate();
    }
}
