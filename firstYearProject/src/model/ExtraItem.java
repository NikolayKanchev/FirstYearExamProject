package model;

import db.InventoryWrapper;

import java.util.ArrayList;

/**
 * Created by Jakub on 09.05.2017.
 */
public class ExtraItem
{
    private Integer id;
    private String name;
    private Double price;




    public ExtraItem(Integer id, String name, Double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }


    //region GETTERS AND SETTERS

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    //endregion
}
