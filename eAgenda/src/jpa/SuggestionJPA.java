/*
 * Copyright UOC.  All rights reserved.
 */
package jpa;
import java.io.Serializable;

import javax.persistence.*;

/**
 * JPA Class SuggestionJPA
 */
@Entity
@Table(name="infogroup2eagenda.suggestion")
public class SuggestionJPA implements Serializable {
	private static final long serialVersionUID = 1L;

	//OBLIGATORI SINO HIBERNATE NO UTILITZA ELS NUMEROS DE SEQUENCIA DE POSTGRE
	@Id
    @SequenceGenerator(name="infogroup2eagenda.suggestion_id_seq", sequenceName="infogroup2eagenda.suggestion_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="infogroup2eagenda.suggestion_id_seq")
	@Column(name="id", unique=true, nullable=false)
	private int id;
	private String text;
	private String email;
	
	@ManyToOne	
	private EventJPA eventSuggestion;
	
	@ManyToOne	
	private UserJPA userSuggestion;
	
	/**
	 * Class constructor methods
	 */
	public SuggestionJPA() {
		
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public EventJPA getEventSuggestion() {
		return eventSuggestion;
	}

	public void setEventSuggestion(EventJPA eventSuggestion) {
		this.eventSuggestion = eventSuggestion;
	}

	public UserJPA getUserSuggestion() {
		return userSuggestion;
	}

	public void setUserSuggestion(UserJPA userSuggestion) {
		this.userSuggestion = userSuggestion;
	}

	/**
	 *  Metodes per acabar el POJO
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SuggestionJPA other = (SuggestionJPA) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SuggestionJPA [id=" + id + ", text=" + text + ", email="
				+ email + "]";
	}
}