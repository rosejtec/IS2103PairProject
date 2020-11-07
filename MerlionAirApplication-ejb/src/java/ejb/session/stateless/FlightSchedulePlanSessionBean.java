/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.FareEntity;
import entity.FlightScheduleEntity;
import entity.FlightSchedulePlanEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.FlightRouteNotFoundException;
import util.exception.FlightScheduleNotFoundException;
import util.exception.FlightSchedulePlanNotFoundException;

/**
 *
 * @author quahjingxin
 */
@Stateless
public class FlightSchedulePlanSessionBean implements FlightSchedulePlanSessionBeanRemote, FlightSchedulePlanSessionBeanLocal {

    @PersistenceContext(unitName = "MerlionAirApplication-ejbPU")
    private EntityManager em;

    
    public Long createFlightSchedulePlan(FlightSchedulePlanEntity fsp, List<FlightScheduleEntity> fs){
        em.persist(fsp);
        em.flush();
        
        for(int i = 0; i< fs.size(); i++){
            FlightScheduleEntity f=fs.get(i);
            em.persist(f);
            em.flush();
            fsp.getFlightSchedules().add(f);
            f.setFlightSchedulePlan(fsp);
        }
        
        em.flush();
        return fsp.getFightSchedulePlanId();
    }

    public FlightSchedulePlanEntity createNewComplementaryFlightSchedulePlan(Long flightSchedulePlanId, FlightSchedulePlanEntity complementaryFsp) throws FlightSchedulePlanNotFoundException
    {
        try
        {
            FlightSchedulePlanEntity fsp = retrieveFlightSchedulePlanById(flightSchedulePlanId);
            
            em.persist(complementaryFsp);
            
            fsp.setComplementaryFsp(complementaryFsp);
            complementaryFsp.setFightSchedulePlanId(flightSchedulePlanId);
            
            em.flush();

            return complementaryFsp;
        }
        catch(FlightSchedulePlanNotFoundException ex)
        {
            throw new FlightSchedulePlanNotFoundException("Unable to create new complementary return route as the flight route record does not exist");
        }
    }
    
    public FlightSchedulePlanEntity retrieveFlightSchedulePlanById(Long flightSchedulePlanId) throws FlightSchedulePlanNotFoundException
    {
        
        FlightSchedulePlanEntity fsp = em.find(FlightSchedulePlanEntity.class, flightSchedulePlanId);
        
        if(fsp != null)
        {
            return fsp;
        }
        else
        {
            throw new FlightSchedulePlanNotFoundException("Flight Schedule Plan ID " + flightSchedulePlanId + " does not exist!");
        }   
    }
    
    public FareEntity createNewFare(Long flightSchedulePlanId, FareEntity newFare) throws FlightSchedulePlanNotFoundException
    {
         try
        {
            FlightSchedulePlanEntity fsp = retrieveFlightSchedulePlanById(flightSchedulePlanId);
            
            em.persist(fsp);
            
            newFare.setFlightSchedulePlan(fsp);
            fsp.getFares().add(newFare);
            
            em.flush();

            return newFare;
        }
        catch(FlightSchedulePlanNotFoundException ex)
        {
            throw new FlightSchedulePlanNotFoundException("Unable to create new complementary return route as the flight route record does not exist");
        }
    }
    
    public List<FlightSchedulePlanEntity> retrieveAllFlightSchedulePlans()
    {
        Query query = em.createQuery("SELECT f FROM FlightSchedulePlanEntity f ORDER BY f.origin.flightNum ASC ORDER BY f.flightSchedules.departure");
        return query.getResultList();
    }
    
    public void deleteFlightSchedulePlan(Long flightSchedulePlanId) throws FlightSchedulePlanNotFoundException
    {
        FlightSchedulePlanEntity fspToDelete = em.find(FlightSchedulePlanEntity.class, flightSchedulePlanId);
 
        if (fspToDelete == null) {
            throw new FlightSchedulePlanNotFoundException("Flight Route ID " + flightSchedulePlanId + " does not exist!");
        }
        
        if(fspToDelete.getFlight() == null) {
            fspToDelete.setComplementaryFsp(null);
            em.remove(fspToDelete.getComplementaryFlightSchedulePlan());
            fspToDelete.setFares(null);
            fspToDelete.setFlightSchedules(null);
            em.remove(fspToDelete);
            em.flush();
        } else {
            fspToDelete.setDisabled(true);
        }
    } 
    
    public void deleteFlightSchedule(Long flightSchedulePlanId, Long flightScheduleId) throws FlightSchedulePlanNotFoundException, FlightScheduleNotFoundException 
    {
        
        FlightSchedulePlanEntity fsp = retrieveFlightSchedulePlanById(flightSchedulePlanId);
        FlightScheduleEntity fsToDelete = em.find(FlightScheduleEntity.class, flightScheduleId);
        
        if (fsToDelete == null) {
            throw new FlightScheduleNotFoundException("Flight Schedule ID " + flightScheduleId + "does not exist!"); 
        }
        
        /*
        //need to check for tickets to do delete and change in fare
        
        if (fsToDelete.)
            fsp.getFlightSchedules().remove(fsToDelete);
            em.remove(fsToDelete);
      */
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
