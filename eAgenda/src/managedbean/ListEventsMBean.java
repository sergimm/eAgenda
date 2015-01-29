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
import jpa.EventJPA;
import ejb.EventFacadeRemote;

/**
 * Managed Bean ListCategoriesMBean
 */
@ManagedBean(name = "eventList")
@SessionScoped
public class ListEventsMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private EventFacadeRemote eventRemote;		
	protected Collection<EventJPA> eventList;
	protected int idEvent = 1;
	
	public ListEventsMBean() throws Exception {
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		eventRemote = (EventFacadeRemote) ctx.lookup("java:app/infogroup2eagenda.jar/EventFacadeBean!ejb.EventFacadeRemote");
		
		this.eventList();
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
	
	/**
	 * Method that takes a collection of instances of CategoryJPA calling 
	 * method listAllCategories of the EJB Session
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String eventList() throws Exception {
		this.eventList = null;
		try{
			this.eventList = (Collection<EventJPA>) eventRemote.listAllEvents();			
		}catch (Exception e) {
			return "ErrorView?faces-redirect=true&includeViewParams=true";	
		}
		return null;
	}
}