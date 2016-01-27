package Controller;

import Donnees.Encherir;
import Donnees.Produit;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.event.CellEditEvent;

@ManagedBean(name = "encherirController")
@SessionScoped
public class EncherirController implements Serializable
{
    private List<Encherir> filteredItems = null, items = null;
    private Encherir nouveau, selected;
    private HttpSession session;
    
    public EncherirController() {
    }
    
    public List<Encherir> getFilteredItems() {
        return filteredItems;
    }
    private void prepareListe() {
        items = Encherir.listeEncherir();
    }
    public List<Encherir> getItems() {
        if (items == null)
            prepareListe();
        return items;
    }
    public Encherir getNouveau() {
        if (nouveau == null)
            nouveau = new Encherir();
        return nouveau;
    }
    public Encherir getSelected() {
        if (selected == null)
            selected = new Encherir();
        return selected;
    }
    
    public void setFilteredItems(List<Encherir> filteredItems) {
        this.filteredItems = filteredItems;
    }
    public void setNouveau(Encherir nouveau) {
        this.nouveau = nouveau;
    }
    public void setSelected(Encherir selected) {
        this.selected = selected;
    }
    
    public void prepareAjout() {
        nouveau = new Encherir();
    }
    public void ajout() {
        if(nouveau.ajout()== true) {
            prepareListe();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Ajout", "succes"));
        } else
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ajout", "erreur"));
    }
    public void maj(CellEditEvent event) {
        if(items.get(event.getRowIndex()).maj()== true)
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mise a jour", "succes"));
        else
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mise a jour", "erreur"));
        prepareListe();
    }
    public void destroy() {
        if(selected.suppression() == true) {
            prepareListe();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Suppression", "succes"));
        } else
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Suppression", "erreur"));
    }
    
    public double getLastPrix(Produit produit) {
        selected = new Encherir();
        selected.rechercheLastEncherir(produit.getIdProduit());
        Double prix;
        if(selected.getPrix() == 0)
            prix = produit.getPrixInitiale();
        else
            prix = selected.getPrix();
        return prix;
    }
    
    public String goToEnchere(Integer idProduit) {
        selected = new Encherir();
        selected.setIdProduit(idProduit);
        session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        selected.setCin((Integer)session.getAttribute("cin"));
        return "client_encher";
    }
}
