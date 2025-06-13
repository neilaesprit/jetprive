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

public class ModifierUtilisateurController {
    @FXML
    private TextField idUserTF;
    @FXML
    private TextField NumPassportTF;
    @FXML
    private TextField NomTF;
    @FXML
    private TextField PrenomTF;
    @FXML
    private TextField AgeTF;
    @FXML
    private TextField EmailTF;
    @FXML
    private TextField RoleTF;
    @FXML
    private TextField PassWordTF;

    @FXML
    void rechercherutilisateur(ActionEvent event) {
        UtilisateurService us = new UtilisateurService();
        try {
            if (idUserTF.getText().isEmpty() )
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("Veuillez saisir votre identifiant");
                alert.showAndWait();
                return;
            }
            if (NumPassportTF.getText().isEmpty() || (NumPassportTF.getText().trim().length()<9))
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("Veuillez saisir votre numero de passport");
                alert.showAndWait();
                return;
            }




            Utilisateur u=(us.rechercherparpasseetid(Integer.parseInt(idUserTF.getText()),Integer.parseInt(NumPassportTF.getText())));
            if(u!=null )
            {

               /* NumPassportTF.setText(String.valueOf(u.getNumPassport()));*/
                NomTF.setText(u.getNom());
                PrenomTF.setText(u.getPrenom());
                AgeTF.setText(String.valueOf(u.getAge()));
                EmailTF.setText(u.getEmail());
                RoleTF.setText(u.getRole());
                PassWordTF.setText(u.getPassword());
            }

        else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setContentText("identifiant invalide ou numero de passport invalide");
                    alert.showAndWait();
                }


        }
        catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }

/*
    @FXML
    public void rechercher(ActionEvent event) {
        UtilisateurService us = new UtilisateurService();
        Utilisateur u = us.rechercherparid(idUserTF.getText());
        PrenomTF.setText(u.getPrenom());
    }*/

    @FXML
    void modifierutilisateur(ActionEvent event) {
        try {
            UtilisateurService us = new UtilisateurService();

            if (idUserTF.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez remplir tous les champs");
                alert.showAndWait();
                return;
            }
            if (NumPassportTF.getText().isEmpty() || (NumPassportTF.getText().trim().length()<9))
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez remplir tous les champs");
                alert.showAndWait();
                return;
            }
            if (NomTF.getText().isEmpty() && (NomTF.getText().length() <= 4))
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez remplir tous les champs");
                alert.showAndWait();
                return;
            }
            if (PrenomTF.getText().isEmpty()&& (PrenomTF.getText().length() <= 3))
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez remplir tous les champs");
                alert.showAndWait();
                return;

            }
            if (AgeTF.getText().isEmpty()||Integer.parseInt(AgeTF.getText())<0  || Integer.parseInt(AgeTF.getText()) >99)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez remplir tous les champs");
                alert.showAndWait();
                return;
            }

            if (EmailTF.getText().isEmpty()|| !(EmailTF.getText().contains("@")) || !(EmailTF.getText().contains(".")) || (EmailTF.getText().indexOf("@") > EmailTF.getText().lastIndexOf(".")))
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez saisir une adresse mail valide (ex:username@domain.com)");
                alert.showAndWait();
                return;
            }
            if (RoleTF.getText().isEmpty() && (!RoleTF.getText().trim().toLowerCase().equals("admin")) || (!RoleTF.getText().trim().toLowerCase().equals("client")))
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez préciser votre role(Admin/client)");
                alert.showAndWait();
                return;

            }
            if ((PassWordTF.getText().isEmpty())|| !Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$")
                    .matcher(PassWordTF.getText())
                    .matches())
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez saisir un mot de passe valide(ex:Test!124)");
                alert.showAndWait();
                return;
            }


            int idUser = Integer.parseInt(idUserTF.getText());
            int NumPassport = Integer.parseInt(NumPassportTF.getText());
            String Nom = NomTF.getText();
            String Prenom = PrenomTF.getText();
            int Age = Integer.parseInt(AgeTF.getText());
            String Email = EmailTF.getText();
            String Role = RoleTF.getText();
            String Password = PassWordTF.getText();

            Utilisateur u = new Utilisateur(idUser, NumPassport, Nom, Prenom, Age, Email, Role, Password);
            us.modifier(u);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/InterfaceClient.fxml"));
                Parent root = loader.load();
                NomTF.getScene().setRoot(root);
            }
            catch (IOException e)
            {
                System.out.println("Erreur");
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
