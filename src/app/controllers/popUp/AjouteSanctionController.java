package app.controllers.popUp;

import app.Models.Abonnee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AjouteSanctionController {

    @FXML
    private Label valeurIdentifiant;
    @FXML
    private Label valeurNom;
    @FXML
    private Label valeurPrenom;
    @FXML
    private Label valeurDateNaissance;


    public void onClickAjouteExemplaire(ActionEvent actionEvent) {
    }

    public void onClickAnnuler(ActionEvent actionEvent) {
        Node source = (Node)  actionEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void setAbonnee(Abonnee abonnee) {
        valeurIdentifiant.setText(abonnee.getIdentifiant());
        valeurNom.setText(abonnee.getNom());
        valeurPrenom.setText(abonnee.getPrenom());

    }
}
