package app.controllers.popUp;

import app.Models.Abonnee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ProlongerDateController {
    @FXML
    public DatePicker newDateLimiteDatePicker;
    @FXML
    public Label valeurIdentifiant;
    @FXML
    public Label valeurNom;
    @FXML
    public Label valeurPrenom;
    @FXML
    public Label valeurDateNaissance;

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
