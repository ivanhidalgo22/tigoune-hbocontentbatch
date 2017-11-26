/**
 * MetadataToContentMapper.java
 *
 * @description: this class transforms HBO objects to content objects. 
 * @author IVAN HIDALGO.
 * @version 1.0
 * @date 10-03-2017.
 *
 **/

package com.tigoune.hbo.mapper;

import com.tigoune.hbo.model.AudioTrack;
import com.tigoune.hbo.model.AudioTrackId;
import com.tigoune.hbo.model.Content;
import com.tigoune.hbo.model.ContentId;
import com.tigoune.hbo.model.ContentSeason;
import com.tigoune.hbo.model.ContentSeasonId;
import com.tigoune.hbo.model.ContentType;
import com.tigoune.hbo.model.Country;
import com.tigoune.hbo.model.Description;
import com.tigoune.hbo.model.DescriptionId;
import com.tigoune.hbo.model.GenreType;
import com.tigoune.hbo.model.GenresOfContent;
import com.tigoune.hbo.model.GenresOfContentId;
import com.tigoune.hbo.model.Link;
import com.tigoune.hbo.model.LinkId;
import com.tigoune.hbo.model.Party;
import com.tigoune.hbo.model.PartyId;
import com.tigoune.hbo.model.Offering;
import com.tigoune.hbo.model.OfferingId;
import com.tigoune.hbo.model.Rating;
import com.tigoune.hbo.model.RatingId;
import com.tigoune.hbo.model.Subtitle;
import com.tigoune.hbo.model.SubtitleId;
import com.tigoune.hbo.model.metadata.Format;
import com.tigoune.hbo.model.metadata.Genre;
import com.tigoune.hbo.model.metadata.Localized;
import com.tigoune.hbo.model.metadata.Metadata;
import com.tigoune.hbo.model.metadata.Ratings;
import com.tigoune.hbo.model.metadata.Season;
import com.tigoune.hbo.model.metadata.Videos;

import java.time.Instant;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Transforms HBO objects to content objects. 
 * @author IVAN HIDALGO.
 */
public abstract class ContentMapper {
    
    /**
     * Logger instance.
     */
    protected final Logger logger = LoggerFactory.getLogger(ContentMapper.class);
    
    protected Content content;
    protected ContentType contentType;
    protected Country countryType;
    protected GenreType genreType;
    protected com.tigoune.hbo.model.metadata.Description contentDescription;
    protected Videos contentVideos;
    protected Season contentSeason;
    protected ContentId contentId;
    protected com.tigoune.hbo.model.metadata.Offering offering;
    protected Ratings ratings;
    
    protected static String EPISODE = "Episode";
    protected static String MOVIE = "Movie";
    protected static String HERO_L = "520x293";
    protected static String HERO_M = "300x169";
    protected static String HERO_S = "260x146";
    protected static String HERO_XL = "1920x1080";
    protected static String POSTER_L = "640x960";
    protected static String POSTER_M = "200x300";
    protected static String POSTER_S = "110x165";
    
    /**
     * MetadataToContentMapper's constructor.
     */
    public ContentMapper() {
    	this.contentType = new ContentType();
    	this.countryType = new Country();
    	this.genreType = new GenreType();
    }
    
    /**
     * Transforms HBO objects to content objects. 
     * @param hboMetadata
     * @return Content.
     * @throws Exception 
     */
    public abstract Content map(Metadata hboMetadata) throws Exception;
    
