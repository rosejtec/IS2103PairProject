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
import entity.AirportEntity;
import entity.CabinClassConfigurationEntity;
import entity.EmployeeEntity;
import entity.FlightRouteEntity;
import entity.FlightScheduleEntity;
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
import util.exception.AirportNotFoundException;
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
            
            while(response < 1 || response > 6) {
            
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
                         System.out.println("Aircraft Configuration not found!\n");
                     }
                 } catch (AircraftTypeNotFoundException ex) {
                       System.out.println("Aircraft Type does not exist!\n");
                 } catch (ExceedsMaximumCapacityException ex) {
                       System.out.println("Number of seats exceeds maximum capacity!\n");
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
                        System.out.println("Aircraft Configuration not found!\n");
                    }
                }
                else if(response == 4)
                {
                    try {
                        try {
                            doCreateFlightRoute();
                        } catch (AirportNotFoundException ex) {
                            System.out.println("Airport code does not exist!\n");
                        }
                    } catch (FlightRouteNotFoundException ex) {
                        System.out.println("Flight Route does not exist!\n");
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
                        System.out.println("Flight Route does not exist!\n");
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
             throw new InvalidAccessRightException("You don't have FLEET MANAGER or ROUTE PLANNER rights to access the flight planning module.\n");
        }    
       

        
    }
  
    public void doCreateAircraftConfiguration() throws AircraftTypeNotFoundException, ExceedsMaximumCapacityException, AircraftConfigurationNotFoundException
    {
        Scanner scanner = new Scanner(System.in);
         System.out.println("Enter Aircraft Type ID> ");
         Long type = scanner.nextLong();
         AircraftTypeEntity aircraft = aircraftTypeSessionBeanRemote.retrieveAircraftTypeByAircraftTypeId(type);
         
        scanner.nextLine();
        AircraftConfigurationEntity newAircraftConfiguration = new AircraftConfigurationEntity();
        
        System.out.println("*** FRS Management Client :: Flight Planning :: Create New Aircraft Configuration ***\n");
        System.out.println("Enter Name> ");
        newAircraftConfiguration.setName(scanner.nextLine().trim());
        System.out.println("Enter Number Of Cabin Classes (1 to 4)> ");
        Integer numOfCabinClasses = scanner.nextInt();
        scanner.nextLine();
        newAircraftConfiguration.setNumOfCabinClass(numOfCabinClasses);
        List<CabinClassConfigurationEntity> newCabinClassConfiguration = new ArrayList<CabinClassConfigurationEntity>();

        int max = 0;
        for(int i =0 ;i<numOfCabinClasses;i++){
            
            int j=i+1;
               System.out.println("Enter Cabin Type (F, J, W, Y) for Cabin Class "  + j+ " > ");
               String cabinClassType = scanner.nextLine().trim();
               System.out.println("Enter Number Of Aisles (0 to 2)> ");
               Integer numOfAisles = scanner.nextInt();
               System.out.println("Enter Number Of Rows> ");
               Integer numOfRows = scanner.nextInt();
               System.out.println("Enter Number Of Seats Abreast> ");
               Integer numOfSeatsAbreast = scanner.nextInt();
               scanner.nextLine();
               System.out.println("Enter Seat Configuration> ");
               String seatConfiguration=scanner.nextLine().trim();
               
               max += numOfRows*numOfSeatsAbreast;
               if(max >= aircraft.getMaxCapacity()) {
                   throw new ExceedsMaximumCapacityException();
               }
               
              newCabinClassConfiguration.add(new CabinClassConfigurationEntity( CabinClassType.valueOf(cabinClassType),numOfAisles, numOfRows, numOfSeatsAbreast, seatConfiguration, max));
        }
        
        newAircraftConfiguration.setMaxSeats(max);
                  
        aircraftConfigurationSessionBeanRemote.createNewAircraftConfiguration(newAircraftConfiguration,newCabinClassConfiguration, type);
        System.out.println("Aircraft Configuration " + newAircraftConfiguration.getName() +  " has been successfully created!");
     
    }
        

    private void doViewAllAircraftConfigurations() {
        List<AircraftConfigurationEntity> air = aircraftConfigurationSessionBeanRemote.retrieveAllAircraftConfigurations();
    
       for (AircraftConfigurationEntity air1 : air) {
           System.out.println("Aircraft ID: " + air1.getAircraftConfigurationId() + "; Name: " + air1.getName() + "; Aircraft Type: " + air1.getAircraftType().getName());
       }
    
    }

    private void doViewAircraftConfigurationDetails() throws AircraftConfigurationNotFoundException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Aircraft Configuration ID> ");
        Long config = sc.nextLong();
        
        AircraftConfigurationEntity aircraft = aircraftConfigurationSessionBeanRemote.retrieveAircraftConfigurationByAircraftConfigurationId(config);
        System.out.println("Details> ");
        System.out.println("Name: " + aircraft.getName());
        System.out.println("Number Of Cabin Class: " + aircraft.getNumOfCabinClass());
        System.out.println("Aircraft Type: " + aircraft.getAircraftType().getName() +  "; Max Capacity: " + aircraft.getMaxSeats());
    
        for(CabinClassConfigurationEntity c : aircraft.getCabinClassConfigurations()) {
            System.out.println("Seat Configuration: " + c.getSeatConfiguration() + "; Cabin Class Type: "+  c.getCabinClassType()+ "; Number Of Aisles: " + c.getNumOfAisles()+ "; Number Of Rows: " + c.getNumOfRows() + "; Number Of Seats Abreast: " + c.getNumOfSeatsAbreast());
        }
        
        System.out.println();
    }

    private void doCreateFlightRoute() throws FlightRouteNotFoundException, AirportNotFoundException {
      Scanner sc = new Scanner(System.in);
      System.out.println("Enter Origin Airport Code> ");
      String codeO= sc.nextLine().trim();
      AirportEntity origin = airportSessionBeanRemote.retriveBy(codeO);
      System.out.println("Enter Destination Airport Code> ");
      String codeD= sc.nextLine().trim();
      AirportEntity destination = airportSessionBeanRemote.retriveBy(codeD);
      //if(flightRouteSessionBeanRemote.)
        FlightRouteEntity route  = new FlightRouteEntity(origin, destination);
        Long id = flightRouteSessionBeanRemote.createNewFlightRoute(route);
        System.out.println("Flight Route " + id + " has been successfully created!");
        System.out.println("Would you like a Complementary Flight Route (Y/N)> ");
        String comp =sc.nextLine().trim();
      
      if(comp.equals("Y")){
          FlightRouteEntity routeComp  = new FlightRouteEntity(airportSessionBeanRemote.retriveBy(codeD),airportSessionBeanRemote.retriveBy(codeO));
          flightRouteSessionBeanRemote.createNewComplementaryReturnRoute(id, routeComp);
          System.out.println("Complementary Flight Route has been successfully created!");
      }
    }

    private void doViewAllFlightRoutes() {
        List<FlightRouteEntity> route = flightRouteSessionBeanRemote.retrieveAllFlightRoutes();
        
        for(FlightRouteEntity r:route){
            System.out.println("Flight Route ID: "+r.getFlightRouteId()+ "; Origin: " + r.getOrigin().getCity()+ "; Destination: " + r.getDestination().getCity());
            if(r.getComplementaryReturnRoute()!= null) {
            System.out.println("Flight Route ID: "+r.getComplementaryReturnRoute().getFlightRouteId()+ "; Origin: " + r.getComplementaryReturnRoute().getOrigin().getCity()+ "; Destination: " + r.getComplementaryReturnRoute().getDestination().getCity());
   
            }
        }
    }

    private void doDeleteFightRoute() throws FlightRouteNotFoundException {
           Scanner sc = new Scanner(System.in);   
           System.out.println("Enter Flight Route ID to be deleted> ");
           Long id = sc.nextLong();
           flightRouteSessionBeanRemote.deleteFlightRoute(id);
           System.out.println("Flight Route ID " + id + " is successfully deleted!");
    }

  
        
    
}
