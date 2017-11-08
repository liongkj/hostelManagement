package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Useracc;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-10-11T12:53:43")
@StaticMetamodel(Guest.class)
public class Guest_ { 

    public static volatile SingularAttribute<Guest, String> address;
    public static volatile SingularAttribute<Guest, String> gender;
    public static volatile SingularAttribute<Guest, String> cusID;
    public static volatile SingularAttribute<Guest, String> phone;
    public static volatile SingularAttribute<Guest, String> IC;
    public static volatile SingularAttribute<Guest, Useracc> staff;
    public static volatile SingularAttribute<Guest, Long> id;
    public static volatile SingularAttribute<Guest, String> email;
    public static volatile SingularAttribute<Guest, Integer> age;
    public static volatile SingularAttribute<Guest, String> Name;

}