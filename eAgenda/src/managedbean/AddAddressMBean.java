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
@ManagedBean(name = "addressAdd")
@SessionScoped
public class AddAddressMBean implements Serializable{	
	private static final long serialVersionUID = 1L;

	@EJB
	private AddressFacadeRemote addAddressRemote;
	//stores CategoryJPA instance
	protected AddressJPA dataAddress;
	//stores CategoryJPA string id
	protected int idAddress;
	
	private String street = "";
	private String number= "";
	private String city= "";
	private String state= "";
	private String country= "";
	private String zip= "";

	
	
	public AddAddressMBean() throws Exception {
		this.street = "";
		this.number = "";
		this.city = "";
		this.state = "";
		this.country = "";
		this.zip = "";
		
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		addAddressRemote = (AddressFacadeRemote) ctx.lookup("java:app/infogroup2eagenda.jar/AddressFacadeBean!ejb.AddressFacadeRemote");

	}	
	

	public String getStreet() {
		return street;
	}


	public void setStreet(String street) {
		this.street = street;
	}


	public String getNumber() {
		return number;
	}


	public void setNumber(String number) {
		this.number = number;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public String getZip() {
		return zip;
	}


	public void setZip(String zip) {
		this.zip = zip;
	}

	public int getIdAddress() {
		return idAddress;
	}


	public void setIdAddress(int idAddress) {
		this.idAddress = idAddress;
	}


	public void reloadCreate(){
		this.street = "";
		this.number = "";
		this.city = "";
		this.state = "";
		this.country = "";
		this.zip = "";
	}	

	public String createAddress() throws Exception	{	
		try{
			addAddressRemote.addAddress(street, number, city, state, country, zip);
			return "addressListView?faces-redirect=true&includeViewParams=true";	
		}catch (Exception e) {
			return "ErrorView?faces-redirect=true&includeViewParams=true";	
		}		
	}

}