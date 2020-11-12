/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.ws;

import ejb.session.stateless.AirportSessionBeanLocal;
import entity.AirportEntity;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author leahr
 */
@WebService(serviceName = "AirportEntityService")
@Stateless
public class AirportEntityService {

    @PersistenceContext(unitName = "MerlionAirApplication-ejbPU")
    private EntityManager em;


    
   
    private AirportSessionBeanLocal airportSessionBean;

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "retrieveAiport")
    public AirportEntity retrieveAiport(@WebParam(name = "name") String txt) {
       
        AirportEntity a = airportSessionBean.retriveBy(txt);
        em.detach(a.getFlightRoutesInbound());
        em.detach(a.getFlightRoutesOutbound());
        return a;
    }
    
    @WebMethod(operationName = "hello")
     public String sayHello() {
        return("Hello from first EJB3.0 Web Service");
    }

    
}
