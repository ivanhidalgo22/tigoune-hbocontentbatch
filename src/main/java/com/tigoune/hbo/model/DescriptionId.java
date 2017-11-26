package com.tigoune.hbo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author ivan
 *
 */
@Embeddable
public class DescriptionId implements Serializable{
	
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    private ContentId contentId;
    
    @Column(name = "descriptionLanguage", nullable = false, columnDefinition = "VARCHAR(3)")
    private String descriptionLanguage;

    public ContentId getContentId() {
            return contentId;
    }

    public void setContentId(ContentId contentId) {
            this.contentId = contentId;
    }

    /**
     * @return the DescriptionLanguage
     */
    public String getDescriptionLanguage() {
            return descriptionLanguage;
    }

    /**
     * @param title the DescriptionLanguage to set
     */
    public void setDescriptionLanguage(String descriptionLanguage) {
            this.descriptionLanguage = descriptionLanguage;
    }
}
