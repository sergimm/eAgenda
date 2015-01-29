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
 * Managed Bean ListCategoriesMBean
 */
@ManagedBean(name = "categoryList")
@SessionScoped
public class ListCategoriesMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private CategoryFacadeRemote categoryRemote;		
	protected Collection<CategoryJPA> categoryList;
	protected int idCategory = 1;
	
	public ListCategoriesMBean() throws Exception {
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		categoryRemote = (CategoryFacadeRemote) ctx.lookup("java:app/infogroup2eagenda.jar/CategoryFacadeBean!ejb.CategoryFacadeRemote");
		
		
		this.categoryList();
	}

	/**
	 * Method get which return Categories Collection
	 * @return Collection
	 */
	public Collection<CategoryJPA> getCategoryList() {
		return categoryList;
	}
	
	//Necesitem passar el ID per a recaregar la llista en cas d'update o add. Sino no es refresca.
	public int getIdCategory()	{
		return idCategory;
	}
	
	public void setIdCategory(int idCategory) throws Exception {
		this.idCategory = idCategory;
		categoryList();
	}
	
	/**
	 * Method that takes a collection of instances of CategoryJPA calling 
	 * method listAllCategories of the EJB Session
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String categoryList() throws Exception {	
		try{
			this.categoryList = (Collection<CategoryJPA>) categoryRemote.listAllCategories();
			return "categoryListView?faces-redirect=true&includeViewParams=true";
		}catch (Exception e) {
			return "ErrorView?faces-redirect=true&includeViewParams=true";	
		}
	}
}