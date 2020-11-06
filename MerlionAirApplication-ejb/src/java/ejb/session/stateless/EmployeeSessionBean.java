/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.EmployeeEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.EmployeeNotFoundException;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author quahjingxin
 */
@Stateless
public class EmployeeSessionBean implements EmployeeSessionBeanRemote, EmployeeSessionBeanLocal {

   @PersistenceContext(unitName = "MerlionAirApplication-ejbPU")
   private EntityManager em;

   public EmployeeSessionBean() 
   {
   }
   
   public Long createNewEmployee(EmployeeEntity newEmployeeEntity)
   {
       em.persist(newEmployeeEntity);
       em.flush();
       
       return newEmployeeEntity.getEmployeeId();
   }
   
   @Override
   public List<EmployeeEntity> retrieveAllEmployee()
   {
       Query query = em.createQuery("SELECT e FROM EmployeeEntity e");
        
        return query.getResultList();
   }
   
   @Override
    public EmployeeEntity retrieveEmployeeByEmployeeId(Long employeeId) throws EmployeeNotFoundException
    {
        EmployeeEntity employeeEntity = em.find(EmployeeEntity.class, employeeId);
        
        if(employeeEntity != null)
        {
            return employeeEntity;
        }
        else
        {
            throw new EmployeeNotFoundException("Employee ID " + employeeId + " does not exist!");
        }
    }
    
    
    
    @Override
    public EmployeeEntity retrieveEmployeeByUsername(String username) throws EmployeeNotFoundException
    {
        Query query = em.createQuery("SELECT e FROM EmployeeEntity e WHERE e.username = :inUsername");
        query.setParameter("inUsername", username);
        
        try
        {
            return (EmployeeEntity)query.getSingleResult();
        }
        catch(NoResultException | NonUniqueResultException ex)
        {
            throw new EmployeeNotFoundException("Employee Username " + username + " does not exist!");
        }
    }
    
    
    
    @Override
    public EmployeeEntity employeeLogin(String username, String password) throws InvalidLoginCredentialException
    {
        try
        {
            EmployeeEntity employeeEntity = retrieveEmployeeByUsername(username);
            
            if(employeeEntity.getPassword().equals(password))
            {               
                return employeeEntity;
            }
            else
            {
                throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
            }
        }
        catch(EmployeeNotFoundException ex)
        {
            throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
        }
    }
    
    
    
    @Override
    public void updateEmployee(EmployeeEntity employeeEntity)
    {
        em.merge(employeeEntity);
    }
    
    
    
    @Override
    public void deleteEmployee(Long employeeId) throws EmployeeNotFoundException
    {
        EmployeeEntity employeeEntityToRemove = retrieveEmployeeByEmployeeId(employeeId);
        em.remove(employeeEntityToRemove);
    }
    
    
   
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
