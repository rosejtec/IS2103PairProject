/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AircraftConfigurationEntity;
import entity.CabinClassConfigurationEntity;
import java.util.List;
import javax.ejb.Local;
import util.exception.AircraftConfigurationNotFoundException;
import util.exception.AircraftTypeNotFoundException;

/**
 *
 * @author quahjingxin
 */
@Local
public interface AircraftConfigurationSessionBeanLocal {
    
    public List<AircraftConfigurationEntity> retrieveAllAircraftConfigurations();
    public AircraftConfigurationEntity retrieveAircraftConfigurationByAircraftConfigurationId(Long aircraftConfigurationId) throws AircraftConfigurationNotFoundException;

        public AircraftConfigurationEntity createNewAircraftConfiguration(AircraftConfigurationEntity aircraftConfigurationEntity,List<CabinClassConfigurationEntity> newCabinClassConfiguration, Long aircraftTypeId) throws AircraftTypeNotFoundException, AircraftConfigurationNotFoundException;

}
