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
import jpa.OrderJPA;
import jpa.UserJPA;
import ejb.EventFacadeRemote;
import ejb.OrderFacadeRemote;

/**
 * Managed Bean ShowCategoryMBean
 */
@ManagedBean(name = "showOrder")
@SessionScoped
public class ShowOrderMBean implements Serializable{	
	private static final long serialVersionUID = 1L;

	@EJB
	private EventFacadeRemote eventRemote;
	private OrderFacadeRemote orderRemote;
	protected int idEvent = 1;
	protected int idOrder = -1;
	protected EventJPA dataEvent;
	protected OrderJPA dataOrder;
	

	private FacesContext context = FacesContext.getCurrentInstance();
	private HttpServletRequest request = (HttpServletRequest) 
	        context.getExternalContext().getRequest();
    private HttpSession misession= (HttpSession) request.getSession(true);
	
	
	public ShowOrderMBean() throws Exception {
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

	
	public int getIdOrder() {
		return idOrder;
	}

	public void setIdOrder(int idOrder) throws Exception {
		this.idOrder = idOrder;
		setDataOrder();
	}

	public OrderJPA getDataOrder() {
		return dataOrder;
	}

	public void setDataOrder(OrderJPA dataOrder) {
		this.dataOrder = dataOrder;
	}

	public void setDataEvent(EventJPA dataEvent) {
		this.dataEvent = dataEvent;
	}

	public void setDataOrder() throws Exception {
		UserJPA user = (UserJPA) misession.getAttribute("user");
		System.out.println("!!!!PRINT!!!! idOrder:" + idOrder);
		if(idOrder != -1){
			this.dataOrder = (OrderJPA) orderRemote.showOrder(idOrder);
			this.dataEvent = (EventJPA) eventRemote.showEvent(this.dataOrder.getEventOrder().getId());
			//Only the user trying to see an order has to be able to see it.
			System.out.println("!!!!PRINT!!!!" + user.getId() + " vs " + this.dataOrder.getUserOrder().getId());
			if(user.getId() != this.dataOrder.getUserOrder().getId()){
				this.dataOrder = null;
				this.dataEvent = null;
			}					
		}
	}
}
