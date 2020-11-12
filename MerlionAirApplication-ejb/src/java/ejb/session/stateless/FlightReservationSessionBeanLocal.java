/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AirportEntity;
import entity.FlightScheduleEntity;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import util.enumeration.CabinClassType;
import util.enumeration.NoFlightsFoundOnSearchException;
import util.exception.AirportNotFoundException;

/**
 *
 * @author leahr
 */
@Local
public interface FlightReservationSessionBeanLocal {
 public List<FlightScheduleEntity> searchSingleDay(boolean connecting, boolean round, String origin, String destination, LocalDateTime departure, int passengers, CabinClassType cabinClass) throws NoFlightsFoundOnSearchException, AirportNotFoundException;
   
    public List<FlightScheduleEntity> searchThreeDaysBefore(boolean connecting,boolean round,String origin, String destination, LocalDateTime departure, int passengers,  CabinClassType cabinClass) throws NoFlightsFoundOnSearchException, AirportNotFoundException;   
     
    public List<FlightScheduleEntity> searchThreeDaysAfter(boolean connecting, boolean round, String origin, String destination, LocalDateTime departure, int passengers,  CabinClassType cabinClass)throws NoFlightsFoundOnSearchException, AirportNotFoundException;

    public  List<List<FlightScheduleEntity>> searchConnectingThreeDaysAfter(boolean connecting, boolean round, String origin, String destination, LocalDateTime departure, int passengers,  CabinClassType cabinClass)throws NoFlightsFoundOnSearchException, AirportNotFoundException;

    public List<FlightScheduleEntity> getOneWayAfter(boolean connecting, boolean round, String origin, String destination, LocalDateTime departure, int passengers, CabinClassType cabinClass)throws NoFlightsFoundOnSearchException, AirportNotFoundException;

    public List<List<List<FlightScheduleEntity>>> searchConnectingDaysAfter(boolean connecting, boolean round, String origin, String destination, LocalDateTime departure, int days, CabinClassType cabinClass) throws NoFlightsFoundOnSearchException, AirportNotFoundException;

    public List<List<List<FlightScheduleEntity>>> searchConnectingDaysBefore(boolean connecting, boolean round, String origin, String destination, LocalDateTime departure, int days, CabinClassType cabinClass) throws NoFlightsFoundOnSearchException, AirportNotFoundException;

    public List<FlightScheduleEntity> getOneWayBefore(boolean connecting, boolean round, String origin, String destination, LocalDateTime departure, int days, CabinClassType cabinClass) throws NoFlightsFoundOnSearchException, AirportNotFoundException;

    public List<List<FlightScheduleEntity>> searchConnectingThreeDaysBefore(boolean connecting, boolean round, String origin, String destination, LocalDateTime departure, int days, CabinClassType cabinClass) throws NoFlightsFoundOnSearchException, AirportNotFoundException;


    public List<FlightScheduleEntity> getDestination(boolean connecting, boolean round, String origin, String destination, LocalDateTime departure, int days, CabinClassType cabinClass) throws NoFlightsFoundOnSearchException, AirportNotFoundException;
   
   
   
}
