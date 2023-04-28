package app.controllers;

import app.ConnectionDataBase;
import app.Models.Abonnee;
import app.Models.Emprunt;
import app.Models.Exemplaire;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.beans.property.SimpleStringProperty;
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
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GestionRapportController implements Initializable {

    @FXML
    public TableColumn<Emprunt, String> identifiantAbonneColumn;
    @FXML
    public TableColumn<Emprunt, String> nomAbonneColumn;
    @FXML
    public TableColumn<Emprunt, String> prenomAbonneColumn;
    @FXML
    public TableColumn<Emprunt, String>  ref1Column;
    @FXML
    public TableColumn<Emprunt, String>  ref2Column;
    @FXML
    public TableColumn<Emprunt, String>  ref3Column;



    @FXML
    private TableView<Emprunt> rapportTableView;

    @FXML
    private TableColumn<Emprunt, String> codeColumn;
    @FXML
    private TableColumn<Emprunt, String> abonneColumn;
    @FXML
    private TableColumn<Emprunt, String> heureColumn;
    @FXML
    private TableColumn<Emprunt, String> dateEmpruntColumn;
    @FXML
    private TableColumn<Emprunt, String> dateLimiteColumn;
    private ObservableList<Emprunt> empruntList = FXCollections.observableArrayList();

    @FXML
    private DatePicker empruntJour_datePick;

    public String dateDuJour = LocalDate.now().toString();







    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        empruntJour_datePick.setValue(LocalDate.parse(dateDuJour));

        ConnectionDataBase connexion = new ConnectionDataBase();
        Connection conn = connexion.conn;

        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        dateEmpruntColumn.setCellValueFactory(new PropertyValueFactory<>("dateEmprunt"));
        dateLimiteColumn.setCellValueFactory(new PropertyValueFactory<>("dateLimite"));
        heureColumn.setCellValueFactory(new PropertyValueFactory<>("heure"));
        identifiantAbonneColumn.setCellValueFactory(cellData -> {
            Abonnee abonnee = cellData.getValue().get_abonnee();
            String identifiant = abonnee.getIdentifiant();
            return new SimpleStringProperty(identifiant);
        });
        nomAbonneColumn.setCellValueFactory(cellData -> {
            Abonnee abonnee = cellData.getValue().get_abonnee();
            String nom = abonnee.getNom();
            return new SimpleStringProperty(nom);
        });
        prenomAbonneColumn.setCellValueFactory(cellData -> {
            Abonnee abonnee = cellData.getValue().get_abonnee();
            String prenom = abonnee.getPrenom();
            return new SimpleStringProperty(prenom);
        });

        ref1Column.setCellValueFactory(cellData -> {
            String prenom = cellData.getValue().getExemplaires().get(0).getReference();
            return new SimpleStringProperty(prenom);
        });

        ref2Column.setCellValueFactory(cellData -> {
            String prenom = cellData.getValue().getExemplaires().get(1).getReference();
            return new SimpleStringProperty(prenom);
        });

        ref3Column.setCellValueFactory(cellData -> {
            String prenom = cellData.getValue().getExemplaires().get(2).getReference();
            return new SimpleStringProperty(prenom);
        });






        try {
            String sql = "SELECT *" +
                    "FROM emprunt " +
                    "JOIN abonne ON abonne.idAbonne = emprunt.idAbonne ";
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Abonnee abonne = new Abonnee(rs.getString("idAbonne"), rs.getString("nom"), rs.getString("prenom"));

                List<Exemplaire> exemplaireList = new ArrayList<>();
                exemplaireList.add(new Exemplaire(rs.getString("refExemplaire1"), null));
                exemplaireList.add(new Exemplaire(rs.getString("refExemplaire2"), null));
                exemplaireList.add(new Exemplaire(rs.getString("refExemplaire3"), null));

                Emprunt emprunt = new Emprunt(rs.getInt("code"), rs.getDate("dateemprunt").toLocalDate(), rs.getDate("daterestitution").toLocalDate(), rs.getTime("heure").toLocalTime(), abonne, exemplaireList);
                empruntList.add(emprunt);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(empruntList.size());

        rapportTableView.setItems(empruntList);

    }

    public void onClickImprimeTout(ActionEvent actionEvent) {

        try {
            ConnectionDataBase connexion = new ConnectionDataBase();
            Connection con = connexion.conn;


            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT emprunt.code, emprunt.dateemprunt, emprunt.heure, emprunt.daterestitution " +
                                                "FROM emprunt " +
                                                "JOIN abonne ON abonne.idAbonne = emprunt.idAbonne");

            // Création d'un nouveau document PDF
            Document document = new Document(PageSize.A4);

            // Afficher une boîte de dialogue pour enregistrer le fichier PDF généré
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Enregistrer le rapport");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf"));
            Stage stage = (Stage) rapportTableView.getScene().getWindow();
            File file = fileChooser.showSaveDialog(stage);
            if (file == null) {
                return; // Sortie si l'utilisateur annule la boîte de dialogue
            }

            // Écriture du document PDF sur le disque
            PdfWriter.getInstance(document, new FileOutputStream(file));

            // Ouverture du document et ajout d'un titre
            document.open();
            Font font = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD);
            Paragraph titre = new Paragraph("Rapport de l'ensemble des emprunts", font);
            titre.setIndentationLeft(20);
            titre.setSpacingAfter(40);
            document.add(titre);



            // Création du tableau
            PdfPTable table = new PdfPTable(4);
            table.setWidths(new float[]{1, 1, 1, 1});
            Font fontTable = new Font(Font.FontFamily.HELVETICA, 9);

            // Ajout des en-têtes de colonnes
            table.addCell(new PdfPCell(new Paragraph("Code",fontTable)));
            table.addCell(new PdfPCell(new Paragraph("Date d'emprunt",fontTable)));
            table.addCell(new PdfPCell(new Paragraph("Heure d'emprunt",fontTable)));
            table.addCell(new PdfPCell(new Paragraph("Date limite",fontTable)));

            // Ajout des données de la requête dans le tableau
            while (rs.next()) {
                table.addCell(new PdfPCell(new Paragraph(rs.getString("code"),fontTable)));
                table.addCell(new PdfPCell(new Paragraph(rs.getString("dateEmprunt"),fontTable)));
                table.addCell(new PdfPCell(new Paragraph(rs.getString("heure"),fontTable)));
                table.addCell(new PdfPCell(new Paragraph(rs.getString("daterestitution"),fontTable)));
            }

            // Ajout du tableau au document
            document.add(table);

            // Fermeture du document
            document.close();

            System.out.println("PDF généré avec succès !");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }







    public void onClickImprimeJour(ActionEvent actionEvent) {
        try {
            ConnectionDataBase connexion = new ConnectionDataBase();
            Connection con = connexion.conn;


            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * " +
                    "FROM emprunt JOIN abonne ON abonne.idAbonne = emprunt.idAbonne " +
                    "WHERE emprunt.dateemprunt = '" + dateDuJour + "'");

            // Création d'un nouveau document PDF
            Document document = new Document(PageSize.A4);

            // Afficher une boîte de dialogue pour enregistrer le fichier PDF généré
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Enregistrer le rapport");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf"));
            Stage stage = (Stage) rapportTableView.getScene().getWindow();
            File file = fileChooser.showSaveDialog(stage);
            if (file == null) {
                return; // Sortie si l'utilisateur annule la boîte de dialogue
            }

            // Écriture du document PDF sur le disque
            PdfWriter.getInstance(document, new FileOutputStream(file));

            // Ouverture du document et ajout d'un titre
            document.open();
            Font font = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD);
            Paragraph titre = new Paragraph("Rapport détaillé des emprunts", font);
            titre.setIndentationLeft(20);
            Paragraph date = new Paragraph("Date : "+dateDuJour,font);
            date.setIndentationLeft(20);
            date.setSpacingAfter(40);

            document.add(titre);
            document.add(date);



            // Création du tableau
            PdfPTable table = new PdfPTable(6);
            table.setWidths(new float[]{1, 1, 1, 1, 2, 2});
            Font fontTable = new Font(Font.FontFamily.HELVETICA, 9);

            // Ajout des en-têtes de colonnes
            table.addCell(new PdfPCell(new Paragraph("Code",fontTable)));
            table.addCell(new PdfPCell(new Paragraph("Date d'emprunt",fontTable)));
            table.addCell(new PdfPCell(new Paragraph("Heure d'emprunt",fontTable)));
            table.addCell(new PdfPCell(new Paragraph("Date limite",fontTable)));
            table.addCell(new PdfPCell(new Paragraph("Abonné(e)",fontTable)));
            table.addCell(new PdfPCell(new Paragraph("Exemplaire",fontTable)));

            // Ajout des données de la requête dans le tableau
            while (rs.next()) {
                table.addCell(new PdfPCell(new Paragraph(rs.getString("code"),fontTable)));
                table.addCell(new PdfPCell(new Paragraph(rs.getString("dateEmprunt"),fontTable)));
                table.addCell(new PdfPCell(new Paragraph(rs.getString("heure"),fontTable)));
                table.addCell(new PdfPCell(new Paragraph(rs.getString("daterestitution"),fontTable)));
                table.addCell(new PdfPCell(new Paragraph("Identifiant : "+rs.getString("idAbonne") +"\nNom : "+rs.getString("nom") + "\nPrenom : " + rs.getString("prenom"),fontTable)));
                table.addCell(new PdfPCell(new Paragraph("Ref1 : "+rs.getString("refExemplaire1") + "\nRef2 : " + rs.getString("refExemplaire2") + "\nRef3 : " + rs.getString("refExemplaire3"),fontTable)));
            }

            // Ajout du tableau au document
            document.add(table);

            // Fermeture du document
            document.close();

            System.out.println("PDF généré avec succès !");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
      Document document = new Document(PageSize.A4, 50, 50, 50, 50);

        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Enregistrer le rapport");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf"));
            Stage stage = (Stage) rapportTableView.getScene().getWindow();
            File file = fileChooser.showSaveDialog(stage);

            if (file != null) {
                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));

                document.open();

                document.addTitle("Table");

                PdfPTable pdfTable = new PdfPTable(rapportTableView.getColumns().size());

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

                document.add(pdfTable);

                document.close();

                System.out.println("Table imprimée avec succès !");
            }
        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        }
     */
