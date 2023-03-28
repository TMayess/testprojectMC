package app.controllers;

import app.Models.Emprunt;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class GestionRapportController implements Initializable {


    @FXML
    private DatePicker empruntJour_datePick;



    @FXML
    private TableView<Emprunt> rapportTableView;

    @FXML
    private TableColumn<Emprunt, String> codeColumn;
    @FXML
    private TableColumn<Emprunt, String> abonneColumn;
    @FXML
    private TableColumn<Emprunt, String> titreColumn;
    @FXML
    private TableColumn<Emprunt, String> dateEmpruntColumn;
    @FXML
    private TableColumn<Emprunt, String> dateLimiteColumn;
    private ObservableList<Emprunt> empruntList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        empruntJour_datePick.setValue(LocalDate.now());

        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        abonneColumn.setCellValueFactory(new PropertyValueFactory<>("abonnee"));
        titreColumn.setCellValueFactory(new PropertyValueFactory<>("titreOuvrage"));
        dateEmpruntColumn.setCellValueFactory(new PropertyValueFactory<>("dateEmprunt"));
        dateLimiteColumn.setCellValueFactory(new PropertyValueFactory<>("dateLimite"));


        empruntList.add(new Emprunt("a", "mayess", "Harry Potter", LocalDate.of(2022, 1, 10), LocalDate.of(2022, 1, 25)));
        empruntList.add(new Emprunt("b", "yanis", "blacklist", LocalDate.of(2022, 1, 10), LocalDate.of(2022, 1, 25)));
        empruntList.add(new Emprunt("c", "autre", "suuiiiiii", LocalDate.of(2022, 1, 10), LocalDate.of(2022, 1, 25)));

        rapportTableView.setItems(empruntList);

    }

    public void onClickImprimeTout(ActionEvent actionEvent) {
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);

        try {
            // Afficher la fenêtre de sélection du fichier
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Enregistrer le rapport");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf"));
            Stage stage = (Stage) rapportTableView.getScene().getWindow();
            File file = fileChooser.showSaveDialog(stage);

            if (file != null) {
                // Créer le writer pour écrire dans le document
                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));

                // Ouvrir le document
                document.open();

                // Ajouter un titre
                document.addTitle("Table");

                // Créer une table avec autant de colonnes que la TableView
                PdfPTable pdfTable = new PdfPTable(rapportTableView.getColumns().size());

                // Ajouter les entêtes de colonnes
                for (TableColumn<Emprunt, ?> column : rapportTableView.getColumns()) {
                    PdfPCell cell = new PdfPCell();
                    cell.setPhrase(new Phrase(column.getText()));
                    pdfTable.addCell(cell);
                }

                // Ajouter les données de la TableView
                ObservableList<Emprunt> items = rapportTableView.getItems();
                for (Emprunt item : items) {
                    for (TableColumn<Emprunt, ?> column : rapportTableView.getColumns()) {
                        Object value = column.getCellData(item);
                        PdfPCell cell = new PdfPCell();
                        cell.setPhrase(new Phrase(String.valueOf(value)));
                        pdfTable.addCell(cell);
                    }
                }

                // Ajouter la table au document
                document.add(pdfTable);

                // Fermer le document
                document.close();

                // Afficher un message de confirmation
                System.out.println("Table imprimée avec succès !");
            }
        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        }
    }







    public void onClickImprimeJour(ActionEvent actionEvent) {
        LocalDate dateEmprunt = empruntJour_datePick.getValue();
        System.out.println("Date sélectionnée : " + dateEmprunt);
    }


}


