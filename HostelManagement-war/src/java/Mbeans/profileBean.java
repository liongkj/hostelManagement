/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mbeans;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import static javax.faces.application.FacesMessage.SEVERITY_INFO;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import model.Useracc;
import model.UseraccFacade;

/**
 *
 * @author KhaiKhai
 */
@Named(value = "profileBean")
@RequestScoped
public class profileBean {

    @EJB
    private UseraccFacade useraccFacade;

    @ManagedProperty("#{loginBean.username}")
    private String username;

    private String name;
    private String password;
    private String email;
    private String department;
    private String phoneNo;
    private String IC;
    private String address;
    private Useracc loggedUser;
    List<Useracc> ul;

    /**
     * Creates a new instance of profileBean
     */
    public profileBean() {
    }

    @PostConstruct
    public void init() {
        System.out.println("profile bean called");
        String user = (String) (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("username"));
        Useracc log = null;

        ul = useraccFacade.findAll();
        for (int i = 0; i < ul.size(); i++) {
            if (user.equals(ul.get(i).getUsername())) {
                log = ul.get(i);
            }
        }

        if (log != null) {
            loggedUser = log;
        }

    }

    public profileBean(String username, String name, String password, String email, String department, String phoneNo, String IC, String address, Useracc loggedUser) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.email = email;
        this.department = department;
        this.phoneNo = phoneNo;
        this.IC = IC;
        this.address = address;
        this.loggedUser = loggedUser;
    }

    public UseraccFacade getUseraccFacade() {
        return useraccFacade;
    }

    public void setUseraccFacade(UseraccFacade useraccFacade) {
        this.useraccFacade = useraccFacade;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Useracc getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(Useracc loggedUser) {
        this.loggedUser = loggedUser;
    }

    public void saveChanges() {
        System.out.println("Account changed");
        loggedUser.setAddress(loggedUser.getAddress());
        loggedUser.setDepartment(loggedUser.getDepartment());
        loggedUser.setEmail(loggedUser.getEmail());
        loggedUser.setIC(loggedUser.getIC());
        loggedUser.setName(loggedUser.getName());
        loggedUser.setPassword(loggedUser.getPassword());
        loggedUser.setPhoneNo(loggedUser.getPhoneNo());
        loggedUser.setDepartment(loggedUser.getDepartment());
        
        useraccFacade.edit(loggedUser);
        FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(SEVERITY_INFO , "Changes are made to User "+ this.loggedUser.getUsername()+".", ""));
    }

}
