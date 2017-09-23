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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
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
import org.primefaces.context.RequestContext;

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

    private Guest guest;
    private String cusName;
    private String id;
    private String staffUser;
    private Useracc staff;
    private Room selectedRoom;
    private Booking booking;
    private int roomNo;

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
        System.out.println("bookingbean called");
        guestList = guestFacade.findAll();
        staffUser = (String) (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("username"));
        Calendar cal = Calendar.getInstance();
        cal.set(2017, 9, 1);
        firstNight = cal.getTime();
        cal.set(2017, 9, 2);
        lastNight = cal.getTime();

    }

    public void changeUser(ValueChangeEvent e) {

        id = e.getNewValue().toString();

        System.out.println("Selected user is " + guest);
    }

    public void selectDate(Date f, Date l) {
        System.out.println("booking Bean Date selected");
        System.out.println(firstNight + "" + lastNight);
        if (f != null && l != null) {
            setFirstNight(f);
            setLastNight(l);
        }
        listBookings();
    }

    public void listBookings() {

        bookings = bookingFacade.findAll();
        List<Room> unRoom = new ArrayList<>();
        System.out.println("Searching booking table.");
        setRooms(roomFacade.findAll());
        if (bookings.isEmpty()) { //if there is no bookings
            System.out.println("Booking table empty");
            System.out.println(rooms.size());
        } else {
            for (int i = 0; i < bookings.size(); i++) {
                Date first = bookings.get(i).getfNight(); //booking table firstnight
                Date last = bookings.get(i).getlNight();//booking table last night

                if (!((last.compareTo(firstNight) <= 0) || (first.compareTo(lastNight) >= 0))) {
//                    rooms.add(bookings.get(i).getbRoom());
                    System.out.println("booking id" + bookings.get(i).getId());
                    unRoom.add(bookings.get(i).getbRoom());
                }
            }
            unRoom.forEach((r) -> {
                System.out.println("Removed" + r.getrNo());
                rooms.remove(r);
            });
            if (rooms.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "No available room", "Please select another date"));
            }
        }
//        System.out.println(name.getName() + staff.getName() + selectedRoom + firstNight + lastNight);
    }

    public String confirmBooking() {
        System.out.println("Try to book");
        setGuest(id);
        setStaff(staffUser);
        setRoom(roomNo);
        booking = new Booking(guest, staff, selectedRoom, firstNight, lastNight);
        bookingFacade.create(booking);
        System.out.println("Booking Done");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Booking added", ""));
        
        return "home.xhtml?faces-redirect=true";
    }

    public void reset() {
        RequestContext.getCurrentInstance().reset("form1:grid");
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

    public void setRoom(int roomNo) {
        System.out.println(roomNo);
        for (int i = 0; i < rooms.size(); i++) {
            if (roomNo == rooms.get(i).getId()) {
                selectedRoom = rooms.get(i);
            }
        }
    }

    public void setGuest(String id) {
        System.out.println(id);
        List<Guest> gl;
        gl = guestFacade.findAll();
        for (int i = 0; i < gl.size(); i++) {
            if (id.equalsIgnoreCase(Long.toString(gl.get(i).getId()))) {
                this.guest = gl.get(i);
            }
        }
    }

    public void selectRoom() {
        System.out.println("Room Selected");
        System.out.println(selectedRoom.getrNo());
    }

    public Date getMin() {
        Calendar cal = Calendar.getInstance();
        cal.set(2017, 9, 1);
        min = cal.getTime();
        return min;
    }

    public String getDateRangeString() {
        return String.format("Check in: %s \tCheck out: %s", formatter.format(firstNight), formatter.format(lastNight));
    }

    public String getRoomString() {
        String roomNo = "No room selected";
        if (selectedRoom != null) {
            roomNo = String.format("Room selected: ", selectedRoom.getrNo());
        }
        return roomNo;
    }

    public int getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(int roomNo) {
        this.roomNo = roomNo;
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
        return guest;
    }

    public void setName(Guest name) {
        this.guest = name;
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

    public Date getFirstNight() throws ParseException {

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

    }

}
