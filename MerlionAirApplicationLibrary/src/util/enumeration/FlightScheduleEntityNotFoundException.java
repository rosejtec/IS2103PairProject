/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.enumeration;

/**
 *
 * @author leahr
 */
public class FlightScheduleEntityNotFoundException extends Exception {

    /**
     * Creates a new instance of
     * <code>FlightScheduleEntityNotFoundException</code> without detail
     * message.
     */
    public FlightScheduleEntityNotFoundException() {
    }

    /**
     * Constructs an instance of
     * <code>FlightScheduleEntityNotFoundException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public FlightScheduleEntityNotFoundException(String msg) {
        super(msg);
    }
}
