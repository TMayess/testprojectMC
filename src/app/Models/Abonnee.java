package app.Models;

import app.ConnectionDataBase;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class Abonnee {
    private String identifiant;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String role;
    private String statut;
    private Emprunt emprunt;



    public Abonnee(String identifiant, String nom, String prenom, LocalDate dateNaissance, String statut, String role) {
        this.identifiant = identifiant;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.statut = statut;
        this.role = role;
    }


    public Abonnee(String identifiant, String nom, String prenom, LocalDate dateNaissance, String role, String statut, Emprunt emprunt) {
        this.identifiant = identifiant;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.role = role;
        this.statut = statut;
        this.emprunt = emprunt;
    }

    public Abonnee(String identifiant, String nom, String prenom, LocalDate dateNaissance) {
        this.identifiant = identifiant;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.statut = null;
        this.role = null;
    }

    public Abonnee(String identifiant, String nom, String prenom) {
        this.identifiant = identifiant;
        this.nom = nom;
        this.prenom = prenom;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }
    public Emprunt getEmprunt() {
        return emprunt;
    }

    public void setEmprunt(Emprunt emprunt) {
        this.emprunt = emprunt;
    }


   public void addAbonne(){
       String query = "INSERT INTO `abonne`(`idAbonne`, `nom`, `prenom`, `datenaiss`, `statut`, `role`) VALUES (?,?,?,?,?,?)";

       try {
           ConnectionDataBase connexion = new ConnectionDataBase();
           Connection conn = connexion.conn;


           PreparedStatement stmt = conn.prepareStatement(query);
           stmt.setInt(1, Integer.parseInt(identifiant));
           stmt.setString(2, nom);
           stmt.setString(3, prenom);
           stmt.setDate(4, Date.valueOf(dateNaissance));
           stmt.setString(5, statut);
           stmt.setString(6, role);


           int rowsInserted = stmt.executeUpdate();
           if (rowsInserted > 0) {
               System.out.println("le nouveau abonne a été ajouté à la table !");

           }

           stmt.close();
           conn.close();
       } catch (SQLException e) {

           System.out.println("Une erreur s'est produite lors de la tentative d'ajout d'un abonne :");
           e.printStackTrace();
       }
   }

   public void dropAbonne(){
       String query = "DELETE FROM abonne WHERE idAbonne = ?";

       try {
           ConnectionDataBase connexion = new ConnectionDataBase();
           Connection conn = connexion.conn;


           PreparedStatement stmt = conn.prepareStatement(query);



           stmt.setString(1, identifiant);

           int rowsDeleted = stmt.executeUpdate();

           if (rowsDeleted > 0) {
               System.out.println("Abonné supprimé avec succès.");
           } else {
               System.out.println("Aucun abonné n'a été supprimé.");
           }
           stmt.close();
           conn.close();
       } catch (SQLException e) {

           System.out.println("Une erreur s'est produite lors de la tentative d'ajout d'un abonne :");
           e.printStackTrace();
       }

   }

    public void dropSanction() {
        String query = "DELETE FROM sanction WHERE Abonne_idAbonne  = ?";

        try {
            ConnectionDataBase connexion = new ConnectionDataBase();
            Connection conn = connexion.conn;


            PreparedStatement stmt = conn.prepareStatement(query);



            stmt.setString(1, identifiant);

            int rowsDeleted = stmt.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Sanction supprimé avec succès.");
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("l'abonne n'est pas sanctionnné");
                alert.showAndWait();
            }
            stmt.close();
            conn.close();
        } catch (SQLException e) {

            System.out.println("Une erreur s'est produite lors de la tentative d'ajout d'un abonne :");
            e.printStackTrace();
        }
    }

    public void prolongeEmprunt(LocalDate date) {
        String query = "UPDATE `emprunt` SET `daterestitution`=? WHERE ?";
        try {
            ConnectionDataBase connexion = new ConnectionDataBase();
            Connection conn = connexion.conn;


            PreparedStatement stmt = conn.prepareStatement(query);


            stmt.setDate(1, Date.valueOf(date));
            stmt.setString(2, identifiant);

            int rowsDeleted = stmt.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Emprunt modifier avec succès.");
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Emprunt n'est pas modifier");
                alert.showAndWait();
            }
            stmt.close();
            conn.close();
        } catch (SQLException e) {

            System.out.println("Une erreur s'est produite lors de la tentative d'ajout d'un abonne :");
            e.printStackTrace();
        }
    }
}
