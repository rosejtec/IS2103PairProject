/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frsmanagementclient;

import ejb.session.stateless.FlightSessionBeanRemote;
import ejb.session.stateless.SeatsInventorySessionBeanRemote;
import entity.EmployeeEntity;
import entity.FareEntity;
import entity.FlightEntity;
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
import util.enumeration.CabinClassType;
import util.enumeration.EmployeeAccessRight;
import util.exception.FlightNotFoundException;
import util.exception.FlightScheduleNotFoundException;
import util.exception.InvalidAccessRightException;

/**
 *
 * @author quahjingxin
 */
public class SalesManagementModule {
 
    @EJB
    private FlightSessionBeanRemote flightSessionBeanRemote;
    @EJB
    private SeatsInventorySessionBeanRemote seatsInventorySessionBeanRemote;
    
    private EmployeeEntity currentEmployeeEntity;

    public SalesManagementModule(FlightSessionBeanRemote flightSessionBeanRemote, SeatsInventorySessionBeanRemote seatsInventorySessionBeanRemote, EmployeeEntity currentEmployeeEntity) {
        this.flightSessionBeanRemote = flightSessionBeanRemote;
        this.seatsInventorySessionBeanRemote = seatsInventorySessionBeanRemote;
    }
    
    public void menuSalesManagement(EmployeeEntity e) throws InvalidAccessRightException
    {
        this.currentEmployeeEntity= e;
        if(currentEmployeeEntity.getAccessRight() == EmployeeAccessRight.SALESMANAGER)
        {
            
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        
        while(true)
        {
          
            response = 0;
            
            while(response < 1 || response > 2)
            {
                
            System.out.println("*** FRS Management Client :: Sales Management ***\n");
            System.out.println("1: View Seats Inventory");
            System.out.println("2: View Flight Reservations");
            System.out.println("3: Back\n");
            System.out.print("> ");

            response = scanner.nextInt();

                if(response == 1)
                {
                try {
                    doViewSeatsInventory();
                } catch (FlightNotFoundException ex) {
                   System.out.println("Flight does not exist!");
                 }
                }
                
                else if(response == 2)
                {
                try {
                    doViewFlightReservations();
                } catch (FlightNotFoundException ex) {
                    System.out.println("Flight does not exist!");
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
           
        } else {
             throw new InvalidAccessRightException("You don't have SALES MANAGER rights to access the flight planning module.");
        }  
    }

    private void doViewSeatsInventory() throws FlightNotFoundException {
       
           Scanner sc = new Scanner(System.in);
                System.out.println("Enter flight number> ");

                FlightEntity f = flightSessionBeanRemote.retrieveFlightByFlightNumber(sc.nextLine().trim());
                
                
                List<FlightSchedulePlanEntity> fspList = f.getFlightSchedulePlans();
                List<FlightScheduleEntity> fsList = new ArrayList();
                
                for(FlightSchedulePlanEntity fsp : fspList)
                {
                    List<FlightScheduleEntity> fs1 = fsp.getFlightSchedules();
                    for(FlightScheduleEntity fs2 : fs1)
                    {
                        fsList.add(fs2);
                    }                    
                }
                /*
                for(FlightScheduleEntity fs : fsList)
                {
                    System.out.println("Flight Schedule " + fs.getFlightScheduleId() + "; Departure: " + fs.getDeparture() + "; Arrival: " + fs.getArrival());
                }
                */
                System.out.println("Enter flight schedule local depature date (yyyy-MM-dd HH:mm)>");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime departure = LocalDateTime.parse(sc.nextLine().trim(), formatter);
            
              FlightScheduleEntity fsToView = null;
             
                for(FlightScheduleEntity fs : fsList)
                {
                    if (departure.getDayOfWeek().equals(fs.getDeparture().getDayOfWeek()))
                    {
                    fsToView = fs;
                    System.out.println("Flight Schedule " + fsToView.getFlightScheduleId() + "; Departure: " + fsToView.getDeparture() + "; Arrival: " + fsToView.getArrival());
                    } 
                }
                
                System.out.println("Total Available Seats: " + fsToView.getSeatsInventory().getAvailableSeats() + "; Total Reserved Seats: " + fsToView.getSeatsInventory().getReservedSeats() + "; Balance Seats: " + fsToView.getSeatsInventory().getBalanceSeats());
            
                 for(FlightSchedulePlanEntity fsp : fspList)
                {
                    List<FareEntity> fareList =  fsp.getFares();
                    for(FareEntity fare : fareList)
                    {
                        if (fare.getCabinClassType().equals(CabinClassType.F))
                        {
                            System.out.println("Cabin Class F> Available Seats: " + fsToView.getSeatsInventory().getAvailableF() + "; Reserved Seats: " + fsToView.getSeatsInventory().getReservedF() + "; Balance Seats: " + (fsToView.getSeatsInventory().getAvailableF()-fsToView.getSeatsInventory().getReservedF()));
                        } else if (fare.getCabinClassType().equals(CabinClassType.J)) 
                        {
                            System.out.println(" Cabin Class J> Available Seats: " + fsToView.getSeatsInventory().getAvailableJ() + "; Reserved Seats: " + fsToView.getSeatsInventory().getReservedJ() + "; Balance Seats: " + (fsToView.getSeatsInventory().getAvailableJ()-fsToView.getSeatsInventory().getReservedJ()));
                        } else if (fare.getCabinClassType().equals(CabinClassType.W)) 
                        {
                            System.out.println(" Cabin Class W> Available Seats: " + fsToView.getSeatsInventory().getAvailableW() + "; Reserved Seats: " + fsToView.getSeatsInventory().getReservedW() + "; Balance Seats: " + (fsToView.getSeatsInventory().getAvailableW()-fsToView.getSeatsInventory().getReservedW()));
                        } else if (fare.getCabinClassType().equals(CabinClassType.Y))
                        {
                            System.out.println(" Cabin Class Y> Available Seats: " + fsToView.getSeatsInventory().getAvailableY() + "; Reserved Seats: " + fsToView.getSeatsInventory().getReservedY() + "; Balance Seats: " + (fsToView.getSeatsInventory().getAvailableY()-fsToView.getSeatsInventory().getReservedY()));
                        }
                    }
                    
                }
            } 
  
    

    private void doViewFlightReservations() throws FlightNotFoundException {

         Scanner sc = new Scanner(System.in);
         System.out.println("Enter flight number> ");

         FlightEntity f = flightSessionBeanRemote.retrieveFlightByFlightNumber(sc.nextLine().trim());
         
         List<FlightSchedulePlanEntity> fspList = f.getFlightSchedulePlans();
                List<FlightScheduleEntity> fsList = new ArrayList();
                
                for(FlightSchedulePlanEntity fsp : fspList)
                {
                    List<FlightScheduleEntity> fs1 = fsp.getFlightSchedules();
                    for(FlightScheduleEntity fs2 : fs1)
                    {
                        fsList.add(fs2);
                    }                    
                }
                 System.out.println("Enter flight schedule local depature date (yyyy-MM-dd HH:mm)>");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime departure = LocalDateTime.parse(sc.nextLine().trim(), formatter);

            
              FlightScheduleEntity fsToView = null;
             
                for(FlightScheduleEntity fs : fsList)
                {
                    if (departure.getDayOfWeek().equals(fs.getDeparture().getDayOfWeek()))
                    {
                    fsToView = fs;
                    }
                }
                
         //   fsToView.getFlightSchedulePlan().getFares()
                
                
    }
}
