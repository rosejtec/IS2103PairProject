/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import entity.AirportEntity;
import entity.CustomerEntity;
import entity.FlightScheduleEntity;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.enumeration.CabinClassType;

/**
 *
 * @author leahr
 */
@Stateless
public class FlightReservationSessionBean implements FlightReservationSessionBeanRemote, FlightReservationSessionBeanLocal {

    @EJB
    private AirportSessionBeanLocal airportSessionBean;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @PersistenceContext(unitName = "MerlionAirApplication-ejbPU")
    private EntityManager em;

    public void persist(Object object) {
        em.persist(object);
    }

    
    
    @Override
    public List<FlightScheduleEntity> searchSingleDay(boolean connecting,boolean round,String origin, String destination, LocalDateTime departure, int passengers,  CabinClassType cabinClass) {    
      
           AirportEntity a1= airportSessionBean.retriveBy(origin);
            AirportEntity a2= airportSessionBean.retriveBy(destination);
            
            Query query = em.createQuery("SELECT f FROM FlightScheduleEntity f JOIN f.flightSchedulePlan p JOIN p.flight t JOIN t.flightRoute m WHERE  m.origin.airportId = :inOrg AND m.destination.airportId = :outDes AND  f.departure BETWEEN :inDate AND :inDate1");
                              
//SELECT m, p FROM FlightRouteEntity m ,FlightRouteEntity p WHERE  m.origin.airportId = "1" AND p.destination.airportId = "3" AND p.origin.airportId = m.destination.airportId
//SELECT m FROM FlightRouteEntity m LEFT JOIN FlightRouteEntity p WHERE  m.origin.airportId = "1" AND p.destination.airportId = "3" AND p.origin.airportId = m.destination.airportId
            System.out.println(a1);
            System.out.println(a2);

            query.setParameter("inOrg", a1.getAirportId());
            query.setParameter("outDes", a2.getAirportId());
             query.setParameter("inDate", departure);
            query.setParameter("inDate1", departure.plusDays(1));   

            List<FlightScheduleEntity>  list = query.getResultList();
            System.out.println(list);
            return list;
            
        
        }

    @Override
    public List<FlightScheduleEntity> searchThreeDaysBefore(boolean connecting, boolean round, String origin, String destination, LocalDateTime departure, int passengers, CabinClassType cabinClass) {
 AirportEntity a1= airportSessionBean.retriveBy(origin);
            AirportEntity a2= airportSessionBean.retriveBy(destination);
              Query query;
           
                
                query = em.createQuery("SELECT f FROM FlightScheduleEntity f JOIN f.flightSchedulePlan p JOIN p.flight t JOIN t.flightRoute m WHERE  m.origin.airportId = :inOrg AND m.destination.airportId = :outDes AND  f.departure BETWEEN :inDate AND :inDate1");
            
            
//SELECT m, p FROM FlightRouteEntity m ,FlightRouteEntity p WHERE  m.origin.airportId = "1" AND p.destination.airportId = "3" AND p.origin.airportId = m.destination.airportId
//SELECT m FROM FlightRouteEntity m LEFT JOIN FlightRouteEntity p WHERE  m.origin.airportId = "1" AND p.destination.airportId = "3" AND p.origin.airportId = m.destination.airportId
            System.out.println(a1);
            System.out.println(a2);

            query.setParameter("inOrg", a1.getAirportId());
            query.setParameter("outDes", a2.getAirportId());
             query.setParameter("inDate", departure.minusDays(1));
            query.setParameter("inDate1", departure.minusDays(4));
             List<FlightScheduleEntity>  list = query.getResultList();
            System.out.println(list);
            return list;
    }

    @Override
    public List<FlightScheduleEntity> searchThreeDaysAfter(boolean connecting, boolean round, String origin, String destination, LocalDateTime departure, int passengers, CabinClassType cabinClass) {
           AirportEntity a1= airportSessionBean.retriveBy(origin);
            AirportEntity a2= airportSessionBean.retriveBy(destination);
            Query query;
                query = em.createQuery("SELECT f FROM FlightScheduleEntity f JOIN f.flightSchedulePlan p JOIN p.flight t JOIN t.flightRoute m WHERE  m.origin.airportId = :inOrg AND m.destination.airportId = :outDes AND  f.departure BETWEEN :inDate AND :inDate1");
            
            
//SELECT m, p FROM FlightRouteEntity m ,FlightRouteEntity p WHERE  m.origin.airportId = "1" AND p.destination.airportId = "3" AND p.origin.airportId = m.destination.airportId
//SELECT m FROM FlightRouteEntity m LEFT JOIN FlightRouteEntity p WHERE  m.origin.airportId = "1" AND p.destination.airportId = "3" AND p.origin.airportId = m.destination.airportId
            System.out.println(a1);
            System.out.println(a2);

            query.setParameter("inOrg", a1.getAirportId());
            query.setParameter("outDes", a2.getAirportId());
            query.setParameter("inDate", departure.plusDays(1));
            query.setParameter("inDate1", departure.plusDays(4));

             List<FlightScheduleEntity>  list = query.getResultList();
            System.out.println(list);
            return list;
    }

    @Override
    public List<List<FlightScheduleEntity>> searchConnectingThreeDaysAfter(boolean connecting, boolean round, String origin, String destination, LocalDateTime departure, int passengers, CabinClassType cabinClass) {
            AirportEntity a1= airportSessionBean.retriveBy(origin);
            AirportEntity a2= airportSessionBean.retriveBy(destination);
            Query query = em.createQuery("SELECT f FROM FlightScheduleEntity f JOIN f.flightSchedulePlan p JOIN p.flight t JOIN t.flightRoute m WHERE m.origin.airportId = :inOrg AND f.departure BETWEEN :inDate AND :inDate1");
      
            query.setParameter("inOrg", a1.getAirportId());
              query.setParameter("inDate", departure.plusDays(1));
            query.setParameter("inDate1", departure.plusDays(4));

             List<FlightScheduleEntity>  list = query.getResultList();
      
             System.out.println(list);
            List<List<FlightScheduleEntity>> c = new ArrayList<>();
            int i =0;
             for(FlightScheduleEntity fs : list) {
             Query query1 = em.createQuery("SELECT f FROM FlightScheduleEntity f JOIN f.flightSchedulePlan p JOIN p.flight t JOIN t.flightRoute m WHERE m.origin.airportId = :inOrg AND m.destination.airportId = :inDes AND f.departure BETWEEN :inDate AND :inDate1");
      
             query1.setParameter("inOrg", fs.getFlightSchedulePlan().getFlight().getFlightRoute().getDestination().getAirportId());
             query1.setParameter("inDes", a2.getAirportId());
             query.setParameter("inDate", fs.getArrival());
             query.setParameter("inDate1", fs.getArrival().plusDays(1));
             
              List<FlightScheduleEntity> connect = query1.getResultList();
             
              if(!connect.isEmpty()) {
                  c.get(i).addAll(connect);
              }
              i++;
            }
             
             return c;  
    }
      
    
   
  

      
}
