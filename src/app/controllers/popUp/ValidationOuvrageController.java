package app.controllers.popUp;

import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.awt.*;

public class ValidationOuvrageController {
    @FXML
    private Label titreLabel;
    @FXML
    private Label auteurLabel;
    @FXML
    private Label categorieLabel;
    @FXML
    private Label rayonLabel;
    @FXML
    private Button confirmerButton;
    @FXML
    private Button annulerButton;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setTitreLabel(String titre) {
        this.titreLabel.setText(titre);
    }

    public void setAuteurLabel(String auteur) {
        this.auteurLabel.setText(auteur);
    }

    public void setCategorieLabel(String categorie) {
        this.categorieLabel.setText(categorie);
    }

    public void setRayonLabel(String rayon) {
        this.rayonLabel.setText(rayon);
    }

    @FXML
    public void onClickConfirmer() {
        System.out.println("quiiiiiiiiiiiiiiiii");
    }

    @FXML
    public void onClickAnnuler() {
        new Stage().close();
        if (stage != null) {
            stage.close();
        } else {
            System.err.println("La variable 'stage' n'a pas été initialisée !");
        }
    }
}