/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mbeans;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import model.Booking;
import model.BookingFacade;
import model.Guest;
import model.Payment;
import model.PaymentFacade;
import model.Room;
import model.RoomFacade;
import model.Useracc;
import model.UseraccFacade;
import org.primefaces.context.RequestContext;

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
        System.out.println("Room status updated");
    }

    public void checkOut(double pay, Payment p) {
//        if(makePayment(pay,p)){
        updatePayment(p);
        // }
        updateRoomstatus(p);

        init();

        receipt(p);
    }

    public void receipt(Payment p) {
//        RequestContext context = RequestContext.getCurrentInstance();
//        context.execute("window.open('../controller/receipt', '_newtab')");

        FacesContext fc = FacesContext.getCurrentInstance();
        ServletContext sc = (ServletContext) fc.getExternalContext().getContext();
        try {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(ec.getRequestContextPath() + "/receipt");

        } catch (IOException ex) {
            Logger.getLogger(checkOutBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//        req.getSession();
        HttpSession s = req.getSession();
        s.setAttribute("payment", p);

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
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Customer checkout successfully", ""));
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
        for (Payment p : payments) {
            if (p.getStatus().equalsIgnoreCase("Due")) {
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
