package Donnees;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Produit
{
    private Integer idProduit;
    private Integer idCategorie;
    private Integer cinClient;
    private String nomProduit;
    private String description;
    private String image;
    private Double prixInitiale;
    private Date dateDebut;
    private Date dateFin;
    private Integer cinAdmin;
    private boolean validite;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    public Produit() {
    }
    public Produit(Integer idProduit, Integer idCategorie, Integer cinClient, String nomProduit, String description, String image, Double prixInitiale, Date dateDebut, Date dateFin, Integer cinAdmin, boolean validite) {
        this.idProduit = idProduit;
        this.idCategorie = idCategorie;
        this.cinClient = cinClient;
        this.nomProduit = nomProduit;
        this.description = description;
        this.image = image;
        this.prixInitiale = prixInitiale;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.cinAdmin = cinAdmin;
        this.validite = validite;
    }
    
    public Integer getIdProduit() {
        return idProduit;
    }
    public Integer getIdCategorie() {
        return idCategorie;
    }
    public Categorie getCategorie() {
        Categorie categorie = new Categorie();
        categorie.rechercheCategorie(idCategorie);
        return categorie;
    }
    public Integer getCinClient() {
        return cinClient;
    }
    public Client getClient() {
        Client client = new Client();
        client.rechercheClient(cinClient);
        return client;
    }
    public String getNomProduit() {
        return nomProduit;
    }
    public String getDescription() {
        return description;
    }
    public String getImage() {
        return image;
    }
    public Double getPrixInitiale() {
        return prixInitiale;
    }
    public Date getDateDebut() {
        if(dateDebut == null)
            dateDebut = new Date();
        return dateDebut;
    }
    public Date getDateFin() {
        if(dateFin == null)
            dateFin = new Date();
        return dateFin;
    }
    public Integer getCinAdmin() {
        return cinAdmin;
    }
    public Administrateur getAdministrateur() {
        Administrateur administrateur = new Administrateur();
        administrateur.rechercheAdministrateur(cinAdmin);
        return administrateur;
    }
    public boolean isValidite() {
        return validite;
    }
    
    public void setIdProduit(Integer idProduit) {
        this.idProduit = idProduit;
    }
    public void setIdCategorie(Integer idCategorie) {
        this.idCategorie = idCategorie;
    }
    public void setCinClient(Integer cinClient) {
        this.cinClient = cinClient;
    }
    public void setNomProduit(String nomProduit) {
        this.nomProduit = nomProduit;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public void setPrixInitiale(Double prixInitiale) {
        this.prixInitiale = prixInitiale;
    }
    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }
    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }
    public void setCinAdmin(Integer cinAdmin) {
        this.cinAdmin = cinAdmin;
    }
    public void setValidite(boolean validite) {
        this.validite = validite;
    }
    
    public boolean ajout() {
        try {
            Connexion cnx = new Connexion();
            ResultSet rs = cnx.selectQuery("SELECT max(`ID_PRODUIT`) FROM `produit`;");
            if(rs == null)
                idProduit = 0;
            else {
                rs.next();
                idProduit = rs.getInt(1) + 1;
            }
            image = "NoPhoto.jpg";
            cnx.updateQuery("INSERT INTO `produit`(`ID_PRODUIT`, `ID_CATEGORIE`, `CIN_CLIENT`, `NOM_PRODUIT`, `DESCRIPTION`, `IMAGE`, `PRIX_INITIALE`, `DATE_DEBUT`, `DATE_FIN`, `CIN_ADMIN`, `VALIDITE`) "
                    + "VALUES("+ idProduit+ ", "+ idCategorie+ ", "+ cinClient+ ", '"+ nomProduit+ "', '"+ description+ "', '"+ image+ "', "+ prixInitiale+ ", '"+ sdf.format(dateDebut)+ "', '"+ sdf.format(dateFin)+ "', NULL, 0);");
            return true;
        } catch(SQLException e) {
            return false;
        }
    }
    public boolean majAdmin() {
        try {
            new Connexion().updateQuery("UPDATE `produit` "
                    + "SET `CIN_ADMIN`="+ cinAdmin+ ",`VALIDITE`="+ validite+ " "
                    + "WHERE `ID_PRODUIT`="+ idProduit+ ";");
            return true;
        } catch(SQLException e) {
            return false;
        }
    }
    public boolean majClient() {
        try {
            new Connexion().updateQuery("UPDATE `produit` "
                    + "SET `ID_CATEGORIE`="+ idCategorie+ ",`CIN_CLIENT`="+ cinClient+ ",`NOM_PRODUIT`='"+ nomProduit+ "',`DESCRIPTION`='"+ description+ "',`IMAGE`='"+ image+ "',`PRIX_INITIALE`="+ prixInitiale+ ",`DATE_DEBUT`='"+ sdf.format(dateDebut)+ "',`DATE_FIN`='"+ sdf.format(dateFin)+ "' "
                    + "WHERE `ID_PRODUIT`="+ idProduit+ ";");
            return true;
        } catch(SQLException e) {
            return false;
        }
    }
    public boolean suppression() {
        try {
            new Connexion().updateQuery("DELETE FROM `produit` WHERE `ID_PRODUIT`="+ idProduit+ ";");
            return true;
        } catch(SQLException e) {
            return false;
        }
    }
    
    public void rechercheProduit(Integer idProduit) {
        ResultSet rs = new Connexion().selectQuery("SELECT * FROM `produit` WHERE `ID_PRODUIT`="+ idProduit+ ";");
        if(rs != null) {
            try {
                rs.next();
                this.idProduit = rs.getInt(1);
                this.idCategorie = rs.getInt(2);
                this.cinClient = rs.getInt(3);
                this.nomProduit = rs.getString(4);
                this.description = rs.getString(5);
                this.image = rs.getString(6);
                this.prixInitiale = rs.getDouble(7);
                try {
                    this.dateDebut = sdf.parse(rs.getString(8));
                    this.dateFin = sdf.parse(rs.getString(9));
                } catch(ParseException e) {
                    this.dateDebut = rs.getDate(8);
                    this.dateFin = rs.getDate(9);
                }
                this.cinAdmin = rs.getInt(10);
                this.validite = rs.getBoolean(11);
            } catch(SQLException e) {
            }
        }
    }
    public static List<Produit> listeProduit() {
        ResultSet rs = new Connexion().selectQuery("SELECT * FROM `produit`;");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(rs == null)
            return null;
        else {
            try {
                List<Produit> liste = new ArrayList<>();
                while(rs.next()) {
                    try {
                        liste.add(new Produit(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getString(5),
                                rs.getString(6), rs.getDouble(7), sdf.parse(rs.getString(8)), sdf.parse(rs.getString(9)), rs.getInt(10), rs.getBoolean(11)));
                    } catch(ParseException e) {
                        liste.add(new Produit(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getString(5),
                                rs.getString(6), rs.getDouble(7), rs.getDate(8), rs.getDate(9), rs.getInt(10), rs.getBoolean(11)));
                    }
                }
                return liste;
            } catch(SQLException e) {
                return null;
            }
        }
    }
    public static List<Produit> listeProduit(int idCategorie) {
        ResultSet rs = new Connexion().selectQuery("SELECT * FROM `produit` WHERE `ID_CATEGORIE`="+ idCategorie+ ";");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(rs == null)
            return null;
        else {
            try {
                List<Produit> liste = new ArrayList<>();
                while(rs.next()) {
                    try {
                        liste.add(new Produit(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getString(5),
                                rs.getString(6), rs.getDouble(7), sdf.parse(rs.getString(8)), sdf.parse(rs.getString(9)), rs.getInt(10), rs.getBoolean(11)));
                    } catch(ParseException e) {
                        liste.add(new Produit(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getString(5),
                                rs.getString(6), rs.getDouble(7), rs.getDate(8), rs.getDate(9), rs.getInt(10), rs.getBoolean(11)));
                    }
                }
                return liste;
            } catch(SQLException e) {
                return null;
            }
        }
    }
    
    @Override
    public String toString() {
        return(nomProduit);
    }
}
