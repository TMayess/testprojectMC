package app.controllers;

import app.Models.Emprunt;
import app.controllers.popUp.ValidationRestitutionController;
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

public class GestionRestitutionController implements Initializable {

    @FXML
    private ComboBox rechercheAvecComboBox;
    private final String[] RECHERCHEPAR_ITEM = {"tout", "Code", "Abonnée" ,"Titre d'ouvrage","Date d'emprunt","Date limite" };
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
    private TableColumn<Emprunt, String> titreColumn;
    @FXML
    private TableColumn<Emprunt, String> dateEmpruntColumn;
    @FXML
    private TableColumn<Emprunt, String> dateLimiteColumn;
    private ObservableList<Emprunt> empruntList = FXCollections.observableArrayList();

    public Emprunt emprunt = null;





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rechercheAvecComboBox.setItems(RECHERCHEPAR_LIST);
        rechercheAvecComboBox.setValue(RECHERCHEPAR_ITEM[0]);

        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        abonneColumn.setCellValueFactory(new PropertyValueFactory<>("abonnee"));
        titreColumn.setCellValueFactory(new PropertyValueFactory<>("titreOuvrage"));
        dateEmpruntColumn.setCellValueFactory(new PropertyValueFactory<>("dateEmprunt"));
        dateLimiteColumn.setCellValueFactory(new PropertyValueFactory<>("dateLimite"));


        empruntList.add(new Emprunt("a", "mayess", "Harry Potter", LocalDate.of(2022, 1, 10), LocalDate.of(2022, 1, 25)));
        empruntList.add(new Emprunt("b", "yanis", "blacklist", LocalDate.of(2022, 1, 10), LocalDate.of(2022, 1, 25)));
        empruntList.add(new Emprunt("c", "autre", "suuiiiiii", LocalDate.of(2022, 1, 10), LocalDate.of(2022, 1, 25)));

        restitutionTableView.setItems(empruntList);

        restitutionTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Emprunt emprunt1 = restitutionTableView.getSelectionModel().getSelectedItem();

                if (emprunt1 != null) {
                    emprunt = emprunt1;
                }
            }
        });


    }

    public void onClickRecherche(ActionEvent actionEvent) {
        String selectedItem = (String) rechercheAvecComboBox.getSelectionModel().getSelectedItem();
        String searchText = rechercheEdit.getText();
        System.out.println("Recherche effectuée avec : " + selectedItem + " et " + searchText);

        ObservableList<Emprunt> filteredList = FXCollections.observableArrayList();

        if(selectedItem.equals("tout")) {
            filteredList.addAll(empruntList.filtered(emprunt ->
                    emprunt.getCode().toLowerCase().contains(searchText.toLowerCase()) ||
                            emprunt.getAbonnee().toLowerCase().contains(searchText.toLowerCase()) ||
                            emprunt.getTitreOuvrage().toLowerCase().contains(searchText.toLowerCase()) ||
                            emprunt.getDateEmprunt().toString().contains(searchText) ||
                            emprunt.getDateLimite().toString().contains(searchText)
            ));
        } else if(selectedItem.equals("Code")) {
            filteredList.addAll(empruntList.filtered(emprunt ->
                    emprunt.getCode().toLowerCase().contains(searchText.toLowerCase())
            ));
        } else if(selectedItem.equals("Abonnée")) {
            filteredList.addAll(empruntList.filtered(emprunt ->
                    emprunt.getAbonnee().toLowerCase().contains(searchText.toLowerCase())
            ));
        } else if(selectedItem.equals("Titre d'ouvrage")) {
            filteredList.addAll(empruntList.filtered(emprunt ->
                    emprunt.getTitreOuvrage().toLowerCase().contains(searchText.toLowerCase())
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

    }

    public void onClickRendreEmprunt(ActionEvent actionEvent) {
        if(emprunt != null){
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/views/popUp/popUp_validationRestitution.fxml"));
                Parent root = loader.load();

                ValidationRestitutionController controller = loader.getController();
                controller.setEmprunt(emprunt);

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
}


