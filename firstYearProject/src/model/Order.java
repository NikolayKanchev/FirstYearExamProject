package model;

import java.sql.Date;

/**
 * Created by Jakub on 09.05.2017.
 */
public abstract class Order
{
    private int id;
    private Date startDate;
    private Date endDate;
    private String startLocation;
    private String endLocation;
    private int assistantID;

    public Order(int id, Date startDate, Date endDate, String startLocation, String endLocation, int assistantID)
    {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.assistantID = assistantID;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public Date getStartDate()
    {
        return startDate;
    }

    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }

    public String getStartLocation()
    {
        return startLocation;
    }

    public void setStartLocation(String startLocation)
    {
        this.startLocation = startLocation;
    }

    public String getEndLocation()
    {
        return endLocation;
    }

    public void setEndLocation(String endLocation)
    {
        this.endLocation = endLocation;
    }

    public int getAssistantID()
    {
        return assistantID;
    }

    public void setAssistantID(int assistantID)
    {
        this.assistantID = assistantID;
    }
}
