/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import model.Booking;
import model.BookingFacade;
import model.Guest;
import model.GuestFacade;
import model.Payment;
import model.PaymentFacade;
import model.Room;
import model.RoomFacade;
import model.Useracc;
import model.UseraccFacade;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.MeterGaugeChartModel;
import org.primefaces.model.chart.PieChartModel;

@ManagedBean
public class ChartView implements Serializable {

    @EJB
    private UseraccFacade useraccFacade;

    @EJB
    private GuestFacade guestFacade;

    @EJB
    private RoomFacade roomFacade;

    @EJB
    private BookingFacade bookingFacade;

    @EJB
    private PaymentFacade paymentFacade;
    
    
    private BarChartModel barModel;
    private PieChartModel pieModel1;
    private LineChartModel lineModel1;
    private LineChartModel lineModel2;
    private MeterGaugeChartModel meterGaugeModel2;

    @PostConstruct
    public void init() {
        createLineModels();
        createMeterGaugeModels();
        createPieModels();
        createBarModels();
    }

    public LineChartModel getLineModel1() {
        return lineModel1;
    }

    public LineChartModel getLineModel2() {
        return lineModel2;
    }
    
     public MeterGaugeChartModel getMeterGaugeModel2() {
        return meterGaugeModel2;
    }
     
     public PieChartModel getPieModel1() {
        return pieModel1;
    }
     
    private void createLineModels() {
        lineModel1 = initLinearModel();
        lineModel1.setTitle("October Bookings");
        lineModel1.setLegendPosition("e");
        lineModel1.setAnimate(true);
        Axis yAxis = lineModel1.getAxis(AxisType.Y);
        Axis xAxis = lineModel1.getAxis(AxisType.X);
        xAxis.setMin(1);
        xAxis.setMax(30);
        xAxis.setTickInterval("1");
        yAxis.setTickInterval("10");
        yAxis.setMin(0);
        yAxis.setMax(50);
        
        lineModel2 = initLinearModel1();
        lineModel2.setTitle("October Sales");
        lineModel2.setLegendPosition("e");
        lineModel2.setAnimate(true);
        Axis yyAxis = lineModel2.getAxis(AxisType.Y);
        Axis xxAxis = lineModel2.getAxis(AxisType.X);
        xxAxis.setMin(1);
        xxAxis.setMax(31);
        xxAxis.setTickInterval("1");
        yyAxis.setTickInterval("500");
        yyAxis.setMin(0);
        yyAxis.setMax(2000);

    }

    private LineChartModel initLinearModel() {
        List<Booking> bookings = new ArrayList();
        int numBook = 0;
        Calendar date = Calendar.getInstance();
        LineChartModel model = new LineChartModel();

        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("Room Booked");

        bookings = bookingFacade.findAll();

        for (Booking b : bookings) {

            date.setTime(b.getFirstNight());
            int day = date.get(Calendar.DAY_OF_MONTH);
            numBook++;
            series1.set(day, numBook);
            
            
        }

        model.addSeries(series1);

        return model;
    }
    
    private LineChartModel initLinearModel1() {
        List<Payment> payments = new ArrayList();
        
        Calendar date = Calendar.getInstance();
        LineChartModel model = new LineChartModel();

        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("Sales");

        payments = paymentFacade.findAll();
        int[] sales = new int[31];
        for (Payment b : payments) {
            date.setTime(b.getPaid());
            int day = date.get(Calendar.DAY_OF_MONTH); 
            
            sales[day] = b.getPrice() + sales[day];
        }
        for(int i=1;i<31;i++){
            if(sales[i] != 0)
            series1.set(i, sales[i]);
        }

        model.addSeries(series1);

        return model;
    }
    
    private void createMeterGaugeModels() {
         
        meterGaugeModel2 = initMeterGaugeModel();
        meterGaugeModel2.setTitle("Room Status");
        meterGaugeModel2.setSeriesColors("66cc66,93b75f,E7E658,cc6666");
        meterGaugeModel2.setGaugeLabel("Dirty Rooms");
        meterGaugeModel2.setGaugeLabelPosition("bottom");
        meterGaugeModel2.setLabelHeightAdjust(110);
        
        
    }

   
 
    private MeterGaugeChartModel initMeterGaugeModel() {
        int noDirty=0;
        List<Number> intervals = new ArrayList<Number>(){{
            add(5);
            add(10);
            add(20);
            add(30);
        }};
        List<Room> rooms = new ArrayList();
        rooms=roomFacade.findAll();
        for(Room r : rooms){
            if(r.getStatus().startsWith("T")) noDirty++;
        }
        
        return new MeterGaugeChartModel(noDirty, intervals);
    }
 
    private void createPieModels() {
        createPieModel1();
        
    }
    
    private void createPieModel1() {
        List<Guest> gl = guestFacade.findAll();
        int m=0,f=0;
        for(Guest g:gl){
            if(g.getGender().startsWith("M")){
                m++;
            }else{
                f++;
            }
        }
        
        pieModel1 = new PieChartModel();
         
        pieModel1.set("Male", m);
        pieModel1.set("Female", f);
         
        pieModel1.setTitle("Gender Pie");
        pieModel1.setLegendPosition("w");
        pieModel1.setShowDataLabels(true);
    }
    
    public BarChartModel getBarModel() {
        return barModel;
    }
    private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();
        List<Useracc> ul = useraccFacade.findAll();
        int[] m = {0,0},r = {0,0},f = {0,0},c = {0,0};
        for(Useracc u:ul){
            if(u.getDepartment().charAt(0) == 'm'){
                if(u.getGender().equalsIgnoreCase("male")) m[0]++; else m[1]++;
            }
            if(u.getDepartment().charAt(0) == 'f'){
                if(u.getGender().equalsIgnoreCase("male")) f[0]++; else f[1]++;
            }if(u.getDepartment().charAt(0) == 'r'){
                if(u.getGender().equalsIgnoreCase("male")) r[0]++; else r[1]++;
            }if(u.getDepartment().charAt(0) == 'c'){
                if(u.getGender().equalsIgnoreCase("male")) c[0]++; else c[1]++;
            }
        }
        
        ChartSeries boys = new ChartSeries();
        boys.setLabel("Male");
        boys.set("Manager", m[0]);
        boys.set("Reservation", r[0]);
        boys.set("Front Desk", f[0]);
        boys.set("Cleaning", c[0]);
        
 
        ChartSeries girls = new ChartSeries();
        girls.setLabel("Female");
        girls.set("Manager", m[1]);
        girls.set("Reservation", r[1]);
        girls.set("Front Desk", f[1]);
        girls.set("Cleaning", c[1]);
       
 
        model.addSeries(boys);
        model.addSeries(girls);
         
        return model;
    }
    
     private void createBarModels() {
        createBarModel();  
    }
     
     private void createBarModel() {
        barModel = initBarModel();
         
        barModel.setTitle("Staff Count");
        barModel.setLegendPosition("ne");
         
        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Gender");
         
        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Department");
        yAxis.setMin(0);
        yAxis.setMax(4);
    }
     

}
