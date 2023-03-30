package app.controllers;

import app.Models.Ouvrage;
import app.controllers.popUp.AjouteExemplaireController;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;



public class GestionAcquisitionController implements Initializable {



    public TextField rayonEdit;
    public TextField titreEdit;
    public TextField categorieEdit;
    public TextField auteurEdit;
    public Button ajouteButton;
    @FXML
    private ComboBox rechercheAvecComboBox;
    private final String[] RECHERCHEPAR_ITEM = {"Tout", "Réference", "Titre" ,"Auteur","Catégorie","Exemplaire" };
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
    private Ouvrage exemplaire = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        rechercheAvecComboBox.setItems(RECHERCHEPAR_LIST);
        rechercheAvecComboBox.setValue(RECHERCHEPAR_ITEM[0]);

        referenceColumn.setCellValueFactory(new PropertyValueFactory<>("Reference"));
        titreColumn.setCellValueFactory(new PropertyValueFactory<>("Titre"));
        auteurColumn.setCellValueFactory(new PropertyValueFactory<>("Auteur"));
        categorieColumn.setCellValueFactory(new PropertyValueFactory<>("Categorie"));
        exemplaireColumn.setCellValueFactory(new PropertyValueFactory<>("Exemplaire"));


        ouvrageList.add(new Ouvrage("a", "Harry Potter", "moie","zeaea","jbeaze"));
        ouvrageList.add(new Ouvrage("b", "Harry Potter", "mozi","zeaea","jbeaze"));
        ouvrageList.add(new Ouvrage("c", "Harry Potter", "maoi","zeaea","jbeaze"));

        acquisitionTableView.setItems(ouvrageList);


        acquisitionTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Ouvrage ouvrage = acquisitionTableView.getSelectionModel().getSelectedItem();

                if (ouvrage != null) {
                    exemplaire = ouvrage;
                    System.out.println(exemplaire.auteur + " ");
                }
            }
        });


    }


    public void onClickRecherche(ActionEvent actionEvent) {
        String selectedItem = (String) rechercheAvecComboBox.getSelectionModel().getSelectedItem();
        String searchText = rechercheEdit.getText();
        System.out.println("Recherche effectuée avec : " + selectedItem + " et " + searchText);

        ObservableList<Ouvrage> filteredList = FXCollections.observableArrayList();

        if(selectedItem.equals("Tout")) {
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

        if (verifieEditNonVide()) {
            Ouvrage ouvrage = new Ouvrage(null, titreEdit.getText(), auteurEdit.getText(), rayonEdit.getText(),null);

            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/views/popUp/popUp_ValidationOuvrage.fxml"));
                Parent root = loader.load();

                ValidationOuvrageController controller = loader.getController();
                controller.setOuvrage(ouvrage);

                Stage popupStage = new Stage();
                Scene scene = new Scene(root);
                popupStage.setScene(scene);
                popupStage.initModality(Modality.APPLICATION_MODAL);
                popupStage.initStyle(StageStyle.UNDECORATED);

                popupStage.showAndWait();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void onClickAjouteExemplaire(ActionEvent actionEvent) {
        if(exemplaire != null){
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/views/popUp/popUp_ajouteExemplaire.fxml"));
                Parent root = loader.load();

                AjouteExemplaireController controller = loader.getController();
                controller.setOuvrage(exemplaire);

                Stage popupStage = new Stage();
                Scene scene = new Scene(root);
                popupStage.setScene(scene);
                popupStage.initModality(Modality.APPLICATION_MODAL);
                popupStage.initStyle(StageStyle.UNDECORATED);

                popupStage.showAndWait();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public Boolean verifieEditNonVide(){

        if (titreEdit.getText().isEmpty()){
            return false;
        }else if(auteurEdit.getText().isEmpty()){
            return false;
        }else if(categorieEdit.getText().isEmpty()){
            return false;
        }else if(rayonEdit.getText().isEmpty()){
            return false;
        }
        return true;
    }
   /* public void verifieEditNonVideStyle(){
        if (titreEdit.getText().isEmpty()){
            titreEdit.setStyle("-fx-effect: dropshadow(three-pass-box, red, 10, 0, 0, 0);");
        }
        if(auteurEdit.getText().isEmpty()) {
            titreEdit.setStyle("-fx-effect: dropshadow(three-pass-box, red, 10, 0, 0, 0);");
        }
        if(categorieEdit.getText().isEmpty()){
            titreEdit.setStyle("-fx-effect: dropshadow(three-pass-box, red, 10, 0, 0, 0);");

        }
        if(rayonEdit.getText().isEmpty()){
            titreEdit.setStyle("-fx-effect: dropshadow(three-pass-box, red, 10, 0, 0, 0);");

        }else {
        }

    }*/
}