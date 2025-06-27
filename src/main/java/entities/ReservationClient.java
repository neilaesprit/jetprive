package entities;

public class ReservationClient {
    private int idReservation;
    private int idClient;
    private String dateReservation;
    private String destination;
    private int nombrePersonnes;

    // Constructeurs
    public ReservationClient(int idReservation, int idClient, String dateReservation, String destination, int nombrePersonnes) {
        this.idReservation = idReservation;
        this.idClient = idClient;
        this.dateReservation = dateReservation;
        this.destination = destination;
        this.nombrePersonnes = nombrePersonnes;
    }

    public ReservationClient(int idClient, String dateReservation, String destination, int nombrePersonnes) {
        this.idClient = idClient;
        this.dateReservation = dateReservation;
        this.destination = destination;
        this.nombrePersonnes = nombrePersonnes;
    }

    public ReservationClient(int idReservation, int idUser) {


        this.idReservation = idReservation;
        this.idClient = idUser;
    }

    // Getters et setters
    public int getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(int idReservation) {
        this.idReservation = idReservation;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public String getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(String dateReservation) {
        this.dateReservation = dateReservation;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getNombrePersonnes() {
        return nombrePersonnes;
    }

    public void setNombrePersonnes(int nombrePersonnes) {
        this.nombrePersonnes = nombrePersonnes;
    }

    @Override
    public String toString() {
        return "ReservationClient{" +
                "idReservation=" + idReservation +
                ", idClient=" + idClient +
                ", dateReservation='" + dateReservation + '\'' +
                ", destination='" + destination + '\'' +
                ", nombrePersonnes=" + nombrePersonnes +
                '}';
    }
}
