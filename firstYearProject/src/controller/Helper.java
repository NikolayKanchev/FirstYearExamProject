package controller;

import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;

import java.io.IOException;
import java.sql.Date;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static java.util.Calendar.MONTH;
import static java.util.Calendar.getAvailableCalendarTypes;

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


    //this method checks what season are we in, returns price of period
    //represents camper price rise in % converted into Double

    //we need to figure out how to check every day

    public static Double seasonalPriceChange(LocalDate startDate, LocalDate endDate, Double priceOfMotorhomePerDay)
    {

        double price;
        price = priceOfMotorhomePerDay;  //just for naming purposes
        double priceTotal = 0;

        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1))
        {

            Month currentMonth = date.getMonth();

            if (currentMonth.getValue() <= 3 || currentMonth.getValue() >= 11) {
                priceTotal += price;
            } else if (currentMonth.getValue() >= 3 && currentMonth.getValue() <= 6) {
                priceTotal += price*1.5;
            } else if (currentMonth.getValue() >= 6 && currentMonth.getValue() <= 11) {
                priceTotal += price*1.7;
            } else {
                System.out.println("FAILED DATE VALIDATION");
                priceTotal += price;
            }
        }

        return priceTotal;
    }
    public static void displayError (String title,String header,String content)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }
    public static void dispplayConfirmation(String title,String header,String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();

    }
}
