/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.PartnerEntity;
import javax.ejb.Remote;
import util.exception.PartnerNotFoundException;

/**
 *
 * @author quahjingxin
 */
@Remote
public interface PartnerSessionBeanRemote {

    public Long createNewPartner(PartnerEntity newPartnerEntity);

    public PartnerEntity retrievePartnerByParnterId(Long partnerId) throws PartnerNotFoundException;
    
}
