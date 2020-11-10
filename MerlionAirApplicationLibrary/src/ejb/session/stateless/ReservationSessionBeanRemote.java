/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.FareEntity;
import entity.FlightReservationDetailsEntity;
import entity.FlightReservationEntity;
import entity.FlightScheduleEntity;
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
FlightReservationEntity reserveFlight(FlightReservationEntity book, List<FlightReservationDetailsEntity> inbound,List<FlightReservationDetailsEntity> outbond);
}
