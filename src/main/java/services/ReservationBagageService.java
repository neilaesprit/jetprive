package services;

import entities.ReservationBagage;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationBagageService {
    private Connection cnx;

    public ReservationBagageService() {
        cnx = MyDatabase.getInstance().getCnx();
    }

    public void ajouter(ReservationBagage r) throws SQLException {
        String sql = "INSERT INTO ReservationBagage (idClient, poids, typeBagage, description) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setInt(1, r.getIdClient());
        ps.setDouble(2, r.getPoids());
        ps.setString(3, r.getTypeBagage());
        ps.setString(4, r.getDescription());
        ps.executeUpdate();
    }

    public void modifier(ReservationBagage r) throws SQLException {
        String sql = "UPDATE ReservationBagage SET idClient = ?, poids = ?, typeBagage = ?, description = ? WHERE idReservationBagage = ?";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setInt(1, r.getIdClient());
        ps.setDouble(2, r.getPoids());
        ps.setString(3, r.getTypeBagage());
        ps.setString(4, r.getDescription());
        ps.setInt(5, r.getIdReservationBagage());
        ps.executeUpdate();
    }

    public void supprimer(ReservationBagage r) throws SQLException {
        String sql = "DELETE FROM ReservationBagage WHERE idReservationBagage = ?";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setInt(1, r.getIdReservationBagage());
        ps.executeUpdate();
    }

    public List<ReservationBagage> recuperer() throws SQLException {
        List<ReservationBagage> liste = new ArrayList<>();
        String sql = "SELECT * FROM ReservationBagage";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            int idReservationBagage = rs.getInt("idReservationBagage");
            int idClient = rs.getInt("idClient");
            double poids = rs.getDouble("poids");
            String typeBagage = rs.getString("typeBagage");
            String description = rs.getString("description");

            ReservationBagage r = new ReservationBagage(idReservationBagage, idClient, poids, typeBagage, description);
            liste.add(r);
        }
        return liste;
    }

    public boolean rechercherparReserv (int idA) throws SQLException
    {
        String sql= "select * from ReservationClient where idReservationBagage =" + idA;
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(sql);
        return rs.next();
    }
    public boolean existe( int idA) throws SQLException {
        String sql= "select * from ReservationBagage where idReservationBagage =" + idA;
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(sql);
        return rs.next();
    }
}
