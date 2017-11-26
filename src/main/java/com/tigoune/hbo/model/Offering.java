package com.tigoune.hbo.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "offerings")
public class Offering implements java.io.Serializable {
    
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private OfferingId offeringId;
    
    @Column(name = "startDateTime", nullable = false, columnDefinition = "DATETIME")
    private String startDateTime;
    
    @Column(name = "endDateTime", nullable = true, columnDefinition = "DATETIME")
    private String endDateTime;
    
    
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
    public void Content(Content content) {
            this.setContent(content);
    }

	/**
	 * @return the offeringId
	 */
	public OfferingId getOfferingId() {
		return offeringId;
	}

	/**
	 * @param offeringId the offeringId to set
	 */
	public void setOfferingId(OfferingId offeringId) {
		this.offeringId = offeringId;
	}

	/**
	 * @return the startDateTime
	 */
	public String getStartDateTime() {
		return startDateTime;
	}

	/**
	 * @param startDateTime the startDateTime to set
	 */
	public void setStartDateTime(String startDateTime) {
		this.startDateTime = startDateTime;
	}

	/**
	 * @return the endDateTime
	 */
	public String getEndDateTime() {
		return endDateTime;
	}

	/**
	 * @param endDateTime the endDateTime to set
	 */
	public void setEndDateTime(String endDateTime) {
		this.endDateTime = endDateTime;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(Content content) {
		this.content = content;
	}
}
