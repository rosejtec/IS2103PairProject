/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.PartnerEntity;
import javax.ejb.Local;
import util.exception.PartnerNotFoundException;

/**
 *
 * @author quahjingxin
 */
@Local
public interface PartnerSessionBeanLocal {
 
    public Long createNewPartner(PartnerEntity newPartnerEntity);

    public PartnerEntity retrievePartnerByParnterId(Long partnerId) throws PartnerNotFoundException;
}
