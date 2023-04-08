package app.Models;

import app.ConnectionDataBase;
import javafx.scene.control.Alert;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Emprunt {
    private int code;
    private LocalDate dateEmprunt;
    private LocalDate dateLimite;
    private LocalTime heure;
    private Abonnee _abonnee;
    private List<Exemplaire> exemplaires;


    public Emprunt(int code, LocalDate dateEmprunt, LocalDate dateLimite, LocalTime heure, Abonnee _abonnee) {
        this.code = code;
        this.dateEmprunt = dateEmprunt;
        this.dateLimite = dateLimite;
        this.heure = heure;
        this._abonnee = _abonnee;
    }

    public Emprunt(int code, LocalDate dateEmprunt, LocalDate dateLimite, LocalTime heure, Abonnee _abonnee, List<Exemplaire> exemplaires) {
        this.code = code;
        this.dateEmprunt = dateEmprunt;
        this.dateLimite = dateLimite;
        this.heure = heure;
        this._abonnee = _abonnee;
        this.exemplaires = exemplaires;
    }
    public Emprunt(int code, LocalDate dateEmprunt, LocalDate dateLimite) {
        this.code = code;
        this.dateEmprunt = dateEmprunt;
        this.dateLimite = dateLimite;

    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public LocalDate getDateEmprunt() {
        return dateEmprunt;
    }

    public void setDateEmprunt(LocalDate dateEmprunt) {
        this.dateEmprunt = dateEmprunt;
    }

    public LocalDate getDateLimite() {
        return dateLimite;
    }

    public void setDateLimite(LocalDate dateLimite) {
        this.dateLimite = dateLimite;
    }

    public LocalTime getHeure() {
        return heure;
    }

    public void setHeure(LocalTime heure) {
        this.heure = heure;
    }

    public Abonnee get_abonnee() {
        return _abonnee;
    }

    public void set_abonnee(Abonnee _abonnee) {
        this._abonnee = _abonnee;
    }

    public void setExemplaires(List<Exemplaire> exemplaires) {
        this.exemplaires = exemplaires;
    }

    public List<Exemplaire> getExemplaires() {
        return exemplaires;
    }

    public void setAbonne(List<Exemplaire> exemplaires) {
        this.exemplaires = exemplaires;
    }

    public void addEmprunt(){
        String query = "INSERT INTO `emprunt` (`dateemprunt`, `heure`, `daterestitution`, `idAbonne`, `refExemplaire1`, `refExemplaire2`, `refExemplaire3`, `rendu`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            ConnectionDataBase connexion = new ConnectionDataBase();
            Connection conn = connexion.conn;

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setDate(1, Date.valueOf(dateEmprunt));
            stmt.setTime(2, Time.valueOf(heure));
            stmt.setDate(3, Date.valueOf(dateLimite));
            stmt.setInt(4, Integer.parseInt(_abonnee.getIdentifiant()));
            stmt.setString(5, exemplaires.get(0).getReference());
            if (exemplaires.size() > 1){
                stmt.setString(6, exemplaires.get(1).getReference());
            }else {
                stmt.setString(6, null);
            }
            if (exemplaires.size() > 2){
                stmt.setString(7, exemplaires.get(2).getReference());
            }else {
                stmt.setString(7, null);
            }
            stmt.setBoolean(8, false);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Le nouvel emprunt a été ajouté à la table !");
                alert.showAndWait();
            }

            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Une erreur s'est produite lors de la tentative d'ajout d'un emprunt :");
            e.printStackTrace();
        }
    }

    public void valideRestitution() {


        try {
            ConnectionDataBase connexion = new ConnectionDataBase();
            Connection conn = connexion.conn;
            String Query = "UPDATE `emprunt` SET `rendu`= "+true+" WHERE idAbonne = ?";
            PreparedStatement stmt = conn.prepareStatement(Query);
            stmt.setString(1, _abonnee.getIdentifiant());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("L'emprunt a été rendu !");
                alert.showAndWait();
            } else {
                System.out.println("Aucun enregistrement n'a été modifié.");
            }


            stmt.close();
            conn.close();



        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
