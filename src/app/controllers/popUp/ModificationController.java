package app.controllers.popUp;

import app.Models.Abonnee;
import app.Models.Utilisateur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModificationController {

    @FXML
    private TextField identifiantEdit;
    @FXML
    private TextField nomEdit;
    @FXML
    private TextField prenomEdit;
    @FXML
    private DatePicker dateNaissanceDatePicker;
    @FXML
    private ComboBox statutComboBox;
    private final String[] STATUT_ITEM = {"Interne","Externe"};
    private final ObservableList<String> STATUT_LIST = FXCollections.observableArrayList(STATUT_ITEM);
    @FXML
    private ComboBox roleComboBox;
    private final String[] ROLE_ITEM = {"Etudiant", "Enseignant"};
    private final ObservableList<String> ROLE_LIST = FXCollections.observableArrayList(ROLE_ITEM);

    public void onClickAjouteExemplaire(ActionEvent actionEvent) {
    }

    public void onClickAnnuler(ActionEvent actionEvent) {
        Node source = (Node)  actionEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void setAbonnee(Abonnee abonne) {

        statutComboBox.setItems(STATUT_LIST);
        roleComboBox.setItems(ROLE_LIST);
        identifiantEdit.setText(abonne.getIdentifiant());
        nomEdit.setText(abonne.getNom());
        prenomEdit.setText(abonne.getPrenom());
        dateNaissanceDatePicker.setValue(abonne.getDateNaissance());
        roleComboBox.setValue(abonne.getRole());
        statutComboBox.setValue(abonne.getStatut());

    }

    public void setAbonnee(Utilisateur userGlobal) {
    }
}
