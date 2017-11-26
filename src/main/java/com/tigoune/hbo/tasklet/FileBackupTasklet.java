/**
 * FileBackupTasklet.java
 *
 * @description: Task to save the processed files.
 * @author IVAN HIDALGO.
 * @version 1.0
 * @date 10-03-2017.
 *
 **/

package com.tigoune.hbo.tasklet;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import com.tigoune.hbo.helper.FileHelper;
import com.tigoune.hbo.storage.Storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Task to save the processed files.
 * @author IVAN HIDALGO.
 * 
 */
public class FileBackupTasklet implements Tasklet, InitializingBean {

	/**
     * Helps with file's operations. 
     */
    protected FileHelper fileHelper;
	
	/**
     * Source directory attribute.
     */
    private Resource sourceDirectory;
    
    /**
     * Output directory attribute.
     */
    private Resource destDirectory;

    /**
     * Storage attribute.
     */
    private Storage storage;
    
    /**
     * Date time format.
     */
    private String dateFormatPattern;

    /**
     * Formatter to save an object. 
     */
    protected DateTimeFormatter dateTimeFormatter;

    /**
     * Extension to save an object.
     */
    private String backupExt;
    
    /**
     * Suffix attribute.
     */
    private static final String SUFFIX = "/";

    /**
     * Folder name format.
     */
    private static final String FOLDER_DATE = "yyyyMMdd";


    /**
     * Logger instance.
     */
    private final Logger logger = LoggerFactory.getLogger(FileBackupTasklet.class);

    /**
     * Verifies attributes are not null.
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
    	Assert.notNull(dateFormatPattern, "dateFormatPattern attribute cannot be null");
        Assert.notNull(sourceDirectory, "sourceDirectory attribute cannot be null");
        Assert.notNull(storage, "storage attribute cannot be null");
        Assert.notNull(fileHelper, "storage attribute cannot be null");
    }

    /**
     * Executes Files Backup Step. 
     * @throws java.lang.Exception
     */
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        logger.info("Files Backup Task started.");
        LocalDateTime dateNow = LocalDateTime.now();
        dateTimeFormatter = DateTimeFormatter.ofPattern(getDateFormatPattern());
        try {
        	File dir = sourceDirectory.getFile();
            File[] files = fileHelper.getFilteredFilesFromDirectory(dir);
            for (File file : files) {
	            try {
	            	storage.saveObject(file, BuildFileName(dateNow, file.getName()));
	            } catch (Exception e) {
	                    logger.warn("Could not copy the file "+ file.getName() +" because writing it caused the error: " + e.getMessage());                   
	            }
            }
        }
        catch(Exception ex) {
            logger.error("Files Backup Task had an error: " + ex.getMessage());
            throw ex;
        }
        logger.info("Files Backup Task finished.");
        return RepeatStatus.FINISHED;
    }

    /**
     * Sets the source directory. 
     * @param sourceDirectory
     */
    public void setSourceDirectory(Resource sourceDirectory) {
        this.sourceDirectory = sourceDirectory;
    }

    /**
     * Gets the storage.
     * @return the storage.
     */
    public Storage getStorage() {
        return storage;
    }

    /**
     * Sets the storage.
     * @param storage the storage to set.
     */
    public void setStorage(Storage storage) {
        this.storage = storage;
    }
    
    /**
     * Builds new file name.
     * @param dateNow
     * @param fileName
     * @return new file name.
     * @throws IOException 
     */
    String BuildFileName(LocalDateTime dateNow, String fileName) throws IOException {
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter folderFormatter = DateTimeFormatter.ofPattern(FOLDER_DATE);
        return "HBO" + SUFFIX + folderFormatter.format(date) + SUFFIX + fileName + "." + dateTimeFormatter.format(dateNow) + getBackupExt();
    }

    /**
     * Gets date Format Pattern to save an object.
     * @return the dateFormatPattern.
     */
    String getDateFormatPattern() {
        return dateFormatPattern;
    }

    /**
     * Sets date Format Pattern to save an object.
     * @param dateFormatPattern the dateFormatPattern to set.
     */
    public void setDateFormatPattern(String dateFormatPattern) {
        this.dateFormatPattern = dateFormatPattern;
    }

    /**
     * @return the backupExt.
     */
    public String getBackupExt() {
        return backupExt;
    }

    /**
     * @param backupExt the backupExt to set.
     */
    public void setBackupExt(String backupExt) {
        this.backupExt = backupExt;
    }
    
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
	 * @return the destDirectory
	 */
	public Resource getDestDirectory() {
		return destDirectory;
	}

	/**
	 * @param destDirectory the destDirectory to set
	 */
	public void setDestDirectory(Resource destDirectory) {
		this.destDirectory = destDirectory;
	}
}