    /**
     * Gets the content from HBO document.
     * @param hboMetadata
     * @return New Content.
     * @throws InvalidContentException
     */
    public Content getContentFromMetadata(Metadata hboMetadata, Content content) throws InvalidContentException {
    	try {
    		logger.debug("Gets the content from HBO document.");
    		
    		contentId = new ContentId();
            contentId.setContentId(hboMetadata.getFeature().getUniqueId());
            content.setContentId(contentId);
            logger.debug("Content Identifier: " + contentId.getContentId());
            
        	if((hboMetadata.getFeature().getDescription() == null) || (hboMetadata.getFeature().getDescription().isEmpty())) {
        		throw new InvalidContentException("The content " + content.getContentId().getContentId() + " doesn't have descriptions.");
        	}
        	
        	if((hboMetadata.getFeature().getVideos() == null)) {
        		throw new InvalidContentException("The content " + content.getContentId().getContentId() + " doesn't have video.");
        	}
        	
        	if((hboMetadata.getFeature().getDescription().get(0).getCategories() == null)) {
        		throw new InvalidContentException("The content " + content.getContentId().getContentId() + " doesn't have category.");
        	}
        	
    		contentDescription = hboMetadata.getFeature().getDescription().get(0);
    		contentVideos = hboMetadata.getFeature().getVideos();
    		content.setYear(contentDescription.getReleaseYear());
    		content.setContetType(contentType.getContentTypeByKey(contentDescription.getCategories().getCategory()));
    		
    		if(content.getContetType().equals(EPISODE)) {
    			if(hboMetadata.getFeature().getSeason() == null) {
            		throw new InvalidContentException("The content " + content.getContentId().getContentId() + " doesn't have season.");
            	}
    			contentSeason = hboMetadata.getFeature().getSeason();
    			content.setContentInSeason(contentDescription.getEpisodeNumber().getInSeason().intValue());
    			content.setContentInSerie(contentDescription.getEpisodeNumber().getInSeries().intValue());
    			content.setSeasonNumber(contentSeason.getSeasonNumber().intValue());
    		}
    		
    		content.setRuntime(contentVideos.getVideo().getRuntime().intValue());
    		Format format = getVideoFormat(content);
        	content.setManifestUrl(format.getValue());
        	content.setDateModified(Instant.now().minusSeconds(18000).toString());
        	
        	logger.debug("The content was gotten from HBO document.");
    	}
    	catch(Exception ex) {
    		logger.error("An error occurred when trying to get the content: " + ex.getMessage());
    		throw ex;
    	}
    		
    	return content;
    }
    
    /**
     * Gets Content's description form HBO document.
     * @param hboMetadata
     * @return New content's description.
     * @throws InvalidContentException
     */
    public Set<Description> getDescriptionFromMetadata(Metadata hboMetadata, Content content) throws InvalidContentException {
    	Set<Description> descriptions = new HashSet<Description>(0);
    	logger.debug("Gets the content's description from HBO document.");
    	try {
        	if((hboMetadata.getFeature().getDescription()==null) || (hboMetadata.getFeature().getDescription().isEmpty())) {
        		throw new InvalidContentException("The content " + content.getContentId().getContentId() + " doesn't have descriptions.");
        	}
        	
        	for(com.tigoune.hbo.model.metadata.Description description : hboMetadata.getFeature().getDescription()) {
        		Description contentDescription = new Description();
        		DescriptionId descriptionId = new DescriptionId();
        		descriptionId.setContentId(content.getContentId());
        		descriptionId.setDescriptionLanguage(description.getLanguage().toUpperCase());
        		contentDescription.setDescriptionId(descriptionId);
        		contentDescription.setTitle(description.getTitle());
        		contentDescription.setSynopsis(description.getSynopsis());
        		contentDescription.setShortSynopsis(description.getShortSynopsis());
        		contentDescription.setOriginalLanguageTitle(description.getOriginalLanguageTitle());
        		contentDescription.setContent(content);
        		descriptions.add(contentDescription);
        	}	
        	
        	logger.debug("The content's description was gotten from HBO document.");
    	}
    	catch(Exception ex) {
    		logger.error("An error occurred when trying to get the content's description: " + ex.getMessage());
    		throw ex;
    	}
    	
    	return descriptions;
    }
    
    /**
     * Gets content's offerings from HBO document.
     * @param hboMetadata
     * @return New content's offering.
     * @throws Exception 
     */
    public Offering getOfferingFromMetadata(Metadata hboMetadata, Content content) throws Exception {
    	Offering contentOffering = new Offering();
    	OfferingId offeringId = new OfferingId();
    	logger.debug("Gets the content's description from HBO document.");
    	try {
    		offeringId.setContentId(content.getContentId());
        	if(hboMetadata.getFeature().getOffering() == null) {
        		throw new InvalidContentException("The content " + content.getContentId().getContentId() + " doesn't have offerings.");
        	}
        	offering = hboMetadata.getFeature().getOffering();
        	offeringId.setOfferingId(offering.getUniqueId());
        	
        	if(offering.getPublishCountries() == null) {
        		throw new InvalidContentException("The content " + content.getContentId().getContentId() + " doesn't have publish countries.");
        	}
        	
        	offeringId.setCountryId(countryType.getCountryTypeByKey(offering.getPublishCountries().getCountry().getId()));
        	contentOffering.setContent(content);
        	contentOffering.setOfferingId(offeringId);
        	if(offering.getStartDate()==null) 
        		throw new InvalidContentException("The StartDate element has an invalid date format.");
        	contentOffering.setStartDateTime(offering.getStartDate().toString());
        	if(offering.getEndDate()==null) 
        		throw new InvalidContentException("The EndDate element has an invalid date format.");
        	contentOffering.setEndDateTime(offering.getEndDate().toString());
        	
        	logger.debug("The content's offering was gotten from HBO document.");
    	}
    	catch(Exception ex) {
    		logger.error("An error occurred when trying to get the content's offerings: " + ex.getMessage());
    		throw ex;
    	}
    	
    	return contentOffering;
    }
    
