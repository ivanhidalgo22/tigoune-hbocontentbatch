/**
 * CustomHibernateItemWriter.java
 *
 * @description: writes a new HBO content in the database.
 * @author IVAN HIDALGO.
 * @version 1.0
 * @date 10-10-2017.
 *
 **/

package com.tigoune.hbo.writer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import com.tigoune.hbo.model.Content;
import com.tigoune.hbo.service.IContentService;

/**
 * Writes a new HBO content in the database.
 * @author IVAN HIDALGO.
 */
public class CustomHibernateItemWriter implements ItemWriter<Content>{

    /**
     * ContentService instance.
     */
    private IContentService contentService;

    /**
    * Logger instance.
    */
    private final Logger logger = LoggerFactory.getLogger(CustomHibernateItemWriter.class);

    /**
     * Writes a new HBO content in the data base.
     * @param contents HBO content.
     * @throws Exception 
     */
    @Override
    public void write(List<? extends Content> contents) throws Exception {
    	
        for(Content content : contents) {
            try {
            	String contentId = content.getContentChilds().isEmpty()  ? content.getContentId().getContentId():content.getContentChilds().iterator().next().getContentId().getContentId();
            	logger.info("The content with identifier "+ contentId +" will be saved in the database.");
                contentService.addContent(content);
                logger.info("The content with identifier "+ contentId +" was saved in the database.");
            } catch (Exception e) {
                throw e;
            }
        }
    }

    /**
     * Gets contentService.
     * @return the contentService.
     */
    public IContentService getContentService() {
        return contentService;
    }

    /**
     * Sets contentService.
     * @param contentService the contentService to set.
     */
    public void setContentService(IContentService contentService) {
        this.contentService = contentService;
    }
}
