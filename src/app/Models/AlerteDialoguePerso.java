package app.Models;

import app.controllers.popUp.AlertPopupController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class AlerteDialoguePerso {
    public String titrePopup;
    public String phrasePopup;

    public AlerteDialoguePerso(String titrePopup, String phrasePopup) {
        this.titrePopup = titrePopup;
        this.phrasePopup = phrasePopup;
    }
    public AlerteDialoguePerso() {
        this.titrePopup = null;
        this.phrasePopup = null;
    }

    public String getTitrePopup() {
        return titrePopup;
    }

    public void setTitrePopup(String titrePopup) {
        this.titrePopup = titrePopup;
    }

    public String getPhrasePopup() {
        return phrasePopup;
    }

    public void setPhrasePopup(String phrasePopup) {
        this.phrasePopup = phrasePopup;
    }

    private static void showAlert(Alert.AlertType alertType, String titrePopup, String phrasePopup) {
        Alert alert = new Alert(alertType);
        alert.setTitle(titrePopup);
        alert.setHeaderText(null);
        alert.setContentText(phrasePopup);
        alert.show();
    }

    public void create(){


        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/views/PopUp/popUp_alert.fxml"));
            Parent root = loader.load();
            AlertPopupController controller = loader.getController();
            controller.setAbonnee(titrePopup,phrasePopup);

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
