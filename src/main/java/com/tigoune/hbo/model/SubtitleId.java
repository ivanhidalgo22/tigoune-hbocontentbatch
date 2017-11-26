package com.tigoune.hbo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SubtitleId implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "subtitleLanguage", nullable = false, columnDefinition = "VARCHAR(3)")
    private String subtitleLanguage;
	
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
	 * @return the subtitleLanguage
	 */
	public String getSubtitleLanguage() {
		return subtitleLanguage;
	}

	/**
	 * @param subtitleLanguage the subtitleLanguage to set
	 */
	public void setSubtitleLanguage(String subtitleLanguage) {
		this.subtitleLanguage = subtitleLanguage;
	}

	
	

}
