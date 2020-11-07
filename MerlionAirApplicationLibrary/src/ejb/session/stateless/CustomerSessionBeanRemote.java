/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CustomerEntity;
import javax.ejb.Remote;
import util.exception.CustomerNotFoundException;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author leahr
 */
@Remote
public interface CustomerSessionBeanRemote {

    public CustomerEntity login(String username, String password) throws InvalidLoginCredentialException;
     public CustomerEntity retrieveCustomerById(Long customerId) throws CustomerNotFoundException;

    public CustomerEntity createNewCustomer(CustomerEntity customer);
    
}
