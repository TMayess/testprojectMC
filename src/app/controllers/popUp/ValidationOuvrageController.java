package app.controllers.popUp;

import app.Models.Ouvrage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.awt.*;

public class ValidationOuvrageController {
    @FXML
    private Label valeurTitre;

    @FXML
    private Label valeurAuteur;
    @FXML
    private Label valeurRayon;
    @FXML
    private Button confirmerButton;
    @FXML
    private Button annulerButton;

    @FXML
    private Button closeButton;
    private Ouvrage valeurOuvrage;



    public void setOuvrage(Ouvrage ouvrage){
        valeurTitre.setText(ouvrage.titre);
        valeurAuteur.setText(ouvrage.auteur);
        valeurRayon.setText(ouvrage.rayon);
        valeurOuvrage = ouvrage;
    }



    public void onClickAnnuler(ActionEvent actionEvent) {
        Node  source = (Node)  actionEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();

    }

    public void onClickConfirmer(ActionEvent actionEvent) {
        valeurOuvrage.addOuvrage();
        Node  source = (Node)  actionEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
}