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
import javax.naming.NamingException;

import jpa.CompanyJPA;
import jpa.SuperadministratorJPA;
import ejb.CompanyFacadeRemote;

/**
 * Managed Bean ShowCategoryMBean
 */
@ManagedBean(name = "companyAdd")
@SessionScoped
public class AddCompanyMBean implements Serializable{	
	private static final long serialVersionUID = 1L;

	@EJB
	private CompanyFacadeRemote addCompanyRemote;
	//stores CategoryJPA instance
	protected CompanyJPA dataCompany;
	//stores CategoryJPA string id
	protected int idCompany;
	protected String name = "";
	protected List<SuperadministratorJPA> superAdminList = new ArrayList<SuperadministratorJPA>();
	protected SuperadministratorJPA superadministrador;
	protected int idSuperadministrador;
	
	@SuppressWarnings("unchecked")
	public AddCompanyMBean() throws Exception {
		this.name = "";
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		addCompanyRemote = (CompanyFacadeRemote) ctx.lookup("java:app/infogroup2eagenda.jar/CompanyFacadeBean!ejb.CompanyFacadeRemote");
		this.superAdminList = (List<SuperadministratorJPA>) addCompanyRemote.listAllSuperadministradors();
	}	
	
	/**
	 * Get/set the id number and CategoryJPA instance
	 * @return Category Id
	 */
	//Necesitem passar el ID per a recaregar les dades. Sino no es recarreguen.
	public int getIdCompany(){
		return idCompany;
	}
	
	public void setIdCompany(int idCategory) throws Exception {
		this.idCompany = idCategory;
		reloadCreate();
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name) throws Exception {
		this.name = name;
		//setDataCategory();
	}
	
	public void reloadCreate(){
		this.name = "";
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

	public String createCompany() throws Exception	{	
		try{
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			addCompanyRemote = (CompanyFacadeRemote) ctx.lookup("java:app/infogroup2eagenda.jar/CompanyFacadeBean!ejb.CompanyFacadeRemote");
			addCompanyRemote.addCompany(name,  superadministrador);
			return "companyListView?faces-redirect=true&includeViewParams=true";	
		}catch (Exception e) {
			return "ErrorView?faces-redirect=true&includeViewParams=true";	
		}		
	}
	
	private SuperadministratorJPA obtainSuperadministradorFromId(int id){
		SuperadministratorJPA superadministrador = null;
		try {
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			addCompanyRemote = (CompanyFacadeRemote) ctx.lookup("java:app/infogroup2eagenda.jar/CompanyFacadeBean!ejb.CompanyFacadeRemote");
			superadministrador = addCompanyRemote.showAdministrador(id);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return superadministrador;
	}
}