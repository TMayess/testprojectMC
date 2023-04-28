package app;

import app.Models.Login;
import app.Models.Utilisateur;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginMain extends Application {

    public TextField identifiantEdit;
    public PasswordField motdepasseEdit;
    public Button submitButton;

    @Override
    public void start(Stage stage) throws Exception {
        stage.initStyle(StageStyle.UNDECORATED);
        Parent root = FXMLLoader.load(getClass().getResource("views/connexion.fxml"));
        submitButton = (Button) root.lookup("#submitButton");
        identifiantEdit = (TextField) root.lookup("#identifiantEdit");
        motdepasseEdit = (PasswordField) root.lookup("#motdepasseEdit");

        submitButton.setOnAction(event -> {

            System.out.println(identifiantEdit.getText());
            System.out.println(motdepasseEdit.getText());

            if (identifiantEdit.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Form Error!",
                        "Please enter your identifiant");
                return;
            }
            if (motdepasseEdit.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Form Error!",
                        "Please enter a password");
                return;
            }

            String identifiant = identifiantEdit.getText();
            String motdepasse = motdepasseEdit.getText();

            Login login = new Login();

            Utilisateur user = login.loginUser(identifiant,motdepasse);


            if (user != null){
                stage.setUserData(user.getRole());
                Main main = new Main();
                try {
                    main.start(stage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

        });

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("bibliothéque");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }
}