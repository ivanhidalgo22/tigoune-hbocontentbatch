package com.tigoune.hbo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class OfferingId implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "offeringId", nullable = false, columnDefinition = "VARCHAR(50)")
    private String offeringId;
	
	@Column(name = "countryId", nullable = false, columnDefinition = "VARCHAR(20)")
    private String countryId;
	  
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
	 * @return the offeringId
	 */
	public String getOfferingId() {
		return offeringId;
	}

	/**
	 * @param offeringId the offeringId to set
	 */
	public void setOfferingId(String offeringId) {
		this.offeringId = offeringId;
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
}
