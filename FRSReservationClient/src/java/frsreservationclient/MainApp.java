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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import util.enumeration.CabinClassType;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author leahr
 */
public class MainApp {
 
   ReservationSessionBeanRemote reservationSessionBean;
    FlightReservationSessionBeanRemote flightReservationSessionBean;
    CustomerSessionBeanRemote customerSessionBean;
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
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
           
            Boolean round;
            String departureAirport="SIN";
            String destinationAirport="MUM";
            boolean connecting;
            int passengers=1;
            
            System.out.println("Enter local depature date (yyyy-MM-dd HH:mm)>");
            String date = scanner.nextLine().trim();
            LocalDateTime departureTime = LocalDateTime.parse(date, formatter);
              LocalDateTime returnTime = null; 
            
            System.out.print("Enter Departure Airport> ");
            departureAirport = scanner.nextLine().trim();
            System.out.print("Enter Destination Airport> ");
            destinationAirport = scanner.nextLine().trim();
            System.out.print("Enter Number of Travellers> ");
            passengers = scanner.nextInt();
            System.out.print("Enter cabin class type 1.FirstClass 2.BusinessClass 3.PremiumEconomy 4.Economy ");
            CabinClassType type = null;
            int cabin = scanner.nextInt();
            switch(cabin){
                case 1 : type =CabinClassType.F;break;
                case 2 : type =CabinClassType.J;break;
                case 3 : type =CabinClassType.W;break;
                case 4 : type =CabinClassType.Y;break;
            }
            
            System.out.print("Trip Type : 1.One-way 2.Round > ");

            int t = scanner.nextInt();
            if(t == 1){
                   round =false;
            } else {
                  scanner.nextLine();
                  round =true;
                  System.out.println("Enter local depature date (yyyy-MM-dd HH:mm)>");
                  String date1 = scanner.nextLine().trim();
                  returnTime = LocalDateTime.parse(date1, formatter);          
            }
           
          
            System.out.print("Connecting Flight : 1.Yes  2.No > ");
            int t2= scanner.nextInt();
            connecting =(t2==1)? true : false;   
           
