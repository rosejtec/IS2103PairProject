/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.ws;

import ejb.session.stateless.FlightReservationSessionBeanLocal;
import entity.FlightScheduleEntity;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;
import util.enumeration.CabinClassType;
import util.enumeration.NoFlightsFoundOnSearchException;

/**
 *
 * @author leahr
 */
@WebService(serviceName = "ReservationWebService")
@Stateless
public class ReservationWebService {

    @EJB
    private FlightReservationSessionBeanLocal flightReservationSessionBean;

    
    
    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "getDestination")
    public List<FlightScheduleEntity> getDestination(@WebParam(name = "origin")String origin, @WebParam(name = "destination")String destination, @WebParam(name = "depTime")int departure1, @WebParam(name = "days")int days) throws NoFlightsFoundOnSearchException {
        String date = "2020-12-08 00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        LocalDateTime departure = LocalDateTime.parse(date, formatter);      
        return flightReservationSessionBean.getDestination(true, true, origin, destination, departure, days,CabinClassType.F);
    }
    
     @WebMethod(operationName = "getOneWayAfter")
     public List<FlightScheduleEntity> getOneWayAfter(@WebParam(name = "origin")String origin, @WebParam(name = "destination")String destination, @WebParam(name = "depTime")int departure1, @WebParam(name = "days")int days) throws NoFlightsFoundOnSearchException {
            String date = "2020-12-08 00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        LocalDateTime departure = LocalDateTime.parse(date, formatter);          
         return flightReservationSessionBean.getOneWayAfter(true, true, origin, destination, departure, days, CabinClassType.F);
    }
     
     @WebMethod(operationName = "getOneWayBefore")
     public List<FlightScheduleEntity> getOneWayBefore(@WebParam(name = "origin")String origin, @WebParam(name = "destination")String destination, @WebParam(name = "depTime")int departure1, @WebParam(name = "days")int days) throws NoFlightsFoundOnSearchException {
            String date = "2020-12-08 00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        LocalDateTime departure = LocalDateTime.parse(date, formatter);      
         return flightReservationSessionBean.getOneWayBefore(true, true, origin, destination, departure, days, CabinClassType.F);
    }
     
     
}
