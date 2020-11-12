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
import ejb.session.stateless.FlightSchedulePlanSessionBeanRemote;

import ejb.session.stateless.FlightSessionBeanRemote;
import entity.CabinClassConfigurationEntity;
import entity.EmployeeEntity;
import entity.FareEntity;
import entity.FlightEntity;
import entity.FlightRouteEntity;
import entity.FlightScheduleEntity;
import entity.FlightSchedulePlanEntity;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import util.enumeration.CabinClassType;
import util.enumeration.EmployeeAccessRight;
import util.enumeration.ExceedsMaximumCapacityException;
import util.enumeration.ScheduleEnum;
import util.exception.AircraftConfigurationNotFoundException;
import util.exception.AircraftTypeNotFoundException;
import util.exception.FareNotFoundException;
import util.exception.FlightNotFoundException;
import util.exception.FlightRouteNotFoundException;
import util.exception.FlightScheduleNotFoundException;
import util.exception.FlightSchedulePlanNotFoundException;
import util.exception.InvalidAccessRightException;
import util.exception.UpdateFlightException;

/**
 *
 * @author quahjingxin
 */
public class FlightOperationModule {
    
    
    private FlightSessionBeanRemote flightSessionBeanRemote;
    
    private FlightSchedulePlanSessionBeanRemote flightSchedulePlanSessionBeanRemote;
   
    private FlightRouteSessionBeanRemote flightRouteSessionBeanRemote;
         
    private AircraftConfigurationSessionBeanRemote aircraftConfigurationSessionBeanRemote;
    
    private EmployeeEntity currentEmployeeEntity;
    
    
    public FlightOperationModule(AircraftConfigurationSessionBeanRemote aircraftConfigurationSessionBeanRemote,FlightRouteSessionBeanRemote flightRouteSessionBeanRemote,FlightSchedulePlanSessionBeanRemote flightSchedulePlanSessionBeanRemote,FlightSessionBeanRemote flightSessionBeanRemote){
        
           this.flightRouteSessionBeanRemote= flightRouteSessionBeanRemote;
           this.aircraftConfigurationSessionBeanRemote=aircraftConfigurationSessionBeanRemote;
           this.flightSessionBeanRemote=flightSessionBeanRemote;
           this.flightSchedulePlanSessionBeanRemote= flightSchedulePlanSessionBeanRemote;
         
    }
       
