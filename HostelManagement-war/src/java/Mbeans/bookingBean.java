/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mbeans;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.Booking;
import model.BookingFacade;
import model.Guest;
import model.GuestFacade;
import model.Room;
import model.RoomFacade;

/**
 *
 * @author KhaiKhai
 */
@ManagedBean
@ViewScoped
public class bookingBean implements Serializable {

    @EJB
    private RoomFacade roomFacade;

    @EJB
    private BookingFacade bookingFacade;

    @EJB
    private GuestFacade guestFacade;

    private String name;
    private String id;

    private List<Guest> guestList;
    private List<Room> rooms;
    private Room selectedRoom;
    private List<Booking> bookings;
    private Booking booking;
    

    
    private Date firstNight, lastNight;
    private Date min1 = new Date();
    private Date min2 = new Date();
    private String test;

    /**
     * Creates a new instance of bookingBean
     */
    @PostConstruct
    public void init() {
        guestList = guestFacade.findAll();
    }

    public void listBookings() {

        bookings = bookingFacade.findAll();
        List<Room> availableRooms = null;
        if (bookings.isEmpty()) {
//            availableRooms 
            System.out.println("Booking table empty");
            setRooms(roomFacade.findAll());

        } else {
            for (int i = 0; i < bookings.size(); i++) {
                Date first = (Date) bookings.get(i).getfNight(); //booking table
                Date last = (Date) bookings.get(i).getlNight();

                if (first.compareTo(firstNight) > 0) {
                    System.out.println("test");
                }
            }
        }
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public GuestFacade getGuestFacade() {
        return guestFacade;
    }

    public Room getSelectedRoom() {
        return selectedRoom;
    }

    public void setSelectedRoom(Room selectedRoom) {
        this.selectedRoom = selectedRoom;
    }

    public void setGuestFacade(GuestFacade guestFacade) {
        this.guestFacade = guestFacade;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Date getFirst() {
        return firstNight;
    }

    public Date getMin1() {
        return min1;
    }

    public Date getMin2() {
        Date min = new Date(getMin1().getTime() + (1000 * 60 * 60 * 24));
        return min;
    }

    public void setMin2(Date min2) {

        this.min2 = min2;
    }

    public void setFirst(Date first) {
        this.firstNight = first;
    }

    public Date getLast() {
        return lastNight;
    }

    public void setLast(Date last) {
        this.lastNight = last;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Guest> getGuestList() {
        return guestList;
    }

    public void setGuestList(List<Guest> guestList) {
        this.guestList = guestList;
    }

    public bookingBean() {
    }

}
