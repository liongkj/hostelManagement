package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Guest;
import model.Room;
import model.Useracc;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-10-11T12:53:43")
@StaticMetamodel(Payment.class)
public class Payment_ { 

    public static volatile SingularAttribute<Payment, Integer> price;
    public static volatile SingularAttribute<Payment, Date> paid;
    public static volatile SingularAttribute<Payment, Useracc> staff;
    public static volatile SingularAttribute<Payment, Guest> guest;
    public static volatile SingularAttribute<Payment, Long> id;
    public static volatile SingularAttribute<Payment, Room> room;
    public static volatile SingularAttribute<Payment, String> status;

}