package app.Models;

public class Utilisateur {
    private String identifiant;
    private String nom;
    private String prenom;
    private String motdepasse;

    public Utilisateur(String identifiant, String nom, String prenom, String motdepasse) {
        this.identifiant = identifiant;
        this.nom = nom;
        this.prenom = prenom;
        this.motdepasse = motdepasse;
    }


}
