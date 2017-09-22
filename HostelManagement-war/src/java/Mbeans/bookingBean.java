/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mbeans;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import model.Booking;
import model.BookingFacade;
import model.Guest;
import model.GuestFacade;
import model.Room;
import model.RoomFacade;
import model.Useracc;
import model.UseraccFacade;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author KhaiKhai
 */
@ManagedBean
@ViewScoped
public class bookingBean implements Serializable {

    @EJB
    private UseraccFacade useraccFacade;

    @EJB
    private RoomFacade roomFacade;

    @EJB
    private BookingFacade bookingFacade;

    @EJB
    private GuestFacade guestFacade;

    private Guest name;
    private String cusName;
    private String id;
    private String staffUser;
    private Useracc staff;
    private Room selectedRoom;
    private Booking booking;

    private List<Guest> guestList;
    private List<Room> rooms;
    private List<Booking> bookings;

    private Date firstNight, lastNight, min;
    private final DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Creates a new instance of bookingBean
     */
    @PostConstruct
    public void init() {
        guestList = guestFacade.findAll();
        staffUser = (String) (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("username"));
    }

    public void changeUser(ValueChangeEvent e) {

        id = e.getNewValue().toString();

        System.out.println("Selected user is " + name);
    }


    public void listBookings() {

        bookings = bookingFacade.findAll();
        List<Room> availableRooms = null;
        if (bookings.isEmpty()) {
//            availableRooms 
            System.out.println("Booking table empty");
            setRooms(roomFacade.findAll());
//            System.out.println(name.getName());
//            System.out.println(staff.getName());
//            System.out.println( selectedRoom);
//            System.out.println(firstNight);
//            System.out.println(lastNight);
        } else {
            for (int i = 0; i < bookings.size(); i++) {
                Date first = (Date) bookings.get(i).getfNight(); //booking table
                Date last = (Date) bookings.get(i).getlNight();

                if (first.compareTo(firstNight) > 0) {
                    System.out.println("test");
                }
            }
        }
//        System.out.println(name.getName() + staff.getName() + selectedRoom + firstNight + lastNight);
    }

    public void confirmBooking() {
        setStaff(staffUser);
        System.out.println(name.getName() + staff.getName() + selectedRoom + firstNight + lastNight);
        booking = new Booking(name, staff, selectedRoom, firstNight, lastNight);
        bookingFacade.create(booking);
        System.out.println("Booking Done");
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

    public void selectDate(){
        getFirstNight();
        getLastNight();
        listBookings();
    }
    
    public Date getMin() {
        Calendar cal = Calendar.getInstance();
        cal.set(2017, 9, 1);
        min = cal.getTime();
        return min;
    }

    public String getDateRangeString() {
        return String.format("From: %s To: %s", formatter.format(firstNight), formatter.format(lastNight));
    }

    public UseraccFacade getUseraccFacade() {
        return useraccFacade;
    }

    public void setUseraccFacade(UseraccFacade useraccFacade) {
        this.useraccFacade = useraccFacade;
    }

    public RoomFacade getRoomFacade() {
        return roomFacade;
    }

    public void setRoomFacade(RoomFacade roomFacade) {
        this.roomFacade = roomFacade;
    }

    public BookingFacade getBookingFacade() {
        return bookingFacade;
    }

    public void setBookingFacade(BookingFacade bookingFacade) {
        this.bookingFacade = bookingFacade;
    }

    public GuestFacade getGuestFacade() {
        return guestFacade;
    }

    public void setGuestFacade(GuestFacade guestFacade) {
        this.guestFacade = guestFacade;
    }

    public Guest getName() {
        return name;
    }

    public void setName(Guest name) {
        this.name = name;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStaffUser() {
        return staffUser;
    }

    public void setStaffUser(String staffUser) {
        this.staffUser = staffUser;
    }

    public Useracc getStaff() {
        return staff;
    }

    public void setStaff(Useracc staff) {
        this.staff = staff;
    }

    public Room getSelectedRoom() {
        return selectedRoom;
    }

    public void setSelectedRoom(Room selectedRoom) {
        this.selectedRoom = selectedRoom;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public List<Guest> getGuestList() {
        return guestList;
    }

    public void setGuestList(List<Guest> guestList) {
        this.guestList = guestList;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
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

    public bookingBean() {
        Calendar cal = Calendar.getInstance();
        cal.set(2017, 9, 1);
        firstNight = cal.getTime();
        cal.set(2017, 9, 2);
        lastNight = cal.getTime();
        
    }

}
