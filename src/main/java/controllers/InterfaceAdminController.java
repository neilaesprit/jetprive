package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import java.io.IOException;


public class InterfaceAdminController {
    @FXML
    private VBox vboxTF;

    @FXML
    void listeuser (ActionEvent event) {


        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherUtilisateur.fxml"));
            Parent root = loader.load();

            vboxTF.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
    @FXML
    void suppuser(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SupprimerUtilisateur.fxml"));
            Parent root = loader.load();

            vboxTF.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
        @FXML
        void listeavi(ActionEvent event) {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherAvis.fxml"));
                Parent root = loader.load();

                vboxTF.getScene().setRoot(root);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

        }
    @FXML
    void suppavi(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SupprimerAvis.fxml"));
            Parent root = loader.load();

            vboxTF.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    }






