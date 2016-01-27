package Controller;

import Donnees.Client;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.CellEditEvent;

@ManagedBean(name = "clientController")
@SessionScoped
public class ClientController implements Serializable
{
    private List<Client> filteredItems = null, items = null;
    private Client nouveau, selected;
    
    public ClientController() {
    }
    
    public List<Client> getFilteredItems() {
        return filteredItems;
    }
    private void prepareListe() {
        items = Client.listeClient();
    }
    public List<Client> getItems() {
        if (items == null)
            prepareListe();
        return items;
    }
    public Client getNouveau() {
        if (nouveau == null)
            nouveau = new Client();
        return nouveau;
    }
    public Client getSelected() {
        if (selected == null)
            selected = new Client();
        return selected;
    }
    
    public void setFilteredItems(List<Client> filteredItems) {
        this.filteredItems = filteredItems;
    }
    public void setNouveau(Client nouveau) {
        this.nouveau = nouveau;
    }
    public void setSelected(Client selected) {
        this.selected = selected;
    }
    
    public void prepareAjout() {
        nouveau = new Client();
    }
    public void ajout() {
        if(nouveau.ajout() == true) {
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
    
    public String registration() {
        if(nouveau.ajout() == true)
            return "login";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Registration", "erreur"));
        return null;
    }
}
