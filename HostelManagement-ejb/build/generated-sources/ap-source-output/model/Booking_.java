package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Guest;
import model.Room;
import model.Useracc;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-09-21T19:57:30")
@StaticMetamodel(Booking.class)
public class Booking_ { 

    public static volatile SingularAttribute<Booking, Room> bRoom;
    public static volatile SingularAttribute<Booking, Useracc> staff;
    public static volatile SingularAttribute<Booking, Long> id;
    public static volatile SingularAttribute<Booking, Guest> bGuest;
    public static volatile SingularAttribute<Booking, String> bId;
    public static volatile SingularAttribute<Booking, Date> firstNight;
    public static volatile SingularAttribute<Booking, Date> lastNight;

}