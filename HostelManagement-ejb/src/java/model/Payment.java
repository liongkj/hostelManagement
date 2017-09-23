/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author KhaiKhai
 */
@Entity
public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Useracc staff;
    @OneToOne
    private Guest guest;
    @OneToOne
    private Room room;
    private int price;
    private String status;

    public Payment() {
    }

    public Payment(Booking book, Useracc staff, Guest guest) {
        this.staff = staff;
        this.guest = guest;
        this.status = "Due";
        this.room = book.getbRoom();
        setPrice(book);
    }
    
    public final void setPrice(Booking b) {
        long diff =  b.getLastNight().getTime()-b.getFirstNight().getTime();
        float day = (diff / (1000*60*60*24));
        this.price = (int) day * room.getPRICE();
    }
    
    public Useracc getStaff() {
        return staff;
    }

    public void setStaff(Useracc staff) {
        this.staff = staff;
    }

    public String getStatus() {
        return status;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public int getPrice() {
        return price;
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
        if (!(object instanceof Payment)) {
            return false;
        }
        Payment other = (Payment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Payment[ id=" + id + " ]";
    }
    
}
