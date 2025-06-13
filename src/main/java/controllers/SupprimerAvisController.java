package controllers;

import entities.Avis;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import services.AvisService;

import java.io.IOException;
import java.sql.SQLException;

public class SupprimerAvisController {
    @FXML
    private TextField idAvisTF;
    @FXML
    private TextField idUserTF;
    @FXML
    void supprimeravis(ActionEvent event) {
        try
        {


            AvisService as = new AvisService();
            int idAvis=Integer.parseInt(idAvisTF.getText());
            int idUser=Integer.parseInt(idUserTF.getText());
            if (as.rechercherparavis(idAvis)) {
                Avis a = new Avis(idAvis, idUser);
                as.supprimer(a);
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Afficheravis.fxml"));
                    Parent root = loader.load();
                    idAvisTF.getScene().setRoot(root);
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
