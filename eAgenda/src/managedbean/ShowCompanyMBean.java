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
 * Managed Bean ShowCompanyMBean
 */
@ManagedBean(name = "companyShow")
@SessionScoped
public class ShowCompanyMBean implements Serializable{	
	private static final long serialVersionUID = 1L;

	@EJB
	private CompanyFacadeRemote showCompanyRemote;
	//stores CompanyJPA instance
	protected CompanyJPA dataCompany;
	//stores CategoryJPA string id
	protected int idCompany = 1;
	//protected String name = "";
	//protected String description = "";
	//protected SuperadministratorJPA superadministrador;

	
	public ShowCompanyMBean() throws Exception {
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
	

	public CompanyJPA getDataCompany() {
		return dataCompany;
	}	
	
	public void setDataCompany() throws Exception {	
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		showCompanyRemote = (CompanyFacadeRemote) ctx.lookup("java:app/infogroup2eagenda.jar/CompanyFacadeBean!ejb.CompanyFacadeRemote");
		dataCompany = (CompanyJPA) showCompanyRemote.showCompany(idCompany);
	}
}
