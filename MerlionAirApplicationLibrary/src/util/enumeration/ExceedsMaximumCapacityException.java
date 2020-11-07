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
public class ExceedsMaximumCapacityException extends Exception {

    /**
     * Creates a new instance of <code>ExceedsMaximumCapacityException</code>
     * without detail message.
     */
    public ExceedsMaximumCapacityException() {
    }

    /**
     * Constructs an instance of <code>ExceedsMaximumCapacityException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public ExceedsMaximumCapacityException(String msg) {
        super(msg);
    }
}
