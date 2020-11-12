/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.AircraftConfigurationSessionBeanLocal;
import ejb.session.stateless.AircraftTypeSessionBeanLocal;
import ejb.session.stateless.AirportSessionBeanLocal;
import ejb.session.stateless.EmployeeSessionBeanLocal;
import ejb.session.stateless.FlightRouteSessionBeanLocal;
import ejb.session.stateless.FlightSchedulePlanSessionBeanLocal;
import ejb.session.stateless.FlightSessionBeanLocal;
import ejb.session.stateless.PartnerSessionBeanLocal;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import util.exception.EmployeeNotFoundException;

import entity.AircraftConfigurationEntity;
import entity.AircraftTypeEntity;
import entity.AirportEntity;
import entity.CabinClassConfigurationEntity;
import entity.CustomerEntity;
import entity.EmployeeEntity;
import entity.FareEntity;
import entity.FlightEntity;
import entity.FlightRouteEntity;
import entity.FlightScheduleEntity;
import entity.FlightSchedulePlanEntity;
import entity.PartnerEntity;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.converter.LocalDateTimeStringConverter;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.enumeration.CabinClassType;
import util.enumeration.EmployeeAccessRight;
import util.enumeration.ScheduleEnum;
import util.exception.AircraftConfigurationNotFoundException;
import util.exception.AircraftTypeNotFoundException;
import util.exception.AirportNotFoundException;
import util.exception.FlightNotFoundException;
import util.exception.FlightRouteNotFoundException;

/**
 *
 * @author leahr
 */
@Singleton
@LocalBean
@Startup
public class DataInitSessionBean {

    @EJB
    private FlightSessionBeanLocal flightSessionBean;

    @EJB
    private AirportSessionBeanLocal airportSessionBean;

    @EJB
    private FlightSchedulePlanSessionBeanLocal flightSchedulePlanSessionBean;

    @EJB
    private FlightRouteSessionBeanLocal flightRouteSessionBean;

    @EJB
    private AircraftConfigurationSessionBeanLocal aircraftConfigurationSessionBean;

    @PersistenceContext(unitName = "MerlionAirApplication-ejbPU")
    private EntityManager em;

    public DataInitSessionBean() {
    }

