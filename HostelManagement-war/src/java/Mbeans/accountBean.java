/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mbeans;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import model.Useracc;
import model.UseraccFacade;
import org.primefaces.context.RequestContext;

/**
 *
 * @author KhaiKhai
 */
@ManagedBean(name = "accountBean")
@RequestScoped
public class accountBean implements Serializable {

    @EJB
    private UseraccFacade useraccFacade;
    List<Useracc> ul;

    @ManagedProperty("#{loginBean.username}")
    private String username;
    private loginBean lb;

    @PostConstruct
    public void init() {
        System.out.println("acc bean called");
        username = (String) (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("username"));

        System.out.println(username + " from accoutnbean");
    }

//    LoginBean loginBean = (LoginBean)((HttpServletRequest)request).getSession().getAttribute("loginBean");
    private String name;
    private String password;
    private String email;
    private String department;
    private String phoneNo;
    private String IC;
    private String address;
    private Useracc loggedUser;

    public accountBean(String name, String username, String password, String email, String department, String phoneNo, String IC, String address) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.email = email;
        this.department = department;
        this.phoneNo = phoneNo;
        this.IC = IC;
        this.address = address;

    }

    public loginBean getLb() {
        return lb;
    }

    public void setLb(loginBean lb) {
        this.lb = lb;
    }

    public Useracc getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(Useracc loggedUser) {
//        this.loggedUser = loggedUser;
        this.loggedUser = loggedUser;
        System.out.println("login bean user injected");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getIC() {
        return IC;
    }

    public void setIC(String IC) {
        this.IC = IC;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean checkUsername(String username) {
        ul = useraccFacade.findAll();
        for (int i = 0; i < ul.size(); i++) {
            if (username.equalsIgnoreCase(ul.get(i).getUsername())) {
                return false;//username found
            }
        }
        return true;
    }

    public String register() {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        String redirect = "index.xhtml?faces-redirect=true";
        if (checkUsername(username)) {
            if (!department.equals("-")) {
                Useracc a = new Useracc(name, username, password, email, department, phoneNo, IC, address);
                useraccFacade.create(a);

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("User Registered"));

            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Please Select a Department."));
                redirect = null;
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Username Exist", "Username Exist"));
            redirect = null;
        }
        return redirect;
    }

    public String registerStaff() {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        String redirect = "/manager/home.xhtml?faces-redirect=true";
        if (checkUsername(username)) {
            if (!department.equals("-")) {
                Useracc a = new Useracc(name, username, password, email, department, phoneNo, IC, address);
                useraccFacade.create(a);

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("User Registered"));

            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Please Select a Department."));
                redirect = null;
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Username Exist", "Username Exist"));
            redirect = null;
        }
        return redirect;
        
    }
   
    public void reset() {
        RequestContext.getCurrentInstance().reset("regForm:registerGrid");
    }
    
    /**
     * Creates a new instance of accountBean
     */
    public accountBean() {

    }

}
