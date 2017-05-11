package controller;

import db.DBCon;
import model.CamperType;

/**
 * Created by Jakub on 09.05.2017.
 */
public class AccController
{
    public void saveCamperType(
            int id,
            String brand,
            String model,
            int capacity,
            double price,
            String description)
    {
        CamperType camperType = new CamperType(
                id, brand, model, capacity, price, description);
        camperType.save();
        DBCon.close();
    }
}
