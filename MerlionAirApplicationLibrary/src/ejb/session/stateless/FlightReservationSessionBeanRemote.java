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
import javax.ejb.Remote;

/**
 *
 * @author leahr
 */
@Remote
public interface FlightReservationSessionBeanRemote {

    public List<FlightScheduleEntity> searchSingleDay(boolean connecting, boolean round, String origin, String destination, Date departure, int passengers, String cabinClass);

    public List<FlightScheduleEntity> searchThreeDaysBefore(boolean connecting, boolean round, String origin, String destination, Date departure, int passengers, String cabinClass);

    public List<FlightScheduleEntity> searchThreeDaysAfter(boolean connecting, boolean round, String origin, String destination, Date departure, int passengers, String cabinClass);

    public List<FlightScheduleEntity> searchConnectingThreeDaysAfter(boolean connecting, boolean round, String origin, String destination, Date departure, int passengers, String cabinClass);
   
   

}
