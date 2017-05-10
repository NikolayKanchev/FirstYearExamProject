package db;

import model.CamperType;

import java.sql.Connection;

/**
 * Created by Dunkl on 10/05/2017.
 */
public class CamperTypeWrapper
{
    private Connection con;

    public CamperTypeWrapper(Connection con)
    {
        this.con = con;
    }

    public void saveNewCamperType(CamperType camperType)
    {

    }
}
