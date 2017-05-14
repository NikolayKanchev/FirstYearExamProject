package model;

import db.MotorhomeDepotWrapper;

import java.sql.Date;

/**
 * Created by Jakub on 09.05.2017.
 */
public class Rental extends Order
{
    MotorhomeDepotWrapper depotWrapper = MotorhomeDepotWrapper.getInstance();

    private double reservPrice;
    private String contract;
    private double extraKilometers;
    private double gasFee;
    private double damagedPrice;
    private int reservID;
    private int rv_id;
    private int customer_id;

    public Rental(int id, Date startDate, Date endDate, String startLocation, String endLocation, int assistantID)
    {
        super(id, startDate, endDate, startLocation, endLocation, assistantID);
    }

    public String getContract()
    {
        return contract;
    }

    public void setContract(String contract)
    {
        this.contract = contract;
    }

    public double getExtraKilometers()
    {
        return extraKilometers;
    }

    public void setExtraKilometers(double extraKilometers)
    {
        this.extraKilometers = extraKilometers;
    }

    public double getGasFee()
    {
        return gasFee;
    }

    public void setGasFee(double gasFee)
    {
        this.gasFee = gasFee;
    }

    public double getDamagedPrice()
    {
        return damagedPrice;
    }

    public void setDamagedPrice(double damagedPrice)
    {
        this.damagedPrice = damagedPrice;
    }

    public double getReservPrice()
    {
        return reservPrice;
    }

    public void setReservPrice(double reservPrice)
    {
        this.reservPrice = reservPrice;
    }

    public int getReservID()
    {
        return reservID;
    }

    public void setReservID(int reservID)
    {
        this.reservID = reservID;
    }

    public void save()
    {
        depotWrapper.createRental(this);
    }

    public void delete()
    {
        depotWrapper.deleteRental(this.getId());
    }

    public int getRv_id()
    {
        return rv_id;
    }

    public void setRv_id(int rv_id)
    {
        this.rv_id = rv_id;
    }

    public int getCustomer_id()
    {
        return customer_id;
    }

    public void setCustomer_id(int customer_id)
    {
        this.customer_id = customer_id;
    }
}
