/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frsmanagementclient;

import ejb.session.stateless.FlightSchedulePlanSessionBeanRemote;
import ejb.session.stateless.FlightSessionBeanRemote;
import entity.EmployeeEntity;
import entity.FareEntity;
import entity.FlightEntity;
import entity.FlightScheduleEntity;
import entity.FlightSchedulePlanEntity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import util.enumeration.EmployeeAccessRight;
import util.enumeration.ExceedsMaximumCapacityException;
import util.enumeration.ScheduleEnum;
import util.exception.AircraftConfigurationNotFoundException;
import util.exception.AircraftTypeNotFoundException;
import util.exception.FlightNotFoundException;
import util.exception.FlightRouteNotFoundException;
import util.exception.FlightSchedulePlanNotFoundException;
import util.exception.InvalidAccessRightException;

/**
 *
 * @author quahjingxin
 */
public class FlightOperationModule {
    
    @EJB
    private static FlightSessionBeanRemote flightSessionBeanRemote;
    
    @EJB
    private static FlightSchedulePlanSessionBeanRemote flightSchedulePlanSessionBeanRemote;
    
    
    EmployeeEntity currentEmployeeEntity;

    FlightOperationModule(FlightSessionBeanRemote flightSessionBeanRemote, FlightSchedulePlanSessionBeanRemote flightSchedulePlanSessionBeanRemote, EmployeeEntity currentEmployeeEntity) {
        this.flightSessionBeanRemote = flightSessionBeanRemote;
        this.flightSchedulePlanSessionBeanRemote = flightSchedulePlanSessionBeanRemote;
        this.currentEmployeeEntity = currentEmployeeEntity;
    }
    
