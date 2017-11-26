package com.tigoune.hbo.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ratings")
public class Rating implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
    private RatingId ratingId; 
	
	@ManyToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="contentId", insertable= false, updatable= false)
	private Content content;
	
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
	 * @return the ratingId
	 */
	public RatingId getRatingId() {
		return ratingId;
	}

	/**
	 * @param ratingId the ratingId to set
	 */
	public void setRatingId(RatingId ratingId) {
		this.ratingId = ratingId;
	}
	
}
