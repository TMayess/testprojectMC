package app.controllers;

import app.Models.Login;
import app.Models.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {






    @FXML
    private TextField identifiantEdit;

    @FXML
    private PasswordField motdepasseEdit;

    @FXML
    private Button submitButton;

    @FXML
    public void onClickLogin(ActionEvent event) throws SQLException, IOException {

        Window owner = submitButton.getScene().getWindow();

        System.out.println(identifiantEdit.getText());
        System.out.println(motdepasseEdit.getText());

        if (identifiantEdit.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your identifiant");
            return;
        }
        if (motdepasseEdit.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter a password");
            return;
        }

        String identifiant = identifiantEdit.getText();
        String motdepasse = motdepasseEdit.getText();

        Login login = new Login();

        Utilisateur user = login.loginUser(identifiant,motdepasse);



            if (user != null){
                Parent mainRoot = null;
                if (user.getRole().equals("bibliothecaire")){

                } else if (user.getRole().equals("gestionnaire")) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("views/mainGestionnaire.fxml"));
                    mainRoot = loader.load();
                }
                Scene mainScene = new Scene(mainRoot);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(mainScene);
                stage.show();
            }


    }


    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }



}