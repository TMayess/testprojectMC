package app.controllers.popUp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AlertPopupController {

    @FXML
    public Label valeurTitrePopup;
    @FXML
    public Label valeurPhrase;

    public void onClickOk(ActionEvent actionEvent) {
        Node source = (Node)  actionEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void setAbonnee(String titre, String probleme) {
        valeurPhrase.setText(probleme);
        valeurTitrePopup.setText(titre);
    }
}
