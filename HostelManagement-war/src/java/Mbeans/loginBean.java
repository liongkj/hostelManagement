/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mbeans;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import model.Useracc;
import model.UseraccFacade;

/**
 *
 * @author KhaiKhai
 */
@Named(value = "loginBean")
@ApplicationScoped
public class loginBean {

    @EJB
    private UseraccFacade useraccFacade;
    List<Useracc> ul;
    private String username;
    private String password;

    Useracc loguser = null;

    public String login() {
        String redirect = "";
        ul = useraccFacade.findAll();
        for (int i = 0; i < ul.size(); i++) {
            if (username.equals(ul.get(i).getUsername())) {
                loguser = ul.get(i);
            }
        }

        if (loguser != null) {
            if (password.equals(loguser.getPassword())) {
                FacesContext facesContext = FacesContext.getCurrentInstance();
                HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
                session.setAttribute("username", username);
                return "home.xhtml?faces-redirect=true";
            } else {
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "Incorrect Passowrd",
                                "Please enter correct username and Password"));
                redirect = "index.xhtml?faces-redirect=true";
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Invalid username",
                            "Please enter correct username and Password"));
            redirect = "index.xhtml?faces-redirect=true";
        }
        return redirect;
    }

    public String logout(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index.xhtml?faces-redirect=true";
    }
    
    public loginBean(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Creates a new instance of loginMbeans
     */
    public loginBean() {

        System.out.println("Login Bean called");
    }

}
