package services;

import entities.ReservationClient;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationClientService {
    private Connection cnx;

    public ReservationClientService() {
        cnx = MyDatabase.getInstance().getCnx();
    }

    public void ajouter(ReservationClient r) throws SQLException {
        String sql = "INSERT INTO ReservationClient (idClient, dateReservation, destination, nombrePersonnes) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setInt(1, r.getIdClient());
        ps.setString(2, r.getDateReservation());
        ps.setString(3, r.getDestination());
        ps.setInt(4, r.getNombrePersonnes());
        ps.executeUpdate();
    }

    public void modifier(ReservationClient r) throws SQLException {
        String sql = "UPDATE ReservationClient SET idClient = ?, dateReservation = ?, destination = ?, nombrePersonnes = ? WHERE idReservation = ?";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setInt(1, r.getIdClient());
        ps.setString(2, r.getDateReservation());
        ps.setString(3, r.getDestination());
        ps.setInt(4, r.getNombrePersonnes());
        ps.setInt(5, r.getIdReservation());
        ps.executeUpdate();
    }

    public void supprimer(ReservationClient r) throws SQLException {
        String sql = "DELETE FROM ReservationClient WHERE idReservation = ?";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setInt(1, r.getIdReservation());
        ps.executeUpdate();
    }

    public List<ReservationClient> recuperer() throws SQLException {
        List<ReservationClient> liste = new ArrayList<>();
        String sql = "SELECT * FROM ReservationClient";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            int idReservation = rs.getInt("idReservation");
            int idClient = rs.getInt("idClient");
            String dateReservation = rs.getString("dateReservation");
            String destination = rs.getString("destination");
            int nombrePersonnes = rs.getInt("nombrePersonnes");

            ReservationClient r = new ReservationClient(idReservation, idClient, dateReservation, destination, nombrePersonnes);
            liste.add(r);
        }
        return liste;
    }
    public boolean rechercherparReserv (int idA) throws SQLException
    {
        String sql= "select * from ReservationClient where idReservation =" + idA;
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(sql);
        return rs.next();
    }

    public boolean existe(int idA) throws SQLException {
        String sql = "select * from ReservationClient where idReservation =" + idA;
        Statement st= cnx.createStatement();
        ResultSet rs = st.executeQuery(sql);
        return rs.next();
    }
}