    /**
     * Gets content's links from HBO document. 
     * @param hboMetadata
     * @return content's links collection.
     * @throws InvalidContentException 
     */
    public Set<Link> getLinksFromMetadata(Metadata hboMetadata, Content content) throws InvalidContentException {
    	Set<Link> links = new HashSet<Link>(0);
    	try {
    		logger.debug("Gets the content's images from HBO document.");
    		
    		if((hboMetadata.getFeature().getVideos() == null)) {
        		throw new InvalidContentException("The content " + content.getContentId().getContentId() + " doesn't have video.");
        	}
    		
    		if((hboMetadata.getFeature().getVideos().getVideo().getImages() == null) || 
    				(hboMetadata.getFeature().getVideos().getVideo().getImages().getImage().isEmpty())) {
        		throw new InvalidContentException("The content " + content.getContentId().getContentId() + " doesn't have images.");
        	}
        	 
        	for(com.tigoune.hbo.model.metadata.Image image : hboMetadata.getFeature().getVideos().getVideo().getImages().getImage()) {
        		if((image.getImageResolution().equals("640x960")) && 
        				(image.getType().equals(EPISODE) || (image.getType().equals(MOVIE)))) {
        			links.add(createNewLink(POSTER_L,image,content));
        			links.add(createNewLink(POSTER_M,image,content));
        			links.add(createNewLink(POSTER_S,image,content));
        		}
        		if((image.getImageResolution().equals("1920x1080")) && 
        				(image.getType().equals(EPISODE) || (image.getType().equals(MOVIE)))) { 
        			links.add(createNewLink(HERO_XL,image,content));
        			links.add(createNewLink(HERO_S,image,content));
        			links.add(createNewLink(HERO_M,image,content));
        			links.add(createNewLink(HERO_L,image,content));
        		}
        	}	
        	logger.debug("The content's images were gotten from HBO document.");
    	}
    	catch(Exception ex) {
    		logger.error("An error occurred when trying to get the content's images: " + ex.getMessage());
    		throw ex;
    	}
    	
    	return links;
    }
    
    /**
     * Gets the content's parties from HBO document.
     * @param hboMetadata
     * @return content's parties collection.
     * @throws InvalidContentException
     */
    public Set<Party>  getPartiesFromMetadata(Metadata hboMetadata, Content content) throws InvalidContentException {
    	Set<Party> parties = new HashSet<Party>(0);
    	try {
    		logger.debug("Gets the content's parties from HBO document.");
    		
    		if(hboMetadata.getFeature().getParties() == null) {
        		throw new InvalidContentException("The content " + content.getContentId().getContentId() + " doesn't have parties.");
        	}
        	
        	for(com.tigoune.hbo.model.metadata.Party party : hboMetadata.getFeature().getParties().getParty()) {
        		Party contentParty = new Party();
        		PartyId partyId = new PartyId();
        		partyId.setContentId(content.getContentId());
        		partyId.setPartyUniqueId(party.getUniqueId());
        		if((party.getRoleType().equals("DIRECTOR")) || (party.getRoleType().equals("WRITER")))
        			partyId.setActorCharacter(party.getRoleType());
        		else
        			partyId.setActorCharacter(party.getCharacter());
        		contentParty.setPartyID(partyId);
        		contentParty.setContent(content);
        		contentParty.setFirstName(party.getFirstName());
        		contentParty.setLastName(party.getLastName());
        		contentParty.setRole(party.getRoleType());
        		parties.add(contentParty);
        	}	
        	logger.debug("The content's parties were gotten from HBO document.");
    	}
    	catch(Exception ex) {
    		logger.error("An error occurred when trying to get the content's parties: " + ex.getMessage());
    		throw ex;
    	}
    	
    	return parties;
    }
    
