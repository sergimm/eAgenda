/*
 * Copyright UOC.  All rights reserved.
 */
package managedbean;

import java.io.Serializable;
import java.util.*;

import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.naming.Context;
import javax.naming.InitialContext;

import jpa.CategoryJPA;
import ejb.CategoryFacadeRemote;

/**
 * Managed Bean ShowCategoryMBean
 */
@ManagedBean(name = "categoryShow")
@SessionScoped
public class ShowCategoryMBean implements Serializable{	
	private static final long serialVersionUID = 1L;

	@EJB
	private CategoryFacadeRemote showCategoryRemote;
	//stores CategoryJPA instance
	protected CategoryJPA dataCategory;
	//stores CategoryJPA string id
	protected int idCategory = 1;
	//protected String name = "";
	//protected String description = "";
	//protected SuperadministratorJPA superadministrador;

	
	public ShowCategoryMBean() throws Exception {
		setDataCategory();
	}
	
	/**
	 * Get/set the id number and CategoryJPA instance
	 * @return Category Id
	 */
	public int getIdCategory() {
		return idCategory;
	}
	
	public void setIdCategory(int idCategory) throws Exception {
		this.idCategory = idCategory;
		setDataCategory();
	}
	
	
	/*
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

	public SuperadministratorJPA getSuperadministrador() {
		return superadministrador;
	}

	public void setSuperadministrador(SuperadministratorJPA superadministrador) {
		this.superadministrador = superadministrador;
	} */

	public CategoryJPA getDataCategory() {
		return dataCategory;
	}	
	
	public void setDataCategory() throws Exception {	
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		showCategoryRemote = (CategoryFacadeRemote) ctx.lookup("java:app/infogroup2eagenda.jar/CategoryFacadeBean!ejb.CategoryFacadeRemote");
		dataCategory = (CategoryJPA) showCategoryRemote.showCategory(idCategory);
	}
}
