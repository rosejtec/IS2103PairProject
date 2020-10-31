/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import entity.AircraftConfigurationEntity;
import entity.AirportEntity;
import entity.CustomerEntity;
import entity.FlightEntity;
import entity.FlightRouteEntity;
import entity.FlightScheduleEntity;
import entity.FlightSchedulePlanEntity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author leahr
 */
@Singleton
@LocalBean
public class DataInitSessionBean {

    @PersistenceContext(unitName = "MerlionAirApplication-ejbPU")
    private EntityManager em;

        
    @PostConstruct
    public void postConstruct()
    {
        AirportEntity staffEntity = em.find(AirportEntity.class, 1L);
        
        if(staffEntity == null)
        {
            initializeData();
        }
    }
    
    
    
    private void initializeData()
    {
       /**      
             AirportEntity a = new  AirportEntity("NAR", "001", "TOKYO", "JAPAN", "JAPAN");
             em.persist(a);
             AirportEntity a1 = new  AirportEntity("SIN", "002", "SINGAPORE", "SINGAPORE", "SINGAPORE");
             em.persist(a1);
             AirportEntity a2 = new  AirportEntity("MUM", "003", "MUMBAI", "MAHARASHTRA", "INDIA");
             em.persist(a2);
             em.flush();
            
            
            FlightRouteEntity fr = new FlightRouteEntity(a1, a2, null, null);
                         em.persist(fr);

            FlightRouteEntity fr1 = new FlightRouteEntity(a, a2, null, null);
                         em.persist(fr1);

            FlightRouteEntity fr2 = new FlightRouteEntity(a, a1, null, null);
                         em.persist(fr2);

                           em.flush();

            FlightEntity  f = new FlightEntity("MA11", null, fr, null); 
            FlightEntity  f1 = new FlightEntity("MA11", null, fr1, null); 
            FlightEntity  f2 = new FlightEntity("MA11", null, fr2, null); 

            Date d= new Date(2020,8,30);
            Date d1= new Date(2021,8,30);

            FlightScheduleEntity fs= new FlightScheduleEntity(d,d1,0, null);          
            FlightSchedulePlanEntity fsp = new FlightSchedulePlanEntity(null, f);
            fsp.getFlightSchedule().add(fs);
            fs.setPlan(fsp);
            
            f.getScheduledFlights().add(fsp);
            
            /**
            CustomerEntity customerEntity1 = new CustomerEntity("One", "Customer", "customer1@gmail.com", "password");
            em.persist(customerEntity1);
            CustomerEntity customerEntity2 = new CustomerEntity("Two", "Customer", "customer2@gmail.com", "password");
            em.persist(customerEntity2);
            CustomerEntity customerEntity3 = new CustomerEntity("Three", "Customer", "customer3@gmail.com", "password");
            em.persist(customerEntity3);
           */
                   
        
        
    }
}
