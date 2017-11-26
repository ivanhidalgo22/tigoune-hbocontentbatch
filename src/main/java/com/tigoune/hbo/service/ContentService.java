/**
 * ContentService.java
 *
 * @description: This service validates an HBO content before saving it in the database.
 * @author IVAN HIDALGO.
 * @version 1.0
 * @date 10-03-2017.
 *
 **/

package com.tigoune.hbo.service;

import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tigoune.hbo.model.Content;
import com.tigoune.hbo.repository.CustomDataAccessException;
import com.tigoune.hbo.repository.IContentRepository;
import com.tigoune.hbo.repository.PersisterException;

import org.hibernate.HibernateException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionTimedOutException;

/**
 * This service validates a HBO content before saving it in the database.
 * @author IVAN HIDALGO.
 *
 */
@Service
public class ContentService implements IContentService {

    /**
     * contentRepository instance.
     */
    private IContentRepository contentRepository;

    /**
    * maxRetries attribute.
    * Default value 1.
    */
    private int maxRetries = 1;
    
    /**
     * retryInterval attribute.
     * Default value 3000.
     */
    private long retryInterval = 3000;
	
    /**
     * Logger instance.
     */
    private final Logger logger = LoggerFactory.getLogger(ContentService.class);

    /**
     * validates an HBO content before saving it in the database.
     * @throws java.lang.InterruptedException
     */
    @Transactional(readOnly = false)    
    @Override
    public synchronized void addContent(Content content) throws PersisterException, InterruptedException {
        int retryNumber = 0;
        while(retryNumber < getMaxRetries()) {
            try {
                contentRepository.saveContent(content);
                return;
            } catch (HibernateException e) {
                retryNumber = retryNumber + 1;
                Thread.sleep(getRetryInterval());
                logger.warn("HibernateException: " + e.getMessage());
                logger.warn("Retry number: " + retryNumber);
                if (retryNumber >= getMaxRetries()) {
                    logger.warn("Has reached the number of retries.");
                    throw new PersisterException("Has reached the number of retries. " + e);
                }
            } catch (TransactionTimedOutException e) {
                retryNumber = retryNumber + 1;
                Thread.sleep(getRetryInterval());
                logger.warn("TransactionTimedOutException: " + e.getMessage());
                logger.warn("Retry number: " + retryNumber);
                if (retryNumber >= getMaxRetries()) {
                    logger.warn("Has reached the number of retries.");
                    throw new PersisterException("Has reached the number of retries. " + e);
                }
            } catch (DataAccessException e) {
                retryNumber = retryNumber + 1;
                Thread.sleep(getRetryInterval());
                logger.warn(e.getMessage());
                logger.warn("Retry number: " + retryNumber);
                if (retryNumber >= getMaxRetries()) {
                    logger.warn("Has reached the number of retries.");
                    throw new PersisterException("Has reached the number of retries. " + e);
                }
            } catch (CustomDataAccessException e) {
                throw new PersisterException(e.getMessage());
            } catch (IllegalStateException e) {
            	throw new PersisterException(e.getMessage());
            } catch (Exception e) {
            	throw e;
            }
        }
    }

    /**
     * Gets max retries to apply in a data access fail.
     * @return the maxRetries.
     */
    public int getMaxRetries() {
        return maxRetries;
    }

    /**
     * Sets max retries to apply in a data access fail.
     * @param maxRetries the maxRetries to set.
     */
    public void setMaxRetries(int maxRetries) {
        this.maxRetries = maxRetries;
    }
    
    /**
     * @return the retryInterval.
     */
    public long getRetryInterval() {
        return retryInterval;
    }

    /**
     * @param retryInterval the retryInterval to set.
     */
    public void setRetryInterval(long retryInterval) {
        this.retryInterval = retryInterval;
    }

	/**
	 * @return the contentRepository.
	 */
	public IContentRepository getContentRepository() {
		return contentRepository;
	}

	/**
	 * @param contentRepository the contentRepository to set.
	 */
	public void setContentRepository(IContentRepository contentRepository) {
		this.contentRepository = contentRepository;
	}
}
