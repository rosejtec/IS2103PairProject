/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AircraftTypeEntity;
import entity.AirportEntity;
import javax.ejb.Local;
import util.exception.AircraftTypeNotFoundException;

/**
 *
 * @author quahjingxin
 */
@Local
public interface AircraftTypeSessionBeanLocal {
    
   
    public Long createNewAircraftType(AircraftTypeEntity newAircraftTypeEntity);

    public AircraftTypeEntity retrieveAircraftTypeByAircraftTypeId(Long aircraftTypeId) throws AircraftTypeNotFoundException;
}
