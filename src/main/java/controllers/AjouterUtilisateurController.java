package controllers;

import entities.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import org.simplejavamail.api.email.Email;

import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;

import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;

import services.UtilisateurService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Pattern;


public class AjouterUtilisateurController {
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
    private TextField PassWordTF;



    @FXML
    void insererutilisateur (ActionEvent event)
    {
        try
        {
        UtilisateurService us=new UtilisateurService();


        if (NumPassportTF.getText().isEmpty() || NumPassportTF.getText().trim().length() < 9)
        {
            Alert a = new Alert (Alert.AlertType.ERROR);
            a.setTitle("Erreur");
            a.setContentText("Veuillez saisir un  numéro de passport valide");
            a.showAndWait();
            return;
        }
            if(us.rechercherparpassport(Integer.parseInt(NumPassportTF.getText())))
            {
                Alert a = new Alert (Alert.AlertType.ERROR);
                a.setTitle("Erreur");
                a.setContentText("Ce compte existe deja");
                a.showAndWait();
                return;
            }
        if((NomTF.getText().isEmpty()) && (NomTF.getText().length() <= 4))
        {
            Alert a = new Alert (Alert.AlertType.ERROR);
            a.setTitle("Erreur");
            a.setContentText("Veuillez saisir un nom valide");
            a.showAndWait();
            return;
        }
        if((PrenomTF.getText().isEmpty()) && (PrenomTF.getText().length() <= 3))
        {
            Alert a = new Alert (Alert.AlertType.ERROR);
            a.setTitle("Erreur");
            a.setContentText("Veuillez saisir un prenom valide");
            a.showAndWait();
            return;
        }
        if ( AgeTF.getText().isEmpty()||Integer.parseInt(AgeTF.getText())<0  || Integer.parseInt(AgeTF.getText()) >99)
        {
            Alert a = new Alert (Alert.AlertType.ERROR);
            a.setTitle("Erreur");
            a.setContentText("Veuillez saisir un age valide");
            a.showAndWait();
            return;
        }

        if((EmailTF.getText().isEmpty()) || !(EmailTF.getText().contains("@")) || !(EmailTF.getText().contains(".")) || (EmailTF.getText().indexOf("@") > EmailTF.getText().lastIndexOf(".")))
        {
            Alert a = new Alert (Alert.AlertType.ERROR);
            a.setTitle("Erreur");
            a.setContentText("Veuillez saisir une adresse mail valide (ex:username@domain.com)");
            a.showAndWait();
            return;
        }
        if((PassWordTF.getText().isEmpty() ) || !Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$")
                .matcher(PassWordTF.getText())
                .matches())
        {
            Alert a = new Alert (Alert.AlertType.ERROR);
            a.setTitle("Erreur");
            a.setContentText("Veuillez saisir un mot de passe valide(ex:Test!124)");
            a.showAndWait();
            return;
        }
            int NumPassport=Integer.parseInt(NumPassportTF.getText());
            String Nom=NomTF.getText();
            String Prenom=PrenomTF.getText();
            int Age=Integer.parseInt(AgeTF.getText());
            String EmailUtilisateur=EmailTF.getText();
            String Password=PassWordTF.getText();


        Utilisateur u = new Utilisateur( NumPassport,Nom,Prenom,Age,EmailUtilisateur,"Client",Password);

            us.ajouter(u);
            try
            {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
                Parent root = loader.load();
                EmailTF.getScene().setRoot(root);

            }
            catch(IOException e)
            {
                System.out.println("Erreur");
            }
            // Create the mailer object with SMTP server settings
            Email mail = EmailBuilder.startingBlank()
                    .from("gestion jet privé", "neilajribi@live.fr")
                    .to(" nouvel Utilisateur" ,EmailUtilisateur)
                    .withSubject("Votre compte a été créé avec succès")
                    .withPlainText("Bienvenue " +Nom+" "+Prenom+ " Merci de nous rejoindre")
                    .buildEmail();

            Mailer mailer = MailerBuilder
                    .withSMTPServer("smtp.mailtrap.io", 587, "91babae0490ca8", "78efea0f19b0a5")
                    /*.withSMTPServer("smtp.gmail.com",587,"EmailUtilisateur","")
                    .withSMTPServer("smtp.office365.com",587)
                     */
                    .withTransportStrategy(TransportStrategy.SMTP)
                    .buildMailer();

            // Send the email
            try {
                mailer.sendMail(mail);
                System.out.println("E-mail envoye avec succes.");
            } catch (Exception e) {
                System.err.println("Erreur lors de l'envoi de l'e-mail : " + e.getMessage());
                e.printStackTrace(); // Pour voir les détails de l'erreur exacte
            }
        }
        catch (SQLException e)
        {
            Alert a = new Alert (Alert.AlertType.ERROR);
            a.setTitle("Erreur");
            a.setContentText(e.getMessage());
            a.showAndWait();
        }
    }


}
