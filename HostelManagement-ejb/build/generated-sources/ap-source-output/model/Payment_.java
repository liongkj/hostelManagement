package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Booking;
import model.Guest;
import model.Useracc;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-09-23T02:05:50")
@StaticMetamodel(Payment.class)
public class Payment_ { 

    public static volatile SingularAttribute<Payment, Integer> price;
    public static volatile SingularAttribute<Payment, Booking> book;
    public static volatile SingularAttribute<Payment, Useracc> staff;
    public static volatile SingularAttribute<Payment, Guest> guest;
    public static volatile SingularAttribute<Payment, Long> id;

}