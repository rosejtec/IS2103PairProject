/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frsreservationclient;

import ejb.session.stateless.CustomerSessionBeanRemote;
import ejb.session.stateless.FlightReservationSessionBeanRemote;
import java.text.ParseException;
import javax.ejb.EJB;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author leahr
 */
public class Main {

    @EJB
    private static FlightReservationSessionBeanRemote flightReservationSessionBean;

    @EJB
    private static CustomerSessionBeanRemote customerSessionBean;

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        MainApp ma= new MainApp(flightReservationSessionBean,customerSessionBean);
        ma.runApp();
    }
    
}
