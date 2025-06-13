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
import java.util.regex.Pattern;

public class LoginController {
    @FXML
    private TextField EmailTF;
    @FXML
    private TextField PassWordTF;
    int compteur;

    @FXML
    void connexion(ActionEvent event) {

        if((EmailTF.getText().isEmpty()) || !(EmailTF.getText().contains("@")) || !(EmailTF.getText().contains(".")) || (EmailTF.getText().indexOf("@") > EmailTF.getText().lastIndexOf(".")))
        {
            Alert a = new Alert (Alert.AlertType.ERROR);
            a.setTitle("Erreur");
            a.setContentText("Veuillez saisir une adresse mail valide ");
            a.showAndWait();
            return;
        }
        if(PassWordTF.getText().isEmpty())
        {
            Alert a = new Alert (Alert.AlertType.ERROR);
            a.setTitle("Erreur");
            a.setContentText("Veuillez saisir un mot de passe");
            a.showAndWait();
            return;
        }

        String email = EmailTF.getText();
        String password = PassWordTF.getText();
        UtilisateurService us=new UtilisateurService();
        try {
            Utilisateur u = us.rechercherpaMailetPassword(email,password);
            if( u != null) {
                if(u.getRole().equals("Admin")){
//naviguez vers interface admin
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/InterfaceAdmin.fxml"));
                    Parent root = loader.load();
                    EmailTF.getScene().setRoot(root);

                }
                else {
//naviguez vers interface client
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/InterfaceClient.fxml"));
                    Parent root = loader.load();
                    EmailTF.getScene().setRoot(root);
                }
            }
            else
            {
                //Veuillez créer un compte
                Alert a = new Alert (Alert.AlertType.ERROR);
                a.setTitle("Erreur");
                a.setContentText("Veuillez vérifier votre email ou mot de passe");
                a.showAndWait();
            }
        } catch (SQLException |IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void creerUnCompte(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterUtilisateur.fxml"));
            Parent root = loader.load();
            EmailTF.getScene().setRoot(root);

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


}
