package app.controllers;

import app.ConnectionDataBase;
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
import java.sql.*;
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
        statutComboBox.setValue(STATUT_ITEM[0]);
        roleComboBox.setItems(ROLE_LIST);
        roleComboBox.setValue(ROLE_ITEM[0]);
        rechercheComboBox.setItems(RECHERCHEPAR_LIST);
        rechercheComboBox.setValue(RECHERCHEPAR_ITEM[0]);


        identifiantColumn.setCellValueFactory(new PropertyValueFactory<>("identifiant"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        dateNaissanceColumn.setCellValueFactory(new PropertyValueFactory<>("dateNaissance"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        statutColumn.setCellValueFactory(new PropertyValueFactory<>("statut"));

        miseAJourTableViewAbonne();

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

    private void miseAJourTableViewAbonne() {
        ConnectionDataBase connexion = new ConnectionDataBase();
        Connection conn = connexion.conn;
        try {
            String sql = "SELECT * FROM abonne" ;
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Abonnee abonnee = new Abonnee( rs.getString("idAbonne"),rs.getString("nom"), rs.getString("prenom"), rs.getDate("datenaiss").toLocalDate(), rs.getString("role"), rs.getString("statut"));
                abonneeList.add(abonnee);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        abonneeTableView.setItems(abonneeList);
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
        if (abonnee1 != null){
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/views/PopUp/popUp_valideModification.fxml"));
                Parent root = loader.load();
                ValideModificationController controller = loader.getController();
                controller.setAbonnee(abonnee1,"suppression abonné","Voulez-vous suprimer cet abonné ?");

                createWindow(root);

            } catch (IOException e) {
                e.printStackTrace();
            }
            abonneeList.clear();
            miseAJourTableViewAbonne();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("selectionner un abonné");
            alert.showAndWait();
        }
    }

    public void onClickAjouteAbonnee(ActionEvent actionEvent) {

        if (verifieEditNonVide()) {

            String identifiantAbonneString = identifiantEdit.getText().toString();
            int identifiantAbonneInt = Integer.parseInt(identifiantAbonneString);


            String role = (String) roleComboBox.getSelectionModel().getSelectedItem();
            String statut = (String) statutComboBox.getSelectionModel().getSelectedItem();
            LocalDate dateNaissance = dateNaissanceEdit.getValue();
            if (testExisteId()) {
                if (testStatutExterne(statut)) {
                    try {


                        Abonnee abonneAAjouter = new Abonnee(identifiantAbonneString, nomEdit.getText(), prenomEdit.getText(), dateNaissance, statut, role);

                        try {

                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/views/PopUp/popUp_valideModification.fxml"));
                            Parent root = loader.load();

                            ValideModificationController controller = loader.getController();
                            controller.setAbonnee(abonneAAjouter, "Ajout abonné", "Voulez-vous ajouter cet abonné ?");

                            createWindow(root);


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        abonneeList.clear();
                        miseAJourTableViewAbonne();

                    } catch (NumberFormatException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erreur");
                        alert.setHeaderText(null);
                        alert.setContentText("L'identifiant est un entier");
                        alert.showAndWait();

                    }
                }
            }
        }
    }

    private boolean testExisteId() {
        ConnectionDataBase connexion = new ConnectionDataBase();
        Connection conn = connexion.conn;
        try (PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM abonne WHERE idAbonne = ?")) {
            stmt.setString(1, identifiantEdit.getText());
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                // ID already exists, show error message and return
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Cet identifiant existe déjà dans la base de données.");
                alert.showAndWait();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    private boolean testStatutExterne(String statut) {
        if(statut == "Externe") {
            ConnectionDataBase connexion = new ConnectionDataBase();
            Connection conn = connexion.conn;
            try (
                 PreparedStatement stmtInterne = conn.prepareStatement("SELECT COUNT(*) FROM abonne WHERE abonne.statut = 'interne'");
                 PreparedStatement stmtExterne = conn.prepareStatement("SELECT COUNT(*) FROM abonne WHERE abonne.statut = 'externe'")) {
                ResultSet rs = stmtInterne.executeQuery();
                rs.next();
                int nbInterns = rs.getInt(1);

                ResultSet rs2 = stmtExterne.executeQuery();
                rs2.next();
                int nbExterns = rs2.getInt(1);

                if (nbExterns >= (int)(nbInterns * 0.1)) {
                    System.out.println(nbExterns+" "+nbInterns);
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText(null);
                    alert.setContentText("Le nombre maximal d'externes a été atteint.");
                    alert.showAndWait();
                    return false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

 /*   private boolean identifiantNotInteger() {
        int maVar = Integer.parseInt(identifiantEdit.getText());
    }*/


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

    public Boolean verifieEditNonVide(){

        String style = "-fx-border-color: red";
        String defaultStyle = "-fx-border-color: -fx-box-border;";
        boolean nonVide = true;

        if (identifiantEdit.getText().isEmpty()){
            identifiantEdit.setStyle(style);
            nonVide = false;
        } else {
            identifiantEdit.setStyle(defaultStyle);
        }

        if (nomEdit.getText().isEmpty()){
            nomEdit.setStyle(style);
            nonVide = false;
        } else {
            nomEdit.setStyle(defaultStyle);
        }

        if (prenomEdit.getText().isEmpty()){
            prenomEdit.setStyle(style);
            nonVide = false;
        } else {
            prenomEdit.setStyle(defaultStyle);
        }


        if (dateNaissanceEdit.getValue() == null || dateNaissanceEdit.getValue().toString().isEmpty()){
            dateNaissanceEdit.setStyle(style);
            nonVide = false;
        } else {
            dateNaissanceEdit.setStyle(defaultStyle);
        }

        return nonVide;
    }



}
