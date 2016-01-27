package Controller;

import Donnees.Administrateur;
import Donnees.Client;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "authentifier")
@SessionScoped
public class Authentifier implements Serializable
{
    private String mail, password;
    private HttpSession session;
    
    public Authentifier() {
    }
    
    public String getMail() {
        return mail;
    }
    public String getPassword() {
        return password;
    }
    public String getSession() {
        session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        return (String)session.getAttribute("nom");
    }
    
    public void setMail(String mail) {
        this.mail = mail;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String logIn() {
        session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        Client client = Client.rechercheClient(mail, password);
        if(client != null) {
            session.setAttribute("cin", client.getCin());
            session.setAttribute("nom", client.getNom()+ " "+ client.getPrenom());
            return "client_index";
        }
        Administrateur admin = Administrateur.rechercheAdministrateur(mail, password);
        if(admin != null) {
            session.setAttribute("cin", admin.getCin());
            session.setAttribute("nom", admin.getNom()+ " "+ admin.getPrenom());
            return "administrateur_index";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Erreur", "Login/Mot de passe: incorrect"));
        return null;
    }
    public String logOut() {
        FacesContext context = FacesContext.getCurrentInstance();
        ((HttpSession)context.getExternalContext().getSession(true)).invalidate();
        String page;
        try {
            page = "/login?logout=true&faces-redirect=true";
            ((HttpServletRequest)context.getExternalContext().getRequest()).logout();
        } catch(ServletException e) {
            page = "/login?logout=false&faces-redirect=true";
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "LogOut", "Erreur"));
        }
        return page;
    }
}
