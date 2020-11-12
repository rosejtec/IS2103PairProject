/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.FlightEntity;
import javax.ejb.Local;
import util.exception.FlightNotFoundException;

/**
 *
 * @author quahjingxin
 */
@Local
public interface FlightSessionBeanLocal {
    
    public FlightEntity createNewFlight(FlightEntity newFlightEntity);
     public FlightEntity retrieveFlightByFlightNumber(String flightNum) throws FlightNotFoundException;
}
