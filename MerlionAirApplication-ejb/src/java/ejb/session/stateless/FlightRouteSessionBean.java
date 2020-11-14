/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.FlightRouteEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.FlightRouteNotFoundException;

/**
 *
 * @author quahjingxin
 */
@Stateless
public class FlightRouteSessionBean implements FlightRouteSessionBeanRemote, FlightRouteSessionBeanLocal {

    @PersistenceContext(unitName = "MerlionAirApplication-ejbPU")
    private EntityManager em;

    //create new flight route use case
    public Long createNewFlightRoute(FlightRouteEntity newFlightRouteEntity)
    {
            em.persist(newFlightRouteEntity);
            em.flush();

            return newFlightRouteEntity.getFlightRouteId();
    }

    public FlightRouteEntity createNewComplementaryReturnRoute(Long flightRouteId, FlightRouteEntity complementaryReturnRoute) throws FlightRouteNotFoundException
    {
        try
        {
            FlightRouteEntity flightRouteEntity = retrieveFlightRouteByFlightRouteId(flightRouteId);
            
            //
            
            flightRouteEntity.setComplementaryReturnRoute(complementaryReturnRoute);
            complementaryReturnRoute.setFlightRouteId(flightRouteId);
            em.persist(complementaryReturnRoute);
            em.flush();

            return complementaryReturnRoute;
        }
        catch(FlightRouteNotFoundException ex)
        {
            throw new FlightRouteNotFoundException("Unable to create new complementary return route as flight route record does not exist!");
        }
    }
    
    public List<FlightRouteEntity> retrieveAllFlightRoutes()
    {
        Query query = em.createQuery("SELECT f FROM FlightRouteEntity f ORDER BY f.origin.name ASC");
        
        return query.getResultList();
    }
    
    //ascending order!!!!!!!!!!!!!!!
    public FlightRouteEntity retrieveFlightRouteByFlightRouteId(Long flightRouteId) throws FlightRouteNotFoundException
    {
        
        FlightRouteEntity flightRouteEntity = em.find(FlightRouteEntity.class, flightRouteId);
        
        if(flightRouteEntity != null)
        {
            System.out.println("Here");
            return flightRouteEntity;
        }
        else
        {
            System.out.println("Not Here");
            throw new FlightRouteNotFoundException("Flight Route ID " + flightRouteId + " does not exist!");
        }   
        
    }
    
    @Override
    public FlightRouteEntity retrieveFlightRouteByAirportCode(String departure, String arrival) throws FlightRouteNotFoundException
    {
       try{ 
       Query query = em.createQuery("SELECT fr FROM FlightRouteEntity fr WHERE fr.origin.code = :inDeparture AND fr.destination.code = :inArrival");
       query.setParameter("inDeparture", departure);
       query.setParameter("inArrival", arrival);
        
       FlightRouteEntity fr = (FlightRouteEntity)query.getSingleResult();
       fr.getFlights().size();
       fr.getComplementaryReturnRoute();
       fr.getDestination();
       fr.getOrigin();
       
              return fr;
       } catch(NoResultException ex){
           throw new FlightRouteNotFoundException();
       }
               
        
    }
     
    //delete flight route use case
    public void deleteFlightRoute(Long flightRouteId) throws FlightRouteNotFoundException
    {
        FlightRouteEntity flightRouteToDelete = em.find(FlightRouteEntity.class, flightRouteId);
 
        if (flightRouteToDelete == null) {
            throw new FlightRouteNotFoundException("Flight Route ID " + flightRouteId + " does not exist!");
        }
        
        if(flightRouteToDelete.getFlights().size()==0) {
            flightRouteToDelete.setComplementaryReturnRoute(null);
            //flightRouteToDelete.setFlights(null);
  //          em.remove(flightRouteToDelete.getComplementaryReturnRoute());
            em.remove(flightRouteToDelete);
            
            em.flush();
        } else {
            flightRouteToDelete.setDisabled(true);
        }
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
}
