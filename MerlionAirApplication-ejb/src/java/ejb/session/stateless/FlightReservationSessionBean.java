/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AirportEntity;
import entity.CustomerEntity;
import entity.FlightScheduleEntity;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
    public List<FlightScheduleEntity> searchSingleDay(boolean connecting,boolean round,String origin, String destination, Date departure, int passengers, String cabinClass) {    
       if(connecting) {
              
           
        } else {
            LocalDateTime t1 = convertToLocalDateTimeViaInstant(departure);
            LocalDateTime t2 = convertToLocalDateTimeViaInstant(departure).plusDays(1);
            System.out.println(t1);
            System.out.println(t2);
           AirportEntity a1= airportSessionBean.retriveBy(origin);
            AirportEntity a2= airportSessionBean.retriveBy(destination);
            
            Query query;
            if(connecting){
                query = em.createQuery("SELECT f FROM FlightScheduleEntity f JOIN f.plan p JOIN p.flight t JOIN t.flightRoute m JOIN t.flightRoute n WHERE  m.origin.airportId = :inOrg AND n.destination.airportId = :outDes AND  n.origin.airportId = m.destination.airportId AND f.departure BETWEEN :inDate AND :inDate1");
            } else {
                query = em.createQuery("SELECT f FROM FlightScheduleEntity f JOIN f.plan p JOIN p.flight t JOIN t.flightRoute m WHERE  m.origin.airportId = :inOrg AND m.destination.airportId = :outDes AND  f.departure BETWEEN :inDate AND :inDate1");
            }
                       
//SELECT m, p FROM FlightRouteEntity m ,FlightRouteEntity p WHERE  m.origin.airportId = "1" AND p.destination.airportId = "3" AND p.origin.airportId = m.destination.airportId
//SELECT m FROM FlightRouteEntity m LEFT JOIN FlightRouteEntity p WHERE  m.origin.airportId = "1" AND p.destination.airportId = "3" AND p.origin.airportId = m.destination.airportId
            System.out.println(a1);
            System.out.println(a2);

            query.setParameter("inOrg", a1);
            query.setParameter("outDes", a2);
            query.setParameter("inDate", t1);
            query.setParameter("inDate1", t2);

            List<FlightScheduleEntity>  list = query.getResultList();
            System.out.println(list);
            return list;
            
        
        }
       
       return null;
        
    }
    
    public LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
    return dateToConvert.toInstant()
      .atZone(ZoneId.systemDefault())
      .toLocalDateTime();
}
    
     @Override
    public List<FlightScheduleEntity> searchThreeDaysBefore(boolean connecting,boolean round,String origin, String destination, Date departure, int passengers, String cabinClass) {    
       if(round) {
      
            
        } else {
          LocalDateTime t1 = convertToLocalDateTimeViaInstant(departure).minusDays(1);
            LocalDateTime t2 = convertToLocalDateTimeViaInstant(departure).minusDays(4);
            System.out.println(t1);
            System.out.println(t2);
            AirportEntity a1= airportSessionBean.retriveBy(origin);
            AirportEntity a2= airportSessionBean.retriveBy(destination);
          Query query;
            if(connecting){
                query = em.createQuery("SELECT f FROM FlightScheduleEntity f JOIN f.plan p JOIN p.flight t JOIN t.flightRoute m JOIN t.flightRoute n WHERE  m.origin.airportId = :inOrg AND n.destination.airportId = :outDes AND  n.origin.airportId = m.destination.airportId AND f.departure BETWEEN :inDate AND :inDate1");
            } else {
                query = em.createQuery("SELECT f FROM FlightScheduleEntity f JOIN f.plan p JOIN p.flight t JOIN t.flightRoute m WHERE  m.origin.airportId = :inOrg AND m.destination.airportId = :outDes AND  f.departure BETWEEN :inDate AND :inDate1");
            }
            
//SELECT m, p FROM FlightRouteEntity m ,FlightRouteEntity p WHERE  m.origin.airportId = "1" AND p.destination.airportId = "3" AND p.origin.airportId = m.destination.airportId
//SELECT m FROM FlightRouteEntity m LEFT JOIN FlightRouteEntity p WHERE  m.origin.airportId = "1" AND p.destination.airportId = "3" AND p.origin.airportId = m.destination.airportId
            System.out.println(a1);
            System.out.println(a2);

            query.setParameter("inOrg", a1);
            query.setParameter("outDes", a2);
            query.setParameter("inDate", t1);
            query.setParameter("inDate1", t2);

             List<FlightScheduleEntity>  list = query.getResultList();
            System.out.println(list);
            return list;
        
        }
        return null;
    } 
        
    @Override
    public List<FlightScheduleEntity> searchThreeDaysAfter(boolean connecting,boolean round,String origin, String destination, Date departure,  int passengers, String cabinClass) {    
       if(round) {
         
            
        } else {
            LocalDateTime t1 = convertToLocalDateTimeViaInstant(departure).plusDays(1);
            LocalDateTime t2 = convertToLocalDateTimeViaInstant(departure).plusDays(4);
            System.out.println(t1);
            System.out.println(t2);
            AirportEntity a1= airportSessionBean.retriveBy(origin);
            AirportEntity a2= airportSessionBean.retriveBy(destination);
            Query query;
            if(connecting){
                query = em.createQuery("SELECT f FROM FlightScheduleEntity f JOIN f.plan p JOIN p.flight t JOIN t.flightRoute m JOIN t.flightRoute n WHERE  m.origin.airportId = :inOrg AND n.destination.airportId = :outDes AND  n.origin.airportId = m.destination.airportId AND f.departure BETWEEN :inDate AND :inDate1");
            } else {
                query = em.createQuery("SELECT f FROM FlightScheduleEntity f JOIN f.plan p JOIN p.flight t JOIN t.flightRoute m WHERE  m.origin.airportId = :inOrg AND m.destination.airportId = :outDes AND  f.departure BETWEEN :inDate AND :inDate1");
            }
            
//SELECT m, p FROM FlightRouteEntity m ,FlightRouteEntity p WHERE  m.origin.airportId = "1" AND p.destination.airportId = "3" AND p.origin.airportId = m.destination.airportId
//SELECT m FROM FlightRouteEntity m LEFT JOIN FlightRouteEntity p WHERE  m.origin.airportId = "1" AND p.destination.airportId = "3" AND p.origin.airportId = m.destination.airportId
            System.out.println(a1);
            System.out.println(a2);

            query.setParameter("inOrg", a1);
            query.setParameter("outDes", a2);
            query.setParameter("inDate", t1);
            query.setParameter("inDate1", t2);

             List<FlightScheduleEntity>  list = query.getResultList();
            System.out.println(list);
            return list;
        }
        return null;
    }
    
     public List<FlightScheduleEntity> searchConnectingThreeDaysAfter(boolean connecting,boolean round,String origin, String destination, Date departure,  int passengers, String cabinClass) {    
       if(round) {
         
            
        } else {
            LocalDateTime t1 = convertToLocalDateTimeViaInstant(departure);
            LocalDateTime t2 = convertToLocalDateTimeViaInstant(departure).plusDays(1);
            System.out.println(t1);
            System.out.println(t2);
            AirportEntity a1= airportSessionBean.retriveBy(origin);
            AirportEntity a2= airportSessionBean.retriveBy(destination);
            Query query = em.createQuery("SELECT f FROM FlightScheduleEntity f JOIN f.plan p JOIN p.flight t JOIN t.flightRoute m WHERE m.origin.airportId = :inOrg AND f.departure BETWEEN :inDate AND :inDate1");
      
            query.setParameter("inOrg", a1);
            query.setParameter("inDate", t1);
            query.setParameter("inDate1", t2);

             List<FlightScheduleEntity>  list = query.getResultList();
      
             System.out.println(list);
            
             for(int i = 0; i<list.size();i++) {
             Query query1 = em.createQuery("SELECT f FROM FlightScheduleEntity f JOIN f.plan p JOIN p.flight t JOIN t.flightRoute m WHERE m.origin.airportId = :inOrg AND f.departure BETWEEN :inDate AND :inDate1");
      
            query1.setParameter("inOrg", a1);
            query1.setParameter("inDate", t1);
            query1.setParameter("inDate1", t2);
//m.destination.airportId = :outDes 
             }
             
             return list;
        }
        return null;
    }

  

      
}