    @PostConstruct
    public void postConstruct() {
        if (em.find(AirportEntity.class, 1l) == null) {
            try {
                initializeData();
            } catch (AirportNotFoundException ex) {
                Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void initializeData() throws AirportNotFoundException {

        EmployeeEntity e = new EmployeeEntity(" Fleet", "Manager", "fleetmanager", "password", EmployeeAccessRight.FLEETMANAGER);
        em.persist(e);
        em.flush();

        e = new EmployeeEntity("Route", "Planner", "routeplanner", "password", EmployeeAccessRight.ROUTEPLANNER);
        em.persist(e);
        em.flush();
        e = new EmployeeEntity(" Schedule", "Manager", "schedulemanager", "password", EmployeeAccessRight.SCHEDULEMANAGER);
        em.persist(e);
        em.flush();
        e = new EmployeeEntity("Sales", "Manager", "salesmanager", "password", EmployeeAccessRight.SALESMANAGER);
        em.persist(e);
        em.flush();

        PartnerEntity p = new PartnerEntity("Holiday.com", "holidaydotcom", "password");
        em.persist(p);

        AirportEntity a = new AirportEntity("Changi", "SIN", "Singapore", "Singapore", "Singapore", 8);
        em.persist(a);
        em.flush();
        a = new AirportEntity("Hong Kong", "HKG", "Chek Lap Kok", " Hong Kong", "China", 8);
        em.persist(a);
        em.flush();
        a = new AirportEntity("Taoyuan", "TPE", "Taoyuan", "Taipei", "Taiwan R.O.C.", 8);
        em.persist(a);
        em.flush();

        a = new AirportEntity("Narita", "NRT", "Narita", "Chiba", "Japan", 9);
        em.persist(a);
        em.flush();

        a = new AirportEntity("Sydney", "SYD", "Sydney", "New South Wales", "Australia", 11);
        em.persist(a);
        em.flush();

        AircraftTypeEntity ate = new AircraftTypeEntity("Boeing 737", 200);
        em.persist(ate);
        em.flush();

        AircraftTypeEntity ate2 = new AircraftTypeEntity("Boeing 747", 400);
        em.persist(ate2);
        em.flush();

        AircraftConfigurationEntity ac = new AircraftConfigurationEntity("Boeing 737 All Economy", 1, 180);
        List<CabinClassConfigurationEntity> cc = new ArrayList<>();
        cc.add(new CabinClassConfigurationEntity(CabinClassType.Y, 1, 30, 6, "3-3", 180));
        try {
            aircraftConfigurationSessionBean.createNewAircraftConfiguration(ac, cc, ate.getAircraftTypeId());
        } catch (AircraftTypeNotFoundException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AircraftConfigurationNotFoundException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        ac = new AircraftConfigurationEntity("Boeing 737 Three Classes", 3, 180);
        cc = new ArrayList<>();
        cc.add(new CabinClassConfigurationEntity(CabinClassType.F, 1, 5, 2, "1-1", 10));
        cc.add(new CabinClassConfigurationEntity(CabinClassType.J, 1, 5, 4, "2-2", 20));
        cc.add(new CabinClassConfigurationEntity(CabinClassType.Y, 1, 25, 6, "3-3", 150));

        try {
            aircraftConfigurationSessionBean.createNewAircraftConfiguration(ac, cc, ate.getAircraftTypeId());
        } catch (AircraftTypeNotFoundException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AircraftConfigurationNotFoundException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        ac = new AircraftConfigurationEntity("Boeing 737 All Economy", 1, 380);
        cc = new ArrayList<>();
        cc.add(new CabinClassConfigurationEntity(CabinClassType.Y, 2, 38, 10, "3-4-3", 380));
        try {   
            aircraftConfigurationSessionBean.createNewAircraftConfiguration(ac, cc, ate.getAircraftTypeId());
        } catch (AircraftTypeNotFoundException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AircraftConfigurationNotFoundException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        ac = new AircraftConfigurationEntity("Boeing 747 Three Classes", 1, 360);
        cc = new ArrayList<>();
        cc.add(new CabinClassConfigurationEntity(CabinClassType.F, 1, 5, 2, "1-1", 10));
        cc.add(new CabinClassConfigurationEntity(CabinClassType.J, 2, 5, 6, "2-2-2", 30));
        cc.add(new CabinClassConfigurationEntity(CabinClassType.Y, 2, 32, 10, "3-4-3", 320));

        try {
            aircraftConfigurationSessionBean.createNewAircraftConfiguration(ac, cc, ate.getAircraftTypeId());
        } catch (AircraftTypeNotFoundException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AircraftConfigurationNotFoundException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        Long id = flightRouteSessionBean.createNewFlightRoute(new FlightRouteEntity(airportSessionBean.retriveBy("SIN"), airportSessionBean.retriveBy("HKG")));
        try {
            flightRouteSessionBean.createNewComplementaryReturnRoute(id, new FlightRouteEntity(airportSessionBean.retriveBy("HKG"), airportSessionBean.retriveBy("SIN")));
        } catch (FlightRouteNotFoundException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        id = flightRouteSessionBean.createNewFlightRoute(new FlightRouteEntity(airportSessionBean.retriveBy("SIN"), airportSessionBean.retriveBy("TPE")));
        try {
            flightRouteSessionBean.createNewComplementaryReturnRoute(id, new FlightRouteEntity(airportSessionBean.retriveBy("TPE"), airportSessionBean.retriveBy("SIN")));
        } catch (FlightRouteNotFoundException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        id = flightRouteSessionBean.createNewFlightRoute(new FlightRouteEntity(airportSessionBean.retriveBy("SIN"), airportSessionBean.retriveBy("NRT")));
        try {
            flightRouteSessionBean.createNewComplementaryReturnRoute(id, new FlightRouteEntity(airportSessionBean.retriveBy("NRT"), airportSessionBean.retriveBy("SIN")));
        } catch (FlightRouteNotFoundException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        id = flightRouteSessionBean.createNewFlightRoute(new FlightRouteEntity(airportSessionBean.retriveBy("HKG"), airportSessionBean.retriveBy("NRT")));
        try {
            flightRouteSessionBean.createNewComplementaryReturnRoute(id, new FlightRouteEntity(airportSessionBean.retriveBy("NRT"), airportSessionBean.retriveBy("HKG")));
        } catch (FlightRouteNotFoundException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        id = flightRouteSessionBean.createNewFlightRoute(new FlightRouteEntity(airportSessionBean.retriveBy("TPE"), airportSessionBean.retriveBy("NRT")));
        try {
            flightRouteSessionBean.createNewComplementaryReturnRoute(id, new FlightRouteEntity(airportSessionBean.retriveBy("NRT"), airportSessionBean.retriveBy("TPE")));
        } catch (FlightRouteNotFoundException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        id = flightRouteSessionBean.createNewFlightRoute(new FlightRouteEntity(airportSessionBean.retriveBy("SIN"), airportSessionBean.retriveBy("SYD")));
        try {
            flightRouteSessionBean.createNewComplementaryReturnRoute(id, new FlightRouteEntity(airportSessionBean.retriveBy("SYD"), airportSessionBean.retriveBy("SIN")));
        } catch (FlightRouteNotFoundException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            id = flightRouteSessionBean.createNewFlightRoute(new FlightRouteEntity(airportSessionBean.retriveBy("SYD"), airportSessionBean.retriveBy("NRT")));
        } catch (AirportNotFoundException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            flightRouteSessionBean.createNewComplementaryReturnRoute(id, new FlightRouteEntity(airportSessionBean.retriveBy("NRT"), airportSessionBean.retriveBy("SYD")));
        } catch (FlightRouteNotFoundException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            FlightEntity f = new FlightEntity("MA111", flightRouteSessionBean.retrieveFlightRouteByAirportCode("SIN", "HKG") , aircraftConfigurationSessionBean.retrieveAircraftConfigurationByAircraftConfigurationId(2L));
            FlightEntity f2 = new FlightEntity("MA112", flightRouteSessionBean.retrieveFlightRouteByAirportCode("HKG", "SIN"), aircraftConfigurationSessionBean.retrieveAircraftConfigurationByAircraftConfigurationId(2L));
            f2.setComplementary(true);
            f = flightSessionBean.createNewFlight(f);
            f.setComplentaryFlight(f2);
            f2 = flightSessionBean.createNewFlight(f2);

        } catch (FlightRouteNotFoundException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AircraftConfigurationNotFoundException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            FlightEntity f = new FlightEntity("ML211", flightRouteSessionBean.retrieveFlightRouteByAirportCode("SIN", "TPE"), aircraftConfigurationSessionBean.retrieveAircraftConfigurationByAircraftConfigurationId(2L));
            FlightEntity f2 = new FlightEntity("ML212", flightRouteSessionBean.retrieveFlightRouteByAirportCode("TPE", "SIN"), aircraftConfigurationSessionBean.retrieveAircraftConfigurationByAircraftConfigurationId(2L));
            f2.setComplementary(true);
            f = flightSessionBean.createNewFlight(f);
            f.setComplentaryFlight(f2);
            flightSessionBean.createNewFlight(f2);

        } catch (FlightRouteNotFoundException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AircraftConfigurationNotFoundException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            FlightEntity f = new FlightEntity("ML311", flightRouteSessionBean.retrieveFlightRouteByAirportCode("SIN", "NRT"), aircraftConfigurationSessionBean.retrieveAircraftConfigurationByAircraftConfigurationId(4L));
            FlightEntity f2 = new FlightEntity("ML312", flightRouteSessionBean.retrieveFlightRouteByAirportCode("NRT", "SIN"), aircraftConfigurationSessionBean.retrieveAircraftConfigurationByAircraftConfigurationId(4L));
            f2.setComplementary(true);
            f = flightSessionBean.createNewFlight(f);
            f.setComplentaryFlight(f2);
            flightSessionBean.createNewFlight(f2);

        } catch (FlightRouteNotFoundException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AircraftConfigurationNotFoundException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            FlightEntity f = new FlightEntity("ML411", flightRouteSessionBean.retrieveFlightRouteByAirportCode("HKG", "NRT"), aircraftConfigurationSessionBean.retrieveAircraftConfigurationByAircraftConfigurationId(2L));
            FlightEntity f2 = new FlightEntity("ML412", flightRouteSessionBean.retrieveFlightRouteByAirportCode("NRT", "HKG"), aircraftConfigurationSessionBean.retrieveAircraftConfigurationByAircraftConfigurationId(2L));
            f2.setComplementary(true);
            f = flightSessionBean.createNewFlight(f);
            f.setComplentaryFlight(f2);
            flightSessionBean.createNewFlight(f2);

        } catch (FlightRouteNotFoundException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AircraftConfigurationNotFoundException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            FlightEntity f = new FlightEntity("ML511", flightRouteSessionBean.retrieveFlightRouteByAirportCode("TPE", "NRT"), aircraftConfigurationSessionBean.retrieveAircraftConfigurationByAircraftConfigurationId(2L));
            FlightEntity f2 = new FlightEntity("ML512", flightRouteSessionBean.retrieveFlightRouteByAirportCode("NRT", "TPE"), aircraftConfigurationSessionBean.retrieveAircraftConfigurationByAircraftConfigurationId(2L));
            f2.setComplementary(true);
            f = flightSessionBean.createNewFlight(f);
            f.setComplentaryFlight(f2);
            flightSessionBean.createNewFlight(f2);

        } catch (FlightRouteNotFoundException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AircraftConfigurationNotFoundException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            FlightEntity f = new FlightEntity("ML611", flightRouteSessionBean.retrieveFlightRouteByAirportCode("SIN", "SYD"), aircraftConfigurationSessionBean.retrieveAircraftConfigurationByAircraftConfigurationId(2L));
            FlightEntity f2 = new FlightEntity("ML612", flightRouteSessionBean.retrieveFlightRouteByAirportCode("SYD", "SIN"), aircraftConfigurationSessionBean.retrieveAircraftConfigurationByAircraftConfigurationId(2L));
            f2.setComplementary(true);
            f = flightSessionBean.createNewFlight(f);
            f.setComplentaryFlight(f2);
            flightSessionBean.createNewFlight(f2);

        } catch (FlightRouteNotFoundException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AircraftConfigurationNotFoundException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            FlightEntity f = new FlightEntity("ML621", flightRouteSessionBean.retrieveFlightRouteByAirportCode("SIN", "SYD"), aircraftConfigurationSessionBean.retrieveAircraftConfigurationByAircraftConfigurationId(2L));
            FlightEntity f2 = new FlightEntity("ML622", flightRouteSessionBean.retrieveFlightRouteByAirportCode("SYD", "SIN"), aircraftConfigurationSessionBean.retrieveAircraftConfigurationByAircraftConfigurationId(2L));
            f2.setComplementary(true);
            f = flightSessionBean.createNewFlight(f);
            f.setComplentaryFlight(f2);
            flightSessionBean.createNewFlight(f2);

        } catch (FlightRouteNotFoundException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AircraftConfigurationNotFoundException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            FlightEntity f = new FlightEntity("ML711", flightRouteSessionBean.retrieveFlightRouteByAirportCode("SYD", "NRT"), aircraftConfigurationSessionBean.retrieveAircraftConfigurationByAircraftConfigurationId(4L));
            FlightEntity f2 = new FlightEntity("ML712", flightRouteSessionBean.retrieveFlightRouteByAirportCode("NRT", "SYD"), aircraftConfigurationSessionBean.retrieveAircraftConfigurationByAircraftConfigurationId(4L));
            f2.setComplementary(true);
            f = flightSessionBean.createNewFlight(f);
            f.setComplentaryFlight(f2);
            flightSessionBean.createNewFlight(f2);

        } catch (FlightRouteNotFoundException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AircraftConfigurationNotFoundException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            FlightSchedulePlanEntity fsp = new FlightSchedulePlanEntity(flightSessionBean.retrieveFlightByFlightNumber("MA711"), ScheduleEnum.RECURRENTWEEK);
            List<FlightScheduleEntity> flightScheduleList = new ArrayList<FlightScheduleEntity>();
            String endDateOfRecurrent = "2020-12-31 09:00";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime endDate = LocalDateTime.parse(endDateOfRecurrent, formatter);

            FlightEntity flight = flightSessionBean.retrieveFlightByFlightNumber("M711");
            String date = "2020-12-07 09:00";
            LocalDateTime departure = LocalDateTime.parse(date, formatter);
            FlightRouteEntity flightRoute = flight.getFlightRoute();
            int timeDifference = flightRoute.getDestination().getTimeZone() - flightRoute.getOrigin().getTimeZone();
            LocalDateTime arrival = departure.plusHours(14 + timeDifference);
            while (arrival.isBefore(endDate) || arrival.isEqual(endDate)) {
                flightScheduleList.add(new FlightScheduleEntity(departure, arrival, 14));
                departure = departure.plusWeeks(1);
                arrival = arrival.plusWeeks(1);
            }
            
        List<CabinClassConfigurationEntity> cccList = flight.getAircraftConfiguration().getCabinClassConfigurations();
        List<FareEntity> fareList = new ArrayList<FareEntity>();
        fareList.add(new FareEntity(CabinClassType.F, "F001", "6500"));
        fareList.add(new FareEntity(CabinClassType.F, "F002", "6000"));
        fareList.add(new FareEntity(CabinClassType.J, "J001", "3500"));
        fareList.add(new FareEntity(CabinClassType.J, "J002", "3000"));
        fareList.add(new FareEntity(CabinClassType.Y, "Y001", "1500"));
        fareList.add(new FareEntity(CabinClassType.Y, "Y002", "1000"));

        

        FlightSchedulePlanEntity fspId = flightSchedulePlanSessionBean.createFlightSchedulePlan(fsp, flightScheduleList, fareList, flight);
        
        FlightEntity complementaryFlight = flightSessionBean.retrieveFlightByFlightNumber(flight.getComplementaryFlight().getFlightNumber());
                List<FlightScheduleEntity> complementaryFs = new ArrayList<FlightScheduleEntity>(); 
                FlightSchedulePlanEntity complementaryFsp = new FlightSchedulePlanEntity(complementaryFlight);
                complementaryFsp.setSchedule(fspId.getSchedule());
                for(FlightScheduleEntity fs : fspId.getFlightSchedules()) 
                {
                    departure = fs.getArrival().plusHours(2);
                    int flightDuration = fs.getDuration();
                    complementaryFs.add(new FlightScheduleEntity(departure, departure.plusHours(flightDuration), flightDuration));
                }
                 FlightSchedulePlanEntity complementaryFspId = flightSchedulePlanSessionBean.createFlightSchedulePlan(complementaryFsp, complementaryFs, fareList,complementaryFlight);
        
        
        } catch (FlightNotFoundException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        
             try {
            FlightSchedulePlanEntity fsp = new FlightSchedulePlanEntity(flightSessionBean.retrieveFlightByFlightNumber("ML611"), ScheduleEnum.RECURRENTWEEK);
            List<FlightScheduleEntity> flightScheduleList = new ArrayList<FlightScheduleEntity>();
            String endDateOfRecurrent = "2020-12-31 12:00";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime endDate = LocalDateTime.parse(endDateOfRecurrent, formatter);

            FlightEntity flight = flightSessionBean.retrieveFlightByFlightNumber("ML611");
            String date = "2020-12-07 12:00";
            LocalDateTime departure = LocalDateTime.parse(date, formatter);
            FlightRouteEntity flightRoute = flight.getFlightRoute();
            int timeDifference = flightRoute.getDestination().getTimeZone() - flightRoute.getOrigin().getTimeZone();
            LocalDateTime arrival = departure.plusHours(8 + timeDifference);
            while (arrival.isBefore(endDate) || arrival.isEqual(endDate)) {
                flightScheduleList.add(new FlightScheduleEntity(departure, arrival, 8));
                departure = departure.plusWeeks(1);
                arrival = arrival.plusWeeks(1);
            }
            
        List<CabinClassConfigurationEntity> cccList = flight.getAircraftConfiguration().getCabinClassConfigurations();
        List<FareEntity> fareList = new ArrayList<FareEntity>();
        fareList.add(new FareEntity(CabinClassType.F, "F001", "3250"));
        fareList.add(new FareEntity(CabinClassType.F, "F002", "3000"));
        fareList.add(new FareEntity(CabinClassType.J, "J001", "1750"));
        fareList.add(new FareEntity(CabinClassType.J, "J002", "1500"));
        fareList.add(new FareEntity(CabinClassType.Y, "Y001", "750"));
        fareList.add(new FareEntity(CabinClassType.Y, "Y002", "500"));

        

        FlightSchedulePlanEntity fspId = flightSchedulePlanSessionBean.createFlightSchedulePlan(fsp, flightScheduleList, fareList, flight);
        
        FlightEntity complementaryFlight = flightSessionBean.retrieveFlightByFlightNumber(flight.getComplementaryFlight().getFlightNumber());
                List<FlightScheduleEntity> complementaryFs = new ArrayList<FlightScheduleEntity>(); 
                FlightSchedulePlanEntity complementaryFsp = new FlightSchedulePlanEntity(complementaryFlight);
                complementaryFsp.setSchedule(fspId.getSchedule());
                for(FlightScheduleEntity fs : fspId.getFlightSchedules()) 
                {
                    departure = fs.getArrival().plusHours(2);
                    int flightDuration = fs.getDuration();
                    complementaryFs.add(new FlightScheduleEntity(departure, departure.plusHours(flightDuration), flightDuration));
                }
                 FlightSchedulePlanEntity complementaryFspId = flightSchedulePlanSessionBean.createFlightSchedulePlan(complementaryFsp, complementaryFs, fareList,complementaryFlight);
        
        
        } catch (FlightNotFoundException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
             
             
                  try {
            FlightSchedulePlanEntity fsp = new FlightSchedulePlanEntity(flightSessionBean.retrieveFlightByFlightNumber("ML621"), ScheduleEnum.RECURRENTWEEK);
            List<FlightScheduleEntity> flightScheduleList = new ArrayList<FlightScheduleEntity>();
            String endDateOfRecurrent = "2020-12-31 10:00";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime endDate = LocalDateTime.parse(endDateOfRecurrent, formatter);

            FlightEntity flight = flightSessionBean.retrieveFlightByFlightNumber("ML621");
            String date = "2020-12-07 10:00";
            LocalDateTime departure = LocalDateTime.parse(date, formatter);
            FlightRouteEntity flightRoute = flight.getFlightRoute();
            int timeDifference = flightRoute.getDestination().getTimeZone() - flightRoute.getOrigin().getTimeZone();
            LocalDateTime arrival = departure.plusHours(8 + timeDifference);
            while (arrival.isBefore(endDate) || arrival.isEqual(endDate)) {
                flightScheduleList.add(new FlightScheduleEntity(departure, arrival, 8));
                departure = departure.plusWeeks(1);
                arrival = arrival.plusWeeks(1);
            }
            
        List<CabinClassConfigurationEntity> cccList = flight.getAircraftConfiguration().getCabinClassConfigurations();
        List<FareEntity> fareList = new ArrayList<FareEntity>();
        fareList.add(new FareEntity(CabinClassType.Y, "Y001", "700"));
        fareList.add(new FareEntity(CabinClassType.Y, "Y002", "400"));
        
        FlightSchedulePlanEntity fspId = flightSchedulePlanSessionBean.createFlightSchedulePlan(fsp, flightScheduleList, fareList, flight);
        
        FlightEntity complementaryFlight = flightSessionBean.retrieveFlightByFlightNumber(flight.getComplementaryFlight().getFlightNumber());
                List<FlightScheduleEntity> complementaryFs = new ArrayList<FlightScheduleEntity>(); 
                FlightSchedulePlanEntity complementaryFsp = new FlightSchedulePlanEntity(complementaryFlight);
                complementaryFsp.setSchedule(fspId.getSchedule());
                for(FlightScheduleEntity fs : fspId.getFlightSchedules()) 
                {
                    departure = fs.getArrival().plusHours(2);
                    int flightDuration = fs.getDuration();
                    complementaryFs.add(new FlightScheduleEntity(departure, departure.plusHours(flightDuration), flightDuration));
                }
                 FlightSchedulePlanEntity complementaryFspId = flightSchedulePlanSessionBean.createFlightSchedulePlan(complementaryFsp, complementaryFs, fareList,complementaryFlight);
        
        
        } catch (FlightNotFoundException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
                  
                       try {
            FlightSchedulePlanEntity fsp = new FlightSchedulePlanEntity(flightSessionBean.retrieveFlightByFlightNumber("ML311"), ScheduleEnum.RECURRENTWEEK);
            List<FlightScheduleEntity> flightScheduleList = new ArrayList<FlightScheduleEntity>();
            String endDateOfRecurrent = "2020-12-31 10:00";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime endDate = LocalDateTime.parse(endDateOfRecurrent, formatter);

            FlightEntity flight = flightSessionBean.retrieveFlightByFlightNumber("ML311");
            String date = "2020-12-07 10:00";
            LocalDateTime departure = LocalDateTime.parse(date, formatter);
            FlightRouteEntity flightRoute = flight.getFlightRoute();
            int timeDifference = flightRoute.getDestination().getTimeZone() - flightRoute.getOrigin().getTimeZone();
            LocalDateTime arrival = departure.plusHours(6 + timeDifference).plusMinutes(30);
            while (arrival.isBefore(endDate) || arrival.isEqual(endDate)) {
                flightScheduleList.add(new FlightScheduleEntity(departure, arrival, 6));//Need to adapt for decimal minutes
                departure = departure.plusWeeks(1);
                arrival = arrival.plusWeeks(1);
            }
            
        List<CabinClassConfigurationEntity> cccList = flight.getAircraftConfiguration().getCabinClassConfigurations();
        List<FareEntity> fareList = new ArrayList<FareEntity>();
        fareList.add(new FareEntity(CabinClassType.F, "F001", "3350"));
        fareList.add(new FareEntity(CabinClassType.F, "F002", "3200"));
        fareList.add(new FareEntity(CabinClassType.J, "J001", "1850"));
        fareList.add(new FareEntity(CabinClassType.J, "J002", "1600"));
        fareList.add(new FareEntity(CabinClassType.Y, "Y001", "850"));
        fareList.add(new FareEntity(CabinClassType.Y, "Y002", "600"));


        

        FlightSchedulePlanEntity fspId = flightSchedulePlanSessionBean.createFlightSchedulePlan(fsp, flightScheduleList, fareList, flight);
        
        FlightEntity complementaryFlight = flightSessionBean.retrieveFlightByFlightNumber(flight.getComplementaryFlight().getFlightNumber());
                List<FlightScheduleEntity> complementaryFs = new ArrayList<FlightScheduleEntity>(); 
                FlightSchedulePlanEntity complementaryFsp = new FlightSchedulePlanEntity(complementaryFlight);
                complementaryFsp.setSchedule(fspId.getSchedule());
                for(FlightScheduleEntity fs : fspId.getFlightSchedules()) 
                {
                    departure= fs.getArrival().plusHours(3);
                    int flightDuration = fs.getDuration();
                    complementaryFs.add(new FlightScheduleEntity(departure, departure.plusHours(flightDuration), flightDuration));
                }
                 FlightSchedulePlanEntity complementaryFspId = flightSchedulePlanSessionBean.createFlightSchedulePlan(complementaryFsp, complementaryFs, fareList,complementaryFlight);
        
        
        } catch (FlightNotFoundException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
                       
                       
            
          try{
            FlightSchedulePlanEntity fsp = new FlightSchedulePlanEntity(flightSessionBean.retrieveFlightByFlightNumber("ML411"), ScheduleEnum.RECURRENTDAY);
            List<FlightScheduleEntity> flightScheduleList = new ArrayList<FlightScheduleEntity>();
            String endDateOfRecurrent = "2020-12-31 13:00";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime endDate = LocalDateTime.parse(endDateOfRecurrent, formatter);

            FlightEntity flight = flightSessionBean.retrieveFlightByFlightNumber("ML411");
            String date = "2020-12-01 13:00";
            LocalDateTime departure = LocalDateTime.parse(date, formatter);
            FlightRouteEntity flightRoute = flight.getFlightRoute();
            int n=2;
            int timeDifference = flightRoute.getDestination().getTimeZone() - flightRoute.getOrigin().getTimeZone();
            LocalDateTime arrival = departure.plusHours(4 + timeDifference);
            while (arrival.isBefore(endDate) || arrival.isEqual(endDate)){
               flightScheduleList.add(new FlightScheduleEntity(departure,arrival,4));
                departure = departure.plusDays(n);
                arrival = arrival.plusDays(n);
            }
        List<CabinClassConfigurationEntity> cccList = flight.getAircraftConfiguration().getCabinClassConfigurations();
        List<FareEntity> fareList = new ArrayList<FareEntity>();
        fareList.add(new FareEntity(CabinClassType.F, "F001", "3350"));
        fareList.add(new FareEntity(CabinClassType.F, "F002", "3200"));
        fareList.add(new FareEntity(CabinClassType.J, "J001", "1850"));
        fareList.add(new FareEntity(CabinClassType.J, "J002", "1600"));
        fareList.add(new FareEntity(CabinClassType.Y, "Y001", "850"));
        fareList.add(new FareEntity(CabinClassType.Y, "Y002", "600"));


        

        FlightSchedulePlanEntity fspId = flightSchedulePlanSessionBean.createFlightSchedulePlan(fsp, flightScheduleList, fareList, flight);
        
        FlightEntity complementaryFlight = flightSessionBean.retrieveFlightByFlightNumber(flight.getComplementaryFlight().getFlightNumber());
                List<FlightScheduleEntity> complementaryFs = new ArrayList<FlightScheduleEntity>(); 
                FlightSchedulePlanEntity complementaryFsp = new FlightSchedulePlanEntity(complementaryFlight);
                complementaryFsp.setSchedule(fspId.getSchedule());
                for(FlightScheduleEntity fs : fspId.getFlightSchedules()) 
                {
                    departure= fs.getArrival().plusHours(4);
                    int flightDuration = fs.getDuration();
                    complementaryFs.add(new FlightScheduleEntity(departure, departure.plusHours(flightDuration), flightDuration));
                }
                 FlightSchedulePlanEntity complementaryFspId = flightSchedulePlanSessionBean.createFlightSchedulePlan(complementaryFsp, complementaryFs, fareList,complementaryFlight);
        
            }catch(FlightNotFoundException ex){
                    
                    }
          
          try{
            FlightSchedulePlanEntity fsp = new FlightSchedulePlanEntity(flightSessionBean.retrieveFlightByFlightNumber("ML511"), ScheduleEnum.MULTIPLE);
            List<FlightScheduleEntity> flightScheduleList = new ArrayList<FlightScheduleEntity>();
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            FlightEntity flight = flightSessionBean.retrieveFlightByFlightNumber("ML511");
            String date = "2020-12-07 17:00";
           LocalDateTime dateTime = LocalDateTime.parse(date, formatter);     
         FlightRouteEntity flightRoute = flight.getFlightRoute();
         int timeDifference = flightRoute.getDestination().getTimeZone() - flightRoute.getOrigin().getTimeZone();
         LocalDateTime arrival = dateTime.plusHours(3+timeDifference);
         flightScheduleList.add(new FlightScheduleEntity(dateTime,arrival,3));
         
           date = "2020-12-08 17:00";
           dateTime = LocalDateTime.parse(date, formatter);     
        flightRoute = flight.getFlightRoute();
          timeDifference = flightRoute.getDestination().getTimeZone() - flightRoute.getOrigin().getTimeZone();
          arrival = dateTime.plusHours(3+timeDifference);
         flightScheduleList.add(new FlightScheduleEntity(dateTime,arrival,3));
         
        date = "2020-12-09 17:00";
           dateTime = LocalDateTime.parse(date, formatter);     
        flightRoute = flight.getFlightRoute();
          timeDifference = flightRoute.getDestination().getTimeZone() - flightRoute.getOrigin().getTimeZone();
          arrival = dateTime.plusHours(3+timeDifference);
         flightScheduleList.add(new FlightScheduleEntity(dateTime,arrival,3));
     
            List<CabinClassConfigurationEntity> cccList = flight.getAircraftConfiguration().getCabinClassConfigurations();
        List<FareEntity> fareList = new ArrayList<FareEntity>();
        fareList.add(new FareEntity(CabinClassType.F, "F001", "3150"));
        fareList.add(new FareEntity(CabinClassType.F, "F002", "2850"));
        fareList.add(new FareEntity(CabinClassType.J, "J001", "1600"));
        fareList.add(new FareEntity(CabinClassType.J, "J002", "1600"));
        fareList.add(new FareEntity(CabinClassType.Y, "Y001", "600"));
        fareList.add(new FareEntity(CabinClassType.Y, "Y002", "650"));


        

        FlightSchedulePlanEntity fspId = flightSchedulePlanSessionBean.createFlightSchedulePlan(fsp, flightScheduleList, fareList, flight);
        
        FlightEntity complementaryFlight = flightSessionBean.retrieveFlightByFlightNumber(flight.getComplementaryFlight().getFlightNumber());
                List<FlightScheduleEntity> complementaryFs = new ArrayList<FlightScheduleEntity>(); 
                FlightSchedulePlanEntity complementaryFsp = new FlightSchedulePlanEntity(complementaryFlight);
                complementaryFsp.setSchedule(fspId.getSchedule());
                for(FlightScheduleEntity fs : fspId.getFlightSchedules()) 
                {
                    dateTime= fs.getArrival().plusHours(4);
                    int flightDuration = fs.getDuration();
                    complementaryFs.add(new FlightScheduleEntity(dateTime, dateTime.plusHours(flightDuration), flightDuration));
                }
                 FlightSchedulePlanEntity complementaryFspId = flightSchedulePlanSessionBean.createFlightSchedulePlan(complementaryFsp, complementaryFs, fareList,complementaryFlight);
        
            }catch(FlightNotFoundException ex){
                          
                    }
      
            
    }

}
//        EmployeeEntity e1 = new EmployeeEntity("L", "J", "123", "123", EmployeeAccessRight.FLEETMANAGER);
//        em.persist(e1);
//        em.flush();
//        
//        EmployeeEntity e2 = new EmployeeEntity("L", "J", "111", "333", EmployeeAccessRight.SCHEDULEMANAGER);
//        em.persist(e2);
//        em.flush();
//        
//        EmployeeEntity e3 = new EmployeeEntity("JX", "Q", "qjx", "456", EmployeeAccessRight.SCHEDULEMANAGER);
//        em.persist(e3);
//        em.flush();
//        
//        EmployeeEntity e4 = new EmployeeEntity("JX", "Q", "jx", "333", EmployeeAccessRight.SALESMANAGER);
//        em.persist(e4);
//        em.flush();
//        
//        AircraftTypeEntity ate= new AircraftTypeEntity("Boeing", 100);
//        em.persist(ate);
//         em.flush();
//
//         //String name, String code, String city, String state, String country
//         AirportEntity a = new  AirportEntity("Narita", "NRT", "Narita", "Chiba", "Japan", 9);
//             em.persist(a);
//              em.flush();
//             AirportEntity a1 = new  AirportEntity("Changi", "SIN", "Singapore", "Singapore", "Singapore", 8);
//             em.persist(a1);
//              em.flush();
//             AirportEntity a2  = new  AirportEntity("Taoyuan", "TPE", "Taoyuan", "Taipei", "Taiwan R.O.C.", 8);
//             em.persist(a2);
//              em.flush();

/*
String now = "2020-11-09 10:30";
DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
LocalDateTime d = LocalDateTime.parse(now, formatter);
String now1 = "2020-12-09 10:30";
LocalDateTime d1 = LocalDateTime.parse(now1, formatter);
 */
 /*
FlightSchedulePlanEntity fsp = new FlightSchedulePlanEntity();
em.persist(fsp);
em.flush();
FlightScheduleEntity fs= new FlightScheduleEntity(d,d1,3);
fs.setFlightSchedulePlan(fsp);
fsp.getFlightSchedules().add(fs);

em.persist(fs);
em.flush();
 */
 /*
FlightSchedulePlanEntity fsp = new FlightSchedulePlanEntity();
em.persist(fsp);
em.flush();
FlightScheduleEntity fs= new FlightScheduleEntity(d,d1,3);
em.persist(fs);
fs.setFlightSchedulePlan(fsp);
em.flush();
fsp.getFlightSchedules().add(fs);

FlightSchedulePlanEntity fsp1 = new FlightSchedulePlanEntity();
em.persist(fsp1);
em.flush();
FlightScheduleEntity fs1= new FlightScheduleEntity(d,d1,3);
em.persist(fs1);
fs1.setFlightSchedulePlan(fsp1);
em.flush();
fsp1.getFlightSchedules().add(fs1);


FlightRouteEntity fr = new FlightRouteEntity(a1, a2);
em.persist(fr);
em.flush();
FlightRouteEntity fr1 = new FlightRouteEntity(a, a2);
em.persist(fr1);
em.flush();
FlightRouteEntity fr2 = new FlightRouteEntity(a, a1);
em.persist(fr2);
em.flush();

FlightEntity  f = new FlightEntity("MA11", fr);
em.persist(f);
em.flush();
FlightEntity  f1 = new FlightEntity("MA12", fr1);
em.persist(f1);
em.flush();
FlightEntity  f2 = new FlightEntity("MA13", fr2);
em.persist(f2);
em.flush();

fr.getFlights().add(f);
fr1.getFlights().add(f1);
fr2.getFlights().add(f2);

fsp.setFlight(f);
f.getFlightSchedulePlans().add(fsp);

// fsp1.setFlight(f2);
//f2.getFlightSchedulePlans().add(fsp1);



//            f.getScheduledFlights().add(fsp);
//            CustomerEntity customerEntity1 = new CustomerEntity("One", "Customer", "customer1@gmail.com", "password");
//            em.persist(customerEntity1);
//            CustomerEntity customerEntity2 = new CustomerEntity("Two", "Customer", "customer2@gmail.com", "password");
//            em.persist(customerEntity2);
//            CustomerEntity customerEntity3 = new CustomerEntity("Three", "Customer", "customer3@gmail.com", "password");
//            em.persist(customerEntity3);


 */
