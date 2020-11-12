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
import javax.ejb.Local;

/**
 *
 * @author quahjingxin
 */
@Local
public interface FlightSchedulePlanSessionBeanLocal {
       public FlightSchedulePlanEntity createFlightSchedulePlan(FlightSchedulePlanEntity fsp, List<FlightScheduleEntity> fs, List<FareEntity> f,FlightEntity flight) ;
}
