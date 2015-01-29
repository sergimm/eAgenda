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
import ejb.EventFacadeRemote;

/**
 * Managed Bean ListCategoriesMBean
 */
@ManagedBean(name = "eventFilteredList")
@SessionScoped
public class listFilteredEventsAdminMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private EventFacadeRemote eventRemote;		
	protected Collection<EventJPA> eventList;
	protected int idEvent = 1;
	protected String nameEvent = "";
	protected String nameWord = "";
	protected List<CategoryJPA> categoryList = new ArrayList<CategoryJPA>();	
	protected CategoryJPA category;
	protected int idCategory;
	
	
	public String getNameWord() {
		return nameWord;
	}

	public void setNameWord(String nameWord) {
		this.nameWord = nameWord;
	}

	public List<CategoryJPA> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<CategoryJPA> categoryList) {
		this.categoryList = categoryList;
	}

	public CategoryJPA getCategory() {
		return category;
	}

	public void setCategory(CategoryJPA category) {
		this.category = category;
	}
	
	public int getIdCategory() {
		return idCategory;
	}

	public void setIdCategory(int idCategory) {
		this.idCategory = idCategory;
	}

	public String getNameEvent() {
		return nameEvent;
	}

	public void setNameEvent(String nameEvent) {
		this.nameEvent = nameEvent;
	}

	public listFilteredEventsAdminMBean() throws Exception {
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		eventRemote = (EventFacadeRemote) ctx.lookup("java:app/infogroup2eagenda.jar/EventFacadeBean!ejb.EventFacadeRemote");
		
		this.eventListCategory();
	}

	/**
	 * Method get which return Categories Collection
	 * @return Collection
	 */
	public Collection<EventJPA> getEventList() {
		return eventList;
	}
	
	//Necesitem passar el ID per a recaregar la llista en cas d'update o add. Sino no es refresca.
	public int getIdEvent()	{
		return idEvent;
	}
	
	public void setIdEvent(int idEvent) throws Exception {
		this.idEvent = idEvent;
		eventList();
	}
	
	
	public String eventList() throws Exception {	
		try{
			//Fix: Si no s'afegeix cap text, que mostri tots.
			this.eventList = (Collection<EventJPA>) eventRemote.SearchEventByName((nameEvent.isEmpty())?"%":"%" + nameEvent + "%");
			//System.out.println("this.eventList" + "  " + this.eventList.toString());
			//System.out.println("this.eventLocal" + "  " + this.eventRemote.toString());

			return "eventFilteredListView?faces-redirect=true&includeViewParams=true";
		}catch (Exception e) {
			return "ErrorView?faces-redirect=true&includeViewParams=true";	
		}
	}	
	
	public String eventListCategory() throws Exception {	
		try{
			@SuppressWarnings("unchecked")
			List<CategoryJPA> listAllCategories = (List<CategoryJPA>) eventRemote.listAllCategories();
			this.categoryList = listAllCategories;
			this.eventList = (Collection<EventJPA>) eventRemote.SearchEventByCategory(idCategory);
			//System.out.println("this.eventList" + "  " + this.eventList.toString());
			//System.out.println("this.listAllCategories" + "  " + this.categoryList.toString());

			return "eventFilteredListViewCategory?faces-redirect=true&includeViewParams=true";
		}catch (Exception e) {
			return "ErrorView?faces-redirect=true&includeViewParams=true";	
		}
	}
	public String eventListWord() throws Exception {	
		try{
			//Fix: Si no s'afegeix cap text, que mostri tots.
			this.eventList = (Collection<EventJPA>) eventRemote.SearchEventByWord(nameWord);
			//System.out.println("this.eventList" + "  " + this.eventList.toString());
			//System.out.println("this.eventLocal" + "  " + this.eventRemote.toString());

			return "eventFilteredListViewWords?faces-redirect=true&includeViewParams=true";
		}catch (Exception e) {
			return "ErrorView?faces-redirect=true&includeViewParams=true";	
		}
	}	
}