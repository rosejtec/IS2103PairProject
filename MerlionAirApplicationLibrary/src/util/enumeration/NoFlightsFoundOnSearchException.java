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
public class NoFlightsFoundOnSearchException extends Exception {

    /**
     * Creates a new instance of <code>NoFlightsFoundOnSearchException</code>
     * without detail message.
     */
    public NoFlightsFoundOnSearchException() {
    }

    /**
     * Constructs an instance of <code>NoFlightsFoundOnSearchException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public NoFlightsFoundOnSearchException(String msg) {
        super(msg);
    }
}
