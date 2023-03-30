package app.controllers;

import app.Models.Abonnee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class GestionAbonneeController implements Initializable {

    @FXML
    private TableView<Abonnee> abonneeTableView;

    @FXML
    private TableColumn<Abonnee, String> identifiantColumn;
    @FXML
    private TableColumn<Abonnee, String> nomColumn;
    @FXML
    private TableColumn<Abonnee, String> prenomColumn;

    private ObservableList<Abonnee> abonneeList = FXCollections.observableArrayList();

    @FXML
    private ComboBox rechercheComboBox;
    private final String[] RECHERCHEPAR_ITEM = {"Tout", "identifiant", "nom", "prenom"};
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
        //dateEmpruntColumn.setCellValueFactory(new PropertyValueFactory<>("dater"));
        //dateLimiteColumn.setCellValueFactory(new PropertyValueFactory<>("datel"));


        abonneeList.add(new Abonnee("a", "Harryzzz Potter", "moie"));
        abonneeList.add(new Abonnee("a", "Harry Pzzzotter", "moie"));

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
    }

    public void onClickSupprimerAbonnee(ActionEvent actionEvent) {
    }

    public void onClickAjouteAbonnee(ActionEvent actionEvent) {
    }

    public void onClickRecherche(ActionEvent actionEvent) {
        String selectedItem = (String) rechercheComboBox.getSelectionModel().getSelectedItem();
        String searchText = rechercheEdit.getText();
        System.out.println("Recherche effectuée avec : " + selectedItem + " et " + searchText);

        ObservableList<Abonnee> filteredList = FXCollections.observableArrayList();

        if(selectedItem.equals("Tout")) {
            filteredList.addAll(abonneeList.filtered(abonnee ->
                    abonnee.getIdentifiant().toLowerCase().contains(searchText.toLowerCase()) ||
                            abonnee.getNom().toLowerCase().contains(searchText.toLowerCase()) ||
                            abonnee.getPrenom().toLowerCase().contains(searchText.toLowerCase())
            ));
        } else if(selectedItem.equals("identifiant")) {
            filteredList.addAll(abonneeList.filtered(abonnee ->
                    abonnee.getIdentifiant().toLowerCase().contains(searchText.toLowerCase())
            ));
        } else if(selectedItem.equals("nom")) {
            filteredList.addAll(abonneeList.filtered(abonnee ->
                    abonnee.getNom().toLowerCase().contains(searchText.toLowerCase())
            ));
        } else if(selectedItem.equals("prenom")) {
            filteredList.addAll(abonneeList.filtered(abonnee ->
                    abonnee.getPrenom().toLowerCase().contains(searchText.toLowerCase())
            ));
        }

        abonneeTableView.setItems(filteredList);
    }
}
