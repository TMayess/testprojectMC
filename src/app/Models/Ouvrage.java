package app.Models;

public class Ouvrage {
    public String identifiant;
    public String titre;
    public String auteur;
    public String categorie;
    public String exemplaire;
    public String rayon;
    public boolean disponible;


    public Ouvrage(String identifiant, String titre, String auteur, String categorie, String exemplaire) {
        this.identifiant = identifiant;
        this.titre = titre;
        this.auteur = auteur;
        this.categorie = categorie;
        this.exemplaire = exemplaire;
    }
    public Ouvrage(String identifiant, String titre, String auteur, String categorie, String exemplaire, boolean disponible) {
        this.identifiant = identifiant;
        this.titre = titre;
        this.auteur = auteur;
        this.categorie = categorie;
        this.exemplaire = exemplaire;
        this.disponible = disponible;
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

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getExemplaire() {
        return exemplaire;
    }

    public void setExemplaire(String exemplaire) {
        this.exemplaire = exemplaire;
    }
    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
    public String getRayon() {
        return rayon;
    }

    public void setRayon(String rayon) {
        this.rayon = rayon;
    }


}
