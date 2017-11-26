package com.tigoune.hbo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AudioTrackId implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "audioLanguage", nullable = false, columnDefinition = "VARCHAR(3)")
    private String audioLanguage;
	
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
	 * @return the audioLanguage
	 */
	public String getAudioLanguage() {
		return audioLanguage;
	}

	/**
	 * @param audioLanguage the audioLanguage to set
	 */
	public void setAudioLanguage(String audioLanguage) {
		this.audioLanguage = audioLanguage;
	}	
    

	
	
}
