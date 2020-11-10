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
import entity.PassengerEntity;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import util.enumeration.FlightScheduleEntityNotFoundException;
import util.enumeration.NoFlightsFoundOnSearchException;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author leahr
 */
public class MainApp {

    ReservationSessionBeanRemote reservationSessionBean;
    FlightReservationSessionBeanRemote flightReservationSessionBean;
    CustomerSessionBeanRemote customerSessionBean;
    CustomerEntity currentCustomer;
    Boolean round;
    String departureAirport;
    String destinationAirport;
    boolean connecting;
    Integer passenger;
    CabinClassType type;

    MainApp(FlightReservationSessionBeanRemote flightReservationSessionBean, CustomerSessionBeanRemote customerSessionBean, ReservationSessionBeanRemote reservationSessionBean) {
        this.flightReservationSessionBean = flightReservationSessionBean;
        this.customerSessionBean = customerSessionBean;
        this.reservationSessionBean = reservationSessionBean;
    }

    void runApp() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
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

            while (response < 1 || response > 8) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {
                    RegisterCustomer();
                } else if (response == 2) {
                    try {
                        CustomerLogin();
                    } catch (InvalidLoginCredentialException ex) {
                        Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (response == 3) {
                  
                    try {
                        searchFlights();
                    } catch (ParseException ex) {
                        Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (NoFlightsFoundOnSearchException ex) {
                        Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                       
                } else if (response == 4) {
                    try {
                        reserveFlights();
                    } catch (FlightScheduleEntityNotFoundException ex) {
                        Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (response == 5) {
                    viewFlightReeservations();
                } else if (response == 6) {
                    viewFlightReservationDetails();
                } else if (response == 7) {
                    customerLogout();
                } else if (response == 8) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

            if (response == 6) {
                break;
            }
        }

    }

    private void viewFlightReservationDetails() {
    }

    private void customerLogout() {
    }

    private void viewFlightReeservations() {
    }

    private void reserveFlights() throws FlightScheduleEntityNotFoundException {
        Scanner sc = new Scanner(System.in);
        List<FlightReservationDetailsEntity> inbound = new ArrayList<FlightReservationDetailsEntity>();
        List<FlightReservationDetailsEntity> outbound = new ArrayList<FlightReservationDetailsEntity>();

        System.out.println("Enter Flight Schedule Id for OutBound: ");
        Long id = sc.nextLong();
        if (connecting) {
            System.out.println("Enter degree of connection");
            Long loop = sc.nextLong();
            while (loop > 0) {
                inbound.add(new FlightReservationDetailsEntity(type, reservationSessionBean.retrievebyId(id)));
            }
        }

        if (round) {
            System.out.println("Enter Flight Schedule Id for Inbound: ");
            Long id2 = sc.nextLong();
            if (connecting) {
                System.out.println("Enter degree of connection");
                Long loop = sc.nextLong();
                while (loop > 0) {
                    outbound.add(new FlightReservationDetailsEntity(type, reservationSessionBean.retrievebyId(id2)));
                }
            }
        }
        sc.nextLine();

        FlightReservationEntity book = new FlightReservationEntity();
        book.setConnecting(connecting);
        book.setTotalPassengers(passenger);
        book.setReturnFlight(round);

        List<PassengerEntity> pass = new ArrayList<PassengerEntity>();
        for (int i = 0; i < this.passenger; i++) {
            System.out.print("Enter First Name> ");
            String first = sc.nextLine().trim();
            System.out.print("Enter Last Name> ");
            String last = sc.nextLine().trim();
            System.out.print("Enter Passport> ");
            String passport = sc.nextLine().trim();

            pass.add(new PassengerEntity(first, last, passport));

        }

        reservationSessionBean.reserveFlight(book, inbound, outbound);

    }

    private void searchFlights() throws ParseException, NoFlightsFoundOnSearchException {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        Boolean round;
        String departureAirport;
        String destinationAirport;
        boolean connecting;
        int passengers = 1;

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
        switch (cabin) {
            case 1:
                type = CabinClassType.F;
                break;
            case 2:
                type = CabinClassType.J;
                break;
            case 3:
                type = CabinClassType.W;
                break;
            case 4:
                type = CabinClassType.Y;
                break;
        }

        System.out.print("Trip Type : 1.One-way 2.Round > ");

        int t = scanner.nextInt();
        if (t == 1) {
            round = false;
        } else {
            scanner.nextLine();
            round = true;
            System.out.println("Enter local depature date (yyyy-MM-dd HH:mm)>");
            String date1 = scanner.nextLine().trim();
            returnTime = LocalDateTime.parse(date1, formatter);
        }

        System.out.print("Connecting Flight : 1.Yes  2.No > ");
        int t2 = scanner.nextInt();
        connecting = (t2 == 1) ? true : false;

        this.connecting = connecting;
        this.departureAirport = departureAirport;
        this.round = round;
        this.type = type;
        this.destinationAirport = destinationAirport;

        if (round || !round) {
            System.out.println("Outbound Flights:");
            System.out.println();

            if (connecting) {
                System.out.println("Connecting on the Same Day");
                System.out.println("FlightScheduleId       ArrivalTime            DepartureTime          Duration");
                List<List<FlightScheduleEntity>> day = flightReservationSessionBean.searchConnectingThreeDaysAfter(false, false, departureAirport, destinationAirport, departureTime, 1, type);
                System.out.println(day.size());
                int i = 0;
                List<FlightScheduleEntity> list = flightReservationSessionBean.getOneWay(true, true, departureAirport, destinationAirport, departureTime, 1, type);
                for (List<FlightScheduleEntity> conn : day) {
                    if (!conn.isEmpty()) {

                        FlightScheduleEntity newf = list.get(i);
                        System.out.println(newf.getFlightScheduleId() + "                     " + newf.getArrival().toString() + "                     " + newf.getDeparture().toString() + "                     " + newf.getDuration());
                        for (FlightScheduleEntity l : conn) {
                            System.out.println(l.getFlightScheduleId() + "                     " + l.getArrival().toString() + "                     " + l.getDeparture().toString() + "                     " + l.getDuration());
                        }
                    }
                    i += 1;

               }
            } else {
                System.out.println("Direct on the Same Day");
                System.out.println("FlightScheduleId       ArrivalTime            DepartureTime          Duration");
                List<FlightScheduleEntity> day1 = flightReservationSessionBean.searchSingleDay(false, false, departureAirport, destinationAirport, departureTime, passengers, type);
                for (FlightScheduleEntity l : day1) {
                    System.out.println();

                    System.out.println(l.getFlightScheduleId() + "                     " + l.getArrival().toString() + "       " + l.getDeparture().toString() + "       " + l.getDuration());

                    System.out.println();
                    System.out.println("                  SinglePassenger   AllPassenger");

                    System.out.println("1.FirstClass:     " + (!(reservationSessionBean.getFare(l, CabinClassType.F)).toString().equals("-1") ? reservationSessionBean.getFare(l, CabinClassType.F) + "               " + (reservationSessionBean.getFare(l, CabinClassType.F) * passengers) : "Not availablr"));
                    System.out.println("2.BusinessClass:  " + (!(reservationSessionBean.getFare(l, CabinClassType.J)).toString().equals("-1") ? reservationSessionBean.getFare(l, CabinClassType.J) + "               " + (reservationSessionBean.getFare(l, CabinClassType.J) * passengers) : "Not availablr"));
                    System.out.println("3.PremiumEconomy: " + (!(reservationSessionBean.getFare(l, CabinClassType.W)).toString().equals("-1") ? reservationSessionBean.getFare(l, CabinClassType.W) + "               " + (reservationSessionBean.getFare(l, CabinClassType.W) * passengers) : "Not availablr"));
                    System.out.println("4.Economy:        " + (!(reservationSessionBean.getFare(l, CabinClassType.Y)).toString().equals("-1") ? reservationSessionBean.getFare(l, CabinClassType.Y) + "               " + (reservationSessionBean.getFare(l, CabinClassType.Y) * passengers) : "Not availablr"));
                }
            }
            
            System.out.println();
            System.out.println("Direct 1 Day Before");

            List<FlightScheduleEntity> day1before = flightReservationSessionBean.searchThreeDaysBefore(false, false, departureAirport, destinationAirport, departureTime, 1, type);
            //System.out.println(day3before.size());
            System.out.println("FlightScheduleId       ArrivalTime            DepartureTime          Duration");
            for (FlightScheduleEntity l : day1before) {
                System.out.println();

                System.out.println(l.getFlightScheduleId() + "                     " + l.getArrival().toString() + "       " + l.getDeparture().toString() + "       " + l.getDuration());

                System.out.println();
                System.out.println("                  SinglePassenger   AllPassenger");

                System.out.println("1.FirstClass:     " + (!(reservationSessionBean.getFare(l, CabinClassType.F)).toString().equals("-1") ? reservationSessionBean.getFare(l, CabinClassType.F) + "               " + (reservationSessionBean.getFare(l, CabinClassType.F) * passengers) : "Not availablr"));
                System.out.println("2.BusinessClass:  " + (!(reservationSessionBean.getFare(l, CabinClassType.J)).toString().equals("-1") ? reservationSessionBean.getFare(l, CabinClassType.J) + "               " + (reservationSessionBean.getFare(l, CabinClassType.J) * passengers) : "Not availablr"));
                System.out.println("3.PremiumEconomy: " + (!(reservationSessionBean.getFare(l, CabinClassType.W)).toString().equals("-1") ? reservationSessionBean.getFare(l, CabinClassType.W) + "               " + (reservationSessionBean.getFare(l, CabinClassType.W) * passengers) : "Not availablr"));
                System.out.println("4.Economy:        " + (!(reservationSessionBean.getFare(l, CabinClassType.Y)).toString().equals("-1") ? reservationSessionBean.getFare(l, CabinClassType.Y) + "               " + (reservationSessionBean.getFare(l, CabinClassType.Y) * passengers) : "Not availablr"));
            }
            System.out.println();
            System.out.println("Direct 2 Day Before");

            List<FlightScheduleEntity> day2before = flightReservationSessionBean.searchThreeDaysBefore(false, false, departureAirport, destinationAirport, departureTime, 2, type);
            //System.out.println(day3before.size());
            System.out.println("FlightScheduleId       ArrivalTime            DepartureTime          Duration");
            for (FlightScheduleEntity l : day2before) {
                System.out.println();

                System.out.println(l.getFlightScheduleId() + "                     " + l.getArrival().toString() + "       " + l.getDeparture().toString() + "       " + l.getDuration());

                System.out.println();
                System.out.println("                  SinglePassenger   AllPassenger");

                System.out.println("1.FirstClass:     " + (!(reservationSessionBean.getFare(l, CabinClassType.F)).toString().equals("-1") ? reservationSessionBean.getFare(l, CabinClassType.F) + "               " + (reservationSessionBean.getFare(l, CabinClassType.F) * passengers) : "Not availablr"));
                System.out.println("2.BusinessClass:  " + (!(reservationSessionBean.getFare(l, CabinClassType.J)).toString().equals("-1") ? reservationSessionBean.getFare(l, CabinClassType.J) + "               " + (reservationSessionBean.getFare(l, CabinClassType.J) * passengers) : "Not availablr"));
                System.out.println("3.PremiumEconomy: " + (!(reservationSessionBean.getFare(l, CabinClassType.W)).toString().equals("-1") ? reservationSessionBean.getFare(l, CabinClassType.W) + "               " + (reservationSessionBean.getFare(l, CabinClassType.W) * passengers) : "Not availablr"));
                System.out.println("4.Economy:        " + (!(reservationSessionBean.getFare(l, CabinClassType.Y)).toString().equals("-1") ? reservationSessionBean.getFare(l, CabinClassType.Y) + "               " + (reservationSessionBean.getFare(l, CabinClassType.Y) * passengers) : "Not availablr"));
            }
            System.out.println();
            System.out.println("Direct 3 Day Before");

            List<FlightScheduleEntity> day3before = flightReservationSessionBean.searchThreeDaysBefore(false, false, departureAirport, destinationAirport, departureTime, 3, type);
            //System.out.println(day3before.size());
            System.out.println("FlightScheduleId       ArrivalTime            DepartureTime          Duration");
            for (FlightScheduleEntity l : day3before) {
                System.out.println();
                System.out.println(l.getFlightScheduleId() + "                     " + l.getArrival().toString() + "       " + l.getDeparture().toString() + "       " + l.getDuration());

                System.out.println();
                System.out.println("                  SinglePassenger   AllPassenger");

                System.out.println("1.FirstClass:     " + (!(reservationSessionBean.getFare(l, CabinClassType.F)).toString().equals("-1") ? reservationSessionBean.getFare(l, CabinClassType.F) + "               " + (reservationSessionBean.getFare(l, CabinClassType.F) * passengers) : "Not availablr"));
                System.out.println("2.BusinessClass:  " + (!(reservationSessionBean.getFare(l, CabinClassType.J)).toString().equals("-1") ? reservationSessionBean.getFare(l, CabinClassType.J) + "               " + (reservationSessionBean.getFare(l, CabinClassType.J) * passengers) : "Not availablr"));
                System.out.println("3.PremiumEconomy: " + (!(reservationSessionBean.getFare(l, CabinClassType.W)).toString().equals("-1") ? reservationSessionBean.getFare(l, CabinClassType.W) + "               " + (reservationSessionBean.getFare(l, CabinClassType.W) * passengers) : "Not availablr"));
                System.out.println("4.Economy:        " + (!(reservationSessionBean.getFare(l, CabinClassType.Y)).toString().equals("-1") ? reservationSessionBean.getFare(l, CabinClassType.Y) + "               " + (reservationSessionBean.getFare(l, CabinClassType.Y) * passengers) : "Not availablr"));
            }
            
           
             System.out.println();

            System.out.println("Direct 1 Days After");

            List<FlightScheduleEntity> day1after = flightReservationSessionBean.searchThreeDaysAfter(false, false, departureAirport, destinationAirport, departureTime, 1, type);
            System.out.println("FlightScheduleId       ArrivalTime            DepartureTime          Duration");
           
            for (FlightScheduleEntity l : day1after) {
                System.out.println();

                System.out.println(l.getFlightScheduleId() + "                     " + l.getArrival().toString() + "       " + l.getDeparture().toString() + "       " + l.getDuration());

                System.out.println();
                System.out.println("                  SinglePassenger   AllPassenger");

                System.out.println("1.FirstClass:     " + (!(reservationSessionBean.getFare(l, CabinClassType.F)).toString().equals("-1") ? reservationSessionBean.getFare(l, CabinClassType.F) + "               " + (reservationSessionBean.getFare(l, CabinClassType.F) * passengers) : "Not availablr"));
                System.out.println("2.BusinessClass:  " + (!(reservationSessionBean.getFare(l, CabinClassType.J)).toString().equals("-1") ? reservationSessionBean.getFare(l, CabinClassType.J) + "               " + (reservationSessionBean.getFare(l, CabinClassType.J) * passengers) : "Not availablr"));
                System.out.println("3.PremiumEconomy: " + (!(reservationSessionBean.getFare(l, CabinClassType.W)).toString().equals("-1") ? reservationSessionBean.getFare(l, CabinClassType.W) + "               " + (reservationSessionBean.getFare(l, CabinClassType.W) * passengers) : "Not availablr"));
                System.out.println("4.Economy:        " + (!(reservationSessionBean.getFare(l, CabinClassType.Y)).toString().equals("-1") ? reservationSessionBean.getFare(l, CabinClassType.Y) + "               " + (reservationSessionBean.getFare(l, CabinClassType.Y) * passengers) : "Not availablr"));
            }
            System.out.println();

            System.out.println("Direct 2 Days After");

            List<FlightScheduleEntity> day2after = flightReservationSessionBean.searchThreeDaysAfter(false, false, departureAirport, destinationAirport, departureTime, 2, type);
            System.out.println("FlightScheduleId       ArrivalTime            DepartureTime          Duration");
           
            for (FlightScheduleEntity l : day2after) {
                System.out.println();

                System.out.println(l.getFlightScheduleId() + "                     " + l.getArrival().toString() + "       " + l.getDeparture().toString() + "       " + l.getDuration());

                System.out.println();
                System.out.println("                  SinglePassenger   AllPassenger");

                System.out.println("1.FirstClass:     " + (!(reservationSessionBean.getFare(l, CabinClassType.F)).toString().equals("-1") ? reservationSessionBean.getFare(l, CabinClassType.F) + "               " + (reservationSessionBean.getFare(l, CabinClassType.F) * passengers) : "Not availablr"));
                System.out.println("2.BusinessClass:  " + (!(reservationSessionBean.getFare(l, CabinClassType.J)).toString().equals("-1") ? reservationSessionBean.getFare(l, CabinClassType.J) + "               " + (reservationSessionBean.getFare(l, CabinClassType.J) * passengers) : "Not availablr"));
                System.out.println("3.PremiumEconomy: " + (!(reservationSessionBean.getFare(l, CabinClassType.W)).toString().equals("-1") ? reservationSessionBean.getFare(l, CabinClassType.W) + "               " + (reservationSessionBean.getFare(l, CabinClassType.W) * passengers) : "Not availablr"));
                System.out.println("4.Economy:        " + (!(reservationSessionBean.getFare(l, CabinClassType.Y)).toString().equals("-1") ? reservationSessionBean.getFare(l, CabinClassType.Y) + "               " + (reservationSessionBean.getFare(l, CabinClassType.Y) * passengers) : "Not availablr"));
            }
            
            
                         System.out.println();

            System.out.println("Direct 3 Days After");

            List<FlightScheduleEntity> day3after = flightReservationSessionBean.searchThreeDaysAfter(false, false, departureAirport, destinationAirport, departureTime, 3, type);
            System.out.println("FlightScheduleId       ArrivalTime            DepartureTime          Duration");
           
            for (FlightScheduleEntity l : day3after) {
                System.out.println();

                System.out.println(l.getFlightScheduleId() + "                     " + l.getArrival().toString() + "       " + l.getDeparture().toString() + "       " + l.getDuration());

                System.out.println();
                System.out.println("                  SinglePassenger   AllPassenger");

                System.out.println("1.FirstClass:     " + (!(reservationSessionBean.getFare(l, CabinClassType.F)).toString().equals("-1") ? reservationSessionBean.getFare(l, CabinClassType.F) + "               " + (reservationSessionBean.getFare(l, CabinClassType.F) * passengers) : "Not availablr"));
                System.out.println("2.BusinessClass:  " + (!(reservationSessionBean.getFare(l, CabinClassType.J)).toString().equals("-1") ? reservationSessionBean.getFare(l, CabinClassType.J) + "               " + (reservationSessionBean.getFare(l, CabinClassType.J) * passengers) : "Not availablr"));
                System.out.println("3.PremiumEconomy: " + (!(reservationSessionBean.getFare(l, CabinClassType.W)).toString().equals("-1") ? reservationSessionBean.getFare(l, CabinClassType.W) + "               " + (reservationSessionBean.getFare(l, CabinClassType.W) * passengers) : "Not availablr"));
                System.out.println("4.Economy:        " + (!(reservationSessionBean.getFare(l, CabinClassType.Y)).toString().equals("-1") ? reservationSessionBean.getFare(l, CabinClassType.Y) + "               " + (reservationSessionBean.getFare(l, CabinClassType.Y) * passengers) : "Not availablr"));
            }
        }

        if (round) {
            System.out.println();
            System.out.println("Inbound Flights:");
            
             if (connecting) {
                System.out.println("Connecting on the Same Day");
                System.out.println("FlightScheduleId       ArrivalTime            DepartureTime          Duration");
                List<List<FlightScheduleEntity>> day = flightReservationSessionBean.searchConnectingThreeDaysAfter(false, false, destinationAirport,departureAirport, departureTime, 1, type);
                System.out.println(day.size());
                int i = 0;
                List<FlightScheduleEntity> list = flightReservationSessionBean.getOneWay(true, true, destinationAirport,departureAirport,  departureTime, 1, type);
                for (List<FlightScheduleEntity> conn : day) {
                    if (!conn.isEmpty()) {

                        FlightScheduleEntity newf = list.get(i);
                        System.out.println(newf.getFlightScheduleId() + "                     " + newf.getArrival().toString() + "                     " + newf.getDeparture().toString() + "                     " + newf.getDuration());
                        for (FlightScheduleEntity l : conn) {
                            System.out.println(l.getFlightScheduleId() + "                     " + l.getArrival().toString() + "                     " + l.getDeparture().toString() + "                     " + l.getDuration());
                        }
                    }
                    i += 1;

               }
            } else {

            System.out.println();
            System.out.println("On the Day");
            //System.out.println(day3before.size());
            System.out.println("FlightScheduleId       ArrivalTime            DepartureTime          Duration");
            List<FlightScheduleEntity> day1 = flightReservationSessionBean.searchSingleDay(false, false, destinationAirport, departureAirport, returnTime, passengers, type);
            System.out.println(day1.size());

            for (FlightScheduleEntity l : day1) {
                System.out.println();

                System.out.println(l.getFlightScheduleId() + "                     " + l.getArrival().toString() + "       " + l.getDeparture().toString() + "       " + l.getDuration());

                System.out.println();
                System.out.println("                  SinglePassenger   AllPassenger");

                System.out.println("1.FirstClass:     " + (!(reservationSessionBean.getFare(l, CabinClassType.F)).toString().equals("-1") ? reservationSessionBean.getFare(l, CabinClassType.F) + "               " + (reservationSessionBean.getFare(l, CabinClassType.F) * passengers) : "Not availablr"));
                System.out.println("2.BusinessClass:  " + (!(reservationSessionBean.getFare(l, CabinClassType.J)).toString().equals("-1") ? reservationSessionBean.getFare(l, CabinClassType.J) + "               " + (reservationSessionBean.getFare(l, CabinClassType.J) * passengers) : "Not availablr"));
                System.out.println("3.PremiumEconomy: " + (!(reservationSessionBean.getFare(l, CabinClassType.W)).toString().equals("-1") ? reservationSessionBean.getFare(l, CabinClassType.W) + "               " + (reservationSessionBean.getFare(l, CabinClassType.W) * passengers) : "Not availablr"));
                System.out.println("4.Economy:        " + (!(reservationSessionBean.getFare(l, CabinClassType.Y)).toString().equals("-1") ? reservationSessionBean.getFare(l, CabinClassType.Y) + "               " + (reservationSessionBean.getFare(l, CabinClassType.Y) * passengers) : "Not availablr"));
            }

             }
           
            System.out.println();
            System.out.println("Direct 3 Day Before");
            System.out.println("FlightScheduleId       ArrivalTime            DepartureTime          Duration");

            List<FlightScheduleEntity> day3before = flightReservationSessionBean.searchThreeDaysBefore(false, false, destinationAirport, departureAirport, returnTime, passengers, type);
            System.out.println(day3before.size());

            for (FlightScheduleEntity l : day3before) {
                System.out.println();

                System.out.println(l.getFlightScheduleId() + "                     " + l.getArrival().toString() + "       " + l.getDeparture().toString() + "       " + l.getDuration());

                System.out.println();
                System.out.println("                  SinglePassenger   AllPassenger");

                System.out.println("1.FirstClass:     " + (!(reservationSessionBean.getFare(l, CabinClassType.F)).toString().equals("-1") ? reservationSessionBean.getFare(l, CabinClassType.F) + "               " + (reservationSessionBean.getFare(l, CabinClassType.F) * passengers) : "Not availablr"));
                System.out.println("2.BusinessClass:  " + (!(reservationSessionBean.getFare(l, CabinClassType.J)).toString().equals("-1") ? reservationSessionBean.getFare(l, CabinClassType.J) + "               " + (reservationSessionBean.getFare(l, CabinClassType.J) * passengers) : "Not availablr"));
                System.out.println("3.PremiumEconomy: " + (!(reservationSessionBean.getFare(l, CabinClassType.W)).toString().equals("-1") ? reservationSessionBean.getFare(l, CabinClassType.W) + "               " + (reservationSessionBean.getFare(l, CabinClassType.W) * passengers) : "Not availablr"));
                System.out.println("4.Economy:        " + (!(reservationSessionBean.getFare(l, CabinClassType.Y)).toString().equals("-1") ? reservationSessionBean.getFare(l, CabinClassType.Y) + "               " + (reservationSessionBean.getFare(l, CabinClassType.Y) * passengers) : "Not availablr"));
            }

            System.out.println("Direct 3 Day After");
            System.out.println("FlightScheduleId       ArrivalTime            DepartureTime          Duration");

            List<FlightScheduleEntity> day3after = flightReservationSessionBean.searchThreeDaysAfter(false, false, destinationAirport, departureAirport, returnTime, passengers, type);

            for (FlightScheduleEntity l : day3after) {
                System.out.println();

                System.out.println(l.getFlightScheduleId() + "                     " + l.getArrival().toString() + "       " + l.getDeparture().toString() + "       " + l.getDuration());

                System.out.println();
                System.out.println("                  SinglePassenger   AllPassenger");

                System.out.println("1.FirstClass:     " + (!(reservationSessionBean.getFare(l, CabinClassType.F)).toString().equals("-1") ? reservationSessionBean.getFare(l, CabinClassType.F) + "               " + (reservationSessionBean.getFare(l, CabinClassType.F) * passengers) : "Not availablr"));
                System.out.println("2.BusinessClass:  " + (!(reservationSessionBean.getFare(l, CabinClassType.J)).toString().equals("-1") ? reservationSessionBean.getFare(l, CabinClassType.J) + "               " + (reservationSessionBean.getFare(l, CabinClassType.J) * passengers) : "Not availablr"));
                System.out.println("3.PremiumEconomy: " + (!(reservationSessionBean.getFare(l, CabinClassType.W)).toString().equals("-1") ? reservationSessionBean.getFare(l, CabinClassType.W) + "               " + (reservationSessionBean.getFare(l, CabinClassType.W) * passengers) : "Not availablr"));
                System.out.println("4.Economy:        " + (!(reservationSessionBean.getFare(l, CabinClassType.Y)).toString().equals("-1") ? reservationSessionBean.getFare(l, CabinClassType.Y) + "               " + (reservationSessionBean.getFare(l, CabinClassType.Y) * passengers) : "Not availablr"));
            }

        }
    }

    private void RegisterCustomer() {
        
        Scanner scanner = new Scanner(System.in);


        System.out.println("*** FRS Reservation :: Create ***\n");

        System.out.print("Enter First name> ");
        String first= scanner.nextLine().trim();
                System.out.print("Enter Last Name> ");
        String last = scanner.nextLine().trim();
        System.out.print("Enter email> ");
        String email = scanner.nextLine().trim();
        System.out.print("Enter password> ");
        String password  = scanner.nextLine().trim();
        System.out.print("Enter MobileNum> ");
        Integer num= scanner.nextInt();
        
        CustomerEntity cust = new CustomerEntity(first, last, email, num, password);

        CustomerEntity custNew = customerSessionBean.createNewCustomer(cust);
        System.out.println("New Customer Id"+ custNew.getCustomerId());
        
    }

    private void CustomerLogin() throws InvalidLoginCredentialException {
        Scanner scanner = new Scanner(System.in);
        String email = "";
        String password = "";

        System.out.println("*** FRS Reservation :: Login ***\n");
        System.out.print("Enter email> ");
        email = scanner.nextLine().trim();
        System.out.print("Enter password> ");
        password = scanner.nextLine().trim();

        if (email.length() > 0 && password.length() > 0) {
            currentCustomer = customerSessionBean.login(email, password);
            this.currentCustomer=currentCustomer;
        } else {
            throw new InvalidLoginCredentialException("Missing login credential!");
        }
        
    }

}
