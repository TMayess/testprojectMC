package app.Models;

import java.time.LocalDate;

public class Sanction {
    private LocalDate dateDebut;
    private LocalDate dateFin;

    public Sanction(LocalDate dateDebut, LocalDate dateFin) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }
}