    /**
     * Gets the content's rating from HBO document.
     * @param hboMetadata
     * @return Content's rating.
     */
    public Rating getRatingFromMetadata(Metadata hboMetadata, Content content) {
    	Rating rating = new Rating();
    	RatingId ratingId = new RatingId();
    	try {
    		logger.debug("Gets the content's rating from HBO document.");
    		
        	ratingId.setContentId(content.getContentId());
        	if((hboMetadata.getFeature().getRatings() == null)) {
        		logger.warn("The content " + content.getContentId().getContentId() + " doesn't have rating");
        		return null;
        	}
        	else {
        		ratings = hboMetadata.getFeature().getRatings();
            	ratingId.setCountryId(countryType.getCountryTypeByKey(
            			hboMetadata.getFeature().getOffering().getPublishCountries().getCountry().getId())
            			);
            	ratingId.setRatingName(ratings.getRating().getValue());
            	rating.setRatingId(ratingId);
            	rating.setContent(content);
        	}
    	}
    	catch (Exception ex) {
    		logger.error("An error occurred when trying to get the content's rating: " + ex.getMessage());
    		throw ex;
    	}
    	finally {
    		logger.debug("The content's rating was gotten from HBO document.");
    	}
    	return rating;
    }
    
    /**
     * Gets the content's audio tracks from HBO document.
     * @param hboMetadata
     * @return content's audio tracks collection.
     * @throws InvalidContentException
     */
    public Set<AudioTrack> getAudioTrackFromMetadata(Metadata hboMetadata, Content content) throws InvalidContentException {
    	Set<AudioTrack> audioTracks = new HashSet<AudioTrack>(0);
    	try {
    		logger.debug("Gets the content's audio tracks from HBO document.");
    		
    		AudioTrack audioTrack = new AudioTrack();
        	AudioTrackId audioTrackId = new AudioTrackId();
        	audioTrackId.setContentId(content.getContentId());
        	
        	if((hboMetadata.getFeature().getVideos().getVideo().getAudioTracks() != null)) {
        		contentVideos = hboMetadata.getFeature().getVideos();
        		if(contentVideos.getVideo().getAudioTracks().getOriginal() != null) {
        			audioTrackId.setAudioLanguage(contentVideos.getVideo().getAudioTracks().getOriginal().getValue());
            		audioTrack.setAudioTrackId(audioTrackId);
            		audioTrack.setContent(content);
            		audioTrack.setIsOriginalLanguage(1);
            		audioTracks.add(audioTrack);	
        		}
        		
        		for(Localized localized : contentVideos.getVideo().getAudioTracks().getLocalized()) {
        			AudioTrack audioTrackLocalized = new AudioTrack();
        	    	AudioTrackId audioTrackLocalzedId = new AudioTrackId();
        	    	audioTrackLocalzedId.setContentId(content.getContentId());
        	    	audioTrackLocalzedId.setAudioLanguage(localized.getValue().toUpperCase());
        	    	audioTrackLocalized.setAudioTrackId(audioTrackLocalzedId);
        	    	audioTrackLocalized.setIsOriginalLanguage(0);
        	    	audioTrackLocalized.setContent(content);
        	    	audioTracks.add(audioTrackLocalized);
        		}
        	}
        	else
        		logger.warn("The content " + content.getContentId().getContentId() + " doesn't have audio tracks.");
    	}
    	catch (Exception ex) {
    		logger.error("An error occurred when trying to get the content's audio tracks: " + ex.getMessage());
    		throw ex;
    	}
		
    	return audioTracks;
    }
    
    /**
     * Gets the content's subtitles from HBO document.
     * @param hboMetadata
     * @return content's subtitles collection.
     */
    public Set<Subtitle> getSubtitlesFromMetadata(Metadata hboMetadata, Content content) {
    	Set<Subtitle> subtitles = new HashSet<Subtitle>(0);
    	try {
    		logger.debug("Gets the content's subtitles from HBO document.");
    		
    		if(hboMetadata.getFeature().getVideos().getVideo().getSubtitles() != null) {
        		for(String subtitleLanguage : hboMetadata.getFeature().getVideos().getVideo().getSubtitles().getSubtitleLanguage()) {
            		Subtitle subtitle = new Subtitle();
            		SubtitleId subtitleId = new SubtitleId();
            		subtitleId.setContentId(content.getContentId());
            		subtitleId.setSubtitleLanguage(subtitleLanguage.toUpperCase());
            		subtitle.setSubtitleID(subtitleId);
            		subtitle.setContent(content);
            		subtitles.add(subtitle);
            	}
        	}
    		logger.debug("The content's subtitles were gotten from HBO document.");
    	}
    	catch (Exception ex) {
    		logger.error("An error occurred when trying to get the content's subtitles: " + ex.getMessage());
    		throw ex;
    	}
    	return subtitles;
    }
    
