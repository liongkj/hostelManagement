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
@ManagedBean(name = "checkBean")
@RequestScoped
public class checkBean {

    @EJB
    private UseraccFacade useraccFacade;

    @EJB
    private PaymentFacade paymentFacade;

    @EJB
    private RoomFacade roomFacade;
    
    @EJB
    private BookingFacade bookingFacade;
    
    private Guest checkInGuest;
    private List<Booking> bookings;
    private List<Booking> temp;
    
    private String staffUser;
    private Useracc staff;
    private Room checkInRoom;
    private Booking booking;
    final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    final LocalDate dt;

    @PostConstruct
    public void init() {
        bookings = bookingFacade.findAll();
        System.out.println("refresh checkBean");
        staffUser = (String) (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("username"));
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
        List<Booking> book = new ArrayList();
        for (Booking b : bookings) {
            if (checkInGuest.equals(b.getbGuest()) && checkBookingTime(b)) {//check booking with name
                System.out.println("Booking record found");
                found = true;
                book.add(b);
            }
            setBookings(book);
        }
        return found;
    }

    public Boolean checkBookingTime(Booking b) {//check timing - disable button?
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

    public Boolean isTime(Booking book){
       LocalDate checkIn = new java.sql.Date(book.getFirstNight().getTime()).toLocalDate();
       return !(checkIn.compareTo(dt)==0);
    }
    
    public void confirmCheckIn(Booking book){
        setStaff(staffUser);
        setCheckInGuest(book.getbGuest());
        Payment p =new Payment(book, staff, checkInGuest);
        System.out.println(book.getId()+ " checked In");
        bookingFacade.remove(book);
//        paymentFacade.create(p);
        updateRoomstatus(book);
    }
    
    public void updateRoomstatus(Booking b){
        Long no = b.getbRoom().getId();
        List<Room> r = roomFacade.findAll();
        for (Room room:r){
            if(Objects.equals(room.getId(), no)){
                checkInRoom = room;
            }
        }
        
        checkInRoom.setStatus('O');
        roomFacade.edit(checkInRoom);
    }
    
    public void setStaff(String username) {
        List<Useracc> ul;
        ul = useraccFacade.findAll();
        for (int i = 0; i < ul.size(); i++) {
            if (username.equals(ul.get(i).getUsername())) {
                this.staff = ul.get(i);
            }
        }
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
        this.dt = LocalDate.parse("04/10/2017", dtf);
    }

}
