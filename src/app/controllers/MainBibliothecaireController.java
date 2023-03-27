package app.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MainBibliothecaireController implements Initializable {



    @FXML
    private Button closeButton;

    @FXML
    private  Button reduceButton;

    @FXML
    private BorderPane bp;


    @FXML
    private  Button restitutionButton;

    


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loadPage("gestionEmprunts");


    }

    public void loadPage (String namePage){
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/app/views/"+ namePage +".fxml"));
        } catch (IOException e) {
            Logger.getLogger(MainBibliothecaireController.class.getName()).log(Level.SEVERE, null, e);
        }
        bp.setCenter(root);
    }



    @FXML
    public void onClickGestionAcquisitions(ActionEvent actionEvent) {
        loadPage("gestionAcquisitions");
    }
    @FXML
    public void onClickGestionRapports(ActionEvent actionEvent) {
        loadPage("gestionRapports");
    }

    @FXML
    public void onClickGestionEmprunts(ActionEvent actionEvent) {
        loadPage("gestionEmprunts");
    }

    @FXML
    public void onClickGestionRestitutions(ActionEvent actionEvent) {
        loadPage("gestionRestitution");
    }


    public void onClickClose(ActionEvent actionEvent) {
        closeButton.setOnMouseClicked((MouseEvent event) -> {
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        });
    }

    public void onClickReduce(ActionEvent actionEvent) {
        reduceButton.setOnMouseClicked((MouseEvent event) -> {
            Stage stage = (Stage) reduceButton.getScene().getWindow();
            stage.setIconified(true);
        });
    }
}