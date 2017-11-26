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
@Table(name = "parties")
public class Party implements java.io.Serializable {

    /**
     *  serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private PartyId partyID;

    @MapsId("contentId")
    @JoinColumns({
        @JoinColumn(name="contentId", referencedColumnName="contentId")
    })
    @ManyToOne(cascade=CascadeType.REMOVE)
    private Content content;

    @Column(name = "firstName", nullable = false, columnDefinition = "VARCHAR(45)")
    private String firstName;
    
    @Column(name = "lastName", nullable = false, columnDefinition = "VARCHAR(45)")
    private String lastName;
    
    @Column(name = "role", nullable = false, columnDefinition = "VARCHAR(10)")
    private String role;

    /**
     * @return the movieContent
     */
    public Content getContent() {
            return content;
    }

    /**
     * @param movieContent the movieContent to set
     */
    public void setContent(Content content) {
            this.content = content;
    }

	/**
	 * @return the partyID
	 */
	public PartyId getPartyID() {
		return partyID;
	}

	/**
	 * @param partyID the partyID to set
	 */
	public void setPartyID(PartyId partyID) {
		this.partyID = partyID;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * @return the rol
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param rol the rol to set
	 */
	public void setRole(String role) {
		this.role = role;
	}
}
