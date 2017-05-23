
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
<<<<<<< HEAD
    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("view/customerdetails.fxml"));
=======

    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("view/employees.fxml"));
>>>>>>> 1d1e25fa0413be45e27c831a0c6ffbd063a5f427
        primaryStage.setTitle("Nordic Motor Home Rental");
        primaryStage.setScene(new Scene(root, 1200, 900));
        primaryStage.setMinWidth(900);
        primaryStage.setMinHeight(600);
        primaryStage.show();
    }


    public static void main(String[] args)
    {
        launch(args);
    }
}
