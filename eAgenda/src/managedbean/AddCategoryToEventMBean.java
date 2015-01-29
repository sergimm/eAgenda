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
import jpa.EventJPA;
import ejb.CategoryFacadeRemote;
import ejb.EventFacadeRemote;

/**
 * Managed Bean ListCategoriesMBean
 */
@ManagedBean(name = "eventAddCategory")
@SessionScoped
public class AddCategoryToEventMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private CategoryFacadeRemote categoryRemote;
	@EJB
	private EventFacadeRemote eventRemote;	
	protected Collection<CategoryJPA> categoryList;
	protected Collection<CategoryJPA> categoriesAssignedToEventList;
	protected int idEvent = 1;
	protected int idCategoryToAssign = -1;
	protected int idEventToAssign = -1;
	protected EventJPA event;
	protected String eventName;	
	
	public AddCategoryToEventMBean() throws Exception {
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		categoryRemote = (CategoryFacadeRemote) ctx.lookup("java:app/infogroup2eagenda.jar/CategoryFacadeBean!ejb.CategoryFacadeRemote");
		eventRemote = (EventFacadeRemote) ctx.lookup("java:app/infogroup2eagenda.jar/EventFacadeBean!ejb.EventFacadeRemote");
		
		this.EventCategoryList();
	}

	
	//Necesitem passar el ID per a recaregar la llista en cas d'update o add. Sino no es refresca.
	public int getIdEvent() {
		return idEvent;
	}

	public void setIdEvent(int idEvent) throws Exception {
		this.idEvent = idEvent;
		this.EventCategoryList();
	}
	
	
	public Collection<CategoryJPA> getCategoryList() {
		return categoryList;
	}


	public Collection<CategoryJPA> getCategoriesAssignedToEventList() {
		return categoriesAssignedToEventList;
	}


	public String getEventName() {
		return eventName;
	}

	public int getIdCategoryToAssign() {
		return idCategoryToAssign;
	}

	public void setIdCategoryToAssign(int idCategoryToAssign) {
		this.idCategoryToAssign = idCategoryToAssign;
	}

	public int getIdEventToAssign() {
		return idEventToAssign;
	}

	public void setIdEventToAssign(int idEventToAssign) {
		this.idEventToAssign = idEventToAssign;
	}

	/**
	 * Method that takes a collection of instances of CategoryJPA calling 
	 * method listAllCategories of the EJB Session
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void EventCategoryList() throws Exception {	
		try{
			this.categoryList = (Collection<CategoryJPA>) categoryRemote.listAllCategories();
			
			this.event = (EventJPA) eventRemote.showEvent(this.idEvent);
			
			//System.out.println("idEvent:" + idEvent);
			//System.out.println("Event name:" + this.event.getName());
			
			this.categoriesAssignedToEventList = this.event.getCategoriesEvent();
			
			this.eventName = this.event.getName();
			
			//Eliminem les categories ja assignades de els disponibles
			this.categoryList.removeAll(this.categoriesAssignedToEventList);
		}catch (Exception e) {
			//TODO: Do something on error
		}
	}

	public void addCategoryToEvent() throws Exception
	{	
		if(this.idCategoryToAssign != -1 && this.idEventToAssign != -1){
			eventRemote.addCategoryToEvent(idEventToAssign, idCategoryToAssign);
			this.EventCategoryList();
		}
	}

	public void removeCategoryToEvent() throws Exception
	{	
		if(this.idCategoryToAssign != -1 && this.idEventToAssign != -1){
			eventRemote.removeCategoryToEvent(idEventToAssign, idCategoryToAssign);
			this.EventCategoryList();
		}
	}
}