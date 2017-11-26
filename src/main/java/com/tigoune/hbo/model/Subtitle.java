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
@Table(name = "subtitles")
public class Subtitle implements java.io.Serializable {
    
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private SubtitleId subtitleID;

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
	 * @return the subtitleID
	 */
	public SubtitleId getSubtitleID() {
		return subtitleID;
	}

	/**
	 * @param subtitleID the subtitleID to set
	 */
	public void setSubtitleID(SubtitleId subtitleID) {
		this.subtitleID = subtitleID;
	}
	
	

}
