/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AircraftTypeEntity;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.AircraftTypeNotFoundException;

/**
 *
 * @author quahjingxin
 */
@Stateless
public class AircraftTypeSessionBean implements AircraftTypeSessionBeanRemote, AircraftTypeSessionBeanLocal {

    @PersistenceContext(unitName = "MerlionAirApplication-ejbPU")
    private EntityManager em;

   public Long createNewAircraftType(AircraftTypeEntity newAircraftTypeEntity)
   {
       em.persist(newAircraftTypeEntity);
       em.flush();
       
       return newAircraftTypeEntity.getAircraftTypeId();
   }
   
   @Override
   public AircraftTypeEntity retrieveAircraftTypeByAircraftTypeId(Long aircraftTypeId) throws AircraftTypeNotFoundException
   {
        AircraftTypeEntity aircraftTypeEntity = em.find(AircraftTypeEntity.class, aircraftTypeId);
        
        if(aircraftTypeEntity != null)
        {
            return aircraftTypeEntity;
        }
        else
        {
            throw new AircraftTypeNotFoundException("Airport ID " + aircraftTypeId + " does not exist!");
        }
   }


    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
