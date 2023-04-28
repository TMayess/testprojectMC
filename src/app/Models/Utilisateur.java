package app.Models;

import app.ConnectionDataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Utilisateur {
    private String identifiant;
    private String nom;
    private String prenom;
    private String role;
    private String motdepasse;

    public Utilisateur(String identifiant, String nom, String prenom, String motdepasse) {
        this.identifiant = identifiant;
        this.nom = nom;
        this.prenom = prenom;
        this.motdepasse = motdepasse;
    }

    public Utilisateur(String identifiant, String nom, String prenom, String role, String motdepasse) {
        this.identifiant = identifiant;
        this.nom = nom;
        this.prenom = prenom;
        this.role = role;
        this.motdepasse = motdepasse;
    }

    public Utilisateur(String identifiant, String nom) {
        this.identifiant = identifiant;
        this.nom = nom;
    }

    public String getRole() {
        return role;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMotdepasse() {
        return motdepasse;
    }

    public void setMotdepasse(String motdepasse) {
        this.motdepasse = motdepasse;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void dropUtilisateur() {
        String query = "DELETE FROM utilisateur WHERE idUtilisateur = ?";

        try {
            ConnectionDataBase connexion = new ConnectionDataBase();
            Connection conn = connexion.conn;


            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, identifiant);

            int rowsDeleted = stmt.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Utilisateur supprimé avec succès.");
            } else {
                System.out.println("Aucun utilisateur n'a été supprimé.");
            }
            stmt.close();
            conn.close();
        } catch (SQLException e) {

            System.out.println("Une erreur s'est produite lors de la tentative d'ajout d'un utilisateur :");
            e.printStackTrace();
        }

    }

    public void addUtilisateur() {
        String query = "INSERT INTO `utilisateur`(`idUtilisateur`, `nom`, `prenom`, `role`, `motdepasse`) VALUES (?,?,?,?,?)";

        try {
            ConnectionDataBase connexion = new ConnectionDataBase();
            Connection conn = connexion.conn;


            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, identifiant);
            stmt.setString(2, nom);
            stmt.setString(3, prenom);
            stmt.setString(4, role);
            stmt.setString(5, motdepasse);



            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("le nouveau utilisateur a été ajouté à la table !");

            }

            stmt.close();
            conn.close();
        } catch (SQLException e) {

            System.out.println("Une erreur s'est produite lors de la tentative d'ajout d'un utilisateur :");
            e.printStackTrace();
        }
    }
}
