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
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import model.Booking;
import model.BookingFacade;
import model.Guest;
import model.Room;
import model.RoomFacade;

/**
 *
 * @author kj
 */
@ManagedBean(name = "checkBean")
@RequestScoped
public class checkBean {

    @EJB
    private RoomFacade roomFacade;

    @EJB
    private BookingFacade bookingFacade;

    private Guest checkInGuest;
    private List<Booking> bookings;
    private List<Booking> temp;
    
    private Room checkInRoom;
    private Booking booking;
    final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    final LocalDate dt;

    @PostConstruct
    public void init() {
        bookings = bookingFacade.findAll();
        System.out.println("refresh checkBean");
    }

    public void checkIn(Guest cig) {

        setCheckInGuest(cig);
        System.out.println(checkInGuest.getName());
        if (checkBookingCus(cig)) {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Choose booking record.", ""));

        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "No Booking record found with this customer.", ""));
        }
    }

    public Boolean checkBookingCus(Guest cig) {
        Boolean found = false;

        for (Booking b : bookings) {
            if (checkInGuest.equals(b.getbGuest()) && checkBookingTime(b)) {//check booking with name
                System.out.println("Booking record found");
                found = true;
                temp.add(b);
            }
        }
        return found;
    }

    public Boolean checkBookingTime(Booking b) {//check timing
        Boolean found = false;
//        for (Booking B : b) {
        LocalDate checkIn = new java.sql.Date(b.getfNight().getTime()).toLocalDate();
        System.out.println(checkIn);
        System.out.println(dt);
        if (checkIn.compareTo(dt) == 0) {

            found = true;
        }
//        }
        return found;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Guest getCheckInGuest() {
        return checkInGuest;
    }

    public void setCheckInGuest(Guest checkInGuest) {
        this.checkInGuest = checkInGuest;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public Room getCheckInRoom() {
        return checkInRoom;
    }

    public void setCheckInRoom(Room checkInRoom) {
        this.checkInRoom = checkInRoom;
    }

    public List<Booking> getTemp() {
        return temp;
    }

    public void setTemp(List<Booking> temp) {
        this.temp = temp;
    }

    /**
     * Creates a new instance of checkBean
     */
    public checkBean() {
        this.dt = LocalDate.parse("01/10/2017", dtf);
    }

}
