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
@ManagedBean(name = "categoryAdd")
@SessionScoped
public class AddCategoryMBean implements Serializable{	
	private static final long serialVersionUID = 1L;

	@EJB
	private CategoryFacadeRemote addCategoryRemote;
	//stores CategoryJPA instance
	protected CategoryJPA dataCategory;
	//stores CategoryJPA string id
	protected int idCategory;
	protected String name = "";
	protected String description = "";
	protected List<SuperadministratorJPA> superAdminList = new ArrayList<SuperadministratorJPA>();
	protected SuperadministratorJPA superadministrador;
	protected int idSuperadministrador;
	
	@SuppressWarnings("unchecked")
	public AddCategoryMBean() throws Exception {
		this.name = "";
		this.description = "";
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		addCategoryRemote = (CategoryFacadeRemote) ctx.lookup("java:app/infogroup2eagenda.jar/CategoryFacadeBean!ejb.CategoryFacadeRemote");
		
		this.superAdminList = (List<SuperadministratorJPA>) addCategoryRemote.listAllSuperadministradors();
	}	
	
	/**
	 * Get/set the id number and CategoryJPA instance
	 * @return Category Id
	 */
	//Necesitem passar el ID per a recaregar les dades. Sino no es recarreguen.
	public int getIdCategory(){
		return idCategory;
	}
	
	public void setIdCategory(int idCategory) throws Exception {
		this.idCategory = idCategory;
		reloadCreate();
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name) throws Exception {
		this.name = name;
		//setDataCategory();
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) throws Exception	{
		this.description = description;
	}
	
	public void reloadCreate(){
		this.name = "";
		this.description = "";
		this.superadministrador = null;
		this.idSuperadministrador = -1;
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

	public String createCategory() throws Exception	{	
		try{
			addCategoryRemote.addCategory(name, description, superadministrador);
			return "categoryListView?faces-redirect=true&includeViewParams=true";	
		}catch (Exception e) {
			return "ErrorView?faces-redirect=true&includeViewParams=true";	
		}		
	}
	
	private SuperadministratorJPA obtainSuperadministradorFromId(int id){
		SuperadministratorJPA superadministrador = null;
		superadministrador = addCategoryRemote.showAdministrador(id);
		return superadministrador;
	}
}