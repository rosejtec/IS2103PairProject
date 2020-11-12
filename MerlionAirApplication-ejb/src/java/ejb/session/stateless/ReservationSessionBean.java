/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CreditCardEntity;
import entity.FareEntity;
import entity.FlightReservationDetailsEntity;
import entity.FlightReservationEntity;
import entity.FlightScheduleEntity;
import entity.PassengerEntity;
import entity.SeatsInventoryEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.enumeration.CabinClassType;
import util.enumeration.FlightScheduleEntityNotFoundException;

/**
 *
 * @author quahjingxin
 */
@Stateless
public class ReservationSessionBean implements ReservationSessionBeanRemote, ReservationSessionBeanLocal {

    @PersistenceContext(unitName = "MerlionAirApplication-ejbPU")
    private EntityManager em;

    public void persist(Object object) {
        em.persist(object);
    }

    @Override
    public Integer getFare(FlightScheduleEntity fs, CabinClassType t){
        
        Query q= em.createQuery("SELECT f FROM FareEntity f WHERE f.flightSchedulePlan.fightSchedulePlanId=:t  AND f.type=:f ORDER BY f.fareAmount ASC");
        q.setParameter("t",fs.getFlightSchedulePlan().getFightSchedulePlanId() ); 
        q.setParameter("f", t);
        
        List<FareEntity> fare =  q.getResultList();
        if(fare.size()>0){
        return Integer.parseInt(fare.get(0).getFareAmount());
        } else {
            return -1;
        }
    }
    
    @Override
    public FlightScheduleEntity retrievebyId(Long id) throws FlightScheduleEntityNotFoundException{
     FlightScheduleEntity fs= em.find(FlightScheduleEntity.class, id);
     
     if(fs!=null){
         return fs;
     } else {
         throw new FlightScheduleEntityNotFoundException();
     }
    }
 
    
    @Override
    public void updateSeat(SeatsInventoryEntity seat){
      em.merge(seat);
      em.flush();
    }
    
    
    
          public FlightReservationEntity reserveFlight(FlightReservationEntity book, List<FlightReservationDetailsEntity> inbound,List<FlightReservationDetailsEntity> outbond, List<PassengerEntity> pass, Integer passenger,CreditCardEntity c){
            
              em.persist(c);
              em.flush();
              book.setCard(c);
              em.persist(book);
              em.flush();
              

             

              
              for(PassengerEntity p:pass){
                  em.persist(p);
                  em.flush();
                  book.getPassenger().add(p);
              }
              for(FlightReservationDetailsEntity frd:inbound){
                  em.persist(frd);
                  em.flush();
                  book.getInBound().add(frd);
                  frd.setReservation(book);
                 
                  
              }
              
             for(FlightReservationDetailsEntity frd:outbond){
                  em.persist(frd);
                  em.flush();
                  book.getOutBound().add(frd);
               
                 frd.setReservation(book);
             
              }
             

            
             return book;
          }
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

}
