/**
 * FileHelper.java
 *
 * @description: helps with file's operations.
 * @author IVAN HIDALGO.
 * @version 1.0
 * @date 10-03-2017.
 *
 **/

package com.tigoune.hbo.helper;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.UnexpectedJobExecutionException;
import org.springframework.util.Assert;

import com.tigoune.hbo.tasklet.FileBackupTasklet;

/**
 * Helps with file's operations.
 * @author IVAN HIDALGO.
 * 
 */
public class FileHelper {
	
    /**
     * File name pattern to filter.
     */
    private String fileNamePattern;

    /**
     * Traces different levels of logging.
     */
    private final Logger logger = LoggerFactory.getLogger(FileBackupTasklet.class);

    /**
     * Gets files list from a directory. 
     * @param directory
     * @return file list.
     * @throws java.io.IOException
     */
    public synchronized File[] getFilesFromDirectory(File directory)throws IOException {
        if (!directory.isDirectory()) {
                logger.error("The directory "+ directory.getAbsolutePath() +
                                     " not found!!");
                throw new java.io.IOException("The directory "
                                              + directory.getAbsolutePath() +
                                                      " not found!!");
        }
        return directory.listFiles();
    }

    /**
     * Gets files list from a directory by applying a filter to it. 
     * @param directory
     * @return Files list filtered. 
     * @throws IOException
     */
    public synchronized File[] getFilteredFilesFromDirectory(File directory)throws IOException {
        Assert.notNull(fileNamePattern, "fileNamePattern attribute cannot be null");
        if (!directory.isDirectory()) {
                logger.error("The directory "+ directory.getAbsolutePath() 
                             +" not found!!");
                throw new java.io.IOException("The directory "+ 
                             directory.getAbsolutePath() 
                             +" not found!!");
        }
        File[] files = directory.listFiles(new FilenameFilter() {
            public boolean accept(File directory, String name) {
                return name.toLowerCase().endsWith(fileNamePattern);
            }
        });
        logger.debug("files Number to copy: "+ files.length);
        return files;
    }

    /**
     * Copies a file from source path to destination path.
     * @param source file.
     * @param dest file.
     * @throws IOException
     */
    public synchronized void copyFile(File source, File dest) throws IOException {
        FileUtils.copyFile(source, dest);
    }

    /**
     * Copies a file from source path to destination path.
     * @param source file.
     * @param fileNameToSave new file name.
     * @throws IOException
     */
    public synchronized void copyFile(File source, String fileNameToSave) throws IOException {
        File newfile = new File(fileNameToSave);
        FileUtils.copyFile(source, newfile);
    }

    /**
     * Deletes any file.
     * @param source file.
     * @throws IOException
     */
    public synchronized void deleteFile(File source) throws IOException {
        boolean deleted = FileUtils.deleteQuietly(source);
        if (!deleted) {
                throw new UnexpectedJobExecutionException(
                   "Could not delete file " + source.getPath());
        } else {
            logger.debug(source.getPath() + " was deleted!");
        }
    }

    /**
     * Gets file name pattern to apply.
     * @return the fileNamePattern
     */
    public String getFileNamePattern() {
        return fileNamePattern;
    }

    /**
     * Sets file name pattern to apply.
     * @param fileNamePattern the fileNamePattern to set.
     */
    public void setFileNamePattern(String fileNamePattern) {
        this.fileNamePattern = fileNamePattern;
    }
}
