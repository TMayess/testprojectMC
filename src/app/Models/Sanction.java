package app.Models;

import app.ConnectionDataBase;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class Sanction {
    private LocalDate dateDebut;
    private LocalDate dateFin;

    private String idAbonne;

    public Sanction(LocalDate dateDebut, LocalDate dateFin, String identifiant) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.idAbonne = identifiant;
    }


    public void addSanction(){
        String query = "INSERT INTO `sanction`(`datedebut`, `datefin`, `Abonne_idAbonne`) VALUES (?,?,?)";

        try {
            ConnectionDataBase connexion = new ConnectionDataBase();
            Connection conn = connexion.conn;


            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setDate(1, Date.valueOf(dateDebut));
            stmt.setDate(2, Date.valueOf(dateFin));
            stmt.setInt(3, Integer.parseInt(idAbonne));



            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("le nouveau sanction a été ajouté à la table !");
            }

            stmt.close();
            conn.close();
        } catch (SQLException e) {

            System.out.println("Une erreur s'est produite lors de la tentative d'ajout d'une sanction");
            e.printStackTrace();
        }
    }

}
