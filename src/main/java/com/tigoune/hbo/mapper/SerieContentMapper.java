/**
 * SerieContentMapper.java
 *
 * @description: this class transforms from series objects to content objects.
 * @author IVAN HIDALGO.
 * @version 1.0
 * @date 10-03-2017.
 *
 **/

package com.tigoune.hbo.mapper;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tigoune.hbo.model.Content;
import com.tigoune.hbo.model.ContentId;
import com.tigoune.hbo.model.Description;
import com.tigoune.hbo.model.DescriptionId;
import com.tigoune.hbo.model.Link;
import com.tigoune.hbo.model.Offering;
import com.tigoune.hbo.model.Rating;
import com.tigoune.hbo.model.metadata.Metadata;

/**
 * this class transforms from series objects to content objects.
 * @author Ivan Hidalgo.
 *
 */
public class SerieContentMapper extends ContentMapper {
	
	private Content parentContent;
	
	/**
     * Logger instance.
     */
    private final Logger logger = LoggerFactory.getLogger(SerieContentMapper.class);
	
    private static String SERIE = "Serie";
    
	/**
     * Transforms HBO objects to content objects. 
     * @param hboMetadata
     * @return Content.
     * @throws Exception 
     */
    public Content map(Metadata hboMetadata) throws Exception {
    	logger.info("Transforming series metadata to content objects.");
    	parentContent = new Content();
    	content = new Content();
    	getSerieContentFromMetadata(hboMetadata,parentContent);
    	getContentFromMetadata(hboMetadata, content);
    	parentContent.setDescriptions(getSerieDescriptionFromMetadata(hboMetadata,parentContent));
    	parentContent.setLinks(getSerieLinksFromMetadata(hboMetadata, parentContent));
    	parentContent.setContentSeasons(getSeasonsFromMetadata(hboMetadata, parentContent));
    	
    	if(isItTheFirstEpisode(hboMetadata)) {
    		Offering offering = getOfferingFromMetadata(hboMetadata, parentContent);
    		offering.setEndDateTime(null);
    		parentContent.getOfferings().add(offering);
    		parentContent.getRatings().add(getRatingFromMetadata(hboMetadata, parentContent));
    		parentContent.setGenresOfContent(getGeneresFromMetadata(hboMetadata, parentContent));
    	}
    	
    	content.setDescriptions(getDescriptionFromMetadata(hboMetadata, content));
		content.getOfferings().add(getOfferingFromMetadata(hboMetadata, content));
		Rating rating = getRatingFromMetadata(hboMetadata, content);
		if(rating != null)
			content.getRatings().add(rating);
		content.setParties(getPartiesFromMetadata(hboMetadata, content));
		content.setAudioTracks(getAudioTrackFromMetadata(hboMetadata, content));
		content.setGenresOfContent(getGeneresFromMetadata(hboMetadata, content));
		content.setSubtitles(getSubtitlesFromMetadata(hboMetadata, content));
		content.setLinks(getLinksFromMetadata(hboMetadata, content));
		
		content.setParentContent(parentContent);
		parentContent.getContentChilds().add(content);
		return parentContent;
    }
    
    /**
     * Gets the content from HBO document.
     * @param hboMetadata
     * @return New Content.
     * @throws InvalidContentException
     */
    private void getSerieContentFromMetadata(Metadata hboMetadata, Content content) throws InvalidContentException {
    	try {
    		logger.debug("Gets the serie content from HBO document.");
    		
    		contentId = new ContentId();
            contentId.setContentId(hboMetadata.getFeature().getSeries().getUniqueId());
            content.setContentId(contentId);
            logger.debug("Content Identifier: " + contentId.getContentId());
            
        	if((hboMetadata.getFeature().getDescription() == null) || (hboMetadata.getFeature().getDescription().isEmpty())) {
        		throw new InvalidContentException("The content " + content.getContentId().getContentId() + " doesn't have descriptions.");
        	}
        	
        	if((hboMetadata.getFeature().getVideos() == null)) {
        		throw new InvalidContentException("The content " + content.getContentId().getContentId() + " doesn't have video.");
        	}
        	
    		contentDescription = hboMetadata.getFeature().getDescription().get(0);
    		content.setYear(contentDescription.getReleaseYear());
    		content.setContetType("Serie");
    		content.setDateModified(Instant.now().minusSeconds(18000).toString());
        	
        	logger.debug("The content was gotten from HBO document.");
    	}
    	catch(Exception ex) {
    		logger.error("An error occurred when trying to get the serie content: " + ex.getMessage());
    		throw ex;
    	}
    }
    
