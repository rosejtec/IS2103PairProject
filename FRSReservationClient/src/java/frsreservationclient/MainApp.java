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
import entity.CabinClassConfigurationEntity;
import entity.CreditCardEntity;
import entity.CustomerEntity;
import entity.FlightEntity;
import entity.FlightReservationDetailsEntity;
import entity.FlightReservationEntity;
import entity.FlightScheduleEntity;
import entity.PassengerEntity;
import entity.SeatPassengeEntity;
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
import util.exception.AirportNotFoundException;
import util.exception.FlightReservationNotFoundException;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author leahr
 */
public class MainApp {

    ReservationSessionBeanRemote reservationSessionBeanRemote;
    FlightReservationSessionBeanRemote flightReservationSessionBeanRemote;
    CustomerSessionBeanRemote customerSessionBeanRemote;
    CustomerEntity currentCustomer;
    Boolean round = false;
    String departureAirport;
    String destinationAirport;
    boolean connecting = false;
    Integer passenger = 2;
    CabinClassType type = CabinClassType.F;

    public MainApp(FlightReservationSessionBeanRemote flightReservationSessionBean, CustomerSessionBeanRemote customerSessionBean, ReservationSessionBeanRemote reservationSessionBean) {
        this.flightReservationSessionBeanRemote = flightReservationSessionBean;
        this.customerSessionBeanRemote = customerSessionBean;
        this.reservationSessionBeanRemote = reservationSessionBean;
    }

    public void runApp() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Welcome to FRS - Reservation Client ***\n");
            System.out.println("1: Register As Customer");
            System.out.println("2: Customer Login");
            System.out.println("3: Search Flight");
            System.out.println("4: Exit\n");
            response = 0;

