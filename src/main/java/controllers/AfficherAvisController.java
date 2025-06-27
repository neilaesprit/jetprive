package controllers;

import entities.Avis;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import services.AvisService;


import java.sql.SQLException;
import java.util.List;

public class AfficherAvisController {
    @FXML
    private ListView<Avis> ListAvis;
    @FXML
    private TableView<Avis> tabavis;
    @FXML
    private TableColumn<Avis, Integer> c1;
    @FXML
    private TableColumn<Avis, Integer> c2;
    @FXML
    private TableColumn<Avis, Integer> c3;
    @FXML
    private TableColumn<Avis, Integer> c4;

    @FXML
    void initialize() {
        try {
            c1.setCellValueFactory(new PropertyValueFactory<>("idAvis"));
            c2.setCellValueFactory(new PropertyValueFactory<>("Rating"));
            c3.setCellValueFactory(new PropertyValueFactory<>("Commentaire"));
            c4.setCellValueFactory(new PropertyValueFactory<>("idUser"));
            AvisService as = new AvisService();
            List<Avis> avisList = as.recuperer();
            tabavis.getItems().addAll(avisList);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }


}
