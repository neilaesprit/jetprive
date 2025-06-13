package services;

import entities.Avis;
import entities.Utilisateur;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class AvisService {
    private Connection cnx;
    public AvisService() {
        cnx = MyDatabase.getInstance().getCnx();
    }

    public void ajouter (Avis a) throws SQLException
    {
        String sql = "insert into Avis (Rating,Commentaire,idUser)"+
                "values ("+a.getRating()+",'"+a.getCommentaire()+"',"+a.getIdUser()+")";
        Statement st = cnx.createStatement();
        st.executeUpdate(sql);
    }

    public void modifier (Avis a ) throws SQLException
    {
        String sql = "update avis set Rating = ?, Commentaire = ?, idUser = ? where idAvis = ?";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setDouble(1, a.getRating());
        ps.setString(2, a.getCommentaire());
        ps.setInt(3, a.getIdUser());
        ps.setInt(4, a.getIdAvis());
        ps.executeUpdate();
    }

    public void supprimer (Avis a) throws SQLException
    {
        String sql = "delete from Avis where idAvis = ?";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setInt(1, a.getIdAvis());
        ps.executeUpdate();
    }

    public List<Avis> recuperer () throws SQLException
    {
        List<Avis> avisL = new ArrayList<>();
        String sql = "select * from avis";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            int idAvis = rs.getInt("idAvis");
            double rating = rs.getDouble("Rating");
            String commentaire = rs.getString("Commentaire");
            int idUser = rs.getInt("idUser");
            Avis a = new Avis (idAvis, rating, commentaire, idUser);
            avisL.add(a);

        }
        return avisL;
    }
    public boolean rechercherparavis (int idA) throws SQLException
    {
        String sql= "select * from avis where idAvis =" + idA;
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(sql);
        return rs.next();
    }
    public boolean rechercherpariduser (int idU) throws SQLException
    {
        String sql= "select avi.idUser  from avis avi join utilisateur util on avi.idUser =util.idUser where avi.idAvis =" + idU;
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(sql);
        return rs.next();
    }
}
