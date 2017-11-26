package com.tigoune.hbo.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "descriptions")
public class Description implements java.io.Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DescriptionId descriptionId;
     
    @Column(name = "title", nullable = false, columnDefinition = "VARCHAR(255)")
    private String title;
    
    @Column(name = "synopsis", nullable = false, columnDefinition = "LONGTEXT")
    private String synopsis;
    
    @Column(name = "originalLanguageTitle", nullable = true, columnDefinition = "VARCHAR(255)")
    private String originalLanguageTitle;
    
    @Column(name = "shortSynopsis", nullable = true, columnDefinition = "MEDIUMTEXT")
    private String shortSynopsis;
    
    @MapsId("contentId")
    @JoinColumns({
        @JoinColumn(name="contentId", referencedColumnName="contentId")
    })
    @ManyToOne
	private Content content;
    
    /**
     * @return the DescriptionId
     */
    public DescriptionId getDescriptionId() {
            return descriptionId;
    }

    /**
     * @param metadataId the DescriptionId to set
     */
    public void setDescriptionId(DescriptionId metadataId) {
            this.descriptionId = metadataId;
    }

    /**
     * @return the content
     */
    public Content getContent() {
            return content;
    }

    /**
     * @param content the movieContent to set
     */
    public void setContent(Content content) {
            this.content = content;
    }

    /**
     * @return the synopsis
     */
    public String getSynopsis() {
            return synopsis;
    }

    /**
     * @param synopsis the synopsis to set
     */
    public void setSynopsis(String synopsis) {
            this.synopsis = synopsis;
    }

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the originalLanguageTitle
	 */
	public String getOriginalLanguageTitle() {
		return originalLanguageTitle;
	}

	/**
	 * @param originalLanguageTitle the originalLanguageTitle to set
	 */
	public void setOriginalLanguageTitle(String originalLanguageTitle) {
		this.originalLanguageTitle = originalLanguageTitle;
	}

	/**
	 * @return the shortSynopsis
	 */
	public String getShortSynopsis() {
		return shortSynopsis;
	}

	/**
	 * @param shortSynopsis the shortSynopsis to set
	 */
	public void setShortSynopsis(String shortSynopsis) {
		this.shortSynopsis = shortSynopsis;
	}
	
}
