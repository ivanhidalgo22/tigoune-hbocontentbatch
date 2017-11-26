package com.tigoune.hbo.model;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "contentseasons")
public class ContentSeason {
	
	@EmbeddedId
	private ContentSeasonId contentSeasonsId;

	@MapsId("contentId")
    @JoinColumns({
        @JoinColumn(name="contentId", referencedColumnName="contentId")
    })
    @ManyToOne(cascade=CascadeType.REMOVE)
	private Content content;
    
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
	 * @return the contentSeasonsId
	 */
	public ContentSeasonId getContentSeasonsId() {
		return contentSeasonsId;
	}

	/**
	 * @param contentSeasonsId the contentSeasonsId to set
	 */
	public void setContentSeasonsId(ContentSeasonId contentSeasonsId) {
		this.contentSeasonsId = contentSeasonsId;
	}
	
}
