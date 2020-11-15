/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.FlightRouteSessionBeanLocal;
import entity.FlightRouteEntity;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.DependsOn;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import util.exception.FlightRouteNotFoundException;

/**
 *
 * @author leahr
 */
@Singleton
@LocalBean
@Startup
@DependsOn("DataInitSessionBean")
public class DebugSessionBean {

    @EJB
    private FlightRouteSessionBeanLocal flightRouteSessionBeanLocal;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    
    
    @PostConstruct
    public void postConstruct()
    {
        try {
            FlightRouteEntity fr = flightRouteSessionBeanLocal.retrieveFlightRouteByAirportCode("SIN", "SYD");            
            System.err.println("********** flightRoute: " + fr.getFlightRouteId());
            
            fr = flightRouteSessionBeanLocal.retrieveFlightRouteByAirportCode("SIN", "HKG");            
            System.err.println("********** flightRoute: " + fr.getFlightRouteId());
            
            fr = flightRouteSessionBeanLocal.retrieveFlightRouteByAirportCode("HKG", "SIN");            
            System.err.println("********** flightRoute: " + fr.getFlightRouteId());
            
            fr = flightRouteSessionBeanLocal.retrieveFlightRouteByAirportCode("SIN", "TPE");            
            System.err.println("********** flightRoute: " + fr.getFlightRouteId());
            
            fr = flightRouteSessionBeanLocal.retrieveFlightRouteByAirportCode("SIN", "NRT");            
            System.err.println("********** flightRoute: " + fr.getFlightRouteId());
            
        } catch (FlightRouteNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
