package view;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import controller.LoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.MotorhomeDepot;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * Created by Nikolaj on 08-05-2017.
 */
public class LoginView implements Initializable
{

    LoginController loginController = new LoginController();

    @FXML
    Label redLabel, tip;

    @FXML
    JFXTextField eMailField;

    @FXML
    JFXPasswordField passField;

    @FXML
    Button signInButton;



    public void checkEmailAndPass(ActionEvent actionEvent) throws IOException
    {
        redLabel.setVisible(false);

        boolean userExist = false;

        if(loginController.validateUser(eMailField.getText(), passField.getText(), actionEvent))
        {
            userExist = true;
        }

        if (!userExist)
        {
            redLabel.setVisible(true);
            return;
        }

        loginController.changeScreen();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        Tooltip tooltip = new Tooltip();

        tooltip.setText(
                "To login as an 'Admin' use:\" +\n" +
                        "mechanic - ras@yahoo.com  - raspass\" +\n" +
                        "assistant - nik@yahoo.com  - nikpass\\n\" +\n" +
                        "admin - jak@yahoo.com  - jakpass\" +\n" +
                        "accountant - mar@yahoo.com  - marpass\" +\n" +
                        "cleaner - las@yahoo.com  - raspass");

        tip.setTooltip(tooltip);
    }
}
