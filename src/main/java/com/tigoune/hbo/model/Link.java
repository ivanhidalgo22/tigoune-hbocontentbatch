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
@Table(name = "links")
public class Link implements java.io.Serializable {
    
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private LinkId linkId; 
    
    @Column(name = "linkUrlOriginal", nullable = false, columnDefinition = "VARCHAR(2000)")
    private String linkUrlOriginal;
    
    @Column(name = "linkUrlS3", nullable = true, columnDefinition = "VARCHAR(2000)")
    private String linkUrlS3;
    
    @Column(name = "linkUrlCdn", nullable = true, columnDefinition = "VARCHAR(2000)")
    private String linkUrlCdn;
    
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
    public void Content(Content content) {
            this.setContent(content);
    }

    /**
     * @return the linkId
     */
    public LinkId getLinkId() {
            return linkId;
    }

    /**
     * @param linkId the linkId to set
     */
    public void setLinkId(LinkId linkId) {
            this.linkId = linkId;
    }

	/**
	 * @return the linkUrlS3
	 */
	public String getLinkUrlS3() {
		return linkUrlS3;
	}

	/**
	 * @param linkUrlS3 the linkUrlS3 to set
	 */
	public void setLinkUrlS3(String linkUrlS3) {
		this.linkUrlS3 = linkUrlS3;
	}

	/**
	 * @return the linkUrlCdn
	 */
	public String getLinkUrlCdn() {
		return linkUrlCdn;
	}

	/**
	 * @param linkUrlCdn the linkUrlCdn to set
	 */
	public void setLinkUrlCdn(String linkUrlCdn) {
		this.linkUrlCdn = linkUrlCdn;
	}

	/**
	 * @return the linkUrlOringinal
	 */
	public String getLinkUrlOriginal() {
		return linkUrlOriginal;
	}

	/**
	 * @param linkUrlOringinal the linkUrlOringinal to set
	 */
	public void setLinkUrlOriginal(String linkUrlOringinal) {
		this.linkUrlOriginal = linkUrlOringinal;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(Content content) {
		this.content = content;
	}
}
