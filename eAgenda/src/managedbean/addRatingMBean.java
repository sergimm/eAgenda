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
import jpa.RattingJPA;
import jpa.UserJPA;
import ejb.EventFacadeRemote;

/**
 * Managed Bean ShowCategoryMBean
 */
@ManagedBean(name = "sendRatting")
@SessionScoped
public class addRatingMBean implements Serializable{	
	private static final long serialVersionUID = 1L;

	@EJB
	private EventFacadeRemote eventRemote;		
	protected int idEvent = 1;
	protected EventJPA dataEvent;
	protected RattingJPA ratting;	
	private UserJPA user;
	//Used to be able to modify comments
	protected int newRatting = 5;
	protected int[] arrayRatting = {1,2,3,4,5,6,7,8,9,10};
	
	/*
	protected UserJPA userAuthenticated;
	*/	
	private FacesContext context = FacesContext.getCurrentInstance();
	private HttpServletRequest request = (HttpServletRequest) 
	        context.getExternalContext().getRequest();
    private HttpSession misession= (HttpSession) request.getSession(true);
	
	
	public addRatingMBean() throws Exception {
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		this.eventRemote = (EventFacadeRemote) ctx.lookup("java:app/infogroup2eagenda.jar/EventFacadeBean!ejb.EventFacadeRemote");
		
		setDataRatting();
	}

	public int getIdEvent() {
		return idEvent;
	}

	public void setIdEvent(int idEvent) throws Exception {
		this.idEvent = idEvent;
		setDataRatting();
	}	
	
	
	public EventJPA getDataEvent() {
		return dataEvent;
	}

	public void setDataEvent(EventJPA dataEvent) {
		this.dataEvent = dataEvent;
	}

	public RattingJPA getRatting() {
		return ratting;
	}

	public void setRatting(RattingJPA ratting) {
		this.ratting = ratting;
	}

	public int getNewRatting() {
		return newRatting;
	}

	public void setNewRatting(int newRatting) {
		this.newRatting = newRatting;
	}

	public int[] getArrayRatting() {
		return arrayRatting;
	}

	public void setArrayRatting(int[] arrayRatting) {
		this.arrayRatting = arrayRatting;
	}

	public void setDataRatting() throws Exception {
		this.newRatting = 5;		
		this.user = (UserJPA) misession.getAttribute("user");
		if(idEvent != -1 && this.user != null){
			this.dataEvent = (EventJPA) eventRemote.showEvent(idEvent);
			this.ratting = (RattingJPA) eventRemote.findEventRattingByUser(dataEvent, user);
			if(this.ratting != null){
				this.newRatting = this.ratting.getRatting();
			}
		}
	}
	
	public String addNewRatting(){
		try{
			//Update
			if(this.ratting != null){
				this.eventRemote.updateRattingToEvent(this.ratting.getId(), this.newRatting);	
			}else{//Create
				this.eventRemote.addRattingToEvent(this.idEvent, this.user.getId(), this.newRatting);
			}
			return "showEventView?faces-redirect=true&includeViewParams=true";	
		}catch (Exception e) {
			return "ErrorView?faces-redirect=true&includeViewParams=true";	
		}
	}
}
