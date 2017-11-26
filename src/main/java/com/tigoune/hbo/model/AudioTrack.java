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
@Table(name = "audiotracks")
public class AudioTrack implements java.io.Serializable {
    
    private static final long serialVersionUID = 1L;

    @EmbeddedId
	private AudioTrackId audioTrackId;
    
    @Column(name = "isOriginalLanguage", nullable = false)
    private int isOriginalLanguage;
    
    
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
	 * @return the audioTrackId
	 */
	public AudioTrackId getAudioTrackId() {
		return audioTrackId;
	}

	/**
	 * @param audioTrackId the audioTrackId to set
	 */
	public void setAudioTrackId(AudioTrackId audioTrackId) {
		this.audioTrackId = audioTrackId;
	}

	/**
	 * @return the isOriginalLanguage
	 */
	public int getIsOriginalLanguage() {
		return isOriginalLanguage;
	}

	/**
	 * @param isOriginalLanguage the isOriginalLanguage to set
	 */
	public void setIsOriginalLanguage(int isOriginalLanguage) {
		this.isOriginalLanguage = isOriginalLanguage;
	}

	
	
}
