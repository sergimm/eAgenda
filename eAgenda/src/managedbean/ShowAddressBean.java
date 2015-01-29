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

import jpa.AddressJPA;
import ejb.AddressFacadeRemote;


/**
 * Managed Bean ShowCategoryMBean
 */
@ManagedBean(name = "addressShow")
@SessionScoped
public class ShowAddressBean implements Serializable{	
	private static final long serialVersionUID = 1L;

	@EJB
	private AddressFacadeRemote showAddressRemote;
	protected AddressJPA dataAddress;
	protected int idAddress = 1;
	
	public ShowAddressBean() throws Exception {
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		showAddressRemote = (AddressFacadeRemote) ctx.lookup("java:app/infogroup2eagenda.jar/AddressFacadeBean!ejb.AddressFacadeRemote");
		
		setDataAddress();
	}
	
	/**
	 * Get/set the id number and CategoryJPA instance
	 * @return Category Id
	 */
	public int getIdAddress() {
		return idAddress;
	}
	
	public void setIdAddress(int idAddress) throws Exception {
		this.idAddress = idAddress;
		setDataAddress();
	}

	public AddressJPA getDataAddress() {
		return dataAddress;
	}	
	
	public void setDataAddress() throws Exception {	
		dataAddress = (AddressJPA) showAddressRemote.showAddress(idAddress);		
	}
}
