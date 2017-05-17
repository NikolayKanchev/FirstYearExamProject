package model;

/**
 * Created by Nikolaj on 16-05-2017.
 */
public class ExtrasLineItem
{
    private int id;
    private String extraItemName;
    private int extraItemID;
    private int quantity;
    private double subTotal;

    public ExtrasLineItem(int id, String extraItemName, int extraItemID, int quantity, double subTotal)
    {
        this.id = id;
        this.extraItemName = extraItemName;
        this.extraItemID = extraItemID;
        this.quantity = quantity;
        this.subTotal = subTotal;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getExtraItemName()
    {
        return extraItemName;
    }

    public void setExtraItemName(String extraItemName)
    {
        this.extraItemName = extraItemName;
    }

    public int getExtraItemID()
    {
        return extraItemID;
    }

    public void setExtraItemID(int extraItemID)
    {
        this.extraItemID = extraItemID;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public double getSubTotal()
    {
        return subTotal;
    }

    public void setSubTotal(double subTotal)
    {
        this.subTotal = subTotal;
    }
}
