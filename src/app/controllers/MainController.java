package app.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.swing.text.html.ImageView;
import java.net.URL;
import java.util.ResourceBundle;


public class MainController implements Initializable {
    

    @FXML
    private ImageView closeButton;






    @FXML
    public void onClickClose(MouseEvent mouseEvent) {
        //Scene scene = closeButton.getScene();

        // Récupère la fenêtre parente de la scène
        //Stage stage = (Stage) scene.getWindow();

        // Ferme la fenêtre
        //stage.close();

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {




    }
    public void onClickClose(java.awt.event.MouseEvent event) {
        // Récupère la scène actuelle
        ImageView scene = closeButton;

        // Récupère la fenêtre parente de la scène
        Stage stage = (Stage) scene.getWindow();

        // Ferme la fenêtre
        stage.close();
    }





    @FXML
    public void onClickGestionAcquisitions(ActionEvent actionEvent) {
    }
    @FXML
    public void onClickGestionRapports(ActionEvent actionEvent) {
    }

    @FXML
    public void onClickGestionEmprunts(ActionEvent actionEvent) {
    }

    @FXML
    public void onClickGestionRestitutions(ActionEvent actionEvent) {
    }
}