package controllers;

import entities.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import services.UtilisateurService;

import java.io.IOException;
import java.sql.SQLException;

public class SupprimerUtilisateurController {
    @FXML
    private TextField idUserTF;
    @FXML
    private TextField NomTF;
    @FXML
    private TextField PrenomTF;
    @FXML
    void supprimerutilisateur (ActionEvent event) {
        try
        {
            UtilisateurService us = new UtilisateurService();

            int idUser = Integer.parseInt(idUserTF.getText());
            String Nom = NomTF.getText();
            String Prenom = PrenomTF.getText();
            if(us.rechercherparid(idUser)){
                Utilisateur u = new Utilisateur(idUser, Nom, Prenom);
                us.supprimer(u);
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherUtilisateur.fxml"));
                    Parent root = loader.load();
                    NomTF.getScene().setRoot(root);
                }
                catch (IOException e)
                {
                    System.out.println("Erreur");
                }


            }
            else {
                Alert a = new Alert (Alert.AlertType.ERROR);
                a.setTitle("Erreur");
                a.setContentText("identifiant inexistant");
                a.showAndWait();
                return;
            }
        }
        catch (SQLException e)
        {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Erreur");
            a.setContentText(e.getMessage());
            a.showAndWait();
        }
    }

}
