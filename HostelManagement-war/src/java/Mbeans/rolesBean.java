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
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import model.Useracc;
import model.UseraccFacade;
import org.primefaces.event.CellEditEvent;

/**
 *
 * @author KhaiKhai
 */
@ManagedBean(name = "rolesBean")
@ViewScoped
public class rolesBean implements Serializable {

    private List<Useracc> acc;
    private Useracc selectedAcc;

    @EJB
    private UseraccFacade useraccFacade;

    @PostConstruct
    public void init() {
        System.out.println("acc database called");
        acc = useraccFacade.findAll();
    }

    public UseraccFacade getUseraccFacade() {
        return useraccFacade;
    }

    public void setUseraccFacade(UseraccFacade useraccFacade) {
        this.useraccFacade = useraccFacade;
    }

    public List<Useracc> getAcc() {
        return acc;
    }

    public void setAcc(List<Useracc> acc) {
        this.acc = acc;
    }

    public Useracc getSelectedAcc() {
        return selectedAcc;
    }

    /**
     * Creates a new instance of rolesBean
     */
    public void setSelectedAcc(Useracc selectedAcc) {
        this.selectedAcc = selectedAcc;
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {
            //FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            //FacesContext.getCurrentInstance().getex.addMessage(null, msg);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue));
            //selectedAcc = useraccFacade.find(oldValue);
            FacesContext context= FacesContext.getCurrentInstance();
            Useracc u = context.getApplication().evaluateExpressionGet(context, "#{acc}", Useracc.class);
            useraccFacade.edit(u);
        }
    }

}
