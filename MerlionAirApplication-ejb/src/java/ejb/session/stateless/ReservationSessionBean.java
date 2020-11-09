/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.FareEntity;
import entity.FlightScheduleEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.enumeration.CabinClassType;

/**
 *
 * @author quahjingxin
 */
@Stateless
public class ReservationSessionBean implements ReservationSessionBeanRemote, ReservationSessionBeanLocal {

    @PersistenceContext(unitName = "MerlionAirApplication-ejbPU")
    private EntityManager em;

    public void persist(Object object) {
        em.persist(object);
    }

    public Integer getFare(FlightScheduleEntity fs, CabinClassType t){
        
        Query q= em.createQuery("SELECT f FROM FareEntity f WHERE f.flightSchedulePlan.fightSchedulePlanId=:t  AND f.cabinClassConfiguration.cabinClassType=:f ORDER BY f.fareAmount ASC");
        q.setParameter("t",fs.getFlightSchedulePlan().getFightSchedulePlanId() ); 
        q.setParameter("f", t);
        
        List<FareEntity> fare =  q.getResultList();
        if(fare.size()>0){
        return Integer.parseInt(fare.get(0).getFareAmount());
        } else {
            return -1;
        }
    }
        
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
