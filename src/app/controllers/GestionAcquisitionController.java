package app.controllers;

import app.Models.Emprunt;
import app.Models.Ouvrage;
import app.controllers.popUp.ValidationOuvrageController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GestionAcquisitionController implements Initializable {



    public TextField rayonEdit;
    public TextField titreEdit;
    public TextField categorieEdit;
    public TextField auteurEdit;
    public Button ajouteButton;
    @FXML
    private ComboBox rechercheAvecComboBox;
    private final String[] RECHERCHEPAR_ITEM = {"tout", "Réference", "Titre" ,"Auteur","Catégorie","Exemplaire" };
    private final ObservableList<String> RECHERCHEPAR_LIST = FXCollections.observableArrayList(RECHERCHEPAR_ITEM);

    @FXML
    private TextField rechercheEdit;

    @FXML
    private Button rechercheButton;
    @FXML
    private TableView<Ouvrage> acquisitionTableView;

    @FXML
    private TableColumn<Ouvrage, String> referenceColumn;
    @FXML
    private TableColumn<Ouvrage, String> titreColumn;
    @FXML
    private TableColumn<Ouvrage, String> auteurColumn;
    @FXML
    private TableColumn<Ouvrage, String> categorieColumn;
    @FXML
    private TableColumn<Ouvrage, String> exemplaireColumn;
    private ObservableList<Ouvrage> ouvrageList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        rechercheAvecComboBox.setItems(RECHERCHEPAR_LIST);
        rechercheAvecComboBox.setValue(RECHERCHEPAR_ITEM[0]);

        referenceColumn.setCellValueFactory(new PropertyValueFactory<>("Reference"));
        titreColumn.setCellValueFactory(new PropertyValueFactory<>("Titre"));
        auteurColumn.setCellValueFactory(new PropertyValueFactory<>("Auteur"));
        categorieColumn.setCellValueFactory(new PropertyValueFactory<>("Categorie"));
        exemplaireColumn.setCellValueFactory(new PropertyValueFactory<>("Exemplaire"));


        ouvrageList.add(new Ouvrage("a", "Harry Potter", "moi","zeaea","jbeaze"));
        ouvrageList.add(new Ouvrage("b", "Harry Potter", "moi","zeaea","jbeaze"));
        ouvrageList.add(new Ouvrage("c", "Harry Potter", "moi","zeaea","jbeaze"));

        acquisitionTableView.setItems(ouvrageList);


    }


    public void onClickRecherche(ActionEvent actionEvent) {
        String selectedItem = (String) rechercheAvecComboBox.getSelectionModel().getSelectedItem();
        String searchText = rechercheEdit.getText();
        System.out.println("Recherche effectuée avec : " + selectedItem + " et " + searchText);

        ObservableList<Ouvrage> filteredList = FXCollections.observableArrayList();

        if(selectedItem.equals("tout")) {
            filteredList.addAll(ouvrageList.filtered(ouvrage ->
                    ouvrage.getReference().toLowerCase().contains(searchText.toLowerCase()) ||
                            ouvrage.getTitre().toLowerCase().contains(searchText.toLowerCase()) ||
                            ouvrage.getAuteur().toLowerCase().contains(searchText.toLowerCase()) ||
                            ouvrage.getCategorie().toLowerCase().contains(searchText.toLowerCase()) ||
                            ouvrage.getExemplaire().toLowerCase().contains(searchText.toLowerCase())
            ));
        } else if(selectedItem.equals("Réference")) {
            filteredList.addAll(ouvrageList.filtered(ouvrage ->
                    ouvrage.getReference().toLowerCase().contains(searchText.toLowerCase())
            ));
        } else if(selectedItem.equals("Titre")) {
            filteredList.addAll(ouvrageList.filtered(ouvrage ->
                    ouvrage.getTitre().toLowerCase().contains(searchText.toLowerCase())
            ));
        } else if(selectedItem.equals("Auteur")) {
            filteredList.addAll(ouvrageList.filtered(ouvrage ->
                    ouvrage.getAuteur().toLowerCase().contains(searchText.toLowerCase())
            ));
        } else if(selectedItem.equals("Catégorie")) {
            filteredList.addAll(ouvrageList.filtered(ouvrage ->
                    ouvrage.getCategorie().toLowerCase().contains(searchText.toLowerCase())
            ));
        } else if(selectedItem.equals("Exemplaire")) {
            filteredList.addAll(ouvrageList.filtered(ouvrage ->
                    ouvrage.getExemplaire().toLowerCase().contains(searchText.toLowerCase())
            ));
        }

        acquisitionTableView.setItems(filteredList);

    }
@FXML
    public void onClickAjouteOuvrage(ActionEvent actionEvent) {
        // Création d'un objet Ouvrage à ajouter
        Ouvrage ouvrage = new Ouvrage("Titre de l'ouvrage", "Auteur de l'ouvrage", "Catégorie de l'ouvrage", "Rayon de l'ouvrage","ddd");

        try {
            // Chargement de la vue FXML de la popup
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/views/popUp/popUp_ValidationOuvrage.fxml"));
            Parent root = loader.load();

            // Création d'une instance du contrôleur associé à la vue FXML de la popup
            ValidationOuvrageController controller = loader.getController();

            // Création d'une nouvelle fenêtre (Stage) pour la popup
            Stage popupStage = new Stage();
            Scene scene = new Scene(root);
            popupStage.setScene(scene);
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.initStyle(StageStyle.UNDECORATED);

            // Affichage de la popup de manière modale
            popupStage.showAndWait();

            // Passage des données de l'ouvrage au contrôleur de la popup
            controller.setTitreLabel(ouvrage.getTitre());
            controller.setAuteurLabel(ouvrage.getAuteur());
            controller.setCategorieLabel(ouvrage.getCategorie());
            controller.setStage(popupStage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onClickAjouteExemplaire(ActionEvent actionEvent) {
    }
}