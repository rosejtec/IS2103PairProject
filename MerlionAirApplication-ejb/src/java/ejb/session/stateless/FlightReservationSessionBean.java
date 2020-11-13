/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import entity.AirportEntity;
import entity.CustomerEntity;
import entity.FlightReservationEntity;
import entity.FlightScheduleEntity;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.enumeration.CabinClassType;
import util.enumeration.NoFlightsFoundOnSearchException;
import util.exception.AirportNotFoundException;

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
    public List<FlightScheduleEntity> searchSingleDay(boolean connecting, boolean round, String origin, String destination, LocalDateTime departure, int passengers, CabinClassType cabinClass) throws NoFlightsFoundOnSearchException ,AirportNotFoundException{
        try {
            AirportEntity a1 = airportSessionBean.retriveBy(origin);
            AirportEntity a2 = airportSessionBean.retriveBy(destination);

            Query query = em.createQuery("SELECT f FROM FlightScheduleEntity f JOIN f.flightSchedulePlan p JOIN p.flight t JOIN t.flightRoute m WHERE  m.origin.airportId = :inOrg AND m.destination.airportId = :outDes AND  f.departure BETWEEN :inDate AND :inDate1");

//SELECT m, p FROM FlightRouteEntity m ,FlightRouteEntity p WHERE  m.origin.airportId = "1" AND p.destination.airportId = "3" AND p.origin.airportId = m.destination.airportId
//SELECT m FROM FlightRouteEntity m LEFT JOIN FlightRouteEntity p WHERE  m.origin.airportId = "1" AND p.destination.airportId = "3" AND p.origin.airportId = m.destination.airportId
            System.out.println(a1);
            System.out.println(a2);

            query.setParameter("inOrg", a1.getAirportId());
            query.setParameter("outDes", a2.getAirportId());
            query.setParameter("inDate", departure);
            query.setParameter("inDate1", departure.plusDays(1));

            List<FlightScheduleEntity> list = query.getResultList();
            System.out.println(list);
            return list;
        } catch (NoResultException ex) {

            throw new NoFlightsFoundOnSearchException();

        } catch (AirportNotFoundException ex) {
            Logger.getLogger(FlightReservationSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return null;
    }

    @Override
    public List<FlightScheduleEntity> searchThreeDaysBefore(boolean connecting, boolean round, String origin, String destination, LocalDateTime departure, int days, CabinClassType cabinClass) throws NoFlightsFoundOnSearchException,AirportNotFoundException {

        try {
            AirportEntity a1 = airportSessionBean.retriveBy(origin);
            AirportEntity a2 = airportSessionBean.retriveBy(destination);
            Query query;

            query = em.createQuery("SELECT f FROM FlightScheduleEntity f JOIN f.flightSchedulePlan p JOIN p.flight t JOIN t.flightRoute m WHERE  m.origin.airportId = :inOrg AND m.destination.airportId = :outDes AND  f.departure BETWEEN :inDate AND :inDate1");

//SELECT m, p FROM FlightRouteEntity m ,FlightRouteEntity p WHERE  m.origin.airportId = "1" AND p.destination.airportId = "3" AND p.origin.airportId = m.destination.airportId
//SELECT m FROM FlightRouteEntity m LEFT JOIN FlightRouteEntity p WHERE  m.origin.airportId = "1" AND p.destination.airportId = "3" AND p.origin.airportId = m.destination.airportId
            System.out.println(a1);
            System.out.println(a2);

            query.setParameter("inOrg", a1.getAirportId());
            query.setParameter("outDes", a2.getAirportId());
            query.setParameter("inDate", departure.minusDays(days));
            query.setParameter("inDate1", departure.minusDays(days + 1));
            List<FlightScheduleEntity> list = query.getResultList();
            System.out.println(list);
            return list;

        } catch (NoResultException ex) {
            throw new NoFlightsFoundOnSearchException();
        } catch (AirportNotFoundException ex) {
            Logger.getLogger(FlightReservationSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<FlightScheduleEntity> searchThreeDaysAfter(boolean connecting, boolean round, String origin, String destination, LocalDateTime departure, int days, CabinClassType cabinClass) throws NoFlightsFoundOnSearchException,AirportNotFoundException {
        try {
            AirportEntity a1 = airportSessionBean.retriveBy(origin);
            AirportEntity a2 = airportSessionBean.retriveBy(destination);
            Query query;
            query = em.createQuery("SELECT f FROM FlightScheduleEntity f JOIN f.flightSchedulePlan p JOIN p.flight t JOIN t.flightRoute m WHERE  m.origin.airportId = :inOrg AND m.destination.airportId = :outDes AND  f.departure BETWEEN :inDate AND :inDate1");

//SELECT m, p FROM FlightRouteEntity m ,FlightRouteEntity p WHERE  m.origin.airportId = "1" AND p.destination.airportId = "3" AND p.origin.airportId = m.destination.airportId
//SELECT m FROM FlightRouteEntity m LEFT JOIN FlightRouteEntity p WHERE  m.origin.airportId = "1" AND p.destination.airportId = "3" AND p.origin.airportId = m.destination.airportId
            System.out.println(a1);
            System.out.println(a2);

            query.setParameter("inOrg", a1.getAirportId());
            query.setParameter("outDes", a2.getAirportId());
            query.setParameter("inDate", departure.plusDays(days));
            query.setParameter("inDate1", departure.plusDays(days + 1));

            List<FlightScheduleEntity> list = query.getResultList();
            System.out.println(list);
            return list;

        } catch (NoResultException ex) {
            throw new NoFlightsFoundOnSearchException();
        } catch (AirportNotFoundException ex) {
        throw new AirportNotFoundException();
        }
    }

    @Override
    public List<List<FlightScheduleEntity>> searchConnectingThreeDaysAfter(boolean connecting, boolean round, String origin, String destination, LocalDateTime departure, int days, CabinClassType cabinClass) throws NoFlightsFoundOnSearchException,AirportNotFoundException {

        try {

            System.out.println("Days" + days);
            departure=departure.plusDays(days);
            System.out.println("Departure" + departure);


            List<FlightScheduleEntity> list = getOneWayAfter(connecting, round, origin, destination, departure, days, cabinClass);
            System.out.println(list.size());
            List<List<FlightScheduleEntity>> c = new ArrayList<>();
            int i = 0;
            AirportEntity a2 = airportSessionBean.retriveBy(destination);
            for (FlightScheduleEntity fs : list) {
                c.add(new ArrayList<FlightScheduleEntity>());
                Query query1 = em.createQuery("SELECT f FROM FlightScheduleEntity f JOIN f.flightSchedulePlan p JOIN p.flight t JOIN t.flightRoute m WHERE m.origin.airportId = :inOrg AND m.destination.airportId = :inDes AND f.departure BETWEEN :inDate AND :inDate1");

                query1.setParameter("inOrg", fs.getFlightSchedulePlan().getFlight().getFlightRoute().getDestination().getAirportId());
                query1.setParameter("inDes", a2.getAirportId());
                query1.setParameter("inDate", fs.getArrival());
                query1.setParameter("inDate1", fs.getArrival().plusDays(1));
                
                System.out.println("inOrg"+ fs.getFlightSchedulePlan().getFlight().getFlightRoute().getDestination().getAirportId());
                System.out.println("inDes"+a2.getAirportId());
                System.out.println("inDate"+fs.getArrival());
                System.out.println("inDate1"+ fs.getArrival().plusDays(1));
                
                System.out.println("c" + c);
                List<FlightScheduleEntity> connect = query1.getResultList();
                System.out.println(fs.getArrival());
                System.out.println(connect);

                if (!connect.isEmpty()) {
                    c.get(i).addAll(connect);
                System.out.println("c after add" + c);
                }

                i++;
            }

            
            return c;

        } catch (NoResultException ex) {
            throw new NoFlightsFoundOnSearchException();
        } catch (AirportNotFoundException ex) {
        throw new AirportNotFoundException();
        }
      
    }

    @Override
    public List<FlightScheduleEntity> getOneWayAfter(boolean connecting, boolean round, String origin, String destination, LocalDateTime departure, int days, CabinClassType cabinClass) throws NoFlightsFoundOnSearchException,AirportNotFoundException {

        try {
           // System.out.println(origin);
            AirportEntity a1 = airportSessionBean.retriveBy(origin);
            Query query = em.createQuery("SELECT f FROM FlightScheduleEntity f JOIN f.flightSchedulePlan p JOIN p.flight t JOIN t.flightRoute m WHERE m.origin.airportId = :inOrg AND f.departure BETWEEN :inDate11 AND :inDate12");
            System.out.println("MaxOutdate" +departure.plusDays(1));
            query.setParameter("inOrg", a1.getAirportId());
            query.setParameter("inDate11", departure);
            query.setParameter("inDate12", departure.plusDays(1));

            List<FlightScheduleEntity> list = query.getResultList();
           System.out.println("List:" +list);
           // System.out.println(list);
            return list;
        } catch (NoResultException ex) {
            throw new NoFlightsFoundOnSearchException();
        } catch (AirportNotFoundException ex) {
        throw new AirportNotFoundException();
        }
    

   
    }
    
      @Override
    public List<List<FlightScheduleEntity>> searchConnectingThreeDaysBefore(boolean connecting, boolean round, String origin, String destination, LocalDateTime departure, int days, CabinClassType cabinClass) throws NoFlightsFoundOnSearchException ,AirportNotFoundException{

        try {

                        departure=departure.minusDays(days);

            List<FlightScheduleEntity> list = getOneWayBefore(connecting, round, origin, destination, departure, days, cabinClass);

            List<List<FlightScheduleEntity>> c = new ArrayList<>();
            int i = 0;
            AirportEntity a2 = airportSessionBean.retriveBy(destination);
            for (FlightScheduleEntity fs : list) {
                c.add(new ArrayList<FlightScheduleEntity>());
                Query query1 = em.createQuery("SELECT f FROM FlightScheduleEntity f JOIN f.flightSchedulePlan p JOIN p.flight t JOIN t.flightRoute m WHERE m.origin.airportId = :inOrg AND m.destination.airportId = :inDes AND f.departure BETWEEN :inDate AND :inDate1");

                query1.setParameter("inOrg", fs.getFlightSchedulePlan().getFlight().getFlightRoute().getDestination().getAirportId());
                query1.setParameter("inDes", a2.getAirportId());
                query1.setParameter("inDate", fs.getArrival());
                query1.setParameter("inDate1", fs.getArrival().plusDays(1));

                List<FlightScheduleEntity> connect = query1.getResultList();
                System.out.println(fs.getArrival());
                System.out.println(connect);

                if (!connect.isEmpty()) {
                    c.get(i).addAll(connect);
                }

                i++;
            }

            System.out.println(c);

            return c;

        } catch (NoResultException ex) {
            throw new NoFlightsFoundOnSearchException();
        } catch (AirportNotFoundException ex) {
        throw new AirportNotFoundException();
        }
    }

    @Override
    public List<FlightScheduleEntity> getOneWayBefore(boolean connecting, boolean round, String origin, String destination, LocalDateTime departure, int days, CabinClassType cabinClass) throws NoFlightsFoundOnSearchException,AirportNotFoundException {

        try {
            System.out.println(origin);
            AirportEntity a1 = airportSessionBean.retriveBy(origin);
            Query query = em.createQuery("SELECT f FROM FlightScheduleEntity f JOIN f.flightSchedulePlan p JOIN p.flight t JOIN t.flightRoute m WHERE m.origin.airportId = :inOrg AND f.departure BETWEEN :inDate11 AND :inDate12");
            System.out.println(departure.plusDays(1));
            query.setParameter("inOrg", a1.getAirportId());
            query.setParameter("inDate11", departure.plusDays(days));
            query.setParameter("inDate12", departure.plusDays(days+1));

            List<FlightScheduleEntity> list = query.getResultList();

            System.out.println(list);
            return list;
        } catch (NoResultException ex) {
            throw new NoFlightsFoundOnSearchException();
        } catch (AirportNotFoundException ex) {
        throw new AirportNotFoundException();
        }
        
    }

    @Override
    public List<FlightScheduleEntity> getDestination(boolean connecting, boolean round, String origin, String destination, LocalDateTime departure, int days, CabinClassType cabinClass) throws NoFlightsFoundOnSearchException,AirportNotFoundException {

        try {
            AirportEntity a1 = airportSessionBean.retriveBy(origin);
            AirportEntity a2= airportSessionBean.retriveBy(destination);

            Query query = em.createQuery("SELECT f FROM FlightScheduleEntity f JOIN f.flightSchedulePlan p JOIN p.flight t JOIN t.flightRoute m WHERE m.origin.airportId = :inOrg AND m.destination.airportId = :inDes AND f.departure BETWEEN :inDate AND :inDate1");

                                query.setParameter("inOrg", a1.getAirportId());
                                query.setParameter("inDes", a2.getAirportId());
                                query.setParameter("inDate", departure);
                                query.setParameter("inDate1", departure.plusDays(1));
                   

                    List<FlightScheduleEntity> list = query.getResultList();
            
            System.out.println(list);
            return list;
        } catch (NoResultException ex) {
            throw new NoFlightsFoundOnSearchException();
        } catch (AirportNotFoundException ex) {
        throw new AirportNotFoundException();
        }
        
    }
    
    public List<FlightReservationEntity> getCabinClassTypeReservations(CabinClassType type, FlightScheduleEntity fs) {
        Query query = em.createQuery("SELECT fr FROM FlightReservationEntity fr WHERE fr.flightSchedule = :inFlightSchedule AND fr.cabinClassType = :inCabinClassType");
         query.setParameter("inFlightSchedule", fs);
         query.setParameter("inCabinClassType", type);
         
         query.getResultList();
         
         return  query.getResultList();
         
    }

}