            if(round || !round) {
            System.out.println("InGoing Flights:");
            System.out.println();
                             
            
                                
            System.out.println("Direct on the Same Day"); 
            List<FlightScheduleEntity> day1 = flightReservationSessionBean.searchSingleDay(false,false, departureAirport,destinationAirport, departureTime, passengers, type);
            //System.out.println(day1.size());
            System.out.println("FlightScheduleId       ArrivalTime            DepartureTime          Duration");            
            for(FlightScheduleEntity l:day1) {
                 System.out.println();

                System.out.println(l.getFlightScheduleId() + "                     "  +l.getArrival().toString()+ "       "+ l.getDeparture().toString() + "       " +l.getDuration());
                    
                    System.out.println();
                System.out.println("                  SinglePassenger   AllPassenger");
                                   
                System.out.println("1.FirstClass:     "  + (!(reservationSessionBean.getFare(l, CabinClassType.F)).toString().equals("-1")? reservationSessionBean.getFare(l, CabinClassType.F) + "               " + (reservationSessionBean.getFare(l, CabinClassType.F)*passengers): "Not availablr"));
                System.out.println("2.BusinessClass:  " +  (!(reservationSessionBean.getFare(l, CabinClassType.J)).toString().equals("-1")? reservationSessionBean.getFare(l, CabinClassType.J) + "               " + (reservationSessionBean.getFare(l, CabinClassType.J)*passengers): "Not availablr"));
                System.out.println("3.PremiumEconomy: " +  (!(reservationSessionBean.getFare(l, CabinClassType.W)).toString().equals("-1")? reservationSessionBean.getFare(l, CabinClassType.W) + "               " + (reservationSessionBean.getFare(l, CabinClassType.W)*passengers): "Not availablr"));
                System.out.println("4.Economy:        "  +  (!(reservationSessionBean.getFare(l, CabinClassType.Y)).toString().equals("-1")? reservationSessionBean.getFare(l, CabinClassType.Y) + "               " + (reservationSessionBean.getFare(l, CabinClassType.Y)*passengers): "Not availablr"));
            }
            
            System.out.println();
            System.out.println("Direct 3 Day Before"); 

            List<FlightScheduleEntity> day3before = flightReservationSessionBean.searchThreeDaysBefore(false,false, departureAirport,destinationAirport, departureTime, passengers, type);
             //System.out.println(day3before.size());
            System.out.println("FlightScheduleId       ArrivalTime            DepartureTime          Duration"); 
            for(FlightScheduleEntity l:day3before) {
              System.out.println();

                System.out.println(l.getFlightScheduleId() + "                     "  +l.getArrival().toString()+ "       "+ l.getDeparture().toString() + "       " +l.getDuration());
                    
                    System.out.println();
                System.out.println("                  SinglePassenger   AllPassenger");
                                   
                System.out.println("1.FirstClass:     "  + (!(reservationSessionBean.getFare(l, CabinClassType.F)).toString().equals("-1")? reservationSessionBean.getFare(l, CabinClassType.F) + "               " + (reservationSessionBean.getFare(l, CabinClassType.F)*passengers): "Not availablr"));
                System.out.println("2.BusinessClass:  " +  (!(reservationSessionBean.getFare(l, CabinClassType.J)).toString().equals("-1")? reservationSessionBean.getFare(l, CabinClassType.J) + "               " + (reservationSessionBean.getFare(l, CabinClassType.J)*passengers): "Not availablr"));
                System.out.println("3.PremiumEconomy: " +  (!(reservationSessionBean.getFare(l, CabinClassType.W)).toString().equals("-1")? reservationSessionBean.getFare(l, CabinClassType.W) + "               " + (reservationSessionBean.getFare(l, CabinClassType.W)*passengers): "Not availablr"));
                System.out.println("4.Economy:        "  +  (!(reservationSessionBean.getFare(l, CabinClassType.Y)).toString().equals("-1")? reservationSessionBean.getFare(l, CabinClassType.Y) + "               " + (reservationSessionBean.getFare(l, CabinClassType.Y)*passengers): "Not availablr"));   
            }
            System.out.println();
                        
            System.out.println("Direct 3 Days After"); 

            List<FlightScheduleEntity> day3after = flightReservationSessionBean.searchThreeDaysAfter(false,false, departureAirport,destinationAirport, departureTime, passengers, type);
            System.out.println("FlightScheduleId       ArrivalTime            DepartureTime          Duration");
            for(FlightScheduleEntity l:day3after) {
            System.out.println();

                System.out.println(l.getFlightScheduleId() + "                     "  +l.getArrival().toString()+ "       "+ l.getDeparture().toString() + "       " +l.getDuration());
                    
                System.out.println();
                System.out.println("                  SinglePassenger   AllPassenger");
                                   
                System.out.println("1.FirstClass:     "  + (!(reservationSessionBean.getFare(l, CabinClassType.F)).toString().equals("-1")? reservationSessionBean.getFare(l, CabinClassType.F) + "               " + (reservationSessionBean.getFare(l, CabinClassType.F)*passengers): "Not availablr"));
                System.out.println("2.BusinessClass:  " +  (!(reservationSessionBean.getFare(l, CabinClassType.J)).toString().equals("-1")? reservationSessionBean.getFare(l, CabinClassType.J) + "               " + (reservationSessionBean.getFare(l, CabinClassType.J)*passengers): "Not availablr"));
                System.out.println("3.PremiumEconomy: " +  (!(reservationSessionBean.getFare(l, CabinClassType.W)).toString().equals("-1")? reservationSessionBean.getFare(l, CabinClassType.W) + "               " + (reservationSessionBean.getFare(l, CabinClassType.W)*passengers): "Not availablr"));
                System.out.println("4.Economy:        "  +  (!(reservationSessionBean.getFare(l, CabinClassType.Y)).toString().equals("-1")? reservationSessionBean.getFare(l, CabinClassType.Y) + "               " + (reservationSessionBean.getFare(l, CabinClassType.Y)*passengers): "Not availablr"));           
            }
            }
            
            if(round) {
                
                  
            List<FlightScheduleEntity> day1 = flightReservationSessionBean.searchSingleDay(false,false,destinationAirport,departureAirport, returnTime, passengers, type);
            System.out.println(day1.size());
                        
            for(FlightScheduleEntity l:day1) {
                System.out.println(l.getFlightScheduleId() + " "  +l.getArrival().toString()+ " "+ l.getDeparture().toString() + " " +l.getDuration());
            }
            
            List<FlightScheduleEntity> day3before = flightReservationSessionBean.searchThreeDaysBefore(false,false,destinationAirport,departureAirport,returnTime, passengers, type);
         System.out.println(day3before.size());

            for(FlightScheduleEntity l:day3before) {
                System.out.println(l.getFlightScheduleId() + " "  +l.getArrival().toString()+ " "+ l.getDeparture().toString() + " " +l.getDuration());
            }
                        
            List<FlightScheduleEntity> day3after = flightReservationSessionBean.searchThreeDaysAfter(false,false,destinationAirport,departureAirport, returnTime, passengers, type);

            for(FlightScheduleEntity l:day3after) {
                System.out.println(l.getFlightScheduleId() + " "  +l.getArrival().toString()+ " "+ l.getDeparture().toString() + " " +l.getDuration());
            }
            
                
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
