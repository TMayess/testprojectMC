package app.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.swing.text.html.ImageView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MainController implements Initializable {


    public Button acquisitionButton;
    public Button rapportButton;
    public Button empruntButton;
    @FXML
    private Button closeButton;

    @FXML
    private  Button reduceButton;

    @FXML
    private BorderPane bp;



    @FXML
    private  Button restitutionButton;

    private AnchorPane test;
    


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loadPage("gestionEmprunts");

        closeButton.setOnMouseClicked((MouseEvent event) -> {
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        });


        reduceButton.setOnMouseClicked((MouseEvent event) -> {
            Stage stage = (Stage) reduceButton.getScene().getWindow();
            stage.setIconified(true);
        });




        empruntButton.setOnMouseClicked((MouseEvent event) -> {
            loadPage("gestionEmprunts");

        });
        rapportButton.setOnMouseClicked((MouseEvent event) -> {
            loadPage("gestionRapports");
        });
        acquisitionButton.setOnMouseClicked((MouseEvent event) -> {
            loadPage("gestionAcquisitions");
        });
        restitutionButton.setOnMouseClicked((MouseEvent event) -> {
            loadPage("gestionRestitution");

        });

    }

    public void loadPage (String namePage){
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/app/views/"+ namePage +".fxml"));
        } catch (IOException e) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, e);
        }
        bp.setCenter(root);
    }



    @FXML
    public void onClickGestionAcquisitions(ActionEvent actionEvent) {
    }
    @FXML
    public void onClickGestionRapports(ActionEvent actionEvent) {
    }

    @FXML
    public void onClickGestionEmprunts(ActionEvent actionEvent) {
    }

    @FXML
    public void onClickGestionRestitutions(ActionEvent actionEvent) {
    }



}