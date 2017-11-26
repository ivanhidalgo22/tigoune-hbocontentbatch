//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.04.11 at 08:11:43 AM COT 
//


package com.tigoune.hbo.model.metadata;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "releaseYear",
    "categories",
    "genres",
    "title",
    "originalLanguageTitle",
    "serieTitle",
    "synopsis",
    "shortSynopsis",
    "episodeNumber"
})
@XmlRootElement(name = "description")
public class Description {

    @XmlElement(name = "release-year")
    @XmlSchemaType(name = "unsignedShort")
    protected Integer releaseYear;
    protected Categories categories;
    protected Genres genres;
    protected String title;
    @XmlElement(name = "original-language-title")
    protected String originalLanguageTitle;
    
    @XmlElement(name = "Title")
	protected String serieTitle;
    
    protected String synopsis;
    @XmlElement(name = "short-synopsis")
    protected String shortSynopsis;
    @XmlElement(name = "episode-number")
    protected EpisodeNumber episodeNumber;
    @XmlAttribute(name = "language", required = true)
    protected String language;

    /**
     * Gets the value of the releaseYear property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getReleaseYear() {
        return releaseYear;
    }

    /**
     * Sets the value of the releaseYear property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setReleaseYear(Integer value) {
        this.releaseYear = value;
    }

    /**
     * Gets the value of the categories property.
     * 
     * @return
     *     possible object is
     *     {@link Metadata.Feature.Description.Categories }
     *     
     */
    public Categories getCategories() {
        return categories;
    }

    /**
     * Sets the value of the categories property.
     * 
     * @param value
     *     allowed object is
     *     {@link Metadata.Feature.Description.Categories }
     *     
     */
    public void setCategories(Categories value) {
        this.categories = value;
    }

    /**
     * Gets the value of the genres property.
     * 
     * @return
     *     possible object is
     *     {@link Metadata.Feature.Description.Genres }
     *     
     */
    public Genres getGenres() {
        return genres;
    }

    /**
     * Sets the value of the genres property.
     * 
     * @param value
     *     allowed object is
     *     {@link Metadata.Feature.Description.Genres }
     *     
     */
    public void setGenres(Genres value) {
        this.genres = value;
    }

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * Gets the value of the originalLanguageTitle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOriginalLanguageTitle() {
        return originalLanguageTitle;
    }

    /**
     * Sets the value of the originalLanguageTitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOriginalLanguageTitle(String value) {
        this.originalLanguageTitle = value;
    }

    /**
     * Gets the value of the synopsis property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSynopsis() {
        return synopsis;
    }

    /**
     * Sets the value of the synopsis property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSynopsis(String value) {
        this.synopsis = value;
    }

    /**
     * Gets the value of the shortSynopsis property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShortSynopsis() {
        return shortSynopsis;
    }

    /**
     * Sets the value of the shortSynopsis property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShortSynopsis(String value) {
        this.shortSynopsis = value;
    }

    /**
     * Gets the value of the episodeNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Metadata.Feature.Description.EpisodeNumber }
     *     
     */
    public EpisodeNumber getEpisodeNumber() {
        return episodeNumber;
    }

    /**
     * Sets the value of the episodeNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Metadata.Feature.Description.EpisodeNumber }
     *     
     */
    public void setEpisodeNumber(EpisodeNumber value) {
        this.episodeNumber = value;
    }

    /**
     * Gets the value of the language property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Sets the value of the language property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLanguage(String value) {
        this.language = value;
    }

	/**
	 * @return the serieTitle
	 */
	public String getSerieTitle() {
		return serieTitle;
	}

	/**
	 * @param serieTitle the serieTitle to set
	 */
	public void setSerieTitle(String serieTitle) {
		this.serieTitle = serieTitle;
	}
}