    /**
     * Gets the content's genres from HBO document.
     * @param hboMetadata
     * @return content's genres collection.
     * @throws InvalidContentException
     */
    public Set<GenresOfContent> getGeneresFromMetadata(Metadata hboMetadata,Content content) throws InvalidContentException {
    	Set<GenresOfContent> genresOfContents = new HashSet<GenresOfContent>(0);
    	try {
        	if((hboMetadata.getFeature().getDescription().get(0).getGenres() == null) ||
        			(hboMetadata.getFeature().getDescription().get(0).getGenres().getGenre() == null) ||
        			(hboMetadata.getFeature().getDescription().get(0).getGenres().getGenre().isEmpty())) {
        		throw new InvalidContentException("The content " + content.getContentId().getContentId() + " doesn't have genres.");
        	}
        	
        	for(com.tigoune.hbo.model.metadata.Description description : hboMetadata.getFeature().getDescription()) {
        		for(Genre genre : description.getGenres().getGenre()) {
            		GenresOfContentId genresOfContentId = new GenresOfContentId();
                	GenresOfContent genresOfContent = new GenresOfContent();
                	genresOfContentId.setContentId(content.getContentId());
                	if(description.getLanguage().equals("SPA"))
                		genresOfContentId.setGenreId(genreType.getSpanishGenreTypeByKey(genre.getValue().toLowerCase()));
                	else if(description.getLanguage().equals("ENG"))
                		genresOfContentId.setGenreId(genreType.getEnglishGenreTypeByKey(genre.getValue().toLowerCase()));
                	else if(description.getLanguage().equals("POR"))
                		genresOfContentId.setGenreId(genreType.getPortugueseGenreTypeByKey(genre.getValue().toLowerCase()));
                	if(genresOfContentId.getGenreId()==0)
                		throw new InvalidContentException("The content " + content.getContentId().getContentId() + " has an invalid genre.");
                	genresOfContent.setContent(content);
                	genresOfContent.setGenresOfContentID(genresOfContentId);
                	genresOfContents.add(genresOfContent);	
            	}
        	}
        	
        	
    	}
    	catch (Exception ex) {
    		logger.error("An error occurred when trying to get the content's genres: " + ex.getMessage());
    		throw ex;
    	}
    	
    	return genresOfContents;
    }
    
    /**
     * Gets the content's seasons from HBO document.
     * @param hboMetadata
     * @return Content's rating.
     * @throws InvalidContentException 
     */
    public Set<ContentSeason> getSeasonsFromMetadata(Metadata hboMetadata, Content content) throws InvalidContentException {
    	Set<ContentSeason> contentSeasons = new HashSet<ContentSeason>(0);
    	try {
    		logger.debug("Gets the content's season from HBO document.");
    		
    		if(hboMetadata.getFeature().getSeason() == null) {
        		throw new InvalidContentException("The content " + content.getContentId().getContentId() + " doesn't have season.");
        	}
    		
    		ContentSeason contentSeason = new ContentSeason();
    		ContentSeasonId contentSeasonId = new ContentSeasonId();
    		contentSeasonId.setContentId(content.getContentId());
    		contentSeasonId.setSeasonNumber(hboMetadata.getFeature().getSeason().getSeasonNumber().intValue());
    		contentSeason.setContentSeasonsId(contentSeasonId);
    		contentSeason.setContent(content);
    		contentSeasons.add(contentSeason);
    	}
    	catch (Exception ex) {
    		logger.error("An error occurred when trying to get the content's season: " + ex.getMessage());
    		throw ex;
    	}
    	finally {
    		logger.debug("The content's season was gotten from HBO document.");
    	}
    	return contentSeasons;
    }

    /**
     * Creates a new link.
     * @param resolution
     * @param image
     * @return New link.
     */
	public Link createNewLink(String resolution,com.tigoune.hbo.model.metadata.Image image, Content content) {
		LinkId linkId = new LinkId();
		linkId.setContentId(content.getContentId());
		linkId.setResolution(resolution);
		Link link = new Link();
		link.setContent(content);
		link.setLinkId(linkId);
		link.setLinkUrlOriginal(image.getUrl());
		return link;
	}
	
	/**
	 * Gets the video format.
	 * @return Video Format.
	 * @throws InvalidContentException 
	 */
	private Format getVideoFormat(Content content) throws InvalidContentException {
		try {
			return contentVideos.getVideo().getFormats().getFormat().stream()
                    .filter(x -> x.getManifestNumber().equals("w8") && x.getPackaging().equals("SS")).findFirst().get();	
		}
		catch (IllegalStateException ex) {
			throw new InvalidContentException("The content " + content.getContentId().getContentId() + " doesn't have a valid video format.");
		}
		catch(NoSuchElementException ex) {
			throw new InvalidContentException("The content " + content.getContentId().getContentId() + " doesn't have a valid video format.");
		}
	}
}
