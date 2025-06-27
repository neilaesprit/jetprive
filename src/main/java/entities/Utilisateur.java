package entities;

import java.util.Objects;

public class Utilisateur {
    private int idUser;
    private int numPassport;
    private String nom;
    private String prenom;
    private int age;
    private String email;
    private String role;
    private String password;

    public Utilisateur(int idUser, String nom, String prenom){
        this.idUser = idUser;
        this.nom = nom;
        this.prenom = prenom;
    }

    public Utilisateur(int idUser, int numPassport, String nom, String prenom, int age, String email, String role, String password) {
        this.idUser = idUser;
        this.numPassport = numPassport;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.email = email;
        this.role = role;
        this.password = password;
    }


    public Utilisateur(int numPassport, String nom, String prenom, int age, String email, String role, String password) {
        this.numPassport = numPassport;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.email = email;
        this.role = role;
        this.password = password;
    }
    public Utilisateur(String nom, String prenom, int age, String email, String role, String password) {
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.email = email;
        this.role = role;
        this.password = password;


    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getNumPassport() {
        return numPassport;
    }

    public void setNumPassport(int numPassport) {
        this.numPassport = numPassport;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "idUser=" + idUser +
                ", numPassport=" + numPassport +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Utilisateur that = (Utilisateur) o;
        return numPassport == that.numPassport && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numPassport, email);
    }
}


