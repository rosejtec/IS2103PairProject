/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AirportEntity;
import entity.FareEntity;
import entity.FlightEntity;
import entity.FlightScheduleEntity;
import entity.FlightSchedulePlanEntity;
import java.util.List;
import javax.ejb.Remote;
import util.exception.AirportNotFoundException;
import util.exception.FareNotFoundException;
import util.exception.FlightScheduleNotFoundException;
import util.exception.FlightSchedulePlanNotFoundException;

/**
 *
 * @author quahjingxin
 */
@Remote
public interface FlightSchedulePlanSessionBeanRemote {

    public FlightSchedulePlanEntity createFlightSchedulePlan(FlightSchedulePlanEntity fsp, List<FlightScheduleEntity> fs, List<FareEntity> f,FlightEntity flight);
    
    public FlightSchedulePlanEntity retrieveFlightSchedulePlanById(Long flightSchedulePlanId) throws FlightSchedulePlanNotFoundException;

    public List<FlightSchedulePlanEntity> retrieveAllFlightSchedulePlans();

    public void deleteFlightSchedulePlan(Long flightSchedulePlanId) throws FlightSchedulePlanNotFoundException;

    public void deleteFlightSchedule(Long flightSchedulePlanId, Long flightScheduleId) throws FlightSchedulePlanNotFoundException, FlightScheduleNotFoundException;

    public FareEntity createNewFare(FareEntity fareEntity);

    public FlightScheduleEntity createNewFlightSchedule(FlightScheduleEntity flightScheduleEntity);

    public FlightScheduleEntity retrieveFlightScheduleById(Long fsId) throws FlightScheduleNotFoundException;

    public FareEntity retrieveFareByFareId(Long fareId) throws FareNotFoundException;

    public void updateFare(FareEntity fare) throws FareNotFoundException;

    public FlightSchedulePlanEntity createCompFlightSchedulePlan(FlightSchedulePlanEntity fsp, FlightSchedulePlanEntity original, List<FlightScheduleEntity> fs, List<FareEntity> f, FlightEntity flight);

    public AirportEntity retriveBy(String code) throws AirportNotFoundException;
    
}
