/*
 * Copyright UOC.  All rights reserved.
 */
package jpa;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

/**
 * JPA Class CompanyJPA
 */
@Entity
@Table(name="infogroup2eagenda.company")
public class CompanyJPA implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @SequenceGenerator(name="infogroup2eagenda.company_id_seq",  sequenceName="infogroup2eagenda.company_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="infogroup2eagenda.company_id_seq")
    @Column(name = "id", updatable=false)
	private int id;

	private String name;

	//Associacio bidireccional Na1 amb Superadministrator
	@ManyToOne
	private SuperadministratorJPA superadministrator;
	
	@OneToMany(mappedBy="company")
	private List<EventJPA> eventCompany;

	public CompanyJPA() {
	}

	/**
	 *  Getters/Setters
	 */
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SuperadministratorJPA getSuperadministrator() {
		return this.superadministrator;
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
		CompanyJPA other = (CompanyJPA) obj;
		if (id != other.id)
			return false;
		return true;
	}

	/**
	 *  toString() nomes per a debuggar
	 */	
	@Override
	public String toString() {
		return "CompanyJPA [id=" + id + ", name=" + name
				+ ", superadministrator=" + superadministrator + "]";
	}
}