/*
 public void onClickImprimeJour(ActionEvent actionEvent) {

        Document document = new Document(PageSize.A4, 50, 50, 50, 50);

        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Enregistrer le rapport");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf"));
            Stage stage = (Stage) rapportTableView.getScene().getWindow();
            File file = fileChooser.showSaveDialog(stage);

            if (file != null) {
                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));

                document.open();

                document.addTitle("Table");

                PdfPTable pdfTable = new PdfPTable(rapportTableView.getColumns().size());

                for (TableColumn<Emprunt, ?> column : rapportTableView.getColumns()) {
                    PdfPCell cell = new PdfPCell();
                    cell.setPhrase(new Phrase(column.getText()));
                    pdfTable.addCell(cell);
                }

                // Ajouter les données de la TableView
                ObservableList<Emprunt> items = rapportTableView.getItems();
                for (Emprunt item : items) {
                    for (TableColumn<Emprunt, ?> column : rapportTableView.getColumns()) {
                        Object value;
                        if (column.getId().equals("abonneColumn")) {
                            value = item.get_abonnee().getIdentifiant() + " - " + item.get_abonnee().getNom() + " " + item.get_abonnee().getPrenom();
                        } else if (column.getId().equals("ref1Column") || column.getId().equals("ref2Column") || column.getId().equals("ref3Column")) {
                            List<Exemplaire> exemplaires = item.getExemplaires();
                            int index = Integer.parseInt(column.getId().substring(column.getId().length() - 1)) - 1;
                            value = (index < exemplaires.size()) ? exemplaires.get(index).getReference() : "";
                        } else {
                            value = column.getCellData(item);
                        }
                        PdfPCell cell = new PdfPCell();
                        cell.setPhrase(new Phrase(String.valueOf(value)));
                        pdfTable.addCell(cell);
                    }
                }

                document.add(pdfTable);

                document.close();

                System.out.println("Table imprimée avec succès !");
            }
        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        }
    }

 */

    public void onClickRecherche(ActionEvent actionEvent) {
        dateDuJour =  empruntJour_datePick.getValue().toString();
        LocalDate dateEmprunt = empruntJour_datePick.getValue();
        System.out.println("Date sélectionnée : " + dateEmprunt);
        String searchText = dateEmprunt.toString();

        ObservableList<Emprunt> filteredList = FXCollections.observableArrayList();
        filteredList.addAll(empruntList.filtered(emprunt ->
                emprunt.getDateEmprunt().toString().toLowerCase().contains(searchText.toLowerCase())
        ));
        rapportTableView.setItems(filteredList);

    }


}


