package app.controllers;

import app.Models.Abonnee;
import app.controllers.popUp.ValideModificationController;
import app.controllers.popUp.ProlongerDateController;
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

public class GestionSanctionController implements Initializable {

    @FXML
    private TableView<Abonnee> sanctionTableView;

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
    private Button rechercheButton;

    @FXML
    private TextField rechercheEdit;

    private Abonnee abonnee1;







    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        rechercheComboBox.setItems(RECHERCHEPAR_LIST);
        rechercheComboBox.setValue(RECHERCHEPAR_ITEM[0]);

        identifiantColumn.setCellValueFactory(new PropertyValueFactory<>("identifiant"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        dateNaissanceColumn.setCellValueFactory(new PropertyValueFactory<>("dateNaissance"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        statutColumn.setCellValueFactory(new PropertyValueFactory<>("statut"));

        abonneeList.add(new Abonnee("a", "Harryzzz Potter", "moie", LocalDate.of(2022, 1, 10),"hiu_","lknoi"));
        abonneeList.add(new Abonnee("a", "Harryzzz Potter", "moie",LocalDate.of(2022, 1, 10),"hiu_","lknoi"));
        abonneeList.add(new Abonnee("a", "Harryzzz Potter", "moie",LocalDate.of(2022, 1, 10),"hiu_","lknoi"));



        sanctionTableView.setItems(abonneeList);

        sanctionTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Abonnee abonnee = sanctionTableView.getSelectionModel().getSelectedItem();

                if (abonnee != null) {
                    abonnee1 = abonnee;
                    System.out.println(abonnee.getNom() + " ");
                }
            }
        });

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

        sanctionTableView.setItems(filteredList);
    }

    public void onClickSanctionner(ActionEvent actionEvent) {


            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/views/PopUp/popUp_valideModification.fxml"));
                Parent root = loader.load();

                ValideModificationController controller = loader.getController();
                controller.setAbonnee(abonnee1,"Sanction abonné", "Voulez-vous sanctionner cet abonné ?");

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

    public void onClickProlonger(ActionEvent actionEvent) {

            //Ouvrage ouvrage = new Ouvrage(null, titreEdit.getText(), auteurEdit.getText(), rayonEdit.getText(),null);

            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/views/popUp/popUp_prolongerDate.fxml"));
                Parent root = loader.load();

                ProlongerDateController controller = loader.getController();
                controller.setAbonnee(abonnee1);

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
