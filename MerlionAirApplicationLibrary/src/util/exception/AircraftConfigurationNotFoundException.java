/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author quahjingxin
 */

public class AircraftConfigurationNotFoundException extends Exception {
    
    public AircraftConfigurationNotFoundException()
    {
    }
    
    
    
    public AircraftConfigurationNotFoundException(String msg)
    {
        super(msg);
    }
}