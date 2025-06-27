package controllers;

import entities.WeatherData;
import entities.airline;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.AirlineService;
import services.WeatherService;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AirlaneController {

    // ---- Gestion des compagnies aériennes ----
    @FXML private TableView<airline> airlineTable;
    @FXML private TableColumn<airline, Integer> idColumn;
    @FXML private TableColumn<airline, String> nameColumn;
    @FXML private TableColumn<airline, String> countryColumn;
    @FXML private TextField nameField;
    @FXML private TextField countryField;
    private VBox vboxTF;

    private final AirlineService service = new AirlineService();
    private ObservableList<airline> airlineList;
    private airline selectedAirline;

    // ---- Gestion météo ----
    @FXML private TextField cityField;
    @FXML private Label tempLabel, humidityLabel, descLabel;

    @FXML
    public void initialize() {
        // Setup colonnes TableView pour airlines
        idColumn.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getId()).asObject());
        nameColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));
        countryColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getCountry()));

        airlineTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedAirline = newSelection;
                nameField.setText(newSelection.getName());
                countryField.setText(newSelection.getCountry());
            }
        });

        refresh();
    }

    @FXML
    public void addAirline() {
        try {
            String name = nameField.getText();
            String country = countryField.getText();

            if (name == null || name.trim().isEmpty()) {
                showAlert("Validation Error", "Le nom de la compagnie aérienne est obligatoire.");
                return;
            }
            if (country == null || country.trim().isEmpty()) {
                showAlert("Validation Error", "Le pays de la compagnie aérienne est obligatoire.");
                return;
            }

            airline a = new airline(name, country);
            service.ajouter(a);
            refresh();
            clearFields();
        } catch (Exception e) {
            showAlert("Erreur lors de l'ajout", e.getMessage());
        }
    }

    @FXML
    public void updateAirline() {
        if (selectedAirline == null) {
            showAlert("Erreur", "Sélectionnez une compagnie aérienne à modifier.");
            return;
        }
        try {
            selectedAirline.setName(nameField.getText());
            selectedAirline.setCountry(countryField.getText());

            if (selectedAirline.getName() == null || selectedAirline.getName().trim().isEmpty()) {
                showAlert("Validation Error", "Le nom de la compagnie aérienne est obligatoire.");
                return;
            }
            if (selectedAirline.getCountry() == null || selectedAirline.getCountry().trim().isEmpty()) {
                showAlert("Validation Error", "Le pays de la compagnie aérienne est obligatoire.");
                return;
            }

            service.modifier(selectedAirline);
            refresh();
            clearFields();
        } catch (Exception e) {
            showAlert("Erreur lors de la modification", e.getMessage());
        }
    }

    @FXML
    public void deleteAirline() {
        if (selectedAirline == null) {
            showAlert("Erreur", "Sélectionnez une compagnie aérienne à supprimer.");
            return;
        }
        try {
            service.supprimer(selectedAirline);
            refresh();
            clearFields();
        } catch (SQLException e) {
            showAlert("Erreur lors de la suppression", e.getMessage());
        }
    }

    @FXML
    public void refresh() {
        try {
            List<airline> list = service.recuperer();
            airlineList = FXCollections.observableArrayList(list);
            airlineTable.setItems(airlineList);
        } catch (SQLException e) {
            showAlert("Erreur de chargement", e.getMessage());
        }
    }

    private void clearFields() {
        nameField.clear();
        countryField.clear();
        selectedAirline = null;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }


    // ---- MÉTÉO ----
    @FXML
    public void getWeather() {
        String city = cityField.getText();
        try {
            WeatherData data = WeatherService.getWeather(city);
            tempLabel.setText("Température : " + data.getTemperature() + "°C");
            humidityLabel.setText("Humidité : " + data.getHumidity() + "%");
            descLabel.setText("Description : " + data.getDescription());
        } catch (Exception e) {
            tempLabel.setText("Erreur !");
            humidityLabel.setText("");
            descLabel.setText("");
            e.printStackTrace();
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
