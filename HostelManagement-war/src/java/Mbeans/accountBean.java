/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mbeans;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import model.Useracc;
import model.UseraccFacade;

/**
 *
 * @author KhaiKhai
 */
@Named(value = "accountBean")
@SessionScoped
public class accountBean implements Serializable {

    @EJB
    private UseraccFacade useraccFacade;

    List<Useracc> ul;

    private String username;
    private String password;
    private String email;
    private String department;
    private String phoneNo;
    private String IC;
    private String address;

    public accountBean(String username, String password, String email, String department, String phoneNo, String IC, String address) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.department = department;
        this.phoneNo = phoneNo;
        this.IC = IC;
        this.address = address;
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
                Useracc a = new Useracc(username, password, email, department, phoneNo, IC, address);
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

    /**
     * Creates a new instance of accountBean
     */
    public accountBean() {
    }

}
