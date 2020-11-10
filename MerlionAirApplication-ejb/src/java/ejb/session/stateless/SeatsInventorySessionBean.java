/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.FlightScheduleEntity;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.FlightRouteNotFoundException;
import util.exception.FlightScheduleNotFoundException;
import util.exception.FlightScheduleNotFoundException;

/**
 *
 * @author quahjingxin
 */
@Stateless
public class SeatsInventorySessionBean implements SeatsInventorySessionBeanRemote, SeatsInventorySessionBeanLocal {

    @PersistenceContext(unitName = "MerlionAirApplication-ejbPU")
    private EntityManager em;

    public FlightScheduleEntity retrieveFlightScheduleByFlightScheduleId(Long flightScheduleId) throws FlightScheduleNotFoundException
    {
        
        FlightScheduleEntity flightScheduleEntity = em.find(FlightScheduleEntity.class, flightScheduleId);
        if(flightScheduleEntity != null)
        {
            return flightScheduleEntity;
        }
        else
        {
            throw new FlightScheduleNotFoundException("Flight Schedule ID " + flightScheduleId + " does not exist!");
        }   
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
