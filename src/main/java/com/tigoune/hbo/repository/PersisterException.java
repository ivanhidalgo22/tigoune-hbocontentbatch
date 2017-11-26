/**
 * PersisterException.java
 *
 * @description: registers the data access exceptions in the database.
 * @author IVAN HIDALGO.
 * @version 1.0
 * @date 10-10-2017.
 *
 **/

package com.tigoune.hbo.repository;

/**
 * Registers the data access exceptions in the database.
 * @author IVAN HIDALGO.
 */
public class PersisterException extends Exception {
    
    /**
	 * default serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
     * registers a data access exception.
     * @param message 
     */
    public PersisterException(String message) {
        super(message);
    }
}
