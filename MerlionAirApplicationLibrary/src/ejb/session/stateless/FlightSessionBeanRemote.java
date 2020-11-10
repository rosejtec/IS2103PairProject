/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.FlightEntity;
import java.util.List;
import javax.ejb.Remote;
import util.exception.FlightNotFoundException;
import util.exception.UpdateFlightException;

/**
 *
 * @author quahjingxin
 */
@Remote
public interface FlightSessionBeanRemote {

public FlightEntity createNewFlight(FlightEntity newFlightEntity);
    public List<FlightEntity> retrieveAllFlights();

    public FlightEntity retrieveFlightByFlightNumber(String flightNumber) throws FlightNotFoundException;

    public FlightEntity retrieveFlightByFlightId(Long flightId) throws FlightNotFoundException;

    public void updateFlight(FlightEntity flightEntity) throws FlightNotFoundException, UpdateFlightException;

    public void deleteFlight(Long flightId) throws FlightNotFoundException;

    public Boolean checkByFlightNumber(String flightNum);
    
}
