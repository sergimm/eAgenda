/*
 * Copyright UOC.  All rights reserved.
 */
package managedbean;

import java.io.Serializable;
import java.util.*;

import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import jpa.EventJPA;
import jpa.UserJPA;
import ejb.AdministrationFacadeRemote;
import ejb.EventFacadeRemote;

/**
 * Managed Bean ShowCategoryMBean
 */
@ManagedBean(name = "suggest")
@SessionScoped
public class SuggestMBean implements Serializable{	
	private static final long serialVersionUID = 1L;

	@EJB
	private EventFacadeRemote eventRemote;
	private AdministrationFacadeRemote administrationRemote;	
	protected int idEvent = 1;
	protected EventJPA dataEvent;
	private UserJPA user;

	protected String email;
	protected String comentary;
	
	/*
	protected UserJPA userAuthenticated;
	*/	
	private FacesContext context = FacesContext.getCurrentInstance();
	private HttpServletRequest request = (HttpServletRequest) 
	        context.getExternalContext().getRequest();
    private HttpSession misession= (HttpSession) request.getSession(true);
	
	
	public SuggestMBean() throws Exception {
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		this.eventRemote = (EventFacadeRemote) ctx.lookup("java:app/infogroup2eagenda.jar/EventFacadeBean!ejb.EventFacadeRemote");
		this.administrationRemote = (AdministrationFacadeRemote) ctx.lookup("java:app/infogroup2eagenda.jar/AdministrationFacadeBean!ejb.AdministrationFacadeRemote");
		setDataSuggestion();
	}

	public int getIdEvent() {
		return idEvent;
	}

	public void setIdEvent(int idEvent) throws Exception {
		this.idEvent = idEvent;
		setDataSuggestion();
	}
	
	public EventJPA getDataEvent() {
		return dataEvent;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getComentary() {
		return comentary;
	}

	public void setComentary(String comentary) {
		this.comentary = comentary;
	}

	public void setDataEvent(EventJPA dataEvent) {
		this.dataEvent = dataEvent;
	}
	
	public void setDataSuggestion() throws Exception {
		this.email = "";
		this.comentary = "";
		this.user = (UserJPA) misession.getAttribute("user");
		if(idEvent != -1 && this.user != null){
			this.dataEvent = (EventJPA) eventRemote.showEvent(idEvent);
		}
	}
	
	public String addNewSuggestion (){
		UserJPA userToSend = null;
		try{
			userToSend = this.administrationRemote.findUserByEmail(this.email);
			//If we don't find the email, we do nothing, so users don't know if there is an user with this email registered or not.
			if(userToSend != null){
				this.eventRemote.suggest(this.idEvent, this.email, userToSend, this.comentary);
			}
			return "showEventView?faces-redirect=true&includeViewParams=true";	
		}catch (Exception e) {
			return "ErrorView?faces-redirect=true&includeViewParams=true";	
		}
	}
}
