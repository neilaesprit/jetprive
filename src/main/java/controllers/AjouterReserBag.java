package controllers;

import entities.ReservationBagage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import services.ReservationBagageService;

import java.io.IOException;
import java.sql.SQLException;


public class AjouterReserBag {

    @FXML
    private AnchorPane Commentaire;

    @FXML
    private TextField DescriptionTF;

    @FXML
    private TextField TypeTF;

    @FXML
    private TextField idUserTF;

    @FXML
    private Slider poidTF;

    @FXML
    void insererreservation(ActionEvent event) {
        try {
            ReservationBagageService rbs = new ReservationBagageService();


            if (TypeTF.getText().isEmpty()) {
                showAlert("Erreur", "Veuillez saisir le type du bagage.");
                return;
            }
            if (DescriptionTF.getText().isEmpty()) {
                showAlert("Erreur", "Veuillez saisir une description.");
                return;
            }
            if (idUserTF.getText().isEmpty()) {
                showAlert("Erreur", "Veuillez saisir un identifiant client valide.");
                return;
            }

            int idClient = Integer.parseInt(idUserTF.getText().trim());
            String type = TypeTF.getText().trim();
            String description = DescriptionTF.getText().trim();
            double poids = poidTF.getValue();

            ReservationBagage rb = new ReservationBagage(idClient, poids, description, type);
            rbs.ajouter(rb);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Réservation bagage enregistrée !");
            alert.showAndWait();


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/InterfaceClient.fxml"));
            Parent root = loader.load();
            idUserTF.getScene().setRoot(root);

        } catch (NumberFormatException e) {
            showAlert("Erreur", "L'identifiant client doit être un nombre.");
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
