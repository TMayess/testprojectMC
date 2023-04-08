package app.Models;

import app.ConnectionDataBase;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Ouvrage {
    public String identifiant;
    public String titre;
    public String auteur;
    public String rayon;
    private List<Exemplaire> exemplaires;

    public Ouvrage(String identifiant, String titre, String auteur, String rayon,  List<Exemplaire> exemplaires) {
        this.identifiant = identifiant;
        this.titre = titre;
        this.auteur = auteur;
        this.rayon = rayon;
        this.exemplaires = exemplaires;
    }

    public Ouvrage(String identifiant, String titre, String auteur, String rayon) {
        this.identifiant = identifiant;
        this.titre = titre;
        this.auteur = auteur;
        this.rayon = rayon;
    }
    public Ouvrage(String titre, String auteur, String rayon, ArrayList<Exemplaire> exemplaires) {
        this.titre = titre;
        this.auteur = auteur;
        this.rayon = rayon;
    }
    public Ouvrage( String titre, String auteur, String rayon) {

        this.titre = titre;
        this.auteur = auteur;
        this.rayon = rayon;
    }


    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }


    public String getRayon() {
        return rayon;
    }

    public void setRayon(String rayon) {
        this.rayon = rayon;
    }


    public List<Exemplaire> getExemplaires() {
        return exemplaires;
    }

    public void setExemplaires(List<Exemplaire> exemplaires) {
        this.exemplaires = exemplaires;
    }

    public void addOuvrage(){
        String query = "INSERT INTO `ouvrage`( `rayon`, `titre`, `auteur`) VALUES (? ,? ,? )";

        try {
            ConnectionDataBase connexion = new ConnectionDataBase();
            Connection conn = connexion.conn;


            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, rayon);
            stmt.setString(2, titre);
            stmt.setString(3, auteur);


            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Un nouveau ouvrage a été ajouté à la table !");
                alert.showAndWait();
            }

            stmt.close();
            conn.close();
        } catch (SQLException e) {

            System.out.println("Une erreur s'est produite lors de la tentative d'ajout d'un livre :");
            e.printStackTrace();
        }
    }
}
