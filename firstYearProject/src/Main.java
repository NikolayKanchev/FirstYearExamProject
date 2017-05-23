
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
        Parent root = FXMLLoader.load(getClass().getResource("view/login.fxml"));
        primaryStage.setTitle("Nordic Motor Home Rental");
=======

    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("view/login.fxml"));
        primaryStage.setTitle("Nordic Motor Homes Rental");
>>>>>>> 88b0ccc0923cf5610f3ecdcdabc3a5ad05e1f55a
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
