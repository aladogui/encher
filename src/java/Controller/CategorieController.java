package Controller;

import Donnees.Categorie;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.CellEditEvent;

@ManagedBean(name = "categorieController")
@SessionScoped
public class CategorieController implements Serializable
{
    private List<Categorie> filteredItems = null, items = null;
    private Categorie nouveau, selected;
    
    public CategorieController() {
    }
    
    public List<Categorie> getFilteredItems() {
        return filteredItems;
    }
    private void prepareListe() {
        items = Categorie.listeCategorie();
    }
    public List<Categorie> getItems() {
        if (items == null)
            prepareListe();
        return items;
    }
    public Categorie getNouveau() {
        if (nouveau == null)
            nouveau = new Categorie();
        return nouveau;
    }
    public Categorie getSelected() {
        if (selected == null)
            selected = new Categorie();
        return selected;
    }
    
    public void setFilteredItems(List<Categorie> filteredItems) {
        this.filteredItems = filteredItems;
    }
    public void setNouveau(Categorie nouveau) {
        this.nouveau = nouveau;
    }
    public void setSelected(Categorie selected) {
        this.selected = selected;
    }
    
    public void prepareAjout() {
        nouveau = new Categorie();
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
}
