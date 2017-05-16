package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Depot;
import model.Service;

/**
 * Created by Jakub on 09.05.2017.
 */
public class ServiceController {

    private Service service = null;

    public ObservableList<Service> loadServices ()
    {
        Depot depot = new Depot();

        ObservableList<Service> services = FXCollections.observableArrayList();
        services.addAll(depot.getServices());
        return services;
    }

    public boolean updateService(int id, double kmCount,
                                 boolean kmChecked, boolean enoughGas,
                                 boolean noRepair, boolean cleaned)
    {
        service = new Service(id, kmCount, kmChecked,
                enoughGas, noRepair, cleaned);

        return service.update();
    }

    public boolean markDone(Service service)
    {
        return service.markDone();
    }

    public Service getService()
    {
        return service;
    }

    public void setService(Service service)
    {
        this.service = service;
    }
}
