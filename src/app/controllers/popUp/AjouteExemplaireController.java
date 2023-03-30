package app.controllers.popUp;

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
    public Label valeurCategorie;
    @FXML
    public Label valeurRayon;

    @FXML
    private ComboBox nbrExemplaireComboBox;
    private final String[] RECHERCHEPAR_ITEM = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"};
    private final ObservableList<String> RECHERCHEPAR_LIST = FXCollections.observableArrayList(RECHERCHEPAR_ITEM);



    public void setOuvrage(Ouvrage ouvrage) {
        valeurTitre.setText(ouvrage.titre);
        valeurAuteur.setText(ouvrage.auteur);
        valeurCategorie.setText(ouvrage.categorie);

        nbrExemplaireComboBox.setItems(RECHERCHEPAR_LIST);
        nbrExemplaireComboBox.setValue(RECHERCHEPAR_ITEM[0]);
    }



    public void onClickAjouteExemplaire(ActionEvent actionEvent) {


    }

    public void onClickAnnuler(ActionEvent actionEvent) {
        Node source = (Node)  actionEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
