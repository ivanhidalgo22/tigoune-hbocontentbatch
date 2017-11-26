/**
 * HboContentProcessor.java
 *
 * @description: this class transforms HBO objects to content objects. 
 * @author IVAN HIDALGO.
 * @version 1.0
 * @date 10-11-2017.
 *
 **/

package com.tigoune.hbo.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import com.tigoune.hbo.model.metadata.Metadata;
import com.tigoune.hbo.mapper.ContentMapper;
import com.tigoune.hbo.mapper.ContentMapperFactory;
import com.tigoune.hbo.mapper.InvalidContentException;
import com.tigoune.hbo.model.Content;
import com.tigoune.hbo.model.ContentType;
/**
 * This class transforms HBO objects to content objects.
 * @author IVAN HIDALGO.
 *
 */
public class HboContentProcessor implements ItemProcessor<Metadata, Content> {

    /**
     * MetadataToContentMapper instance.
     */
    private ContentMapper metadataToContentMapper;
    
    /**
     * Content types.
     */
    private ContentType contentType;
    
    private static String EPISODE = "Episode";
    
    private static String MOVIE = "Movie";
    
    /**
     * Logger instance.
     */
    private final Logger logger = LoggerFactory.getLogger(HboContentProcessor.class);
    
    /**
     * Default constructor.
     */
    public HboContentProcessor() {
    	contentType = new ContentType();
	}

    /**
     * Transforms HBO objects to content objects.
     * @param hboMetadata HBO object.
     * @throws java.lang.Exception
     */
    @Override
    public Content process(Metadata hboMetadata) throws Exception {
        logger.info("xml metadata with id "+hboMetadata.getFeature().getUniqueId() +" will be processed.");
        try {
        	String type = getContentType(hboMetadata);
            metadataToContentMapper = ContentMapperFactory.createContentMapper(type);
            if (metadataToContentMapper == null)
            	throw new InvalidContentException("The content " + hboMetadata.getFeature().getUniqueId() + " has an invalid content type.");
            return metadataToContentMapper.map(hboMetadata);
        } catch (Exception ex) {
        	throw ex;
        }
        finally {
        	logger.info("The HBO metadata was processed.");
        }
    }

    /**
     * @return the metadataToContentMapper.
     */
    public ContentMapper getMetadataToContentMapper() {
        return metadataToContentMapper;
    }

    /**
     * @param metadataToContentMapper the metadataToContentMapper to set.
     */
    public void setMetadataToContentMapper(ContentMapper metadataToContentMapper) {
        this.metadataToContentMapper = metadataToContentMapper;
    }
    
    /**
     * Gets the content type.
     * @param hboMetadata
     * @return Content type
     * @throws InvalidContentException
     */
    public String getContentType(Metadata hboMetadata) throws InvalidContentException {
    	if((hboMetadata.getFeature().getDescription() == null) || (hboMetadata.getFeature().getDescription().isEmpty())) {
    		throw new InvalidContentException("The content " + hboMetadata.getFeature().getUniqueId() + " doesn't have descriptions.");
    	}
    	
    	if((hboMetadata.getFeature().getDescription().get(0).getCategories() == null) ||
    			(hboMetadata.getFeature().getDescription().get(0).getCategories().getCategory() == null)) {
    		throw new InvalidContentException("The content " + hboMetadata.getFeature().getUniqueId() + " doesn't have category.");
    	}
    	
    	if(contentType.getContentTypeByKey(hboMetadata.getFeature().getDescription().get(0).getCategories().getCategory()).equals(EPISODE)) {
        	logger.debug("It's a serie content.");
        	return "SERIE";
        } else if (contentType.getContentTypeByKey(hboMetadata.getFeature().getDescription().get(0).getCategories().getCategory()).equals(MOVIE)) {
        	logger.debug("It's a movie content.");
        	return "MOVIE";	
        }
    	return "";
    }
    
}
