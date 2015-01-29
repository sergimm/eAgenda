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

import jpa.OrderJPA;
import jpa.UserJPA;
import ejb.OrderFacadeRemote;

/**
 * Managed Bean ListCategoriesMBean
 */
@ManagedBean(name = "orderList")
@SessionScoped
public class ListAllOrdersMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private OrderFacadeRemote orderRemote;		
	protected Collection<OrderJPA> orderList;
	protected int idOrders = -1;
	private UserJPA user;
	
	private FacesContext context = FacesContext.getCurrentInstance();
	private HttpServletRequest request = (HttpServletRequest) 
	        context.getExternalContext().getRequest();
    private HttpSession misession= (HttpSession) request.getSession(true);
	
	public ListAllOrdersMBean() throws Exception {
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		this.orderRemote = (OrderFacadeRemote) ctx.lookup("java:app/infogroup2eagenda.jar/OrderFacadeBean!ejb.OrderFacadeRemote");
		
		orderList();
	}	
	
	public Collection<OrderJPA> getOrderList() {
		return orderList;
	}

	public void setOrderList(Collection<OrderJPA> orderList) {
		this.orderList = orderList;		
	}

	public int getIdOrders() {
		return idOrders;
	}

	public void setIdOrders(int idOrders) throws Exception {
		this.idOrders = idOrders;
		orderList();
	}


	public String orderList() throws Exception {
		this.user = (UserJPA) misession.getAttribute("user");
		if(user != null){
			try{
				this.orderList = this.orderRemote.findOrdersByUser(user);
				return "listOrdersView?faces-redirect=true&includeViewParams=true";
			}catch (Exception e) {
				return "ErrorView?faces-redirect=true&includeViewParams=true";	
			}
		}else{
			return "ErrorView?faces-redirect=true&includeViewParams=true";
		}
	}
}