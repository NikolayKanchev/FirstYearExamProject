package controller;

import db.DBCon;
import db.MotorhomeDepotWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.CamperType;
import model.MotorhomeDepot;

/**
 * Created by Jakub on 09.05.2017.
 */
public class AccController
{


    public boolean saveCamperType(int id,
                                  String brand,
                                  String model,
                                  int capacity,
                                  double price,
                                  String description)
    {
        CamperType camperType = new CamperType(
                id, brand, model, capacity, price, description);

        boolean result = camperType.save();

        return result;
    }

    public ObservableList<CamperType> loadCamperTypes()
    {
        MotorhomeDepot depot = new MotorhomeDepot();

        ObservableList<CamperType> types = FXCollections.observableArrayList();
        types.addAll(depot.getMotorhomeTypes());
        return types;
    }

    public boolean deleteCamperType(int id)
    {
        CamperType type = new CamperType();

        return type.delete();
    }
}
