package com.tigoune.hbo.model;

import java.io.Serializable;
import javax.persistence.Embeddable;

@Embeddable
public class GenresOfContentId implements Serializable {

	private static final long serialVersionUID = 1L;

	private ContentId contentId;
	
	private int genreId;

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
	 * @return the genreId
	 */
	public int getGenreId() {
		return genreId;
	}

	/**
	 * @param genreId the genreId to set
	 */
	public void setGenreId(int genreId) {
		this.genreId = genreId;
	}
}
