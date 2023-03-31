package app.controllers.popUp;

import app.Models.Abonnee;
import app.Models.Ouvrage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

public class ValidationEmpruntController {
    @FXML
    public Label identifiantValeur;
    @FXML

    public Label nomValeur;
    @FXML

    public Label dateEmpruntValeur;
    @FXML

    public Label prenomValeur;
    @FXML

    public Label refLivre1Valeur;
    @FXML

    public Label refLivre2Valeur;
    @FXML

    public Label refLivre3Valeur;
    @FXML

    public Label dateLimiteValeur;



    public void setAbonnee(Abonnee abonnee, List<Ouvrage> listOuvrage) {
        identifiantValeur.setText(abonnee.getIdentifiant());
        nomValeur.setText(abonnee.getNom());
        prenomValeur.setText(abonnee.getPrenom());
        refLivre1Valeur.setText(listOuvrage.get(0).getIdentifiant());
        if (listOuvrage.size() > 1){
            refLivre2Valeur.setText(listOuvrage.get(1).getIdentifiant());
        }
        if (listOuvrage.size() > 2){
            refLivre3Valeur.setText(listOuvrage.get(2).getIdentifiant());
        }
        dateEmpruntValeur.setText(LocalDate.now().toString());
        dateLimiteValeur.setText(LocalDate.now().plusMonths(2).toString());

    }

    public void onClickAnnuler(ActionEvent actionEvent) {
        Node source = (Node)  actionEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void onClickConfirme(ActionEvent actionEvent) {
    }
}
