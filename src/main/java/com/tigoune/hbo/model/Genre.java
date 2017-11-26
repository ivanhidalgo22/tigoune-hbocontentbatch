package com.tigoune.hbo.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "genres")
public class Genre {
	
	@EmbeddedId
	private GenreId genreId;
	
	@Column(name = "genreName", nullable = false, columnDefinition = "VARCHAR(255)")
	private String genreName;
	
	@OneToMany(mappedBy="genre")
	private Set<GenresOfContent> genresOfContent = new HashSet<GenresOfContent>(0);
	
	@Column(name = "genreLanguage", nullable = false, columnDefinition = "VARCHAR(3)")
	private String genreLanguage;

	/**
	 * @return the genreId
	 */
	public GenreId getGenreId() {
		return genreId;
	}

	/**
	 * @param genreId the genreId to set
	 */
	public void setGenreId(GenreId genreId) {
		this.genreId = genreId;
	}

	/**
	 * @return the genreName
	 */
	public String getGenreName() {
		return genreName;
	}

	/**
	 * @param genreName the genreName to set
	 */
	public void setGenreName(String genreName) {
		this.genreName = genreName;
	}

	/**
	 * @return the genresOfContent
	 */
	public Set<GenresOfContent> getGenresOfContent() {
		return genresOfContent;
	}

	/**
	 * @param genresOfContent the genresOfContent to set
	 */
	public void setGenresOfContent(Set<GenresOfContent> genresOfContent) {
		this.genresOfContent = genresOfContent;
	}

	/**
	 * @return the genreLanguage
	 */
	public String getGenreLanguage() {
		return genreLanguage;
	}

	/**
	 * @param genreLanguage the genreLanguage to set
	 */
	public void setGenreLanguage(String genreLanguage) {
		this.genreLanguage = genreLanguage;
	}
}
