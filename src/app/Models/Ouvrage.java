package app.Models;

public class Ouvrage {
    public String reference;
    public String titre;
    public String auteur;
    public String categorie;
    public String exemplaire;


    public Ouvrage(String reference, String titre, String auteur, String categorie, String exemplaire) {
        this.reference = reference;
        this.titre = titre;
        this.auteur = auteur;
        this.categorie = categorie;
        this.exemplaire = exemplaire;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
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

}
