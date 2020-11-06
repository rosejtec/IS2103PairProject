/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CustomerEntity;
import javax.ejb.Local;
import util.exception.CustomerNotFoundException;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author leahr
 */
@Local
public interface CustomerSessionBeanLocal {

    public CustomerEntity retrieveCustomerById(Long customerId) throws CustomerNotFoundException;
public CustomerEntity login(String username, String password) throws InvalidLoginCredentialException;
    public CustomerEntity createNewCustomer(CustomerEntity customer);
    
}
