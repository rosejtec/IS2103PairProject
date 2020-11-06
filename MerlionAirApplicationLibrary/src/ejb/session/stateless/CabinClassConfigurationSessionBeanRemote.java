/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CabinClassConfigurationEntity;
import javax.ejb.Remote;
import util.exception.CabinClassConfigurationNotFoundException;

/**
 *
 * @author quahjingxin
 */
@Remote
public interface CabinClassConfigurationSessionBeanRemote {
    public CabinClassConfigurationEntity retrieveCabinClassConfigurationByCabinClassConfigurationId(Long cabinClassConfigurationId) throws CabinClassConfigurationNotFoundException;
}
