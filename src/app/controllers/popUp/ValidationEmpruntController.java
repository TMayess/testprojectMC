package app.controllers.popUp;

import app.Models.Abonnee;
import app.Models.Emprunt;
import app.Models.Exemplaire;
import app.Models.Ouvrage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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
    public Emprunt globalEmprunt;
    List<Exemplaire> exemplaireList =  new ArrayList<>(3);



    public void setAbonnee(Abonnee abonnee, List<Ouvrage> listOuvrage) {
        System.out.println(listOuvrage.get(0).getExemplaires().get(0).getReference());
        globalEmprunt = new Emprunt(1, LocalDate.now(),LocalDate.now().plusMonths(3), LocalTime.now(),abonnee);


        identifiantValeur.setText(globalEmprunt.getCode()+"");
        nomValeur.setText(abonnee.getNom());
        prenomValeur.setText(abonnee.getPrenom());

        Exemplaire exemplaire = listOuvrage.get(0).getExemplaires().get(0);
        exemplaireList.add(exemplaire);



        refLivre1Valeur.setText(exemplaire.getReference());

        if (listOuvrage.size() > 1){
            exemplaire = listOuvrage.get(1).getExemplaires().get(0);
            exemplaireList.add(exemplaire);
            refLivre2Valeur.setText(exemplaire.getReference());
        }
        if (listOuvrage.size() > 2){
            exemplaire = listOuvrage.get(2).getExemplaires().get(0);
            exemplaireList.add(exemplaire);
            refLivre3Valeur.setText(exemplaire.getReference());
        }

        globalEmprunt.setExemplaires(exemplaireList);
        dateEmpruntValeur.setText(LocalDate.now().toString());
        dateLimiteValeur.setText(LocalDate.now().plusMonths(2).toString());





    }

    public void onClickAnnuler(ActionEvent actionEvent) {
        Node source = (Node)  actionEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void onClickConfirme(ActionEvent actionEvent) {

        globalEmprunt.getExemplaires().get(0).modifDisponibleExemplaire(false);

        if(globalEmprunt.getExemplaires().size() > 1){
            globalEmprunt.getExemplaires().get(1).modifDisponibleExemplaire(false);
        }
        if(globalEmprunt.getExemplaires().size() > 2){
            globalEmprunt.getExemplaires().get(2).modifDisponibleExemplaire(false);
        }
        globalEmprunt.addEmprunt();
        Node source = (Node)  actionEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
