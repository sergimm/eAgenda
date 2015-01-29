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
import jpa.SuperadministratorJPA;
import ejb.CategoryFacadeRemote;

/**
 * Managed Bean ShowCategoryMBean
 */
@ManagedBean(name = "categoryModify")
@SessionScoped
public class ModifyCategoryMBean implements Serializable{	
	private static final long serialVersionUID = 1L;

	@EJB
	private CategoryFacadeRemote updateCategoryRemote;
	//stores CategoryJPA instance
	protected CategoryJPA dataCategory;
	//stores CategoryJPA string id
	protected int idCategory = 1;
	protected String name = "";
	protected String description = "";
	protected List<SuperadministratorJPA> superAdminList = new ArrayList<SuperadministratorJPA>();
	protected SuperadministratorJPA superadministrador;
	protected int idSuperadministrador;
	
	
	
	public ModifyCategoryMBean() throws Exception 
	{
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		updateCategoryRemote = (CategoryFacadeRemote) ctx.lookup("java:app/infogroup2eagenda.jar/CategoryFacadeBean!ejb.CategoryFacadeRemote");
		
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
	
	public String getName()	{
		return name;		
	}
	
	public void setName(String name) throws Exception {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) throws Exception	{
		this.description = description;
	}
	
	public CategoryJPA getDataCategory() {
		return dataCategory;
	}
	
	public int getIdSuperadministrador() {
		return idSuperadministrador;
	}

	public void setIdSuperadministrador(int idSuperadministrador) {
		this.idSuperadministrador = idSuperadministrador;
		this.superadministrador = obtainSuperadministradorFromId(idSuperadministrador);		
	}

	public List<SuperadministratorJPA> getSuperAdminList() {
		return superAdminList;
	}

	public void setSuperAdminList(List<SuperadministratorJPA> superAdminList) {
		this.superAdminList = superAdminList;
	}

	public SuperadministratorJPA getSuperadministrador() {
		return superadministrador;
	}

	public void setSuperadministrador(SuperadministratorJPA superadministrador) {
		this.superadministrador = superadministrador;
	}
	
	@SuppressWarnings("unchecked")
	public void setDataCategory() throws Exception {			
		dataCategory = (CategoryJPA) updateCategoryRemote.showCategory(idCategory);
		this.superAdminList = (List<SuperadministratorJPA>) updateCategoryRemote.listAllSuperadministradors();
		if(dataCategory != null){
			name = dataCategory.getName();
			description = dataCategory.getDescription();
		}
	}	
	
	public String updateDataCategory() throws Exception	{	
		try {
			updateCategoryRemote.updateCategory(idCategory, name, description, superadministrador);
			dataCategory = (CategoryJPA) updateCategoryRemote.showCategory(idCategory);
			return "categoryDetailView?faces-redirect=true&idCategory="+dataCategory.getId();
		}catch (Exception e) {
			//System.out.println(e.toString());
			return "ErrorView?faces-redirect=true&includeViewParams=true";	
		}
	}
	
	private SuperadministratorJPA obtainSuperadministradorFromId(int id){
		SuperadministratorJPA superadministrador = null;
		superadministrador = updateCategoryRemote.showAdministrador(id);
		return superadministrador;
	}
}