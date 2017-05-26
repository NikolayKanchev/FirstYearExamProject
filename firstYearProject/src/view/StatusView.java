package view;

import controller.AccController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Camper;


import java.net.URL;
import java.util.ResourceBundle;

public class StatusView implements Initializable

{
    // region FXML elements
    @FXML
    TableView<Camper> camperTableView;
    @FXML
    TableColumn<Integer, Camper> camperIdClm;
    @FXML
    TableColumn<Integer, Camper> camperTypeClm;
    @FXML
    TableColumn<String, Camper> plateClm;
    @FXML
    TableColumn<String, Camper> statusClm;
    @FXML
    TableColumn<Double, Camper> kmCountClm;
    @FXML
            public ChoiceBox exitOptions;



// endregion
    AccController acc = new AccController();
@Override
public void initialize(URL location, ResourceBundle resources) {
    loadInfo();
    exitOptions.setItems(FXCollections.observableArrayList("Log out", "Exit"));



}
public void loadInfo ()
{
camperIdClm.setCellValueFactory(new PropertyValueFactory<>("id"));
camperTypeClm.setCellValueFactory(new PropertyValueFactory<>("rvTypeID"));
plateClm.setCellValueFactory(new PropertyValueFactory<>("plate"));
statusClm.setCellValueFactory(new PropertyValueFactory<>("status"));
kmCountClm.setCellValueFactory(new PropertyValueFactory<>("kmCount"));

    ObservableList<Camper> campers = FXCollections.observableArrayList();
    campers.addAll(acc.loadCampers());
    camperTableView.setItems(campers);
}
    public void exitOrLogOut(MouseEvent mouseEvent)
    {
        Screen screen = new Screen();
        screen.exitOrLogOut(mouseEvent, exitOptions);
    }






}
