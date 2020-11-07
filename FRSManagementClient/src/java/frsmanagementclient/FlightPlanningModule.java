/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frsmanagementclient;

import ejb.session.stateless.AircraftConfigurationSessionBeanRemote;
import ejb.session.stateless.AircraftTypeSessionBeanRemote;
import ejb.session.stateless.AirportSessionBeanRemote;
import ejb.session.stateless.FlightRouteSessionBeanRemote;
import entity.AircraftConfigurationEntity;
import entity.AircraftTypeEntity;
import entity.CabinClassConfigurationEntity;
import entity.EmployeeEntity;
import entity.FlightRouteEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import util.enumeration.CabinClassType;
import util.enumeration.EmployeeAccessRight;
import util.enumeration.ExceedsMaximumCapacityException;
import util.exception.AircraftConfigurationNotFoundException;
import util.exception.AircraftTypeNotFoundException;
import util.exception.FlightRouteNotFoundException;
import util.exception.InvalidAccessRightException;

/**
 *
 * @author quahjingxin
 */
public class FlightPlanningModule {
    
    @EJB
    private static AircraftConfigurationSessionBeanRemote aircraftConfigurationSessionBeanRemote;
    @EJB
    private static AircraftTypeSessionBeanRemote aircraftTypeSessionBeanRemote; 
    @EJB
    private static AirportSessionBeanRemote airportSessionBeanRemote;
    @EJB
    private static FlightRouteSessionBeanRemote flightRouteSessionBeanRemote;
      
    //call all the session beans
    private EmployeeEntity currentEmployeeEntity;
    
