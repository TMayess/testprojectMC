package app.Models;

import app.ConnectionDataBase;

import java.sql.*;

public class Exemplaire {
    private String reference;
    private String idOuvrage;

    public Exemplaire(String reference, String idOuvrage) {
        this.reference = reference;
        this.idOuvrage = idOuvrage;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }


    public String getIdOuvrage() {
        return idOuvrage;
    }

    public void setIdOuvrage(String idOuvrage) {
        this.idOuvrage = idOuvrage;
    }

    public void addExemplaire(){
        String query = "INSERT INTO `exemplaire`(`ref`, `Ouvrage_idOuvrage`) VALUES ( ?, ?)";

        try {
            ConnectionDataBase connexion = new ConnectionDataBase();
            Connection conn = connexion.conn;


            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, reference);
            stmt.setString(2, idOuvrage);


            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {

                System.out.println("les nouveau exemplaire sans ajouter a été ajouté à la table !");

            }

            stmt.close();
            conn.close();
        } catch (SQLException e) {

            System.out.println("Une erreur s'est produite lors de la tentative d'ajout d'un livre :");
            e.printStackTrace();
        }
    }
    public int nombreExemplaire(){
        int nbExemplaires = 0;
        try {
            System.out.println("1");
            ConnectionDataBase connexion = new ConnectionDataBase();
            Connection conn = connexion.conn;
            // Charger le pilote JDBC pour votre base de données

            // Créer un objet Statement pour exécuter la requête
            Statement stmt = conn.createStatement();

            // Exécuter la requête et récupérer le résultat dans un objet ResultSet
            ResultSet rs = stmt.executeQuery("SELECT COUNT(exemplaire.Ouvrage_idOuvrage) AS nb_exemplaires FROM ouvrage JOIN exemplaire ON ouvrage.idOuvrage = exemplaire.Ouvrage_idOuvrage WHERE ouvrage.idOuvrage = "+idOuvrage+"");
            System.out.println();

            if (rs.next()) {
                nbExemplaires = rs.getInt("nb_exemplaires");
                System.out.println(nbExemplaires);
            }

            rs.close();
            stmt.close();
            conn.close();



        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nbExemplaires;


    }

    public void modifDisponibleExemplaire(Boolean etat){
        try {
            ConnectionDataBase connexion = new ConnectionDataBase();
            Connection conn = connexion.conn;
            String Query = "UPDATE exemplaire SET disponible = "+etat+" WHERE ref = ?";
            PreparedStatement stmt = conn.prepareStatement(Query);
            stmt.setString(1, reference);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("La mise à jour a été effectuée avec succès.");
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
