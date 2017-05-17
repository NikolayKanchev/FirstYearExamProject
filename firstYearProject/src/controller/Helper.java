package controller;

import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static java.util.Calendar.MONTH;

/**
 * Created by Dunkl on 11/05/2017.
 */
public class Helper
{
    public static view.Screen screen = new view.Screen();

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

    public static void doubleClick(MouseEvent mouseEvent, TableView table, String name) {
        table.setOnMousePressed(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent)
            {
                if (mouseEvent.isPrimaryButtonDown() && mouseEvent.getClickCount() == 2)
                {
                    try {

                        screen.changeOnMouse(mouseEvent, name);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            }
        });
    }


    public boolean getDouble(String text)
    {
        boolean bul = false;

        try
        {

            int i = Integer.parseInt(text);

            return true;

        }catch (Exception e)
        {
            try
            {

                double d = Double.parseDouble(text);

                return true;

            }catch (Exception ex)
            {

                return false;
            }

        }

    }


    //this method checks what season are we in, returns ?? if low, ?? if medium and ?? if high season
    //represents camper price rise in % converted into Double

    //we need to figure out how to check every day
    public static Double seasonalPriceChange(LocalDate startDate)
    {
        Month currentMonth = startDate.getMonth();

        if (currentMonth.getValue() <= 3 || currentMonth.getValue() >= 11)
        {
            return 1.0;
        }
        else if (currentMonth.getValue() >= 3 && currentMonth.getValue() <= 6 )
        {
            return 1.5;
        }
        else if (currentMonth.getValue() >= 6 && currentMonth.getValue() <= 11)
        {
            return 1.7;
        }
        else {
            System.out.println("FAILED DATE VALIDATION");
            return 1.0;
        }
    }
}
