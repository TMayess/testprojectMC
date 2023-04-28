package app.controllers;

import app.LoginMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainGestionnaireController implements Initializable {

    @FXML
    public ToggleButton gestionAbonneButton;
    @FXML
    public ToggleButton gestionSanctionButton;
    public ToggleButton TableauBordButton;
    @FXML
    private Button closeButton;

    @FXML
    private  Button reduceButton;

    @FXML
    private BorderPane bp;

    public Button logoutButton;






    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadPage("gestionAbonnees");

        gestionAbonneButton.setOnAction(e -> {
            gestionSanctionButton.setSelected(false);
            loadPage("gestionAbonnees");
        });
        gestionSanctionButton.setOnAction(e -> {
            gestionAbonneButton.setSelected(false);
            loadPage("gestionSanctions");
        });
        gestionAbonneButton.setSelected(true);

    }

    public void loadPage (String namePage){
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/app/views/"+ namePage +".fxml"));
        } catch (IOException e) {
            Logger.getLogger(MainBibliothecaireController.class.getName()).log(Level.SEVERE, null, e);
        }
        bp.setCenter(root);
    }



    public void onClickClose(ActionEvent actionEvent) {
        closeButton.setOnMouseClicked((MouseEvent event) -> {
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        });
    }

    public void onClickReduce(ActionEvent actionEvent) {
        reduceButton.setOnMouseClicked((MouseEvent event) -> {
            Stage stage = (Stage) reduceButton.getScene().getWindow();
            stage.setIconified(true);
        });
    }

    public void onClickLogout(ActionEvent actionEvent) {

        logoutButton.setOnMouseClicked((MouseEvent event) -> {
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            LoginMain log = new LoginMain();
            stage.close();
            try {
                log.start(new Stage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}