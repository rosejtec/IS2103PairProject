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
import util.exception.FlightNotFoundException;
import util.exception.FlightRouteNotFoundException;
import util.exception.FlightSchedulePlanNotFoundException;
import util.exception.InvalidAccessRightException;
import util.exception.UpdateFlightException;

/**
 *
 * @author quahjingxin
 */
public class FlightOperationModule {
    
    @EJB
    private FlightSessionBeanRemote flightSessionBeanRemote;
    @EJB
    private FlightSchedulePlanSessionBeanRemote flightSchedulePlanSessionBeanRemote;
    @EJB
    private FlightRouteSessionBeanRemote flightRouteSessionBeanRemote;
    @EJB     
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
            
            while(response < 1 || response > 11)
            {
                
            System.out.println("*** FRS Management Client :: Flight Operation ***\n");
            System.out.println("1: Create Flight");
            System.out.println("2: View All Flights");
            System.out.println("3: View Flight Details");
            System.out.println("4: Update Flight");
            System.out.println("5: Delete Flight");
            System.out.println("6: Create Flight Schedule Plan");
            System.out.println("7: View All Flight Schedule Plans");
            System.out.println("8: View All Flight Schedule Plan Details");
            System.out.println("9: Update Flight Schedule Plan");
            System.out.println("10: Delete Flight Schedule Plan");

            System.out.println("11: Back\n");
                System.out.print("> ");

                response = scanner.nextInt();

                if(response == 1)
                {
                 try {
                     doCreateFlight();
                 } catch (FlightRouteNotFoundException ex) {
                     Logger.getLogger(FlightOperationModule.class.getName()).log(Level.SEVERE, null, ex);
                 } catch (AircraftConfigurationNotFoundException ex) {
                     Logger.getLogger(FlightOperationModule.class.getName()).log(Level.SEVERE, null, ex);
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
                     Logger.getLogger(FlightOperationModule.class.getName()).log(Level.SEVERE, null, ex);
                 }
                }
                else if(response == 4)
                {
                 try { 
                     doUpdateFlight() ;
                 } catch (FlightNotFoundException ex) {
                     Logger.getLogger(FlightOperationModule.class.getName()).log(Level.SEVERE, null, ex);
                 } catch (UpdateFlightException ex) {
                     Logger.getLogger(FlightOperationModule.class.getName()).log(Level.SEVERE, null, ex);
                 } catch (AircraftConfigurationNotFoundException ex) {
                     Logger.getLogger(FlightOperationModule.class.getName()).log(Level.SEVERE, null, ex);
                 } catch (FlightRouteNotFoundException ex) {
                     Logger.getLogger(FlightOperationModule.class.getName()).log(Level.SEVERE, null, ex);
                 }
                }
                else if(response == 5)
                {
                 try {
                     doDeleteFlight();
                 } catch (FlightNotFoundException ex) {
                     Logger.getLogger(FlightOperationModule.class.getName()).log(Level.SEVERE, null, ex);
                 }
                }
                else if(response == 6)
                {
                try {
                    doCreateFlightSchedulePlan();
                } catch (FlightNotFoundException ex) {
                    Logger.getLogger(FlightOperationModule.class.getName()).log(Level.SEVERE, null, ex);
                }
                }
                 else if(response == 7)
                {
                    doViewAllFlightSchedulePlans();
                }
                else if(response == 8)
                {
                 doViewFlightSchedulePlanDetails();  
                }
                  else if(response == 9)
                {
                //   doUpdateFlightSchedulePlan();
                }
                 else if(response == 10)
                {
                //   doDeleteFlightSchedulePlan();
                   
                }
                else if(response == 11)
                {
                    break;
                }
                else
                {
                    System.out.println("Invalid option, please try again!\n");                
                }
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
            String date = sc.nextLine().trim();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
            System.out.println("Enter estimated flight duration (in hours)>");
            int duration = sc.nextInt();
            LocalDateTime arrival = dateTime.plusHours(duration);
            fsp.getFlightSchedules().add(flightSchedulePlanSessionBeanRemote.createNewFlightSchedule(new FlightScheduleEntity(dateTime,arrival,duration)));
            
        } else if(type==2) {
            fsp.setSchedule(ScheduleEnum.MULTIPLE);
            System.out.println("Enter the number of schedules>");
            int n = sc.nextInt();
            sc.nextInt();
            sc.nextLine();
            for(int i=0 ; i < n; i++){
                 System.out.println("Enter local depature date (yyyy-MM-dd HH:mm)>");
            String date = sc.nextLine().trim();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
           System.out.println("Enter estimated flight duration (in hours)>");
         int duration = sc.nextInt();
         LocalDateTime arrival = dateTime.plusHours(duration);
            flightScheduleList.add(new FlightScheduleEntity(dateTime,arrival,duration));
            }
            
        } else if( type ==3){
            fsp.setSchedule(ScheduleEnum.RECURRENTDAY);
            
            System.out.println("Enter every n days>");
            int n = sc.nextInt();
            sc.nextLine();
            System.out.println("Enter end date (yyyy-MM-dd HH:mm)>");
            String endDateOfRecurrent = sc.nextLine().trim();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime endDate = LocalDateTime.parse(endDateOfRecurrent, formatter);
            fsp.setEnd(endDate);
            
           System.out.println("Enter local depature date (yyyy-MM-dd HH:mm)>");
            String date = sc.nextLine().trim();
            LocalDateTime departureTime = LocalDateTime.parse(date, formatter);
            System.out.println("Enter estimated flight duration (in hours)>");
            int duration = sc.nextInt();
            LocalDateTime arrivalTime = departureTime.plusHours(duration);
            fsp.setN(n);
            for(int i =0 ;i<n;i++){
               flightScheduleList.add(new FlightScheduleEntity(departureTime,arrivalTime,duration));
                departureTime = departureTime.plusDays(n);
            }
        } else if (type == 4) {
              
            fsp.setSchedule(ScheduleEnum.RECURRENTWEEK);
            System.out.println("Enter end date (yyyy-MM-dd HH:mm)>");
            String endDateOfRecurrent = sc.nextLine().trim();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime endDate = LocalDateTime.parse(endDateOfRecurrent, formatter);

               fsp.setEnd(endDate);
            
            System.out.println("Enter local depature date (yyyy-MM-dd HH:mm)>");
            String date = sc.nextLine().trim();
            LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
           System.out.println("Enter estimated flight duration (in hours)>");
         int duration = sc.nextInt();
            LocalDateTime arrival = dateTime.plusHours(duration);
           
         
         while (arrival.isBefore(endDate) || arrival.isEqual(endDate)){
                      flightScheduleList.add(new FlightScheduleEntity(dateTime,arrival,duration));

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
      
        
        
        /*
        for (CabinClassConfigurationEntity ccc : cccList)
        {
            System.out.println();
            
            if (ccc.getCabinClassType() == CabinClassType.F) 
            {
                
                System.out.print("First class - Enter number of fares>");
            int faresNum1 = sc.nextInt();
            sc.nextLine();
            for(int i = 0; i < faresNum1; i++) {
            
            FareEntity f = new FareEntity();
            f.setCabinClassConfiguration(ccc);
            System.out.print("First Class - Enter fare basis code>");
            f.setFareBasisCode(sc.nextLine().trim());
            System.out.print("First Class - Enter fare amount>");
            f.setFareAmount(sc.nextLine().trim());
            fareList.add(f);
               // flightSchedulePlanSessionBeanRemote.createNewFare(id, f); 
        }
            } else if (ccc.getCabinClassType() == CabinClassType.J)
            {
              System.out.print("Business Class - Enter number of fares>");
         int faresNum2 = sc.nextInt();
        sc.nextLine();
        for(int i = 0; i < faresNum2; i++) {
            FareEntity j = new FareEntity();
            j.setCabinClassConfiguration(ccc);
            System.out.print("Business Class - Enter fare basis code>");
            j.setFareBasisCode(sc.nextLine().trim());
            System.out.print("Business Class - Enter fare amount>");
            j.setFareAmount(sc.nextLine().trim());
           fareList.add(j);  
            }
        
        } else if (ccc.getCabinClassType() == CabinClassType.W)
        {
          System.out.print("Premium Economy Class - Enter number of fares>");
         int faresNum3 = sc.nextInt();
        sc.nextLine();
        for(int i = 0; i < faresNum3; i++) {
            FareEntity w = new FareEntity();
            w.setCabinClassConfiguration(ccc);
            System.out.print("Premium Economy Class - Enter fare basis code>");
            w.setFareBasisCode(sc.nextLine().trim());
            System.out.print("Premium Economy Class - Enter fare amount>");
            w.setFareAmount(sc.nextLine().trim());
            fareList.add(w);
               // flightSchedulePlanSessionBeanRemote.createNewFare(id, w);
        }  
        } else if (ccc.getCabinClassType() == CabinClassType.Y)
        {
          System.out.print("Economy Class - Enter number of fares>");
        int faresNum4 = sc.nextInt();
        sc.nextLine();
        for(int i = 0; i < faresNum4; i++) {
            FareEntity y = new FareEntity();
            y.setCabinClassConfiguration(ccc);
            System.out.print("Economy Class - Enter fare basis code>");
            y.setFareBasisCode(sc.nextLine().trim());
            System.out.print("Economy Class - Enter fare amount>");


            y.setFareAmount(sc.nextLine().trim());
            fareList.add(y);  
            System.out.println(i);
        }
        }
        }
        */

       System.out.println(fsp);
       System.out.println(flight);
     
       
        FlightSchedulePlanEntity fspId = flightSchedulePlanSessionBeanRemote.createFlightSchedulePlan(fsp,flightScheduleList,fareList,flight);
        System.out.println("Flight Schedule Plan " + fspId.getFightSchedulePlanId() + " has been successfully created!");
        
}
        
        /*
         //check if got complementary return flight
        if (flight.getComplentaryFlight()!= null)
        {
            System.out.println("Would you like to create a complementary return schedule plan? (Y/N)> ");
            if(sc.nextLine().trim().equals("Y")) {
                System.out.println("Enter layover duration (in hours)");
                int layoverDuration = sc.nextInt();
                
                
                FlightEntity complementaryFlight = flightSessionBeanRemote.retrieveFlightByFlightNumber(flight.getComplentaryFlight().getFlightNum());
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


*/
    public void doViewAllFlightSchedulePlans() {
       List<FlightSchedulePlanEntity> fsp = flightSchedulePlanSessionBeanRemote.retrieveAllFlightSchedulePlans();
    
       for(FlightSchedulePlanEntity fsp1 : fsp) {
           System.out.println("Flight Schedule Plan ID: " + fsp1.getFightSchedulePlanId() + " Flight Number: " + fsp1.getFlight().getFlightNum());
           
           if(fsp1.getComplementaryFlightSchedulePlan() != null) {
               System.out.println("Complementary Flight Schedule Plan ID: " + fsp1.getComplementaryFlightSchedulePlan().getFightSchedulePlanId()+ "\n" + "Flight Number: " + fsp1.getComplementaryFlightSchedulePlan().getFlight().getFlightNum());
           }
       }        
    }
    
    public void doViewFlightSchedulePlanDetails() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Flight Schedule Plan ID> ");
        Long id = sc.nextLong();
            
        try {
            FlightSchedulePlanEntity fsp = flightSchedulePlanSessionBeanRemote.retrieveFlightSchedulePlanById(id);
            System.out.println("--- Flight Schedule Plan " + id + " details ---");
            System.out.println("Flight Route: " + fsp.getFlight().getFlightRoute().getOrigin() + "-" + fsp.getFlight().getFlightRoute().getDestination());
            
            List<FlightScheduleEntity> fs = fsp.getFlightSchedules();
            System.out.println(">>> Flight Schedule(s) <<<");
            for(FlightScheduleEntity fs1 : fs) {
                System.out.println("Flight Schedule " + fs1.getFlightScheduleId() + ">>>");
                System.out.println("Departure: " + fs1.getDeparture());
                System.out.println("Arrival: " + fs1.getArrival());
                System.out.println("Estimated Flight Duration: " + fs1.getDuration());
            }
            
            System.out.println(">>> Fares <<< ");
            List<FareEntity> f = fsp.getFares();
            for(FareEntity f1 : f) {
                System.out.println("Cabin Class Type: " + f1.getCabinClassConfiguration().getCabinClassType());
                System.out.println("Fare Basis Code: " + f1.getFareBasisCode());
                System.out.println("Fare Amount: " + f1.getFareAmount());
            }
        } catch (FlightSchedulePlanNotFoundException ex) {
            Logger.getLogger(FlightOperationModule.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*
    public void doUpdateFlightSchedulePlan() {
        Scanner sc = new Scanner(System.in);   
           System.out.println("Enter Flight Schedule Plan ID to be updated> ");
           Long id = sc.nextLong();
        try {
            FlightSchedulePlanEntity fsp = flightSchedulePlanSessionBeanRemote.retrieveFlightSchedulePlanById(id);
        } catch (FlightSchedulePlanNotFoundException ex) {
            Logger.getLogger(FlightOperationModule.class.getName()).log(Level.SEVERE, null, ex);
        }
           
           System.out.println("1: Delete flight schedule(s)");
           System.out.println("2: Change fare(s)");
           System.out.println(">");
           int response = 0;
           
           /*
           if (response == 1) 
           {
               List<FlightScheduleEntity> fs = fsp.getFlightSchedules();
               for (FlightScheduleEntity fs1 : fs)
               {
                   flightSchedulePlanSessionBeanRemote.deleteFlightSchedule(id);
               }
           }
      
           
    
    private void doDeleteFlightSchedulePlan() {
        Scanner sc = new Scanner(System.in);   
           System.out.println("Enter Flight Schedule Plan ID to be deleted> ");
           Long id = sc.nextLong();
        try {
            flightSchedulePlanSessionBeanRemote.deleteFlightSchedulePlan(id);
        } catch (FlightSchedulePlanNotFoundException ex) {
            Logger.getLogger(FlightOperationModule.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

*/


    public void doDeleteFlight() throws FlightNotFoundException {
             Scanner sc = new Scanner(System.in);
             System.out.println("Enter Id of Flight> ");
             Long flight = sc.nextLong();
             
             flightSessionBeanRemote.deleteFlight(flight);
     }

    public void doUpdateFlight() throws FlightNotFoundException, UpdateFlightException, AircraftConfigurationNotFoundException, FlightRouteNotFoundException {
          Scanner sc = new Scanner(System.in);
             System.out.println("Enter Id of Flight> ");
             Long flight = sc.nextLong();
          
         System.out.println("Enter Id of AirConfig> ");
        Long aircraftConfiguration = sc.nextLong();
          
        System.out.println("Enter Id of FlightRoute> ");
        Long flightRoute = sc.nextLong();
        
        FlightEntity update = flightSessionBeanRemote.retrieveFlightByFlightId(flight);
        update.setAircraftConfiguration(aircraftConfigurationSessionBeanRemote.retrieveAircraftConfigurationByAircraftConfigurationId(aircraftConfiguration));
        update.setFlightRoute(flightRouteSessionBeanRemote.retrieveFlightRouteByFlightRouteId(flightRoute));
        
        flightSessionBeanRemote.updateFlight(update);
    }

    public void doViewFlightDetails() throws FlightNotFoundException {
             Scanner sc = new Scanner(System.in);
             System.out.println("Enter Id of Flight> ");
             Long flight = sc.nextLong();
          
            FlightEntity f = flightSessionBeanRemote.retrieveFlightByFlightId(flight);
        
        System.out.println("Details> ");
        System.out.println("Name: " + f.getFlightNum());
        System.out.println("Flight route: " + f.getFlightRoute() +" Origin: " +f.getFlightRoute().getOrigin().getCity()+ " Destination: " + f.getFlightRoute().getDestination().getCity());
    
    }

    public void doViewAllFlights() {
           List<FlightEntity> list =  flightSessionBeanRemote.retrieveAllFlights();
           
           for(FlightEntity r:list){
               if(!r.isComplementary()){
                System.out.println("Id: "+r.getFlightId()+ " Flight Number: " + r.getFlightNum());

               if(r.getComplentaryFlight()!=null) {
                 System.out.println("Id: "+r.getComplentaryFlight().getFlightId()+ " Flight Number: " + r.getComplentaryFlight().getFlightNum());

               }
            }
        }  
    }

    public void doCreateFlight() throws FlightRouteNotFoundException, AircraftConfigurationNotFoundException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Id of AirConfig> ");
        Long aircraftConfiguration = sc.nextLong();
          
        System.out.println("Enter Id of FlightRoute> ");
        Long flightRoute = sc.nextLong();
        
       System.out.println("Enter flight Number");
       Long fNum = sc.nextLong();
       
       String concat = "MA"+ fNum;
        FlightRouteEntity id = flightRouteSessionBeanRemote.retrieveFlightRouteByFlightRouteId(flightRoute);
       FlightEntity f = new FlightEntity(concat, true,id , aircraftConfigurationSessionBeanRemote.retrieveAircraftConfigurationByAircraftConfigurationId(aircraftConfiguration));
       f.setComplementary(false);
       
       
       if(id.getComplementaryReturnRoute()!=null){
       
       sc.nextLine();
     
        System.out.println("Would you like a ComplementaryFlight (Y/N)> ");
        String comp = sc.nextLine().trim();
      
      if(comp.equals("Y") ){
          
         concat = "MA" + fNum +"C";
         FlightEntity f2 = new FlightEntity(concat, true,id.getComplementaryReturnRoute(), aircraftConfigurationSessionBeanRemote.retrieveAircraftConfigurationByAircraftConfigurationId(aircraftConfiguration));
         
         f2.setComplementary(true);
         f2 = flightSessionBeanRemote.createNewFlight(f2);
         f.setComplentaryFlight(f2);
         flightSessionBeanRemote.createNewFlight(f);
         
          
             } else {
                    flightSessionBeanRemote.createNewFlight(f);

             }
      
       }  else {
          flightSessionBeanRemote.createNewFlight(f);
      }
    }

   

    
    
    
    
}
