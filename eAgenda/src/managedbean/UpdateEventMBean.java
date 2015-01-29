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
import jpa.CompanyJPA;
import jpa.EventJPA;
import ejb.CompanyFacadeRemote;
import ejb.EventFacadeRemote;

/**
 * Managed Bean ShowEventMBean
 */
@ManagedBean(name = "eventUpdate")
@SessionScoped
public class UpdateEventMBean implements Serializable{	
	private static final long serialVersionUID = 1L;

	@EJB
	private EventFacadeRemote eventRemote;	
	private CompanyFacadeRemote companyRemote;
	protected EventJPA dataEvent;
	protected int idEvent;
	protected String name = "";
	protected String description = "";	
	protected String picture;
	protected Date initDate;
	protected Date endDate;
	protected List<AddressJPA> addressList = new ArrayList<AddressJPA>();
	protected AddressJPA address;
	protected int idAddress;
	
	protected List<CompanyJPA> companyList = new ArrayList<CompanyJPA>();
	protected CompanyJPA company;
	protected int idCompany;
	
	
	@SuppressWarnings("unchecked")
	public UpdateEventMBean() throws Exception {
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		eventRemote = (EventFacadeRemote) ctx.lookup("java:app/infogroup2eagenda.jar/EventFacadeBean!ejb.EventFacadeRemote");		
		companyRemote = (CompanyFacadeRemote) ctx.lookup("java:app/infogroup2eagenda.jar/CompanyFacadeBean!ejb.CompanyFacadeRemote");		
		this.companyList = (List<CompanyJPA>) companyRemote.listAllCompanies();	
		this.addressList = (List<AddressJPA>) eventRemote.listAllAdress();	
		setDataEvent();
	}	
	
	/**
	 * Get/set the id number and EventJPA instance
	 * @return Event Id
	 */
	//Necesitem passar el ID per a recaregar les dades. Sino no es recarreguen.
	public int getIdEvent(){
		return idEvent;
	}
	
	public EventJPA getDataEvent() {
		return dataEvent;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getPicture() {
		return picture;
	}

	public Date getInitDate() {
		return initDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public List<AddressJPA> getAddressList() {
		return addressList;
	}

	public AddressJPA getAddress() {
		return address;
	}
	
	public int getIdAddress(){
		return idAddress;
	}
	
	public void setIdEvent(int idEvent) throws Exception {
		this.idEvent = idEvent;
		setDataEvent();
	}

	public void setDataEvent(EventJPA dataEvent) {
		this.dataEvent = dataEvent;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public void setInitDate(Date initDate) {
		this.initDate = initDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setAddressList(List<AddressJPA> addressList) {
		this.addressList = addressList;
	}

	public void setAddress(AddressJPA address) {
		this.address = address;
	}
	
	public void setIdAddress(int idAddress) {
		this.idAddress = idAddress;
		this.address = obtainAdressFromId(idAddress);
		System.out.println("setIdAddress(int idAddress):" + idAddress + ":" + this.address.toString() );
	}
	
	public List<CompanyJPA> getCompanyList() {
		return companyList;
	}

	public void setCompanyList(List<CompanyJPA> companyList) {
		this.companyList = companyList;
	}

	public CompanyJPA getCompany() {
		return company;
	}

	public void setCompany(CompanyJPA company) {
		this.company = company;
	}

	public int getIdCompany() {
		return idCompany;
	}

	public void setIdCompany(int idCompany) {
		this.idCompany = idCompany;
		this.company = obtainCompanyFromId(idCompany);
	}
	
	@SuppressWarnings("unchecked")
	public void setDataEvent() throws Exception {			
		this.addressList = (List<AddressJPA>) eventRemote.listAllAdress();
		this.companyList = (List<CompanyJPA>) companyRemote.listAllCompanies();	
		dataEvent = (EventJPA) eventRemote.showEvent(this.idEvent);
		if(dataEvent != null){
			this.name = dataEvent.getName();
			this.description = dataEvent.getDescription();
			this.picture = dataEvent.getPicture();
			this.initDate = dataEvent.getInitDate();
			this.endDate = dataEvent.getEndDate();
			this.address = dataEvent.getAddress();
			this.idAddress = dataEvent.getAddress().getId();
			this.idCompany =  dataEvent.getCompany().getId();
		}
	}

	public String updateEvent()	{	
		try{
			eventRemote.updateEvent(this.idEvent, this.name, this.description, this.picture, this.address, this.initDate, this.endDate, this.company);
			return "eventAdminListView?faces-redirect=true&includeViewParams=true";	
		}catch (Exception e) {
			return "ErrorView?faces-redirect=true&includeViewParams=true";	
		}		
	}
	
	private AddressJPA obtainAdressFromId(int id){
		AddressJPA address = null;
		address = eventRemote.showAddress(id);
		return address;
	}
	
	private CompanyJPA obtainCompanyFromId(int id){
		CompanyJPA company = companyRemote.showCompany(id);
		return company;
	}
}