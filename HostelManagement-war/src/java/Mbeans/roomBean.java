/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mbeans;

import java.io.Serializable;
import java.util.ArrayList;
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
    private List<Room> dirty;
    private Room room;
    
    @PostConstruct
    public void init() {
        rooms = roomFacade.findAll();
    }

    
    public void addRoom() {
        String level = "20";
        
        for (int i = 1; i < 10; i++) {
            String lbl = level + Integer.toString(i);
            Room room = new Room(lbl, "Cleaned");
            roomFacade.create(room);
        }
        System.out.println("Room added");
    }

    
    public RoomFacade getRoomFacade() {
        return roomFacade;
    }

    public void setRoomFacade(RoomFacade roomFacade) {
        this.roomFacade = roomFacade;
    }

    public List<Room> getDirty() {
        List<Room> temp = new ArrayList<>();
        for(Room r:rooms){
            if(r.getStatus().charAt(0) == 'T')
            temp.add(r);
        }
        return temp;
    }

    public void setDirty(List<Room> dirty) {
        this.dirty = dirty;
    }

    
    
    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    
    /**
     * Creates a new instance of roomBean
     */
    public roomBean() {
    }

}
