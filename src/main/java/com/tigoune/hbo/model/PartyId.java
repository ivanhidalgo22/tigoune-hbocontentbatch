package com.tigoune.hbo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author ivan
 *
 */
@Embeddable
public class PartyId implements Serializable{

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    @Column(name = "partyUniqueId", nullable = false, columnDefinition = "VARCHAR(50)")
    private String partyUniqueId;
    
    @Column(name = "actorCharacter", nullable = false, columnDefinition = "VARCHAR(30)")
    private String actorCharacter;

    private ContentId contentId;

    public ContentId getContentId() {
            return contentId;
    }

    public void setContentId(ContentId contentId) {
            this.contentId = contentId;
    }

    /**
     * @return the urn
     */
    public String getPartyUniqueId() {
            return partyUniqueId;
    }

    /**
     * @param urn the urn to set
     */
    public void setPartyUniqueId(String  partyUniqueId) {
            this.partyUniqueId = partyUniqueId;
    }
    
	/**
	 * @return the actorCharacter
	 */
	public String getActorCharacter() {
		return actorCharacter;
	}

	/**
	 * @param actorCharacter the actorCharacter to set
	 */
	public void setActorCharacter(String actorCharacter) {
		this.actorCharacter = actorCharacter;
	}
}
