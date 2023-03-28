package app.Models;

import java.time.LocalDate;
import java.util.Date;

public class Emprunt {
    public String code;
    public String abonnee;
    public String titreOuvrage;
    public LocalDate dateEmprunt;
    public LocalDate dateLimite;

    public Emprunt(String code, String abonnee, String titreOuvrage, LocalDate dateEmprunt, LocalDate dateLimite) {
        this.code = code;
        this.abonnee = abonnee;
        this.titreOuvrage = titreOuvrage;
        this.dateEmprunt = dateEmprunt;
        this.dateLimite = dateLimite;
    }

    public Emprunt() {
        this.code = "";
        this.abonnee = "";
        this.titreOuvrage = "";
        this.dateEmprunt = null;
        this.dateLimite = null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAbonnee() {
        return abonnee;
    }

    public void setAbonnee(String abonnee) {
        this.abonnee = abonnee;
    }

    public String getTitreOuvrage() {
        return titreOuvrage;
    }

    public void setTitreOuvrage(String titreOuvrage) {
        this.titreOuvrage = titreOuvrage;
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
}
