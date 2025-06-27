package services;

import entities.airport;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class airportService {

    private Connection cnx;

    public airportService(){
        cnx = MyDatabase.getInstance().getCnx();
    }

    // Ajouter un nouvel airport (with airline_id)
    public void ajouter(airport a) throws SQLException {
        if (a.getNom() == null || a.getNom().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom de l'aéroport est obligatoire.");
        }
        if (a.getLocalisation() == null || a.getLocalisation().trim().isEmpty()) {
            throw new IllegalArgumentException("La localisation de l'aéroport est obligatoire.");
        }
        if (a.getCapacity() <= 0) {
            throw new IllegalArgumentException("La capacité doit être un nombre positif.");
        }
        if (a.getAirlineId() <= 0) {
            throw new IllegalArgumentException("L'ID de la compagnie aérienne est obligatoire et doit être positif.");
        }

        String sql = "INSERT INTO airport (nom, localisation, capacity, airline_id) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setString(1, a.getNom());
        ps.setString(2, a.getLocalisation());
        ps.setInt(3, a.getCapacity());
        ps.setInt(4, a.getAirlineId());
        int rowsInserted = ps.executeUpdate();
        System.out.println(rowsInserted + " row(s) inserted.");
    }

    // Modifier un airport existant (with airline_id)
    public void modifier(airport a) throws SQLException {
        if (a.getId() <= 0) {
            throw new IllegalArgumentException("L'ID de l'aéroport est invalide.");
        }
        if (a.getNom() == null || a.getNom().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom de l'aéroport est obligatoire.");
        }
        if (a.getLocalisation() == null || a.getLocalisation().trim().isEmpty()) {
            throw new IllegalArgumentException("La localisation de l'aéroport est obligatoire.");
        }
        if (a.getCapacity() <= 0) {
            throw new IllegalArgumentException("La capacité doit être un nombre positif.");
        }
        if (a.getAirlineId() <= 0) {
            throw new IllegalArgumentException("L'ID de la compagnie aérienne est obligatoire et doit être positif.");
        }

        String sql = "UPDATE airport SET nom = ?, localisation = ?, capacity = ?, airline_id = ? WHERE id = ?";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setString(1, a.getNom());
        ps.setString(2, a.getLocalisation());
        ps.setInt(3, a.getCapacity());
        ps.setInt(4, a.getAirlineId());
        ps.setInt(5, a.getId());
        ps.executeUpdate();
    }

    // Supprimer un airport par id
    public void supprimer(airport a) throws SQLException {
        String sql = "DELETE FROM airport WHERE id = ?";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setInt(1, a.getId());
        ps.executeUpdate();
    }

    // Récupérer la liste des airports avec airline_id (no join here, just id)
    public List<airport> recuperer() throws SQLException {
        List<airport> airports = new ArrayList<>();
        String sql = "SELECT * FROM airport";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()){
            int id = rs.getInt("id");
            String nom = rs.getString("nom");
            String localisation = rs.getString("localisation");
            int capacity = rs.getInt("capacity");
            int airlineId = rs.getInt("airline_id");

            airport a = new airport(id, nom, localisation, capacity);
            a.setAirlineId(airlineId);
            airports.add(a);
        }

        return airports;
    }

    // Récupérer la liste des airports avec jointure pour récupérer nom airline (optional)
    public List<String> recupererAvecNomAirline() throws SQLException {
        List<String> results = new ArrayList<>();
        String sql = "SELECT airport.id, airport.nom, airport.localisation, airport.capacity, airline.name AS airline_name " +
                "FROM airport " +
                "JOIN airline ON airport.airline_id = airline.id";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()){
            int id = rs.getInt("id");
            String nom = rs.getString("nom");
            String localisation = rs.getString("localisation");
            int capacity = rs.getInt("capacity");
            String airlineName = rs.getString("airline_name");

            results.add("Airport{" +
                    "id=" + id +
                    ", nom='" + nom + '\'' +
                    ", localisation='" + localisation + '\'' +
                    ", capacity=" + capacity +
                    ", airlineName='" + airlineName + '\'' +
                    '}');
        }

        return results;
    }

}
