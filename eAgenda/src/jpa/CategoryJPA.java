/*
 * Copyright UOC.  All rights reserved.
 */
package jpa;
import java.io.Serializable;

import javax.persistence.*;

/**
 * JPA Class CategoryJPA
 */
@Entity
@Table(name="infogroup2eagenda.category")
public class CategoryJPA implements Serializable {
	private static final long serialVersionUID = 1L;

	//OBLIGATORI SINO HIBERNATE NO UTILITZA ELS NUMEROS DE SEQUENCIA DE POSTGRE
	@Id
    @SequenceGenerator(name="infogroup2eagenda.category_id_seq", sequenceName="infogroup2eagenda.category_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="infogroup2eagenda.category_id_seq")
	@Column(name="id", unique=true, nullable=false)
	private int id;
	private String name;
	private String description;
	
	//Associacio bidireccional Na1 amb Superadministrator
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="superadministrator_id")
	private SuperadministratorJPA superadministrator;
	
	
	/**
	 * Class constructor methods
	 */
	public CategoryJPA() {
	}
	
	public CategoryJPA(String name, String description) {		
		this.name = name;
		this.description = description;
	}
	

	/**
	 *  Getters/Setters
	 */	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public SuperadministratorJPA getSuperadministrator() {
		return superadministrator;
	}

	public void setSuperadministrator(SuperadministratorJPA superadministrator) {
		this.superadministrator = superadministrator;
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
		CategoryJPA other = (CategoryJPA) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	/**
	 *  toString() nomes per a debuggar
	 */	
	@Override
	public String toString() {
		return "CategoryJPA [id=" + id + ", name=" + name + ", description="
				+ description + ", superadministrator=" + superadministrator
				+ "]";
	}
}