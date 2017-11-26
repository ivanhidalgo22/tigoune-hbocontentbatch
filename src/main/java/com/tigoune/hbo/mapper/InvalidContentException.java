package com.tigoune.hbo.mapper;

public class InvalidContentException extends Exception {

	/**
	 * Default serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
     * registers a data access exception.
     * @param message error message.
     */
    public InvalidContentException(String message) {
        super(message);
    }

}
