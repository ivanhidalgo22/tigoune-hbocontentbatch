/**
 * Storage.java
 *
 * @description: contract to store objects.
 * @author IVAN HIDALGO.
 * @version 1.0
 * @date 05-04-2017.
 *
 **/
package com.tigoune.hbo.storage;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Contract to store objects.
 * @author IVAN HIDALGO.
 */
public interface Storage {
    
    /**
     * operation to save objects.
     * @param sourceDirectory
     * @throws IOException
     */
	String saveObject(File resource, String resourceName) throws IOException, URISyntaxException;
}
