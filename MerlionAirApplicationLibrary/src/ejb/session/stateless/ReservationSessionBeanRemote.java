/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.FareEntity;
import entity.FlightScheduleEntity;
import javax.ejb.Remote;
import util.enumeration.CabinClassType;

/**
 *
 * @author quahjingxin
 */
@Remote
public interface ReservationSessionBeanRemote {
    Integer getFare(FlightScheduleEntity fs, CabinClassType t);
}
