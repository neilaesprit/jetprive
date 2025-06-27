package entities;

public class airport {

    private int id;
    private String nom;
    private String localisation;
    private int capacity;

    // Add airlineId to link airport with airline
    private int airlineId;

    public airport(int id, String nom, String localisation, int capacity, int airlineId) {
        this.id = id;
        this.nom = nom;
        this.localisation = localisation;
        this.capacity = capacity;
        this.airlineId = airlineId;
    }

    public airport(String nom, String localisation, int capacity, int airlineId) {
        this.nom = nom;
        this.localisation = localisation;
        this.capacity = capacity;
        this.airlineId = airlineId;
    }

    // existing constructors without airlineId (optional, but less recommended now)
    public airport(int id, String nom, String localisation, int capacity) {
        this(id, nom, localisation, capacity, 0);
    }

    public airport(String nom, String localisation, int capacity) {
        this(nom, localisation, capacity, 0);
    }

    // getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getAirlineId() {
        return airlineId;
    }

    public void setAirlineId(int airlineId) {
        this.airlineId = airlineId;
    }

    @Override
    public String toString() {
        return "airport{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", localisation='" + localisation + '\'' +
                ", capacity=" + capacity +
                ", airlineId=" + airlineId +
                '}';
    }
}
