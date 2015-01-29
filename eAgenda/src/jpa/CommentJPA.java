/*
 * Copyright UOC.  All rights reserved.
 */
package jpa;
import java.io.Serializable;

import javax.persistence.*;

/**
 * JPA Class CommentJPA
 */
@Entity
@Table(name="infogroup2eagenda.comment")
public class CommentJPA implements Serializable {
	private static final long serialVersionUID = 1L;

	//OBLIGATORI SINO HIBERNATE NO UTILITZA ELS NUMEROS DE SEQUENCIA DE POSTGRE
	@Id
    @SequenceGenerator(name="infogroup2eagenda.comment_id_seq", sequenceName="infogroup2eagenda.comment_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="infogroup2eagenda.comment_id_seq")
	@Column(name="id", unique=true, nullable=false)
	private int id;
	private String text;
	
	@ManyToOne	
	private EventJPA eventComment;
	
	@ManyToOne	
	private UserJPA userComment;
	
	/**
	 * Class constructor methods
	 */
	public CommentJPA() {
		
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
	
	public EventJPA getEventComment() {
		return eventComment;
	}

	public void setEventComment(EventJPA eventComment) {
		this.eventComment = eventComment;
	}
	
	public UserJPA getUserComment() {
		return userComment;
	}
	
	public void setUserComment(UserJPA userComment) {
		this.userComment = userComment;
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
		CommentJPA other = (CommentJPA) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CommentJPA [id=" + id + ", text=" + text + "]";
	}
}