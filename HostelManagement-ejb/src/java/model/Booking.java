/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author KhaiKhai
 */
@Entity
public class Booking implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    
    @OneToOne
    private Guest bGuest;
    @OneToOne
    private Useracc staff;
    
    
    
    @OneToOne
    private Room bRoom;
    
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date firstNight;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date lastNight;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date bookDate;
    

    public Booking() {
        
    }

    public Booking(Guest bGuest, Useracc staff, Room bRoom, Date fNight, Date lNight) {
        this.bookDate = new Date();
        this.bGuest = bGuest;
        this.staff = staff;
        this.bRoom = bRoom;
        this.firstNight = fNight;
        this.lastNight = lNight;
    }

    public Room getbRoom() {
        return bRoom;
    }

    public void setbRoom(Room bRoom) {
        this.bRoom = bRoom;
    }

    public Guest getbGuest() {
        return bGuest;
    }

    public void setbGuest(Guest bGuest) {
        this.bGuest = bGuest;
    }

    public Date getfNight() {
        
        return firstNight;
    }

    public void setfNight(Date fNight) {
        this.firstNight = fNight;
    }

    public Date getlNight() {
        return lastNight;
    }

    public void setlNight(Date lNight) {
        this.lastNight = lNight;
    }

    public Date getFirstNight() {
        return firstNight;
    }

    public void setFirstNight(Date firstNight) {
        this.firstNight = firstNight;
    }

    public Date getLastNight() {
        return lastNight;
    }

    public void setLastNight(Date lastNight) {
        this.lastNight = lastNight;
    }

    

    public Useracc getStaff() {
        return staff;
    }

    public void setStaff(Useracc staff) {
        this.staff = staff;
    }
    
    
    
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Booking)) {
            return false;
        }
        Booking other = (Booking) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Booking[ id=" + id + " ]";
    }
    
}
