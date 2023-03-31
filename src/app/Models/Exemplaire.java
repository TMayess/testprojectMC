package app.Models;

public class Exemplaire {
    private String reference;
    private String rangee;

    public Exemplaire(String reference, String codeRayon) {
        this.reference = reference;
        this.rangee = codeRayon;
    }
}
