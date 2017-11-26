package com.tigoune.hbo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class LinkId implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "resolution", nullable = false, columnDefinition = "VARCHAR(20)")
    private String resolution;
	
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
     * @return the Resolution.
     */
    public String getResolution() {
            return resolution;
    }

    /**
     * @param movieUrl the Resolution to set
     */
    public void setResolution(String resolution) {
            this.resolution = resolution;
    }
}
