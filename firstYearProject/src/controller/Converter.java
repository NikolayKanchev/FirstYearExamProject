package controller;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Created by Dunkl on 11/05/2017.
 */
public class Converter
{
    public int intFromString(String txt)
    {
        int number;

        try
        {
            number = Integer.parseInt(txt);
        }
        catch (NumberFormatException e)
        {
            return -12345;
        }

        return number;
    }

    public double doubleFromTxt (String txt)
    {
        double number;

        try
        {
            number = Double.parseDouble(txt);
        }
        catch (NumberFormatException e)
        {
            return -12345;
        }

        return number;
    }

    public boolean hasEmptyTxt(String[] txts)
    {
        for (String txt : txts)
        {
            if (txt.isEmpty() || txt == null)
            {
                return true;
            }
        }

        return false;
    }

    // Converts LocalDate to String
    public String txtFromDate(LocalDate date)
    {
        try
        {
            final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return date.format(dtf);
        }
        catch (DateTimeException e)
        {
            return "00/00/0000";
        }
    }

    // Converts String to LocalDate
    public LocalDate dateFromTxt(String date)
    {
        try
        {
            final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return LocalDate.parse(date, dtf);
        }
        catch (DateTimeParseException e)
        {
            return null;
        }
    }
}
