/*
 * Copyright UOC.  All rights reserved.
 */
package jpa;

import java.io.Serializable;

import javax.persistence.*;


/**
 * JPA Class SuperadministratorJPA
 */
@Entity
@Table(name="infogroup2eagenda.superadministrator")
public class SuperadministratorJPA implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @SequenceGenerator(name="infogroup2eagenda.superadministrator_id_seq",
                       sequenceName="infogroup2eagenda.superadministrator_id_seq",
                       allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
    				generator="infogroup2eagenda.superadministrator_id_seq")
    @Column(name = "id", updatable=false)
	private int id;

	private String email;
	private String name;
	private String nif;
	private String password;

	public SuperadministratorJPA() {
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

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNif() {
		return this.nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/*public Set<CategoryJPA> getCategories() {
		return this.categories;
	}

	public void setCategories(Set<CategoryJPA> categories) {
		this.categories = categories;
	}

	public CategoryJPA addCategory(CategoryJPA category) {
		getCategories().add(category);
		category.setSuperadministrator(this);

		return category;
	}

	public CategoryJPA removeCategory(CategoryJPA category) {
		getCategories().remove(category);
		category.setSuperadministrator(null);

		return category;
	}

	public Set<CompanyJPA> getCompanies() {
		return this.companies;
	}

	public void setCompanies(Set<CompanyJPA> companies) {
		this.companies = companies;
	}

	public CompanyJPA addCompany(CompanyJPA company) {
		getCompanies().add(company);
		company.setSuperadministrator(this);

		return company;
	}

	public CompanyJPA removeCompany(CompanyJPA company) {
		getCompanies().remove(company);
		company.setSuperadministrator(null);

		return company;
	}*/

	
	
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
		SuperadministratorJPA other = (SuperadministratorJPA) obj;
		if (id != other.id)
			return false;
		return true;
	}

	/**
	 *  toString() nomes per a debuggar
	 */
	@Override
	public String toString() {
		return "SuperadministratorJPA [id=" + id + ", email=" + email
				+ ", name=" + name + ", nif=" + nif + ", password=" + password
				//+ ", categories=" + categories + ", companies=" + companies
				+ "]";
	}
	
	
}