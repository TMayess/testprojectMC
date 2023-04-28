package app.controllers;

import app.ConnectionDataBase;
import app.Models.Abonnee;
import app.Models.Utilisateur;
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
import java.util.ResourceBundle;

public class GestionUtilisateur implements Initializable {
    @FXML
    public TextField identifiantEdit;
    @FXML
    public TextField nomEdit;
    @FXML
    public TextField prenomEdit;
    @FXML
    public TextField motdepasseEdit;
    @FXML
    private TableView<Utilisateur> utilisateurTableView;

    @FXML
    private TableColumn<Utilisateur, String> identifiantColumn;
    @FXML
    private TableColumn<Utilisateur, String> nomColumn;
    @FXML
    private TableColumn<Utilisateur, String> prenomColumn;

    @FXML
    private TableColumn<Utilisateur, String> roleColumn;

    private ObservableList<Utilisateur> utilisateurList = FXCollections.observableArrayList();

    @FXML
    private ComboBox rechercheComboBox;
    private final String[] RECHERCHEPAR_ITEM = {"Tout", "Identifiant", "Nom", "Prenom","Date de naissance","Statue", "Role"};
    private final ObservableList<String> RECHERCHEPAR_LIST = FXCollections.observableArrayList(RECHERCHEPAR_ITEM);

    @FXML
    private ComboBox roleComboBox;
    private final String[] ROLE_ITEM = {"Admin", "Bibliothecaire", "Gestionnaire"};
    private final ObservableList<String> ROLE_LIST = FXCollections.observableArrayList(ROLE_ITEM);

    @FXML
    private Button rechercheButton;

    @FXML
    private TextField rechercheEdit;



    private Utilisateur userGlobal;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        roleComboBox.setItems(ROLE_LIST);
        roleComboBox.setValue(ROLE_ITEM[0]);
        rechercheComboBox.setItems(RECHERCHEPAR_LIST);
        rechercheComboBox.setValue(RECHERCHEPAR_ITEM[0]);
        ConnectionDataBase connexion = new ConnectionDataBase();
        Connection conn = connexion.conn;

        identifiantColumn.setCellValueFactory(new PropertyValueFactory<>("identifiant"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        try {
            String sql = "SELECT * FROM utilisateur" ;
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {

                Utilisateur user = new Utilisateur(rs.getString("idUtilisateur"),rs.getString("nom"), rs.getString("prenom"), rs.getString("role"),null);
                utilisateurList.add(user);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        utilisateurTableView.setItems(utilisateurList);

        utilisateurTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Utilisateur user = utilisateurTableView.getSelectionModel().getSelectedItem();

                if (user != null) {
                    userGlobal = user;
                    System.out.println(user.getRole() + " ");
                }
            }
        });

    }



    public void onClickSupprimerUtilisateur(ActionEvent actionEvent) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/views/PopUp/popUp_valideModification.fxml"));
            Parent root = loader.load();
            ValideModificationController controller = loader.getController();
            controller.setUtilisateur(userGlobal,"suppression utilisateur","Voulez-vous suprimer cet utilisateur ?");

            createWindow(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onClickAjouteUtilisateur(ActionEvent actionEvent) {

        if (verifieEditNonVide()) {
            if (testExisteId()) {

                String role = (String) roleComboBox.getSelectionModel().getSelectedItem();

                Utilisateur utilisateurAjouter = new Utilisateur(identifiantEdit.getText(), nomEdit.getText(), prenomEdit.getText(), role, motdepasseEdit.getText());

                try {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/views/PopUp/popUp_valideModification.fxml"));
                    Parent root = loader.load();

                    ValideModificationController controller = loader.getController();
                    controller.setUtilisateur(utilisateurAjouter, "Ajout utilisateur", "Voulez-vous ajouter cet utilisateur ?");

                    createWindow(root);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }




    public void onClickRecherche(ActionEvent actionEvent) {
        String selectedItem = (String) rechercheComboBox.getSelectionModel().getSelectedItem();
        String searchText = rechercheEdit.getText();

        ObservableList<Abonnee> filteredList = FXCollections.observableArrayList();

    }

    public void createWindow(Parent root){

        Stage popupStage = new Stage();
        Scene scene = new Scene(root);
        popupStage.setScene(scene);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initStyle(StageStyle.UNDECORATED);

        popupStage.showAndWait();
    }
    private boolean testExisteId() {
        ConnectionDataBase connexion = new ConnectionDataBase();
        Connection conn = connexion.conn;
        try (PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM utilisateur WHERE idUtilisateur = ?")) {
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




        return nonVide;
    }



}
