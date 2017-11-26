package com.tigoune.hbo.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "contents")
public class Content implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
    private ContentId contentId;
	
    @Column(name = "contentType", nullable = false, columnDefinition = "VARCHAR(50)")
    private String contentType;
    
    @Column(name = "year", nullable = true)
    private int year; 
    
    @Column(name = "manifestUrl", nullable = true, columnDefinition = "VARCHAR(2000)")
    private String manifestUrl;
    
    @Column(name = "runtime", nullable = true)
    private int runtime;
    
    @Column(name = "contentInSeason", nullable = true)
    private int contentInSeason;
    
    @Column(name = "contentInSerie", nullable = true)
    private int contentInSerie;
    
    @Column(name = "seasonNumber", nullable = true)
    private int seasonNumber;
        
    @Column(name = "dateModified", nullable = true)
    private String dateModified;
    
    @ManyToOne
    @JoinColumn(name = "contentParentId")
    private Content parentContent;

    @OneToMany(mappedBy = "parentContent", cascade = CascadeType.ALL)
    private Set<Content> contentChilds = new HashSet<Content>(0);

    @OneToMany( mappedBy = "content", cascade=CascadeType.ALL)
    private Set<Link> links = new HashSet<Link>(0);
    
    @OneToMany(mappedBy="content",cascade=CascadeType.ALL)
    private Set<Rating> ratings = new HashSet<Rating>(0);
    
    @OneToMany(mappedBy="content",cascade=CascadeType.ALL)
    private Set<Description> descriptions = new HashSet<Description>(0);

    @OneToMany( mappedBy = "content", cascade=CascadeType.ALL)
    private Set<Party> parties = new HashSet<Party>(0);
    
    @OneToMany( mappedBy = "content", cascade=CascadeType.ALL)
    private Set<Offering> offerings = new HashSet<Offering>(0);
    
    @OneToMany( mappedBy = "content", cascade=CascadeType.ALL)
    private Set<AudioTrack> audioTracks = new HashSet<AudioTrack>(0);
    
    @OneToMany( mappedBy = "content", cascade=CascadeType.ALL)
    private Set<Subtitle> subtitles = new HashSet<Subtitle>(0);
    
    @OneToMany(mappedBy="content", cascade=CascadeType.ALL)
	private Set<GenresOfContent> genresOfContent = new HashSet<GenresOfContent>(0);
    
    @OneToMany( mappedBy = "content", cascade=CascadeType.ALL)
    private Set<ContentSeason> contentSeasons = new HashSet<ContentSeason>(0);

    public Set<Link> getLinks() {
        return links;
    }

    public void setLinks(Set<Link> links) {
        this.links = links;
    }

    /**
     * @return the dateModified
     */
    public String getDateModified() {
            return dateModified;
    }

    /**
     * @param dateModified the dateModified to set
     */
    public void setDateModified(String dateModified) {
            this.dateModified = dateModified;
    }

    /**
     * @return the contetType
     */
    public String getContetType() {
            return contentType;
    }

    /**
     * @param contetType the contetType to set
     */
    public void setContetType(String contetType) {
            this.contentType = contetType;
    }

    /**
     * @return the contentId
     */
    public ContentId getContentId() {
            return contentId;
    }

    /**
     * @param contentId the contentId to set
     */
    public void setContentId(ContentId contentId) {
            this.contentId = contentId;
    }

	/**
	 * @return the ratings
	 */
	public Set<Rating> getRatings() {
		return ratings;
	}

	/**
	 * @param ratings the ratings to set
	 */
	public void setRatings(Set<Rating> ratings) {
		this.ratings = ratings;
	}

	/**
	 * @return the descriptions
	 */
	public Set<Description> getDescriptions() {
		return descriptions;
	}

	/**
	 * @param descriptions the descriptions to set
	 */
	public void setDescriptions(Set<Description> descriptions) {
		this.descriptions = descriptions;
	}

	/**
	 * @return the parties
	 */
	public Set<Party> getParties() {
		return parties;
	}

	/**
	 * @param parties the parties to set
	 */
	public void setParties(Set<Party> parties) {
		this.parties = parties;
	}

	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * @return the manifestUrl
	 */
	public String getManifestUrl() {
		return manifestUrl;
	}

	/**
	 * @param manifestUrl the manifestUrl to set
	 */
	public void setManifestUrl(String manifestUrl) {
		this.manifestUrl = manifestUrl;
	}

	/**
	 * @return the runtime
	 */
	public int getRuntime() {
		return runtime;
	}

	/**
	 * @param runtime the runtime to set
	 */
	public void setRuntime(int runtime) {
		this.runtime = runtime;
	}

	/**
	 * @return the contentInSeason
	 */
	public int getContentInSeason() {
		return contentInSeason;
	}

	/**
	 * @param contentInSeason the contentInSeason to set
	 */
	public void setContentInSeason(int contentInSeason) {
		this.contentInSeason = contentInSeason;
	}

	/**
	 * @return the contentInSerie
	 */
	public int getContentInSerie() {
		return contentInSerie;
	}

	/**
	 * @param contentInSerie the contentInSerie to set
	 */
	public void setContentInSerie(int contentInSerie) {
		this.contentInSerie = contentInSerie;
	}

	/**
	 * @return the parentContent
	 */
	public Content getParentContent() {
		return parentContent;
	}

	/**
	 * @param parentContent the parentContent to set
	 */
	public void setParentContent(Content parentContent) {
		this.parentContent = parentContent;
	}

	/**
	 * @return the contentChilds
	 */
	public Set<Content> getContentChilds() {
		return contentChilds;
	}

	/**
	 * @param contentChilds the contentChilds to set
	 */
	public void setContentChilds(Set<Content> contentChilds) {
		this.contentChilds = contentChilds;
	}

	/**
	 * @return the offerings
	 */
	public Set<Offering> getOfferings() {
		return offerings;
	}

	/**
	 * @param offerings the offerings to set
	 */
	public void setOfferings(Set<Offering> offerings) {
		this.offerings = offerings;
	}

	/**
	 * @return the audioTracks
	 */
	public Set<AudioTrack> getAudioTracks() {
		return audioTracks;
	}

	/**
	 * @param audioTracks the audioTracks to set
	 */
	public void setAudioTracks(Set<AudioTrack> audioTracks) {
		this.audioTracks = audioTracks;
	}

	/**
	 * @return the subtitles
	 */
	public Set<Subtitle> getSubtitles() {
		return subtitles;
	}

	/**
	 * @param subtitles the subtitles to set
	 */
	public void setSubtitles(Set<Subtitle> subtitles) {
		this.subtitles = subtitles;
	}

	/**
	 * @return the genresOfContent
	 */
	public Set<GenresOfContent> getGenresOfContent() {
		return genresOfContent;
	}

	/**
	 * @param genresOfContent the genresOfContent to set
	 */
	public void setGenresOfContent(Set<GenresOfContent> genresOfContent) {
		this.genresOfContent = genresOfContent;
	}

	/**
	 * @return the seasonNumber
	 */
	public int getSeasonNumber() {
		return seasonNumber;
	}

	/**
	 * @param seasonNumber the seasonNumber to set
	 */
	public void setSeasonNumber(int seasonNumber) {
		this.seasonNumber = seasonNumber;
	}

	/**
	 * @return the contentSeasons
	 */
	public Set<ContentSeason> getContentSeasons() {
		return contentSeasons;
	}

	/**
	 * @param contentSeasons the contentSeasons to set
	 */
	public void setContentSeasons(Set<ContentSeason> contentSeasons) {
		this.contentSeasons = contentSeasons;
	}
}
