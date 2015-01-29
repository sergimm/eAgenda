package ejb;

import java.util.Collection;

import javax.ejb.Local;

import jpa.AddressJPA;

/**
 * Session EJB Local Interfaces
 */
@Local
public interface AddressFacade {
	public Collection<AddressJPA> listAllAddress();
	public void updateAddress(int id,String street, String number, String city,
			String state, String country, String zip);
	public AddressJPA showAddress(int id);
	public void addAddress(String street, String number, String city, String state,
			String country, String zip);
}
