package model;

import db.CamperTypeWrapper;

/**
 * Created by Dunkl on 10/05/2017.
 */
public class CamperType
{
    private int id = -1;
    private String brand;
    private String model;
    private int capacity; // Amount of beds
    private double price; // Rental price/day
    private String description;

    public CamperType(int id,
                      String brand,
                      String model,
                      int capacity,
                      double price,
                      String description)
    {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.capacity = capacity;
        this.price = price;
        this.description = description;
    }

    public CamperType(String brand,
                      String model,
                      int capacity,
                      double price,
                      String description)
    {
        this.brand = brand;
        this.model = model;
        this.capacity = capacity;
        this.price = price;
        this.description = description;
    }

    public CamperType()
    {
    }

    public boolean save()
    {
        CamperTypeWrapper wrapper = CamperTypeWrapper.getInstance();

        if (id == -1)
        {
            return wrapper.saveNew(this) != -1;
        }
        else
        {
            return wrapper.update(this);
        }
    }

    public boolean load (int id) {

        this.id = id;

        CamperTypeWrapper wrapper = CamperTypeWrapper.getInstance();

        CamperType camperType = wrapper.load(id);

        if (camperType == null)
        {
            return false;
        }

        this.brand = camperType.getBrand();
        this.model = camperType.getModel();
        this.capacity = camperType.getCapacity();
        this.price = camperType.getPrice();
        this.description = camperType.getDescription();

        return true;
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

    public String getBrand()
    {
        return brand;
    }

    public void setBrand(String brand)
    {
        this.brand = brand;
    }

    public String getModel()
    {
        return model;
    }

    public void setModel(String model)
    {
        this.model = model;
    }

    public int getCapacity()
    {
        return capacity;
    }

    public void setCapacity(int capacity)
    {
        this.capacity = capacity;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }
    //endregion
}
