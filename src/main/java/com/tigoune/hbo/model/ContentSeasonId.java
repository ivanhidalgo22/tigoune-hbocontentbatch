package com.tigoune.hbo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ContentSeasonId implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "seasonNumber", nullable = false)
    private int seasonNumber;
	
    private ContentId contentId;

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
    
    

}
