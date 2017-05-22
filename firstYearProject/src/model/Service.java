package model;

import db.CamperWrapper;
import db.ServiceWrapper;

/**
 * Created by Dunkl on 15/05/2017.
 */
public class Service
{
    ServiceWrapper wrapper = ServiceWrapper.getInstance();
    CamperWrapper camperWrapper = CamperWrapper.getInstance();

    private int id = -1;
    private int camperId;
    private String camperPlate;

    private double kmCount;

    private boolean kmChecked = false;
    private boolean enoughGas = false;
    private boolean noRepair = false;
    private boolean cleaned = false;

    public Service()
    {

    }

    public Service(int id, int camperId, String camperPlate, double kmCount)
    {
        this.id = id;
        this.camperId = camperId;
        this.camperPlate = camperPlate;
        this.kmCount = kmCount;
    }

    public Service(int id, double kmCount, boolean kmChecked,
                   boolean enoughGas, boolean noRepair, boolean cleaned)
    {
        this.id = id;
        this.kmCount = kmCount;
        this.kmChecked = kmChecked;
        this.enoughGas = enoughGas;
        this.noRepair = noRepair;
        this.cleaned = cleaned;
    }

    public Service(int id, int camperId, String camperPlate,
                   double kmCount, boolean kmChecked,
                   boolean enoughGas, boolean noRepair,
                   boolean cleaned)
    {
        this.id = id;
        this.camperId = camperId;
        this.camperPlate = camperPlate;
        this.kmCount = kmCount;
        this.kmChecked = kmChecked;
        this.enoughGas = enoughGas;
        this.noRepair = noRepair;
        this.cleaned = cleaned;
    }

    public boolean markDone()
    {
        if (!isReady())
        {
            return false;
        }

        if (camperWrapper.saveStatusAndKm(camperId, "available", kmCount))
        {
            wrapper.delete(id);
            return true;
        }
        return false;
    }

    public boolean saveNew(Camper camper)
    {
        camperId = camper.getId();
        kmCount = camper.getKmCount();

        if (camperWrapper.saveStatusAndKm(camperId, "not available", kmCount))
        {
            wrapper.saveNew(this);
            return true;
        }
        return false;
    }

    public boolean update()
    {
        return wrapper.update(this);
    }

    public boolean load (int id)
    {
        Service service = wrapper.load(id);

        if (service == null)
        {
            return false;
        }

        setId(id);
        setCamperId(service.getCamperId());
        setCamperPlate(service.getCamperPlate());

        setKmCount(service.getKmCount());

        setKmChecked(service.getKmChecked());
        setEnoughGas(service.getEnoughGas());
        setNoRepair(service.getNoRepair());
        setCleaned(service.getCleaned());

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

    //region Getters & setters
    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getCamperId()
    {
        return camperId;
    }

    public void setCamperId(int camperId)
    {
        this.camperId = camperId;
    }

    public String getCamperPlate()
    {
        return camperPlate;
    }

    public void setCamperPlate(String camperPlate)
    {
        this.camperPlate = camperPlate;
    }

    public double getKmCount()
    {
        return kmCount;
    }

    public void setKmCount(double kmCount)
    {
        this.kmCount = kmCount;
    }

    public boolean isReady()
    {
        if (kmChecked && cleaned)
        {
            return true;
        }
        return false;
    }

    public boolean getKmChecked()
    {
        return kmChecked;
    }

    public void setKmChecked(boolean kmChecked)
    {
        this.kmChecked = kmChecked;
    }

    public boolean getEnoughGas()
    {
        return enoughGas;
    }

    public void setEnoughGas(boolean enoughGas)
    {
        this.enoughGas = enoughGas;
    }

    public boolean getNoRepair()
    {
        return noRepair;
    }

    public void setNoRepair(boolean noRepair)
    {
        this.noRepair = noRepair;
    }

    public boolean getCleaned()
    {
        return cleaned;
    }

    public void setCleaned(boolean cleaned)
    {
        this.cleaned = cleaned;
    }

    public String getMechStatus()
    {
        if (kmChecked)
        {
            return "done";
        }
        return "not done";
    }

    public String getCleanStatus()
    {
        if (cleaned)
        {
            return "done";
        }
        return "not done";
    }
    //endregion
}
