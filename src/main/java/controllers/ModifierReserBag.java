package controllers;

import entities.ReservationBagage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import services.ReservationBagageService;

import java.io.IOException;
import java.sql.SQLException;

public class ModifierReserBag {

    @FXML
    private TextField idTF;
    @FXML
    private TextField typeTF;
    @FXML
    private TextField descriptionTF;
    @FXML
    private Slider poidTF;
    @FXML
    private TextField idUserTF;      // idClient

    @FXML
    void modifierResBag(ActionEvent event) {
        try {
            ReservationBagageService rbs = new ReservationBagageService();

            if (idTF.getText().isEmpty()) {
                showAlert("Erreur", "Veuillez saisir un identifiant de réservation.");
                return;
            }
            int idReservation = Integer.parseInt(idTF.getText());
            if (!rbs.existe(idReservation)) { // méthode à créer dans service pour vérifier l'existence
                showAlert("Erreur", "Identifiant de réservation bagage inexistant.");
                return;
            }

            if (typeTF.getText().isEmpty()) {
                showAlert("Erreur", "Veuillez saisir un type de bagage.");
                return;
            }
            if (descriptionTF.getText().isEmpty()) {
                showAlert("Erreur", "Veuillez saisir une description.");
                return;
            }
            if (idUserTF.getText().isEmpty()) {
                showAlert("Erreur", "Veuillez saisir un identifiant utilisateur.");
                return;
            }

            int idClient = Integer.parseInt(idUserTF.getText());
            String type = typeTF.getText();
            String description = descriptionTF.getText();
            double poids = poidTF.getValue();

            ReservationBagage rb = new ReservationBagage(idReservation, idClient, poids, description, type);
            rbs.modifier(rb);

            // Chargement de la vue de retour
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
