package app.controllers.popUp;

import app.Models.Abonnee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ValideModificationController {

    @FXML
    private Label valeurIdentifiant;
    @FXML
    private Label valeurNom;
    @FXML
    private Label valeurPrenom;
    @FXML
    private Label valeurDateNaissance;
    @FXML
    private Label valeurRole;
    @FXML
    private Label valeurStatut;

    @FXML
    private Label valeurTitrePopupValidation;

    @FXML
    private Label valeurQuestionPopup;


    public void onClickAjouteExemplaire(ActionEvent actionEvent) {
    }

    public void onClickAnnuler(ActionEvent actionEvent) {
        Node source = (Node)  actionEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void setAbonnee(Abonnee abonne,String titre,String question) {
        valeurIdentifiant.setText(abonne.getIdentifiant());
        valeurNom.setText(abonne.getNom());
        valeurPrenom.setText(abonne.getPrenom());
        valeurDateNaissance.setText(abonne.getDateNaissance().toString());
        valeurRole.setText(abonne.getRole());
        valeurStatut.setText(abonne.getStatut());
        valeurTitrePopupValidation.setText(titre);
        valeurQuestionPopup.setText(question);

    }
}
