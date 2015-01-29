/*
 * Copyright UOC.  All rights reserved.
 */
package ejb;

import java.util.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import jpa.AddressJPA;

/**
 * EJB Session Bean Class of example "Practical Case Study JEE"
 */
@Stateless
public class AddressFacadeBean implements AddressFacadeRemote {	
	//Persistence Unit Context
	@PersistenceContext(unitName="infogroup2eagenda") 
	private EntityManager entman;
   
	/**
	 * Method that returns Collection of all categories
	 */
	@SuppressWarnings("unchecked")
	@Override
	public java.util.Collection<AddressJPA> listAllAddress() {		
		try{
			Collection<AddressJPA> allAddress = entman.createQuery("from AddressJPA").getResultList();
			return allAddress;	
		}catch (PersistenceException e) {
			throw e;
		}
	}	
	
	@Override
	public void addAddress(String street, String number, String city, String state, String country, String zip) {
		try	{
			AddressJPA address = new AddressJPA();			
			address.setStreet(street);
			address.setNumber(number);
			address.setCity(city);
			address.setState(state);
			address.setCountry(country);
			address.setZip(zip);
			entman.joinTransaction();
			entman.persist(address);
			entman.flush();
		}catch (PersistenceException e) {
			throw e;
		}
	}

	public void updateAddress(int id,String street, String number, String city, String state, String country, String zip) {
		try	{
			AddressJPA address = new AddressJPA();			
			address.setId(id);
			address.setStreet(street);
			address.setNumber(number);
			address.setCity(city);
			address.setState(state);
			address.setCountry(country);
			address.setZip(zip);
			entman.merge(address);
			entman.flush();
		}catch (PersistenceException e) {
			throw e;
		}
		
	}

	@Override
	public AddressJPA showAddress(int id) {
		AddressJPA address = null;
		try	{
			address = entman.find(AddressJPA.class, id);
		}catch (PersistenceException e) {
			throw e;
		}
	    return address;
	}
}