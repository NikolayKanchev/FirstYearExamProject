package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.CamperType;
import model.ExtraItem;
import model.MotorhomeDepot;
import model.Reservation;

import java.util.ArrayList;

/**
 * Created by Jakub on 09.05.2017.
 */
public class COController
{

    MotorhomeDepot motorhomeDepot = new MotorhomeDepot();

    public ObservableList<CamperType> getMotorhomeTypes()
    {
        ObservableList<CamperType> types = FXCollections.observableArrayList();
        types.addAll(motorhomeDepot.getMotorhomeTypes());
        return types;
    }

    public ArrayList<ExtraItem> getExtras()
    {
        ArrayList<ExtraItem> extras = new ArrayList<>();
        extras = motorhomeDepot.getExtras();
        return extras;
    }

    public ArrayList<Reservation> getReservations(String str)
    {
        return motorhomeDepot.getReservations(str);
    }
}
