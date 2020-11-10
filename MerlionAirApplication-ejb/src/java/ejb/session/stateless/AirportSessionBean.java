/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AirportEntity;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.AirportNotFoundException;

/**
 *
 * @author leahr
 */
@Stateless
public class AirportSessionBean implements AirportSessionBeanRemote, AirportSessionBeanLocal {

    @PersistenceContext(unitName = "MerlionAirApplication-ejbPU")
    private EntityManager em;

    public void persist(Object object) {
        em.persist(object);
    }

 
    @Override
    public AirportEntity retriveBy(String code){
        Query q = em.createQuery("SELECT a FROM AirportEntity a WHERE a.code = :inCode");
        q.setParameter("inCode", code);
        AirportEntity a = (AirportEntity) q.getSingleResult();
        return a;
    }

    @Override
   public Long createNewAirport(AirportEntity newAirportEntity)
   {
       em.persist(newAirportEntity);
       em.flush();
       
       return newAirportEntity.getAirportId();
   }
   
   @Override
   public AirportEntity retrieveAirportByAirportId(Long airportId) throws AirportNotFoundException
   {
        AirportEntity airportEntity = em.find(AirportEntity.class, airportId);
        
        if(airportEntity != null)
        {
            return airportEntity;
        }
        else
        {
            throw new AirportNotFoundException("Airport ID " + airportId + " does not exist!");
        }
   }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
