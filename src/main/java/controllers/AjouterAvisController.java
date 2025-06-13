package controllers;

import entities.Avis;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import services.AvisService;


import java.io.IOException;
import java.sql.SQLException;

public class AjouterAvisController {
    @FXML
    private Slider ratingTF;
    @FXML
    private TextField commentaireTF;
    @FXML
    private TextField idUserTF;


    @FXML
    void insereravis(ActionEvent event) {
        try
        {
        AvisService as = new AvisService();

        if (ratingTF.getValue() < 0 || ratingTF.getValue() > 5)
        {
            Alert a = new Alert (Alert.AlertType.ERROR);
            a.setTitle("Erreur");
            a.setContentText("Veuillez choisir un rating ");
            a.showAndWait();
            return;

        }
        if (commentaireTF.getText().isEmpty())
        {
            Alert a = new Alert (Alert.AlertType.ERROR);
            a.setTitle("Erreur");
            a.setContentText("Veuillez saisir un commentaire ");
            a.showAndWait();
            return;
        }
        if (idUserTF.getText().isEmpty()|| (!as.rechercherpariduser (Integer.parseInt(idUserTF.getText()))))
        {
            Alert a = new Alert (Alert.AlertType.ERROR);
            a.setTitle("Erreur");
            a.setContentText("Veuillez saisir un identifiant valide");
            a.showAndWait();
            return;
        }

            double Rating = (double) (ratingTF.getValue());
            String Commentaire = commentaireTF.getText();
            int idUser = Integer.parseInt(idUserTF.getText());

        Avis a = new Avis ( Rating, Commentaire, idUser);

            as.ajouter(a);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/InterfaceClient.fxml"));
                Parent root = loader.load();
                idUserTF.getScene().setRoot(root);
            }
            catch (IOException e)
            {
                System.out.println("Erreur");
            }
        }
        catch (
                SQLException e) {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle("Erreur");
            al.setContentText(e.getMessage());
            al.showAndWait();
        }
    }
}
