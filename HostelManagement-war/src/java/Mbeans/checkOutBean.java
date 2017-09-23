/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mbeans;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import model.Booking;
import model.BookingFacade;
import model.Guest;
import model.Payment;
import model.PaymentFacade;
import model.Room;
import model.RoomFacade;
import model.Useracc;
import model.UseraccFacade;

/**
 *
 * @author kj
 */
@ManagedBean(name = "checkOutBean")
@ViewScoped
public class checkOutBean {

    @EJB
    private UseraccFacade useraccFacade;

    @EJB
    private PaymentFacade paymentFacade;

    @EJB
    private RoomFacade roomFacade;

    private Guest checkInGuest;
    private List<Payment> payments;
    private List<Payment> filteredPayment;

    private String staffUser;
    private Room checkInRoom;
    private Payment payment;
    private double paid;

    @PostConstruct
    public void init() {
        payments = paymentFacade.findAll();

        System.out.println("refresh checkOutBean");
    }

    public void updateRoomstatus(Payment p) {
        Long no = p.getRoom().getId();
        List<Room> r = roomFacade.findAll();
        for (Room room : r) {
            if (Objects.equals(room.getId(), no)) {
                checkInRoom = room;
            }
        }
        checkInRoom.setStatus("To be cleaned");
        roomFacade.edit(checkInRoom);
    }

    public void checkOut(double pay, Payment p) {
//        if(makePayment(pay,p)){
        updatePayment(p);
        // }
//        updateRoomstatus(p);
    }

    public Boolean makePayment(double pay, Payment p) {
        System.out.println("makepayment called");
        Boolean isPaid = false;
        if (pay >= p.getPrice()) {
            isPaid = true;
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    "Insufficient Amount Paid.", "Please pay for the amount RM " + p.getPrice()));
        }
        return isPaid;
    }

    public void updatePayment(Payment p) {
        System.out.println("updatePayemnt called");
        Payment temp = paymentFacade.find(p.getId());
        temp.setStatus("Paid");
        paymentFacade.edit(temp);
    }

    public void sendReceipt() {

    }

    public Guest getCheckInGuest() {
        return checkInGuest;
    }

    public void setCheckInGuest(Guest checkInGuest) {
        this.checkInGuest = checkInGuest;
    }

    public Room getCheckInRoom() {
        return checkInRoom;
    }

    public void setCheckInRoom(Room checkInRoom) {
        this.checkInRoom = checkInRoom;
    }

    public List<Payment> getPayments() {
        List<Payment> temp = new ArrayList();
        for(Payment p:payments){
            if(p.getStatus().equalsIgnoreCase("Due")){
                temp.add(p);
            }
        }
        return temp;
    }

    public double getPaid() {
        return paid;
    }

    public void setPaid(double paid) {
        this.paid = paid;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }


    public String getStaffUser() {
        return staffUser;
    }

    public void setStaffUser(String staffUser) {
        this.staffUser = staffUser;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public List<Payment> getFilteredPayment() {
        return filteredPayment;
    }

    public void setFilteredPayment(List<Payment> filteredPayment) {
        this.filteredPayment = filteredPayment;
    }

    /**
     * Creates a new instance of checkBean
     */
    public checkOutBean() {
    }

}
