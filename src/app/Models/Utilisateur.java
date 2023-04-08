package app.Models;

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

    public void setRole(String role) {
        this.role = role;
    }
}
