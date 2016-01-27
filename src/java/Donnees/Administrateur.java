package Donnees;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Administrateur
{
    private Integer cin;
    private String nom;
    private String prenom;
    private String mail;
    private String mdp;
    
    public Administrateur() {
    }
    public Administrateur(Integer cin, String nom, String prenom, String mail, String mdp) {
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.mdp = mdp;
    }
    
    public Integer getCin() {
        return cin;
    }
    public String getNom() {
        return nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public String getMail() {
        return mail;
    }
    public String getMdp() {
        return mdp;
    }
    
    public void setCin(Integer cin) {
        this.cin = cin;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }
    public void setMdp(String mdp) {
        this.mdp = mdp;
    }
    
    public boolean ajout() {
        try {
            new Connexion().updateQuery("INSERT INTO `administrateur`(`CIN`, `NOM`, `PRENOM`, `MAIL`, `MDP`) "
                    + "VALUES("+ cin+ ", '"+ nom+ "', '"+ prenom+ "', '"+ mail+ "', '"+ mdp+ "');");
            return true;
        } catch(SQLException e) {
            return false;
        }
    }
    public boolean maj() {
        try {
            new Connexion().updateQuery("UPDATE `administrateur` "
                    + "SET `NOM`='"+ nom+ "',`PRENOM`='"+ prenom+ "',`MAIL`='"+ mail+ "',`MDP`='"+ mdp+ "' "
                    + "WHERE `CIN`="+ cin+ ";");
            return true;
        } catch(SQLException e) {
            return false;
        }
    }
    public boolean suppression() {
        try {
            new Connexion().updateQuery("DELETE FROM `administrateur` WHERE `CIN`="+ cin+ ";");
            return true;
        } catch(SQLException e) {
            return false;
        }
    }
    
    public void rechercheAdministrateur(Integer cin) {
        ResultSet rs = new Connexion().selectQuery("SELECT * FROM `administrateur` WHERE `CIN`="+ cin+ ";");
        if(rs != null) {
            try {
                rs.next();
                this.cin = rs.getInt(1);
                this.nom = rs.getString(2);
                this.prenom = rs.getString(3);
                this.mail = rs.getString(4);
                this.mdp = rs.getString(5);
            } catch(SQLException e) {
            }
        }
    }
    public static Administrateur rechercheAdministrateur(String mail, String mdp) {
        ResultSet rs = new Connexion().selectQuery("SELECT * FROM `administrateur` WHERE `MAIL`='"+ mail+ "' AND `MDP`='"+ mdp+ "';");
        if(rs != null) {
            try {
                rs.next();
                return new Administrateur(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
            } catch(SQLException e) {
            }
        }
        return null;
    }
    public static List<Administrateur> listeAdministrateur() {
        ResultSet rs = new Connexion().selectQuery("SELECT * FROM `administrateur`;");
        if(rs == null)
            return null;
        else {
            try {
                List<Administrateur> liste = new ArrayList<>();
                while(rs.next())
                    liste.add(new Administrateur(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
                return liste;
            } catch(SQLException e) {
                return null;
            }
        }
    }
    
    @Override
    public String toString() {
        return(cin+ ": "+ nom+ " "+ prenom);
    }
}
