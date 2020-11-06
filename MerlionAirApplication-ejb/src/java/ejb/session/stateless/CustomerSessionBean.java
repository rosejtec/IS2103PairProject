/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CustomerEntity;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.CustomerNotFoundException;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author leahr
 */
@Stateless
public class CustomerSessionBean implements CustomerSessionBeanRemote, CustomerSessionBeanLocal {


    @PersistenceContext(unitName = "MerlionAirApplication-ejbPU")
    private EntityManager em;

    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    /**
     private List<Customer> customers;

    
    
    public CustomerSessionBean() 
    {
        customers = new ArrayList<>();
    }
    
    
    
    @PostConstruct
    public void postConstruct()
    {
        customers.add(new Customer(1l, "Customer One", "customer1@gmail.com", "password"));
        customers.add(new Customer(2l, "Customer Two", "customer2@gmail.com", "password"));
        customers.add(new Customer(3l, "Customer Three", "customer3@gmail.com", "password"));
    }
    
    
    
    @PreDestroy
    public void preDestroy()
    {
        if(customers != null)
        {
            customers.clear();
            customers = null;
        }
    }
    
    */
    
    public CustomerEntity retrieveCustomerById(Long customerId) throws CustomerNotFoundException
    {
        try
        {
            CustomerEntity customer = em.find(CustomerEntity.class, customerId);
               return customer;
        
        }
        catch(NoResultException ex)
        {
            throw new CustomerNotFoundException("Customer does not exist: " + customerId);
        }
        
        
        
    }
    
    
    
     public CustomerEntity createNewCustomer(CustomerEntity customer)
    {
        //check if customer already exists
        em.persist(customer);
        em.flush();
        
        return customer;
    }
    
   
    
    
    public CustomerEntity login(String username, String password) throws InvalidLoginCredentialException
    {
        try
        {
            Query query = em.createQuery("SELECT c FROM CustomerEntity c WHERE c.username = :inName");
            query.setParameter("inName", username);
            CustomerEntity customer = (CustomerEntity)query.getSingleResult();
            
            if(customer.getPassword().equals(password))
            {
                return customer;
            }
            else
            {
                throw new InvalidLoginCredentialException("Invalid login credential");
            }
        }
        catch(NoResultException ex)
        {
            throw new InvalidLoginCredentialException("Invalid login credential");
        }
    }
    
    

    public void persist(Object object) {
        em.persist(object);
    }
}