/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AirportEntity;
import entity.CabinClassConfigurationEntity;
import entity.FareEntity;
import entity.FlightEntity;
import entity.FlightScheduleEntity;
import entity.FlightSchedulePlanEntity;
import entity.SeatsInventoryEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.enumeration.CabinClassType;
import util.exception.AirportNotFoundException;
import util.exception.FareNotFoundException;
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

    @Override
    public FlightSchedulePlanEntity createFlightSchedulePlan(FlightSchedulePlanEntity fsp, List<FlightScheduleEntity> fs, List<FareEntity> f, FlightEntity flight) {

        em.persist(fsp);
        em.flush();
        System.out.println("Here");
        System.out.println(fsp.getFightSchedulePlanId());

        fsp.setFlight(flight);
        //map flight schedules
        for (FlightScheduleEntity d : fs) {
            d.setFlightSchedulePlan(fsp);
            em.persist(d);
            em.flush();
            fsp.getFlightSchedules().add(d);
            SeatsInventoryEntity sie = new SeatsInventoryEntity(flight.getAircraftConfiguration().getMaxSeats(), 0);
            for (CabinClassConfigurationEntity cc : flight.getAircraftConfiguration().getCabinClassConfigurations()) {
                if (cc.getCabinClassType() == CabinClassType.F) {
                    sie.setAvailableF(cc.getMaximum());
                } else if (cc.getCabinClassType() == CabinClassType.W) {
                    sie.setAvailableW(cc.getMaximum());

                } else if (cc.getCabinClassType() == CabinClassType.Y) {
                    sie.setAvailableY(cc.getMaximum());

                } else {
                    sie.setAvailableJ(cc.getMaximum());

                }
            }

            System.out.println(fsp.getFlightSchedules().size());
            em.persist(sie);
            em.flush();
            d.setSeatsInventory(sie);
            sie.setFlightSchedule(d);
        }

        //map fare entity
        for (FareEntity fe : f) {
            
            fe.setFlightSchedulePlan(fsp);
            em.persist(fe);
            em.flush();
            fsp.getFares().add(fe);
           System.out.println("NotComp" +fe.getFareId());
        }
        
        //em.flush();
        fsp.getFlightSchedules().size();
        flight.getFlightSchedulePlans().size();
        flight.getFlightSchedulePlans().add(fsp);
        em.flush();
        return fsp;

    }

    @Override
    public AirportEntity retriveBy(String code) throws AirportNotFoundException
    {
        
        Query q = em.createQuery("SELECT a FROM AirportEntity a WHERE a.code = :inCode");
        q.setParameter("inCode", code);
        
        try
        {
        AirportEntity a = (AirportEntity) q.getSingleResult();
        return a;
        }
         catch(NoResultException | NonUniqueResultException ex)
        {
         
            throw new AirportNotFoundException("Airport code " + code + " does not exist!");
        }
    }
    
    @Override
    public FlightSchedulePlanEntity createCompFlightSchedulePlan(FlightSchedulePlanEntity fsp,FlightSchedulePlanEntity original, List<FlightScheduleEntity> fs, List<FareEntity> f, FlightEntity flight) {

        em.persist(fsp);
        em.flush();
        
        original.setComplementaryFsp(fsp);
        System.out.println("Here");
        System.out.println(fsp.getFightSchedulePlanId());

        //map flight schedules
        for (FlightScheduleEntity d : fs) {
            d.setFlightSchedulePlan(fsp);
            em.persist(d);
            em.flush();
            fsp.getFlightSchedules().add(d);
            SeatsInventoryEntity sie = new SeatsInventoryEntity(flight.getAircraftConfiguration().getMaxSeats(), 0);
            for (CabinClassConfigurationEntity cc : flight.getAircraftConfiguration().getCabinClassConfigurations()) {
                if (cc.getCabinClassType() == CabinClassType.F) {
                    sie.setAvailableF(cc.getMaximum());
                } else if (cc.getCabinClassType() == CabinClassType.W) {
                    sie.setAvailableW(cc.getMaximum());

                } else if (cc.getCabinClassType() == CabinClassType.Y) {
                    sie.setAvailableY(cc.getMaximum());

                } else {
                    sie.setAvailableJ(cc.getMaximum());

                }
            }

            System.out.println(fsp.getFlightSchedules().size());
            em.persist(sie);
            em.flush();
            d.setSeatsInventory(sie);
            sie.setFlightSchedule(d);
        }

        //map fare entity
        for (FareEntity fe : f) {
             fe.setFlightSchedulePlan(fsp);
            em.persist(fe);
            em.flush();
            fsp.getFares().add(fe);
           System.out.println("Comp" +fe.getFareId());
        }
        //em.flush();
        fsp.getFlightSchedules().size();
        flight.getFlightSchedulePlans().size();
        flight.getFlightSchedulePlans().add(fsp);
        em.flush();
        return fsp;

    }
    /*
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
     */
    public List<FlightSchedulePlanEntity> retrieveAllFlightSchedulePlans() {
        Query query = em.createQuery("SELECT f FROM FlightSchedulePlanEntity f ORDER BY f.flight.flightNum ASC");
        return query.getResultList();
    }

    public void deleteFlightSchedulePlan(Long flightSchedulePlanId) throws FlightSchedulePlanNotFoundException {
        FlightSchedulePlanEntity fspToDelete = em.find(FlightSchedulePlanEntity.class, flightSchedulePlanId);

        if (fspToDelete == null) {
            throw new FlightSchedulePlanNotFoundException("Flight Route ID " + flightSchedulePlanId + " does not exist!");
        }

        if (fspToDelete.getFlight() == null) {
           // fspToDelete.setComplementaryFsp(null);
            //em.remove(fspToDelete.getComplementaryFlightSchedulePlan());
            fspToDelete.setFares(null);
            fspToDelete.setFlightSchedules(null);
            em.remove(fspToDelete);
            em.flush();
        } else {
            fspToDelete.setDisabled(true);
        }
    }

    public FlightSchedulePlanEntity retrieveFlightSchedulePlanById(Long flightSchedulePlanId) throws FlightSchedulePlanNotFoundException {
        FlightSchedulePlanEntity fsp = em.find(FlightSchedulePlanEntity.class, flightSchedulePlanId);

        if (fsp != null) {
            fsp.getFlightSchedules().size();
            fsp.getFares().size();
            return fsp;
        } else {
            throw new FlightSchedulePlanNotFoundException();
        }
    }

    public void deleteFlightSchedule(Long flightSchedulePlanId, Long flightScheduleId) throws FlightSchedulePlanNotFoundException, FlightScheduleNotFoundException {

        FlightSchedulePlanEntity fsp = retrieveFlightSchedulePlanById(flightSchedulePlanId);
        FlightScheduleEntity fsToDelete = em.find(FlightScheduleEntity.class, flightScheduleId);

        if (fsToDelete == null) {
            throw new FlightScheduleNotFoundException("Flight Schedule ID " + flightScheduleId + "does not exist!");
        }

        fsp.getFlightSchedules().remove(fsToDelete);
        em.remove(fsToDelete);
    }

    public FlightScheduleEntity retrieveFlightScheduleById(Long fsId) throws FlightScheduleNotFoundException {
        FlightScheduleEntity fs = em.find(FlightScheduleEntity.class, fsId);

        if (fs != null) {
            return fs;
        } else {
            throw new FlightScheduleNotFoundException("Flight ScheduleID " + fsId + " does not exist!");
        }
    }

    public FareEntity retrieveFareByFareId(Long fareId) throws FareNotFoundException {
        FareEntity fare = em.find(FareEntity.class, fareId);

        if (fare != null) {
            return fare;
        } else {
            throw new FareNotFoundException("Fare ID " + fareId + " does not exist!");
        }
    }

    @Override
    public FareEntity createNewFare(FareEntity flightScheduleEntity) {
        em.persist(flightScheduleEntity);
        em.flush();

        return flightScheduleEntity;
    }

    @Override
    public FlightScheduleEntity createNewFlightSchedule(FlightScheduleEntity flightScheduleEntity) {

        SeatsInventoryEntity sie = new SeatsInventoryEntity();
        em.persist(sie);
        flightScheduleEntity.setSeatsInventory(new SeatsInventoryEntity());

        return flightScheduleEntity;
    }

    public void updateFare(FareEntity fare) throws FareNotFoundException {
        // if (fare != null && fare.getFareId()) != null)
        // {
        FareEntity fareToUpdate = retrieveFareByFareId(fare.getFareId());

        // if(fareToUpdate.getFareId().equals(fare.getFareId()))
        //{
        //things to be updated
        fareToUpdate.setFareBasisCode(fare.getFareBasisCode());
        fareToUpdate.setFareAmount(fare.getFareAmount());
        //   }
        // else
        // {
        //     throw new UpdateFlightException("Flight number of flight record to be updated does not match the existing record!");
        //}
    }
    // else 
    //{ 
    //   throw new FlightNotFoundException("Flight ID not provided for product to be updated");
    // }

//em.update(flightEntity);
}
