package app.controllers.popUp;

import app.Models.Emprunt;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class ValidationRestitutionController {
    @FXML
    public Label valeurAbonnee;
    @FXML
    public Label valeurLivre1;
    @FXML
    public Label valeurLivre2;
    @FXML
    public Label valeurLivre3;
    public Emprunt globalEmprunt;
    public void onClickAjouteExemplaire(ActionEvent actionEvent) {
    }

    public void onClickAnnuler(ActionEvent actionEvent) {
        Node source = (Node)  actionEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();

    }

    public void setEmprunt(Emprunt emprunt) {
        globalEmprunt = emprunt;
        valeurAbonnee.setText(emprunt.get_abonnee().getNom()+" "+emprunt.get_abonnee().getPrenom());
        valeurLivre1.setText(emprunt.getExemplaires().get(0).getReference());
        valeurLivre2.setText(emprunt.getExemplaires().get(1).getReference());
        valeurLivre3.setText(emprunt.getExemplaires().get(2).getReference());

    }

    public void onClickRestitution(ActionEvent actionEvent) throws IOException {
        globalEmprunt.valideRestitution();
        globalEmprunt.getExemplaires().get(0).modifDisponibleExemplaire(true);
        globalEmprunt.getExemplaires().get(1).modifDisponibleExemplaire(true);
        globalEmprunt.getExemplaires().get(2).modifDisponibleExemplaire(true);

        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();

    }

}
