/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AirportEntity;
import entity.FlightScheduleEntity;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author leahr
 */
@Stateless
public class ReservationSessionBean implements ReservationSessionBeanRemote, ReservationSessionBeanLocal {

    @PersistenceContext(unitName = "MerlionAirApplication-ejbPU")
    private EntityManager em;
//                query = em.createQuery("SELECT f FROM FlightScheduleEntity f JOIN f.plan p JOIN p.flight t JOIN t.flightRoute m WHERE  m.origin.airportId = :inOrg AND m.destination.airportId = :outDes AND  f.departure BETWEEN :inDate AND :inDate1");

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    public FlightScheduleEntity retrieveById(Long id) {
         Query q = em.createQuery("SELECT a FROM FlightScheduleEntity a WHERE a.id = :inName");
        q.setParameter("inName", id);
        FlightScheduleEntity a = (FlightScheduleEntity) q.getSingleResult();
        return a;
    }

    public void persist(Object object) {
        em.persist(object);
    }
}
