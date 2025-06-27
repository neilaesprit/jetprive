package controllers;

import entities.ReservationClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import services.ReservationClientService;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class AjouterReserClient {

    @FXML
    private AnchorPane Commentaire; // Pas nécessaire ici mais on le garde si utilisé dans FXML

    @FXML
    private DatePicker DateTF;

    @FXML
    private TextField DestinationTF;

    @FXML
    private TextField idUserTF;

    @FXML
    private Slider nombreTF;

    @FXML
    void insererreservation(ActionEvent event) {
        try {
            ReservationClientService rs = new ReservationClientService();

            // Vérifications des champs
            if (DateTF.getValue() == null) {
                showAlert("Erreur", "Veuillez choisir une date de réservation.");
                return;
            }

            if (DestinationTF.getText().isEmpty()) {
                showAlert("Erreur", "Veuillez saisir une destination.");
                return;
            }

            if (idUserTF.getText().isEmpty() ) {
                showAlert("Erreur", "Veuillez saisir un identifiant client valide.");
                return;
            }

            int idClient = Integer.parseInt(idUserTF.getText());
            String date = DateTF.getValue().toString();
            String destination = DestinationTF.getText();
            int nombre = (int) nombreTF.getValue();

            ReservationClient rc = new ReservationClient(idClient, date, destination, nombre);
            rs.ajouter(rc);

            // Redirection ou message de succès
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/InterfaceClient.fxml"));
            Parent root = loader.load();
            idUserTF.getScene().setRoot(root);

        } catch (SQLException e) {
            showAlert("Erreur SQL", e.getMessage());
        } catch (IOException e) {
            showAlert("Erreur FXML", "Impossible de charger l'interface.");
        } catch (NumberFormatException e) {
            showAlert("Erreur", "L'identifiant client doit être un nombre.");
        }
    }

    private void showAlert(String title, String message) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(title);
        a.setContentText(message);
        a.showAndWait();
    }
}
