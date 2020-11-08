/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.FareEntity;
import entity.FlightEntity;
import entity.FlightScheduleEntity;
import entity.FlightSchedulePlanEntity;
import java.util.List;
import javax.ejb.Remote;
import util.exception.FlightScheduleNotFoundException;
import util.exception.FlightSchedulePlanNotFoundException;

/**
 *
 * @author quahjingxin
 */
@Remote
public interface FlightSchedulePlanSessionBeanRemote {

    public Long createFlightSchedulePlan(FlightSchedulePlanEntity fsp, FlightEntity flightEntity, List<FlightScheduleEntity> fs, List<FareEntity> fares);
    
    public FlightSchedulePlanEntity retrieveFlightSchedulePlanById(Long flightSchedulePlanId) throws FlightSchedulePlanNotFoundException;

    public List<FlightSchedulePlanEntity> retrieveAllFlightSchedulePlans();

    public void deleteFlightSchedulePlan(Long flightSchedulePlanId) throws FlightSchedulePlanNotFoundException;

    public void deleteFlightSchedule(Long flightSchedulePlanId, Long flightScheduleId) throws FlightSchedulePlanNotFoundException, FlightScheduleNotFoundException;
    
}