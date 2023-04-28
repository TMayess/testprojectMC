package app.controllers.popUp;

import app.Models.Abonnee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.time.LocalDate;

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

    public Abonnee globalAbonne;

    public void onClickAjouteExemplaire(ActionEvent actionEvent) {
        LocalDate date = newDateLimiteDatePicker.getValue();
        globalAbonne.prolongeEmprunt(date);
        Node source = (Node)  actionEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();

    }

    public void onClickAnnuler(ActionEvent actionEvent) {
        Node source = (Node)  actionEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void setAbonnee(Abonnee abonnee) {
        globalAbonne = abonnee;
        valeurIdentifiant.setText(abonnee.getIdentifiant());
        valeurNom.setText(abonnee.getNom());
        newDateLimiteDatePicker.setValue(LocalDate.now());
        valeurPrenom.setText(abonnee.getPrenom());

    }
}
