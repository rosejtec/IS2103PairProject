/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frsmanagementclient;

import ejb.session.stateless.AircraftConfigurationSessionBeanRemote;
import ejb.session.stateless.AircraftTypeSessionBeanRemote;
import ejb.session.stateless.AirportSessionBeanRemote;
import ejb.session.stateless.CabinClassConfigurationSessionBeanRemote;
import ejb.session.stateless.EmployeeSessionBeanRemote;
import ejb.session.stateless.FlightRouteSessionBeanRemote;
import ejb.session.stateless.FlightSchedulePlanSessionBeanRemote;
import ejb.session.stateless.FlightSessionBeanRemote;
import javax.ejb.EJB;

/**
 *
 * @author leahr
 */
public class Main 
{

    @EJB(name = "FlightSchedulePlanSessionBeanRemote")
    private static FlightSchedulePlanSessionBeanRemote flightSchedulePlanSessionBeanRemote;

    @EJB
    private static FlightSchedulePlanSessionBeanRemote flightSchedulePlanSessionBean;

    @EJB
    private static FlightSessionBeanRemote flightSessionBeanRemote;

    @EJB
    private static FlightRouteSessionBeanRemote flightRouteSessionBeanRemote;

    @EJB
    private static CabinClassConfigurationSessionBeanRemote cabinClassConfigurationSessionBeanRemote;

    @EJB
    private static AirportSessionBeanRemote airportSessionBeanRemote;

    @EJB
    private static AircraftTypeSessionBeanRemote aircraftTypeSessionBeanRemote;

    @EJB
    private static AircraftConfigurationSessionBeanRemote aircraftConfigurationSessionBeanRemote;

    @EJB
    private static EmployeeSessionBeanRemote employeeSessionBeanRemote;

    /**
     * @param args the command line arguments
     */
    
    
    
    public static void main(String[] args) {
        MainApp mainApp = new MainApp(flightSchedulePlanSessionBeanRemote, flightSessionBeanRemote, flightRouteSessionBeanRemote, cabinClassConfigurationSessionBeanRemote, airportSessionBeanRemote, aircraftTypeSessionBeanRemote, aircraftConfigurationSessionBeanRemote, employeeSessionBeanRemote);
        mainApp.runApp();
// TODO code application logic here
    }
    
}
