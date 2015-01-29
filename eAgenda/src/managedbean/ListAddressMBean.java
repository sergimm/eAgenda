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
 * Managed Bean ListCategoriesMBean
 */
@ManagedBean(name = "addressList")
@SessionScoped
public class ListAddressMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private AddressFacadeRemote addressRemote;		
	protected Collection<AddressJPA> addressList;
	protected int idAddress = 1;
	
	public ListAddressMBean() throws Exception {
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		addressRemote = (AddressFacadeRemote) ctx.lookup("java:app/infogroup2eagenda.jar/AddressFacadeBean!ejb.AddressFacadeRemote");
		
		
		this.addressList();
	}

	/**
	 * Method get which return Categories Collection
	 * @return Collection
	 */
	public Collection<AddressJPA> getAddressList() {
		return addressList;
	}
	
	//Necesitem passar el ID per a recaregar la llista en cas d'update o add. Sino no es refresca.
	public int getIdAddress()	{
		return idAddress;
	}
	
	public void setIdAddress(int idAddress) throws Exception {
		this.idAddress = idAddress;
		addressList();
	}
	
	/**
	 * Method that takes a collection of instances of CategoryJPA calling 
	 * method listAllCategories of the EJB Session
	 * @throws Exception
	 */
	public String addressList() throws Exception {	
		try{
			this.addressList = (Collection<AddressJPA>) addressRemote.listAllAddress();
			return "addressListView?faces-redirect=true&includeViewParams=true";
		}catch (Exception e) {
			return "ErrorView?faces-redirect=true&includeViewParams=true";	
		}
	}
}