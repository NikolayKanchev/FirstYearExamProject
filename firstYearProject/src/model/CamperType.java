package model;

/**
 * Created by Dunkl on 10/05/2017.
 */
public class CamperType
{
    private int id = -1;
    private String brand;
    private String model;
    private int capacity; // Amount of beds
    private String description;
    private double price; // Rental price/day

    public CamperType(int id, String brand, String model, int capacity, String description, double price)
    {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.capacity = capacity;
        this.description = description;
        this.price = price;
    }

    public CamperType(String brand, String model, int capacity, String description, double price)
    {
        this.brand = brand;
        this.model = model;
        this.capacity = capacity;
        this.description = description;
        this.price = price;
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
