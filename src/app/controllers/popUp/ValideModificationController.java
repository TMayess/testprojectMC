package app.controllers.popUp;

import app.Models.Abonnee;
import app.Models.Sanction;
import app.Models.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.time.LocalDate;

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
    private Abonnee globalAbonne;
    private Utilisateur globalUtilisateur;

    private String globalTitre;




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
        globalAbonne = abonne;
        globalTitre = titre;

    }

    public void onClickAjouteExemplaire(ActionEvent actionEvent) {
        if (globalTitre.equals("Ajout abonné")){
            globalAbonne.addAbonne();
            System.out.println(globalTitre);
        }else if(globalTitre.equals("suppression abonné")){
            globalAbonne.dropAbonne();
            System.out.println(globalTitre);
        }else if(globalTitre.equals("Sanction abonné")){
            System.out.println(globalAbonne.getIdentifiant().toString());
            Sanction sanction = new Sanction(LocalDate.now(),LocalDate.now().plusMonths(1),globalAbonne.getIdentifiant().toString());
            sanction.addSanction();

        }else if(globalTitre.equals("suppression utilisateur")){
            globalUtilisateur.dropUtilisateur();

        } else if (globalTitre.equals("Ajout utilisateur")) {
            globalUtilisateur.addUtilisateur();
        }
        Node source = (Node)  actionEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();

    }



    public void setUtilisateur(Utilisateur userGlobal, String titre, String s) {
        valeurIdentifiant.setText(userGlobal.getIdentifiant());
        valeurNom.setText(userGlobal.getNom());
        valeurPrenom.setText(userGlobal.getPrenom());
        valeurRole.setText(userGlobal.getRole());
        valeurTitrePopupValidation.setText(titre);
        valeurQuestionPopup.setText(s);
        globalUtilisateur = userGlobal;
        globalTitre = titre;
    }


}