    public void menuflightOperation(EmployeeEntity e) throws InvalidAccessRightException
    {
        this.currentEmployeeEntity= e;
        if(currentEmployeeEntity.getAccessRight() == EmployeeAccessRight.SCHEDULEMANAGER) {
        
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        
        while(true)
        {
          
            response = 0;
            
            while(response < 1 || response > 9)
            {
                
            System.out.println("*** FRS Management Client :: Flight Operation ***\n");
            System.out.println("1: Create Flight");
            System.out.println("2: View All Flights");
            System.out.println("3: View Flight Details");
            //System.out.println("4: Update Flight");
            //System.out.println("5: Delete Flight");
            System.out.println("4: Create Flight Schedule Plan");
            System.out.println("5: View All Flight Schedule Plans");
            System.out.println("6: View All Flight Schedule Plan Details");
           // System.out.println("7: Update Flight Schedule Plan");
            //System.out.println("8: Delete Flight Schedule Plan");

            System.out.println("7: Back\n");
                System.out.print("> ");

                response = scanner.nextInt();

                if(response == 1)
                {
                 try {
                     doCreateFlight();
                 } catch (FlightRouteNotFoundException ex) {
                     System.out.println("Flight Route ID does not exist!");
                 } catch (AircraftConfigurationNotFoundException ex) {
                     System.out.println("Aircraft Configuration! ID does not exist!");
                 }
                }
                else if(response == 2)
                {
                    doViewAllFlights();
                }
                else if(response == 3)
                {
                 try {
                     doViewFlightDetails();
                 } catch (FlightNotFoundException ex) {
                     System.out.println("Flight ID does not exist!");
                 }
                }
               /* else if(response == 4)
                {
                 try { 
                     doUpdateFlight() ;
                 } catch (FlightNotFoundException ex) {
                     System.out.println("Flight ID does not exist!");
                 } catch (UpdateFlightException ex) {
                     System.out.println("Flight number of flight record to be updated does not match the existing record!");
                 } catch (AircraftConfigurationNotFoundException ex) {
                     System.out.println("Aircraft Configuration ID does not exist!");
                 } catch (FlightRouteNotFoundException ex) {
                     System.out.println("Flight Route ID does not exist!");
                 }
                }
                else if(response == 5)
                {
                 try {
                     doDeleteFlight();
                 } catch (FlightNotFoundException ex) {
                     System.out.println("Flight ID does not exist!");
                 }
                }*/
                else if(response == 4)
                {
                try {
                    doCreateFlightSchedulePlan();
                } catch (FlightNotFoundException ex) {
                    System.out.println("Flight ID does not exist!");
                }
                }
                 else if(response == 5)
                {
                    doViewAllFlightSchedulePlans();
                }
                else if(response == 6)
                {
                    try {
                        try {  
                            doViewFlightSchedulePlanDetails();
                        } catch (FareNotFoundException ex) {
                            System.out.println("Fare ID does not exist!");
                        }
                    } catch (FlightSchedulePlanNotFoundException ex)
                    {
                        System.out.println("Flight Schedule Plan ID does not exist!");
                    }
                }
                  /*else if(response == 7)
                {
                //   doUpdateFlightSchedulePlan();
                }
                 else if(response == 8)
                {
                //   doDeleteFlightSchedulePlan();
                   
                }*/
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
             throw new InvalidAccessRightException("You don't have SCHEDULE MANAGER rights to access the flight planning module.");
        }   
        
    }
  
public void doCreateFlightSchedulePlan() throws FlightNotFoundException {
    
    
        if(flightSchedulePlanSessionBeanRemote == null)
            System.out.println("********** Module: NULL");
        else
            System.out.println("********** Module: NOT null");
    
        Scanner sc = new Scanner(System.in);   
        
        
        System.out.println("Enter flight number>");
        String flightNum = sc.nextLine().trim();
        FlightEntity flight = flightSessionBeanRemote.retrieveFlightByFlightNumber(flightNum);
        FlightSchedulePlanEntity fsp = new FlightSchedulePlanEntity(flight);
        
        System.out.println("Select type of flight schedule plan");
        System.out.println("1. SINGLE, 2. MULTIPLE, 3. RECURRENT DAY, 4. RECURRENT WEEK");
        int type= sc.nextInt();
        sc.nextLine();
        List<FlightScheduleEntity> flightScheduleList = new ArrayList<FlightScheduleEntity>(); 
  
        if(type==1) {
            
            fsp.setSchedule(ScheduleEnum.SINGLE);
            System.out.println("Enter local depature date (yyyy-MM-dd HH:mm)>");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime departure = LocalDateTime.parse(sc.nextLine().trim(), formatter);
            System.out.println("Enter estimated flight duration (in hours)>");
            int duration = sc.nextInt();    
            FlightRouteEntity flightRoute = flight.getFlightRoute();
            int timeDifference = flightRoute.getDestination().getTimeZone() - flightRoute.getOrigin().getTimeZone();
            sc.nextLine();
            LocalDateTime arrival = departure.plusHours(duration+timeDifference);
            flightScheduleList.add(new FlightScheduleEntity(departure,arrival,duration));
//fsp.getFlightSchedules().add(flightSchedulePlanSessionBeanRemote.createNewFlightSchedule(new FlightScheduleEntity(departure,arrival,duration)));
            
        } else if(type==2) {
            fsp.setSchedule(ScheduleEnum.MULTIPLE);
            System.out.println("Enter the number of schedules>");
            int n = sc.nextInt();
            sc.nextLine();
            for(int i=0 ; i < n; i++){
                 System.out.println("Enter local depature date for flight schedule " + i+1 + " (yyyy-MM-dd HH:mm)>");
            String date = sc.nextLine().trim();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
           System.out.println("Enter estimated flight duration " + (i+1) + " (in hours)>");
         int duration = sc.nextInt();
         sc.nextLine();
         FlightRouteEntity flightRoute = flight.getFlightRoute();
            int timeDifference = flightRoute.getDestination().getTimeZone() - flightRoute.getOrigin().getTimeZone();
         LocalDateTime arrival = dateTime.plusHours(duration+timeDifference);
            flightScheduleList.add(new FlightScheduleEntity(dateTime,arrival,duration));
            }
            
        } else if( type == 3){
            fsp.setSchedule(ScheduleEnum.RECURRENTDAY);
            
            System.out.println("Enter every n days>");
            int n = sc.nextInt();
            sc.nextLine();
            System.out.println("Enter end date (yyyy-MM-dd HH:mm)>");
            //String endDate = sc.nextLine().trim();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime endDate = LocalDateTime.parse(sc.nextLine().trim(), formatter);
           // fsp.setEnd(endDate);
            
           System.out.println("Enter local depature date (yyyy-MM-dd HH:mm)>");
            String date = sc.nextLine().trim();
            LocalDateTime departure = LocalDateTime.parse(date, formatter);
            System.out.println("Enter estimated flight duration (in hours)>");
            int duration = sc.nextInt();
            sc.nextLine();
            FlightRouteEntity flightRoute = flight.getFlightRoute();
            int timeDifference = flightRoute.getDestination().getTimeZone() - flightRoute.getOrigin().getTimeZone();
            LocalDateTime arrival = departure.plusHours(duration+timeDifference);
            //fsp.setN(n);
            //for(int i =0 ;i<n;i++){
            while (arrival.isBefore(endDate) || arrival.isEqual(endDate)){
               flightScheduleList.add(new FlightScheduleEntity(departure,arrival,duration));
                departure = departure.plusDays(n);
                arrival = arrival.plusDays(n);
            }
            //}
        } else if (type == 4) {
              
            fsp.setSchedule(ScheduleEnum.RECURRENTWEEK);
            System.out.println("Enter end date (yyyy-MM-dd HH:mm)>");
            String endDateOfRecurrent = sc.nextLine().trim();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime endDate = LocalDateTime.parse(endDateOfRecurrent, formatter);

              // fsp.setEnd(endDate);
            
            System.out.println("Enter local depature date (yyyy-MM-dd HH:mm)>");
            String date = sc.nextLine().trim();
            LocalDateTime departure = LocalDateTime.parse(date, formatter);
           System.out.println("Enter estimated flight duration (in hours)>");
         int duration = sc.nextInt();
         sc.nextLine();
          FlightRouteEntity flightRoute = flight.getFlightRoute();
            int timeDifference = flightRoute.getDestination().getTimeZone() - flightRoute.getOrigin().getTimeZone();
            LocalDateTime arrival = departure.plusHours(duration+timeDifference);
           
         
         while (arrival.isBefore(endDate) || arrival.isEqual(endDate)){
                      flightScheduleList.add(new FlightScheduleEntity(departure,arrival,duration));
                      
             departure = departure.plusWeeks(1);
             arrival = arrival.plusWeeks(1);
         }
       } else {
            System.out.println("Invalid option! Please try again.");
        }
 
        //input fares
        //System.out.println("Enter number of cabin class>");
        //int cabinClassNum = sc.nextInt();
        //sc.nextLong();
        
       
        List<CabinClassConfigurationEntity> cccList = flight.getAircraftConfiguration().getCabinClassConfigurations();
        List<FareEntity> fareList = new ArrayList<FareEntity>();
      
        for (CabinClassConfigurationEntity ccc : cccList)
        {
            System.out.println();
            
            if (ccc.getCabinClassType() == CabinClassType.F) 
            {
                
                System.out.print("First class - Enter number of fares> ");
            int faresNum1 = sc.nextInt();
            sc.nextLine();
            for(int i = 0; i < faresNum1; i++) {
            
            FareEntity f = new FareEntity();
            f.setCabinClassConfiguration(ccc);
            System.out.print("First Class - Enter fare basis code> ");
            f.setFareBasisCode(sc.nextLine().trim());
            System.out.print("First Class - Enter fare amount> ");
            f.setFareAmount(sc.nextLine().trim());
            fareList.add(f);
               // flightSchedulePlanSessionBeanRemote.createNewFare(fr, f); 
        }
            } else if (ccc.getCabinClassType() == CabinClassType.J)
            {
              System.out.print("Business Class - Enter number of fares> ");
         int faresNum2 = sc.nextInt();
        sc.nextLine();
        for(int i = 0; i < faresNum2; i++) {
            FareEntity j = new FareEntity();
            j.setCabinClassConfiguration(ccc);
            System.out.print("Business Class - Enter fare basis code> ");
            j.setFareBasisCode(sc.nextLine().trim());
            System.out.print("Business Class - Enter fare amount> ");
            j.setFareAmount(sc.nextLine().trim());
           fareList.add(j);  
            }
        
        } else if (ccc.getCabinClassType() == CabinClassType.W)
        {
          System.out.print("Premium Economy Class - Enter number of fares> ");
         int faresNum3 = sc.nextInt();
        sc.nextLine();
        for(int i = 0; i < faresNum3; i++) {
            FareEntity w = new FareEntity();
            w.setCabinClassConfiguration(ccc);
            System.out.print("Premium Economy Class - Enter fare basis code> ");
            w.setFareBasisCode(sc.nextLine().trim());
            System.out.print("Premium Economy Class - Enter fare amount> ");
            w.setFareAmount(sc.nextLine().trim());
            fareList.add(w);
               // flightSchedulePlanSessionBeanRemote.createNewFare(fr, w);
        }  
        } else if (ccc.getCabinClassType() == CabinClassType.Y)
        {
          System.out.print("Economy Class - Enter number of fares> ");
        int faresNum4 = sc.nextInt();
        sc.nextLine();
        for(int i = 0; i < faresNum4; i++) {
            FareEntity y = new FareEntity();
            y.setCabinClassConfiguration(ccc);
            System.out.print("Economy Class - Enter fare basis code> ");
            y.setFareBasisCode(sc.nextLine().trim());
            System.out.print("Economy Class - Enter fare amount> ");
            y.setFareAmount(sc.nextLine().trim());
            fareList.add(y);  
            System.out.println(i);
        }
        }
        }
        

      // System.out.println(fsp);
       //System.out.println(flight);
     
       
        FlightSchedulePlanEntity fspId = flightSchedulePlanSessionBeanRemote.createFlightSchedulePlan(fsp,flightScheduleList,fareList,flight);
        System.out.println("Flight Schedule Plan " + fspId.getFightSchedulePlanId() + " has been successfully created!");
            
         //check if got complementary return flightId
        if (flight.getComplentaryFlight() != null)
        {
            System.out.println("Would you like to create a complementary return schedule plan? (Y/N)> ");
            if(sc.nextLine().trim().equals("Y")) {
                System.out.println("Enter layover duration (in hours)> ");
                int layoverDuration = sc.nextInt();
                sc.nextLine();
                
                FlightEntity complementaryFlight = flightSessionBeanRemote.retrieveFlightByFlightNumber(flight.getComplentaryFlight().getFlightNumber());
                List<FlightScheduleEntity> complementaryFs = new ArrayList<FlightScheduleEntity>(); 
                FlightSchedulePlanEntity complementaryFsp = new FlightSchedulePlanEntity(complementaryFlight);
                complementaryFsp.setSchedule(fspId.getSchedule());
                for(FlightScheduleEntity fs : fspId.getFlightSchedules()) 
                {
                    LocalDateTime departure = fs.getArrival().plusHours(layoverDuration);
                    int flightDuration = fs.getDuration();
                    complementaryFs.add(new FlightScheduleEntity(departure, departure.plusHours(flightDuration), flightDuration));
                }
                 FlightSchedulePlanEntity complementaryFspId = flightSchedulePlanSessionBeanRemote.createFlightSchedulePlan(complementaryFsp, complementaryFs, fareList,complementaryFlight);
                 System.out.println("Complementary Flight Schedule Plan " + complementaryFspId.getFightSchedulePlanId() + " has been successfuly created!");
            }          
        }     
        
    }



    public void doViewAllFlightSchedulePlans() {
       List<FlightSchedulePlanEntity> fsp = flightSchedulePlanSessionBeanRemote.retrieveAllFlightSchedulePlans();
    
       for(FlightSchedulePlanEntity fsp1 : fsp) {
           System.out.println("Flight Schedule Plan ID: " + fsp1.getFightSchedulePlanId() + " Flight Number: " + fsp1.getFlight().getFlightNumber());
           
           if(fsp1.getComplementaryFlightSchedulePlan() != null) {
               System.out.println("Complementary Flight Schedule Plan ID: " + fsp1.getComplementaryFlightSchedulePlan().getFightSchedulePlanId()+ "; Flight Number: " + fsp1.getComplementaryFlightSchedulePlan().getFlight().getFlightNumber());
           }
       }        
    }
    
    public void doViewFlightSchedulePlanDetails() throws FlightSchedulePlanNotFoundException, FareNotFoundException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Flight Schedule Plan ID> ");
        Long fspId = sc.nextLong();
        sc.nextLine();
            

            FlightSchedulePlanEntity fsp = flightSchedulePlanSessionBeanRemote.retrieveFlightSchedulePlanById(fspId);
            System.out.println("Flight Schedule Plan " + fspId + " details");
            System.out.println("Flight Route: " + fsp.getFlight().getFlightRoute().getOrigin().getName() + "-" + fsp.getFlight().getFlightRoute().getDestination().getName());
            
            List<FlightScheduleEntity> fs = fsp.getFlightSchedules();
            System.out.println("Flight Schedule(s)> ");
            for(FlightScheduleEntity fs1 : fs) {
                System.out.println("Flight Schedule " + fs1.getFlightScheduleId() + "; Departure: " + fs1.getDeparture() + "; Arrival: " + fs1.getArrival() + "; Estimated Flight Duration: " + fs1.getDuration());
            }
            
            System.out.println("Fares>");
            List<FareEntity> f = fsp.getFares();
            for(FareEntity f1 : f) {
                System.out.println("Fare ID: " + f1.getFareId() +"; Cabin Class Type: " + f1.getCabinClassConfiguration().getCabinClassType() + "; Fare Basis Code: " + f1.getFareBasisCode() + "; Fare Amount: " + f1.getFareAmount());
            }
       
             while(true)
            {
          
               Integer response = 0;
            
            while(response < 1 || response > 3)
            {
                
            System.out.println("1: Update Flight Schedule Plan " + fspId);
            System.out.println("2: Delete Flight Schedule Plan " + fspId);
            System.out.println("3: Back\n");
            System.out.print("> ");

                response = sc.nextInt();

                if(response == 1)
                {
                
                try {
                    doUpdateFlightSchedulePlan(fspId);
                } catch (FlightScheduleNotFoundException ex) {
                    System.out.println("Flight Schedule does not exist!");
                }
                
                }
                else if(response == 2)
                {
                 
                    try {
                     doDeleteFlightSchedulePlan(fspId);
                      } catch (FlightSchedulePlanNotFoundException ex) {
                    System.out.println("Flight Schedule Plan does not exist!");
                }
                
                }
                else if(response == 3)
                {
                    break;
                }
                else 
                {
                    System.out.println("Invalid option, please try again!\n");                
                }
                
           
            }
                 
                if(response == 3)
                {
                    break;
                }
        } 
    }
    
    
    public void doUpdateFlightSchedulePlan(Long fspId) throws FlightSchedulePlanNotFoundException, FlightScheduleNotFoundException, FareNotFoundException {
       
           Scanner sc = new Scanner(System.in);   
    
           FlightSchedulePlanEntity fsp = flightSchedulePlanSessionBeanRemote.retrieveFlightSchedulePlanById(fspId);
      
            while(true)
            {
          
               Integer response = 0;
            
            while(response < 1 || response > 3)
            {
           System.out.println("1: Change flight schedule details");
           System.out.println("2: Delete flight schedule");
           System.out.println("3: Change fare details");
           System.out.println("4: Back\n");
           System.out.print("> ");
           
           response = sc.nextInt();
           
           if (response == 1) {
               System.out.println("Enter ID of Flight Schedule to be changed> ");
               Long fsId = sc.nextLong();
               sc.nextLine();
            
                FlightScheduleEntity fsToBeUpdated = flightSchedulePlanSessionBeanRemote.retrieveFlightScheduleById(fsId);        
                System.out.println("Enter new departure date/time (yyyy-MM-dd HH:mm)> ");
                String dateTime = sc.nextLine().trim();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime newDate = LocalDateTime.parse(dateTime, formatter);
                fsToBeUpdated.setDeparture(newDate);
                System.out.println("Enter new duration> ");
                int newDuration = sc.nextInt();
                sc.nextLong();
                fsToBeUpdated.setDuration(newDuration);  
               System.out.println("Flight Schedule ID " + fsToBeUpdated.getFlightScheduleId() + " has been successfully updated!");
               
            }
           else if (response == 2) 
           {
              System.out.println("Enter ID of Flight Schedule to be deleted> ");
              Long fsId = sc.nextLong();
              sc.nextLine();
                   flightSchedulePlanSessionBeanRemote.deleteFlightSchedule(fspId, fsId);
               } else if (response == 3)
               {
                    System.out.println("Enter ID of Fare to be changed> ");
               Long fareId = sc.nextLong();
               sc.nextLine();
            
                FareEntity fareToBeUpdated = flightSchedulePlanSessionBeanRemote.retrieveFareByFareId(fareId);
                
                System.out.println("Enter new fare basis code> ");
                String fareBasisCode = sc.nextLine().trim();
                fareToBeUpdated.setFareBasisCode(fareBasisCode); //string
                System.out.println("Enter new fare amount> ");
                String fareAmount = sc.nextLine().trim();
                fareToBeUpdated.setFareAmount(fareAmount);//String
                flightSchedulePlanSessionBeanRemote.updateFare(fareToBeUpdated);
                
               System.out.println("Fare ID " + fareToBeUpdated.getFareId() + " has been successfully updated!");
               } else if(response == 4)
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
            
    public void doDeleteFlightSchedulePlan(Long fspId) throws FlightSchedulePlanNotFoundException {
    
            flightSchedulePlanSessionBeanRemote.deleteFlightSchedulePlan(fspId);
      
            System.out.println("Flight Schedule Plan ID " + fspId + " has been successfully deleted!");
        }
   




    public void doDeleteFlight(Long flightId) throws FlightNotFoundException {
            // Scanner sc = new Scanner(System.in);
             //System.out.println("Enter Flight ID to be deleted> ");
             //Long flightId = sc.nextLong();
             
             flightSessionBeanRemote.deleteFlight(flightId);
             System.out.println("Flight ID " + flightId + " has been successfully deleted!");
     }

    public void doUpdateFlight(Long flightId) throws FlightNotFoundException, UpdateFlightException, AircraftConfigurationNotFoundException, FlightRouteNotFoundException {
          Scanner sc = new Scanner(System.in);    
          FlightEntity flightToBeUpdated = flightSessionBeanRemote.retrieveFlightByFlightId(flightId);
         System.out.println("Enter ID of Aircraft Configuration to be updated> ");
        Long aircraftConfiguration = sc.nextLong();
          flightToBeUpdated.setAircraftConfiguration(aircraftConfigurationSessionBeanRemote.retrieveAircraftConfigurationByAircraftConfigurationId(aircraftConfiguration));
        System.out.println("Enter ID of Flight Route to be updated> ");
        Long flightRoute = sc.nextLong();
        
      
        flightToBeUpdated.setFlightRoute(flightRouteSessionBeanRemote.retrieveFlightRouteByFlightRouteId(flightRoute));
        
        flightSessionBeanRemote.updateFlight(flightToBeUpdated);
        System.out.println("Flight ID " + flightToBeUpdated.getFlightId() + " has been successfully updated!");
    }

    public void doViewFlightDetails() throws FlightNotFoundException {
             Scanner sc = new Scanner(System.in);
             System.out.println("Enter Flight ID> ");
             Long flightId = sc.nextLong();
        //  sc.nextLine();
          
            FlightEntity f = flightSessionBeanRemote.retrieveFlightByFlightId(flightId);
        
        System.out.println("Flight Number: " + f.getFlightNumber());
        System.out.println("Flight Route: Origin> " +f.getFlightRoute().getOrigin().getCity()+ "; Destination> " + f.getFlightRoute().getDestination().getCity());
    
        Integer response = 0;
       
        while(true)
        {
          
            response = 0;
            
            while(response < 1 || response > 3)
            {
                
            System.out.println("1: Update Flight ID " + flightId);
            System.out.println("2: Delete Flight ID " + flightId);
            System.out.println("3: Back\n");
            System.out.print("> ");

                response = sc.nextInt();

                if(response == 1)
                {
                 try { 
                     doUpdateFlight(flightId) ;
                 } catch (FlightNotFoundException ex) {
                     System.out.println("Flight ID does not exist!");
                 } catch (UpdateFlightException ex) {
                     System.out.println("Flight number of flight record to be updated does not match the existing record!");
                 } catch (AircraftConfigurationNotFoundException ex) {
                     System.out.println("Aircraft Configuration ID does not exist!");
                 } catch (FlightRouteNotFoundException ex) {
                     System.out.println("Flight Route ID does not exist!");
                 }
                }
                else if(response == 2)
                {
                 try {
                     doDeleteFlight(flightId);
                 } catch (FlightNotFoundException ex) {
                     System.out.println("Flight ID does not exist!");
                 }
                }
                else if(response == 3)
                {
                    break;
                }
                else 
                {
                    System.out.println("Invalid option, please try again!\n");                
                }
                
           
            }
                 
                if(response == 3)
                {
                    break;
                }
        } 
        
    }

    public void doViewAllFlights() {
           List<FlightEntity> list =  flightSessionBeanRemote.retrieveAllFlights();
           
           for(FlightEntity r:list){
               if(!r.isComplementary()){
                System.out.println("Flight ID: "+r.getFlightId()+ "; Flight Number: " + r.getFlightNumber());

               if(r.getComplentaryFlight()!=null) {
                 System.out.println("Flight ID: "+r.getComplentaryFlight().getFlightId()+ "; Flight Number: " + r.getComplentaryFlight().getFlightNumber());

               }
            }
        }  
    }

    public void doCreateFlight() throws FlightRouteNotFoundException, AircraftConfigurationNotFoundException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Aircraft Configuration ID> ");
        Long aircraftConfiguration = sc.nextLong();
          
        System.out.println("Enter Flight Route ID> ");
        Long flightRoute = sc.nextLong();
         FlightRouteEntity fr = flightRouteSessionBeanRemote.retrieveFlightRouteByFlightRouteId(flightRoute);
        sc.nextLine();
        
       sc.nextLine();
       System.out.println("Enter Flight Number>");
       
       String flightNum = sc.nextLine().trim();
 
       // FlightRouteEntity fr = flightRouteSessionBeanRemote.retrieveFlightRouteByFlightRouteId(flightRoute);
       FlightEntity f = new FlightEntity(flightNum, false, fr , aircraftConfigurationSessionBeanRemote.retrieveAircraftConfigurationByAircraftConfigurationId(aircraftConfiguration));
       f.setComplementary(false);
       //flightSessionBeanRemote.createNewFlight(f);
       
       if(fr.getComplementaryReturnRoute()!=null){
       
       //sc.nextLine();
     
        System.out.println("Would you like a complementary flight? (Y/N)> ");
        String comp = sc.nextLine().trim();
      
      if(comp.equals("Y") ){
          
         flightNum = flightNum + "C";
         FlightEntity f2 = new FlightEntity(flightNum, false,fr.getComplementaryReturnRoute(), aircraftConfigurationSessionBeanRemote.retrieveAircraftConfigurationByAircraftConfigurationId(aircraftConfiguration));
          
         f2.setComplementary(true);
         f = flightSessionBeanRemote.createNewFlight(f);
         //System.out.println("Flight ID " + f.getFlightId() + " has been successfully created!");
        // f2 = flightSessionBeanRemote.createNewFlight(f2);
         f.setComplentaryFlight(f2);
       //flightSessionBeanRemote.createNewFlight(f);
       f2 = flightSessionBeanRemote.createNewFlight(f2);
       System.out.println("Flight ID " + f.getFlightId() + " has been successfully created!");
       System.out.println("Complementary Flight ID " + f2.getFlightId() + " has been successfully created!");
          
             } 
      else {
                   f =  flightSessionBeanRemote.createNewFlight(f);
                    System.out.println("Flight ID " + f.getFlightId() + " has been successfully created!");

             }
      
       }  else {
         f =  flightSessionBeanRemote.createNewFlight(f);
          System.out.println("Flight ID " + f.getFlightId() + " has been successfully created!");
      //}
    }

    } 
}
