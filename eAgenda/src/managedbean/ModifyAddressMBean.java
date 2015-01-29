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
@ManagedBean(name = "addressModify")
@SessionScoped
public class ModifyAddressMBean implements Serializable{	
	private static final long serialVersionUID = 1L;

	@EJB
	private AddressFacadeRemote updateAddressRemote;
	protected AddressJPA dataAddress;
	protected int idAddress = 1;
	protected String street ="";
	protected String number="";
	protected String city="";
	protected String state="";
	protected String country="";
	protected String zip="";
	
	
	public ModifyAddressMBean() throws Exception 
	{
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		updateAddressRemote = (AddressFacadeRemote) ctx.lookup("java:app/infogroup2eagenda.jar/AddressFacadeBean!ejb.AddressFacadeRemote");
		
		setDataAddress();
	}
	
	public int getIdAddress() {
		return idAddress;
	}

	public void setIdAddress(int idAddress) throws Exception {
		this.idAddress = idAddress;
		setDataAddress();
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
	
	public AddressJPA getDataAddress() {
		return dataAddress;
	}	


	public void setDataAddress() throws Exception {			
		dataAddress = (AddressJPA) updateAddressRemote.showAddress(idAddress);
		
		//this.eventList = (Set<EventJPA>) updateAddressRemote.listAllEvents();
		if(dataAddress != null){
			street = dataAddress.getStreet();
			number=dataAddress.getNumber();
			city=dataAddress.getCity();
			state=dataAddress.getState();
			country=dataAddress.getCountry();
			zip=dataAddress.getZip();			
		}		
	}	
	
	public String updateDataAddress() throws Exception	{	
		try {
			updateAddressRemote.updateAddress(idAddress,street, number, city, state, country, zip);
			dataAddress= (AddressJPA) updateAddressRemote.showAddress(idAddress);
			//return "companyDetailView?faces-redirect=true&includeViewParams=true";
			return "addressDetailView?faces-redirect=true&idAddress="+dataAddress.getId();
		}catch (Exception e) {
			//System.out.println(e.toString());
			return "ErrorView?faces-redirect=true&includeViewParams=true";	
		}
	}
}