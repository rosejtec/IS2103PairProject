/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CreditCardEntity;
import entity.FlightReservationDetailsEntity;
import entity.FlightReservationEntity;
import entity.FlightScheduleEntity;
import entity.PassengerEntity;
import entity.SeatsInventoryEntity;
import java.util.List;
import javax.ejb.Local;
import util.enumeration.CabinClassType;
import util.enumeration.FlightScheduleEntityNotFoundException;

/**
 *
 * @author quahjingxin
 */
@Local
public interface ReservationSessionBeanLocal {
     Integer getFare(FlightScheduleEntity fs, CabinClassType t); 
     FlightScheduleEntity retrievebyId(Long id) throws FlightScheduleEntityNotFoundException;

}
