package Controller;

import Donnees.Categorie;
import Donnees.Client;
import Donnees.Produit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.FileUploadEvent;

@ManagedBean(name = "produitController")
@SessionScoped
public class ProduitController implements Serializable
{
    private List<Categorie> categories = null;
    private List<Client> clients = null;
    private List<Produit> filteredItems = null, items = null;
    private Produit nouveau, selected, valide, image;
    private final HttpSession session;
    
    public ProduitController() {
        session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    }
    
    public List<Categorie> getCategories() {
        if(categories == null)
            categories = Categorie.listeCategorie();
        return categories;
    }
    public List<Client> getClients() {
        if(clients == null)
            clients = Client.listeClient();
        return clients;
    }
    public List<Produit> getFilteredItems() {
        return filteredItems;
    }
    private void prepareListe() {
        items = Produit.listeProduit();
    }
    public void prepareListeSelonCategorie(String idCategorie) {
        try {
            items = Produit.listeProduit(Integer.parseInt(idCategorie));
            if(items == null)
                items = new ArrayList<Produit>();
        } catch(NumberFormatException e) {
            prepareListe();
        }
    }
    public List<Produit> getItems() {
        if (items == null)
            prepareListe();
        return items;
    }
    public Produit getNouveau() {
        if (nouveau == null)
            nouveau = new Produit();
        return nouveau;
    }
    public Produit getSelected() {
        if (selected == null)
            selected = new Produit();
        return selected;
    }
    public Produit getValide() {
        if (valide == null)
            valide = new Produit();
        return valide;
    }
    public Produit getImage() {
        if (image == null)
            image = new Produit();
        return image;
    }
    
    public void setFilteredItems(List<Produit> filteredItems) {
        this.filteredItems = filteredItems;
    }
    public void setNouveau(Produit nouveau) {
        this.nouveau = nouveau;
    }
    public void setSelected(Produit selected) {
        this.selected = selected;
    }
    public void setValide(Produit valide) {
        this.valide = valide;
    }
    public void setImage(Produit image) {
        this.image = image;
    }
    
    public void prepareAjout() {
        nouveau = new Produit();
    }
    public void ajout() {
        nouveau.setDateDebut(new Date());
        if(nouveau.ajout()== true) {
            prepareListe();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Ajout", "succes"));
        } else
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ajout", "erreur"));
    }
    public void maj(CellEditEvent event) {
        if(event.getRowIndex() != 5) {
            if(items.get(event.getRowIndex()).majClient()== true)
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mise a jour", "succes"));
            else
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mise a jour", "erreur"));
            prepareListe();
        }
    }
    public void destroy() {
        if(selected.suppression() == true) {
            prepareListe();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Suppression", "succes"));
        } else
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Suppression", "erreur"));
    }
    public void validation() {
        valide.setCinAdmin((int)session.getValue("cin"));
        valide.setValidite(true);
        if(valide.majAdmin()== true)
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Validation", "True"));
        else
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validation", "erreur"));
        prepareListe();
    }
    public void devalidation() {
        valide.setCinAdmin((int)session.getValue("cin"));
        valide.setValidite(false);
        if(valide.majAdmin()== true)
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Validation", "False"));
        else
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validation", "erreur"));
        prepareListe();
    }
    
    public void uploadFile(FileUploadEvent event) {
        try {
            try(InputStream input = event.getFile().getInputstream()) {
                File directory = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/ImageProduits"));
                directory.mkdirs();
                try(OutputStream output = new FileOutputStream(new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/ImageProduits")+ "/"+ event.getFile().getFileName()))) {
                    int data;
                    while((data = input.read()) != -1)
                        output.write(data);
                }
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded."));
            if(image.getImage().equals("NoPhoto.jpg")) {
                File file = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/ImageProduits")+ "/"+ image.getImage());
                file.delete();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Succesful", image.getImage() + " is deleted."));
            }
            image.setImage(event.getFile().getFileName());
            if(image.majClient() == true)
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mise a jour", "succes"));
            else
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mise a jour", "erreur"));
            prepareListe();
	} catch(FileNotFoundException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Upload", "File Not Found"));
        } catch(IOException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Upload", "Erruer"));
        }
    }
}
