/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CreditCardEntity;
import entity.CustomerEntity;
import entity.FareEntity;
import entity.FlightReservationDetailsEntity;
import entity.FlightReservationEntity;
import entity.FlightScheduleEntity;
import entity.PassengerEntity;
import entity.SeatPassengeEntity;
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
        
        Query q= em.createQuery("SELECT f FROM FareEntity f WHERE f.flightSchedulePlan.fightSchedulePlanId=:t  AND f.cabinClassType=:fare ORDER BY f.fareAmount ASC");
        q.setParameter("t",fs.getFlightSchedulePlan().getFightSchedulePlanId() ); 
        q.setParameter("fare", t);
        
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
    public void updateSeat(SeatsInventoryEntity seat,CabinClassType type,int passenger){

                seat.updateAvailableSeats(passenger);
                seat.updateReservedSeats(passenger);   
               if (type == CabinClassType.F) {
                    seat.setAvailableF(seat.getAvailableF()-passenger);
                    seat.setReservedF(seat.getReservedF()+passenger);

                } else if (type == CabinClassType.W) {
                    seat.setAvailableW(seat.getAvailableW()-passenger);
                    seat.setReservedW(seat.getReservedW()+passenger);
                } else if (type== CabinClassType.Y) {
                    seat.setAvailableY(seat.getAvailableY()-passenger);
                    seat.setReservedY(seat.getReservedY()+passenger);

                } else {
                     
                    seat.setAvailableJ(seat.getAvailableJ()-passenger);
                    seat.setReservedJ(seat.getReservedJ()+passenger);

                }
     
        
        em.merge(seat);
      seat.setBalanceSeats();
      em.flush();
    }
    
    
    
     public FlightReservationEntity reserveFlight(FlightReservationEntity book, List<FlightReservationDetailsEntity> inbound,List<FlightReservationDetailsEntity> outbond, List<PassengerEntity> pass, Integer passenger,CreditCardEntity c, CustomerEntity customer){
            
              em.persist(c);
              em.flush();
              book.setCard(c);
              em.persist(book);
              em.flush();
      
              for(FlightReservationDetailsEntity frd:inbound){
             
                  book.getInBound().add(frd);
                  frd.setFlightReservation(book);
                 
                  
              }
              
             for(FlightReservationDetailsEntity frd:outbond){
                  book.getOutBound().add(frd);
               
                 frd.setFlightReservation(book);
             
              }
             
             book.setCustomer(customer);
             customer.getFlightReservations().add(book);
           
            
             return book;
          }
     
     
      public FlightReservationDetailsEntity reserveFlightReservation(FlightReservationDetailsEntity inbound, SeatPassengeEntity seat){
     
           em.persist(seat);
           
           em.persist(inbound);
           
           inbound.getSeats().add(seat);
           return inbound;
      }
      
      public PassengerEntity persistPassenger(PassengerEntity p) {
            em.persist(p);
            em.flush();
            
            return p;
      }

    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

  

   
   

}
