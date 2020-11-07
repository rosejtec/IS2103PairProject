/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AircraftTypeEntity;
import javax.ejb.Remote;
import util.exception.AircraftTypeNotFoundException;

/**
 *
 * @author quahjingxin
 */
@Remote
public interface AircraftTypeSessionBeanRemote {

    public Long createNewAircraftType(AircraftTypeEntity newAircraftTypeEntity);

    public AircraftTypeEntity retrieveAircraftTypeByAircraftTypeId(Long aircraftTypeId) throws AircraftTypeNotFoundException;
    
}