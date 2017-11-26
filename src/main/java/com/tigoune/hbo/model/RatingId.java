package com.tigoune.hbo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RatingId implements Serializable {

	private static final long serialVersionUID = 1L;

	private ContentId contentId;
	
	@Column(columnDefinition = "VARCHAR(20)")
	private String countryId;
	
	@Column(columnDefinition = "VARCHAR(5)")
	private String ratingName;

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
	 * @return the countryId
	 */
	public String getCountryId() {
		return countryId;
	}

	/**
	 * @param countryId the countryId to set
	 */
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	/**
	 * @return the ratingName
	 */
	public String getRatingName() {
		return ratingName;
	}

	/**
	 * @param ratingName the ratingName to set
	 */
	public void setRatingName(String ratingName) {
		this.ratingName = ratingName;
	}
}
