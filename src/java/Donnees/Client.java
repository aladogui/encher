package Donnees;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Client
{
    private Integer cin;
    private String nom;
    private String prenom;
    private String mail;
    private String mdp;
    private String adresse;
    private Date dateNaissance;
    private Long telephone;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    public Client() {
    }
    public Client(Integer cin, String nom, String prenom, String mail, String mdp, String adresse, Date dateNaissance, Long telephone) {
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.mdp = mdp;
        this.adresse = adresse;
        this.dateNaissance = dateNaissance;
        this.telephone = telephone;
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
    public String getAdresse() {
        return adresse;
    }
    public Date getDateNaissance() {
        if(dateNaissance == null)
            dateNaissance = new Date();
        return dateNaissance;
    }
    public Long getTelephone() {
        return telephone;
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
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }
    public void setDateNaissance(String dateNaissance) {
        try {
            System.out.println(dateNaissance);
            this.dateNaissance = sdf.parse(dateNaissance);
        } catch(ParseException e) { }
    }
    public void setTelephone(Long telephone) {
        this.telephone = telephone;
    }
    
    public boolean ajout() {
        try {
            new Connexion().updateQuery("INSERT INTO `client`(`CIN`, `NOM`, `PRENOM`, `MAIL`, `MDP`, `ADRESSE`, `DATE_NAISSANCE`, `TELEPHONE`) "
                    + "VALUES ("+ cin+ ", '"+ nom+ "', '"+ prenom+ "', '"+ mail+ "', '"+ mdp+ "', '"+ adresse+ "', '"+ sdf.format(dateNaissance)+ "', "+ telephone+ ");");
            return true;
        } catch(SQLException e) {
            return false;
        }
    }
    public boolean maj() {
        try {
            new Connexion().updateQuery("UPDATE `client` "
                    + "SET `NOM`='"+ nom+ "',`PRENOM`='"+ prenom+ "',`MAIL`='"+ mail+ "',`MDP`='"+ mdp+ "',`ADRESSE`='"+ adresse+ "',`DATE_NAISSANCE`='"+ sdf.format(dateNaissance)+ "',`TELEPHONE`="+ telephone+ " "
                    + "WHERE `CIN`="+ cin+ ";");
            return true;
        } catch(SQLException e) {
            return false;
        }
    }
    public boolean suppression() {
        try {
            new Connexion().updateQuery("DELETE FROM `client` WHERE `CIN`="+ cin+ ";");
            return true;
        } catch(SQLException e) {
            return false;
        }
    }
    
    public void rechercheClient(Integer cin) {
        ResultSet rs = new Connexion().selectQuery("SELECT * FROM `client` WHERE `CIN`="+ cin+ ";");
        if(rs != null) {
            try {
                rs.next();
                this.cin = rs.getInt(1);
                this.nom = rs.getString(2);
                this.prenom = rs.getString(3);
                this.mail = rs.getString(4);
                this.mdp = rs.getString(5);
                this.adresse = rs.getString(6);
                this.dateNaissance = rs.getDate(7);
                this.telephone = rs.getLong(8);
            } catch(SQLException e) {
            }
        }
    }
    public static Client rechercheClient(String mail, String mdp) {
        ResultSet rs = new Connexion().selectQuery("SELECT * FROM `client` WHERE `MAIL`='"+ mail+ "' AND `MDP`='"+ mdp+ "';");
        if(rs != null) {
            try {
                rs.next();
                return new Client(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6), rs.getDate(7), rs.getLong(8));
            } catch(SQLException e) {
            }
        }
        return null;
    }
    public static List<Client> listeClient() {
        ResultSet rs = new Connexion().selectQuery("SELECT * FROM `client`;");
        if(rs == null)
            return null;
        else {
            try {
                List<Client> liste = new ArrayList<>();
                while(rs.next())
                    liste.add(new Client(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
                            rs.getString(5), rs.getString(6), rs.getDate(7), rs.getLong(8)));
                return liste;
            } catch(SQLException e) {
                return null;
            }
        }
    }
    
    @Override
    public String toString() {
        return("["+ cin+ "] "+ nom+ " "+ prenom);
    }
}
