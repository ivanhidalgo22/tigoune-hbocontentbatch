package com.tigoune.hbo.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "genresofcontents")
public class GenresOfContent {
	
	@EmbeddedId
	private GenresOfContentId genresOfContentID;
	
	@ManyToOne
	@JoinColumn(name="contentId", insertable=false, updatable=false)
	private Content content;
	
    @ManyToOne
	@JoinColumn(name="genreId", insertable=false, updatable=false)
	private Genre genre;	

	/**
	 * @return the content
	 */
	public Content getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(Content content) {
		this.content = content;
		}

	/**
	 * @return the genre
	 */
	public Genre getGenre() {
		return genre;
	}

	/**
	 * @param genre the genre to set
	 */
	public void setGenre(Genre genre) {
		this.genre = genre;
	}
	
	/**
	 * @return the genresOfContentID
	 */
	public GenresOfContentId getGenresOfContentID() {
		return genresOfContentID;
	}

	/**
	 * @param genresOfContentID the genresOfContentID to set
	 */
	public void setGenresOfContentID(GenresOfContentId genresOfContentID) {
		this.genresOfContentID = genresOfContentID;
	}

}
