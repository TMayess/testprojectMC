package app.Models;

import app.ConnectionDataBase;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {
    private Connection connection;

    public Login() {
        connection = new ConnectionDataBase().conn;
    }

    public boolean isDatabaseConnected() {
        return connection != null;
    }

    public Utilisateur loginUser(String identifiant, String motdepasse) {
        String query = "SELECT * FROM utilisateur WHERE idUtilisateur = ? AND motdepasse = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, identifiant);
            preparedStatement.setString(2, motdepasse);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                Utilisateur user = new Utilisateur(rs.getString("idUtilisateur"), rs.getString("nom"), rs.getString("prenom"), rs.getString("role"), rs.getString("motdepasse"));
                return user;
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de connexion");
                alert.setHeaderText(null);
                alert.setContentText("Identifiant ou mot de passe incorrect");
                alert.showAndWait();
                return null;
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de connexion");
            alert.setHeaderText("Une erreur s'est produite lors de la connexion.");
            alert.setContentText("Veuillez réessayer ultérieurement.");
            alert.showAndWait();
            return null;
        }
    }

    public void logoutUser(Utilisateur user) {

    }


}