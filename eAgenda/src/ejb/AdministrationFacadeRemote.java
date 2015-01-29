package ejb;

import javax.ejb.Remote;

import jpa.AddressJPA;
import jpa.SuperadministratorJPA;
import jpa.UserJPA;

/**
 * Session EJB Remote Interfaces
 */
@Remote
public interface AdministrationFacadeRemote {
	
	public SuperadministratorJPA findAdmin (String email, String pass); 
	public UserJPA findUser (String email, String pass);
	public boolean addUser(UserJPA user, AddressJPA address);
	public UserJPA findUserByEmail (String email);

}
