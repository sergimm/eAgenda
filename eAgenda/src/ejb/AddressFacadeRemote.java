/*
 * Copyright UOC.  All rights reserved.
 */
package ejb;

import java.util.Collection;


import javax.ejb.Remote;

import jpa.AddressJPA;



/**
 * Session EJB Remote Interfaces
 */
@Remote
public interface AddressFacadeRemote {
	public Collection<AddressJPA> listAllAddress();
	public void updateAddress(int id,String street, String number, String city,
			String state, String country, String zip);
	public AddressJPA showAddress(int id);
	public void addAddress(String street, String number, String city, String state,
			String country, String zip);
}