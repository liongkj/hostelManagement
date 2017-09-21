/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mbeans;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import model.Room;
import model.RoomFacade;

@ManagedBean
@ViewScoped
public class roomBean {

    @EJB
    private RoomFacade roomFacade;

    private List<Room> rooms;
    private Room selectedCar;

    @PostConstruct
    public void init() {
        rooms = roomFacade.findAll();
    }

    public RoomFacade getRoomFacade() {
        return roomFacade;
    }

    public void setRoomFacade(RoomFacade roomFacade) {
        this.roomFacade = roomFacade;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public Room getSelectedCar() {
        return selectedCar;
    }

    public void setSelectedCar(Room selectedCar) {
        this.selectedCar = selectedCar;
    }

    public void addRoom() {
        String level = "10";
        for (int i = 0; i < 10; i++) {
            level = level + Integer.toString(i);
            Room room = new Room(level, 'r');
            roomFacade.create(room);
        }
        System.out.println("Room added");
    }

    /**
     * Creates a new instance of roomBean
     */
    public roomBean() {
    }

}
