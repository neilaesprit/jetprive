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
import services.ReservationClientService;

import java.io.IOException;
import java.sql.SQLException;

public class ModiferReserClient {

    @FXML
    private DatePicker DateTF;

    @FXML
    private TextField DestinationTF;

    @FXML
    private TextField idTF;

    @FXML
    private TextField idUserTF;

    @FXML
    private Slider nombreTF;

    @FXML
    void modifierResClient(ActionEvent event) {
        try {
            ReservationClientService rcs = new ReservationClientService();

            if (idTF.getText().isEmpty()) {
                showAlert("Erreur", "Veuillez saisir un identifiant de réservation.");
                return;
            }
            int idReservation = Integer.parseInt(idTF.getText());
            if (!rcs.existe(idReservation)) { // méthode à créer dans service pour vérifier existence
                showAlert("Erreur", "Identifiant de réservation client inexistant.");
                return;
            }

            if (DateTF.getValue() == null) {
                showAlert("Erreur", "Veuillez sélectionner une date.");
                return;
            }
            if (DestinationTF.getText().isEmpty()) {
                showAlert("Erreur", "Veuillez saisir une destination.");
                return;
            }
            if (idUserTF.getText().isEmpty()) {
                showAlert("Erreur", "Veuillez saisir un identifiant client.");
                return;
            }

            int idClient = Integer.parseInt(idUserTF.getText());
            String date = DateTF.getValue().toString();
            String destination = DestinationTF.getText();
            int nombre = (int) nombreTF.getValue();

            ReservationClient rc = new ReservationClient(idReservation, idClient, date, destination, nombre);
            rcs.modifier(rc);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/InterfaceClient.fxml"));
            Parent root = loader.load();
            idTF.getScene().setRoot(root);

        } catch (NumberFormatException e) {
            showAlert("Erreur", "Les identifiants doivent être des nombres valides.");
        } catch (SQLException e) {
            showAlert("Erreur SQL", e.getMessage());
        } catch (IOException e) {
            showAlert("Erreur FXML", "Impossible de charger l'interface.");
        }
    }

    private void showAlert(String title, String message) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(title);
        a.setContentText(message);
        a.showAndWait();
    }
}
