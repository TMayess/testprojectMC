package app.controllers.popUp;

import app.Models.Abonnee;
import app.Models.Emprunt;
import app.Models.Exemplaire;
import app.Models.Ouvrage;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

    public Label valideEmprunt;

    public Label titreLivre1;
    public Label titreLivre2;
    public Label titreLivre3;

    List<Exemplaire> exemplaireList =  new ArrayList<>(3);
    List<Ouvrage> ouvrageList = new ArrayList<>();

    public Emprunt imprimeEmprunt;
    public Abonnee globalAbonne;




    public void setAbonnee(Abonnee abonnee, List<Ouvrage> listOuvrage) {
        ouvrageList = listOuvrage;
        globalAbonne = abonnee;
        System.out.println(listOuvrage.get(0).getExemplaires().get(0).getReference());
        globalEmprunt = new Emprunt(1, LocalDate.now(),LocalDate.now().plusMonths(3), LocalTime.now(),abonnee);


        identifiantValeur.setText(globalEmprunt.getCode()+"");
        nomValeur.setText(abonnee.getNom());
        prenomValeur.setText(abonnee.getPrenom());

        titreLivre1.setText(listOuvrage.get(0).getTitre());




        Exemplaire exemplaire = listOuvrage.get(0).getExemplaires().get(0);
        exemplaireList.add(exemplaire);



        refLivre1Valeur.setText(exemplaire.getReference());

        if (listOuvrage.size() > 1){
            exemplaire = listOuvrage.get(1).getExemplaires().get(0);
            exemplaireList.add(exemplaire);
            titreLivre2.setText(listOuvrage.get(1).getTitre());
            refLivre2Valeur.setText(exemplaire.getReference());
        }
        if (listOuvrage.size() > 2){
            exemplaire = listOuvrage.get(2).getExemplaires().get(0);
            exemplaireList.add(exemplaire);
            titreLivre3.setText(listOuvrage.get(2).getTitre());
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
        Emprunt newEmprunt = globalEmprunt.addEmprunt();

        if (newEmprunt != null){
            valideEmprunt.setText("L'ajout de l'emprunt a été effectué avec succès.");
            imprimeEmprunt = newEmprunt;
        }else{
            valideEmprunt.setText("Un problème est survenu lors de l'ajout de l'emprunt.");
        }

    }

    public void onClickImprime(ActionEvent actionEvent) {
        if (imprimeEmprunt != null){
            System.out.println(imprimeEmprunt.getCode());
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Enregistrer le rapport");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf"));
            File file = fileChooser.showSaveDialog(null);
            if (file == null) {
                return;
            }

            Document document = new Document(PageSize.A6);
            System.out.println(imprimeEmprunt.getCode());

            try {

                PdfWriter.getInstance(document, new FileOutputStream(file));


                document.open();
                document.setMargins(50, 50, 30, 30);
                Font fontTitre = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD);
                Font fontSousTitre = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
                Font font = new Font(Font.FontFamily.HELVETICA, 10);


                Paragraph titre = new Paragraph("Reçu d'emprunt", fontTitre);
                titre.setAlignment(Element.ALIGN_CENTER);
                titre.setSpacingAfter(30);
                document.add(titre);

                Paragraph sectionEmprunt = new Paragraph("Informations sur l'emprunt",fontSousTitre);
                document.add(sectionEmprunt);

                Paragraph code = new Paragraph("Code : "+ imprimeEmprunt.getCode() ,font);
                Paragraph dateEmprunt = new Paragraph("Date d'emprunt : "+ imprimeEmprunt.getDateEmprunt() ,font);
                Paragraph dateLimite = new Paragraph("Date limite : "+ imprimeEmprunt.getDateLimite(), font);
                dateLimite.setSpacingAfter(15);
                document.add(code);
                document.add(dateEmprunt);
                document.add(dateLimite);

                Paragraph sectionAbonne = new Paragraph("Informations sur l'abonné", fontSousTitre);
                document.add(sectionAbonne);

                Paragraph abonne = new Paragraph(globalAbonne.getIdentifiant()+" "+globalAbonne.getNom()+" "+globalAbonne.getPrenom(), font);
                abonne.setSpacingAfter(15);
                document.add(abonne);


                Paragraph sectionOuvrages = new Paragraph("Ouvrages empruntés",fontSousTitre);
                document.add(sectionOuvrages);

                for (int i = 0; i < ouvrageList.size(); i++) {
                    Paragraph ouvrage = new Paragraph("Ouvrage " + (i+1) + ": " + ouvrageList.get(i).getTitre(),font);
                    Paragraph exemplaire = new Paragraph("Référence : " + exemplaireList.get(i).getReference(),font);
                    exemplaire.setSpacingAfter(10);
                    document.add(ouvrage);
                    document.add(exemplaire);
                }



                Paragraph sanction = new Paragraph("En cas de retard, vous risquez une sanction d'au minimum 3 mois.",fontSousTitre);
                sanction.setAlignment(Element.ALIGN_CENTER);
                sanction.setSpacingBefore(5);
                document.add(sanction);




            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (DocumentException e) {
                e.printStackTrace();
            } finally {
                document.close();
            }

        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Vous n'avez pas confirmé l'ajout de l'emprunt. ");
            System.out.println(imprimeEmprunt);
            alert.showAndWait();
        }
    }
}
