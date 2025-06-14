package controllers;

import entities.Avis;
import entities.ReservationClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import services.AvisService;
import services.ReservationClientService;

import java.io.IOException;
import java.sql.SQLException;

public class SupprimerResClient {

    @FXML
    private TextField idReserTF;

    @FXML
    private TextField idUserTF;

    @FXML
    void supprimerReservationClient(ActionEvent event) {
        try
        {


            ReservationClientService as = new ReservationClientService();
            int idAvis=Integer.parseInt(idReserTF.getText());
            int idUser=Integer.parseInt(idUserTF.getText());
            if (as.rechercherparReserv(idAvis)) {
                ReservationClient a = new ReservationClient(idAvis, idUser);
                as.supprimer(a);
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherResClient.fxml"));
                    Parent root = loader.load();
                    idReserTF.getScene().setRoot(root);
                }
                catch (IOException e)
                {
                    System.out.println("Erreur");
                }
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("identifiant inexistant");
                alert.showAndWait();

            }

        }
        catch (SQLException e)
        {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle("Erreur");
            al.setContentText(e.getMessage());
            al.showAndWait();
        }

    }

}
