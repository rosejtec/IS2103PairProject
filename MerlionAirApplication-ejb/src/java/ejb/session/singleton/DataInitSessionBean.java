/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.AircraftTypeSessionBeanLocal;
import ejb.session.stateless.AirportSessionBeanLocal;
import ejb.session.stateless.EmployeeSessionBeanLocal;
import ejb.session.stateless.PartnerSessionBeanLocal;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import util.exception.EmployeeNotFoundException;

/**
 *
 * @author leahr
 */
@Singleton
@LocalBean
@Startup

public class DataInitSessionBean {

    @EJB
    private AirportSessionBeanLocal airportSessionBeanLocal;

    @EJB
    private PartnerSessionBeanLocal partnerSessionBeanLocal;

    @EJB
    private EmployeeSessionBeanLocal employeeSessionBeanLocal;

    @EJB
    private AircraftTypeSessionBeanLocal aircraftTypeSessionBeanLocal;

    @PostConstruct
    public void postConstruct()
    {
        try
        {
            employeeSessionBeanLocal.retrieveEmployeeByEmployeeId(1l);
        }
        catch(EmployeeNotFoundException ex)
        {
            initializeData();
        }
    }
    
    public DataInitSessionBean() 
    {
    }
    
    private void initializeData() 
    {
        
    }
     
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
