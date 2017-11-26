/**
 * AbstractStorage.java
 *
 * @description: Default storage to save objects.
 * @author IVAN HIDALGO.
 * @version 1.0
 * @date 05-04-2017.
 *
 **/
package com.tigoune.hbo.storage;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import org.springframework.util.Assert;

import com.tigoune.hbo.helper.FileHelper;

/**
 * Default storage to save objects.
 * @author IVAN HIDALGO.
 *
 */
public abstract class AbstractStorage implements Storage {

    /**
     * Helps with file's operations. 
     */
    protected FileHelper fileHelper;

    /**
     * Verifies attributes are not null.
     * @throws Exception
     */
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(fileHelper, "fileHelper attribute cannot be null");
    }

    /**
     * Default operation to save objects.
     * @param sourceDirectory
     * @throws IOException
     */
    @Override
    public abstract String saveObject(File resource, String resourceName) throws IOException, URISyntaxException; 

    /**
     * Sets file Helper.
     * @return the fileHelper object.
     */
    FileHelper getFileHelper() {
        return fileHelper;
    }

    /**
     * Gets file Helper.
     * @param fileHelper the fileHelper to set.
     */
    public void setFileHelper(FileHelper fileHelper) {
        this.fileHelper = fileHelper;
    }

    /**
     * Deletes an object. 
     * @param file
     * @throws IOException
     */
    public void deleteFile(File file) throws IOException {
        fileHelper.deleteFile(file);
    }

    /**
     * Copies an object.  
     * @param source
     * @param dest
     * @throws IOException
     */
    public void copyFile(File source, String newFile) throws IOException{
        fileHelper.copyFile(source, newFile);
    }

}
