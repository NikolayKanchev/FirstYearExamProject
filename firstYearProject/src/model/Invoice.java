package model;

import db.DepotWrapper;

import java.sql.Date;
import java.time.LocalDate;

/**
 * Created by Nikolaj on 22-05-2017.
 */
public class Invoice
{
    DepotWrapper depotWrapper = DepotWrapper.getInstance();

    private int id;
    private int rentalID;
    private String text;
    private java.sql.Date date;

    public Invoice(int rentalID, String text, Date date)
    {
        this.rentalID = rentalID;
        this.text = text;
        this.date = date;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getRentalID()
    {
        return rentalID;
    }

    public void setRentalID(int rentalID)
    {
        this.rentalID = rentalID;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public void save()
    {
        depotWrapper.saveInvoice(this);
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    @Override
    public String toString()
    {
        return "Invoice nr.: " + id + " \t" + date;
    }
}
