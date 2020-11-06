/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AircraftConfigurationEntity;
import entity.CabinClassConfigurationEntity;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.AircraftConfigurationNotFoundException;
import util.exception.CabinClassConfigurationNotFoundException;

/**
 *
 * @author quahjingxin
 */
@Stateless
public class CabinClassConfigurationSessionBean implements CabinClassConfigurationSessionBeanRemote, CabinClassConfigurationSessionBeanLocal {

    @EJB
    private AircraftConfigurationSessionBeanLocal aircraftConfigurationSessionBeanLocal;

    @PersistenceContext(unitName = "MerlionAirApplication-ejbPU")
    private EntityManager em;

    public CabinClassConfigurationEntity createNewCabinClassConfiguration(CabinClassConfigurationEntity cabinClassConfigurationEntity, Long aircraftConfigurationId) throws AircraftConfigurationNotFoundException
    {
        try 
        {
            AircraftConfigurationEntity aircraftConfigurationEntity = aircraftConfigurationSessionBeanLocal.retrieveAircraftConfigurationByAircraftConfigurationId(aircraftConfigurationId);
        
            em.persist(cabinClassConfigurationEntity);
            
            cabinClassConfigurationEntity.setAircraftConfiguration(aircraftConfigurationEntity);
            aircraftConfigurationEntity.getCabinClassConfigurations().add(cabinClassConfigurationEntity);
                    
            em.flush();
         
            return cabinClassConfigurationEntity;
        }
        catch(AircraftConfigurationNotFoundException ex)
        {
            throw new AircraftConfigurationNotFoundException("Unable to create new cabin class configuration as the aircraft configuration record does not exist");
        }
    }
            
    public CabinClassConfigurationEntity retrieveCabinClassConfigurationByCabinClassConfigurationId(Long cabinClassConfigurationId) throws CabinClassConfigurationNotFoundException
    {
        
        CabinClassConfigurationEntity cabinClassConfigurationEntity = em.find(CabinClassConfigurationEntity.class, cabinClassConfigurationId);
        
        if(cabinClassConfigurationId != null)
        {
            return cabinClassConfigurationEntity;
        }
        else
        {
            throw new CabinClassConfigurationNotFoundException("Cabin Class Configuration ID " + cabinClassConfigurationId + " does not exist!");
        }   
        
    }
    

    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
