package services;

import entities.Utilisateur;
import utils.MyDatabase;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurService {
    private Connection cnx;
    public UtilisateurService() {
        cnx = MyDatabase.getInstance().getCnx();
    }
    public void ajouter (Utilisateur u)  throws SQLException
    {
        String sql = "insert into utilisateur(numPassport,nom,prenom,age,email,role, password)" +
                "values("+u.getNumPassport()+",'"+u.getNom()+"','"+u.getPrenom()+"',"+u.getAge()+",'"+u.getEmail()+"','Client','"+u.getPassword()+"')";
        Statement st = cnx.createStatement();
        st.executeUpdate(sql);
    }

    public void modifier (Utilisateur u ) throws SQLException
    {
        String sql = "update utilisateur set numPassport = ?, nom = ?, prenom = ?, age = ?, email = ?, role = ?, password = ? where idUser = ?";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setInt(1, u.getNumPassport());
        ps.setString(2, u.getNom());
        ps.setString(3, u.getPrenom());
        ps.setInt(4, u.getAge());
        ps.setString(5, u.getEmail());
        ps.setString(6, u.getRole());
        ps.setString(7, u.getPassword());
        ps.setInt(8, u.getIdUser());
        ps.executeUpdate();
    }

    public void supprimer (Utilisateur u) throws SQLException
    {
        String sql = "delete from utilisateur where idUser = ?";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setInt(1,u.getIdUser());
        ps.executeUpdate();
    }

    public List<Utilisateur> recuperer () throws SQLException
    {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        String sql = "select * from utilisateur";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            int idUser = rs.getInt("idUser");
            int numPassport = rs.getInt("numPassport");
            String nom = rs.getString("nom");
            String prenom = rs.getString("prenom");
            int age = rs.getInt("age");
            String email = rs.getString("email");
            String role = rs.getString("role");
            String password = rs.getString("password");
            Utilisateur u = new Utilisateur(idUser,numPassport,nom,prenom,age,email,role,password);
            utilisateurs.add(u);

        }
        return utilisateurs;
    }
    public boolean rechercherparpassport(int passport) throws SQLException
    {
      String sql= "select * from utilisateur where NumPassport =" + passport;
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(sql);
        return rs.next();
    }
    public boolean rechercherparid (int id) throws SQLException
    {
        String sql= "select * from utilisateur where idUser =" + id;
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(sql);
        return rs.next();
    }
    public Utilisateur rechercherpaMailetPassword (String mail, String mdp) throws SQLException
    {
        String sql= "select * from utilisateur where email ='" + mail + "' and password ='" + mdp+"'";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(sql);
        Utilisateur u = null;
        while (rs.next()) {
            int idUser = rs.getInt("idUser");
            int numPassport = rs.getInt("numPassport");
            String nom = rs.getString("nom");
            String prenom = rs.getString("prenom");
            int age = rs.getInt("age");
            String email = rs.getString("email");
            String role = rs.getString("role");
            String password = rs.getString("password");
             u = new Utilisateur(idUser,numPassport,nom,prenom,age,email,role,password);

        }
        return u;
    }
    public Utilisateur rechercherparidu (int idu) throws SQLException
    {
        String sql= "select * from utilisateur where idUser =" + idu;
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(sql);
        Utilisateur u = null;
        while (rs.next()) {
            int numPassport = rs.getInt("numPassport");
            String nom = rs.getString("nom");
            String prenom = rs.getString("prenom");
            int age = rs.getInt("age");
            String email = rs.getString("email");
            String role = rs.getString("role");
            String password = rs.getString("password");
            u = new Utilisateur(numPassport,nom,prenom,age,email,role,password);
        }
        return u;
    }
    public Utilisateur rechercherparpasseetid (int idu,int pass) throws SQLException {
        String sql = "select * from utilisateur where idUser ='" + idu + "' and NumPassport ='" + pass+"'";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(sql);
        Utilisateur u = null;
        while (rs.next()) {
            String nom = rs.getString("nom");
            String prenom = rs.getString("prenom");
            int age = rs.getInt("age");
            String email = rs.getString("email");
            String role = rs.getString("role");
            String password = rs.getString("password");
            u = new Utilisateur(nom, prenom, age, email, role, password);
        }
        return u;
    }

}
