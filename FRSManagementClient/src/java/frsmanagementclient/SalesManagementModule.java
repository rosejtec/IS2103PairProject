/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frsmanagementclient;

import ejb.session.stateless.FlightSessionBeanRemote;
import ejb.session.stateless.SeatsInventorySessionBeanRemote;
import entity.EmployeeEntity;
import entity.FlightEntity;
import entity.FlightScheduleEntity;
import entity.FlightSchedulePlanEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
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
                     doViewSeatsInventory();
                }
                
                else if(response == 2)
                {
                    doViewFlightReservations();
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

    private void doViewSeatsInventory() {
       
           Scanner sc = new Scanner(System.in);
                System.out.println("Enter flight number> ");
                
            try {
                
                
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
                
                for(FlightScheduleEntity fs : fsList)
                {
                    System.out.println("Flight Schedule " + fs.getFlightScheduleId() + "; Departure: " + fs.getDeparture() + "; Arrival: " + fs.getArrival());
                }
                
                System.out.println("Select flight schedule number to view> ");
                try {
                    FlightScheduleEntity fsToView = seatsInventorySessionBeanRemote.retrieveFlightScheduleByFlightScheduleId(sc.nextLong());
                    int maxCapacity = f.getAircraftConfiguration().getAircraftType().getMaxCapacity();
                    System.out.println("Available Seats: " + fsToView.getSeatsInventory().getAvailableSeats());
                    System.out.println("Balance Seats: " + fsToView.getSeatsInventory().getBalanceSeats());
                    System.out.println("Reserved Seats: " + fsToView.getSeatsInventory().getReservedSeats());
                    
                } catch (FlightScheduleNotFoundException ex) {
                    Logger.getLogger(SalesManagementModule.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            } catch (FlightNotFoundException ex) {
                Logger.getLogger(SalesManagementModule.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    

    private void doViewFlightReservations() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
