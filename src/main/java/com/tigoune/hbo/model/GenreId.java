package com.tigoune.hbo.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class GenreId implements Serializable {

	private static final long serialVersionUID = 1L;
	
    private int genreId;

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
