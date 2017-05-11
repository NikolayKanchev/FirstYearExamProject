package model;

import java.sql.Date;

/**
 * Created by Jakub on 09.05.2017.
 */
public class Reservation extends Order
{
    public Reservation(int id, Date startDate, Date endDate, String startLocation, String endLocation, int assistantID)
    {
        super(id, startDate, endDate, startLocation, endLocation, assistantID);
    }
}
