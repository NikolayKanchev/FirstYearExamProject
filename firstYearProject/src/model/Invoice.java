package model;

import db.DepotWrapper;

/**
 * Created by Nikolaj on 22-05-2017.
 */
public class Invoice
{
    DepotWrapper depotWrapper = DepotWrapper.getInstance();

    private int id;
    private int rentalID;
    private String text;

    public Invoice(int rentalID, String text)
    {
        this.rentalID = rentalID;
        this.text = text;
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
}
