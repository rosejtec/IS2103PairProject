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
import entity.FlightReservationDetailsEntity;
import entity.FlightReservationEntity;
import entity.FlightScheduleEntity;
import entity.FlightSchedulePlanEntity;
import entity.PassengerEntity;
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

    public void menuSalesManagement(EmployeeEntity e) throws InvalidAccessRightException {
        this.currentEmployeeEntity = e;
        if (currentEmployeeEntity.getAccessRight() == EmployeeAccessRight.SALESMANAGER) {

            Scanner scanner = new Scanner(System.in);
            Integer response = 0;

            while (true) {

                response = 0;

                while (response < 1 || response > 2) {

                    System.out.println("*** FRS Management Client :: Sales Management ***\n");
                    System.out.println("1: View Seats Inventory");
                    System.out.println("2: View Flight Reservations");
                    System.out.println("3: Back\n");
                    System.out.print("> ");

                    response = scanner.nextInt();

                    if (response == 1) {
                        try {
                            doViewSeatsInventory();
                        } catch (FlightNotFoundException ex) {
                            System.out.println("Flight does not exist!");
                        }
                    } else if (response == 2) {
                        try {
                            doViewFlightReservations();
                        } catch (FlightNotFoundException ex) {
                            System.out.println("Flight does not exist!");
                        }
                    } else if (response == 3) {
                        break;
                    } else {
                        System.out.println("Invalid option, please try again!\n");
                    }
                }

                if (response == 3) {
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
        /*
                for(FlightScheduleEntity fs : FfsList)
                {
                    System.out.println("Flight Schedule " + fs.getFlightScheduleId() + "; Departure: " + fs.getDeparture() + "; Arrival: " + fs.getArrival());
                }
         */
        System.out.println("Enter flight schedule local depature date (yyyy-MM-dd HH:mm)>");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime departure = LocalDateTime.parse(sc.nextLine().trim(), formatter);

        List<FlightScheduleEntity> fsList = flightSessionBeanRemote.retrieveFlightSchedule(f, departure);
        
     
        for (FlightScheduleEntity fs : fsList) {
                System.out.println("Flight Schedule " + fs.getFlightScheduleId() + "; Departure: " + fs.getDeparture() + "; Arrival: " + fs.getArrival());
                System.out.println("Total Available Seats: " +fs.getSeatsInventory().getAvailableSeats() + "; Total Reserved Seats: " + fs.getSeatsInventory().getReservedSeats() + "; Balance Seats: " + fs.getSeatsInventory().getBalanceSeats());
               List<FareEntity> fareList = fs.getFlightSchedulePlan().getFares();
               System.out.println(fareList.size());
            for (FareEntity fare : fareList) {
                if (fare.getCabinClassType().equals(CabinClassType.F)) {
                    int A =fs.getSeatsInventory().getAvailableF();
                    int R= fs.getSeatsInventory().getReservedF();
                    Integer bal =A-R;
                    System.out.println("Cabin Class F> Available Seats: " + fs.getSeatsInventory().getAvailableF() + "; Reserved Seats: " + fs.getSeatsInventory().getReservedF() + "; Balance Seats: " + bal);
                } else if (fare.getCabinClassType().equals(CabinClassType.J)) {
                    int A =fs.getSeatsInventory().getAvailableJ();
                    int R= fs.getSeatsInventory().getReservedJ();
                    Integer bal =A-R;

                    System.out.println(" Cabin Class J> Available Seats: " + fs.getSeatsInventory().getAvailableJ() + "; Reserved Seats: " + fs.getSeatsInventory().getReservedJ() + "; Balance Seats: " + bal);
                } else if (fare.getCabinClassType().equals(CabinClassType.W)) {
                    int A =fs.getSeatsInventory().getAvailableW();
                    int R= fs.getSeatsInventory().getReservedW();
                    Integer bal =A-R;
                    System.out.println("Cabin Class W> Available Seats: " + fs.getSeatsInventory().getAvailableW() + "; Reserved Seats: " + fs.getSeatsInventory().getReservedW() + "; Balance Seats: " + bal);
                } else if (fare.getCabinClassType().equals(CabinClassType.Y)) {
                    int A =fs.getSeatsInventory().getAvailableY();
                    int R= fs.getSeatsInventory().getReservedY();
                    Integer bal =A-R;
                    System.out.println("Cabin Class Y> Available Seats: " + fs.getSeatsInventory().getAvailableY() + "; Reserved Seats: " + fs.getSeatsInventory().getReservedY() + "; Balance Seats: " +  bal);
                }
            }
        
        
        }


    }

    void doViewFlightReservations() throws FlightNotFoundException {
        /*
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
                for(FlightSchedulePlanEntity fsp : fspList)
                {
                    List<FareEntity> fareList =  fsp.getFares();
                    for(FareEntity fare : fareList)
                    {
                        
                        if (fare.getCabinClassType().equals(CabinClassType.F))
                        {
                            //List<FlightReservationEntity> frList = fsToView.getFlightReservations();
                            for (FlightReservationEntity fr : frList)
                            {
                                if(fr.getTotalAmount()/fr.getTotalPassengers() == fare.getFareAmount())
                                System.out.println("Cabin Class Type F> Fare Basis Code");
                                
                                for(int i = 0 ; i<fr.getTotalPassengers();i++){
                                    
                                  System.out.println(fr.getPassenger().get(i) + );
                                }
                                List<FlightReservationDetailsEntity> frdList = fr.getInBound();
                                for(FlightReservationDetailsEntity frd : frdList)
                                {
                                    System.out.println("Seat number: " 
                                }
                            }
                            
                            
                            
                            System.out.println("Cabin Class F> Available Seats: " + fsToView. + "; Reserved Seats: " + fsToView.getSeatsInventory().getReservedF() + "; Balance Seats: " + (fsToView.getSeatsInventory().getAvailableF()-fsToView.getSeatsInventory().getReservedF()));
                        } else if (fare.getCabinClassType().equals(CabinClassType.J)) 
                        {
                            System.out.println(" Cabin Class J> Available Seats: " + fsToView.getSeatsInventory().getAvailableJ() + "; Reserved Seats: " + fsToView.getSeatsInventory().getReservedJ() + "; Balance Seats: " + (fsToView.getSeatsInventory().getAvailableJ()-fsToView.getSeatsInventory().getReservedJ()));
                        } else if (fare.getCabinClassType().equals(CabinClassType.W)) 
                        {
    

System.out.println(" Cabin Class W> Available Seats: " + fsToView.getSeatsInventory().getAvailableW() + "; Reserved Seats: " + fsToView.getSeatsInventory().getReservedW() + "; Balance Seats: " + (fsToView.getSeatsInventory().getAvailableW()-fsToView.getSeatsInventory().getReservedW()));

         */
    }

}
