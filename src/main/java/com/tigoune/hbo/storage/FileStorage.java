/**
 * FileStorage.java
 *
 * @description: saves objects in a local storage.
 * @author IVAN HIDALGO.
 * @version 1.0
 * @date 05-04-2017.
 *
 **/
package com.tigoune.hbo.storage;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Saves objects in a local storage.
 * @author IVAN HIDALGO.
 *
 */
public class FileStorage extends AbstractStorage {
	
    /**
	 * Logger instance.
	 */
	private final Logger logger = LoggerFactory.getLogger(FileStorage.class);

	/* Saves objects in a local storage.
	 * @see com.example.storage.AbstractStorage#saveObject(java.io.File, java.lang.String)
	 */
	@Override
	public String saveObject(File resource, String resourceName) throws IOException, URISyntaxException {
        logger.info("Saving a new object to local storage");
        try {
            logger.debug("Old File: " + resource.getPath());
            copyFile(resource, resourceName);
            logger.debug("New File: ", resourceName);
            deleteFile(resource);
        } catch (IOException e) {
            logger.error("Error saving the object: " + e.getMessage());
            throw e;
        }
        logger.info("The object was saved!");
        return resourceName;
	}
}
