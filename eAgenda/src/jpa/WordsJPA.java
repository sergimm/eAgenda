/*
 * Copyright UOC.  All rights reserved.
 */
package jpa;
import java.io.Serializable;


import javax.persistence.*;

/**
 * JPA Class WordsJPA
 */
@Entity
@Table(name="infogroup2eagenda.words")
public class WordsJPA implements Serializable {
	private static final long serialVersionUID = 1L;

	//OBLIGATORI SINO HIBERNATE NO UTILITZA ELS NUMEROS DE SEQUENCIA DE POSTGRE
	@Id
    @SequenceGenerator(name="infogroup2eagenda.words_id_seq", sequenceName="infogroup2eagenda.words_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="infogroup2eagenda.words_id_seq")
	@Column(name="id", unique=true, nullable=false)
	private int id;
	private String name;
	
	//Associacio bidireccional 1aN amb Event
	//S'escull Set com a colleccio ja que l'ordre no es important
	@ManyToOne	
	private EventJPA eventWords;
	/**
	 * Class constructor methods
	 */
	public WordsJPA() {
		
	}
	
	public WordsJPA(String name) {		
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public EventJPA getEvent() {
		return eventWords;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEventWords(EventJPA eventWords) {
		this.eventWords = eventWords;
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
		WordsJPA other = (WordsJPA) obj;
		if (id != other.id)
			return false;
		return true;
	}


	/**
	 *  toString() nomes per a debuggar
	 */
	@Override
	public String toString() {
		return "WordsJPA [id=" + id + ", name=" + name + "]";
	}
	
}