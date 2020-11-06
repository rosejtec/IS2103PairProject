/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import javax.ejb.Singleton;
import javax.ejb.LocalBean;

import entity.AircraftConfigurationEntity;
import entity.AirportEntity;
import entity.CustomerEntity;
import entity.FlightEntity;
import entity.FlightRouteEntity;
import entity.FlightScheduleEntity;
import entity.FlightSchedulePlanEntity;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.util.converter.LocalDateTimeStringConverter;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author leahr
 */
@Singleton
@LocalBean
@Startup
public class DataInitSessionBean {

    @PersistenceContext(unitName = "MerlionAirApplication-ejbPU")
    private EntityManager em;

     public DataInitSessionBean()
    {
    }
    
    @PostConstruct
    public void postConstruct()
    {
      if(em.find(AirportEntity.class, 1l) == null)
        {
            initializeData();
        }
    }
    
    
    
    private void initializeData()
    {
       
             AirportEntity a = new  AirportEntity("NAR", "111", "TOKYO", "JAPAN", "JAPAN");
             em.persist(a);
              em.flush();
             AirportEntity a1 = new  AirportEntity("SIN", "112", "SINGAPORE", "SINGAPORE", "SINGAPORE");
             em.persist(a1);
              em.flush();
             AirportEntity a2  = new  AirportEntity("MUM", "113", "MUMBAI", "MAHARASHTRA", "INDIA");
             em.persist(a2);
              em.flush();
 
            
            String now = "2020-11-09 10:30";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime d = LocalDateTime.parse(now, formatter);
            String now1 = "2020-12-09 10:30";
            LocalDateTime d1 = LocalDateTime.parse(now1, formatter);

            FlightScheduleEntity fs= new FlightScheduleEntity(d,d1,3);    
                                     em.persist(fs);
                                     em.flush();   
            FlightSchedulePlanEntity fsp = new FlightSchedulePlanEntity();
                                     em.persist(fsp);
                                     em.flush();   
            fsp.getFlightSchedule().add(fs);
            fs.setPlan(fsp);          
            
             FlightScheduleEntity fs1= new FlightScheduleEntity(d,d1,3);    
                                     em.persist(fs1);
                                     em.flush();   
            FlightSchedulePlanEntity fsp1 = new FlightSchedulePlanEntity();
                                     em.persist(fsp1);
                                     em.flush();   
            fsp1.getFlightSchedule().add(fs1);
            fs1.setPlan(fsp1);       
            
            FlightRouteEntity fr = new FlightRouteEntity(a1, a2);
                         em.persist(fr);
                         em.flush();
            FlightRouteEntity fr1 = new FlightRouteEntity(a, a2);
                         em.persist(fr1);
                         em.flush();
            FlightRouteEntity fr2 = new FlightRouteEntity(a, a1);
                         em.persist(fr2);
                         em.flush();             
              
            FlightEntity  f = new FlightEntity("MA11", fr); 
                                     em.persist(f);
                                     em.flush();
            FlightEntity  f1 = new FlightEntity("MA12", fr1); 
                                     em.persist(f1);
                                     em.flush();   
            FlightEntity  f2 = new FlightEntity("MA13", fr2); 
                                     em.persist(f2);
                                     em.flush();   
            
            fr.getFlightEntity().add(f);
            fr1.getFlightEntity().add(f1);
            fr2.getFlightEntity().add(f2);

            fsp.setFlight(f);
            f.getScheduledFlights().add(fsp);
            
             fsp1.setFlight(f2);
            f2.getScheduledFlights().add(fsp1);
            
            

//            f.getScheduledFlights().add(fsp);
//            CustomerEntity customerEntity1 = new CustomerEntity("One", "Customer", "customer1@gmail.com", "password");
//            em.persist(customerEntity1);
//            CustomerEntity customerEntity2 = new CustomerEntity("Two", "Customer", "customer2@gmail.com", "password");
//            em.persist(customerEntity2);
//            CustomerEntity customerEntity3 = new CustomerEntity("Three", "Customer", "customer3@gmail.com", "password");
//            em.persist(customerEntity3);
           
                         
        
    }
}
