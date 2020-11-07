/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.FlightScheduleEntity;
import entity.FlightSchedulePlanEntity;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author quahjingxin
 */
@Remote
public interface FlightSchedulePlanSessionBeanRemote {

    public void createFlightSchedulePlan(FlightSchedulePlanEntity fsp, List<FlightScheduleEntity> fs);
    
}
