/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mbeans;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;
import model.Useracc;
import model.UseraccFacade;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author KhaiKhai
 */
@ManagedBean(name = "loginBean", eager = true)
@SessionScoped
public class loginBean implements Serializable {

    @EJB
    private UseraccFacade useraccFacade;

    List<Useracc> ul;
    private String username;
    private String password;
    private Useracc loguser;

    public Useracc getLoguser() {

        return loguser;
    }

    public void setLoguser(Useracc loguser) {
        this.loguser = loguser;
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

    }

     public String logout() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        HttpSession session = (HttpSession) ec.getSession(false);
        HttpServletResponse response = (HttpServletResponse) ec.getResponse();
         System.out.println("Printing session map: ");
        System.out.print(ec.getSessionMap());
        System.out.println("invalidating session");
        session.invalidate();

        return "/index.jsf?faces-redirect=true";
    }

     
     public String checkAcctype(Useracc loguser){
        char uType;
        String url="";
        uType = loguser.getDepartment().charAt(0);
         System.out.println(uType);
        if(uType=='m' || uType=='M'){
            url = "/manager/home.xhtml?faces-redirect=true";
        }
        if (uType=='r'||uType=='R'){
            url = "reservation/home.xhtml?faces-redirect=true";
        }
        return url;
     }
    
    public String login() {
        Useracc log = null;
        String redirect = null;
        ul = useraccFacade.findAll();
        for (int i = 0; i < ul.size(); i++) {
            if (username.equals(ul.get(i).getUsername())) {
                log = ul.get(i);
            }
        }
        if (log != null) {

            if (password.equals(log.getPassword())) {
            
                FacesContext context = FacesContext.getCurrentInstance();

                context.getExternalContext().getSessionMap().put("username", username);
                System.out.println(log.getUsername() + " logged in");
                this.loguser = log;
                //manager case
                redirect = checkAcctype(log);

            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Password is Incorrect."));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Invalid Username."));
        }
        return redirect;
    }

    public boolean isLoggedIn() {

        return loguser != null;

    }

   
}
