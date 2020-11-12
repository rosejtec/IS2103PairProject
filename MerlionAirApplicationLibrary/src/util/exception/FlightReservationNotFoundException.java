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
public class FlightReservationNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>FlightReservationNotFoundException</code>
     * without detail message.
     */
    public FlightReservationNotFoundException() {
    }

    /**
     * Constructs an instance of <code>FlightReservationNotFoundException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public FlightReservationNotFoundException(String msg) {
        super(msg);
    }
}