    /**
     * Gets Content's description form HBO document.
     * @param hboMetadata
     * @return New content's description.
     * @throws InvalidContentException
     */
    private Set<Description> getSerieDescriptionFromMetadata(Metadata hboMetadata, Content content) throws InvalidContentException {
    	Set<Description> descriptions = new HashSet<Description>(0);
    	logger.debug("Gets the serie content's description from HBO document.");
    	try {
    		
        	if((hboMetadata.getFeature().getSeries().getDescription() == null) || (hboMetadata.getFeature().getSeries().getDescription().isEmpty())) {
        		throw new InvalidContentException("The serie content " + hboMetadata.getFeature().getSeries().getUniqueId() + " doesn't have descriptions.");
        	}
        	
        	for(com.tigoune.hbo.model.metadata.Description description : hboMetadata.getFeature().getSeries().getDescription()) {
        		Description contentDescription = new Description();
        		DescriptionId descriptionId = new DescriptionId();
        		descriptionId.setContentId(content.getContentId());
        		descriptionId.setDescriptionLanguage(description.getLanguage().toUpperCase());
        		contentDescription.setDescriptionId(descriptionId);
        		contentDescription.setTitle(description.getSerieTitle());
        		contentDescription.setSynopsis(description.getSynopsis());
        		contentDescription.setContent(content);
        		descriptions.add(contentDescription);
        	}	
        	
        	logger.debug("The serie content's description was gotten from HBO document.");
    	}
    	catch(Exception ex) {
    		logger.error("An error occurred when trying to get the content's description: " + ex.getMessage());
    		throw ex;
    	}
    	
    	return descriptions;
    }
    
    /**
     * Gets content's links from HBO document. 
     * @param hboMetadata
     * @return content's links collection.
     * @throws InvalidContentException 
     */
    private Set<Link> getSerieLinksFromMetadata(Metadata hboMetadata, Content content) throws InvalidContentException {
    	Set<Link> links = new HashSet<Link>(0);
    	try {
    		logger.debug("Gets the serie content's images from HBO document.");
    		
    		if((hboMetadata.getFeature().getVideos().getVideo().getImages() == null) || 
    				(hboMetadata.getFeature().getVideos().getVideo().getImages().getImage().isEmpty())) {
        		throw new InvalidContentException("The serie content " + content.getContentId().getContentId() + " doesn't have images.");
        	}
        	 
        	for(com.tigoune.hbo.model.metadata.Image image : hboMetadata.getFeature().getVideos().getVideo().getImages().getImage()) {
        		if((image.getImageResolution().equals("640x960")) && (image.getType().equals(SERIE))) {
        			links.add(createNewLink(POSTER_L,image,content));
        			links.add(createNewLink(POSTER_M,image,content));
        			links.add(createNewLink(POSTER_S,image,content));
        		}
        		if((image.getImageResolution().equals("1920x1080")) && (image.getType().equals(SERIE))) { 
        			links.add(createNewLink(HERO_XL,image,content));
        			links.add(createNewLink(HERO_S,image,content));
        			links.add(createNewLink(HERO_M,image,content));
        			links.add(createNewLink(HERO_L,image,content));
        		}
        	}	
        	logger.debug("The serie content's images were gotten from HBO document.");
    	}
    	catch(Exception ex) {
    		logger.error("An error occurred when trying to get the serie content's images: " + ex.getMessage());
    		throw ex;
    	}
    	
    	return links;
    }
    
    private boolean isItTheFirstEpisode(Metadata hboMetadata) {
    	if((hboMetadata.getFeature().getSeason().getSeasonNumber().intValue() == 1) && 
    			(hboMetadata.getFeature().getDescription().get(0).getEpisodeNumber().getInSeries().intValue() == 1)) 
    		return true;
    	return false;
    }
}
