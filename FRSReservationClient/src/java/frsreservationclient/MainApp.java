/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frsreservationclient;

import ejb.session.stateless.CustomerSessionBeanRemote;
import ejb.session.stateless.FlightReservationSessionBeanRemote;
import ejb.session.stateless.ReservationSessionBeanRemote;
import entity.AirportEntity;
import entity.CustomerEntity;
import entity.FlightReservationDetailsEntity;
import entity.FlightReservationEntity;
import entity.FlightScheduleEntity;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author leahr
 */
public class MainApp {
 
     @EJB
    private static ReservationSessionBeanRemote reservationSessionBean;
    
    @EJB
    private static FlightReservationSessionBeanRemote flightReservationSessionBean;

    @EJB
    private static CustomerSessionBeanRemote customerSessionBean;
    CustomerEntity  currentCustomer;

    MainApp(FlightReservationSessionBeanRemote flightReservationSessionBean, CustomerSessionBeanRemote customerSessionBean,ReservationSessionBeanRemote reservationSessionBean) {
        this.flightReservationSessionBean=flightReservationSessionBean;
        this.customerSessionBean= customerSessionBean;
        this.reservationSessionBean= reservationSessionBean;
    }




    void runApp() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        
        while(true)
        {
            System.out.println("*** Welcome to Mock PE System ***\n");
           System.out.println("1: Register As Customer");
            System.out.println("2: Customer Login");
            System.out.println("3: Search Flight");
            System.out.println("4: Reserve Flight");
            System.out.println("5: View My Flight Reservations");
            System.out.println("6: View My Flight Reservation Details");
            System.out.println("7: Customer Logout");

            System.out.println("8: Exit\n");
            response = 0;
            
            while(response < 1 || response > 8)
            {
                System.out.print("> ");

                response = scanner.nextInt();

                if(response == 1)
                {
                    RegisterCustomer();
                }
                else if(response == 2)
                {
                    try {
                        CustomerLogin();
                    } catch (InvalidLoginCredentialException ex) {
                        Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                 else if(response == 3)
                {
                    try {
                        searchFlights();
                    } catch (ParseException ex) {
                        Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else if(response == 4)
                {
                    reserveFlights();
                }
                else if(response == 5)
                {
                    viewFlightReeservations();
                }
                else if(response == 6)
                {
                    viewFlightReservationDetails();
                }
                 else if(response == 7)
                {
                    customerLogout();
                }
                else if (response == 8)
                {
                    break;
                }
                else
                {
                    System.out.println("Invalid option, please try again!\n");                
                }
            }
            
            if(response == 6)
            {
                break;
            }
        }
        
    }

    private void viewFlightReservationDetails() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void customerLogout() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void viewFlightReeservations() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void reserveFlights() {
            System.out.println("Connecting Flight : 1.Yes  2.No > ");

           
            Scanner sc = new Scanner(System.in);
            FlightReservationEntity fr = new FlightReservationEntity();
            System.out.println("Connecting Flight : 1.Yes  2.No > ");
            int t = sc.nextInt();
            boolean connecting = (t==1)? true:false;
            
            for(int i = 0 ; i<2; i++) {
                System.out.println("Enter Cabin Class for Inbound: ");
                String cabin= sc.next();            
                System.out.println("Enter Flight Schedule Id for Inbound: ");
                Long id = sc.nextLong();
             //   fr.getInBound().add(new FlightReservationDetailsEntity(cabin,reservationSessionBean.retrieveById(id)));
                if(!connecting) {
                    break;
                }
            }
            
            
      
            
  
               

    }

    private void searchFlights() throws ParseException {
            Scanner scanner = new Scanner(System.in);
            Integer response = 0;
            SimpleDateFormat inputDateFormat = new SimpleDateFormat("d/M/y");
            SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
            Date departureDate;
            Date returnDate;
            Boolean round;
            String departureAirport="SIN";
            String destinationAirport="MUM";
            boolean connecting;
            int passengers=1;
            
//            System.out.println("*** Holiday Reservation System :: Search Holiday ***\n");
            System.out.print("Enter Departure Date (dd/mm/yyyy)> ");
            departureDate = inputDateFormat.parse("09/11/2020");
//            System.out.print("Enter Departure Airport> ");
//            departureAirport = scanner.nextLine().trim();
//            System.out.print("Enter Destination Airport> ");
//            destinationAirport = scanner.nextLine().trim();
//            System.out.print("Enter Number of Travellers> ");
//            passengers = scanner.nextInt();
////            System.out.print("Enter cabin class ");
          String cabin = " ";
//            System.out.print("Trip Type : 1.One-way 2.Round > ");

//            int t= scanner.nextInt();
//            if(t == 1){
//                   round =false;
//            } else {
//                   round =true;
//                   System.out.print("Enter Return Date (dd/mm/yyyy)> ");
//                   returnDate = inputDateFormat.parse(scanner.nextLine().trim());            
//            }
//           
//          
//            System.out.print("Connecting Flight : 1.Yes  2.No > ");
//            int t2= scanner.nextInt();
//            connecting =(t2==1)? true : false;   
           
            
        System.out.println(departureDate);
            List<FlightScheduleEntity> day1 = flightReservationSessionBean.searchSingleDay(false,false, departureAirport,destinationAirport, departureDate, passengers, cabin);
         System.out.println(day1.size());
                        
            for(FlightScheduleEntity l:day1) {
                System.out.println(l.getFlightScheduleId() + " "  +l.getArrival().toString()+ " "+ l.getDeparture().toString() + " " +l.getDuration());
            }
            
            List<FlightScheduleEntity> day3before = flightReservationSessionBean.searchThreeDaysBefore(false,false, departureAirport,destinationAirport, departureDate, passengers, cabin);
         System.out.println(day3before.size());

            for(FlightScheduleEntity l:day3before) {
                System.out.println(l.getFlightScheduleId() + " "  +l.getArrival().toString()+ " "+ l.getDeparture().toString() + " " +l.getDuration());
            }
                        
            List<FlightScheduleEntity> day3after = flightReservationSessionBean.searchThreeDaysAfter(false,false, departureAirport,destinationAirport, departureDate, passengers, cabin);

            for(FlightScheduleEntity l:day3after) {
                System.out.println(l.getFlightScheduleId() + " "  +l.getArrival().toString()+ " "+ l.getDeparture().toString() + " " +l.getDuration());
            }
    }

    private void RegisterCustomer() {
    }

    private void CustomerLogin() throws InvalidLoginCredentialException {
        Scanner scanner = new Scanner(System.in);
        String email = "";
        String password = "";
        
        System.out.println("*** Holiday Reservation System :: Login ***\n");
        System.out.print("Enter email> ");
        email = scanner.nextLine().trim();
        System.out.print("Enter password> ");
        password = scanner.nextLine().trim();
        
        if(email.length() > 0 && password.length() > 0)
        {
            currentCustomer = customerSessionBean.login(email, password);
        }
        else
        {
            throw new InvalidLoginCredentialException("Missing login credential!");
        }
    }


}
