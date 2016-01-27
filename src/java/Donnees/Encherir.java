package Donnees;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "encherir")
@SessionScoped
public class Encherir
{
    private Integer idEncherir;
    private Integer idProduit;
    private Integer cin;
    private Double prix;
    private Date date;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    
    public Encherir() {
    }
    public Encherir(Integer idEncherir, Integer idProduit, Integer cin, Double prix, Date date) {
        this.idEncherir = idEncherir;
        this.idProduit = idProduit;
        this.cin = cin;
        this.prix = prix;
        this.date = date;
    }
    
    public Integer getIdEncherir() {
        return idEncherir;
    }
    public Integer getIdProduit() {
        return idProduit;
    }
    public Produit getProduit() {
        Produit produit = new Produit();
        produit.rechercheProduit(idProduit);
        return produit;
    }
    public Integer getCin() {
        return cin;
    }
    public Client getClient() {
        Client client = new Client();
        client.rechercheClient(cin);
        return(client);
    }
    public Double getPrix() {
        return prix;
    }
    public Date getDate() {
        if(date == null)
            date = new Date();
        return date;
    }
    
    public void setIdEncherir(Integer idEncherir) {
        this.idEncherir = idEncherir;
    }
    public void setIdProduit(Integer idProduit) {
        this.idProduit = idProduit;
    }
    public void setCin(Integer cin) {
        this.cin = cin;
    }
    public void setPrix(Double prix) {
        this.prix = prix;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    
    public boolean ajout() {
        try {
            new Connexion().updateQuery("INSERT INTO `encherir`(`ID_ENCHERIR`, `ID_PRODUIT`, `CIN`, `PRIX`, `DATE`) "
                    + "VALUES("+ idEncherir+ ", "+ idProduit+ ", "+ cin+ ", "+ prix+ ", '"+ sdf.format(date)+ "');");
            return true;
        } catch(SQLException e) {
            return false;
        }
    }
    public boolean maj() {
        try {
            new Connexion().updateQuery("UPDATE `encherir` "
                    + "SET `PRIX`="+ prix+ ",`DATE`='"+ sdf.format(date)+ "' "
                    + "WHERE `ID_ENCHERIR`="+ idEncherir+ " AND `ID_PRODUIT`="+ idProduit+ " AND `CIN`="+ cin+ ";");
            return true;
        } catch(SQLException e) {
            return false;
        }
    }
    public boolean suppression() {
        try {
            new Connexion().updateQuery("DELETE FROM `encherir` WHERE `ID_ENCHERIR`="+ idEncherir+ " AND `ID_PRODUIT`="+ idProduit+ " AND `CIN`="+ cin+ ";");
            return true;
        } catch(SQLException e) {
            return false;
        }
    }
    
    public void rechercheEncherir(Integer idEncherir, Integer idProduit, Integer cin) {
        ResultSet rs = new Connexion().selectQuery("SELECT * FROM `encherir` WHERE `ID_ENCHERIR`="+ idEncherir+ " AND `ID_PRODUIT`="+ idProduit+ " AND `CIN`="+ cin+ ";");
        if(rs != null) {
            try {
                rs.next();
                this.idEncherir = rs.getInt(1);
                this.idProduit = rs.getInt(2);
                this.cin = rs.getInt(3);
                this.prix = rs.getDouble(4);
                this.date = rs.getDate(5);
            } catch(SQLException e) {
            }
        }
    }
    public void rechercheLastEncherir(Integer idProduit) {
        ResultSet rs = new Connexion().selectQuery("SELECT `ID_ENCHERIR`, `ID_PRODUIT`, `CIN`, max(`PRIX`), `DATE` FROM `encherir` WHERE `ID_PRODUIT`="+ idProduit+ ";");
        if(rs != null) {
            try {
                rs.next();
                this.idEncherir = rs.getInt(1);
                this.idProduit = rs.getInt(2);
                this.cin = rs.getInt(3);
                this.prix = rs.getDouble(4);
                this.date = rs.getDate(5);
            } catch(SQLException e) {
            }
        }
    }
    public static List<Encherir> listeEncherir() {
        ResultSet rs = new Connexion().selectQuery("SELECT * FROM `encherir`;");
        if(rs == null)
            return null;
        else {
            try {
                List<Encherir> liste = new ArrayList<>();
                while(rs.next())
                    liste.add(new Encherir(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getDouble(4), rs.getDate(5)));
                return liste;
            } catch(SQLException e) {
                return null;
            }
        }
    }
    
    public int nbrEncher(String client) {
        try {
            ResultSet rs = new Connexion().selectQuery("SELECT count(*) FROM encherir E, client C WHERE E.cin=C.cin AND c.mail='"+client+"';");
            rs.next();
            return rs.getInt(1);
        } catch(SQLException e) {
        }
        return -1;
    }
    
    @Override
    public String toString() {
        return(idEncherir+ "-"+ idProduit+ "-"+ cin+ ": "+ prix);
    }
}
