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
public class AirportNotFoundException extends Exception {
    
    public AirportNotFoundException()
    {
    }
    
    
    
    public AirportNotFoundException(String msg)
    {
        super(msg);
    }
    
}
