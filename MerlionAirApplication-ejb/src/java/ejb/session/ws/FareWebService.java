/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.ws;

import ejb.session.stateless.ReservationSessionBeanLocal;
import entity.FlightScheduleEntity;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;
import util.enumeration.CabinClassType;

/**
 *
 * @author leahr
 */
@WebService(serviceName = "FareWebService")
@Stateless()
public class FareWebService {

    @EJB
    private ReservationSessionBeanLocal reservationSessionBean;

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "getFare")
    public Integer hello(@WebParam(name = "CabinClass") CabinClassType type, @WebParam(name = "FlightSchedule") FlightScheduleEntity FlightSchedule) {
        return reservationSessionBean.getFare(FlightSchedule, type);
    }
}
