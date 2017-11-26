/**
 * ContentRepository.java
 *
 * @description: This repository saves a new HBO content in the database.
 * @author IVAN HIDALGO.
 * @version 1.0
 * @date 10-10-2017.
 *
 **/
package com.tigoune.hbo.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tigoune.hbo.model.Content;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Repository;

/**
 * This repository saves a new HBO content in the database.
 * @author IVAN HIDALGO.
 *
 */
@Repository
public class ContentRepository implements IContentRepository {
	
	/**
	 * Session factory attribute.
	 */
	private SessionFactory sessionFactory;
	
	/**
	 * Logger instance.
	 */
	private final Logger logger = LoggerFactory.getLogger(ContentRepository.class);
	
	/**
	 * saves a new HBO content in the database.
	 * @param content.
	 */
	@Override
	public synchronized void saveContent(Content content) throws CustomDataAccessException {
		String contentId = content.getContentChilds().isEmpty()  ? content.getContentId().getContentId():content.getContentChilds().iterator().next().getContentId().getContentId();
       try {
            getSessionFactory().getCurrentSession().merge(content);
            getSessionFactory().getCurrentSession().evict(content);
            logger.debug("The content with id "+ contentId +" was saved.");   
        } catch (CannotAcquireLockException e) {
            logger.warn("The repository was saving the content with id "+ contentId +" but it failed. The error detail was: (CannotAcquireLockException) " + e.getMessage());
            throw new CustomDataAccessException("CannotAcquireLockException: " + e.getMessage());
        } catch (OptimisticLockingFailureException e) {
            logger.warn("The repository was saving the content with id "+ contentId +" but it failed. The error detail was: (OptimisticLockingFailureException) " + e.getMessage());
            throw new CustomDataAccessException("OptimisticLockingFailureException: " + e.getMessage());
        } catch (HibernateException e) {
            logger.warn("The repository was saving the content with id "+ contentId +" but it failed. The error detail was: (HibernateException) " + e.getMessage());
            throw new CustomDataAccessException("HibernateException: " + e.getMessage());
        } catch (IllegalStateException e) {
        	logger.warn("The repository was saving the content with id "+ contentId +" but it had an invalid element. The error detail was: (IllegalStateException) " + e.getMessage());
            throw e;
        } catch (Exception e) {
        	logger.warn("The repository was saving the content with id "+ contentId +" but it had an invalid element. The error detail was: (Exception) " + e.getMessage());
            throw e;
        }
	}
	
    /**
     * Gets Session Factory.
     * @return SessionFactory Session Factory.
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    /**
     * Sets Session Factory
     * @param SessionFactory Session Factory
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
