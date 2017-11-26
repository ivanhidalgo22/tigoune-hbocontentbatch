/**
 * MovieContentMapper.java
 *
 * @description: this class transforms from movie objects to content objects.
 * @author IVAN HIDALGO.
 * @version 1.0
 * @date 10-03-2017.
 *
 **/

package com.tigoune.hbo.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tigoune.hbo.model.Content;
import com.tigoune.hbo.model.Rating;
import com.tigoune.hbo.model.metadata.Metadata;

/**
 * this class transforms from movie objects to content objects.
 * @author Ivan Hidalgo.
 *
 */
public class MovieContentMapper extends ContentMapper {
	
	/**
     * Logger instance.
     */
    private final Logger logger = LoggerFactory.getLogger(MovieContentMapper.class);
	
	/**
     * Transforms movie objects to content objects. 
     * @param hboMetadata
     * @return Content.
     * @throws Exception.
     */
    public Content map(Metadata hboMetadata) throws Exception {
    	logger.info("Transforming movie metadata to content objects.");
    	content = new Content();
    	getContentFromMetadata(hboMetadata, content);
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
		return content;
    }
}
