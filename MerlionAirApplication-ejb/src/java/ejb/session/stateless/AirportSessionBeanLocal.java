/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AirportEntity;
import javax.ejb.Local;
import util.exception.AirportNotFoundException;


@Local
public interface AirportSessionBeanLocal {
    
    public Long createNewAirport(AirportEntity newAirportEntity);

    public AirportEntity retrieveAirportByAirportId(Long airportId) throws AirportNotFoundException;

    public Long retriveBy(String name);
}
