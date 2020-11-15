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
import javax.ejb.Remote;
import util.enumeration.CabinClassType;
import util.enumeration.FlightScheduleEntityNotFoundException;

/**
 *
 * @author quahjingxin
 */
@Remote
public interface ReservationSessionBeanRemote {
    Integer getFare(FlightScheduleEntity fs, CabinClassType t); 
     FlightScheduleEntity retrievebyId(Long id) throws FlightScheduleEntityNotFoundException;
      
 void updateSeat(SeatsInventoryEntity seat,CabinClassType type,int passenger);

    public FlightReservationEntity reserveFlight(FlightReservationEntity book, List<FlightReservationDetailsEntity> inbound, List<FlightReservationDetailsEntity> outbond, List<PassengerEntity> pass, Integer passenger, CreditCardEntity c, CustomerEntity customer);

    public FlightReservationDetailsEntity reserveFlightReservation(FlightReservationDetailsEntity inbound, SeatPassengeEntity seat);

    public PassengerEntity persistPassenger(PassengerEntity p);

}
