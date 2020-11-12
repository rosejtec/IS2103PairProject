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
import entity.CreditCardEntity;
import entity.CustomerEntity;
import entity.FlightReservationDetailsEntity;
import entity.FlightReservationEntity;
import entity.FlightScheduleEntity;
import entity.PassengerEntity;
import entity.SeatsInventoryEntity;
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
    Boolean round=false;
    String departureAirport;
    String destinationAirport;
    boolean connecting=false;
    Integer passenger=2;
    CabinClassType type=CabinClassType.F;

    MainApp(FlightReservationSessionBeanRemote flightReservationSessionBean, CustomerSessionBeanRemote customerSessionBean, ReservationSessionBeanRemote reservationSessionBean) {
        this.flightReservationSessionBean = flightReservationSessionBean;
        this.customerSessionBean = customerSessionBean;
        this.reservationSessionBean = reservationSessionBean;
    }

    void runApp() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Welcome to FRS - Reservation Client ***\n");
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
                    doRegisterCustomer();
                } else if (response == 2) {
                    try {
                        CustomerLogin();
                    } catch (InvalidLoginCredentialException ex) {
                        Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (response == 3) {

                    try {
                        doSearchFlights();
                    } catch (ParseException ex) {
                        Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (NoFlightsFoundOnSearchException ex) {
                        Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else if (response == 4) {
                    try {
                        try {
                            doReserveFlights();
                        } catch (NoFlightsFoundOnSearchException ex) {
                            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ParseException ex) {
                            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } catch (FlightScheduleEntityNotFoundException ex) {
                        Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (response == 5) {
                    doViewFlightReservations();
                } else if (response == 6) {
                    doViewFlightReservationDetails();
                } else if (response == 7) {
                    doCustomerLogout();
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

    private void doViewFlightReservationDetails() {
    }

    private void doCustomerLogout() {
    }

    private void doViewFlightReservations() {
    }

    private void doReserveFlights() throws FlightScheduleEntityNotFoundException, NoFlightsFoundOnSearchException, ParseException {
        Scanner sc = new Scanner(System.in);
        List<FlightReservationDetailsEntity> inbound = new ArrayList<FlightReservationDetailsEntity>();
        List<FlightReservationDetailsEntity> outbound = new ArrayList<FlightReservationDetailsEntity>();
        
        if(passenger==null ){
            doSearchFlights();
        }
        System.out.println("Enter Flight Schedule Id for OutBound: ");
        Long id = sc.nextLong();
        inbound.add(new FlightReservationDetailsEntity(type, reservationSessionBean.retrievebyId(id)));
        List<String> seatNum = new ArrayList<>();
        sc.nextLine();
        int amount = 0;
        SeatsInventoryEntity seat = reservationSessionBean.retrievebyId(id).getSeatsInventory();
        System.out.println(seat);

        for (int i = 0; i < this.passenger; i++) {
            int j = i+1;
            System.out.println("Seat Details for Passenger " + (j) + ": ");
            System.out.print("Enter SeatNumber> ");
            String first = sc.nextLine().trim();
            seat.getSeats().add(first);
            seatNum.add(first);
        }
        

        seat.updateAvailableSeats(passenger);
        seat.updateReservedSeats(passenger);
        inbound.get(0).setSeatNum(seatNum);
        
        reservationSessionBean.updateSeat(seat);

        seatNum.clear();
        amount += reservationSessionBean.getFare(reservationSessionBean.retrievebyId(id), type) * passenger;

        if (connecting) {
            Long loop = sc.nextLong();
            for (int i = 1; i <= loop; i++) {
                System.out.println("Enter Flight Schedule Id for OutBound Connecting: ");

                id = sc.nextLong();
                sc.nextLine();

                inbound.add(new FlightReservationDetailsEntity(type, reservationSessionBean.retrievebyId(id)));
                seat = reservationSessionBean.retrievebyId(id).getSeatsInventory();
                for (int j = 0; j < this.passenger; j++) {
                    System.out.println("Seat Details for Passenger:" + (i + 1));
                    System.out.print("Enter SeatNumber> ");
                    String first = sc.nextLine().trim();
                          seat.getSeats().add(first);

                    seatNum.add(first);

                }
                seat.updateAvailableSeats(passenger);
                seat.updateReservedSeats(passenger);
                inbound.get(i).setSeatNum(seatNum);
        reservationSessionBean.updateSeat(seat);

                seatNum.clear();
                amount += reservationSessionBean.getFare(reservationSessionBean.retrievebyId(id), type) * passenger;

            }
        }

        if (round) {
            System.out.println("Enter Flight Schedule Id for Inbound: ");
            id = sc.nextLong();
            sc.nextLine();

            outbound.add(new FlightReservationDetailsEntity(type, reservationSessionBean.retrievebyId(id)));
            seat = reservationSessionBean.retrievebyId(id).getSeatsInventory();
            for (int i = 0; i < this.passenger; i++) {
                System.out.println("Seat Details for Passenger:" + (i + 1));
                System.out.print("Enter SeatNumber> ");
                String first = sc.nextLine().trim();
                seat.getSeats().add(first);
                seatNum.add(first);

            }
            seat.updateAvailableSeats(passenger);
            seat.updateReservedSeats(passenger);
            amount += reservationSessionBean.getFare(reservationSessionBean.retrievebyId(id), type) * passenger;
        reservationSessionBean.updateSeat(seat);

            outbound.get(0).setSeatNum(seatNum);
            seatNum.clear();
            if (connecting) {
                System.out.println("Enter degree of connection");
                Long loop = sc.nextLong();

                for (int i = 1; i <= loop; i++) {
                    System.out.println("Enter Flight Schedule Id for OutBound Connecting: ");

                    id = sc.nextLong();
                    sc.nextLine();
                    seat = reservationSessionBean.retrievebyId(id).getSeatsInventory();
                    outbound.add(new FlightReservationDetailsEntity(type, reservationSessionBean.retrievebyId(id)));
                    for (int j = 0; j < this.passenger; j++) {
                        System.out.println("Seat Details for Passenger:" + (i + 1));
                        System.out.print("Enter SeatNumber> ");
                        String first = sc.nextLine().trim();
                        seat.getSeats().add(first);

                        seatNum.add(first);
                    }
                    seat.updateAvailableSeats(passenger);
                    seat.updateReservedSeats(passenger);
                    amount += reservationSessionBean.getFare(reservationSessionBean.retrievebyId(id), type) * passenger;
                  reservationSessionBean.updateSeat(seat);
 
                    outbound.get(i).setSeatNum(seatNum);
                    seatNum.clear();

                }

            }
        }

        FlightReservationEntity book = new FlightReservationEntity();
        book.setConnecting(connecting);
        book.setTotalPassengers(passenger);
        book.setReturnFlight(round);
        book.setTotalAmount(amount);
        List<PassengerEntity> pass = new ArrayList<PassengerEntity>();
        for (int i = 0; i < this.passenger; i++) {
            int j = i+1;
            System.out.println("Details for Passenger: " + j);
            System.out.print("Enter First Name> ");
            String first = sc.nextLine().trim();
            System.out.print("Enter Last Name> ");
            String last = sc.nextLine().trim();
            System.out.print("Enter Passport> ");
            String passport = sc.nextLine().trim();

            pass.add(new PassengerEntity(first, last, passport));
            System.out.println("");
        }
        
        
            
            System.out.print("Enter Credit Card Details > ");
            System.out.print("Enter Credit Card Number> ");
            String num = sc.nextLine().trim();
            System.out.print("Enter Credit Card Pin> ");
            String pin = sc.nextLine().trim();
            
            CreditCardEntity c = new CreditCardEntity(num, pin);

        reservationSessionBean.reserveFlight(book, inbound, outbound, pass,passenger,c);

        System.out.println("done");
        //create creditcard entiy and ask for input details
    }

    private void doSearchFlights() throws ParseException, NoFlightsFoundOnSearchException {
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
        this.passenger=passengers;
       
        boolean use = true;
        if (round || !round) {

            System.out.println("Outbound Flights:");
            System.out.println();

            if (connecting) {
                if (use) {
                    System.out.println("Connecting on the Same Day");
                    System.out.println("FlightScheduleId       ArrivalTime            DepartureTime          Duration");
                    int i = 0;
                    List<FlightScheduleEntity> list = flightReservationSessionBean.getOneWayAfter(true, true, departureAirport, destinationAirport, departureTime, 0, type);

//                    System.out.println(list);
                    for (FlightScheduleEntity conn : list) {

                        List<FlightScheduleEntity> list4 = flightReservationSessionBean.getDestination(connecting, round, conn.getFlightSchedulePlan().getFlight().getFlightRoute().getDestination().getCode(), destinationAirport, conn.getArrival(), 0, type);

                        for (FlightScheduleEntity l : list4) {
                            System.out.println(conn.getFlightScheduleId() + "                     " + conn.getArrival().toString() + "        " + conn.getDeparture().toString() + "           " + conn.getDuration());
                            System.out.println(l.getFlightScheduleId() + "                     " + l.getArrival().toString() + "        " + l.getDeparture().toString() + "            " + l.getDuration());
                        }

                    }

                    List<FlightScheduleEntity> day2degree = flightReservationSessionBean.getOneWayAfter(true, true, departureAirport, destinationAirport, departureTime, 0, type);
//                    System.out.println(day2degree);

                    int j = 0;
                    i = 0;
                    for (FlightScheduleEntity fs : day2degree) {

                        List<FlightScheduleEntity> list4 = flightReservationSessionBean.getOneWayAfter(connecting, round, fs.getFlightSchedulePlan().getFlight().getFlightRoute().getDestination().getCode(), fs.getFlightSchedulePlan().getFlight().getFlightRoute().getDestination().getCode(), fs.getArrival(), 0, type);
                        j = 0;
//                        System.out.println(list4);
//                        System.out.println("FS" + fs);

                        for (FlightScheduleEntity conn : list4) {

                            System.out.println("Conn" + conn);

                            List<FlightScheduleEntity> l5 = flightReservationSessionBean.getDestination(connecting, round, conn.getFlightSchedulePlan().getFlight().getFlightRoute().getDestination().getCode(), destinationAirport, conn.getArrival(), 0, type);

                            for (FlightScheduleEntity l : l5) {
                                System.out.println(fs.getFlightScheduleId() + "                     " + fs.getArrival().toString() + "        " + fs.getDeparture().toString() + "           " + fs.getDuration());
                                System.out.println(conn.getFlightScheduleId() + "                     " + conn.getArrival().toString() + "        " + conn.getDeparture().toString() + "           " + conn.getDuration());
                                System.out.println(l.getFlightScheduleId() + "                     " + l.getArrival().toString() + "        " + l.getDeparture().toString() + "            " + l.getDuration());
                            }
                        }
                    }

                }

                for (int k = 1; k <= 3; k++) {
                    System.out.println("Connecting " + k + " day after");
                    System.out.println("FlightScheduleId       ArrivalTime            DepartureTime          Duration");
                    List<FlightScheduleEntity> list = flightReservationSessionBean.getOneWayAfter(true, true, departureAirport, destinationAirport, departureTime.plusDays(k), 0, type);

//                    System.out.println(list);
                    for (FlightScheduleEntity conn : list) {

                        List<FlightScheduleEntity> list4 = flightReservationSessionBean.getDestination(connecting, round, conn.getFlightSchedulePlan().getFlight().getFlightRoute().getDestination().getCode(), destinationAirport, conn.getArrival(), 0, type);

                        for (FlightScheduleEntity l : list4) {
                            System.out.println(conn.getFlightScheduleId() + "                     " + conn.getArrival().toString() + "        " + conn.getDeparture().toString() + "           " + conn.getDuration());
                            System.out.println(l.getFlightScheduleId() + "                     " + l.getArrival().toString() + "        " + l.getDeparture().toString() + "            " + l.getDuration());
                        }

                    }

                    List<FlightScheduleEntity> day2degree = flightReservationSessionBean.getOneWayAfter(true, true, departureAirport, destinationAirport, departureTime.plusDays(k), 0, type);
//                    System.out.println(day2degree);

                    int j = 0;
                    int i = 0;
                    for (FlightScheduleEntity fs : day2degree) {

                        List<FlightScheduleEntity> list4 = flightReservationSessionBean.getOneWayAfter(connecting, round, fs.getFlightSchedulePlan().getFlight().getFlightRoute().getDestination().getCode(), fs.getFlightSchedulePlan().getFlight().getFlightRoute().getDestination().getCode(), fs.getArrival(), 0, type);
                        j = 0;
//                        System.out.println(list4);
//                        System.out.println("FS" + fs);

                        for (FlightScheduleEntity conn : list4) {

//                            System.out.println("Conn" + conn);

                            List<FlightScheduleEntity> l5 = flightReservationSessionBean.getDestination(connecting, round, conn.getFlightSchedulePlan().getFlight().getFlightRoute().getDestination().getCode(), destinationAirport, conn.getArrival(), 0, type);

                            for (FlightScheduleEntity l : l5) {
                                System.out.println(fs.getFlightScheduleId() + "                     " + fs.getArrival().toString() + "        " + fs.getDeparture().toString() + "           " + fs.getDuration());
                                System.out.println(conn.getFlightScheduleId() + "                     " + conn.getArrival().toString() + "        " + conn.getDeparture().toString() + "           " + conn.getDuration());
                                System.out.println(l.getFlightScheduleId() + "                     " + l.getArrival().toString() + "        " + l.getDeparture().toString() + "            " + l.getDuration());
                            }
                        }
                    }

                }

                for (int k = 1; k <= 3; k++) {
                    System.out.println("Connecting " + k + " day Before");
                    System.out.println("FlightScheduleId       ArrivalTime            DepartureTime          Duration");
                    List<FlightScheduleEntity> list = flightReservationSessionBean.getOneWayAfter(true, true, departureAirport, destinationAirport, departureTime.minusDays(k), 0, type);

                    System.out.println(list);
                    for (FlightScheduleEntity conn : list) {

                        List<FlightScheduleEntity> list4 = flightReservationSessionBean.getDestination(connecting, round, conn.getFlightSchedulePlan().getFlight().getFlightRoute().getDestination().getCode(), destinationAirport, conn.getArrival(), 0, type);

                        for (FlightScheduleEntity l : list4) {
                            System.out.println(conn.getFlightScheduleId() + "                     " + conn.getArrival().toString() + "        " + conn.getDeparture().toString() + "           " + conn.getDuration());
                            System.out.println(l.getFlightScheduleId() + "                     " + l.getArrival().toString() + "        " + l.getDeparture().toString() + "            " + l.getDuration());
                        }

                    }

                    List<FlightScheduleEntity> day2degree = flightReservationSessionBean.getOneWayAfter(true, true, departureAirport, destinationAirport, departureTime.minusDays(k), 0, type);
//                    System.out.println(day2degree);

                    int j = 0;
                    int i = 0;
                    for (FlightScheduleEntity fs : day2degree) {

                        List<FlightScheduleEntity> list4 = flightReservationSessionBean.getOneWayAfter(connecting, round, fs.getFlightSchedulePlan().getFlight().getFlightRoute().getDestination().getCode(), fs.getFlightSchedulePlan().getFlight().getFlightRoute().getDestination().getCode(), fs.getArrival(), 0, type);
                        j = 0;
//                        System.out.println(list4);
//                        System.out.println("FS" + fs);

                        for (FlightScheduleEntity conn : list4) {

//                            System.out.println("Conn" + conn);

                            List<FlightScheduleEntity> l5 = flightReservationSessionBean.getDestination(connecting, round, conn.getFlightSchedulePlan().getFlight().getFlightRoute().getDestination().getCode(), destinationAirport, conn.getArrival(), 0, type);

                            for (FlightScheduleEntity l : l5) {
                                System.out.println(fs.getFlightScheduleId() + "                     " + fs.getArrival().toString() + "        " + fs.getDeparture().toString() + "           " + fs.getDuration());
                                System.out.println(conn.getFlightScheduleId() + "                     " + conn.getArrival().toString() + "        " + conn.getDeparture().toString() + "           " + conn.getDuration());
                                System.out.println(l.getFlightScheduleId() + "                     " + l.getArrival().toString() + "        " + l.getDeparture().toString() + "            " + l.getDuration());
                            }
                        }
                    }

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

                for (int i = 1; i <= 3; i++) {
                    System.out.println("Direct " + i + " Day Before ");
                    System.out.println("FlightScheduleId       ArrivalTime            DepartureTime          Duration");
                    List<FlightScheduleEntity> day2 = flightReservationSessionBean.searchThreeDaysBefore(false, false, departureAirport, destinationAirport, departureTime, i, type);
                    for (FlightScheduleEntity l : day2) {
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

                for (int i = 1; i <= 3; i++) {
                    System.out.println("Direct " + i + " Day After ");
                    System.out.println("FlightScheduleId       ArrivalTime            DepartureTime          Duration");
                    List<FlightScheduleEntity> day2 = flightReservationSessionBean.searchThreeDaysAfter(false, false, departureAirport, destinationAirport, departureTime, i, type);
                    for (FlightScheduleEntity l : day2) {
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
        }

        if (round) {

            if (!connecting) {
                System.out.println();
                System.out.println("Inbound Flights:");
                System.out.println("Direct on the Same Day");
                System.out.println("FlightScheduleId       ArrivalTime            DepartureTime          Duration");
                List<FlightScheduleEntity> day1 = flightReservationSessionBean.searchSingleDay(false, false, destinationAirport, departureAirport, departureTime, passengers, type);
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

                for (int i = 1; i <= 3; i++) {

                    System.out.println("Direct " + i + " Day Before ");
                    System.out.println("FlightScheduleId       ArrivalTime            DepartureTime          Duration");
                    List<FlightScheduleEntity> day2 = flightReservationSessionBean.searchThreeDaysBefore(false, false, destinationAirport, departureAirport, departureTime, i, type);
                    for (FlightScheduleEntity l : day2) {
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

                for (int i = 1; i <= 3; i++) {
                    System.out.println("Direct " + i + " Day After ");
                    System.out.println("FlightScheduleId       ArrivalTime            DepartureTime          Duration");
                    List<FlightScheduleEntity> day2 = flightReservationSessionBean.searchThreeDaysAfter(false, false, destinationAirport, departureAirport, departureTime, i, type);
                    for (FlightScheduleEntity l : day2) {
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

            } else {
               if (connecting) {
                if (use) {
                    
           LocalDateTime temp = departureTime;
           departureTime=returnTime;
           returnTime=temp;       
                
                    System.out.println("Connecting on the Same Day");
                    System.out.println("FlightScheduleId       ArrivalTime            DepartureTime          Duration");
                    int i = 0;
                    List<FlightScheduleEntity> list = flightReservationSessionBean.getOneWayAfter(true, true, departureAirport, destinationAirport, departureTime, 0, type);

                    System.out.println(list);
                    for (FlightScheduleEntity conn : list) {

                        List<FlightScheduleEntity> list4 = flightReservationSessionBean.getDestination(connecting, round, conn.getFlightSchedulePlan().getFlight().getFlightRoute().getDestination().getCode(), destinationAirport, conn.getArrival(), 0, type);

                        for (FlightScheduleEntity l : list4) {
                            System.out.println(conn.getFlightScheduleId() + "                     " + conn.getArrival().toString() + "        " + conn.getDeparture().toString() + "           " + conn.getDuration());
                            System.out.println(l.getFlightScheduleId() + "                     " + l.getArrival().toString() + "        " + l.getDeparture().toString() + "            " + l.getDuration());
                        }

                    }

                    List<FlightScheduleEntity> day2degree = flightReservationSessionBean.getOneWayAfter(true, true, departureAirport, destinationAirport, departureTime, 0, type);
//                    System.out.println(day2degree);

                    int j = 0;
                    i = 0;
                    for (FlightScheduleEntity fs : day2degree) {

                        List<FlightScheduleEntity> list4 = flightReservationSessionBean.getOneWayAfter(connecting, round, fs.getFlightSchedulePlan().getFlight().getFlightRoute().getDestination().getCode(), fs.getFlightSchedulePlan().getFlight().getFlightRoute().getDestination().getCode(), fs.getArrival(), 0, type);
                        j = 0;
//                        System.out.println(list4);
//                        System.out.println("FS" + fs);

                        for (FlightScheduleEntity conn : list4) {

//                            System.out.println("Conn" + conn);

                            List<FlightScheduleEntity> l5 = flightReservationSessionBean.getDestination(connecting, round, conn.getFlightSchedulePlan().getFlight().getFlightRoute().getDestination().getCode(), destinationAirport, conn.getArrival(), 0, type);

                            for (FlightScheduleEntity l : l5) {
                                System.out.println(fs.getFlightScheduleId() + "                     " + fs.getArrival().toString() + "        " + fs.getDeparture().toString() + "           " + fs.getDuration());
                                System.out.println(conn.getFlightScheduleId() + "                     " + conn.getArrival().toString() + "        " + conn.getDeparture().toString() + "           " + conn.getDuration());
                                System.out.println(l.getFlightScheduleId() + "                     " + l.getArrival().toString() + "        " + l.getDeparture().toString() + "            " + l.getDuration());
                            }
                        }
                    }

                }

                for (int k = 1; k <= 3; k++) {
                    System.out.println("Connecting " + k + " day after");
                    System.out.println("FlightScheduleId       ArrivalTime            DepartureTime          Duration");
                    List<FlightScheduleEntity> list = flightReservationSessionBean.getOneWayAfter(true, true, departureAirport, destinationAirport, departureTime.plusDays(k), 0, type);

//                    System.out.println(list);
                    for (FlightScheduleEntity conn : list) {

                        List<FlightScheduleEntity> list4 = flightReservationSessionBean.getDestination(connecting, round, conn.getFlightSchedulePlan().getFlight().getFlightRoute().getDestination().getCode(), destinationAirport, conn.getArrival(), 0, type);

                        for (FlightScheduleEntity l : list4) {
                            System.out.println(conn.getFlightScheduleId() + "                     " + conn.getArrival().toString() + "        " + conn.getDeparture().toString() + "           " + conn.getDuration());
                            System.out.println(l.getFlightScheduleId() + "                     " + l.getArrival().toString() + "        " + l.getDeparture().toString() + "            " + l.getDuration());
                        }

                    }

                    List<FlightScheduleEntity> day2degree = flightReservationSessionBean.getOneWayAfter(true, true, departureAirport, destinationAirport, departureTime.plusDays(k), 0, type);
//                    System.out.println(day2degree);

                    int j = 0;
                    int i = 0;
                    for (FlightScheduleEntity fs : day2degree) {

                        List<FlightScheduleEntity> list4 = flightReservationSessionBean.getOneWayAfter(connecting, round, fs.getFlightSchedulePlan().getFlight().getFlightRoute().getDestination().getCode(), fs.getFlightSchedulePlan().getFlight().getFlightRoute().getDestination().getCode(), fs.getArrival(), 0, type);
                        j = 0;
//                        System.out.println(list4);
//                        System.out.println("FS" + fs);

                        for (FlightScheduleEntity conn : list4) {

//                            System.out.println("Conn" + conn);

                            List<FlightScheduleEntity> l5 = flightReservationSessionBean.getDestination(connecting, round, conn.getFlightSchedulePlan().getFlight().getFlightRoute().getDestination().getCode(), destinationAirport, conn.getArrival(), 0, type);

                            for (FlightScheduleEntity l : l5) {
                                System.out.println(fs.getFlightScheduleId() + "                     " + fs.getArrival().toString() + "        " + fs.getDeparture().toString() + "           " + fs.getDuration());
                                System.out.println(conn.getFlightScheduleId() + "                     " + conn.getArrival().toString() + "        " + conn.getDeparture().toString() + "           " + conn.getDuration());
                                System.out.println(l.getFlightScheduleId() + "                     " + l.getArrival().toString() + "        " + l.getDeparture().toString() + "            " + l.getDuration());
                            }
                        }
                    }

                }

                for (int k = 1; k <= 3; k++) {
                    System.out.println("Connecting " + k + " day Before");
                    System.out.println("FlightScheduleId       ArrivalTime            DepartureTime          Duration");
                    List<FlightScheduleEntity> list = flightReservationSessionBean.getOneWayAfter(true, true, departureAirport, destinationAirport, departureTime.minusDays(k), 0, type);

//                    System.out.println(list);
                    for (FlightScheduleEntity conn : list) {

                        List<FlightScheduleEntity> list4 = flightReservationSessionBean.getDestination(connecting, round, conn.getFlightSchedulePlan().getFlight().getFlightRoute().getDestination().getCode(), destinationAirport, conn.getArrival(), 0, type);

                        for (FlightScheduleEntity l : list4) {
                            System.out.println(conn.getFlightScheduleId() + "                     " + conn.getArrival().toString() + "        " + conn.getDeparture().toString() + "           " + conn.getDuration());
                            System.out.println(l.getFlightScheduleId() + "                     " + l.getArrival().toString() + "        " + l.getDeparture().toString() + "            " + l.getDuration());
                        }

                    }

                    List<FlightScheduleEntity> day2degree = flightReservationSessionBean.getOneWayAfter(true, true, departureAirport, destinationAirport, departureTime.minusDays(k), 0, type);
//                    System.out.println(day2degree);

                    int j = 0;
                    int i = 0;
                    for (FlightScheduleEntity fs : day2degree) {

                        List<FlightScheduleEntity> list4 = flightReservationSessionBean.getOneWayAfter(connecting, round, fs.getFlightSchedulePlan().getFlight().getFlightRoute().getDestination().getCode(), fs.getFlightSchedulePlan().getFlight().getFlightRoute().getDestination().getCode(), fs.getArrival(), 0, type);
                        j = 0;
//                        System.out.println(list4);
//                        System.out.println("FS" + fs);

                        for (FlightScheduleEntity conn : list4) {

//                            System.out.println("Conn" + conn);

                            List<FlightScheduleEntity> l5 = flightReservationSessionBean.getDestination(connecting, round, conn.getFlightSchedulePlan().getFlight().getFlightRoute().getDestination().getCode(), destinationAirport, conn.getArrival(), 0, type);

                            for (FlightScheduleEntity l : l5) {
                                System.out.println(fs.getFlightScheduleId() + "                     " + fs.getArrival().toString() + "        " + fs.getDeparture().toString() + "           " + fs.getDuration());
                                System.out.println(conn.getFlightScheduleId() + "                     " + conn.getArrival().toString() + "        " + conn.getDeparture().toString() + "           " + conn.getDuration());
                                System.out.println(l.getFlightScheduleId() + "                     " + l.getArrival().toString() + "        " + l.getDeparture().toString() + "            " + l.getDuration());
                            }
                        }
                    }

                }
            }
        }
        }
    }

    private void doRegisterCustomer() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("*** FRS Reservation :: Create ***\n");

        System.out.print("Enter First name> ");
        String first = scanner.nextLine().trim();
        System.out.print("Enter Last Name> ");
        String last = scanner.nextLine().trim();
        System.out.print("Enter email> ");
        String email = scanner.nextLine().trim();
        System.out.print("Enter password> ");
        String password = scanner.nextLine().trim();
        System.out.print("Enter MobileNum> ");
        Integer num = scanner.nextInt();

        CustomerEntity cust = new CustomerEntity(first, last, email, num, password);

        CustomerEntity custNew = customerSessionBean.createNewCustomer(cust);
        System.out.println("New Customer Id" + custNew.getCustomerId());

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
            this.currentCustomer = currentCustomer;
        } else {
            throw new InvalidLoginCredentialException("Missing login credential!");
        }

    }

}
