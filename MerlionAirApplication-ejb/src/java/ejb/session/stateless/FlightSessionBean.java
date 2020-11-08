/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.FlightEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.FlightNotFoundException;
import util.exception.UpdateFlightException;
/**
 *
 * @author quahjingxin
 */
@Stateless
public class FlightSessionBean implements FlightSessionBeanRemote, FlightSessionBeanLocal {

    @PersistenceContext(unitName = "MerlionAirApplication-ejbPU")
    private EntityManager em;

    public FlightEntity createNewFlight(FlightEntity newFlightEntity)
    {
        em.persist(newFlightEntity);
        em.flush();
        

        return newFlightEntity;
    }

    
    public List<FlightEntity> retrieveAllFlights()
    {
        Query query = em.createQuery("SELECT f FROM FlightEntity f ORDER BY f.flightNumber ASC ");
        
        return query.getResultList();
    }
    
    public FlightEntity retrieveFlightByFlightNumber(String flightNumber) throws FlightNotFoundException
    { 
       Query query = em.createQuery("SELECT f FROM FlightEntity f WHERE f.flightNumber = :inFlightNumber");
       query.setParameter("inFlightNumber", flightNumber);
       
       try
        {
            return (FlightEntity)query.getSingleResult();
        }
        catch(NoResultException | NonUniqueResultException ex)
        {
            throw new FlightNotFoundException("Flight number " + flightNumber + " does not exist!");
        }
    }   
    
    public FlightEntity retrieveFlightByFlightId(Long flightId) throws FlightNotFoundException
    {
        FlightEntity flightEntity = em.find(FlightEntity.class, flightId);
        
        if(flightEntity != null)
        {
            return flightEntity;
        }
        else
        {
            throw new FlightNotFoundException("Flight ID " + flightId + " does not exist!");
        }               
    }
    
    public void updateFlight(FlightEntity flightEntity) throws FlightNotFoundException, UpdateFlightException
    {
        if (flightEntity != null && flightEntity.getFlightId() != null)
        {
           FlightEntity flightEntityToUpdate = retrieveFlightByFlightId(flightEntity.getFlightId());
           
           if(flightEntityToUpdate.getFlightNumber().equals(flightEntity.getFlightNumber()))
           {
               //things to be updated
               flightEntityToUpdate.setFlightRoute(flightEntity.getFlightRoute());
               flightEntityToUpdate.setAircraftConfiguration(flightEntity.getAircraftConfiguration());
            }
            else
            {
                throw new UpdateFlightException("Flight number of flight record to be updated does not match the existing record");
            }
        }
        else 
        { 
            throw new FlightNotFoundException("Flight ID not provided for product to be updated");
        }

//em.update(flightEntity);
    }
    
   public void deleteFlight(Long flightId) throws FlightNotFoundException
    {
        FlightEntity flightEntityToDelete = em.find(FlightEntity.class, flightId);
 
        if (flightEntityToDelete == null) {
            throw new FlightNotFoundException("Flight ID " + flightId + " does not exist!");
        }
        
        if(flightEntityToDelete.getFlightSchedulePlans() == null) {
            em.remove(flightEntityToDelete);
        } else {
            flightEntityToDelete.setDisabled(true);
        }
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
