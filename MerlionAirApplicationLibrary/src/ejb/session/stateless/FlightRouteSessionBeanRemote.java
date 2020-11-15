/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.FlightRouteEntity;
import java.util.List;
import javax.ejb.Remote;
import util.exception.AirportNotFoundException;
import util.exception.FlightRouteNotFoundException;

/**
 *
 * @author quahjingxin
 */
@Remote
public interface FlightRouteSessionBeanRemote {
     public Long createNewFlightRoute(String origin, String destination, Boolean createComplementary) throws AirportNotFoundException;
         public FlightRouteEntity createNewComplementaryReturnRoute(Long flightRouteId, FlightRouteEntity complementaryReturnRoute) throws FlightRouteNotFoundException;
public List<FlightRouteEntity> retrieveAllFlightRoutes();
public FlightRouteEntity retrieveFlightRouteByFlightRouteId(Long flightRouteId) throws FlightRouteNotFoundException;
public void deleteFlightRoute(Long flightRouteId) throws FlightRouteNotFoundException;
            public FlightRouteEntity retrieveFlightRouteByAirportCode(String departure, String arrival) throws FlightRouteNotFoundException;


}
