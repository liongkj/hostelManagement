/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import model.Guest;
import org.primefaces.event.FlowEvent;

@ManagedBean
@ViewScoped
public class bookingBean implements Serializable {

    private Guest user = new Guest();
    private String name;
    private boolean skip;

    public Guest getUser() {
        return user;
    }

    public void setUser(Guest user) {
        this.user = user;
    }

    public void save() {
        FacesMessage msg = new FacesMessage("Successful", "Welcome :");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public String onFlowProcess(FlowEvent event) {
        if (skip) {
            skip = false;   //reset in case user goes back
            return "confirm";
        } else {
            return event.getNewStep();
        }
    }

    public char getThemeGroup(Guest theme) {
        return 'g';
//                theme.getDisplayName().charAt(0);
    }

    public List<Guest> completeThemeContains(String query) {
        List<Guest> allThemes = new ArrayList<Guest>();
//                service.getThemes();
//		List<Theme> filteredThemes = new ArrayList<Theme>();
//        
//        for (int i = 0; i < allThemes.size(); i++) {
//            Theme skin = allThemes.get(i);
//            if(skin.getName().toLowerCase().contains(query)) {
//                filteredThemes.add(skin);

        return allThemes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
