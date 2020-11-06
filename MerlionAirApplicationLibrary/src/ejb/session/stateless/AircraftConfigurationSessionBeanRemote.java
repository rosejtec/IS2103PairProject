/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AircraftConfigurationEntity;
import java.util.List;
import javax.ejb.Remote;
import util.exception.AircraftConfigurationNotFoundException;
import util.exception.AircraftTypeNotFoundException;

/**
 *
 * @author quahjingxin
 */
@Remote
public interface AircraftConfigurationSessionBeanRemote {

    public AircraftConfigurationEntity createNewAircraftConfiguration(AircraftConfigurationEntity aircraftConfigurationEntity, Long aircraftTypeId) throws AircraftTypeNotFoundException;
    public List<AircraftConfigurationEntity> retrieveAllAircraftConfigurations();
    public AircraftConfigurationEntity retrieveAircraftConfigurationByAircraftConfigurationId(Long aircraftConfigurationId) throws AircraftConfigurationNotFoundException;
    
}
