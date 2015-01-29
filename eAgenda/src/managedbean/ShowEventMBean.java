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

import jpa.CommentJPA;
import jpa.EventJPA;
import jpa.UserJPA;
import ejb.EventFacadeRemote;

/**
 * Managed Bean ShowCategoryMBean
 */
@ManagedBean(name = "eventShow")
@SessionScoped
public class ShowEventMBean implements Serializable{	
	private static final long serialVersionUID = 1L;

	@EJB
	private EventFacadeRemote eventRemote;		
	protected int idEvent = 1;
	protected EventJPA dataEvent;
	protected List<CommentJPA> comments;
	
	/*
	protected UserJPA userAuthenticated;
	*/	
	private FacesContext context = FacesContext.getCurrentInstance();
	private HttpServletRequest request = (HttpServletRequest) 
	        context.getExternalContext().getRequest();
    private HttpSession misession= (HttpSession) request.getSession(true);
	
	
	public ShowEventMBean() throws Exception {
		setDataEvent();
	}

	public int getIdEvent() {
		return idEvent;
	}
	
	public void setIdEvent(int idEvent) throws Exception {
		this.idEvent = idEvent;
		setDataEvent();
	}

	public EventJPA getDataEvent() {
		return dataEvent;
	}
	
	public List<CommentJPA> getComments() {
		return comments;
	}
	
	//This method is used on the view to make visible or not the operations on the event details.
	public Boolean isUserAuthenticated() {
		return ((UserJPA) misession.getAttribute("user") == null)?false:true;
	}

	public void setDataEvent() throws Exception {
		if(idEvent != -1){
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			//misession= (HttpSession) request.getSession(true);
			eventRemote = (EventFacadeRemote) ctx.lookup("java:app/infogroup2eagenda.jar/EventFacadeBean!ejb.EventFacadeRemote");
			dataEvent = (EventJPA) eventRemote.showEvent(idEvent);						
			this.comments = (List<CommentJPA>) eventRemote.listAllEventComments(this.dataEvent);
		}
	}
}
