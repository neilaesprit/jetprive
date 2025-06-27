package services;

import entities.airline;
import entities.airport;
import utils.MyDatabase;

import java.sql.*;
import java.util.*;

public class AirlineService {

    private Connection cnx;

    public AirlineService() {
        cnx = MyDatabase.getInstance().getCnx();
    }

    // Create - Ajouter un nouvel airline
    public void ajouter(airline airline) throws SQLException {
        // Validation
        if (airline.getName() == null || airline.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom de la compagnie aérienne est obligatoire.");
        }
        if (airline.getCountry() == null || airline.getCountry().trim().isEmpty()) {
            throw new IllegalArgumentException("Le pays de la compagnie aérienne est obligatoire.");
        }

        // Prepare SQL statement with RETURN_GENERATED_KEYS to get the auto-generated ID
        String sql = "INSERT INTO airline (name, country) VALUES (?, ?)";
        PreparedStatement ps = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, airline.getName());
        ps.setString(2, airline.getCountry());

        // Execute insert
        int affectedRows = ps.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("L'insertion de la compagnie aérienne a échoué, aucune ligne affectée.");
        }

        // Get generated ID from DB and set it back to the airline object
        try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                airline.setId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("L'insertion de la compagnie aérienne a échoué, aucun ID généré.");
            }
        }
    }



    // Read - Récupérer la liste des airlines (sans airports)
    public List<airline> recuperer() throws SQLException {
        List<airline> airlines = new ArrayList<>();
        String sql = "SELECT * FROM airline";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String country = rs.getString("country");

            airline airline = new airline(id, name, country);
            airlines.add(airline);
        }

        return airlines;
    }

    // Read - Récupérer airlines avec leurs airports (join)
    public List<airline> recupererAvecAirports() throws SQLException {
        String sql = "SELECT a.id AS airline_id, a.name AS airline_name, a.country, " +
                "p.id AS airport_id, p.nom, p.localisation, p.capacity " +
                "FROM airline a " +
                "LEFT JOIN airport p ON a.id = p.airline_id";

        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(sql);

        Map<Integer, airline> airlineMap = new HashMap<>();

        while (rs.next()) {
            int airlineId = rs.getInt("airline_id");
            airline airline = airlineMap.get(airlineId);

            if (airline == null) {
                String airlineName = rs.getString("airline_name");
                String country = rs.getString("country");
                airline = new airline(airlineId, airlineName, country);
                airline.setAirports(new ArrayList<>());
                airlineMap.put(airlineId, airline);
            }

            int airportId = rs.getInt("airport_id");
            if (airportId != 0) { // check if airport exists (LEFT JOIN might return nulls)
                String nom = rs.getString("nom");
                String localisation = rs.getString("localisation");
                int capacity = rs.getInt("capacity");

                airport airport = new airport(airportId, nom, localisation, capacity);
                airport.setAirlineId(airline.getId()); // optional back-reference
                airline.getAirports().add(airport);
            }
        }

        return new ArrayList<>(airlineMap.values());
    }

    // Update - Modifier une airline existante
    public void modifier(airline airline) throws SQLException {
        if (airline.getId() <= 0) {
            throw new IllegalArgumentException("L'ID de la compagnie aérienne est invalide.");
        }
        if (airline.getName() == null || airline.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom de la compagnie aérienne est obligatoire.");
        }
        if (airline.getCountry() == null || airline.getCountry().trim().isEmpty()) {
            throw new IllegalArgumentException("Le pays de la compagnie aérienne est obligatoire.");
        }

        String sql = "UPDATE airline SET name = ?, country = ? WHERE id = ?";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setString(1, airline.getName());
        ps.setString(2, airline.getCountry());
        ps.setInt(3, airline.getId());
        ps.executeUpdate();
    }

    // Delete - Supprimer une airline par id
    public void supprimer(airline airline) throws SQLException {
        if (airline.getId() <= 0) {
            throw new IllegalArgumentException("L'ID de la compagnie aérienne est invalide.");
        }

        String sql = "DELETE FROM airline WHERE id = ?";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setInt(1, airline.getId());
        ps.executeUpdate();
    }

    public List<Integer> getAllAirlineIds() throws SQLException {
        List<Integer> ids = new ArrayList<>();
        String query = "SELECT id FROM airline";  // replace 'airline' with your actual airline table name

        try (Statement stmt = cnx.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                ids.add(rs.getInt("id"));
            }
        }

        return ids;
    }
}
