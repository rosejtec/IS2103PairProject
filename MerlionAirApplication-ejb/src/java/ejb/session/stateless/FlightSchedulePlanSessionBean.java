/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.FlightScheduleEntity;
import entity.FlightSchedulePlanEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author quahjingxin
 */
@Stateless
public class FlightSchedulePlanSessionBean implements FlightSchedulePlanSessionBeanRemote, FlightSchedulePlanSessionBeanLocal {

    @PersistenceContext(unitName = "MerlionAirApplication-ejbPU")
    private EntityManager em;

    
    public void createFlightSchedulePlan(FlightSchedulePlanEntity fsp, List<FlightScheduleEntity> fs){
        em.persist(fsp);
        em.flush();
        
        for(int i = 0; i< fs.size(); i++){
            FlightScheduleEntity f=fs.get(i);
            em.persist(f);
            em.flush();
            fsp.getFlightSchedules().add(f);
            f.setFlightSchedulePlan(fsp);
        }
        
        
        em.flush();
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
