package controllers;

import entities.Utilisateur;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import services.UtilisateurService;

import java.sql.SQLException;
import java.util.List;

public class AfficherUtilisateurController {

    @FXML
    private TextField rechTF;
    @FXML
    private ListView<Utilisateur> ListUser;
    @FXML
    private TableView<Utilisateur> tabUser;
    @FXML
    private TableColumn<Utilisateur, String> c1;
    @FXML
    private TableColumn<Utilisateur, String> c2;
    @FXML
    private TableColumn<Utilisateur, String> c3;
    @FXML
    private TableColumn<Utilisateur, String> c4;
    @FXML
    private TableColumn<Utilisateur, String> c5;
    @FXML
    private TableColumn<Utilisateur, String> c6;
    @FXML
    private TableColumn<Utilisateur, String> c7;
    @FXML
    private TableColumn<Utilisateur, String> c8;
/*

    @FXML
    void initialize() {
        try {
            UtilisateurService us = new UtilisateurService();
            List<Utilisateur> utilisateurList = us.recuperer();
            ListUser.getItems().addAll(utilisateurList);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

 */
    @FXML
    void initialize() {
        try {
            c1.setCellValueFactory(new PropertyValueFactory<>("idUser"));
            c2.setCellValueFactory(new PropertyValueFactory<>("numPassport"));
            c3.setCellValueFactory(new PropertyValueFactory<>("nom"));
            c4.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            c5.setCellValueFactory(new PropertyValueFactory<>("age"));
            c6.setCellValueFactory(new PropertyValueFactory<>("email"));
            c7.setCellValueFactory(new PropertyValueFactory<>("role"));
            c8.setCellValueFactory(new PropertyValueFactory<>("password"));

            UtilisateurService us = new UtilisateurService();
            List<Utilisateur> utilisateurList = us.recuperer();
            tabUser.getItems().addAll(utilisateurList);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        try {
            FilteredList<Utilisateur> filteredUser = new FilteredList<>(tabUser.getItems(), e -> true);
            rechTF.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredUser.setPredicate(Utilisateur -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    return Utilisateur.getNom().toLowerCase().contains(lowerCaseFilter)
                            || Utilisateur.getPrenom().toLowerCase().contains(lowerCaseFilter)
                            || Utilisateur.getEmail().toLowerCase().contains(lowerCaseFilter)
                            || Utilisateur.getRole().toLowerCase().contains(lowerCaseFilter)
                            || Utilisateur.getPassword().toLowerCase().contains(lowerCaseFilter);

                });
            });
            tabUser.setItems(filteredUser);
        }
        catch (Exception e) {
            System.err.println(e.getMessage());

        }

    }
}
