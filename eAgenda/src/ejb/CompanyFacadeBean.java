/*
 * Copyright UOC.  All rights reserved.
 */
package ejb;

import java.util.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;



import jpa.CompanyJPA;
import jpa.SuperadministratorJPA;

/**
 * EJB Session Bean Class of example "Practical Case Study JEE"
 */
@Stateless
public class CompanyFacadeBean implements CompanyFacadeRemote, CompanyFacade {	
	//Persistence Unit Context
	@PersistenceContext(unitName="infogroup2eagenda") 
	private EntityManager entman;
   
	@SuppressWarnings("unchecked")
	@Override
	public Collection<?> listAllSuperadministradors() {
		try{
			Collection<SuperadministratorJPA> allSuperAdmin = entman.createQuery("from SuperadministratorJPA").getResultList();
			return allSuperAdmin;	
		}catch (PersistenceException e) {
			throw e;
		}
	}

	@Override
	public SuperadministratorJPA showAdministrador(int id) {
		SuperadministratorJPA superadministrador = null;
		try	{
			superadministrador = entman.find(SuperadministratorJPA.class, id);
		}catch (PersistenceException e) {
			throw e;
		}
	    return superadministrador;
	}

	/**
	 * Method that returns Collection of all categories
	 */
	@SuppressWarnings("unchecked")
	@Override
	public java.util.Collection<CompanyJPA> listAllCompanies() {		
		try{
			Collection<CompanyJPA> allCompanies = entman.createQuery("from CompanyJPA").getResultList();
			return allCompanies;	
		}catch (PersistenceException e) {
			throw e;
		}
	}
	
	@Override
	public CompanyJPA showCompany(int id) {
		CompanyJPA company = null;
		try	{
			company = entman.find(CompanyJPA.class, id);
		}catch (PersistenceException e) {
			throw e;
		}
	    return company;
	}

	@Override
	public void addCompany(String name, SuperadministratorJPA superadministrator) {
		try	{
			CompanyJPA company = new CompanyJPA();			
			company.setName(name);
			company.setSuperadministrator(superadministrator);	
			entman.joinTransaction();
			entman.persist(company);
			entman.flush();
		}catch (PersistenceException e) {
			throw e;
		}
	}

	@Override
	public void updateCompany(int id, String name, SuperadministratorJPA superadministrator) {
		try	{
			CompanyJPA company = new CompanyJPA();			
			company.setId(id);
			company.setName(name);
			company.setSuperadministrator(superadministrator);
			entman.merge(company);
			entman.flush();
		}catch (PersistenceException e) {
			throw e;
		}
	}
}