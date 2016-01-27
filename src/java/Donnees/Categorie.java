package Donnees;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Categorie
{
    private Integer idCategorie;
    private String categorie;
    
    public Categorie() {
    }
    public Categorie(Integer idCategorie, String categorie) {
        this.idCategorie = idCategorie;
        this.categorie = categorie;
    }
    
    public Integer getIdCategorie() {
        return idCategorie;
    }
    public String getCategorie() {
        return categorie;
    }
    
    public void setIdCategorie(Integer idCategorie) {
        this.idCategorie = idCategorie;
    }
    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }
    
    public boolean ajout() {
        try {
            Connexion cnx = new Connexion();
            ResultSet rs = cnx.selectQuery("SELECT max(`ID_CATEGORIE`) FROM `categorie`;");
            if(rs == null)
                idCategorie = 0;
            else {
                rs.next();
                idCategorie = rs.getInt(1) + 1;
            }
            cnx.updateQuery("INSERT INTO `categorie`(`ID_CATEGORIE`, `CATEGORIE`) "
                    + "VALUES("+ idCategorie+ ", '"+ categorie+ "');");
            return true;
        } catch(SQLException e) {
            return false;
        }
    }
    public boolean maj() {
        try {
            new Connexion().updateQuery("UPDATE `categorie` "
                    + "SET `CATEGORIE`='"+ categorie+ "' "
                    + "WHERE `ID_CATEGORIE`="+ idCategorie+ ";");
            return true;
        } catch(SQLException e) {
            return false;
        }
    }
    public boolean suppression() {
        try {
            new Connexion().updateQuery("DELETE FROM `categorie` WHERE `ID_CATEGORIE`="+ idCategorie+ ";");
            return true;
        } catch(SQLException e) {
            return false;
        }
    }
    
    public void rechercheCategorie(Integer idCategorie) {
        ResultSet rs = new Connexion().selectQuery("SELECT * FROM `categorie` WHERE `ID_CATEGORIE`="+ idCategorie+ ";");
        if(rs != null) {
            try {
                rs.next();
                this.idCategorie = rs.getInt(1);
                this.categorie = rs.getString(2);
            } catch(SQLException e) {
            }
        }
    }
    public static List<Categorie> listeCategorie() {
        ResultSet rs = new Connexion().selectQuery("SELECT * FROM `categorie`;");
        if(rs == null)
            return null;
        else {
            try {
                List<Categorie> liste = new ArrayList<>();
                while(rs.next())
                    liste.add(new Categorie(rs.getInt(1), rs.getString(2)));
                return liste;
            } catch(SQLException e) {
                return null;
            }
        }
    }
    
    @Override
    public String toString() {
        return("["+ idCategorie+ "] "+ categorie);
    }
}
