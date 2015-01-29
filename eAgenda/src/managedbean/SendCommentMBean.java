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
@ManagedBean(name = "sendComment")
@SessionScoped
public class SendCommentMBean implements Serializable{	
	private static final long serialVersionUID = 1L;

	@EJB
	private EventFacadeRemote eventRemote;		
	protected int idEvent = 1;
	protected EventJPA dataEvent;
	protected CommentJPA comment;	
	private UserJPA user;
	//Used to be able to modify comments
	protected String newComment;
	
	/*
	protected UserJPA userAuthenticated;
	*/	
	private FacesContext context = FacesContext.getCurrentInstance();
	private HttpServletRequest request = (HttpServletRequest) 
	        context.getExternalContext().getRequest();
    private HttpSession misession= (HttpSession) request.getSession(true);
	
	
	public SendCommentMBean() throws Exception {
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		this.eventRemote = (EventFacadeRemote) ctx.lookup("java:app/infogroup2eagenda.jar/EventFacadeBean!ejb.EventFacadeRemote");
		
		setDataComment();
	}

	public int getIdEvent() {
		return idEvent;
	}

	public void setIdEvent(int idEvent) throws Exception {
		this.idEvent = idEvent;
		setDataComment();
	}
	
	public EventJPA getDataEvent() {
		return dataEvent;
	}
	
	public void setDataEvent(EventJPA dataEvent) {
		this.dataEvent = dataEvent;
	}
	
	public CommentJPA getComment() {
		return comment;
	}
	
	public void setComment(CommentJPA comment) {
		this.comment = comment;
	}
	
	public String getNewComment() {
		return newComment;
	}

	public void setNewComment(String newComment) {
		this.newComment = newComment;
	}

	public void setDataComment() throws Exception {
		this.newComment = "";		
		this.user = (UserJPA) misession.getAttribute("user");
		if(idEvent != -1 && this.user != null){
			this.dataEvent = (EventJPA) eventRemote.showEvent(idEvent);
			this.comment = (CommentJPA) eventRemote.findEventCommentByUser(dataEvent, user);
			if(this.comment != null){
				this.newComment = this.comment.getText();
			}
		}
	}
	
	public String addNewComment (){
		try{
			//Update
			if(this.comment != null){
				this.eventRemote.updateCommentToEvent(this.comment.getId(), this.newComment);	
			}else{//Create
				this.eventRemote.addCommentToEvent(this.idEvent, this.user.getId(), this.newComment);
			}
			return "showEventView?faces-redirect=true&includeViewParams=true";	
		}catch (Exception e) {
			return "ErrorView?faces-redirect=true&includeViewParams=true";	
		}
	}
}

