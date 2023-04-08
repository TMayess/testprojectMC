package app.controllers.popUp;

import app.Models.Exemplaire;
import app.Models.Ouvrage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AjouteExemplaireController {

    @FXML
    public Label valeurTitre;
    @FXML
    public Label valeurAuteur;
    @FXML
    public Label valeurRayon;

    @FXML
    private ComboBox nbrExemplaireComboBox;
    private final String[] RECHERCHEPAR_ITEM = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"};
    private final ObservableList<String> RECHERCHEPAR_LIST = FXCollections.observableArrayList(RECHERCHEPAR_ITEM);

    private Ouvrage globalOuvrage;

    public void setOuvrage(Ouvrage ouvrage) {
        valeurTitre.setText(ouvrage.titre);
        valeurAuteur.setText(ouvrage.auteur);
        valeurRayon.setText(ouvrage.rayon);
        globalOuvrage = ouvrage;

        nbrExemplaireComboBox.setItems(RECHERCHEPAR_LIST);
        nbrExemplaireComboBox.setValue(RECHERCHEPAR_ITEM[0]);
    }


    public void onClickAjouteExemplaire(ActionEvent actionEvent) {



        String selectedItem = (String) nbrExemplaireComboBox.getSelectionModel().getSelectedItem();
        int nbrExemplaire = Integer.parseInt(selectedItem);


        Exemplaire exemplaire = new Exemplaire(null,globalOuvrage.identifiant);
        String valeurTitre = globalOuvrage.getTitre().substring(0, 3);
        String valeurAuteur = globalOuvrage.getAuteur().substring(0, 3);
        String valeurRayon = globalOuvrage.getRayon().substring(0, 3);

        int i = exemplaire.nombreExemplaire();
        int i_jusqua = exemplaire.nombreExemplaire() + nbrExemplaire;
        String iString;
        while(i < i_jusqua ){

            iString = String.format("%02d", i);
            exemplaire.setReference(iString+"-"+valeurRayon+"-"+valeurTitre+"-"+valeurAuteur+"");
            exemplaire.addExemplaire();
            i++;
        }
        Node source = (Node)  actionEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();

    }

    public void onClickAnnuler(ActionEvent actionEvent) {
        Node source = (Node)  actionEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
