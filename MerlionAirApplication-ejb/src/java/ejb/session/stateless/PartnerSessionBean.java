/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.PartnerEntity;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.PartnerNotFoundException;

/**
 *
 * @author quahjingxin
 */
@Stateless
public class PartnerSessionBean implements PartnerSessionBeanRemote, PartnerSessionBeanLocal {

    @PersistenceContext(unitName = "MerlionAirApplication-ejbPU")
    private EntityManager em;

    
    
   public Long createNewPartner(PartnerEntity newPartnerEntity)
   {
       em.persist(newPartnerEntity);
       em.flush();
       
       return newPartnerEntity.getPartnerId();
   }
   
   @Override
   public PartnerEntity retrievePartnerByParnterId(Long partnerId) throws PartnerNotFoundException
   {
        PartnerEntity partnerEntity = em.find(PartnerEntity.class, partnerId);
        
        if(partnerEntity != null)
        {
            return partnerEntity;
        }
        else
        {
            throw new PartnerNotFoundException("Partner ID " + partnerId + " does not exist!");
        }
   }
   
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

}
