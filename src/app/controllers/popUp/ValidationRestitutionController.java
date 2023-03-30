package app.controllers.popUp;

import app.Models.Emprunt;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ValidationRestitutionController {
    @FXML
    public Label valeurAbonnee;
    public Label valeurLivre1;
    public void onClickAjouteExemplaire(ActionEvent actionEvent) {
    }

    public void onClickAnnuler(ActionEvent actionEvent) {
        Node source = (Node)  actionEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();

    }

    public void setEmprunt(Emprunt emprunt) {
        valeurAbonnee.setText(emprunt.abonnee);
        valeurLivre1.setText(emprunt.titreOuvrage);
    }
}
