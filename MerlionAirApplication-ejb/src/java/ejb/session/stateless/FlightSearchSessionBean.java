/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AirportEntity;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author leahr
 */
@Stateless
public class FlightSearchSessionBean implements FlightSearchSessionBeanRemote, FlightSearchSessionBeanLocal {

    @PersistenceContext(unitName = "MerlionAirApplication-ejbPU")
    private EntityManager em;

    public void persist(Object object) {
        em.persist(object);
    }

    void search(boolean connecting,boolean round,AirportEntity origin, AirportEntity destination, Date dep, Date ret, int passengers, String cabinClass) {
        /**
        if(connecting) {
            
            
            
        } else {
            Query query =  em.createQuery("SELECT f FROM FlightScheduleEntity f WHERE f.departure= :inDate AND F.arrival = :outDate,  IN (f.plan) p, IN (p.flight) m WHERE m.origin = :inDate AND m.departure = :inDate ");
           // query.setParameter("inName", username);
            //CustomerEntity customer = (CustomerEntity)query.getSingleResult();
            
            
        }
        
        */
        
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
