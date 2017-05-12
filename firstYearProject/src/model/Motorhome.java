package model;

/**
 * Created by Jakub on 09.05.2017.
 */
public class Motorhome
{
    private int id;
    private int rvTypeID;
    private String plate;
    private String status;
    private double kmCount;

    public Motorhome(int id, int rvTypeID, String plate, String status, double kmCount)
    {
        this.id = id;
        this.rvTypeID = rvTypeID;
        this.plate = plate;
        this.status = status;
        this.kmCount = kmCount;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getRvTypeID()
    {
        return rvTypeID;
    }

    public void setRvTypeID(int rvTypeID)
    {
        this.rvTypeID = rvTypeID;
    }

    public String getPlate()
    {
        return plate;
    }

    public void setPlate(String plate)
    {
        this.plate = plate;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public double getKmCount()
    {
        return kmCount;
    }

    public void setKmCount(double kmCount)
    {
        this.kmCount = kmCount;
    }
}
