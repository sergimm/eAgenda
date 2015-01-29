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

import jpa.CompanyJPA;
import jpa.SuperadministratorJPA;
import ejb.CompanyFacadeRemote;

/**
 * Managed Bean ShowCategoryMBean
 */
@ManagedBean(name = "companyModify")
@SessionScoped
public class ModifyCompanyMBean implements Serializable{	
	private static final long serialVersionUID = 1L;

	@EJB
	private CompanyFacadeRemote updateCompanyRemote;
	protected CompanyJPA dataCompany;
	protected int idCompany = 1;
	protected String name = "";
	protected List<SuperadministratorJPA> superAdminList = new ArrayList<SuperadministratorJPA>();
	protected SuperadministratorJPA superadministrador;
	protected int idSuperadministrador;
	
	public ModifyCompanyMBean() throws Exception 
	{
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		updateCompanyRemote = (CompanyFacadeRemote) ctx.lookup("java:app/infogroup2eagenda.jar/CompanyFacadeBean!ejb.CompanyFacadeRemote");
		
		setDataCompany();
	}
	
	/**
	 * Get/set the id number and CategoryJPA instance
	 * @return Category Id
	 */
	public int getIdCompany() {
		return idCompany;
	}
	
	public void setIdCompany(int idCompany) throws Exception {
		this.idCompany = idCompany;
		setDataCompany();
	}
	
	public String getName()	{
		return name;		
	}
	
	public void setName(String name) throws Exception {
		this.name = name;
	}
	
	
	public CompanyJPA getDataCompany() {
		return dataCompany;
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
	public void setDataCompany() throws Exception {			
		dataCompany = (CompanyJPA) updateCompanyRemote.showCompany(idCompany);
		this.superAdminList = (List<SuperadministratorJPA>) updateCompanyRemote.listAllSuperadministradors();
		if(dataCompany != null){
			name = dataCompany.getName();
			
		}
	}	
	
	public String updateDataCompany() throws Exception	{	
		try {
			updateCompanyRemote.updateCompany(idCompany, name, superadministrador);
			dataCompany = (CompanyJPA) updateCompanyRemote.showCompany(idCompany);
			//return "companyDetailView?faces-redirect=true&includeViewParams=true";
			return "companyDetailView?faces-redirect=true&idCompany="+dataCompany.getId();
		}catch (Exception e) {
			//System.out.println(e.toString());
			return "ErrorView?faces-redirect=true&includeViewParams=true";	
		}
	}
	
	private SuperadministratorJPA obtainSuperadministradorFromId(int id){
		SuperadministratorJPA superadministrador = null;
		superadministrador = updateCompanyRemote.showAdministrador(id);
		return superadministrador;
	}
}