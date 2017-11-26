/**
 * FtpGetRemoteFilesTasklet.java
 *
 * @description: gets objects from FTP server.
 * @author IVAN HIDALGO.
 * @version 1.0
 * @date 10-03-2017.
 *
 *
 */
package com.tigoune.hbo.tasklet;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.integration.file.filters.SimplePatternFileListFilter;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.file.remote.synchronizer.AbstractInboundFileSynchronizer;
import org.springframework.integration.ftp.filters.FtpSimplePatternFileListFilter;
import org.springframework.integration.ftp.inbound.FtpInboundFileSynchronizer;
import org.springframework.integration.sftp.filters.SftpSimplePatternFileListFilter;
import org.springframework.integration.sftp.inbound.SftpInboundFileSynchronizer;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import org.springframework.util.Assert;

/**
 * Gets objects from FTP server.
 *
 * @author IVAN HIDALGO.
 *
 */
public class FtpGetRemoteFilesTasklet implements Tasklet, InitializingBean {

    /**
     * localDirectory attribute.
     */
    private File localDirectory;

    /**
     * ftpInboundFileSynchronizer instance.
     */
    private AbstractInboundFileSynchronizer<?> ftpInboundFileSynchronizer;

    /**
     * sessionFactory instance.
     */
    private SessionFactory sessionFactory;

    /**
     * autoCreateLocalDirectory attribute. Default value true.
     */
    private boolean autoCreateLocalDirectory = true;

    /**
     * deleteLocalFiles attribute. Default value true.
     */
    private boolean deleteLocalFiles = true;

    /**
     * deleteRemoteFiles attribute. Default value true.
     */
    private boolean deleteRemoteFiles = true;

    /**
     * fileNamePattern attribute.
     */
    private String fileNamePattern;

    /**
     * remoteDirectory attribute.
     */
    private String remoteDirectory;

    /**
     * downloadFileAttempts attribute. Default value 12.
     */
    private int downloadFileAttempts = 12;

    /**
     * retryIntervalMilliseconds attribute. Default value 300000.
     */
    private long retryIntervalMilliseconds = 300000;

    /**
     * retryIfNotFound attribute. Default value false.
     */
    private boolean retryIfNotFound = false;

    /**
     * Logger instance.
     */
    private final Logger logger = LoggerFactory.getLogger(FtpGetRemoteFilesTasklet.class);

    /**
     * Synchronizers files from the FTP server to local directory. 
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(sessionFactory, "sessionFactory attribute cannot be null");
        Assert.notNull(localDirectory, "localDirectory attribute cannot be null");
        Assert.notNull(remoteDirectory, "remoteDirectory attribute cannot be null");
        Assert.notNull(fileNamePattern, "fileNamePattern attribute cannot be null");

        setupFileSynchronizer();
        if (!this.localDirectory.exists()) {
            if (this.autoCreateLocalDirectory) {
                if (logger.isDebugEnabled()) {
                    logger.debug("The '" + this.localDirectory + "' directory doesn't exist; Will create.");
                }
                this.localDirectory.mkdirs();
            } else {
                throw new FileNotFoundException(this.localDirectory.getName());
            }
        }
    }

    /**
     * Synchronizes files from the FTP server to local repository.
     */
    private void setupFileSynchronizer() {
        if (isSftp()) {
            ftpInboundFileSynchronizer = new SftpInboundFileSynchronizer(sessionFactory);
            ((SftpInboundFileSynchronizer) ftpInboundFileSynchronizer).setFilter(new SftpSimplePatternFileListFilter(fileNamePattern));
        } else {
            ftpInboundFileSynchronizer = new FtpInboundFileSynchronizer(sessionFactory);
            ((FtpInboundFileSynchronizer) ftpInboundFileSynchronizer).setFilter(new FtpSimplePatternFileListFilter(fileNamePattern));
        }
        ftpInboundFileSynchronizer.setRemoteDirectory(remoteDirectory);
        ftpInboundFileSynchronizer.setDeleteRemoteFiles(deleteRemoteFiles);
    }

    /**
     * Deletes files in local directory.
     */
    private void deleteLocalFiles() {
        if (deleteLocalFiles) {
            SimplePatternFileListFilter filter = new SimplePatternFileListFilter(fileNamePattern);
            List<File> matchingFiles = filter.filterFiles(localDirectory.listFiles());
            if (CollectionUtils.isNotEmpty(matchingFiles)) {
            	logger.info("Deleting files from local directory.");
                matchingFiles.forEach((file) -> {
                    FileUtils.deleteQuietly(file);
                });
            }
        }
    }