    public FlightPlanningModule(AircraftConfigurationSessionBeanRemote aircraftConfigurationSessionBeanRemote,AircraftTypeSessionBeanRemote aircraftTypeSessionBeanRemote,AirportSessionBeanRemote airportSessionBeanRemote,FlightRouteSessionBeanRemote flightRouteSessionBeanRemote,EmployeeEntity currentEmployeeEntity) 
    {
        
           this.flightRouteSessionBeanRemote= flightRouteSessionBeanRemote;
           this.aircraftConfigurationSessionBeanRemote=aircraftConfigurationSessionBeanRemote;
           this.airportSessionBeanRemote= airportSessionBeanRemote;
           this.aircraftTypeSessionBeanRemote= aircraftTypeSessionBeanRemote;
          
    }
     //insert constructor
    public void menuFlightPlanning(EmployeeEntity e) throws InvalidAccessRightException
    {
        this.currentEmployeeEntity= e;
        if(currentEmployeeEntity.getAccessRight() == EmployeeAccessRight.FLEETMANAGER || currentEmployeeEntity.getAccessRight() == EmployeeAccessRight.ROUTEPLANNER)
        {
            Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        
        while(true)
        {
          
            response = 0;
            
            while(response < 1 || response > 6)
            {
             System.out.println("*** FRS Management Client :: Flight Planning ***\n");
            System.out.println("1: Create Aircraft Configuration");
            System.out.println("2: View All Aircraft Configurations");
            System.out.println("3: View Aircraft Configuration Details");
            System.out.println("4: Create Flight Route");
            System.out.println("5: View All Flight Routes");
            System.out.println("6: Delete Fight Route");
            System.out.println("7: Back\n");
                System.out.print("> ");

                response = scanner.nextInt();

                if(response == 1)
                {
                    
                 try {
                     try {
                         doCreateAircraftConfiguration();
                     } catch (AircraftConfigurationNotFoundException ex) {
                         Logger.getLogger(FlightPlanningModule.class.getName()).log(Level.SEVERE, null, ex);
                     }
                 } catch (AircraftTypeNotFoundException ex) {
                       System.out.println("type");
                 } catch (ExceedsMaximumCapacityException ex) {
                       System.out.println("Capacity");
                 }
                    
                }
                
                else if(response == 2)
                {
                    doViewAllAircraftConfigurations();
                }
                else if(response == 3)
                {
                    try {
                        doViewAircraftConfigurationDetails();
                    } catch (AircraftConfigurationNotFoundException ex) {
                        Logger.getLogger(FlightPlanningModule.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else if(response == 4)
                {
                    try {
                        doCreateFlightRoute();
                    } catch (FlightRouteNotFoundException ex) {
                        Logger.getLogger(FlightPlanningModule.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else if(response == 5)
                {
                    doViewAllFlightRoutes();
                }
                else if(response == 6)
                {
                    try {
                        doDeleteFightRoute();
                    } catch (FlightRouteNotFoundException ex) {
                        Logger.getLogger(FlightPlanningModule.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else if(response == 7)
                {
                    break;
                }
                else
                {
                    System.out.println("Invalid option, please try again!\n");                
                }
            }
            
            if(response == 7)
            {
                break;
            }
            
           }
           
        } else {
             throw new InvalidAccessRightException("You don't have FLEET MANAGER or ROUTE PLANNER rights to access the flight planning module.");
        }    
       

        
    }
  
    public void doCreateAircraftConfiguration() throws AircraftTypeNotFoundException, ExceedsMaximumCapacityException, AircraftConfigurationNotFoundException
    {
        Scanner scanner = new Scanner(System.in);
         System.out.println("Enter Id of AirCraft Type> ");
         Long type = scanner.nextLong();
         AircraftTypeEntity aircraft = aircraftTypeSessionBeanRemote.retrieveAircraftTypeByAircraftTypeId(type);
         
        scanner.nextLine();
        AircraftConfigurationEntity newAircraftConfiguration = new AircraftConfigurationEntity();
        
        System.out.println("\"*** FRS Management Client :: Flight Planning :: Create New Aircraft Configuration ***\n");
        System.out.println("Enter Name> ");
        newAircraftConfiguration.setName(scanner.nextLine().trim());
        System.out.println("Enter Number Of Cabin Classes (1 to 4) > ");
        Integer numOfCabinClasses = scanner.nextInt();
        newAircraftConfiguration.setNumOfCabinClass(numOfCabinClasses);
        List<CabinClassConfigurationEntity> newCabinClassConfiguration = new ArrayList<CabinClassConfigurationEntity>();

        
       
     
        int max =0;
        while (numOfCabinClasses >= 1) {
               System.out.println("Enter Number Of Ailes> ");
               Integer numOfAisles=scanner.nextInt();
               System.out.println("Enter Number Of Rows> ");
               Integer numOfRows=scanner.nextInt();
               System.out.println("Enter Number Of Seats Abreast> ");
               Integer numOfSeatsAbreast=scanner.nextInt();
               scanner.nextLine();

               System.out.println("Enter Number Of seat Configuration> ");
               String seatConfiguration=scanner.nextLine().trim();
               //System.out.println("Enter Cabin type> ");
               CabinClassType cabinClassType=CabinClassType.F;
               if(max <aircraft.getMaxCapacity()) {
                   max+= numOfRows*numOfSeatsAbreast;
               } else {
                   throw new ExceedsMaximumCapacityException();
               }
               
               numOfCabinClasses-=1;
              newCabinClassConfiguration.add(new CabinClassConfigurationEntity(numOfAisles, numOfRows, numOfSeatsAbreast, seatConfiguration, CabinClassType.Y));
        }
        
                  
        aircraftConfigurationSessionBeanRemote.createNewAircraftConfiguration(newAircraftConfiguration,newCabinClassConfiguration, type);
     
    }
        

    private void doViewAllAircraftConfigurations() {
        List<AircraftConfigurationEntity> air = aircraftConfigurationSessionBeanRemote.retrieveAllAircraftConfigurations();
    
       for (AircraftConfigurationEntity air1 : air) {
           System.out.println("Id: " + air1.getAircraftConfigurationId() + " Name: " + air1.getName());
       }
    
    }

    

    private void doViewAircraftConfigurationDetails() throws AircraftConfigurationNotFoundException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Id of AircraftConfiguration Id> ");
        Long config = sc.nextLong();
        
        AircraftConfigurationEntity aircraft = aircraftConfigurationSessionBeanRemote.retrieveAircraftConfigurationByAircraftConfigurationId(config);
        System.out.println("Details> ");
        System.out.println("Name: " + aircraft.getName());
        System.out.println("Num of Cabin Class " + aircraft.getNumOfCabinClass());
        System.out.println("AirCraft Type 1. " + aircraft.getAircraftType().getName() +  " 2.Max Capacity: " + aircraft.getAircraftType().getMaxCapacity());
    
        for(CabinClassConfigurationEntity c : aircraft.getCabinClassConfigurations()) {
            System.out.println("SeatConfiguration: " + c.getSeatConfiguration() + " CabinClassType: "+  c.getCabinClassType()+ " NumOfAisles: " + c.getNumOfAisles()+ " NumOfRows: " + c.getNumOfRows() +" NumOfSeatsAbreast: " + c.getNumOfSeatsAbreast());
        }
        
    }

    private void doCreateFlightRoute() throws FlightRouteNotFoundException {
      Scanner sc = new Scanner(System.in);
      System.out.println("Enter Origin AiportCode> ");
      String codeO= sc.nextLine().trim();
      System.out.println("Enter Destination AiportCode> ");
      String codeD= sc.nextLine().trim();

        FlightRouteEntity route  = new FlightRouteEntity(airportSessionBeanRemote.retriveBy(codeO), airportSessionBeanRemote.retriveBy(codeD));
        Long id = flightRouteSessionBeanRemote.createNewFlightRoute(route);
        System.out.println("Would you like a ComplementaryFlightRoute (Y/N)> ");
        String comp =sc.nextLine().trim();
      
      if(comp.equals("Y")){
          FlightRouteEntity routeComp  = new FlightRouteEntity( airportSessionBeanRemote.retriveBy(codeD),airportSessionBeanRemote.retriveBy(codeO));
          flightRouteSessionBeanRemote.createNewComplementaryReturnRoute(id, routeComp);
      }
      
      

    }

    private void doViewAllFlightRoutes() {
        List<FlightRouteEntity> route = flightRouteSessionBeanRemote.retrieveAllFlightRoutes();
        
        for(FlightRouteEntity r:route){
            System.out.println("Id: "+r.getFlightRouteId()+ " Origin: " + r.getOrigin().getCity()+ " Destination: " + r.getDestination().getCity());
            if(r.getComplementaryReturnRoute()!= null) {
            System.out.println("Id: "+r.getComplementaryReturnRoute().getFlightRouteId()+ " Origin: " + r.getComplementaryReturnRoute().getOrigin().getCity()+ " Destination: " + r.getComplementaryReturnRoute().getDestination().getCity());
   
            }
        }
    }

    private void doDeleteFightRoute() throws FlightRouteNotFoundException {
           Scanner sc = new Scanner(System.in);   
           System.out.println("Enter FlightRoute Id to be deleted> ");
           Long config = sc.nextLong();
           flightRouteSessionBeanRemote.deleteFlightRoute(config);
    }

  
        
    
}
