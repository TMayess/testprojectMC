package app.controllers;

import app.ConnectionDataBase;
import app.Models.Abonnee;
import app.Models.AlerteDialoguePerso;
import app.Models.Emprunt;
import app.Models.Exemplaire;
import app.controllers.popUp.ValidationRestitutionController;
import javafx.beans.property.SimpleStringProperty;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GestionRestitutionController implements Initializable {
    @FXML
    public TableColumn<Emprunt, String> identifiantAbonneColumn;
    @FXML
    public TableColumn<Emprunt, String> nomAbonneColumn;
    @FXML
    public TableColumn<Emprunt, String> prenomAbonneColumn;
    @FXML
    public TableColumn<Emprunt, String>  ref1Column;
    @FXML
    public TableColumn<Emprunt, String>  ref2Column;
    @FXML
    public TableColumn<Emprunt, String>  ref3Column;

    @FXML
    private ComboBox rechercheAvecComboBox;
    private final String[] RECHERCHEPAR_ITEM = {"Tout", "Code", "Abonnée" ,"Titre d'ouvrage","Date d'emprunt","Date limite" };
    private final ObservableList<String> RECHERCHEPAR_LIST = FXCollections.observableArrayList(RECHERCHEPAR_ITEM);

    @FXML
    private TextField rechercheEdit;

    @FXML
    private Button rechercheButton;
    @FXML
    private TableView<Emprunt> restitutionTableView;

    @FXML
    private TableColumn<Emprunt, String> codeColumn;
    @FXML
    private TableColumn<Emprunt, String> abonneColumn;
    @FXML
    private TableColumn<Emprunt, String> heureColumn;
    @FXML
    private TableColumn<Emprunt, String> dateEmpruntColumn;
    @FXML
    private TableColumn<Emprunt, String> dateLimiteColumn;
    private ObservableList<Emprunt> empruntList = FXCollections.observableArrayList();

    public Emprunt globalemprunt = null;





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rechercheAvecComboBox.setItems(RECHERCHEPAR_LIST);
        rechercheAvecComboBox.setValue(RECHERCHEPAR_ITEM[0]);

        ConnectionDataBase connexion = new ConnectionDataBase();
        Connection conn = connexion.conn;

        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        dateEmpruntColumn.setCellValueFactory(new PropertyValueFactory<>("dateEmprunt"));
        dateLimiteColumn.setCellValueFactory(new PropertyValueFactory<>("dateLimite"));
        heureColumn.setCellValueFactory(new PropertyValueFactory<>("heure"));
        identifiantAbonneColumn.setCellValueFactory(cellData -> {
            Abonnee abonnee = cellData.getValue().get_abonnee();
            String identifiant = abonnee.getIdentifiant();
            return new SimpleStringProperty(identifiant);
        });
        nomAbonneColumn.setCellValueFactory(cellData -> {
            Abonnee abonnee = cellData.getValue().get_abonnee();
            String nom = abonnee.getNom();
            return new SimpleStringProperty(nom);
        });
        prenomAbonneColumn.setCellValueFactory(cellData -> {
            Abonnee abonnee = cellData.getValue().get_abonnee();
            String prenom = abonnee.getPrenom();
            return new SimpleStringProperty(prenom);
        });

        ref1Column.setCellValueFactory(cellData -> {
            String prenom = cellData.getValue().getExemplaires().get(0).getReference();
            return new SimpleStringProperty(prenom);
        });

        ref2Column.setCellValueFactory(cellData -> {
            String prenom = cellData.getValue().getExemplaires().get(1).getReference();
            return new SimpleStringProperty(prenom);
        });

        ref3Column.setCellValueFactory(cellData -> {
            String prenom = cellData.getValue().getExemplaires().get(2).getReference();
            return new SimpleStringProperty(prenom);
        });






        try {
            String sql = "SELECT *" +
                    "FROM emprunt " +
                    "JOIN abonne ON abonne.idAbonne = emprunt.idAbonne WHERE emprunt.rendu = 0";
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Abonnee abonne = new Abonnee(rs.getString("idAbonne"), rs.getString("nom"), rs.getString("prenom"));

                List<Exemplaire> exemplaireList = new ArrayList<>();
                exemplaireList.add(new Exemplaire(rs.getString("refExemplaire1"), null));
                exemplaireList.add(new Exemplaire(rs.getString("refExemplaire2"), null));
                exemplaireList.add(new Exemplaire(rs.getString("refExemplaire3"), null));

                Emprunt emprunt = new Emprunt(rs.getInt("code"), rs.getDate("dateemprunt").toLocalDate(), rs.getDate("daterestitution").toLocalDate(), rs.getTime("heure").toLocalTime(), abonne, exemplaireList);
                empruntList.add(emprunt);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(empruntList.size());

        restitutionTableView.setItems(empruntList);

        restitutionTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Emprunt emprunt = restitutionTableView.getSelectionModel().getSelectedItem();

                if (emprunt != null) {
                    globalemprunt = emprunt;
                }
            }
        });


    }

    public void onClickRecherche(ActionEvent actionEvent) {
        String selectedItem = (String) rechercheAvecComboBox.getSelectionModel().getSelectedItem();
        String searchText = rechercheEdit.getText();
        System.out.println("Recherche effectuée avec : " + selectedItem + " et " + searchText);

        ObservableList<Emprunt> filteredList = FXCollections.observableArrayList();
/*
        if(selectedItem.equals("Tout")) {
            filteredList.addAll(empruntList.filtered(emprunt ->
                    emprunt.getCode().toLowerCase().contains(searchText.toLowerCase()) ||
                            emprunt.getDateEmprunt().toString().contains(searchText) ||
                            emprunt.getDateLimite().toString().contains(searchText)
            ));
        } else if(selectedItem.equals("Code")) {
            filteredList.addAll(empruntList.filtered(emprunt ->
                    emprunt.getCode().toLowerCase().contains(searchText.toLowerCase())
            ));
        } else if(selectedItem.equals("Date d'emprunt")) {
            filteredList.addAll(empruntList.filtered(emprunt ->
                    emprunt.getDateEmprunt().toString().contains(searchText)
            ));
        } else if(selectedItem.equals("Date limite")) {
            filteredList.addAll(empruntList.filtered(emprunt ->
                    emprunt.getDateLimite().toString().contains(searchText)
            ));
        }

        restitutionTableView.setItems(filteredList);
*/
    }

    public void onClickRendreEmprunt(ActionEvent actionEvent) {
        if(globalemprunt != null){
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/views/popUp/popUp_validationRestitution.fxml"));
                Parent root = loader.load();

                ValidationRestitutionController controller = loader.getController();
                controller.setEmprunt(globalemprunt);

                Stage popupStage = new Stage();
                Scene scene = new Scene(root);
                popupStage.setScene(scene);
                popupStage.initModality(Modality.APPLICATION_MODAL);
                popupStage.initStyle(StageStyle.UNDECORATED);

                popupStage.showAndWait();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            AlerteDialoguePerso alert = new AlerteDialoguePerso("Erreur","Sélectionnez l'abonné qui veut rendre les ouvrages empruntés");
            alert.create();
        }
    }
}


