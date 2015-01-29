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
import jpa.WordsJPA;
import ejb.EventFacadeRemote;

/**
 * Managed Bean ListCategoriesMBean
 */
@ManagedBean(name = "eventAddWord")
@SessionScoped
public class AddWordsToEventMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private EventFacadeRemote eventRemote;	
	protected int idEvent = 1;
	protected int idWordToAssign = -1;
	protected int idEventToAssign = -1;
	protected EventJPA event;
	protected String eventName;	
	protected String newWordName;
	protected List<WordsJPA> wordsAssignedToEventList;
	
	public AddWordsToEventMBean() throws Exception {
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		eventRemote = (EventFacadeRemote) ctx.lookup("java:app/infogroup2eagenda.jar/EventFacadeBean!ejb.EventFacadeRemote");
		
		
		this.EventWordsList();
	}

	
	//Necesitem passar el ID per a recaregar la llista en cas d'update o add. Sino no es refresca.
	public int getIdEvent() {
		return idEvent;
	}

	public void setIdEvent(int idEvent) throws Exception {
		this.idEvent = idEvent;
		this.EventWordsList();
	}
	
	public String getEventName() {
		return eventName;
	}

	public int getIdEventToAssign() {
		return idEventToAssign;
	}

	public int getIdWordToAssign() {
		return idWordToAssign;
	}


	public String getNewWordName() {
		return newWordName;
	}


	public List<WordsJPA> getWordsAssignedToEventList() {
		return wordsAssignedToEventList;
	}


	public void setIdWordToAssign(int idWordToAssign) {
		this.idWordToAssign = idWordToAssign;
	}


	public void setEventName(String eventName) {
		this.eventName = eventName;
	}


	public void setNewWordName(String newWordName) {
		this.newWordName = newWordName;
	}


	public void setWordsAssignedToEventList(List<WordsJPA> wordsAssignedToEventList) {
		this.wordsAssignedToEventList = wordsAssignedToEventList;
	}
	
	public void setIdEventToAssign(int idEventToAssign) {
		this.idEventToAssign = idEventToAssign;
	}


	/**
	 * Method that takes a collection of instances of CategoryJPA calling 
	 * method listAllCategories of the EJB Session
	 * @throws Exception
	 */
	public void EventWordsList() throws Exception {	
		try{
			
			this.newWordName = "";
			if(idEvent != -1){
				//Obtenim l'event al que anem a assignar/dessasignar categories
				this.event = (EventJPA) eventRemote.showEvent(this.idEvent);
				
				System.out.println("idEvent:" + idEvent);
				System.out.println("Event name:" + this.event.getName());
				
				this.wordsAssignedToEventList = (List<WordsJPA>) eventRemote.listAllEventWords(this.event);		
				this.eventName = this.event.getName();
			}
		}catch (Exception e) {
			//TODO: Do something on error
		}
	}

	public void addWordToEvent() throws Exception
	{	
		if(this.idEvent != -1 && !this.newWordName.isEmpty()){
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);

			eventRemote = (EventFacadeRemote) ctx.lookup("java:app/infogroup2eagenda.jar/EventFacadeBean!ejb.EventFacadeRemote");
			eventRemote.addWordToEvent(this.idEvent, this.newWordName);
			this.EventWordsList();
		}
	}

	public void removeWordToEvent() throws Exception
	{	
		if(this.idWordToAssign != -1 && this.idEvent != -1){
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);

			eventRemote = (EventFacadeRemote) ctx.lookup("java:app/infogroup2eagenda.jar/EventFacadeBean!ejb.EventFacadeRemote");
			eventRemote.removeWordToEvent(this.idWordToAssign);
			this.EventWordsList();
		}
	}
}