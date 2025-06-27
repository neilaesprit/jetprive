package entities;

public class Avis {
    private int idAvis;
    private double Rating;
    private String Commentaire;
    private int idUser;

    public Avis (int idAvis, double Rating, String Commentaire, int idUser) {
        this.idAvis = idAvis;
        this.Rating = Rating;
        this.Commentaire = Commentaire;
        this.idUser = idUser;
    }

    public Avis (double Rating, String Commentaire, int idUser) {
        this.Rating = Rating;
        this.Commentaire = Commentaire;
        this.idUser = idUser;
    }
    public Avis(int idAvis, int idUser) {
        this.idAvis = idAvis;
        this.idUser = idUser;

    }

    public int getIdAvis() {
        return idAvis;
    }

    public void setIdAvis(int idAvis) {
        this.idAvis = idAvis;
    }

    public double getRating() {
        return Rating;
    }

    public void setRating(double rating) {
        Rating = rating;
    }

    public String getCommentaire() {
        return Commentaire;
    }

    public void setCommentaire(String commentaire) {
        Commentaire = commentaire;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    @Override
    public String toString() {
        return "Avis{" +
                "idAvis=" + idAvis +
                ", Rating=" + Rating +
                ", Commentaire='" + Commentaire + '\'' +
                ", idUser=" + idUser +
                '}';
    }
}
