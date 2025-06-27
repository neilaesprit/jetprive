package controllers;

import entities.airport;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.AirlineService;
import services.airportService;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Stream;

public class AirportController {

    @FXML private TableView<airport> airportTable;
    @FXML private TableColumn<airport, Integer> idColumn;
    @FXML private TableColumn<airport, String> nomColumn;
    @FXML private TableColumn<airport, String> localisationColumn;
    @FXML private TableColumn<airport, Integer> capacityColumn;
    @FXML private TableColumn<airport, Integer> airlineIdColumn;

    @FXML private TextField nomField;
    @FXML private TextField localisationField;
    @FXML private TextField capacityField;
    @FXML private ComboBox<Integer> airlineIdComboBox;

    @FXML private TextField searchField;

    private final airportService service = new airportService();
    private final AirlineService airlineService = new AirlineService();

    private ObservableList<airport> airportList;
    private FilteredList<airport> filteredList;
    private airport selectedAirport;
    private VBox vboxTF;

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        nomColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
        localisationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLocalisation()));
        capacityColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCapacity()).asObject());
        airlineIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAirlineId()).asObject());

        loadAirlineIds();
        setupSelectionListener();
        rafraichir();
    }

    private void setupSelectionListener() {
        airportTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedAirport = newSelection;
                nomField.setText(newSelection.getNom());
                localisationField.setText(newSelection.getLocalisation());
                capacityField.setText(String.valueOf(newSelection.getCapacity()));
                airlineIdComboBox.setValue(newSelection.getAirlineId());
            }
        });
    }

    private void loadAirlineIds() {
        try {
            List<Integer> ids = airlineService.getAllAirlineIds();
            airlineIdComboBox.setItems(FXCollections.observableArrayList(ids));
        } catch (SQLException e) {
            showAlert("Erreur", "Impossible de charger les IDs des compagnies aériennes: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void ajouterAirport() {
        try {
            String nom = nomField.getText();
            String localisation = localisationField.getText();
            int capacity = Integer.parseInt(capacityField.getText());
            Integer airlineId = airlineIdComboBox.getValue();

            if (airlineId == null) {
                showAlert("Erreur", "Veuillez sélectionner un Airline ID.", Alert.AlertType.ERROR);
                return;
            }

            airport a = new airport(0, nom, localisation, capacity);
            a.setAirlineId(airlineId);
            service.ajouter(a);
            rafraichir();
            clearFields();
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Capacité doit être un nombre entier.", Alert.AlertType.ERROR);
        } catch (Exception e) {
            showAlert("Erreur lors de l'ajout", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void modifierAirport() {
        if (selectedAirport == null) {
            showAlert("Erreur", "Sélectionnez un aéroport à modifier.", Alert.AlertType.WARNING);
            return;
        }

        try {
            selectedAirport.setNom(nomField.getText());
            selectedAirport.setLocalisation(localisationField.getText());
            selectedAirport.setCapacity(Integer.parseInt(capacityField.getText()));
            Integer airlineId = airlineIdComboBox.getValue();

            if (airlineId == null) {
                showAlert("Erreur", "Veuillez sélectionner un Airline ID.", Alert.AlertType.ERROR);
                return;
            }

            selectedAirport.setAirlineId(airlineId);
            service.modifier(selectedAirport);
            rafraichir();
            clearFields();
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Capacité doit être un nombre entier.", Alert.AlertType.ERROR);
        } catch (Exception e) {
            showAlert("Erreur lors de la modification", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void supprimerAirport() {
        if (selectedAirport == null) {
            showAlert("Erreur", "Sélectionnez un aéroport à supprimer.", Alert.AlertType.WARNING);
            return;
        }

        try {
            service.supprimer(selectedAirport);
            rafraichir();
            clearFields();
        } catch (SQLException e) {
            showAlert("Erreur lors de la suppression", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void rafraichir() {
        try {
            List<airport> list = service.recuperer();
            airportList = FXCollections.observableArrayList(list);
            filteredList = new FilteredList<>(airportList, p -> true);

            SortedList<airport> sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(airportTable.comparatorProperty());

            airportTable.setItems(sortedList);
        } catch (SQLException e) {
            showAlert("Erreur de chargement", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void rechercherAirport() {
        if (filteredList == null) return;

        String keyword = searchField.getText().toLowerCase();
        filteredList.setPredicate(aero -> {
            if (keyword == null || keyword.isEmpty()) return true;
            return aero.getNom().toLowerCase().contains(keyword) ||
                    aero.getLocalisation().toLowerCase().contains(keyword);
        });
    }

    private void clearFields() {
        nomField.clear();
        localisationField.clear();
        capacityField.clear();
        airlineIdComboBox.getSelectionModel().clearSelection();
        selectedAirport = null;
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void exporterPDF() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer le PDF");
        fileChooser.setInitialFileName("airports.pdf");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF files", "*.pdf"));

        File file = fileChooser.showSaveDialog(airportTable.getScene().getWindow());
        if (file == null) return;

        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("Liste des Aéroports", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            PdfPTable pdfTable = new PdfPTable(5);
            pdfTable.setWidthPercentage(100);

            Stream.of("ID", "Nom", "Localisation", "Capacité", "Airline ID")
                    .forEach(headerTitle -> {
                        PdfPCell header = new PdfPCell();
                        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        header.setPhrase(new Phrase(headerTitle));
                        pdfTable.addCell(header);
                    });

            for (airport a : airportList) {
                pdfTable.addCell(String.valueOf(a.getId()));
                pdfTable.addCell(a.getNom());
                pdfTable.addCell(a.getLocalisation());
                pdfTable.addCell(String.valueOf(a.getCapacity()));
                pdfTable.addCell(String.valueOf(a.getAirlineId()));
            }

            document.add(pdfTable);
            showAlert("Succès", "PDF exporté avec succès : " + file.getAbsolutePath(), Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            showAlert("Erreur PDF", "Impossible d'exporter le PDF : " + e.getMessage(), Alert.AlertType.ERROR);
        } finally {
            document.close();
        }
    }

    @FXML
    public void goBackToAdmin(javafx.event.ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/InterfaceAdmin.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 800, 600);
            stage.setScene(scene);
            stage.setTitle("Espace Admin");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
