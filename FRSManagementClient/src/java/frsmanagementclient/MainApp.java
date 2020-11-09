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
import entity.EmployeeEntity;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import util.exception.InvalidAccessRightException;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author quahjingxin
 */
public class MainApp {
    
    
    private FlightSessionBeanRemote flightSessionBeanRemote;
    
    
    private FlightSchedulePlanSessionBeanRemote flightSchedulePlanSessionBeanRemote;
    
    
    private FlightRouteSessionBeanRemote flightRouteSessionBeanRemote;

    
    private CabinClassConfigurationSessionBeanRemote cabinClassConfigurationSessionBeanRemote;


    private AirportSessionBeanRemote airportSessionBeanRemote;

    
    private AircraftTypeSessionBeanRemote aircraftTypeSessionBeanRemote;

    
    private AircraftConfigurationSessionBeanRemote aircraftConfigurationSessionBeanRemote;


    private EmployeeSessionBeanRemote employeeSessionBeanRemote;

  
    private FlightPlanningModule flightPlanningModule;
    private FlightOperationModule flightOperationModule;
    private SalesManagementModule salesManagementModule;
    
    private EmployeeEntity currentEmployeeEntity;
    
    public MainApp() 
    {
    }

    
    MainApp(FlightSchedulePlanSessionBeanRemote flightSchedulePlanSessionBeanRemote, FlightSessionBeanRemote flightSessionBeanRemote, FlightRouteSessionBeanRemote flightRouteSessionBeanRemote, CabinClassConfigurationSessionBeanRemote cabinClassConfigurationSessionBeanRemote, AirportSessionBeanRemote airportSessionBeanRemote, AircraftTypeSessionBeanRemote aircraftTypeSessionBeanRemote, AircraftConfigurationSessionBeanRemote aircraftConfigurationSessionBeanRemote, EmployeeSessionBeanRemote employeeSessionBeanRemote) {
           this.flightSchedulePlanSessionBeanRemote = flightSchedulePlanSessionBeanRemote;
           this.flightSessionBeanRemote = flightSessionBeanRemote;
           this.flightRouteSessionBeanRemote= flightRouteSessionBeanRemote;
           this.cabinClassConfigurationSessionBeanRemote= cabinClassConfigurationSessionBeanRemote;
           this.aircraftConfigurationSessionBeanRemote=aircraftConfigurationSessionBeanRemote;
           this.airportSessionBeanRemote= airportSessionBeanRemote;
           this.aircraftTypeSessionBeanRemote= aircraftTypeSessionBeanRemote;
           this.employeeSessionBeanRemote= employeeSessionBeanRemote;
          
    }
    
    public void runApp()
    {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        
        while(true)
        {
            System.out.println("*** Welcome to FRS - Management Client ***\n");
            System.out.println("1: Login");
            System.out.println("2: Exit\n");
            response = 0;
            
            while(response < 1 || response > 2)
            {
                System.out.print("> ");

                response = scanner.nextInt();

                if(response == 1)
                {                    
                    try
                    {
                        doLogin(); 
                        System.out.println("Login successful!\n");
                     
                        
                        menuMain();
                    }
                    catch(InvalidLoginCredentialException ex) 
                    {
                        System.out.println("Invalid login credential: " + ex.getMessage() + "\n");
                    }
                }
                else if (response == 2)
                {
                    break;
                }
                else
                {
                    System.out.println("Invalid option, please try again!\n");                
                }
            }
            
            if(response == 2)
            {
                break;
            }
        }
    }
    
    
    private void doLogin() throws InvalidLoginCredentialException
    {
        Scanner scanner = new Scanner(System.in);
        String username = "";
        String password = "";
        
        System.out.println("*** Management Client :: Login ***\n");
        System.out.print("Enter username> ");
        username = scanner.nextLine().trim();
        System.out.print("Enter password> ");
        password = scanner.nextLine().trim();
        
        if(username.length() > 0 && password.length() > 0)
        {
            currentEmployeeEntity = employeeSessionBeanRemote.employeeLogin(username, password);
        }
        else
        {
            
            
            throw new InvalidLoginCredentialException("Missing login credential!");
        }
    }
    
    private void menuMain()
    {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        
        while(true)
        {
            System.out.println("*** FRS - Management Client ***\n");
            System.out.println("You are login as " + currentEmployeeEntity.getFirstName() + " " + currentEmployeeEntity.getLastName() + " with " + currentEmployeeEntity.getAccessRight().toString() + " rights\n");
            System.out.println("1: Flight Planning");
            System.out.println("2: Flight Operation");
            System.out.println("3: Seats Management");
            System.out.println("4: Logout\n");
            response = 0;
            
            while(response < 1 || response > 4)
            {
                System.out.print("> ");

                response = scanner.nextInt();

                if(response == 1)
                {
                    
                    flightPlanningModule = new FlightPlanningModule(aircraftConfigurationSessionBeanRemote, aircraftTypeSessionBeanRemote, airportSessionBeanRemote, flightRouteSessionBeanRemote, currentEmployeeEntity);
                    try
                    {
                    flightPlanningModule.menuFlightPlanning(currentEmployeeEntity);
                    }
                    catch (InvalidAccessRightException ex)
                    {
                        System.out.println("Invalid option, please try again!: " + ex.getMessage() + "\n");
                    }
                }
                
                else if(response == 2)
                {
                    if(flightSchedulePlanSessionBeanRemote == null)
                        System.out.println("********** MainApp: NULL");
                    else
                        System.out.println("********** MainApp: NOT null");

                    flightOperationModule = new FlightOperationModule(aircraftConfigurationSessionBeanRemote, flightRouteSessionBeanRemote, flightSchedulePlanSessionBeanRemote, flightSessionBeanRemote);
                    try
                    {
                     
                        flightOperationModule.menuflightOperation(currentEmployeeEntity);
                    }
                    catch (InvalidAccessRightException ex)
                    {
                        System.out.println("Invalid option, please try again!: " + ex.getMessage() + "\n");
                    }
                }
                
                
                else if(response == 3)
                {
//                    try
//                    {
//                        salesManagementModule.menuSalesManagement();
//                    }
//                    catch (InvalidAccessRightException ex)
//                    {
//                        System.out.println("Invalid option, please try again!: " + ex.getMessage() + "\n");
//                    }
//                    
                }
                else if (response == 4)
                {
                    break;
                }
                else
                {
                    System.out.println("Invalid option, please try again!\n");                
                }
            }
            
            if(response == 4)
            {
                break;
            }

        }
               

       
    
    }


        }