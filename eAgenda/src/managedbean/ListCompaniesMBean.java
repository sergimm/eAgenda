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
import ejb.CompanyFacadeRemote;

/**
 * Managed Bean ListCategoriesMBean
 */
@ManagedBean(name = "companyList")
@SessionScoped
public class ListCompaniesMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private CompanyFacadeRemote companyRemote;		
	protected Collection<CompanyJPA> companyList;
	protected int idCompany = 1;
	
	public ListCompaniesMBean() throws Exception {
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		companyRemote = (CompanyFacadeRemote) ctx.lookup("java:app/infogroup2eagenda.jar/CompanyFacadeBean!ejb.CompanyFacadeRemote");
		
		this.companyList();
	}

	/**
	 * Method get which return Categories Collection
	 * @return Collection
	 */
	public Collection<CompanyJPA> getCompanyList() {
		return companyList;
	}
	
	//Necesitem passar el ID per a recaregar la llista en cas d'update o add. Sino no es refresca.
	public int getIdCompany()	{
		return idCompany;
	}
	
	public void setIdCompany(int idCompany) throws Exception {
		this.idCompany = idCompany;
		companyList();
	}
	
	/**
	 * Method that takes a collection of instances of CategoryJPA calling 
	 * method listAllCategories of the EJB Session
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String companyList() throws Exception {	
		try{
			this.companyList = (Collection<CompanyJPA>) companyRemote.listAllCompanies();
			return "companyListView?faces-redirect=true&includeViewParams=true";
		}catch (Exception e) {
			return "ErrorView?faces-redirect=true&includeViewParams=true";	
		}
	}
}