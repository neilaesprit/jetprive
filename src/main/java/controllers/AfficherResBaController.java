package controllers;
import entities.ReservationBagage;
import entities.ReservationClient;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import services.ReservationBagageService;
import services.ReservationClientService;

import java.sql.SQLException;
import java.util.List;

public class AfficherResBaController {
    @FXML
    private ListView<ReservationBagage> ListReserv;

    @FXML
    private TableColumn<ReservationBagage, Integer> c1;

    @FXML
    private TableColumn<ReservationBagage, Integer> c11;

    @FXML
    private TableColumn<ReservationBagage, String> c2;

    @FXML
    private TableColumn<ReservationBagage, String> c3;

    @FXML
    private TableColumn<ReservationBagage, Double> c4;

    @FXML
    private TableView<ReservationBagage> tabReserv;

    @FXML
    void initialize() {
        try {
            c1.setCellValueFactory(new PropertyValueFactory<>("idReservation"));
            c11.setCellValueFactory(new PropertyValueFactory<>("idClient"));
            c2.setCellValueFactory(new PropertyValueFactory<>("typeBagage"));
            c3.setCellValueFactory(new PropertyValueFactory<>("description"));
            c4.setCellValueFactory(new PropertyValueFactory<>("poids"));
            ReservationBagageService as = new ReservationBagageService();
            List<ReservationBagage> reservList = as.recuperer();
            tabReserv.getItems().addAll(reservList);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

}