       public void menuFlightOperation(FlightSessionBeanRemote flightSessionBeanRemote, FlightSchedulePlanSessionBeanRemote flightSchedulePlanSessionBeanRemote, EmployeeEntity e) throws InvalidAccessRightException
    {
        this.currentEmployeeEntity= e;
        if(currentEmployeeEntity.getAccessRight() == EmployeeAccessRight.SCHEDULEMANAGER);
        {
            Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        
        while(true)
        {
          
            response = 0;
            
            while(response < 1 || response > 6)
            {
             System.out.println("*** FRS Management Client :: Flight Planning ***\n");
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
                    doCreateFlight();
                }
                else if(response == 2)
                {
                    doViewAllFlights();
                }
                else if(response == 3)
                {
                   doViewFlightDetails();
                }
                else if(response == 4)
                {
                 doUpdateFlight() ; 
                }
                else if(response == 5)
                {
                    doDeleteFlight();
                }
                else if(response == 6)
                {
                  doCreateFlightSchedulePlan();
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
                   doUpdateFlightSchedulePlan();
                }
                 else if(response == 10)
                {
                   doDeleteFlightSchedulePlan();
                   
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
            
            if(response == 11)
            {
                break;
            }
            
           }
           
        } else {
             throw new InvalidAccessRightException("You don't have SCHEDULE MANAGER rights to access the flight planning module.");
        }    
       

        
    }
  

    public void doCreateFlightSchedulePlan() {
        Scanner sc = new Scanner(System.in);
        FlightSchedulePlanEntity fsp = new FlightSchedulePlanEntity();
        System.out.println(" Select type of FlightScedulePlan");
        System.out.println("1. SINGLE, 2. MULTIPLE, 3. RECURRENT DAY, 4. RECURRENT WEEK");
        int type= sc.nextInt();
        
        List<FlightScheduleEntity> list = new ArrayList<FlightScheduleEntity>(); 
  
        if(type==1) {
            fsp.setSchedule(ScheduleEnum.SINGLE);
             System.out.println("Enter local depature date >");
          LocalDateTime d =LocalDateTime.of(2017, 1, 14, 10, 34);
           System.out.println("Enter estimated flight duration >");
         int duration = sc.nextInt();
         LocalDateTime arrival = d.plusHours(duration);
            list.add(new FlightScheduleEntity(d,arrival,duration));
            
        } else if( type==2) {
            fsp.setSchedule(ScheduleEnum.MULTIPLE);
            System.out.println("Enter the number of schedules");
            int n = sc.nextInt();
            
            for(int i=0 ; i < n; i++){
                 System.out.println("Enter local depature date >");
         LocalDateTime d =LocalDateTime.of(2017, 1, 14, 10, 34);
           System.out.println("Enter estimated flight duration >");
         int duration = sc.nextInt();
         LocalDateTime arrival = d.plusHours(duration);
         list.add(new FlightScheduleEntity(d,arrival,duration));
            }
            
        } else if( type ==3){
            fsp.setSchedule(ScheduleEnum.RECURRENTDAY);
              System.out.println("Enter every n days");
            int n = sc.nextInt();
            System.out.println("Enter end date >");
         LocalDateTime d =LocalDateTime.of(2017, 1, 14, 10, 34);
           System.out.println("Enter duration");
         int duration = sc.nextInt();
         LocalDateTime arrival = d.plusHours(duration);
        fsp.setEnd(d);
         fsp.setN(n);
         for(int i =0 ;i<n;i++){
            list.add(new FlightScheduleEntity(d,arrival,duration));
             d = d.plusDays(n);
         }
        } else {
              
     fsp.setSchedule(ScheduleEnum.RECURRENTWEEK);
            System.out.println("Enter end date >");
         LocalDateTime d =LocalDateTime.of(2017, 1, 14, 10, 34);
           System.out.println("Enter duration >");
         int duration = sc.nextInt();
         LocalDateTime end= d;
        
         fsp.setEnd(end);
         while (d.isBefore(end) || d.isEqual(end)){
            list.add(new FlightScheduleEntity(d,duration));
             d = d.plusWeeks(1);
         }
       }
        
        System.out.println("Enter flight num");
        String flightNum = sc.nextLine().trim();
        Long id = flightSchedulePlanSessionBeanRemote.createFlightSchedulePlan(fsp, list);
        FlightEntity newFlightEntity;
        
        System.out.println("Enter end date >");
        
        
        try {
            newFlightEntity = flightSessionBeanRemote.retrieveFlightByFlightNumber(flightNum);
            fsp.setFlight(newFlightEntity);
            if(newFlightEntity.getFlightRoute().getComplementaryReturnRoute() != null) {
                System.out.println("Would you like a complementary return flight? (Y/N)> ");
                 if(sc.nextLine().trim().equals("Y")){
                     System.out.println("Enter layover duration >"); 
                     int layoverDuration = sc.nextInt();
                     FlightSchedulePlanEntity complementaryFsp = new FlightSchedulePlanEntity();
                   List<FlightScheduleEntity> complementaryFs = new ArrayList<FlightScheduleEntity>(); 
                   //for(LocalDateTime arrival: fsp.getFlightSchedules().getArrival())
                   //complementaryFs.add(new FlightScheduleEntity(arrival, arrival+layoverDuration,))
                     
                    try {
                        flightSchedulePlanSessionBeanRemote.createNewComplementaryFlightSchedulePlan(id, complementaryFsp);
                    } catch (FlightSchedulePlanNotFoundException ex) {
                        Logger.getLogger(FlightOperationModule.class.getName()).log(Level.SEVERE, null, ex);
                    }
                 }
            }
        } catch (FlightNotFoundException ex) {
            Logger.getLogger(FlightOperationModule.class.getName()).log(Level.SEVERE, null, ex);
        }
  
        System.out.println("First class - Enter number of fares>");
        for(int i = 0; i < sc.nextInt(); i++) {
            FareEntity f = new FareEntity();
            System.out.println("First Class - Enter fare basis code>");
            f.setFareBasisCode(sc.nextLine().trim());
            System.out.println("First Class - Enter fare amount>");
            f.setFareAmount(sc.nextLine().trim());
            try {
                flightSchedulePlanSessionBeanRemote.createNewFare(id, f);
            } catch (FlightSchedulePlanNotFoundException ex) {
                Logger.getLogger(FlightOperationModule.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        System.out.println("Business Class - Enter number of fares>");
        for(int i = 0; i < sc.nextInt(); i++) {
            FareEntity j = new FareEntity();
            System.out.println("Business Class - Enter fare basis code>");
            j.setFareBasisCode(sc.nextLine().trim());
            System.out.println("Business Class - Enter fare amount>");
            j.setFareAmount(sc.nextLine().trim());
            try {
                flightSchedulePlanSessionBeanRemote.createNewFare(id, j);
            } catch (FlightSchedulePlanNotFoundException ex) {
                Logger.getLogger(FlightOperationModule.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        System.out.println("Premium Economy Class - Enter number of fares>");
        for(int i = 0; i < sc.nextInt(); i++) {
            FareEntity w = new FareEntity();
            System.out.println("Premium Economy Class - Enter fare basis code>");
            w.setFareBasisCode(sc.nextLine().trim());
            System.out.println("Premium Economy Class - Enter fare amount>");
            w.setFareAmount(sc.nextLine().trim());
            try {
                flightSchedulePlanSessionBeanRemote.createNewFare(id, w);
            } catch (FlightSchedulePlanNotFoundException ex) {
                Logger.getLogger(FlightOperationModule.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        System.out.println("Economy Class - Enter number of fares>");
        for(int i = 0; i < sc.nextInt(); i++) {
            FareEntity y = new FareEntity();
            System.out.println("Economy Class - Enter fare basis code>");
            y.setFareBasisCode(sc.nextLine().trim());
            System.out.println("Economy Class - Enter fare amount>");
            y.setFareAmount(sc.nextLine().trim());
            try {
                flightSchedulePlanSessionBeanRemote.createNewFare(id, y);
            } catch (FlightSchedulePlanNotFoundException ex) {
                Logger.getLogger(FlightOperationModule.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }


    public void doViewAllFlightSchedulePlans() {
       List<FlightSchedulePlanEntity> fsp = flightSchedulePlanSessionBeanRemote.retrieveAllFlightSchedulePlans();
    
       for(FlightSchedulePlanEntity fsp1 : fsp) {
           System.out.println("Flight Schedule Plan ID: " + fsp1.getFightSchedulePlanId() + " Flight Number: " + fsp1.getFlightNum());
           
           if(fsp1.getComplementaryFlightSchedulePlan() != null) {
               System.out.println("Complementary Flight Schedule Plan ID: " + fsp1.getComplementaryFlightSchedulePlan().getFightSchedulePlanId()+ "\n" + "Flight Number: " + fsp1.getComplementaryFlightSchedulePlan().getFlightNum());
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
            System.out.println(">>> Fares <<<");
            for(FareEntity f1 : f) {
                System.out.println("Cabin Class Type: " + f1.getCabinClassConfiguration().getCabinClassType());
                System.out.println("Fare Basis Code: " + f1.getFareBasisCode());
                System.out.println("Fare Amount: " + f1.getFareAmount());
            }
        } catch (FlightSchedulePlanNotFoundException ex) {
            Logger.getLogger(FlightOperationModule.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
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
      */
           
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




    public void doDeleteFlight() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void doUpdateFlight() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void doViewFlightDetails() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void doViewAllFlights() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void doCreateFlight() {
        
    }

   

    
    
}