    /**
     * Executes Task.
     */
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        deleteLocalFiles();
        ftpInboundFileSynchronizer.synchronizeToLocalDirectory(localDirectory);
        if (retryIfNotFound) {
            SimplePatternFileListFilter filter = new SimplePatternFileListFilter(fileNamePattern);
            int attemptCount = 1;
            while (filter.filterFiles(localDirectory.listFiles()).isEmpty() && attemptCount <= downloadFileAttempts) {
                logger.info("File(s) matching " + fileNamePattern
                        + " not found on remote site.  Attempt "
                        + attemptCount + " out of " + downloadFileAttempts);
                Thread.sleep(retryIntervalMilliseconds);
                ftpInboundFileSynchronizer.synchronizeToLocalDirectory(localDirectory);
                attemptCount++;
            }
            if (attemptCount >= downloadFileAttempts && filter.filterFiles(localDirectory.listFiles()).isEmpty()) {
                logger.warn("Could not find remote file(s) matching "
                        + fileNamePattern + " after " + downloadFileAttempts
                        + " attempts.");
            }
        }
        return RepeatStatus.FINISHED;
    }

    /**
     * Gets downloadFileAttempts attribute.
     *
     * @return the downloadFileAttempts
     */
    public int getDownloadFileAttempts() {
        return downloadFileAttempts;
    }

    /**
     * Gets fileNamePattern attribute.
     *
     * @return the fileNamePattern.
     */
    public String getFileNamePattern() {
        return fileNamePattern;
    }

    /**
     * Gets localDirectory attribute.
     *
     * @return the localDirectory.
     */
    public File getLocalDirectory() {
        return localDirectory;
    }

    /**
     * Gets RemoteDirectory attribute.
     *
     * @return the remoteDirectory.
     */
    public String getRemoteDirectory() {
        return remoteDirectory;
    }

    /**
     * Gets retryIntervalMilliseconds attribute.
     *
     * @return the retryIntervalMilliseconds.
     */
    public long getRetryIntervalMilliseconds() {
        return retryIntervalMilliseconds;
    }

    /**
     * Gets sessionFactory instance.
     *
     * @return the sessionFactory.
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Verifies if creates local directory.
     *
     * @return the autoCreateLocalDirectory.
     */
    public boolean isAutoCreateLocalDirectory() {
        return autoCreateLocalDirectory;
    }

    /**
     * Verifies if deletes the files on FTP server.
     *
     * @return the deleteLocalFiles.
     */
    public boolean isDeleteLocalFiles() {
        return deleteLocalFiles;
    }

    /**
     * Verifies if retries the files downloading when they are not on FTP
     * server.
     *
     * @return the retryIfNotFound.
     */
    public boolean isRetryIfNotFound() {
        return retryIfNotFound;
    }

    /**
     * Validates is security FTP server.
     *
     * @return the sftp.
     */
    public boolean isSftp() {
        return sessionFactory instanceof DefaultSftpSessionFactory;
    }

    /**
     * Sets autoCreateLocalDirectory attribute.
     *
     * @param autoCreateLocalDirectory the autoCreateLocalDirectory to set.
     */
    public void setAutoCreateLocalDirectory(boolean autoCreateLocalDirectory) {
        this.autoCreateLocalDirectory = autoCreateLocalDirectory;
    }

    /**
     * Sets deleteLocalFiles attribute.
     *
     * @param deleteLocalFiles the deleteLocalFiles to set.
     */
    public void setDeleteLocalFiles(boolean deleteLocalFiles) {
        this.deleteLocalFiles = deleteLocalFiles;
    }

    /**
     * Sets downloadFileAttempts attribute.
     *
     * @param downloadFileAttempts the downloadFileAttempts to set.
     */
    public void setDownloadFileAttempts(int downloadFileAttempts) {
        this.downloadFileAttempts = downloadFileAttempts;
    }

    /**
     * Sets fileNamePattern attribute.
     *
     * @param fileNamePattern the fileNamePattern to set.
     */
    public void setFileNamePattern(String fileNamePattern) {
        this.fileNamePattern = fileNamePattern;
    }

    /**
     * Gets remote Directory to gets files.
     *
     * @param localDirectory the localDirectory to set.
     */
    public void setLocalDirectory(File localDirectory) {
        this.localDirectory = localDirectory;
    }

    /**
     * Sets remote Directory to gets files.
     *
     * @param remoteDirectory the remoteDirectory to set.
     */
    public void setRemoteDirectory(String remoteDirectory) {
        this.remoteDirectory = remoteDirectory;
    }

    /**
     * Sets retryIfNotFound attribute.
     *
     * @param retryIfNotFound the retryIfNotFound to set.
     */
    public void setRetryIfNotFound(boolean retryIfNotFound) {
        this.retryIfNotFound = retryIfNotFound;
    }

    /**
     * Sets retryIntervalMilliseconds attribute.
     *
     * @param retryIntervalMilliseconds the retryIntervalMilliseconds to set.
     */
    public void setRetryIntervalMilliseconds(long retryIntervalMilliseconds) {
        this.retryIntervalMilliseconds = retryIntervalMilliseconds;
    }

    /**
     * Sets sessionFactory instance.
     *
     * @param sessionFactory the sessionFactory to set.
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Get deleteRemoteFiles attribute.
     *
     * @return the deleteRemoteFiles.
     */
    public boolean getDeleteRemoteFiles() {
        return deleteRemoteFiles;
    }

    /**
     * Sets deleteRemoteFiles attribute.
     *
     * @param deleteRemoteFiles the deleteRemoteFiles to set.
     */
    public void setDeleteRemoteFiles(boolean deleteRemoteFiles) {
        this.deleteRemoteFiles = deleteRemoteFiles;
    }
}
