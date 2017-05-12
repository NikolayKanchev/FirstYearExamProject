package model;

import java.sql.Date;

/**
 * Created by Jakub on 09.05.2017.
 */
public class Rental extends Order
{

    private double reservPrice;
    private String contract;
    private double extraKilometers;
    private double gasFee;
    private double damagedPrice;
    private int reservID;

    public Rental(int id, Date startDate, Date endDate, String startLocation, String endLocation, int assistantID)
    {
        super(id, startDate, endDate, startLocation, endLocation, assistantID);
    }

    public String getContract()
    {
        return contract;
    }

    public void setContract(String contr)
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
}