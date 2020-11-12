/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.FlightRouteEntity;
import javax.ejb.Local;
import util.exception.FlightRouteNotFoundException;

/**
 *
 * @author quahjingxin
 */
@Local
public interface FlightRouteSessionBeanLocal {
     public Long createNewFlightRoute(FlightRouteEntity newFlightRouteEntity);
    public FlightRouteEntity createNewComplementaryReturnRoute(Long flightRouteId, FlightRouteEntity complementaryReturnRoute) throws FlightRouteNotFoundException;
     public FlightRouteEntity retrieveFlightRouteByFlightRouteId(Long flightRouteId) throws FlightRouteNotFoundException;

    public FlightRouteEntity retrieveFlightRouteByAirportCode(String departure, String arrival) throws FlightRouteNotFoundException;
}
