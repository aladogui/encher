package Controller;

import Donnees.Administrateur;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.CellEditEvent;

@ManagedBean(name = "administrateurController")
@SessionScoped
public class AdministrateurController implements Serializable
{
    private List<Administrateur> filteredItems = null, items = null;
    private Administrateur nouveau, selected;
    
    public AdministrateurController() {
    }
    
    public List<Administrateur> getFilteredItems() {
        return filteredItems;
    }
    private void prepareListe() {
        items = Administrateur.listeAdministrateur();
    }
    public List<Administrateur> getItems() {
        if (items == null)
            prepareListe();
        return items;
    }
    public Administrateur getNouveau() {
        if (nouveau == null)
            nouveau = new Administrateur();
        return nouveau;
    }
    public Administrateur getSelected() {
        if (selected == null)
            selected = new Administrateur();
        return selected;
    }
    
    public void setFilteredItems(List<Administrateur> filteredItems) {
        this.filteredItems = filteredItems;
    }
    public void setNouveau(Administrateur nouveau) {
        this.nouveau = nouveau;
    }
    public void setSelected(Administrateur selected) {
        this.selected = selected;
    }
    
    public void prepareAjout() {
        nouveau = new Administrateur();
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
