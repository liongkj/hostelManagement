/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import model.Guest;
import model.GuestFacade;
import model.Useracc;
import model.UseraccFacade;
import org.primefaces.context.RequestContext;

/**
 *
 * @author KhaiKhai
 */
@ManagedBean(name="customerBean")
@ViewScoped
public class customerBean implements Serializable {

    @EJB
    private UseraccFacade useraccFacade;

    @EJB
    private GuestFacade guestFacade;

    @ManagedProperty("#{loginBean.username}")
    private String username;

    private List<Guest> gl;
    private List<Useracc> ul;
    private List<Integer> ageList;

//    LoginBean loginBean = (LoginBean)((HttpServletRequest)request).getSession().getAttribute("loginBean");
    private String name;
    private String cusID;
    private String email;
    private String IC;
    private String phone;
    private String address;
    private int age;
    private Useracc staff;
    private List<Guest> filteredCus;
    private Guest selectedGuest;
    
    @PostConstruct
    public void init() {
        username = (String) (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("username"));
        ageList = populateAge();
        gl = guestFacade.findAll();
        selectedGuest=null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public customerBean(String name, String cusID, String email, String IC, String phone, String address, int age) {
        this.name = name;
        this.cusID = cusID;
        this.email = email;
        this.IC = IC;
        this.phone = phone;
        this.address = address;
        this.age = age;
    }

    public List<Integer> populateAge() {
        List<Integer> ages = new ArrayList<>();
        for (int i = 18; i <= 80; i++) {
            ages.add(i);
        }
        return ages;
    }

    public void setStaff(String username) {
        ul = useraccFacade.findAll();
        for (int i = 0; i < ul.size(); i++) {
            if (username.equals(ul.get(i).getUsername())) {
                this.staff = ul.get(i);
            }
        }
    }

    public boolean checkName(String name) {
        gl = guestFacade.findAll();
        for (int i = 0; i < gl.size(); i++) {
            if (name.equalsIgnoreCase(gl.get(i).getName())) {
                return false;//username found
            }
        }
        return true;
    }

    public void editCus(Guest s){
        System.out.println(s.getName());
//        selectedGuest.setAddress(selectedGuest.getAddress());
//        selectedGuest.setAge(selectedGuest.getAge());
//        selectedGuest.setPhone(selectedGuest.getPhone());
//        selectedGuest.setEmail(selectedGuest.getEmail());
//        selectedGuest.setIC(selectedGuest.getIC());
//        selectedGuest.setName(selectedGuest.getName());
//        
        guestFacade.edit(selectedGuest);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage
        (FacesMessage.SEVERITY_INFO, "Customer Profile updated", ""));
        init();
    }
    
    public String register() {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        String redirect = "index.xhtml?faces-redirect=true";
        if (checkName(name)) {
            //maybe validate IC
            setStaff(username);
            Guest g = new Guest(name, cusID, email, IC, phone, address, age, staff);
            guestFacade.create(g);
            System.out.println(staff.getUsername());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Customer Registered"));

        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Customer Exist", "Customer Exist"));
            redirect = null;
        }
        return null;
    }

    public void reset() {
        RequestContext.getCurrentInstance().reset("regForm:registerGrid");
    }

    public List<Integer> getAgeList() {
        return ageList;
    }

    public void setAgeList(List<Integer> ageList) {

        this.ageList = ageList;
    }

    public GuestFacade getGuestFacade() {
        return guestFacade;
    }

    public void setGuestFacade(GuestFacade guestFacade) {
        this.guestFacade = guestFacade;
    }

    public Useracc getStaff() {
        return staff;
    }

    public List<Guest> getGl() {
        return gl;
    }

    public void setGl(List<Guest> gl) {
        this.gl = gl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getCusID() {
        return cusID;
    }

    public void setCusID(String cusID) {
        this.cusID = cusID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIC() {
        return IC;
    }

    public Guest getSelectedGuest() {
        return selectedGuest;
    }

    public void setSelectedGuest(Guest selectedGuest) {
        this.selectedGuest = selectedGuest;
    }

    
    
    public List<Guest> getFilteredCus() {
        return filteredCus;
    }

    public void setFilteredCus(List<Guest> filteredCus) {
        this.filteredCus = filteredCus;
    }

    public void setIC(String IC) {
        this.IC = IC;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Creates a new instance of accountBean
     */
    public customerBean() {

    }

}
