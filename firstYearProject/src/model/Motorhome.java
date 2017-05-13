package model;

import db.CamperWrapper;
import db.MotorhomeDepotWrapper;

/**
 * Created by Jakub on 09.05.2017.
 */
public class Motorhome
{
    private CamperWrapper wrapper = CamperWrapper.getInstance();
    private MotorhomeDepotWrapper depotWrapper = MotorhomeDepotWrapper.getInstance();

    private int id;
    private int rvTypeID;
    private CamperType camperType;
    private String plate;
    private String status = "available";
    private double kmCount = 0;

    public Motorhome(int id, int rvTypeID, String plate, String status, double kmCount)
    {
        this.id = id;
        this.rvTypeID = rvTypeID;
        this.plate = plate;
        this.status = status;
        this.kmCount = kmCount;
    }

    //region Rasmus
    public Motorhome(int id, CamperType camperType, String plate)
    {
        setId(id);
        setCamperType(camperType);
        setPlate(plate);
    }

    public Motorhome()
    {
    }

    public boolean save()
    {
        if (id == -1)
        {
            return wrapper.saveNew(this) != -1;
        }
        else
        {
            return wrapper.update(this);
        }
    }

    public boolean load (int id)
    {
        Motorhome camper = wrapper.load(id);

        if (camper == null)
        {
            return false;
        }

        setId(id);
        setRvTypeID(camper.getRvTypeID());
        setPlate(camper.getPlate());
        setStatus(camper.getStatus());
        setKmCount(camper.getKmCount());

        CamperType camperType = new CamperType();

        if (!camperType.load(camper.getRvTypeID()))
        {
            return false;
        }

        setCamperType(camperType);

        return true;
    }

    public boolean delete ()
    {
        return delete(this.id);
    }

    public boolean delete (int id)
    {
        this.id = id;

        return wrapper.delete(id);
    }
    //endregion

    //region Getters & setters
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

    public CamperType getCamperType()
    {
        return camperType;
    }

    public void setCamperType(CamperType camperType)
    {
        setRvTypeID(camperType.getId());
        this.camperType = camperType;
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

        saveStatusChanges(status);
    }

    private void saveStatusChanges(String status)
    {
        depotWrapper.saveCamperStatusChanges(this.id, status);
    }

    public double getKmCount()
    {
        return kmCount;
    }

    public void setKmCount(double kmCount)
    {
        this.kmCount = kmCount;
    }
    //endregion
}