            while (response < 1 || response > 4) {
                System.out.print("> ");
                response = scanner.nextInt();

                if (response == 1) {
                    doRegisterCustomer();
                } else if (response == 2) {
                    try {
                        doCustomerLogin();
                        menuMain();
                    } catch (InvalidLoginCredentialException ex) {
                        System.out.println("Invalid login credential: " + ex.getMessage() + "\n");
                    }
                } else if (response == 3) {
                    try {
                        doSearchFlights();
                    } catch (NoFlightsFoundOnSearchException ex) {
                        System.out.println("No flights search available!\n");
                    } catch (AirportNotFoundException ex) {
                        System.out.println("Airport does not exist!");
                    }
                } else if (response == 4) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

            if (response == 4) {
                break;
            }
        }
    }

    private void menuMain() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Welcome to FRS - Reservation Client ***\n");
            System.out.println("You are login as " + currentCustomer.getFirstName() + " " + currentCustomer.getLastName() + "\n");
            System.out.println("1: Reserve Flight");
            System.out.println("2: View My Flight Reservations");
            System.out.println("3: View My Flight Reservation Details");
            System.out.println("4: Customer Logout\n");
            response = 0;

            while (response < 1 || response > 4) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {
                    try {
                        try {
                            doReserveFlight();
                        } catch (AirportNotFoundException ex) {
                            System.out.println("Airport does not exist!");
                        }
                    } catch (FlightScheduleEntityNotFoundException ex) {
                        System.out.println("Flight schedule does not exist!");
                    } catch (NoFlightsFoundOnSearchException ex) {
                        System.out.println("No flights search available!");
                    }
                } else if (response == 2) {
                    doViewFlightReservations();
                } else if (response == 3) {
                    try {
                        doViewFlightReservationDetails();
                    } catch (FlightReservationNotFoundException ex) {
                        System.out.println("Flight Reservation does not exist!");
                    }
                } else if (response == 4) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

            if (response == 4) {
                break;
            }
        }
    }

    private void doViewFlightReservations() {
        List<FlightReservationEntity> frList = currentCustomer.getFlightReservations();
        for (FlightReservationEntity fr : frList) {
            System.out.println("Flight Reservation ID " + fr.getFlightReservationId() + "; Total Passengers:" + fr.getTotalPassengers() + "; Total Amount: " + fr.getTotalAmount());

        }
    }

    private void doViewFlightReservationDetails() throws FlightReservationNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Flight Reservation ID> ");
        Long frId = scanner.nextLong();
        scanner.nextLine();
        FlightReservationEntity fr = customerSessionBeanRemote.retrieveFlightReservationById(frId);

        System.out.println("Flight Reservation ID " + frId + " details>");

        List<FlightReservationDetailsEntity> inBoundList = fr.getInBound();
        System.out.println("Inbound> ");
        for (FlightReservationDetailsEntity inBound : inBoundList) {
            System.out.println("Departure: " + inBound.getFlightSchedule().getDeparture() + "; Arrival: " + inBound.getFlightSchedule().getArrival());
            List<SeatPassengeEntity> seatNum = inBound.getSeats();
            System.out.print("Seat Numbers: ");
            for (SeatPassengeEntity seat : seatNum) {
                System.out.print(seat + " ");
            }
            System.out.println();
        }

        List<FlightReservationDetailsEntity> outBoundList = fr.getOutBound();
        System.out.println("Outbound> ");
        for (FlightReservationDetailsEntity outbound : outBoundList) {
            System.out.println("Departure: " + outbound.getFlightSchedule().getDeparture() + "; Arrival: " + outbound.getFlightSchedule().getArrival());
            List<SeatPassengeEntity> seatNum = outbound.getSeats();
            System.out.print("Seat Numbers: ");
            for (SeatPassengeEntity seat : seatNum) {
                System.out.print(seat + " ");
            }
            System.out.println();
        }

        System.out.println("Total amount paid: " + fr.getTotalAmount());

    }

    private void doReserveFlight() throws FlightScheduleEntityNotFoundException, NoFlightsFoundOnSearchException, AirportNotFoundException {
        Scanner sc = new Scanner(System.in);

        System.out.print("Trip Type : 1.One-way 2.Round > ");

        int t = sc.nextInt();
        if (t == 1) {
            round = false;
        } else {

            round = true;
        }

        System.out.print("Connecting Flight : 1.Yes  2.No > ");
        int t2 = sc.nextInt();
        connecting = (t2 == 1) ? true : false;

        List<FlightReservationDetailsEntity> inbound = new ArrayList<FlightReservationDetailsEntity>();
        List<FlightReservationDetailsEntity> outbound = new ArrayList<FlightReservationDetailsEntity>();
       sc.nextLine();
//        if (passenger == null) {
//            try {
//                doSearchFlights();
//            } catch (AirportNotFoundException ex) {
//                Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }

        int amount = 0;
        int passenger = 4;
                this.passenger=passenger;
        ///individually perist passenger and return
        List<PassengerEntity> pass = new ArrayList<PassengerEntity>();
        for (int i = 0; i < this.passenger; i++) {
            int j = i + 1;
            System.out.println("Details for Passenger: " + j);
            System.out.print("Enter First Name> ");
            String first = sc.nextLine().trim();
            System.out.print("Enter Last Name> ");
            String last = sc.nextLine().trim();
            System.out.print("Enter Passport> ");
            String passport = sc.nextLine().trim();
            PassengerEntity p = new PassengerEntity(first, last, passport);
            p = reservationSessionBeanRemote.persistPassenger(p);
            pass.add(p);
            System.out.print("Enter cabin class type 1.FirstClass 2.BusinessClass 3.PremiumEconomy 4.Economy 5.NoPreference ");
            CabinClassType type = null;
            int cabin = sc.nextInt();
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
                case 5:
                    type = CabinClassType.Y;
                    break;

            }

            int loop = 1;
            if (connecting) {
                System.out.println("Enter degree of connection");
                loop = sc.nextInt();
            }
            for (int k = 0; k < loop; k++) {
                System.out.println("Enter Flight Schedule ID for Outbound: ");
                Long id = sc.nextLong();
                FlightScheduleEntity ret = reservationSessionBeanRemote.retrievebyId(id);
                sc.nextLine();
                SeatsInventoryEntity seat = reservationSessionBeanRemote.retrievebyId(id).getSeatsInventory();
                System.out.println("Seat Details for Passenger " + p.getFirstName() + ": ");
                System.out.print("Enter Seat Number> ");
                String pseat = sc.nextLine().trim();
                reservationSessionBeanRemote.updateSeat(seat, type, 1);
                SeatPassengeEntity sp = new SeatPassengeEntity(p, pseat, reservationSessionBeanRemote.getFare(ret, type));
                FlightReservationDetailsEntity frd = new FlightReservationDetailsEntity(ret);
                amount += reservationSessionBeanRemote.getFare(ret, type);
                frd.setFlightSchedule(ret);
                frd = reservationSessionBeanRemote.reserveFlightReservation(frd, sp);
                inbound.add(frd);

            }

            if (round) {
                loop = 1;
                if (connecting) {
                    System.out.println("Enter degree of connection");
                    loop = sc.nextInt();
                }
                for (int k = 0; k < loop; k++) {
                    System.out.println("Enter Flight Schedule ID for Outbound: ");
                    Long id = sc.nextLong();
                    FlightScheduleEntity ret = reservationSessionBeanRemote.retrievebyId(id);
                    sc.nextLine();
                    SeatsInventoryEntity seat = reservationSessionBeanRemote.retrievebyId(id).getSeatsInventory();
                    System.out.println("Seat Details for Passenger " + (j) + ": ");
                    System.out.print("Enter Seat Number> ");
                    String pseat = sc.nextLine().trim();
                    reservationSessionBeanRemote.updateSeat(seat, type, 1);
                    SeatPassengeEntity sp = new SeatPassengeEntity(p, pseat, reservationSessionBeanRemote.getFare(ret, type));
                    FlightReservationDetailsEntity frd = new FlightReservationDetailsEntity(ret);
                    frd.setFlightSchedule(ret);
                    amount += reservationSessionBeanRemote.getFare(ret, type);
                    frd = reservationSessionBeanRemote.reserveFlightReservation(frd, sp);

                    outbound.add(frd);
                }
            }

        }

        FlightReservationEntity book = new FlightReservationEntity();
        book.setConnecting(connecting);
        book.setTotalPassengers(passenger);
        book.setReturnFlight(round);
        book.setTotalAmount(amount);

        System.out.print("Enter Credit Card Details > ");
        System.out.print("Enter Credit Card Number> ");
        String num = sc.nextLine().trim();
        System.out.print("Enter Credit Card Pin> ");
        String pin = sc.nextLine().trim();
        CreditCardEntity c = new CreditCardEntity(num, pin);

        book.setInBound(inbound);
        book.setOutBound(outbound);
        book.setCard(c);
        book.setCustomer(currentCustomer);
        reservationSessionBeanRemote.reserveFlight(book, inbound, outbound, pass, passenger, c, currentCustomer);

        System.out.println("Flight Reservation has been successful!");
        //create creditcard entiy and ask for input details
    }

    private void doSearchFlights() throws NoFlightsFoundOnSearchException, AirportNotFoundException {
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
        System.out.print("Enter cabin class type 1.FirstClass 2.BusinessClass 3.PremiumEconomy 4.Economy 5.NoPreference ");
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
            case 5:
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

        System.out.print("Connecting Flight : 1.Yes  2.No 3.NoPreference > ");
        int t2 = scanner.nextInt();
        connecting = (t2 == 1) ? true : false;
        boolean both = false;
        if (t2 == 3) {
            both = true;
        }
        this.connecting = connecting;
        this.departureAirport = departureAirport;
        this.round = round;
        this.type = type;
        this.destinationAirport = destinationAirport;
        this.passenger = passengers;

        boolean use = true;
        if (round || !round) {

            System.out.println("Outbound Flights:");
            System.out.println();

            if (connecting || both) {
                if (use) {

                    int i = 0;
                    List<FlightScheduleEntity> list = flightReservationSessionBeanRemote.getOneWayAfter(true, true, departureAirport, destinationAirport, departureTime, 0, type);

//                    System.out.println(list);
                    for (FlightScheduleEntity conn : list) {

                        List<FlightScheduleEntity> list4 = flightReservationSessionBeanRemote.getDestination(connecting, round, conn.getFlightSchedulePlan().getFlight().getFlightRoute().getDestination().getCode(), destinationAirport, conn.getArrival(), 0, type);

                        for (FlightScheduleEntity l : list4) {
                            System.out.println("Connecting on the Same Day");
                            System.out.println("FlightScheduleId       ArrivalTime            DepartureTime          Duration");
                            System.out.println(conn.getFlightScheduleId() + "                     " + conn.getArrival().toString() + "        " + conn.getDeparture().toString() + "           " + conn.getDuration());
                            System.out.println(l.getFlightScheduleId() + "                     " + l.getArrival().toString() + "        " + l.getDeparture().toString() + "            " + l.getDuration());
                        }

                    }

                    List<FlightScheduleEntity> day2degree = flightReservationSessionBeanRemote.getOneWayAfter(true, true, departureAirport, destinationAirport, departureTime, 0, type);
//                    System.out.println(day2degree);

                    int j = 0;
                    i = 0;
                    for (FlightScheduleEntity fs : day2degree) {

                        List<FlightScheduleEntity> list4 = flightReservationSessionBeanRemote.getOneWayAfter(connecting, round, fs.getFlightSchedulePlan().getFlight().getFlightRoute().getDestination().getCode(), fs.getFlightSchedulePlan().getFlight().getFlightRoute().getDestination().getCode(), fs.getArrival(), 0, type);
                        j = 0;
//                        System.out.println(list4);
//                        System.out.println("FS" + fs);

                        for (FlightScheduleEntity conn : list4) {

                            System.out.println("Conn" + conn);

                            List<FlightScheduleEntity> l5 = flightReservationSessionBeanRemote.getDestination(connecting, round, conn.getFlightSchedulePlan().getFlight().getFlightRoute().getDestination().getCode(), destinationAirport, conn.getArrival(), 0, type);

                            for (FlightScheduleEntity l : l5) {
                                System.out.println("Connecting on the Same Day");
                                System.out.println("FlightScheduleId       ArrivalTime            DepartureTime          Duration");
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
                    List<FlightScheduleEntity> list = flightReservationSessionBeanRemote.getOneWayAfter(true, true, departureAirport, destinationAirport, departureTime.plusDays(k), 0, type);

//                    System.out.println(list);
                    for (FlightScheduleEntity conn : list) {

                        List<FlightScheduleEntity> list4 = flightReservationSessionBeanRemote.getDestination(connecting, round, conn.getFlightSchedulePlan().getFlight().getFlightRoute().getDestination().getCode(), destinationAirport, conn.getArrival(), 0, type);

                        for (FlightScheduleEntity l : list4) {
                            System.out.println("Connecting on the Same Day");
                            System.out.println("FlightScheduleId       ArrivalTime            DepartureTime          Duration");
                            System.out.println(conn.getFlightScheduleId() + "                     " + conn.getArrival().toString() + "        " + conn.getDeparture().toString() + "           " + conn.getDuration());
                            System.out.println(l.getFlightScheduleId() + "                     " + l.getArrival().toString() + "        " + l.getDeparture().toString() + "            " + l.getDuration());
                        }

                    }

                    List<FlightScheduleEntity> day2degree = flightReservationSessionBeanRemote.getOneWayAfter(true, true, departureAirport, destinationAirport, departureTime.plusDays(k), 0, type);
//                    System.out.println(day2degree);

                    int j = 0;
                    int i = 0;
                    for (FlightScheduleEntity fs : day2degree) {

                        List<FlightScheduleEntity> list4 = flightReservationSessionBeanRemote.getOneWayAfter(connecting, round, fs.getFlightSchedulePlan().getFlight().getFlightRoute().getDestination().getCode(), fs.getFlightSchedulePlan().getFlight().getFlightRoute().getDestination().getCode(), fs.getArrival(), 0, type);
                        j = 0;
                        for (FlightScheduleEntity conn : list4) {

                            List<FlightScheduleEntity> l5 = flightReservationSessionBeanRemote.getDestination(connecting, round, conn.getFlightSchedulePlan().getFlight().getFlightRoute().getDestination().getCode(), destinationAirport, conn.getArrival(), 0, type);

                            for (FlightScheduleEntity l : l5) {
                                System.out.println("Connecting on the Same Day");
                                System.out.println("FlightScheduleId       ArrivalTime            DepartureTime          Duration");
                                System.out.println(fs.getFlightScheduleId() + "                     " + fs.getArrival().toString() + "        " + fs.getDeparture().toString() + "           " + fs.getDuration());
                                System.out.println(conn.getFlightScheduleId() + "                     " + conn.getArrival().toString() + "        " + conn.getDeparture().toString() + "           " + conn.getDuration());
                                System.out.println(l.getFlightScheduleId() + "                     " + l.getArrival().toString() + "        " + l.getDeparture().toString() + "            " + l.getDuration());
                            }
                        }
                    }

                }

                for (int k = 1; k <= 3; k++) {

                    List<FlightScheduleEntity> list = flightReservationSessionBeanRemote.getOneWayAfter(true, true, departureAirport, destinationAirport, departureTime.minusDays(k), 0, type);

                    System.out.println(list);
                    for (FlightScheduleEntity conn : list) {

                        List<FlightScheduleEntity> list4 = flightReservationSessionBeanRemote.getDestination(connecting, round, conn.getFlightSchedulePlan().getFlight().getFlightRoute().getDestination().getCode(), destinationAirport, conn.getArrival(), 0, type);

                        for (FlightScheduleEntity l : list4) {
                            System.out.println("Connecting " + k + " day Before");
                            System.out.println("FlightScheduleId       ArrivalTime            DepartureTime          Duration");
                            System.out.println(conn.getFlightScheduleId() + "                     " + conn.getArrival().toString() + "        " + conn.getDeparture().toString() + "           " + conn.getDuration());
                            System.out.println(l.getFlightScheduleId() + "                     " + l.getArrival().toString() + "        " + l.getDeparture().toString() + "            " + l.getDuration());
                        }

                    }

                    List<FlightScheduleEntity> day2degree = flightReservationSessionBeanRemote.getOneWayAfter(true, true, departureAirport, destinationAirport, departureTime.minusDays(k), 0, type);
//                    System.out.println(day2degree);

                    int j = 0;
                    int i = 0;
                    for (FlightScheduleEntity fs : day2degree) {

                        List<FlightScheduleEntity> list4 = flightReservationSessionBeanRemote.getOneWayAfter(connecting, round, fs.getFlightSchedulePlan().getFlight().getFlightRoute().getDestination().getCode(), fs.getFlightSchedulePlan().getFlight().getFlightRoute().getDestination().getCode(), fs.getArrival(), 0, type);
                        j = 0;
//                        System.out.println(list4);
//                        System.out.println("FS" + fs);

                        for (FlightScheduleEntity conn : list4) {

//                            System.out.println("Conn" + conn);
                            List<FlightScheduleEntity> l5 = flightReservationSessionBeanRemote.getDestination(connecting, round, conn.getFlightSchedulePlan().getFlight().getFlightRoute().getDestination().getCode(), destinationAirport, conn.getArrival(), 0, type);

                            for (FlightScheduleEntity l : l5) {
                                System.out.println("Connecting " + k + " day Before");
                                System.out.println("FlightScheduleId       ArrivalTime            DepartureTime          Duration");
                                System.out.println(fs.getFlightScheduleId() + "                     " + fs.getArrival().toString() + "        " + fs.getDeparture().toString() + "           " + fs.getDuration());
                                System.out.println(conn.getFlightScheduleId() + "                     " + conn.getArrival().toString() + "        " + conn.getDeparture().toString() + "           " + conn.getDuration());
                                System.out.println(l.getFlightScheduleId() + "                     " + l.getArrival().toString() + "        " + l.getDeparture().toString() + "            " + l.getDuration());
                            }
                        }
                    }

                }
            }

            if (!connecting || both) {

                List<FlightScheduleEntity> day1 = flightReservationSessionBeanRemote.searchSingleDay(false, false, departureAirport, destinationAirport, departureTime, passengers, type);
                for (FlightScheduleEntity l : day1) {
                    System.out.println("Direct on the Same Day");
                    System.out.println("FlightScheduleId       ArrivalTime            DepartureTime          Duration");
                    System.out.println();

                    System.out.println(l.getFlightScheduleId() + "                     " + l.getArrival().toString() + "       " + l.getDeparture().toString() + "       " + l.getDuration());

                    System.out.println();
                    System.out.println("                  SinglePassenger   AllPassenger  TotalFlightAvailibility       CabinClassAvailibility");

                    System.out.println("1.FirstClass:     " + (!(reservationSessionBeanRemote.getFare(l, CabinClassType.F)).toString().equals("-1") ? reservationSessionBeanRemote.getFare(l, CabinClassType.F) + "               " + (reservationSessionBeanRemote.getFare(l, CabinClassType.F) * passengers) + "               " + l.getSeatsInventory().getBalanceSeats() + "               " + l.getSeatsInventory().getAvailableF() : "Not availablr"));
                    System.out.println("2.BusinessClass:  " + (!(reservationSessionBeanRemote.getFare(l, CabinClassType.J)).toString().equals("-1") ? reservationSessionBeanRemote.getFare(l, CabinClassType.J) + "               " + (reservationSessionBeanRemote.getFare(l, CabinClassType.J) * passengers) + "               " + l.getSeatsInventory().getBalanceSeats() + "               " + l.getSeatsInventory().getAvailableJ() : "Not availablr"));
                    System.out.println("3.PremiumEconomy: " + (!(reservationSessionBeanRemote.getFare(l, CabinClassType.W)).toString().equals("-1") ? reservationSessionBeanRemote.getFare(l, CabinClassType.W) + "               " + (reservationSessionBeanRemote.getFare(l, CabinClassType.W) * passengers) + "               " + l.getSeatsInventory().getBalanceSeats() + "               " + l.getSeatsInventory().getAvailableW() : "Not availablr"));
                    System.out.println("4.Economy:        " + (!(reservationSessionBeanRemote.getFare(l, CabinClassType.Y)).toString().equals("-1") ? reservationSessionBeanRemote.getFare(l, CabinClassType.Y) + "               " + (reservationSessionBeanRemote.getFare(l, CabinClassType.Y) * passengers) + "               " + l.getSeatsInventory().getBalanceSeats() + "               " + l.getSeatsInventory().getAvailableY() : "Not availablr"));

                }

                for (int i = 1; i <= 3; i++) {

                    List<FlightScheduleEntity> day2 = flightReservationSessionBeanRemote.searchThreeDaysBefore(false, false, departureAirport, destinationAirport, departureTime, i, type);
                    for (FlightScheduleEntity l : day2) {
                        System.out.println("Direct " + i + " Day Before ");
                        System.out.println("FlightScheduleId       ArrivalTime            DepartureTime          Duration");
                        System.out.println();

                        System.out.println(l.getFlightScheduleId() + "                     " + l.getArrival().toString() + "       " + l.getDeparture().toString() + "       " + l.getDuration());

                        System.out.println();
                        System.out.println("                  SinglePassenger   AllPassenger  TotalFlightAvailibility       CabinClassAvailibility");
                        System.out.println("1.FirstClass:     " + (!(reservationSessionBeanRemote.getFare(l, CabinClassType.F)).toString().equals("-1") ? reservationSessionBeanRemote.getFare(l, CabinClassType.F) + "               " + (reservationSessionBeanRemote.getFare(l, CabinClassType.F) * passengers) + "               " + l.getSeatsInventory().getBalanceSeats() + "               " + l.getSeatsInventory().getAvailableF() : "Not availablr"));
                        System.out.println("2.BusinessClass:  " + (!(reservationSessionBeanRemote.getFare(l, CabinClassType.J)).toString().equals("-1") ? reservationSessionBeanRemote.getFare(l, CabinClassType.J) + "               " + (reservationSessionBeanRemote.getFare(l, CabinClassType.J) * passengers) + "               " + l.getSeatsInventory().getBalanceSeats() + "               " + l.getSeatsInventory().getAvailableJ() : "Not availablr"));
                        System.out.println("3.PremiumEconomy: " + (!(reservationSessionBeanRemote.getFare(l, CabinClassType.W)).toString().equals("-1") ? reservationSessionBeanRemote.getFare(l, CabinClassType.W) + "               " + (reservationSessionBeanRemote.getFare(l, CabinClassType.W) * passengers) + "               " + l.getSeatsInventory().getBalanceSeats() + "               " + l.getSeatsInventory().getAvailableW() : "Not availablr"));
                        System.out.println("4.Economy:        " + (!(reservationSessionBeanRemote.getFare(l, CabinClassType.Y)).toString().equals("-1") ? reservationSessionBeanRemote.getFare(l, CabinClassType.Y) + "               " + (reservationSessionBeanRemote.getFare(l, CabinClassType.Y) * passengers) + "               " + l.getSeatsInventory().getBalanceSeats() + "               " + l.getSeatsInventory().getAvailableY() : "Not availablr"));
                    }
                }

                for (int i = 1; i <= 3; i++) {

                    List<FlightScheduleEntity> day2 = flightReservationSessionBeanRemote.searchThreeDaysAfter(false, false, departureAirport, destinationAirport, departureTime, i, type);
                    for (FlightScheduleEntity l : day2) {
                        System.out.println("Direct " + i + " Day After ");
                        System.out.println("FlightScheduleId       ArrivalTime            DepartureTime          Duration");
                        System.out.println();

                        System.out.println(l.getFlightScheduleId() + "                     " + l.getArrival().toString() + "       " + l.getDeparture().toString() + "       " + l.getDuration());

                        System.out.println();
                        System.out.println("                  SinglePassenger   AllPassenger  TotalFlightAvailibility       CabinClassAvailibility");

                        System.out.println("1.FirstClass:     " + (!(reservationSessionBeanRemote.getFare(l, CabinClassType.F)).toString().equals("-1") ? reservationSessionBeanRemote.getFare(l, CabinClassType.F) + "               " + (reservationSessionBeanRemote.getFare(l, CabinClassType.F) * passengers) + "               " + l.getSeatsInventory().getBalanceSeats() + "               " + l.getSeatsInventory().getAvailableF() : "Not availablr"));
                        System.out.println("2.BusinessClass:  " + (!(reservationSessionBeanRemote.getFare(l, CabinClassType.J)).toString().equals("-1") ? reservationSessionBeanRemote.getFare(l, CabinClassType.J) + "               " + (reservationSessionBeanRemote.getFare(l, CabinClassType.J) * passengers) + "               " + l.getSeatsInventory().getBalanceSeats() + "               " + l.getSeatsInventory().getAvailableJ() : "Not availablr"));
                        System.out.println("3.PremiumEconomy: " + (!(reservationSessionBeanRemote.getFare(l, CabinClassType.W)).toString().equals("-1") ? reservationSessionBeanRemote.getFare(l, CabinClassType.W) + "               " + (reservationSessionBeanRemote.getFare(l, CabinClassType.W) * passengers) + "               " + l.getSeatsInventory().getBalanceSeats() + "               " + l.getSeatsInventory().getAvailableW() : "Not availablr"));
                        System.out.println("4.Economy:        " + (!(reservationSessionBeanRemote.getFare(l, CabinClassType.Y)).toString().equals("-1") ? reservationSessionBeanRemote.getFare(l, CabinClassType.Y) + "               " + (reservationSessionBeanRemote.getFare(l, CabinClassType.Y) * passengers) + "               " + l.getSeatsInventory().getBalanceSeats() + "               " + l.getSeatsInventory().getAvailableY() : "Not availablr"));
                    }
                }

            }
        }

        if (round) {

            if (!connecting || both) {
                System.out.println();
                System.out.println("Inbound Flights:");

                List<FlightScheduleEntity> day1 = flightReservationSessionBeanRemote.searchSingleDay(false, false, destinationAirport, departureAirport, departureTime, passengers, type);
                for (FlightScheduleEntity l : day1) {
                    System.out.println("Direct on the Same Day");
                    System.out.println("FlightScheduleId       ArrivalTime            DepartureTime          Duration");
                    System.out.println();

                    System.out.println(l.getFlightScheduleId() + "                     " + l.getArrival().toString() + "       " + l.getDeparture().toString() + "       " + l.getDuration());

                    System.out.println();
                    System.out.println("                  SinglePassenger   AllPassenger  TotalFlightAvailibility       CabinClassAvailibility");

                    System.out.println("1.FirstClass:     " + (!(reservationSessionBeanRemote.getFare(l, CabinClassType.F)).toString().equals("-1") ? reservationSessionBeanRemote.getFare(l, CabinClassType.F) + "               " + (reservationSessionBeanRemote.getFare(l, CabinClassType.F) * passengers) + "               " + l.getSeatsInventory().getBalanceSeats() + "               " + l.getSeatsInventory().getAvailableF() : "Not availablr"));
                    System.out.println("2.BusinessClass:  " + (!(reservationSessionBeanRemote.getFare(l, CabinClassType.J)).toString().equals("-1") ? reservationSessionBeanRemote.getFare(l, CabinClassType.J) + "               " + (reservationSessionBeanRemote.getFare(l, CabinClassType.J) * passengers) + "               " + l.getSeatsInventory().getBalanceSeats() + "               " + l.getSeatsInventory().getAvailableJ() : "Not availablr"));
                    System.out.println("3.PremiumEconomy: " + (!(reservationSessionBeanRemote.getFare(l, CabinClassType.W)).toString().equals("-1") ? reservationSessionBeanRemote.getFare(l, CabinClassType.W) + "               " + (reservationSessionBeanRemote.getFare(l, CabinClassType.W) * passengers) + "               " + l.getSeatsInventory().getBalanceSeats() + "               " + l.getSeatsInventory().getAvailableW() : "Not availablr"));
                    System.out.println("4.Economy:        " + (!(reservationSessionBeanRemote.getFare(l, CabinClassType.Y)).toString().equals("-1") ? reservationSessionBeanRemote.getFare(l, CabinClassType.Y) + "               " + (reservationSessionBeanRemote.getFare(l, CabinClassType.Y) * passengers) + "               " + l.getSeatsInventory().getBalanceSeats() + "               " + l.getSeatsInventory().getAvailableY() : "Not availablr"));
                }

                for (int i = 1; i <= 3; i++) {

                    List<FlightScheduleEntity> day2 = flightReservationSessionBeanRemote.searchThreeDaysBefore(false, false, destinationAirport, departureAirport, departureTime, i, type);
                    for (FlightScheduleEntity l : day2) {
                        System.out.println("Direct " + i + " Day Before ");
                        System.out.println("FlightScheduleId       ArrivalTime            DepartureTime          Duration");
                        System.out.println();

                        System.out.println(l.getFlightScheduleId() + "                     " + l.getArrival().toString() + "       " + l.getDeparture().toString() + "       " + l.getDuration());

                        System.out.println();
                        System.out.println("                  SinglePassenger   AllPassenger  TotalFlightAvailibility       CabinClassAvailibility");

                        System.out.println("1.FirstClass:     " + (!(reservationSessionBeanRemote.getFare(l, CabinClassType.F)).toString().equals("-1") ? reservationSessionBeanRemote.getFare(l, CabinClassType.F) + "               " + (reservationSessionBeanRemote.getFare(l, CabinClassType.F) * passengers) + "               " + l.getSeatsInventory().getBalanceSeats() + "               " + l.getSeatsInventory().getAvailableF() : "Not availablr"));
                        System.out.println("2.BusinessClass:  " + (!(reservationSessionBeanRemote.getFare(l, CabinClassType.J)).toString().equals("-1") ? reservationSessionBeanRemote.getFare(l, CabinClassType.J) + "               " + (reservationSessionBeanRemote.getFare(l, CabinClassType.J) * passengers) + "               " + l.getSeatsInventory().getBalanceSeats() + "               " + l.getSeatsInventory().getAvailableJ() : "Not availablr"));
                        System.out.println("3.PremiumEconomy: " + (!(reservationSessionBeanRemote.getFare(l, CabinClassType.W)).toString().equals("-1") ? reservationSessionBeanRemote.getFare(l, CabinClassType.W) + "               " + (reservationSessionBeanRemote.getFare(l, CabinClassType.W) * passengers) + "               " + l.getSeatsInventory().getBalanceSeats() + "               " + l.getSeatsInventory().getAvailableW() : "Not availablr"));
                        System.out.println("4.Economy:        " + (!(reservationSessionBeanRemote.getFare(l, CabinClassType.Y)).toString().equals("-1") ? reservationSessionBeanRemote.getFare(l, CabinClassType.Y) + "               " + (reservationSessionBeanRemote.getFare(l, CabinClassType.Y) * passengers) + "               " + l.getSeatsInventory().getBalanceSeats() + "               " + l.getSeatsInventory().getAvailableY() : "Not availablr"));
                    }
                }

                for (int i = 1; i <= 3; i++) {

                    List<FlightScheduleEntity> day2 = flightReservationSessionBeanRemote.searchThreeDaysAfter(false, false, destinationAirport, departureAirport, departureTime, i, type);
                    for (FlightScheduleEntity l : day2) {
                        System.out.println("Direct " + i + " Day After ");
                        System.out.println("FlightScheduleId       ArrivalTime            DepartureTime          Duration");
                        System.out.println();

                        System.out.println(l.getFlightScheduleId() + "                     " + l.getArrival().toString() + "       " + l.getDeparture().toString() + "       " + l.getDuration());

                        System.out.println();
                        System.out.println("                  SinglePassenger   AllPassenger  TotalFlightAvailibility       CabinClassAvailibility");

                        System.out.println("1.FirstClass:     " + (!(reservationSessionBeanRemote.getFare(l, CabinClassType.F)).toString().equals("-1") ? reservationSessionBeanRemote.getFare(l, CabinClassType.F) + "               " + (reservationSessionBeanRemote.getFare(l, CabinClassType.F) * passengers) + "               " + l.getSeatsInventory().getBalanceSeats() + "               " + l.getSeatsInventory().getAvailableF() : "Not availablr"));
                        System.out.println("2.BusinessClass:  " + (!(reservationSessionBeanRemote.getFare(l, CabinClassType.J)).toString().equals("-1") ? reservationSessionBeanRemote.getFare(l, CabinClassType.J) + "               " + (reservationSessionBeanRemote.getFare(l, CabinClassType.J) * passengers) + "               " + l.getSeatsInventory().getBalanceSeats() + "               " + l.getSeatsInventory().getAvailableJ() : "Not availablr"));
                        System.out.println("3.PremiumEconomy: " + (!(reservationSessionBeanRemote.getFare(l, CabinClassType.W)).toString().equals("-1") ? reservationSessionBeanRemote.getFare(l, CabinClassType.W) + "               " + (reservationSessionBeanRemote.getFare(l, CabinClassType.W) * passengers) + "               " + l.getSeatsInventory().getBalanceSeats() + "               " + l.getSeatsInventory().getAvailableW() : "Not availablr"));
                        System.out.println("4.Economy:        " + (!(reservationSessionBeanRemote.getFare(l, CabinClassType.Y)).toString().equals("-1") ? reservationSessionBeanRemote.getFare(l, CabinClassType.Y) + "               " + (reservationSessionBeanRemote.getFare(l, CabinClassType.Y) * passengers) + "               " + l.getSeatsInventory().getBalanceSeats() + "               " + l.getSeatsInventory().getAvailableY() : "Not availablr"));
                    }
                }

            }

            if (connecting || both) {
                if (use) {

                    LocalDateTime temp = departureTime;
                    departureTime = returnTime;
                    returnTime = temp;

                    int i = 0;
                    List<FlightScheduleEntity> list = flightReservationSessionBeanRemote.getOneWayAfter(true, true, departureAirport, destinationAirport, departureTime, 0, type);

                    System.out.println(list);
                    for (FlightScheduleEntity conn : list) {

                        List<FlightScheduleEntity> list4 = flightReservationSessionBeanRemote.getDestination(connecting, round, conn.getFlightSchedulePlan().getFlight().getFlightRoute().getDestination().getCode(), destinationAirport, conn.getArrival(), 0, type);

                        for (FlightScheduleEntity l : list4) {
                            System.out.println("Connecting on the Same Day");
                            System.out.println("FlightScheduleId       ArrivalTime            DepartureTime          Duration");
                            System.out.println(conn.getFlightScheduleId() + "                     " + conn.getArrival().toString() + "        " + conn.getDeparture().toString() + "           " + conn.getDuration());
                            System.out.println(l.getFlightScheduleId() + "                     " + l.getArrival().toString() + "        " + l.getDeparture().toString() + "            " + l.getDuration());
                        }

                    }

                    List<FlightScheduleEntity> day2degree = flightReservationSessionBeanRemote.getOneWayAfter(true, true, departureAirport, destinationAirport, departureTime, 0, type);
//                    System.out.println(day2degree);

                    int j = 0;
                    i = 0;
                    for (FlightScheduleEntity fs : day2degree) {

                        List<FlightScheduleEntity> list4 = flightReservationSessionBeanRemote.getOneWayAfter(connecting, round, fs.getFlightSchedulePlan().getFlight().getFlightRoute().getDestination().getCode(), fs.getFlightSchedulePlan().getFlight().getFlightRoute().getDestination().getCode(), fs.getArrival(), 0, type);
                        j = 0;
//                        System.out.println(list4);
//                        System.out.println("FS" + fs);

                        for (FlightScheduleEntity conn : list4) {

//                            System.out.println("Conn" + conn);
                            List<FlightScheduleEntity> l5 = flightReservationSessionBeanRemote.getDestination(connecting, round, conn.getFlightSchedulePlan().getFlight().getFlightRoute().getDestination().getCode(), destinationAirport, conn.getArrival(), 0, type);

                            for (FlightScheduleEntity l : l5) {
                                System.out.println("Connecting on the Same Day");
                                System.out.println("FlightScheduleId       ArrivalTime            DepartureTime          Duration");
                                System.out.println(fs.getFlightScheduleId() + "                     " + fs.getArrival().toString() + "        " + fs.getDeparture().toString() + "           " + fs.getDuration());
                                System.out.println(conn.getFlightScheduleId() + "                     " + conn.getArrival().toString() + "        " + conn.getDeparture().toString() + "           " + conn.getDuration());
                                System.out.println(l.getFlightScheduleId() + "                     " + l.getArrival().toString() + "        " + l.getDeparture().toString() + "            " + l.getDuration());
                            }
                        }
                    }

                }

                for (int k = 1; k <= 3; k++) {

                    List<FlightScheduleEntity> list = flightReservationSessionBeanRemote.getOneWayAfter(true, true, departureAirport, destinationAirport, departureTime.plusDays(k), 0, type);

//                    System.out.println(list);
                    for (FlightScheduleEntity conn : list) {

                        List<FlightScheduleEntity> list4 = flightReservationSessionBeanRemote.getDestination(connecting, round, conn.getFlightSchedulePlan().getFlight().getFlightRoute().getDestination().getCode(), destinationAirport, conn.getArrival(), 0, type);

                        for (FlightScheduleEntity l : list4) {
                            System.out.println("Connecting " + k + " day after");
                            System.out.println("FlightScheduleId       ArrivalTime            DepartureTime          Duration");
                            System.out.println(conn.getFlightScheduleId() + "                     " + conn.getArrival().toString() + "        " + conn.getDeparture().toString() + "           " + conn.getDuration());
                            System.out.println(l.getFlightScheduleId() + "                     " + l.getArrival().toString() + "        " + l.getDeparture().toString() + "            " + l.getDuration());
                        }

                    }

                    List<FlightScheduleEntity> day2degree = flightReservationSessionBeanRemote.getOneWayAfter(true, true, departureAirport, destinationAirport, departureTime.plusDays(k), 0, type);
//                    System.out.println(day2degree);

                    int j = 0;
                    int i = 0;
                    for (FlightScheduleEntity fs : day2degree) {

                        List<FlightScheduleEntity> list4 = flightReservationSessionBeanRemote.getOneWayAfter(connecting, round, fs.getFlightSchedulePlan().getFlight().getFlightRoute().getDestination().getCode(), fs.getFlightSchedulePlan().getFlight().getFlightRoute().getDestination().getCode(), fs.getArrival(), 0, type);
                        j = 0;

                        for (FlightScheduleEntity conn : list4) {

//                            System.out.println("Conn" + conn);
                            List<FlightScheduleEntity> l5 = flightReservationSessionBeanRemote.getDestination(connecting, round, conn.getFlightSchedulePlan().getFlight().getFlightRoute().getDestination().getCode(), destinationAirport, conn.getArrival(), 0, type);

                            for (FlightScheduleEntity l : l5) {
                                System.out.println("Connecting " + k + " day after");
                                System.out.println("FlightScheduleId       ArrivalTime            DepartureTime          Duration");
                                System.out.println(fs.getFlightScheduleId() + "                     " + fs.getArrival().toString() + "        " + fs.getDeparture().toString() + "           " + fs.getDuration());
                                System.out.println(conn.getFlightScheduleId() + "                     " + conn.getArrival().toString() + "        " + conn.getDeparture().toString() + "           " + conn.getDuration());
                                System.out.println(l.getFlightScheduleId() + "                     " + l.getArrival().toString() + "        " + l.getDeparture().toString() + "            " + l.getDuration());
                            }
                        }
                    }

                }

                for (int k = 1; k <= 3; k++) {

                    List<FlightScheduleEntity> list = flightReservationSessionBeanRemote.getOneWayAfter(true, true, departureAirport, destinationAirport, departureTime.minusDays(k), 0, type);

//                    System.out.println(list);
                    for (FlightScheduleEntity conn : list) {

                        List<FlightScheduleEntity> list4 = flightReservationSessionBeanRemote.getDestination(connecting, round, conn.getFlightSchedulePlan().getFlight().getFlightRoute().getDestination().getCode(), destinationAirport, conn.getArrival(), 0, type);

                        for (FlightScheduleEntity l : list4) {
                            System.out.println("Connecting " + k + " day Before");
                            System.out.println("FlightScheduleId       ArrivalTime            DepartureTime          Duration");
                            System.out.println(conn.getFlightScheduleId() + "                     " + conn.getArrival().toString() + "        " + conn.getDeparture().toString() + "           " + conn.getDuration());
                            System.out.println(l.getFlightScheduleId() + "                     " + l.getArrival().toString() + "        " + l.getDeparture().toString() + "            " + l.getDuration());
                        }

                    }

                    List<FlightScheduleEntity> day2degree = flightReservationSessionBeanRemote.getOneWayAfter(true, true, departureAirport, destinationAirport, departureTime.minusDays(k), 0, type);
//                    System.out.println(day2degree);

                    int j = 0;
                    int i = 0;
                    for (FlightScheduleEntity fs : day2degree) {

                        List<FlightScheduleEntity> list4 = flightReservationSessionBeanRemote.getOneWayAfter(connecting, round, fs.getFlightSchedulePlan().getFlight().getFlightRoute().getDestination().getCode(), fs.getFlightSchedulePlan().getFlight().getFlightRoute().getDestination().getCode(), fs.getArrival(), 0, type);
                        j = 0;
//                        System.out.println(list4);
//                        System.out.println("FS" + fs);

                        for (FlightScheduleEntity conn : list4) {

//                            System.out.println("Conn" + conn);
                            List<FlightScheduleEntity> l5 = flightReservationSessionBeanRemote.getDestination(connecting, round, conn.getFlightSchedulePlan().getFlight().getFlightRoute().getDestination().getCode(), destinationAirport, conn.getArrival(), 0, type);

                            for (FlightScheduleEntity l : l5) {
                                System.out.println("Connecting " + k + " day Before");
                                System.out.println("FlightScheduleId       ArrivalTime            DepartureTime          Duration");
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

    private void doRegisterCustomer() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("*** FRS Reservation :: Register As Customer ***\n");

        System.out.print("Enter first name> ");
        String first = scanner.nextLine().trim();
        System.out.print("Enter last name> ");
        String last = scanner.nextLine().trim();
        System.out.print("Enter email> ");
        String email = scanner.nextLine().trim();
        System.out.print("Enter password> ");
        String password = scanner.nextLine().trim();
        System.out.print("Enter mobile number> ");
        Integer num = scanner.nextInt();

        CustomerEntity cust = new CustomerEntity(first, last, email, num, password);

        CustomerEntity custNew = customerSessionBeanRemote.createNewCustomer(cust);
        System.out.println("New Customer ID " + custNew.getCustomerId() + " has been successfully created!");

    }

    private void doCustomerLogin() throws InvalidLoginCredentialException {

        Scanner scanner = new Scanner(System.in);
        String email = "";
        String password = "";

        System.out.println("*** FRS Reservation :: Login ***\n");
        System.out.print("Enter email> ");
        email = scanner.nextLine().trim();
        System.out.print("Enter password> ");
        password = scanner.nextLine().trim();

        if (email.length() > 0 && password.length() > 0) {
            currentCustomer = customerSessionBeanRemote.login(email, password);
            this.currentCustomer = currentCustomer;
        } else {
            throw new InvalidLoginCredentialException("Missing login credential!");
        }

    }

}
