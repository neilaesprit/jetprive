package entities;

public class ReservationBagage {
    private int idReservationBagage;
    private int idClient;
    private double poids; // poids en kg
    private String typeBagage; // ex: valise, sac à dos, etc.
    private String description;

    // Constructeurs
    public ReservationBagage(int idReservationBagage, int idClient, double poids, String typeBagage, String description) {
        this.idReservationBagage = idReservationBagage;
        this.idClient = idClient;
        this.poids = poids;
        this.typeBagage = typeBagage;
        this.description = description;
    }

    public ReservationBagage(int idClient, double poids, String typeBagage, String description) {
        this.idClient = idClient;
        this.poids = poids;
        this.typeBagage = typeBagage;
        this.description = description;
    }

    public ReservationBagage(int idReservationBagage, int idUser) {
        this.idReservationBagage = idReservationBagage;
        this.idClient = idUser;
    }

    // Getters et setters
    public int getIdReservationBagage() {
        return idReservationBagage;
    }

    public void setIdReservationBagage(int idReservationBagage) {
        this.idReservationBagage = idReservationBagage;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public double getPoids() {
        return poids;
    }

    public void setPoids(double poids) {
        this.poids = poids;
    }

    public String getTypeBagage() {
        return typeBagage;
    }

    public void setTypeBagage(String typeBagage) {
        this.typeBagage = typeBagage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ReservationBagage{" +
                "idReservationBagage=" + idReservationBagage +
                ", idClient=" + idClient +
                ", poids=" + poids +
                ", typeBagage='" + typeBagage + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
