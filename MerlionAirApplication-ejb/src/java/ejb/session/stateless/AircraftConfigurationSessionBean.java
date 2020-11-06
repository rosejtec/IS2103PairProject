/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AircraftConfigurationEntity;
import entity.AircraftTypeEntity;
import entity.CabinClassConfigurationEntity;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.AircraftConfigurationNotFoundException;
import util.exception.AircraftTypeNotFoundException;

/**
 *
 * @author quahjingxin
 */
@Stateless
public class AircraftConfigurationSessionBean implements AircraftConfigurationSessionBeanRemote, AircraftConfigurationSessionBeanLocal {

    //do i need call the relationship for ccc?
    @EJB
    private CabinClassConfigurationSessionBeanLocal cabinClassConfigurationSessionBeanLocal;

    @EJB
    private AircraftTypeSessionBeanLocal aircraftTypeSessionBeanLocal;

    @PersistenceContext(unitName = "MerlionAirApplication-ejbPU")
    private EntityManager em;
    
    //7. create aircraftconfiguration for particular aircraft type use case
    public AircraftConfigurationEntity createNewAircraftConfiguration(AircraftConfigurationEntity aircraftConfigurationEntity, Long aircraftTypeId) throws AircraftTypeNotFoundException
    {
        try 
        {
            AircraftTypeEntity aircraftTypeEntity = aircraftTypeSessionBeanLocal.retrieveAircraftTypeByAircraftTypeId(aircraftTypeId);
        
            em.persist(aircraftConfigurationEntity);
            
            aircraftConfigurationEntity.setAircraftType(aircraftTypeEntity);
            aircraftTypeEntity.getAircraftConfigurations().add(aircraftConfigurationEntity);
                    
            em.flush();
         
            return aircraftConfigurationEntity;
        }
        catch(AircraftTypeNotFoundException ex)
        {
            throw new AircraftTypeNotFoundException("Unable to create new aircraft configuration as the aircraft type record does not exist");
        }
    }
    
    //view all aircraft configuration use case
    @Override
    public List<AircraftConfigurationEntity> retrieveAllAircraftConfigurations()
    {
        Query query = em.createQuery("SELECT ac FROM AircraftConfigurationEntity ac");
        
        return query.getResultList();
    }
    
    //view aircraft configuration details use case
    
    public AircraftConfigurationEntity retrieveAircraftConfigurationByAircraftConfigurationId(Long aircraftConfigurationId) throws AircraftConfigurationNotFoundException
    {
        
        AircraftConfigurationEntity aircraftConfigurationEntity = em.find(AircraftConfigurationEntity.class, aircraftConfigurationId);
        
        if(aircraftConfigurationEntity != null)
        {
            return aircraftConfigurationEntity;
        }
        else
        {
            throw new AircraftConfigurationNotFoundException("Aircraft Configuration ID " + aircraftConfigurationId + " does not exist!");
        }   
        
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
