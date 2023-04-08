package app.controllers;

import app.ConnectionDataBase;
import app.Models.AlerteDialoguePerso;
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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;



public class GestionAcquisitionController implements Initializable {



    public TextField rayonEdit;
    public TextField titreEdit;
    public TextField categorieEdit;
    public TextField auteurEdit;
    public Button ajouteButton;
    @FXML
    private ComboBox rechercheAvecComboBox;
    private final String[] RECHERCHEPAR_ITEM = {"Tout", "Identifiant", "Titre" ,"Auteur","Catégorie","rayon" };
    private final ObservableList<String> RECHERCHEPAR_LIST = FXCollections.observableArrayList(RECHERCHEPAR_ITEM);
    @FXML
    private TextField rechercheEdit;

    @FXML
    private Button rechercheButton;
    @FXML
    private TableView<Ouvrage> acquisitionTableView;

    @FXML
    private TableColumn<Ouvrage, String> identifiantColumn;
    @FXML
    private TableColumn<Ouvrage, String> titreColumn;
    @FXML
    private TableColumn<Ouvrage, String> auteurColumn;
    @FXML
    private TableColumn<Ouvrage, String> rayonColumn;
    private ObservableList<Ouvrage> ouvrageList = FXCollections.observableArrayList();
    private Ouvrage exemplaire = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        rechercheAvecComboBox.setItems(RECHERCHEPAR_LIST);
        rechercheAvecComboBox.setValue(RECHERCHEPAR_ITEM[0]);

            ConnectionDataBase connexion = new ConnectionDataBase();
            Connection conn = connexion.conn;

            identifiantColumn.setCellValueFactory(new PropertyValueFactory<>("Identifiant"));
            titreColumn.setCellValueFactory(new PropertyValueFactory<>("Titre"));
            auteurColumn.setCellValueFactory(new PropertyValueFactory<>("Auteur"));
            rayonColumn.setCellValueFactory(new PropertyValueFactory<>("Rayon"));

        try {
            String sql = "SELECT * FROM ouvrage";
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Ouvrage ouvrage = new Ouvrage( rs.getString("idOuvrage"),rs.getString("Titre"), rs.getString("Auteur"), rs.getString("rayon"));
                ouvrageList.add(ouvrage);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
           acquisitionTableView.setItems(ouvrageList);

            acquisitionTableView.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1) {
                    Ouvrage ouvrage = acquisitionTableView.getSelectionModel().getSelectedItem();

                    if (ouvrage != null) {
                        exemplaire = ouvrage;
                    }
                }
            });
    }

    public void onClickRecherche(ActionEvent actionEvent) {
        String selectedItem = (String) rechercheAvecComboBox.getSelectionModel().getSelectedItem();
        String searchText = rechercheEdit.getText();

        ObservableList<Ouvrage> filteredList = FXCollections.observableArrayList();

        if(selectedItem.equals("Tout")) {
            filteredList.addAll(ouvrageList.filtered(ouvrage ->
                    ouvrage.getIdentifiant().toLowerCase().contains(searchText.toLowerCase()) ||
                            ouvrage.getTitre().toLowerCase().contains(searchText.toLowerCase()) ||
                            ouvrage.getAuteur().toLowerCase().contains(searchText.toLowerCase()) ||
                            ouvrage.getRayon().toLowerCase().contains(searchText.toLowerCase())
            ));
        } else if(selectedItem.equals("Identifiant")) {
            filteredList.addAll(ouvrageList.filtered(ouvrage ->
                    ouvrage.getIdentifiant().toLowerCase().contains(searchText.toLowerCase())
            ));
        } else if(selectedItem.equals("Titre")) {
            filteredList.addAll(ouvrageList.filtered(ouvrage ->
                    ouvrage.getTitre().toLowerCase().contains(searchText.toLowerCase())
            ));
        } else if(selectedItem.equals("Auteur")) {
            filteredList.addAll(ouvrageList.filtered(ouvrage ->
                    ouvrage.getAuteur().toLowerCase().contains(searchText.toLowerCase())
            ));

        } else if(selectedItem.equals("Rayon")) {
            filteredList.addAll(ouvrageList.filtered(ouvrage ->
                    ouvrage.getRayon().toLowerCase().contains(searchText.toLowerCase())
            ));

        }

        acquisitionTableView.setItems(filteredList);

    }
    @FXML
    public void onClickAjouteOuvrage(ActionEvent actionEvent) {



        if (verifieEditNonVide()) {
            String titre = titreEdit.getText().toString();
            String auteur  = auteurEdit.getText().toString();
            String rayon = rayonEdit.getText().toString();

            Ouvrage ouvrage = new Ouvrage(titre,auteur,rayon);

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
        }else {
            AlerteDialoguePerso alert = new AlerteDialoguePerso("Erreur","Vous devez remplir tous les informations de l'ouvrage.");
            alert.create();
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
        }else {
            AlerteDialoguePerso alert = new AlerteDialoguePerso("Erreur", "Selctionné les ouvrages auxquels vous souhaitez ajouter des exemplaires.");
            alert.create();
        }
    }


    public Boolean verifieEditNonVide(){

        String style = "-fx-border-color: red";
        String defaultStyle = "-fx-border-color: -fx-box-border;";
        boolean nonVide = true;

        if (titreEdit.getText().isEmpty()){
            titreEdit.setStyle(style);
            nonVide = false;
        } else {
            titreEdit.setStyle(defaultStyle);
        }

        if (auteurEdit.getText().isEmpty()){
            auteurEdit.setStyle(style);
            nonVide = false;
        } else {
            auteurEdit.setStyle(defaultStyle);
        }



        if (rayonEdit.getText().isEmpty()){
            rayonEdit.setStyle(style);
            nonVide = false;
        } else {
            rayonEdit.setStyle(defaultStyle);
        }

        return nonVide;
    }

}