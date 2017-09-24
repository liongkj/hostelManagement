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
import model.Payment;
import model.PaymentFacade;
import model.Room;
import model.RoomFacade;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.MeterGaugeChartModel;

@ManagedBean
public class ChartView implements Serializable {

    @EJB
    private RoomFacade roomFacade;

    @EJB
    private BookingFacade bookingFacade;

    @EJB
    private PaymentFacade paymentFacade;
    
    

    private LineChartModel lineModel1;
    private MeterGaugeChartModel meterGaugeModel2;

    @PostConstruct
    public void init() {
        createLineModels();
        createMeterGaugeModels();
    }

    public LineChartModel getLineModel1() {
        return lineModel1;
    }

     public MeterGaugeChartModel getMeterGaugeModel2() {
        return meterGaugeModel2;
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

    }

    private LineChartModel initLinearModel() {
        List<Booking> bookings = new ArrayList();
        int numBook = 1;
        Calendar date = Calendar.getInstance();
        LineChartModel model = new LineChartModel();

        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("Room Booked");

        bookings = bookingFacade.findAll();

        for (Booking b : bookings) {

            date.setTime(b.getFirstNight());
            int day = date.get(Calendar.DAY_OF_MONTH);
            
            series1.set(day, numBook);
            
            numBook++;
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
 
    
 

}
