package view;

import controller.LoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.MotorhomeDepot;


/**
 * Created by Nikolaj on 08-05-2017.
 */
public class LoginView
{

    LoginController loginController = new LoginController();

    @FXML
    Label redLabel;

    @FXML
    TextField eMailField;

    @FXML
    PasswordField passField;

    @FXML
    Button signInButton;



    public void checkEmailAndPass(ActionEvent actionEvent)
    {
        boolean userExist = false;

        if(loginController.validateUser(eMailField.getText(), passField.getText()))
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
}
