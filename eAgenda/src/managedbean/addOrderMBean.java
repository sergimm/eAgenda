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
import ejb.OrderFacadeRemote;

/**
 * Managed Bean ShowCategoryMBean
 */
@ManagedBean(name = "addOrder")
@SessionScoped
public class addOrderMBean implements Serializable{	
	private static final long serialVersionUID = 1L;

	@EJB
	private EventFacadeRemote eventRemote;
	private OrderFacadeRemote orderRemote;
	protected int idEvent = 1;
	protected EventJPA dataEvent;
	protected RattingJPA ratting;	
	private UserJPA user;
	//Used to be able to modify comments
	protected int newOrder = 1;
	protected int[] arrayTickets = {1,2,3,4,5};
	
		
	private FacesContext context = FacesContext.getCurrentInstance();
	private HttpServletRequest request = (HttpServletRequest) 
	        context.getExternalContext().getRequest();
    private HttpSession misession= (HttpSession) request.getSession(true);
	
	
	public addOrderMBean() throws Exception {
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		this.eventRemote = (EventFacadeRemote) ctx.lookup("java:app/infogroup2eagenda.jar/EventFacadeBean!ejb.EventFacadeRemote");
		this.orderRemote = (OrderFacadeRemote) ctx.lookup("java:app/infogroup2eagenda.jar/OrderFacadeBean!ejb.OrderFacadeRemote");
		
		setDataOrder();
	}

	public int getIdEvent() {
		return idEvent;
	}

	public void setIdEvent(int idEvent) throws Exception {
		this.idEvent = idEvent;
		setDataOrder();
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

	
	public int getNewOrder() {
		return newOrder;
	}

	public void setNewOrder(int newOrder) {
		this.newOrder = newOrder;
	}

	public int[] getArrayTickets() {
		return arrayTickets;
	}

	public void setArrayTickets(int[] arrayTickets) {
		this.arrayTickets = arrayTickets;
	}

	public void setDataOrder() throws Exception {
		this.newOrder= 1;		
		this.user = (UserJPA) misession.getAttribute("user");
		if(idEvent != -1 && this.user != null){
			this.dataEvent = (EventJPA) eventRemote.showEvent(idEvent);
		}
	}
	
	public String addNewOrder(){
		try{
			this.orderRemote.addOrder(this.idEvent, this.user.getId(), this.newOrder);
			return "listAllOrdersView?faces-redirect=true&includeViewParams=true";	
		}catch (Exception e) {
			return "ErrorView?faces-redirect=true&includeViewParams=true";	
		}
	}
}
