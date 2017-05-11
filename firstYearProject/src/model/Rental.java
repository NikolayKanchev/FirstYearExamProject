package model;

import java.sql.Date;

/**
 * Created by Jakub on 09.05.2017.
 */
public class Rental extends Order
{
    public Rental(int id, Date startDate, Date endDate, String startLocation, String endLocation, int assistantID)
    {
        super(id, startDate, endDate, startLocation, endLocation, assistantID);
    }
}
