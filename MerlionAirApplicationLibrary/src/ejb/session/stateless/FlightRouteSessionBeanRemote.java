/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.FlightRouteEntity;
import java.util.List;
import javax.ejb.Remote;
import util.exception.FlightRouteNotFoundException;

/**
 *
 * @author quahjingxin
 */
@Remote
public interface FlightRouteSessionBeanRemote {
     public Long createNewFlightRoute(FlightRouteEntity newFlightRouteEntity);
         public FlightRouteEntity createNewComplementaryReturnRoute(Long flightRouteId, FlightRouteEntity complementaryReturnRoute) throws FlightRouteNotFoundException;
public List<FlightRouteEntity> retrieveAllFlightRoutes();
public FlightRouteEntity retrieveFlightRouteByFlightRouteId(Long flightRouteId) throws FlightRouteNotFoundException;
public void deleteFlightRoute(Long flightRouteId) throws FlightRouteNotFoundException;
            public FlightRouteEntity retrieveFlightRouteByAirportCode(String departure, String arrival) throws FlightRouteNotFoundException;


}
