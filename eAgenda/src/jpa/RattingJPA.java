/*
 * Copyright UOC.  All rights reserved.
 */
package jpa;
import java.io.Serializable;

import javax.persistence.*;

/**
 * JPA Class RattingJPA
 */
@Entity
@Table(name="infogroup2eagenda.ratting")
public class RattingJPA implements Serializable {
	private static final long serialVersionUID = 1L;

	//OBLIGATORI SINO HIBERNATE NO UTILITZA ELS NUMEROS DE SEQUENCIA DE POSTGRE
	@Id
    @SequenceGenerator(name="infogroup2eagenda.ratting_id_seq", sequenceName="infogroup2eagenda.ratting_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="infogroup2eagenda.ratting_id_seq")
	@Column(name="id", unique=true, nullable=false)
	private int id;
	private int ratting;
	
	@ManyToOne	
	private EventJPA eventRatting;
	
	@ManyToOne	
	private UserJPA userRatting;
	
	/**
	 * Class constructor methods
	 */
	public RattingJPA() {
		
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRatting() {
		return ratting;
	}

	public void setRatting(int ratting) {
		this.ratting = ratting;
	}

	public EventJPA getEventRatting() {
		return eventRatting;
	}

	public void setEventRatting(EventJPA eventRatting) {
		this.eventRatting = eventRatting;
	}

	public UserJPA getUserRatting() {
		return userRatting;
	}

	public void setUserRatting(UserJPA userRatting) {
		this.userRatting = userRatting;
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
		RattingJPA other = (RattingJPA) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RattingJPA [id=" + id + ", ratting=" + ratting + "]";
	}
}