package controllers;

import entities.Avis;
import entities.ReservationClient;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import services.AvisService;
import services.ReservationClientService;

import java.sql.SQLException;
import java.util.List;

public class AfficherResController {

    @FXML
    private ListView<ReservationClient> ListReserv;

    @FXML
    private TableColumn<ReservationClient, Integer> c1;

    @FXML
    private TableColumn<ReservationClient, Integer> c11;

    @FXML
    private TableColumn<ReservationClient, String> c2;

    @FXML
    private TableColumn<ReservationClient, String> c3;

    @FXML
    private TableColumn<ReservationClient, Integer> c4;

    @FXML
    private TableView<ReservationClient> tabReserv;

    @FXML
    void initialize() {
        try {
            c1.setCellValueFactory(new PropertyValueFactory<>("idReservation"));
            c11.setCellValueFactory(new PropertyValueFactory<>("idClient"));
            c2.setCellValueFactory(new PropertyValueFactory<>("dateReservation"));
            c3.setCellValueFactory(new PropertyValueFactory<>("destination"));
            c4.setCellValueFactory(new PropertyValueFactory<>("nombrePersonnes"));
            ReservationClientService as = new ReservationClientService();
            List<ReservationClient> reservList = as.recuperer();
            tabReserv.getItems().addAll(reservList);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

}
