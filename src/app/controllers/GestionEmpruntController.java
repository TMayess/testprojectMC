package app.controllers;

import app.ConnectionDataBase;
import app.Models.Abonnee;
import app.Models.AlerteDialoguePerso;
import app.Models.Exemplaire;
import app.Models.Ouvrage;
import app.controllers.popUp.ValidationEmpruntController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class GestionEmpruntController implements Initializable {


    @FXML
    public Label identifiantValeur;
    @FXML
    public Label roleValeur;
    @FXML
    public Label statutValeur;
    @FXML
    public Label nomValeur;
    @FXML
    public Label prenomValeur;
    @FXML
    public Label valeurNbLivre;
    @FXML
    public TableColumn rayonColumn;
    @FXML
    public TableView <Exemplaire> empruntExemplaireTableView;
    @FXML
    public TableColumn<Exemplaire, String> identifiantExemplaireColumn;

    @FXML
    public TableColumn<Exemplaire, String> referenceExemplaireColumn;
    private ObservableList<Exemplaire> exemplairesList = FXCollections.observableArrayList();
    @FXML
    private TableView<Abonnee> empruntTableView;

    @FXML
    private TableColumn<Abonnee, String> identifiantColumn;
    @FXML
    private TableColumn<Abonnee, String> nomColumn;
    @FXML
    private TableColumn<Abonnee, String> prenomColumn;
    @FXML
    private TextField rechercheEdit;
    @FXML
    private TextField rechercheOuvrageEdit;
    @FXML
    private TableColumn<Abonnee, String> statutColumn;
    @FXML
    private TableColumn<Abonnee, String> roleColumn;
    private ObservableList<Abonnee> abonneeList = FXCollections.observableArrayList();
    @FXML
    private HBox hbox;

    @FXML
    private TableView<Ouvrage> empruntOuvrageTableView;

    @FXML
    private TableColumn<Ouvrage, String> identifiantOuvrageColumn;
    @FXML
    private TableColumn<Ouvrage, String> titreColumn;
    @FXML
    private TableColumn<Ouvrage, String> auteurColumn;
    @FXML
    private TableColumn<Ouvrage, String> categorieColumn;



    private ObservableList<Ouvrage> ouvrageList = FXCollections.observableArrayList();
    private List<Exemplaire> exemplaireList = FXCollections.observableArrayList();


    @FXML
    private ComboBox rechercheComboBox;
    private final String[] RECHERCHEPAR_ITEM = {"Tout", "Identifiant", "Nom", "Prenom", "Statut", "Role"};
    private final ObservableList<String> RECHERCHEPAR_LIST = FXCollections.observableArrayList(RECHERCHEPAR_ITEM);
    @FXML
    private ComboBox rechercheOuvrageComboBox;
    private final String[] RECHERCHEOUVRAGE_ITEM = {"Tout", "Identifiant", "Titre", "Auteur", "Rayon"};
    private final ObservableList<String> RECHERCHEOUVRAGE_LIST = FXCollections.observableArrayList(RECHERCHEOUVRAGE_ITEM);

    List<Ouvrage> listOuvrage = new ArrayList<>(3);
    Ouvrage globalOuvrage;
    Abonnee globalAbonne;
    Pane plusPane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rechercheComboBox.setItems(RECHERCHEPAR_LIST);
        rechercheComboBox.setValue(RECHERCHEPAR_ITEM[0]);

        rechercheOuvrageComboBox.setItems(RECHERCHEOUVRAGE_LIST);
        rechercheOuvrageComboBox.setValue(RECHERCHEOUVRAGE_ITEM[0]);


        identifiantColumn.setCellValueFactory(new PropertyValueFactory<>("identifiant"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        statutColumn.setCellValueFactory(new PropertyValueFactory<>("statut"));

        upDateTableView_Abonne();


        identifiantOuvrageColumn.setCellValueFactory(new PropertyValueFactory<>("Identifiant"));
        titreColumn.setCellValueFactory(new PropertyValueFactory<>("Titre"));
        auteurColumn.setCellValueFactory(new PropertyValueFactory<>("Auteur"));
        rayonColumn.setCellValueFactory(new PropertyValueFactory<>("Rayon"));

        upDateTableView_Ouvrage();


        identifiantExemplaireColumn.setCellValueFactory(new PropertyValueFactory<>("idOuvrage"));
        referenceExemplaireColumn.setCellValueFactory(new PropertyValueFactory<>("reference"));




        empruntTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Abonnee abonnee = empruntTableView.getSelectionModel().getSelectedItem();
                if (abonnee != null) {
                    identifiantValeur.setText(abonnee.getIdentifiant());
                    nomValeur.setText(abonnee.getNom());
                    prenomValeur.setText(abonnee.getPrenom());
                    roleValeur.setText(abonnee.getRole());
                    statutValeur.setText(abonnee.getStatut());
                    globalAbonne = abonnee;
                }
            }
        });


        empruntOuvrageTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Ouvrage ouvrage = empruntOuvrageTableView.getSelectionModel().getSelectedItem();
                if (ouvrage != null) {
                    for (Ouvrage ouvrage2 : ouvrageList) {
                        if (ouvrage2.getIdentifiant() == ouvrage.getIdentifiant()){
                            exemplaireList = ouvrage2.getExemplaires();
                            exemplairesList.clear();
                            exemplairesList.addAll(exemplaireList);

                            empruntExemplaireTableView.setItems(exemplairesList);
                        }
                    }
                    globalOuvrage = ouvrage;
                }
            }
        });



        valeurNbLivre.setText("(Nombre de livres sélectionnés 0)");

        plusPane = createPlusPane();
        HBox.setMargin(plusPane, new Insets(0, 5, 0, 5));
        hbox.getChildren().add(plusPane);
    }

    private void upDateTableView_Ouvrage() {
        try {
            ConnectionDataBase connexion = new ConnectionDataBase();
            Connection conn = connexion.conn;
            String query = "SELECT idOuvrage, titre, auteur, rayon, ref " +
                    "FROM exemplaire " +
                    "JOIN ouvrage ON exemplaire.Ouvrage_idOuvrage = ouvrage.idOuvrage " +
                    "WHERE exemplaire.disponible = 1";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);


            Map<String, Ouvrage> ouvrageMap = new HashMap<>();

            while (rs.next()) {
                String idOuvrage = rs.getString("idOuvrage");
                String titre = rs.getString("titre");
                String auteur = rs.getString("auteur");
                String rayon = rs.getString("rayon");
                String ref = rs.getString("ref");

                Ouvrage ouvrage;
                if (ouvrageMap.containsKey(idOuvrage)) {
                    ouvrage = ouvrageMap.get(idOuvrage);
                } else {
                    ouvrage = new Ouvrage(idOuvrage, titre, auteur, rayon, new ArrayList<Exemplaire>());
                    ouvrageMap.put(idOuvrage, ouvrage);
                    ouvrageList.add(ouvrage);
                }

                Exemplaire exemplaire = new Exemplaire(ref, idOuvrage);
                ouvrage.getExemplaires().add(exemplaire);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        empruntOuvrageTableView.setItems(ouvrageList);
    }

    private void upDateTableView_Abonne() {
        try {
            ConnectionDataBase connexion = new ConnectionDataBase();
            Connection conn = connexion.conn;
            String query = "SELECT * FROM Abonne WHERE idAbonne NOT IN (SELECT DISTINCT idAbonne FROM emprunt WHERE rendu = 0) AND abonne.idAbonne NOT IN (SELECT Abonne_idAbonne FROM sanction)";

            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Abonnee abonne = new Abonnee(rs.getString("idAbonne"),rs.getString("nom"), rs.getString("prenom"), rs.getDate("dateNaiss").toLocalDate(),rs.getString("role"),rs.getString("statut"));
                abonneeList.add(abonne);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        empruntTableView.setItems(abonneeList);
    }


    public void onClickRecherche(ActionEvent actionEvent) {

        String selectedItem = (String) rechercheComboBox.getSelectionModel().getSelectedItem();
        String searchText = rechercheEdit.getText();

        ObservableList<Abonnee> filteredList = FXCollections.observableArrayList();

        if (selectedItem.equals("Tout")) {
            filteredList.addAll(abonneeList.filtered(abonnee ->
                    abonnee.getIdentifiant().toLowerCase().contains(searchText.toLowerCase()) ||
                            abonnee.getNom().toLowerCase().contains(searchText.toLowerCase()) ||
                            abonnee.getPrenom().toLowerCase().contains(searchText.toLowerCase()) ||
                            abonnee.getDateNaissance().toString().toLowerCase().contains(searchText.toLowerCase()) ||
                            abonnee.getStatut().toString().toLowerCase().contains(searchText.toLowerCase()) ||
                            abonnee.getRole().toLowerCase().contains(searchText.toLowerCase())


            ));
        } else if (selectedItem.equals("Identifiant")) {
            filteredList.addAll(abonneeList.filtered(abonnee ->
                    abonnee.getIdentifiant().toLowerCase().contains(searchText.toLowerCase())
            ));
        } else if (selectedItem.equals("Nom")) {
            filteredList.addAll(abonneeList.filtered(abonnee ->
                    abonnee.getNom().toLowerCase().contains(searchText.toLowerCase())
            ));
        } else if (selectedItem.equals("Prenom")) {
            filteredList.addAll(abonneeList.filtered(abonnee ->
                    abonnee.getPrenom().toLowerCase().contains(searchText.toLowerCase())
            ));
        } else if (selectedItem.equals("Date de naissance")) {
            filteredList.addAll(abonneeList.filtered(abonnee ->
                    abonnee.getDateNaissance().toString().toLowerCase().contains(searchText.toLowerCase())
            ));
        } else if (selectedItem.equals("Role")) {
            filteredList.addAll(abonneeList.filtered(abonnee ->
                    abonnee.getRole().toLowerCase().contains(searchText.toLowerCase())
            ));
        } else if (selectedItem.equals("Statut")) {
            filteredList.addAll(abonneeList.filtered(abonnee ->
                    abonnee.getStatut().toLowerCase().contains(searchText.toLowerCase())
            ));
        }

        empruntTableView.setItems(filteredList);
    }

    public void onClickRechercheOuvrage(ActionEvent actionEvent) {

        String selectedItem = (String) rechercheOuvrageComboBox.getSelectionModel().getSelectedItem();
        String searchText = rechercheOuvrageEdit.getText();

        ObservableList<Ouvrage> filteredList = FXCollections.observableArrayList();

        if (selectedItem.equals("Tout")) {
            filteredList.addAll(ouvrageList.filtered(ouvrage ->
                    ouvrage.getIdentifiant().toLowerCase().contains(searchText.toLowerCase()) ||
                            ouvrage.getTitre().toLowerCase().contains(searchText.toLowerCase()) ||
                            ouvrage.getRayon().toLowerCase().contains(searchText.toLowerCase()) ||
                            ouvrage.getAuteur().toLowerCase().contains(searchText.toLowerCase())


            ));
        } else if (selectedItem.equals("Identifiant")) {
            filteredList.addAll(ouvrageList.filtered(ouvrage ->
                    ouvrage.getIdentifiant().toLowerCase().contains(searchText.toLowerCase())
            ));
        } else if (selectedItem.equals("Titre")) {
            filteredList.addAll(ouvrageList.filtered(ouvrage ->
                    ouvrage.getTitre().toLowerCase().contains(searchText.toLowerCase())
            ));
        } else if (selectedItem.equals("Auteur")) {
            filteredList.addAll(ouvrageList.filtered(ouvrage ->
                    ouvrage.getAuteur().toLowerCase().contains(searchText.toLowerCase())
            ));
        } else if (selectedItem.equals("Rayon")) {
            filteredList.addAll(ouvrageList.filtered(ouvrage ->
                    ouvrage.getRayon().toLowerCase().contains(searchText.toLowerCase())
            ));
        }


            empruntOuvrageTableView.setItems(filteredList);
        }



    private void addNewPane(Ouvrage ouvrage) {
        int nombrePane = hbox.getChildren().size();

        int nb = listOuvrage.indexOf(ouvrage) ;
        if (nb == -1){
            if (nombrePane >= 3){
                hbox.getChildren().remove(plusPane);
            }
            Pane newPane = createNewPane(ouvrage);
            HBox.setMargin(newPane, new Insets(0, 5, 0, 5));
            hbox.getChildren().add(hbox.getChildren().size()-1,newPane);
            listOuvrage.add(ouvrage);
            valeurNbLivre.setText("(Nombre de ouvrage sélectionnés "+ listOuvrage.size() +")");
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Vous avez deja ajouter le livre");
            alert.showAndWait();
        }

    }

    private Pane createPlusPane(){

        Pane livrePane = new Pane();
        livrePane.setPrefSize(207.0, 175.0);
        livrePane.setStyle("-fx-background-color: white;");

        Button duplicateButton = new Button();
        duplicateButton.setLayoutX(63.0);
        duplicateButton.setLayoutY(51.0);
        duplicateButton.setPrefSize(34.0, 34.0);
        duplicateButton.setStyle("-fx-background-color: white;");
        duplicateButton.setOnAction(e -> {
            if (globalOuvrage != null){

                addNewPane(globalOuvrage);

            }else{
                AlerteDialoguePerso alert = new AlerteDialoguePerso("Erreur","Sélectionnez d'abord le livre pour l'ajouter");
                alert.create();
            }
        });

        ImageView imageView = new ImageView();
        imageView.setFitHeight(67.0);
        imageView.setFitWidth(65.0);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        Image image = new Image("app/images/icon_plus.png");
        imageView.setImage(image);

        duplicateButton.setGraphic(imageView);
        livrePane.getChildren().add(duplicateButton);



        return livrePane;
    }

    private Pane createNewPane(Ouvrage ouvrage) {
        Pane pane = new Pane();
        pane.setPrefSize(210.0, 170.0);
        pane.setStyle("-fx-background-color: white;");

        Label referenceLabel = new Label("Reference");
        referenceLabel.setLayoutX(26.0);
        referenceLabel.setLayoutY(38.0);
        referenceLabel.setFont(new Font(14.0));
        referenceLabel.setId("referenceLabelId"); // ajout de l'ID pour le label de référence
        pane.getChildren().add(referenceLabel);
        referenceLabel.setText(ouvrage.getIdentifiant());

        Label titleLabel = new Label("Titre");
        titleLabel.setLayoutX(26.0);
        titleLabel.setLayoutY(65.0);
        titleLabel.setFont(new Font(14.0));
        pane.getChildren().add(titleLabel);
        titleLabel.setText(ouvrage.getTitre());

        Label auteurLabel = new Label("Auteur");
        auteurLabel.setLayoutX(26.0);
        auteurLabel.setLayoutY(92.0);
        auteurLabel.setFont(new Font(14.0));
        pane.getChildren().add(auteurLabel);
        auteurLabel.setText(ouvrage.auteur);

        Button closeButton = new Button();
        closeButton.setLayoutX(170.0);
        closeButton.setLayoutY(2.0);
        closeButton.setPrefSize(34.0, 34.0);
        closeButton.setStyle("-fx-background-color: white;");
        ImageView closeIcon = new ImageView(new Image(getClass().getResourceAsStream("../images/icon_closeElement.png")));
        closeIcon.setFitHeight(20.0);
        closeIcon.setFitWidth(20.0);
        closeButton.setGraphic(closeIcon);
        closeButton.setOnAction(e -> {



            Node parent = closeButton.getParent();
            Label labelInParent = (Label) parent.lookup("#referenceLabelId");
            String referenceValue = labelInParent.getText();

            Iterator<Ouvrage> iter = listOuvrage.iterator();
            while (iter.hasNext()) {
                Ouvrage ouv = iter.next();
                if (ouv.getIdentifiant().equals(referenceValue)) {
                    iter.remove();
                    break;
                }
            }

            if (plusPane == null || !hbox.getChildren().contains(plusPane)) {
                plusPane = createPlusPane();
                hbox.setMargin(plusPane, new Insets(0, 5, 0, 5));
                hbox.getChildren().add(plusPane);
            }
            hbox.getChildren().remove(pane);
            valeurNbLivre.setText("(Nombre de ouvrage sélectionnés "+ listOuvrage.size() +")");



        });
        pane.getChildren().add(closeButton);


        return pane;
    }
    public void onClickAjouteEmprunt(ActionEvent actionEvent) {

        FXMLLoader loader;
        Parent root;
        AlerteDialoguePerso alert = new AlerteDialoguePerso();

        if(globalAbonne == null){

            alert.setTitrePopup("Erreur");
            alert.setPhrasePopup("Vous n'avez pas sélectionné l'abonné.");
            alert.create();

        }else if(listOuvrage.size() == 0){
            alert.setTitrePopup("Erreur");
            alert.setPhrasePopup("Vous n'avez sélectionné aucun livre.");
            alert.create();

        }else {
            ouvrageList.clear();
            abonneeList.clear();
            try {
                loader = new FXMLLoader(getClass().getResource("/app/views/PopUp/popUp_validationEmprunt.fxml"));
                root = loader.load();

                ValidationEmpruntController controller = loader.getController();

                controller.setAbonnee(globalAbonne,listOuvrage);
                createWindow(root);


            } catch (IOException e) {
                e.printStackTrace();
            }
            upDateTableView_Abonne();
            upDateTableView_Ouvrage();

        }


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

