package app.controllers;

import app.Models.Abonnee;
import app.controllers.popUp.ModificationController;
import app.controllers.popUp.ValideModificationController;
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
import java.time.LocalDate;
import java.util.ResourceBundle;

public class GestionAbonneeController implements Initializable {
    @FXML
    public TextField identifiantEdit;
    @FXML
    public DatePicker dateNaissanceEdit;
    @FXML
    public TextField nomEdit;
    @FXML
    public TextField prenomEdit;
    @FXML
    private TableView<Abonnee> abonneeTableView;

    @FXML
    private TableColumn<Abonnee, String> identifiantColumn;
    @FXML
    private TableColumn<Abonnee, String> nomColumn;
    @FXML
    private TableColumn<Abonnee, String> prenomColumn;
    @FXML
    private TableColumn<Abonnee, String> dateNaissanceColumn;
    @FXML
    private TableColumn<Abonnee, String> statutColumn;
    @FXML
    private TableColumn<Abonnee, String> roleColumn;

    private ObservableList<Abonnee> abonneeList = FXCollections.observableArrayList();

    @FXML
    private ComboBox rechercheComboBox;
    private final String[] RECHERCHEPAR_ITEM = {"Tout", "Identifiant", "Nom", "Prenom","Date de naissance","Statue", "Role"};
    private final ObservableList<String> RECHERCHEPAR_LIST = FXCollections.observableArrayList(RECHERCHEPAR_ITEM);
    @FXML
    private ComboBox statutComboBox;
    private final String[] STATUT_ITEM = {"Interne","Externe"};
    private final ObservableList<String> STATUT_LIST = FXCollections.observableArrayList(STATUT_ITEM);
    @FXML
    private ComboBox roleComboBox;
    private final String[] ROLE_ITEM = {"Etudiant", "Enseignant"};
    private final ObservableList<String> ROLE_LIST = FXCollections.observableArrayList(ROLE_ITEM);

    @FXML
    private Button rechercheButton;

    @FXML
    private TextField rechercheEdit;



    private Abonnee abonnee1;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        statutComboBox.setItems(STATUT_LIST);
        roleComboBox.setItems(ROLE_LIST);
        rechercheComboBox.setItems(RECHERCHEPAR_LIST);
        rechercheComboBox.setValue(RECHERCHEPAR_ITEM[0]);

        identifiantColumn.setCellValueFactory(new PropertyValueFactory<>("identifiant"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        dateNaissanceColumn.setCellValueFactory(new PropertyValueFactory<>("dateNaissance"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        statutColumn.setCellValueFactory(new PropertyValueFactory<>("statut"));

        abonneeList.add(new Abonnee("a", "Harryzzz Potter", "moie",LocalDate.of(2022, 1, 10),"hiu_","lknoi"));
        abonneeList.add(new Abonnee("a", "Harryzzz Potter", "moie",LocalDate.of(2022, 1, 10),"hiu_","lknoi"));
        abonneeList.add(new Abonnee("a", "Harryzzz Potter", "moie",LocalDate.of(2022, 1, 10),"hiu_","lknoi"));


        abonneeTableView.setItems(abonneeList);

        abonneeTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Abonnee abonnee = abonneeTableView.getSelectionModel().getSelectedItem();

                if (abonnee != null) {
                    abonnee1 = abonnee;
                    System.out.println(abonnee.getNom() + " ");
                }
            }
        });

    }

    public void onClickModifierAbonnee(ActionEvent actionEvent) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/views/PopUp/popUp_modifierAbonne.fxml"));
            Parent root = loader.load();

            ModificationController controller = loader.getController();
            controller.setAbonnee(abonnee1);

            createWindow(root);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onClickSupprimerAbonnee(ActionEvent actionEvent) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/views/PopUp/popUp_valideModification.fxml"));
            Parent root = loader.load();
            ValideModificationController controller = loader.getController();
            controller.setAbonnee(abonnee1,"suppression abonné","Voulez-vous suprimer cet abonné ?");

            createWindow(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onClickAjouteAbonnee(ActionEvent actionEvent) {


        String role = (String) roleComboBox.getSelectionModel().getSelectedItem();
        String statut = (String) statutComboBox.getSelectionModel().getSelectedItem();
        LocalDate dateNaissance = dateNaissanceEdit.getValue();

        Abonnee abonneAAjouter = new Abonnee(identifiantEdit.getText(), nomEdit.getText(), prenomEdit.getText(), dateNaissance, role, statut);


        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/views/PopUp/popUp_valideModification.fxml"));
            Parent root = loader.load();

            ValideModificationController controller = loader.getController();
            controller.setAbonnee(abonneAAjouter, "Ajout abonné", "Voulez-vous ajouter cet abonné ?");

            createWindow(root);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public void onClickRecherche(ActionEvent actionEvent) {
        String selectedItem = (String) rechercheComboBox.getSelectionModel().getSelectedItem();
        String searchText = rechercheEdit.getText();

        ObservableList<Abonnee> filteredList = FXCollections.observableArrayList();

        if(selectedItem.equals("Tout")) {
            filteredList.addAll(abonneeList.filtered(abonnee ->
                    abonnee.getIdentifiant().toLowerCase().contains(searchText.toLowerCase()) ||
                            abonnee.getNom().toLowerCase().contains(searchText.toLowerCase()) ||
                            abonnee.getPrenom().toLowerCase().contains(searchText.toLowerCase()) ||
                            abonnee.getDateNaissance().toString().toLowerCase().contains(searchText.toLowerCase()) ||
                            abonnee.getStatut().toString().toLowerCase().contains(searchText.toLowerCase()) ||
                            abonnee.getRole().toLowerCase().contains(searchText.toLowerCase())


            ));
        } else if(selectedItem.equals("Identifiant")) {
            filteredList.addAll(abonneeList.filtered(abonnee ->
                    abonnee.getIdentifiant().toLowerCase().contains(searchText.toLowerCase())
            ));
        } else if(selectedItem.equals("Nom")) {
            filteredList.addAll(abonneeList.filtered(abonnee ->
                    abonnee.getNom().toLowerCase().contains(searchText.toLowerCase())
            ));
        } else if(selectedItem.equals("Prenom")) {
            filteredList.addAll(abonneeList.filtered(abonnee ->
                    abonnee.getPrenom().toLowerCase().contains(searchText.toLowerCase())
            ));
        } else if(selectedItem.equals("Date de naissance")) {
            filteredList.addAll(abonneeList.filtered(abonnee ->
                    abonnee.getDateNaissance().toString().toLowerCase().contains(searchText.toLowerCase())
            ));
        } else if(selectedItem.equals("Role")) {
            filteredList.addAll(abonneeList.filtered(abonnee ->
                    abonnee.getRole().toLowerCase().contains(searchText.toLowerCase())
            ));
        } else if(selectedItem.equals("Statut")) {
            filteredList.addAll(abonneeList.filtered(abonnee ->
                    abonnee.getStatut().toLowerCase().contains(searchText.toLowerCase())
            ));
        }

        abonneeTableView.setItems(filteredList);
    }

    public void createWindow(Parent root){

        Stage popupStage = new Stage();
        Scene scene = new Scene(root);
        popupStage.setScene(scene);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initStyle(StageStyle.UNDECORATED);

        popupStage.showAndWait();
    }



}
