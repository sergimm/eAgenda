package ejb;

import javax.ejb.Local;

import jpa.AddressJPA;
import jpa.SuperadministratorJPA;
import jpa.UserJPA;

/**
 * Session EJB Local Interface
 */
@Local
public interface AdministrationFacade {
	
	public SuperadministratorJPA findAdmin (String email, String pass); 
	public UserJPA findUser (String email, String pass);
	public boolean addUser(UserJPA user, AddressJPA address);
	public UserJPA findUserByEmail (String email);

}
