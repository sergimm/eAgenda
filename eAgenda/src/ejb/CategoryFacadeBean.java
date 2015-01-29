/*
 * Copyright UOC.  All rights reserved.
 */
package ejb;

import java.util.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import jpa.CategoryJPA;
import jpa.SuperadministratorJPA;

/**
 * EJB Session Bean Class of example "Practical Case Study JEE"
 */
@Stateless
public class CategoryFacadeBean implements CategoryFacadeRemote, CategoryFacade {	
	//Persistence Unit Context
	@PersistenceContext(unitName="infogroup2eagenda") 
	private EntityManager entman;
   
	/**
	 * Method that returns Collection of all categories
	 */
	@SuppressWarnings("unchecked")
	@Override
	public java.util.Collection<CategoryJPA> listAllCategories() {		
		try{
			Collection<CategoryJPA> allCategories = entman.createQuery("from CategoryJPA").getResultList();
			return allCategories;	
		}catch (PersistenceException e) {
			throw e;
		}
	}	
	
	@Override
	public void addCategory(String name, String description, SuperadministratorJPA superadministrator) {
		try	{
			CategoryJPA category = new CategoryJPA();			
			category.setName(name);
			category.setDescription(description);
			category.setSuperadministrator(superadministrator);	
			entman.joinTransaction();
			entman.persist(category);
			entman.flush();
		}catch (PersistenceException e) {
			throw e;
		}
	}

	@Override
	public void updateCategory(int id, String name, String description, SuperadministratorJPA superadministrator) {
		try	{
			CategoryJPA category = new CategoryJPA();			
			category.setId(id);
			category.setName(name);
			category.setDescription(description);
			category.setSuperadministrator(superadministrator);
			entman.merge(category);
			entman.flush();
		}catch (PersistenceException e) {
			throw e;
		}
	}

	@Override
	public CategoryJPA showCategory(int id) {
		CategoryJPA category = null;
		try	{
			//Obtenim la categoria amb id que busquem
			category = entman.find(CategoryJPA.class, id);
		}catch (PersistenceException e) {
			throw e;
		}
	    return category;
	}

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
			//Obtenim la categoria amb id que busquem
			superadministrador = entman.find(SuperadministratorJPA.class, id);
		}catch (PersistenceException e) {
			throw e;
		}
	    return superadministrador;
	}
}