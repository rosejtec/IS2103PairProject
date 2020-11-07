/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frsmanagementclient;

import ejb.session.stateless.FlightSchedulePlanSessionBeanRemote;
import ejb.session.stateless.FlightSessionBeanRemote;
import entity.EmployeeEntity;
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
    
       public void menuFlightPlanning(EmployeeEntity e) throws InvalidAccessRightException
    {
        this.currentEmployeeEntity= e;
        if(currentEmployeeEntity.getAccessRight() == EmployeeAccessRight.SCHEDULEMANAGER )
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

                    createFlight();
                }
                
                else if(response == 2)
                {
                    viewAllFlights();
                }
                else if(response == 3)
                {
                   viewFlightDetails();
                }
                else if(response == 4)
                {
                 updateFlight() ; 
                }
                else if(response == 5)
                {
                    deleteFlight();
                }
                else if(response == 6)
                {
                  createFlightSchedulePlan();
                }
                 else if(response == 7)
                {
                    viewAllFlightSchedulePlan();
                }
                else if(response == 8)
                {
                 viewFlightSchedulePlanDetails();  
                }
                  else if(response == 9)
                {
                   updateFlightSchedulePlan();
                }
                 else if(response == 10)
                {
                   deleteFlightSchedulePlan();
                   
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
  

    public void createFlightSchedulePlan() {
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
            list.add(new FlightScheduleEntity(d,duration));
            
        } else if( type==2) {
            fsp.setSchedule(ScheduleEnum.MULTIPLE);
            System.out.println("Enter the number of schedules");
            int n = sc.nextInt();
            
            for(int i=0 ; i < n; i++){
                 System.out.println("Enter local depature date >");
         LocalDateTime d =LocalDateTime.of(2017, 1, 14, 10, 34);
           System.out.println("Enter estimated flight duration >");
         int duration = sc.nextInt();
         list.add(new FlightScheduleEntity(d,duration));
            }
            
        } else if( type ==3){
            fsp.setSchedule(ScheduleEnum.RECURRENTDAY);
              System.out.println("Enter every n days");
            int n = sc.nextInt();
            System.out.println("Enter end date >");
         LocalDateTime d =LocalDateTime.of(2017, 1, 14, 10, 34);
           System.out.println("Enter duration");
         int duration = sc.nextInt();
        fsp.setEnd(d);
         fsp.setN(n);
         for(int i =0 ;i<n;i++){
            list.add(new FlightScheduleEntity(d,duration));
             d = d.plusDays(n);
         }
        } else {
              
     fsp.setSchedule(ScheduleEnum.RECURRENTWEEK);
            System.out.println("Enter end date >");
         LocalDateTime d =LocalDateTime.of(2017, 1, 14, 10, 34);
           System.out.println("Enter duration");
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
        
        FlightEntity newFlightEntity;
        try {
            newFlightEntity = flightSessionBeanRemote.retrieveFlightByFlightNumber(flightNum);
            fsp.setFlight(newFlightEntity);
        } catch (FlightNotFoundException ex) {
            Logger.getLogger(FlightOperationModule.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       flightSchedulePlanSessionBeanRemote.createFlightSchedulePlan(fsp, list);
    }

    private void deleteFlightSchedulePlan() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void updateFlightSchedulePlan() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void viewFlightSchedulePlanDetails() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void viewAllFlightSchedulePlan() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void deleteFlight() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void updateFlight() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void viewFlightDetails() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void viewAllFlights() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void createFlight() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   

    
    
}
